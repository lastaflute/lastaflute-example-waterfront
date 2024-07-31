/*
 * Copyright 2015-2016 the original author or authors.
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
package org.docksidestage.app.job;

import jakarta.annotation.Resource;

import org.dbflute.optional.OptionalThing;
import org.dbflute.util.DfCollectionUtil;
import org.docksidestage.app.logic.context.AccessContextLogic;
import org.lastaflute.core.time.TimeManager;
import org.lastaflute.job.LaCron;
import org.lastaflute.job.LaJobRunner;
import org.lastaflute.job.LaJobScheduler;

/**
 * @author jflute
 */
public class AllJobScheduler implements LaJobScheduler {

    protected static final String APP_TYPE = "JOB";

    @Resource
    private TimeManager timeManager;
    @Resource
    private AccessContextLogic accessContextLogic;

    public enum Dual {
        WAIT, QUIT, ERROR
    }

    @Override
    public void schedule(LaCron cron) {
        cron.register("* * * * *", SeaJob.class, waitIfConcurrent(), op -> {});
        cron.register("*/1 * * * *", LandJob.class, quitIfConcurrent(), op -> op.params(() -> {
            return DfCollectionUtil.newHashMap("showbase", "oneman");
        }));
    }

    @Override
    public LaJobRunner createRunner() {
        return new LaJobRunner().useAccessContext(resource -> {
            return accessContextLogic.create(resource, () -> OptionalThing.empty(), () -> OptionalThing.empty(), () -> APP_TYPE);
        });
    }
}
