package org.docksidestage.esflute.maihama;

import static org.codelibs.elasticsearch.runner.ElasticsearchClusterRunner.newConfigs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.codelibs.elasticsearch.runner.ElasticsearchClusterRunner;
import org.codelibs.elasticsearch.runner.net.Curl;
import org.codelibs.elasticsearch.runner.net.CurlResponse;
import org.dbflute.cbean.result.ListResultBean;
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

        {
            // Match All Query
            ListResultBean<Product> list = productBhv.selectList(cb -> cb.query().matchAll());
            assertEquals(10, list.size());
            assertEquals(20, list.getAllRecordCount());
        }

        // Match Query
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
