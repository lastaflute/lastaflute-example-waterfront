package org.docksidestage.esflute.maihama;

import static org.codelibs.elasticsearch.runner.ElasticsearchClusterRunner.newConfigs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.codelibs.elasticsearch.runner.ElasticsearchClusterRunner;
import org.codelibs.elasticsearch.runner.net.Curl;
import org.codelibs.elasticsearch.runner.net.CurlResponse;
import org.dbflute.cbean.result.PagingResultBean;
import org.docksidestage.esflute.maihama.exbhv.ProductBhv;
import org.docksidestage.esflute.maihama.exentity.Product;
import org.docksidestage.unit.UnitWaterfrontTestCase;
import org.elasticsearch.common.settings.Settings.Builder;
import org.lastaflute.di.exception.IORuntimeException;

public class ProductTest extends UnitWaterfrontTestCase {

    private ElasticsearchClusterRunner runner;

    private String clusterName;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        clusterName = "es-waterfront-test";
        // create runner instance
        runner = new ElasticsearchClusterRunner();
        // create ES nodes
        runner.onBuild(new ElasticsearchClusterRunner.Builder() {
            @Override
            public void build(final int number, final Builder settingsBuilder) {
                settingsBuilder.put("http.cors.enabled", true);
                settingsBuilder.put("http.cors.allow-origin", "*");
                settingsBuilder.putArray("discovery.zen.ping.unicast.hosts", "localhost:9301-9305");
            }
        }).build(newConfigs().clusterName(clusterName).numOfNode(3));

        // wait for yellow status
        runner.ensureYellow();
    }

    @Override
    public void tearDown() throws Exception {
        // close runner
        runner.close();
        // delete all files
        runner.clean();
    }

    public void test_runCluster() throws Exception {
        final String index = "maihama";

        // create an index
        runner.createIndex(index, builder -> {
            try (final InputStream input = Thread.currentThread().getContextClassLoader().getResource("create-maihama.json").openStream()) {
                final String mappingSource = IOUtils.toString(input);
                builder.setSource(mappingSource);
            } catch (final IOException e) {
                throw new IORuntimeException(e);
            }
            return builder;
        });
        runner.ensureYellow(index);

        if (!runner.indexExists(index)) {
            fail();
        }

        // bulk
        try (final CurlResponse response = Curl.post(runner.node(), "/_bulk").onConnect((req, con) -> {
            con.setDoOutput(true);
            try (final InputStream input = Thread.currentThread().getContextClassLoader().getResource("data-maihama.json").openStream();
                    final OutputStream output = con.getOutputStream()) {
                IOUtils.copy(input, output);
            } catch (final IOException e) {
                throw new IORuntimeException(e);
            }
        }).execute()) {
            assertEquals(200, response.getHttpStatusCode());
        }

        runner.refresh();

        ProductBhv productBhv = getComponent(ProductBhv.class);

        // Match All Query
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                // TODO cb.query().addOrderBy_Id_Asc();
                cb.query().addOrderBy_ProductName_Asc();
                cb.paging(5, 1);
            });
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
            // assertEquals("", list1.get(0).asDocMeta().id());
            // assertEquals("", list1.get(1).asDocMeta().id());
            // assertEquals("", list1.get(2).asDocMeta().id());
            // assertEquals("", list1.get(3).asDocMeta().id());
            // assertEquals("", list1.get(4).asDocMeta().id());

            // middle page
            PagingResultBean<Product> list2 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.query().addOrderBy_ProductName_Asc();
                cb.paging(5, 2);
            });
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
            // assertEquals("", list2.get(0).asDocMeta().id());
            // assertEquals("", list2.get(1).asDocMeta().id());
            // assertEquals("", list2.get(2).asDocMeta().id());
            // assertEquals("", list2.get(3).asDocMeta().id());
            // assertEquals("", list2.get(4).asDocMeta().id());

            // last page
            PagingResultBean<Product> list3 = productBhv.selectPage(cb -> {
                cb.query().matchAll();
                cb.query().addOrderBy_ProductName_Asc();
                cb.paging(5, 4);
            });
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
            // assertEquals("", list3.get(0).asDocMeta().id());
            // assertEquals("", list3.get(1).asDocMeta().id());
            // assertEquals("", list3.get(2).asDocMeta().id());
            // assertEquals("", list3.get(3).asDocMeta().id());
            // assertEquals("", list3.get(4).asDocMeta().id());
        }

        // Match Query
        {
            // first page
            PagingResultBean<Product> list1 = productBhv.selectPage(cb -> {
                cb.query().setProductName_Match("flute");
                cb.query().addOrderBy_ProductName_Asc();
                cb.paging(5, 1);
            });
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
            // assertEquals("Gold Flute", list1.get(0).getProductName());
            // assertEquals("Low Price Flute", list1.get(1).getProductName());
            // assertEquals("Silver Flute", list1.get(2).getProductName());
        }

        // Match Phrase Query
        // Match Phrase Prefix Query
        // Multi Match Query
        // Common Terms Query
        // Query String Query
        // Simple Query String Query
        // Term Query
        // Terms Query
        // Range Query
        // Exists Query
        // Prefix Query
        // Wildcard Query
        // Regexp Query
        // Fuzzy Query
        // Type Query
        // Ids Query
        // Constant Score Query
        // Bool Query
        // Dis Max Query
        // Function Score Query
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
        // Cardinality Aggregation
        // Extended Stats Aggregation
        // Geo Bounds Aggregation
        // Geo Centroid Aggregation
        // Max Aggregation
        // Min Aggregation
        // Percentiles Aggregation
        // Percentile Ranks Aggregation
        // Scripted Metric Aggregation
        // Stats Aggregation
        // Sum Aggregation
        // Top hits Aggregation
        // Value Count Aggregation
        // Children Aggregation
        // Date Histogram Aggregation
        // Date Range Aggregation
        // Diversified Sampler Aggregation
        // Filter Aggregation
        // Filters Aggregation
        // Geo Distance Aggregation
        // GeoHash grid Aggregation
        // Global Aggregation
        // Histogram Aggregation
        // IP Range Aggregation
        // Missing Aggregation
        // Nested Aggregation
        // Range Aggregation
        // Reverse nested Aggregation
        // Sampler Aggregation
        // Significant Terms Aggregation
        // Terms Aggregation
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

        // wait for yellow status
        runner.ensureYellow();
    }
}
