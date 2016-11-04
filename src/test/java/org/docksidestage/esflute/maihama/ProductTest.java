package org.docksidestage.esflute.maihama;

import static org.codelibs.elasticsearch.runner.ElasticsearchClusterRunner.newConfigs;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.codelibs.elasticsearch.runner.ElasticsearchClusterRunner;
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

        ProductBhv productBhv = getComponent(ProductBhv.class);

        // insert
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        product1.setProductName("Test Product1");
        product2.setProductName("Test Product2");
        product3.setProductName("Test Product3");
        productBhv.insert(product1);
        productBhv.insert(product2);
        productBhv.insert(product3);
        runner.refresh();

        // Match All Query
        productBhv.selectCursor(cb -> {  }, product -> {
            assertContains(product.getProductName(), "Test Product");
        });

        // wait for yellow status
        runner.ensureYellow();
    }
}
