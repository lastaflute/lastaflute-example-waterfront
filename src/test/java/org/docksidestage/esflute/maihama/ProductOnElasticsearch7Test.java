package org.docksidestage.esflute.maihama;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.codelibs.curl.Curl;
import org.codelibs.curl.CurlResponse;
import org.codelibs.fesen.client.HttpClient;
import org.codelibs.fesen.client.HttpClient.ContentType;
import org.dbflute.cbean.result.PagingResultBean;
import org.docksidestage.client.ClientWrapper;
import org.docksidestage.esflute.maihama.allcommon.EsPagingResultBean;
import org.docksidestage.esflute.maihama.exbhv.ProductBhv;
import org.docksidestage.esflute.maihama.exentity.Product;
import org.docksidestage.unit.UnitWaterfrontTestCase;
import org.lastaflute.di.exception.IORuntimeException;
import org.opensearch.action.admin.indices.create.CreateIndexResponse;
import org.opensearch.common.settings.Settings;
import org.opensearch.common.xcontent.XContentType;
import org.opensearch.index.query.functionscore.ScoreFunctionBuilders;
import org.opensearch.search.aggregations.Aggregations;
import org.opensearch.search.aggregations.bucket.filter.Filter;
import org.opensearch.search.aggregations.bucket.histogram.Histogram;
import org.opensearch.search.aggregations.bucket.missing.Missing;
import org.opensearch.search.aggregations.bucket.range.Range;
import org.opensearch.search.aggregations.bucket.terms.Terms;
import org.opensearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.opensearch.search.aggregations.metrics.Avg;
import org.opensearch.search.aggregations.metrics.Cardinality;
import org.opensearch.search.aggregations.metrics.ExtendedStats;
import org.opensearch.search.aggregations.metrics.Max;
import org.opensearch.search.aggregations.metrics.Min;
import org.opensearch.search.aggregations.metrics.PercentileRanks;
import org.opensearch.search.aggregations.metrics.Percentiles;
import org.opensearch.search.aggregations.metrics.Stats;
import org.opensearch.search.aggregations.metrics.Sum;
import org.opensearch.search.aggregations.metrics.ValueCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class ProductOnElasticsearch7Test extends UnitWaterfrontTestCase {

    private static final Logger logger = LoggerFactory.getLogger(ProductOnElasticsearch7Test.class);

    private final String version = "7.17.0";

    private final String imageTag = "docker.elastic.co/elasticsearch/elasticsearch:" + version;

    private GenericContainer server;

    private HttpClient client;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        server = new GenericContainer<>(DockerImageName.parse(imageTag))//
                .withEnv("discovery.type", "single-node")//
                .withEnv("xpack.security.enabled", "false")//
                .withExposedPorts(9200);
        server.start();

        final String url = "http://" + server.getHost() + ":" + server.getFirstMappedPort();
        logger.info("Elasticsearch " + version + ": " + url);
        for (int i = 0; i < 10; i++) {
            try (CurlResponse response = Curl.get(url).execute()) {
                if (response.getHttpStatusCode() == 200) {
                    logger.info(url + " is available.");
                    break;
                }
            } catch (Exception e) {
                logger.debug(e.getLocalizedMessage());
            }
            try {
                logger.info("Waiting for " + url);
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                // nothing
            }
        }

        final String host = server.getHost() + ":" + server.getFirstMappedPort();
        final Settings settings = Settings.builder().putList("http.hosts", host).put("http.compression", true).build();
        client = new HttpClient(settings, null);
        ((ClientWrapper) getComponent("client")).setClient(client);
    }

    @Override
    public void tearDown() throws Exception {
        logger.info("Closing client");
        client.close();
        server.stop();
    }

    public void test_runCluster() throws Exception {
        final String memberIndex = "member";
        final String productIndex = "product";

        // create an index
        {
            final String settingsSource = "{\n" + //
                    "        \"index\": {\n" + //
                    "            \"number_of_shards\": \"2\",\n" + //
                    "            \"number_of_replicas\": \"0\"\n" + //
                    "        }\n" + //
                    "    }";
            final String mappingSource = "{\n" + //
                    "            \"properties\": {\n" + //
                    "                \"account\": {\n" + //
                    "                    \"type\": \"keyword\"\n" + //
                    "                },\n" + //
                    "                \"name\": {\n" + //
                    "                    \"type\": \"text\",\n" + //
                    "                    \"analyzer\": \"standard\",\n" + //
                    "                    \"fields\": {\n" + //
                    "                        \"raw\": {\n" + //
                    "                            \"type\": \"keyword\"\n" + //
                    "                        }\n" + //
                    "                    }\n" + //
                    "                }\n" + //
                    "            }\n" + //
                    "        }";
            final CreateIndexResponse response = client.admin()
                    .indices()
                    .prepareCreate(memberIndex)
                    .setSettings(settingsSource, XContentType.JSON)//
                    .setMapping(mappingSource)//
                    .execute()
                    .actionGet();
            assertTrue(response.isAcknowledged());
        }
        refresh(memberIndex);
        existsIndices(memberIndex);

        {
            final String settingsSource = "{\n" + //
                    "        \"index\": {\n" + //
                    "            \"number_of_shards\": \"2\",\n" + //
                    "            \"number_of_replicas\": \"0\"\n" + //
                    "        }\n" + //
                    "    }";
            final String mappingSource = "{\n" + //
                    "            \"properties\": {\n" + //
                    "                \"latest_purchase_date\": {\n" + //
                    "                    \"type\": \"date\",\n" + //
                    "                    \"format\": \"date_optional_time\"\n" + //
                    "                },\n" + //
                    "                \"product_category\": {\n" + //
                    "                    \"type\": \"keyword\"\n" + //
                    "                },\n" + //
                    "                \"product_category_code\": {\n" + //
                    "                    \"type\": \"keyword\"\n" + //
                    "                },\n" + //
                    "                \"product_description\": {\n" + //
                    "                    \"type\": \"text\",\n" + //
                    "                    \"analyzer\": \"standard\"\n" + //
                    "                },\n" + //
                    "                \"product_handle_code\": {\n" + //
                    "                    \"type\": \"keyword\"\n" + //
                    "                },\n" + //
                    "                \"product_name\": {\n" + //
                    "                    \"type\": \"text\",\n" + //
                    "                    \"fielddata\": true,\n" + //
                    "                    \"analyzer\": \"standard\",\n" + //
                    "                    \"fields\": {\n" + //
                    "                        \"raw\": {\n" + //
                    "                            \"type\": \"keyword\"\n" + //
                    "                        }\n" + //
                    "                    }\n" + //
                    "                },\n" + //
                    "                \"product_status\": {\n" + //
                    "                    \"type\": \"keyword\"\n" + //
                    "                },\n" + //
                    "                \"product_status_code\": {\n" + //
                    "                    \"type\": \"keyword\"\n" + //
                    "                },\n" + //
                    "                \"register_datetime\": {\n" + //
                    "                    \"type\": \"date\",\n" + //
                    "                    \"format\": \"date_optional_time\"\n" + //
                    "                },\n" + //
                    "                \"register_user\": {\n" + //
                    "                    \"type\": \"keyword\"\n" + //
                    "                },\n" + //
                    "                \"regular_price\": {\n" + //
                    "                    \"type\": \"integer\"\n" + //
                    "                },\n" + //
                    "                \"update_datetime\": {\n" + //
                    "                    \"type\": \"date\",\n" + //
                    "                    \"format\": \"date_optional_time\"\n" + //
                    "                },\n" + //
                    "                \"update_user\": {\n" + //
                    "                    \"type\": \"keyword\"\n" + //
                    "                }\n" + //
                    "            }\n" + //
                    "        }";
            final CreateIndexResponse response = client.admin()
                    .indices()
                    .prepareCreate(productIndex)
                    .setSettings(settingsSource, XContentType.JSON)//
                    .setMapping(mappingSource)//
                    .execute()
                    .actionGet();
            assertTrue(response.isAcknowledged());
        }
        refresh(productIndex);
        existsIndices(productIndex);

        // bulk
        try (final CurlResponse response = client.getCurlRequest(Curl::post, ContentType.X_NDJSON, "/_bulk").onConnect((req, con) -> {
            con.setDoOutput(true);
            try (final InputStream input = Thread.currentThread().getContextClassLoader().getResource("data-maihama.ndjson").openStream();
                    final OutputStream output = con.getOutputStream()) {
                IOUtils.copy(input, output);
            } catch (final IOException e) {
                throw new IORuntimeException(e);
            }
        }).execute()) {
            assertEquals(response.getContentAsString(), 200, response.getHttpStatusCode());
        }

        refresh();

        ProductBhv productBhv = getComponent(ProductBhv.class);

        // Match All Query
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.query().addOrderBy_RegisterDatetime_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(20, list1.getAllRecordCount());
            assertEquals(4, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertEquals(2, list1.getNextPageNumber());
            assertFalse(list1.existsPreviousPage());
            assertTrue(list1.existsNextPage());
            assertEquals("1", list1.get(0).asDocMeta().id());
            assertEquals("2", list1.get(1).asDocMeta().id());
            assertEquals("3", list1.get(2).asDocMeta().id());
            assertEquals("4", list1.get(3).asDocMeta().id());
            assertEquals("5", list1.get(4).asDocMeta().id());

            // middle page
            PagingResultBean<Product> list2 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.query().addOrderBy_RegisterDatetime_Asc();
                cb.paging(5, 2);
            });
            System.out.println(((EsPagingResultBean<Product>) list2).getQueryDsl());
            assertEquals(5, list2.size());
            assertEquals(20, list2.getAllRecordCount());
            assertEquals(4, list2.getAllPageCount());
            assertEquals(2, list2.getCurrentPageNumber());
            assertEquals(6, list2.getCurrentStartRecordNumber());
            assertEquals(10, list2.getCurrentEndRecordNumber());
            assertEquals(1, list2.getPreviousPageNumber());
            assertEquals(3, list2.getNextPageNumber());
            assertTrue(list2.existsPreviousPage());
            assertTrue(list2.existsNextPage());
            assertEquals("6", list2.get(0).asDocMeta().id());
            assertEquals("7", list2.get(1).asDocMeta().id());
            assertEquals("8", list2.get(2).asDocMeta().id());
            assertEquals("9", list2.get(3).asDocMeta().id());
            assertEquals("10", list2.get(4).asDocMeta().id());

            // last page
            PagingResultBean<Product> list3 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.query().addOrderBy_RegisterDatetime_Asc();
                cb.paging(5, 4);
            });
            System.out.println(((EsPagingResultBean<Product>) list3).getQueryDsl());
            assertEquals(5, list3.size());
            assertEquals(20, list3.getAllRecordCount());
            assertEquals(4, list3.getAllPageCount());
            assertEquals(4, list3.getCurrentPageNumber());
            assertEquals(16, list3.getCurrentStartRecordNumber());
            assertEquals(20, list3.getCurrentEndRecordNumber());
            assertEquals(3, list3.getPreviousPageNumber());
            try {
                list3.getNextPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertTrue(list3.existsPreviousPage());
            assertFalse(list3.existsNextPage());
            assertEquals("16", list3.get(0).asDocMeta().id());
            assertEquals("17", list3.get(1).asDocMeta().id());
            assertEquals("18", list3.get(2).asDocMeta().id());
            assertEquals("19", list3.get(3).asDocMeta().id());
            assertEquals("20", list3.get(4).asDocMeta().id());
        }

        // Match Query
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().setProductName_Match("flute");
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(3, list1.size());
            assertEquals(3, list1.getAllRecordCount());
            assertEquals(1, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(3, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            try {
                list1.getNextPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertFalse(list1.existsPreviousPage());
            assertFalse(list1.existsNextPage());
            assertEquals("FLUTE-01", list1.get(0).getProductHandleCode());
            assertEquals("FLUTE-02", list1.get(1).getProductHandleCode());
            assertEquals("FLUTE-03", list1.get(2).getProductHandleCode());
        }

        // Match Phrase Query
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().setProductName_MatchPhrase("Low Price Flute");
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(1, list1.size());
            assertEquals(1, list1.getAllRecordCount());
            assertEquals(1, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(1, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            try {
                list1.getNextPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertFalse(list1.existsPreviousPage());
            assertFalse(list1.existsNextPage());
            assertEquals("FLUTE-01", list1.get(0).getProductHandleCode());
        }

        // Match Phrase Prefix Query
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().setProductName_MatchPhrasePrefix("L", op -> {
                    op.maxExpansions(1);
                });
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(2, list1.size());
            assertEquals(2, list1.getAllRecordCount());
            assertEquals(1, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(2, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            try {
                list1.getNextPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertFalse(list1.existsPreviousPage());
            assertFalse(list1.existsNextPage());
            assertEquals("FLUTE-01", list1.get(0).getProductHandleCode());
            assertEquals("HARB-100-02", list1.get(1).getProductHandleCode());
        }

        // Multi Match Query
        // Common Terms Query
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().setProductName_CommonTerms("What is Grand Piano", op -> {
                    op.cutoffFrequency(0.001f);
                });
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(2, list1.size());
            assertEquals(2, list1.getAllRecordCount());
            assertEquals(1, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(2, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            try {
                list1.getNextPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertFalse(list1.existsPreviousPage());
            assertFalse(list1.existsNextPage());
            assertEquals("BILLYJOEL-02", list1.get(0).getProductHandleCode());
        }

        // Query String Query
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().queryString("Flute OR Piano", op -> {
                    op.defaultField("product_name");
                });
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(6, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(6, list1.size());
            assertEquals(6, list1.getAllRecordCount());
            assertEquals(1, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(6, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            try {
                list1.getNextPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertFalse(list1.existsPreviousPage());
            assertFalse(list1.existsNextPage());
            assertEquals("BILLYJOEL-02", list1.get(0).getProductHandleCode());
            assertEquals("FLUTE-01", list1.get(1).getProductHandleCode());
            assertEquals("FLUTE-02", list1.get(2).getProductHandleCode());
            assertEquals("FLUTE-03", list1.get(3).getProductHandleCode());
            assertEquals("PIANO-01", list1.get(4).getProductHandleCode());
            assertEquals("PIANO-02", list1.get(5).getProductHandleCode());
        }

        // Simple Query String Query
        // Term Query
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().setProductHandleCode_Term("FLUTE-01", op -> {
                    op.queryName("exact_value");
                });
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(1, list1.size());
            assertEquals(1, list1.getAllRecordCount());
            assertEquals(1, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(1, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertFalse(list1.existsPreviousPage());
            assertEquals("FLUTE-01", list1.get(0).getProductHandleCode());
        }

        // Terms Query
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                ArrayList<String> terms = new ArrayList<>();
                terms.add("piano");
                terms.add("flute");
                cb.query().setProductName_Terms(terms);
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(6, list1.getAllRecordCount());
            assertEquals(2, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertFalse(list1.existsPreviousPage());
            assertEquals("BILLYJOEL-02", list1.get(0).getProductHandleCode());
            assertEquals("FLUTE-01", list1.get(1).getProductHandleCode());
            assertEquals("FLUTE-02", list1.get(2).getProductHandleCode());
            assertEquals("FLUTE-03", list1.get(3).getProductHandleCode());
            assertEquals("PIANO-01", list1.get(4).getProductHandleCode());
        }

        // Range Query
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().setLatestPurchaseDate_GreaterEqual(LocalDateTime.of(2014, 1, 1, 0, 0, 0));
                cb.query().addOrderBy_LatestPurchaseDate_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(2, list1.size());
            assertEquals(2, list1.getAllRecordCount());
            assertEquals(1, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(2, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertFalse(list1.existsPreviousPage());
            assertEquals("BILLYJOEL-05", list1.get(0).getProductHandleCode());
            assertEquals("HARB-100-01", list1.get(1).getProductHandleCode());
        }

        // Exists Query
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().setProductName_Exists();
                cb.query().addOrderBy_ProductName_raw_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(20, list1.getAllRecordCount());
            assertEquals(4, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertFalse(list1.existsPreviousPage());
        }

        // Prefix Query
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().setProductCategory_Prefix("Ins");
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(5, list1.getAllRecordCount());
            assertEquals(1, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            try {
                list1.getNextPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertFalse(list1.existsPreviousPage());
            assertFalse(list1.existsNextPage());
            assertEquals("FLUTE-01", list1.get(0).getProductHandleCode());
            assertEquals("FLUTE-02", list1.get(1).getProductHandleCode());
            assertEquals("FLUTE-03", list1.get(2).getProductHandleCode());
            assertEquals("PIANO-01", list1.get(3).getProductHandleCode());
            assertEquals("PIANO-02", list1.get(4).getProductHandleCode());
        }

        // Wildcard Query
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().setProductCategoryCode_Wildcard("H?B");
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(4, list1.size());
            assertEquals(4, list1.getAllRecordCount());
            assertEquals(1, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(4, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            try {
                list1.getNextPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertFalse(list1.existsPreviousPage());
            assertFalse(list1.existsNextPage());
            assertEquals("HARB-100-01", list1.get(0).getProductHandleCode());
            assertEquals("HARB-100-02", list1.get(1).getProductHandleCode());
            assertEquals("HARB-100-03", list1.get(2).getProductHandleCode());
            assertEquals("HARB-100-04", list1.get(3).getProductHandleCode());
        }

        // Regexp Query
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().setProductHandleCode_Regexp("[A-Z]{5}-<01-02>");
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(4, list1.size());
            assertEquals(4, list1.getAllRecordCount());
            assertEquals(1, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(4, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertFalse(list1.existsPreviousPage());
            assertEquals("FLUTE-01", list1.get(0).getProductHandleCode());
            assertEquals("FLUTE-02", list1.get(1).getProductHandleCode());
            assertEquals("PIANO-01", list1.get(2).getProductHandleCode());
            assertEquals("PIANO-02", list1.get(3).getProductHandleCode());
        }

        // Fuzzy Query
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().setProductCategory_Fuzzy("Insturument");
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(5, list1.getAllRecordCount());
            assertEquals(1, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertFalse(list1.existsPreviousPage());
            assertEquals("FLUTE-01", list1.get(0).getProductHandleCode());
            assertEquals("FLUTE-02", list1.get(1).getProductHandleCode());
            assertEquals("FLUTE-03", list1.get(2).getProductHandleCode());
            assertEquals("PIANO-01", list1.get(3).getProductHandleCode());
            assertEquals("PIANO-02", list1.get(4).getProductHandleCode());
        }

        // Type Query
        // Ids Query
        // Constant Score Query
        // Bool Query
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().bool((must, should, mustNot, filter) -> {
                    must.setProductName_Match("flute");
                    mustNot.setProductName_Match("gold");
                });
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(2, list1.size());
            assertEquals(2, list1.getAllRecordCount());
            assertEquals(1, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(2, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            try {
                list1.getNextPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertFalse(list1.existsPreviousPage());
            assertFalse(list1.existsNextPage());
            assertEquals("FLUTE-01", list1.get(0).getProductHandleCode());
            assertEquals("FLUTE-02", list1.get(1).getProductHandleCode());
        }

        // Dis Max Query
        // Function Score Query
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().functionScore(query -> {
                    query.setProductCategory_Equal("MusicCD");
                }, functions -> {
                    functions.filter(cq -> {
                        cq.setProductName_Match("street");
                    }, ScoreFunctionBuilders.weightFactorFunction(10.0f));
                }, op -> {});
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(11, list1.getAllRecordCount());
            assertEquals(3, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertEquals(2, list1.getNextPageNumber());
            assertFalse(list1.existsPreviousPage());
            assertTrue(list1.existsNextPage());
            assertEquals("6", list1.get(0).asDocMeta().id());
        }
        // Boosting Query
        // Indices Query
        // Nested Query
        // Has Child Query
        // Has Parent Query
        // Parent Id Query
        // GeoShape Query
        // Geo Bounding Box Query
        // Geo Distance Query
        // Geo Distance Range Query
        // Geo Polygon Query
        // More Like This Query
        // Template Query
        // Script Query
        // Percolate Query
        // Span Term Query
        // Span Multi Term Query
        // Span First Query
        // Span Near Query
        // Span Or Query
        // Span Not Query
        // Span Containing Query
        // Span Within Query
        // Span Field Masking Query

        // Avg Aggregation
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.aggregation().setRegularPrice_Avg();
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(20, list1.getAllRecordCount());
            assertEquals(4, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertEquals(2, list1.getNextPageNumber());
            assertFalse(list1.existsPreviousPage());
            assertTrue(list1.existsNextPage());
            Aggregations aggregations = ((EsPagingResultBean<Product>) list1).getAggregations();
            Avg avg = (Avg) aggregations.get("regular_price");
            assertEquals(353455.0, avg.getValue());
        }
        // Cardinality Aggregation
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.aggregation().setProductCategory_Cardinality();
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(20, list1.getAllRecordCount());
            assertEquals(4, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertEquals(2, list1.getNextPageNumber());
            assertFalse(list1.existsPreviousPage());
            assertTrue(list1.existsNextPage());
            Aggregations aggregations = ((EsPagingResultBean<Product>) list1).getAggregations();
            Cardinality cardinality = (Cardinality) aggregations.get("product_category");
            assertEquals(3, cardinality.getValue());
        }
        // Extended Stats Aggregation
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.aggregation().setRegularPrice_ExtendedStats();
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(20, list1.getAllRecordCount());
            assertEquals(4, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertEquals(2, list1.getNextPageNumber());
            assertFalse(list1.existsPreviousPage());
            assertTrue(list1.existsNextPage());
            Aggregations aggregations = ((EsPagingResultBean<Product>) list1).getAggregations();
            ExtendedStats extendedStats = (ExtendedStats) aggregations.get("regular_price");
            assertEquals(353455.0, extendedStats.getAvg());
        }
        // Geo Bounds Aggregation
        // Geo Centroid Aggregation
        // Max Aggregation
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.aggregation().setRegularPrice_Max();
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(20, list1.getAllRecordCount());
            assertEquals(4, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertEquals(2, list1.getNextPageNumber());
            assertFalse(list1.existsPreviousPage());
            assertTrue(list1.existsNextPage());
            Aggregations aggregations = ((EsPagingResultBean<Product>) list1).getAggregations();
            Max max = (Max) aggregations.get("regular_price");
            assertEquals(4000000.0, max.getValue());
        }
        // Min Aggregation
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.aggregation().setRegularPrice_Min();
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(20, list1.getAllRecordCount());
            assertEquals(4, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertEquals(2, list1.getNextPageNumber());
            assertFalse(list1.existsPreviousPage());
            assertTrue(list1.existsNextPage());
            Aggregations aggregations = ((EsPagingResultBean<Product>) list1).getAggregations();
            Min min = (Min) aggregations.get("regular_price");
            assertEquals(340.0, min.getValue());
        }
        // Percentiles Aggregation
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.aggregation().setRegularPrice_Percentiles();
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(20, list1.getAllRecordCount());
            assertEquals(4, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertEquals(2, list1.getNextPageNumber());
            assertFalse(list1.existsPreviousPage());
            assertTrue(list1.existsNextPage());
            Aggregations aggregations = ((EsPagingResultBean<Product>) list1).getAggregations();
            Percentiles percentiles = (Percentiles) aggregations.get("regular_price");
            assertEquals(340.0, percentiles.percentile(1));
            assertEquals(360.0, percentiles.percentile(5));
            assertEquals(1150.0, percentiles.percentile(25));
            assertEquals(1650.0, percentiles.percentile(50));
            assertEquals(26050.0, percentiles.percentile(75));
            assertEquals(2700000.0, percentiles.percentile(95));
            assertEquals(4000000.0, percentiles.percentile(99));
        }
        // Percentile Ranks Aggregation
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.aggregation().setRegularPrice_PercentileRanks(new double[] { 1000, 10000 });
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(20, list1.getAllRecordCount());
            assertEquals(4, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertEquals(2, list1.getNextPageNumber());
            assertFalse(list1.existsPreviousPage());
            assertTrue(list1.existsNextPage());
            Aggregations aggregations = ((EsPagingResultBean<Product>) list1).getAggregations();
            PercentileRanks percentileRanks = (PercentileRanks) aggregations.get("regular_price");
            assertEquals(23.125, percentileRanks.percent(1000));
            assertEquals(71.65625, percentileRanks.percent(10000));
        }
        // Scripted Metric Aggregation
        // Stats Aggregation
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.aggregation().setRegularPrice_Stats();
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(20, list1.getAllRecordCount());
            assertEquals(4, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertEquals(2, list1.getNextPageNumber());
            assertFalse(list1.existsPreviousPage());
            assertTrue(list1.existsNextPage());
            Aggregations aggregations = ((EsPagingResultBean<Product>) list1).getAggregations();
            Stats stats = (Stats) aggregations.get("regular_price");
            assertEquals(353455.0, stats.getAvg());
        }
        // Sum Aggregation
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.aggregation().setRegularPrice_Sum();
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(20, list1.getAllRecordCount());
            assertEquals(4, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertEquals(2, list1.getNextPageNumber());
            assertFalse(list1.existsPreviousPage());
            assertTrue(list1.existsNextPage());
            Aggregations aggregations = ((EsPagingResultBean<Product>) list1).getAggregations();
            Sum sum = (Sum) aggregations.get("regular_price");
            assertEquals(7069100.0, sum.getValue());
        }
        // Top hits Aggregation
        // Value Count Aggregation
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.aggregation().setProductCategory_Count();
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(20, list1.getAllRecordCount());
            assertEquals(4, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertEquals(2, list1.getNextPageNumber());
            assertFalse(list1.existsPreviousPage());
            assertTrue(list1.existsNextPage());
            Aggregations aggregations = ((EsPagingResultBean<Product>) list1).getAggregations();
            ValueCount valueCount = (ValueCount) aggregations.get("product_category");
            assertEquals(20, valueCount.getValue());
        }
        // Children Aggregation
        // Date Histogram Aggregation
        // Date Range Aggregation
        // Diversified Sampler Aggregation
        // Filter Aggregation
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.aggregation().filter("filter", cq -> cq.setProductName_Equal("flute"), op -> {}, aggs -> aggs.setRegularPrice_Avg());
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(20, list1.getAllRecordCount());
            assertEquals(4, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertEquals(2, list1.getNextPageNumber());
            assertFalse(list1.existsPreviousPage());
            assertTrue(list1.existsNextPage());
            Aggregations aggregations = ((EsPagingResultBean<Product>) list1).getAggregations();
            Filter filter = (Filter) aggregations.get("filter");
            Avg avg = (Avg) filter.getAggregations().get("regular_price");
            assertEquals(1516666.6666666667, avg.getValue());
        }
        // Filters Aggregation
        // Geo Distance Aggregation
        // GeoHash grid Aggregation
        // Global Aggregation
        // Histogram Aggregation
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().setRegularPrice_LessThan(2000);
                cb.aggregation().setRegularPrice_Histogram(op -> {
                    op.interval(200).extendedBounds(0L, 2000L);
                }, aggs -> {});
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(13, list1.getAllRecordCount());
            assertEquals(3, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertEquals(2, list1.getNextPageNumber());
            assertFalse(list1.existsPreviousPage());
            assertTrue(list1.existsNextPage());
            Aggregations aggregations = ((EsPagingResultBean<Product>) list1).getAggregations();
            Histogram histogram = (Histogram) aggregations.get("regular_price");
            assertEquals(11, histogram.getBuckets().size());
            assertEquals(0L, histogram.getBuckets().get(0).getDocCount());
            assertEquals(3L, histogram.getBuckets().get(1).getDocCount());
            assertEquals(1L, histogram.getBuckets().get(2).getDocCount());
            assertEquals(0L, histogram.getBuckets().get(3).getDocCount());
            assertEquals(0L, histogram.getBuckets().get(4).getDocCount());
            assertEquals(1L, histogram.getBuckets().get(5).getDocCount());
            assertEquals(2L, histogram.getBuckets().get(6).getDocCount());
            assertEquals(2L, histogram.getBuckets().get(7).getDocCount());
            assertEquals(2L, histogram.getBuckets().get(8).getDocCount());
            assertEquals(2L, histogram.getBuckets().get(9).getDocCount());
            assertEquals(0L, histogram.getBuckets().get(10).getDocCount());
        }
        // IP Range Aggregation
        // Missing Aggregation
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.aggregation().setRegularPrice_Missing();
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(20, list1.getAllRecordCount());
            assertEquals(4, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertFalse(list1.existsPreviousPage());
            assertTrue(list1.existsNextPage());
            Aggregations aggregations = ((EsPagingResultBean<Product>) list1).getAggregations();
            Missing missing = (Missing) aggregations.get("regular_price");
            assertEquals(0, missing.getDocCount());
        }
        // Nested Aggregation
        // Range Aggregation
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.aggregation().setRegularPrice_Range(op -> {
                    op.addUnboundedFrom("all", 0).addRange("average", 1000, 5000).addUnboundedTo("cheap", 1000);
                }, aggs -> {});
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(20, list1.getAllRecordCount());
            assertEquals(4, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertFalse(list1.existsPreviousPage());
            assertTrue(list1.existsNextPage());
            Aggregations aggregations = ((EsPagingResultBean<Product>) list1).getAggregations();
            Range range = (Range) aggregations.get("regular_price");
            assertEquals(3, range.getBuckets().size());
            range.getBuckets().forEach(bucket -> {
                System.out.println(bucket.getFromAsString());
                System.out.println(bucket.getToAsString());
                System.out.println(bucket.getKeyAsString());
                System.out.println(bucket.getDocCount());
            });
            //assertEquals(4, range.getBuckets().get(0).getDocCount());
            //assertEquals(15, range.getBuckets().get(0).getDocCount());
            //assertEquals(11, range.getBuckets().get(0).getDocCount());
        }
        // Reverse nested Aggregation
        // Sampler Aggregation
        // Significant Terms Aggregation
        // Terms Aggregation
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.aggregation().setProductName_Terms();
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list1).getQueryDsl());
            assertEquals(5, list1.size());
            assertEquals(20, list1.getAllRecordCount());
            assertEquals(4, list1.getAllPageCount());
            assertEquals(1, list1.getCurrentPageNumber());
            assertEquals(1, list1.getCurrentStartRecordNumber());
            assertEquals(5, list1.getCurrentEndRecordNumber());
            try {
                list1.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertEquals(2, list1.getNextPageNumber());
            assertFalse(list1.existsPreviousPage());
            assertTrue(list1.existsNextPage());
            Aggregations aggregations = ((EsPagingResultBean<Product>) list1).getAggregations();
            Terms terms = (Terms) aggregations.get("product_name");
            List<? extends Bucket> buckets = terms.getBuckets();
            assertEquals(10, buckets.size());
            Bucket bucket1 = buckets.get(0);
            assertEquals("100g", bucket1.getKey());
            assertEquals(4, bucket1.getDocCount());
            Bucket bucket2 = buckets.get(1);
            assertEquals("flute", bucket2.getKey());
            assertEquals(3, bucket2.getDocCount());
            Bucket bucket3 = buckets.get(2);
            assertEquals("piano", bucket3.getKey());
            assertEquals(3, bucket3.getDocCount());

            // sug aggs
            PagingResultBean<Product> list2 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.aggregation().setProductName_Terms(op -> {}, aggs -> aggs.setRegularPrice_Avg());
                cb.query().addOrderBy_ProductHandleCode_Asc();
                cb.paging(5, 1);
            });
            System.out.println(((EsPagingResultBean<Product>) list2).getQueryDsl());
            assertEquals(5, list2.size());
            assertEquals(20, list2.getAllRecordCount());
            assertEquals(4, list2.getAllPageCount());
            assertEquals(1, list2.getCurrentPageNumber());
            assertEquals(1, list2.getCurrentStartRecordNumber());
            assertEquals(5, list2.getCurrentEndRecordNumber());
            try {
                list2.getPreviousPageNumber();
                fail();
            } catch (IllegalStateException e) {
                // pass
            }
            assertEquals(2, list2.getNextPageNumber());
            assertFalse(list2.existsPreviousPage());
            assertTrue(list2.existsNextPage());
            Aggregations aggregations2 = ((EsPagingResultBean<Product>) list2).getAggregations();
            Terms terms2 = (Terms) aggregations2.get("product_name");
            List<? extends Bucket> buckets2 = terms2.getBuckets();
            assertEquals(10, buckets2.size());
            Bucket bucket21 = buckets2.get(0);
            assertEquals("100g", bucket21.getKey());
            assertEquals(4, bucket21.getDocCount());
            Aggregations aggregations3 = bucket21.getAggregations();
            Avg avg = (Avg) aggregations3.get("regular_price");
            assertEquals(375.0, avg.getValue());
        }
        // Avg Bucket Aggregation
        // Derivative Aggregation
        // Max Bucket Aggregation
        // Min Bucket Aggregation
        // Sum Bucket Aggregation
        // Stats Bucket Aggregation
        // Extended Stats Bucket Aggregation
        // Percentiles Bucket Aggregation
        // Moving Average Aggregation
        // Cumulative Sum Aggregation
        // Bucket Script Aggregation
        // Bucket Selector Aggregation
        // Serial Differencing Aggregation
        // Matrix Stats

        {
            // insert
            Product product = new Product();
            product.setProductName("Test Product1");
            productBhv.insert(product);
        }

    }

    private void existsIndices(final String... indices) {
        assertTrue(client.admin().indices().prepareExists(indices).execute().actionGet().isExists());
    }

    private void refresh(final String... indices) {
        assertEquals(200, client.admin().indices().prepareRefresh(indices).execute().actionGet().getStatus().getStatus());
    }
}
