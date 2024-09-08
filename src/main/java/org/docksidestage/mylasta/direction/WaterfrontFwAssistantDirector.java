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
package org.docksidestage.mylasta.direction;

import jakarta.annotation.Resource;

import org.docksidestage.mylasta.direction.sponsor.WaterfrontActionAdjustmentProvider;
import org.docksidestage.mylasta.direction.sponsor.WaterfrontApiFailureHook;
import org.docksidestage.mylasta.direction.sponsor.WaterfrontCookieResourceProvider;
import org.docksidestage.mylasta.direction.sponsor.WaterfrontCurtainBeforeHook;
import org.docksidestage.mylasta.direction.sponsor.WaterfrontJsonResourceProvider;
import org.docksidestage.mylasta.direction.sponsor.WaterfrontListedClassificationProvider;
import org.docksidestage.mylasta.direction.sponsor.WaterfrontMailDeliveryDepartmentCreator;
import org.docksidestage.mylasta.direction.sponsor.WaterfrontSecurityResourceProvider;
import org.docksidestage.mylasta.direction.sponsor.WaterfrontSessionResourceProvider;
import org.docksidestage.mylasta.direction.sponsor.WaterfrontTimeResourceProvider;
import org.docksidestage.mylasta.direction.sponsor.WaterfrontUserLocaleProcessProvider;
import org.docksidestage.mylasta.direction.sponsor.WaterfrontUserTimeZoneProcessProvider;
import org.lastaflute.core.direction.CachedFwAssistantDirector;
import org.lastaflute.core.direction.FwAssistDirection;
import org.lastaflute.core.direction.FwCoreDirection;
import org.lastaflute.core.security.InvertibleCryptographer;
import org.lastaflute.core.security.OneWayCryptographer;
import org.lastaflute.db.dbflute.classification.ListedClassificationProvider;
import org.lastaflute.db.direction.FwDbDirection;
import org.lastaflute.web.direction.FwWebDirection;

/**
 * @author jflute
 */
public class WaterfrontFwAssistantDirector extends CachedFwAssistantDirector {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private WaterfrontConfig config;

    // ===================================================================================
    //                                                                              Assist
    //                                                                              ======
    @Override
    protected void prepareAssistDirection(FwAssistDirection direction) {
        direction.directConfig(nameList -> nameList.add("waterfront_config.properties"), "waterfront_env.properties");
    }

    // ===================================================================================
    //                                                                               Core
    //                                                                              ======
    @Override
    protected void prepareCoreDirection(FwCoreDirection direction) {
        // this configuration is on waterfront_env.properties because this is true only when development
        direction.directDevelopmentHere(config.isDevelopmentHere());

        // titles of the application for logging are from configurations
        direction.directLoggingTitle(config.getDomainTitle(), config.getEnvironmentTitle());

        // this configuration is on sea_env.properties because it has no influence to production
        // even if you set true manually and forget to set false back
        direction.directFrameworkDebug(config.isFrameworkDebug()); // basically false

        // you can add your own process when your application is booting
        direction.directCurtainBefore(createCurtainBeforeHook());

        direction.directSecurity(createSecurityResourceProvider());
        direction.directTime(createTimeResourceProvider());
        direction.directJson(createJsonResourceProvider());
        direction.directMail(createMailDeliveryDepartmentCreator().create());
    }

    protected WaterfrontCurtainBeforeHook createCurtainBeforeHook() {
        return new WaterfrontCurtainBeforeHook();
    }

    protected WaterfrontSecurityResourceProvider createSecurityResourceProvider() { // #change_it_first
        final InvertibleCryptographer inver = InvertibleCryptographer.createBlowfishCipher("waterfront:docks");
        final OneWayCryptographer oneWay = OneWayCryptographer.createSha256Cryptographer();
        return new WaterfrontSecurityResourceProvider(inver, oneWay);
    }

    protected WaterfrontTimeResourceProvider createTimeResourceProvider() {
        return new WaterfrontTimeResourceProvider(config);
    }

    protected WaterfrontJsonResourceProvider createJsonResourceProvider() {
        return new WaterfrontJsonResourceProvider();
    }

    protected WaterfrontMailDeliveryDepartmentCreator createMailDeliveryDepartmentCreator() {
        return new WaterfrontMailDeliveryDepartmentCreator(config);
    }

    // ===================================================================================
    //                                                                                 DB
    //                                                                                ====
    @Override
    protected void prepareDbDirection(FwDbDirection direction) {
        direction.directClassification(createListedClassificationProvider());
    }

    protected ListedClassificationProvider createListedClassificationProvider() {
        return new WaterfrontListedClassificationProvider();
    }

    // ===================================================================================
    //                                                                                Web
    //                                                                               =====
    @Override
    protected void prepareWebDirection(FwWebDirection direction) {
        direction.directRequest(createUserLocaleProcessProvider(), createUserTimeZoneProcessProvider());
        direction.directSession(createSessionResourceProvider());
        direction.directCookie(createCookieResourceProvider());
        direction.directAdjustment(createActionAdjustmentProvider());
        direction.directMessage(nameList -> nameList.add("waterfront_message"), "waterfront_label");
        direction.directApiCall(createApiFailureHook());
    }

    protected WaterfrontUserLocaleProcessProvider createUserLocaleProcessProvider() {
        return new WaterfrontUserLocaleProcessProvider();
    }

    protected WaterfrontUserTimeZoneProcessProvider createUserTimeZoneProcessProvider() {
        return new WaterfrontUserTimeZoneProcessProvider();
    }

    protected WaterfrontSessionResourceProvider createSessionResourceProvider() {
        return new WaterfrontSessionResourceProvider();
    }

    protected WaterfrontCookieResourceProvider createCookieResourceProvider() { // #change_it_first
        final InvertibleCryptographer cr = InvertibleCryptographer.createBlowfishCipher("docks:waterfront");
        return new WaterfrontCookieResourceProvider(config, cr);
    }

    protected WaterfrontActionAdjustmentProvider createActionAdjustmentProvider() {
        return new WaterfrontActionAdjustmentProvider();
    }

    protected WaterfrontApiFailureHook createApiFailureHook() {
        return new WaterfrontApiFailureHook();
    }
}
