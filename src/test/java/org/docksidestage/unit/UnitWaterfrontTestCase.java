/*
 * Copyright 2015-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.unit;

import javax.annotation.Resource;

import org.dbflute.utflute.lastaflute.WebContainerTestCase;
import org.lastaflute.job.JobManager;
import org.lastaflute.job.mock.MockJobRuntime;

/**
 * Use like this:
 * <pre>
 * YourTest extends {@link UnitWaterfrontTestCase} {
 * 
 *     public void test_yourMethod() {
 *         <span style="color: #3F7E5E">// ## Arrange ##</span>
 *         YourAction action = new YourAction();
 *         <span style="color: #FD4747">inject</span>(action);
 * 
 *         <span style="color: #3F7E5E">// ## Act ##</span>
 *         action.submit();
 * 
 *         <span style="color: #3F7E5E">// ## Assert ##</span>
 *         assertTrue(action...);
 *     }
 * }
 * </pre>
 * @author jflute
 */
public abstract class UnitWaterfrontTestCase extends WebContainerTestCase {

    @Resource
    private JobManager jobManager;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        jobManager.getJobList().forEach(job -> job.becomeNonCron()); // suppress scheduling for unit test
    }

    protected MockJobRuntime getMockJobRuntime() {
        return MockJobRuntime.asDefault();
    }
}
