package org.docksidestage.app.job;

import org.docksidestage.unit.UnitWaterfrontTestCase;

/**
 * @author jflute
 */
public class SeaJobTest extends UnitWaterfrontTestCase {

    public void test_run() throws Exception {
        // ## Arrange ##
        SeaJob job = new SeaJob();
        inject(job);

        // ## Act ##
        job.run(getMockJobRuntime());

        // ## Assert ##
    }
}
