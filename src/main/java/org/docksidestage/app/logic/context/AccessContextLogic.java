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
package org.docksidestage.app.logic.context;

import javax.annotation.Resource;

import org.dbflute.hook.AccessContext;
import org.dbflute.optional.OptionalThing;
import org.lastaflute.core.time.TimeManager;
import org.lastaflute.db.dbflute.accesscontext.AccessContextResource;

/**
 * @author jflute
 */
public class AccessContextLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private TimeManager timeManager;

    // ===================================================================================
    //                                                                  Resource Interface
    //                                                                  ==================
    @FunctionalInterface
    public interface UserTypeSupplier {
        OptionalThing<String> supply();
    }

    @FunctionalInterface
    public interface UserInfoSupplier {
        OptionalThing<Object> supply();
    }

    @FunctionalInterface
    public interface AppTypeSupplier {
        String supply();
    }

    // ===================================================================================
    //                                                                      Create Context
    //                                                                      ==============
    public AccessContext create(AccessContextResource resource, UserTypeSupplier userTypeSupplier, UserInfoSupplier userBeanSupplier,
            AppTypeSupplier appTypeSupplier) {
        final AccessContext context = new AccessContext();
        context.setAccessLocalDateTimeProvider(() -> timeManager.currentDateTime());
        context.setAccessUserProvider(() -> buildAccessUserTrace(resource, userTypeSupplier, userBeanSupplier, appTypeSupplier));
        return context;
    }

    private String buildAccessUserTrace(AccessContextResource resource, UserTypeSupplier userTypeSupplier,
            UserInfoSupplier userBeanSupplier, AppTypeSupplier appTypeSupplier) {
        // #change_it you can customize the user trace for common column
        // example default style: "M:7,WTF,ProductListAction" or "_:_,WTF,ProductListAction"
        final StringBuilder sb = new StringBuilder();
        sb.append(userTypeSupplier.supply().orElse("_")).append(":");
        sb.append(userBeanSupplier.supply().orElse("_"));
        sb.append(",").append(appTypeSupplier.supply()).append(",").append(resource.getModuleName());
        final String trace = sb.toString();
        final int columnSize = 200; // is same as e.g. REGISTER_USER
        return trace.length() > columnSize ? trace.substring(0, columnSize) : trace;
    }
}
