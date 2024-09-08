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
package org.docksidestage.app.web.base;

import javax.annotation.Resource;

import org.dbflute.optional.OptionalObject;
import org.dbflute.optional.OptionalThing;
import org.docksidestage.app.logic.context.AccessContextLogic;
import org.docksidestage.app.web.base.login.WaterfrontLoginAssist;
import org.docksidestage.mylasta.action.WaterfrontHtmlPath;
import org.docksidestage.mylasta.action.WaterfrontMessages;
import org.docksidestage.mylasta.action.WaterfrontUserBean;
import org.docksidestage.mylasta.direction.WaterfrontConfig;
import org.lastaflute.db.dbflute.accesscontext.AccessContextArranger;
import org.lastaflute.web.TypicalAction;
import org.lastaflute.web.login.LoginManager;
import org.lastaflute.web.response.ActionResponse;
import org.lastaflute.web.ruts.process.ActionRuntime;
import org.lastaflute.web.servlet.request.RequestManager;
import org.lastaflute.web.validation.ActionValidator;
import org.lastaflute.web.validation.LaValidatable;

/**
 * @author jflute
 */
public abstract class WaterfrontBaseAction extends TypicalAction // has several interfaces for direct use
        implements LaValidatable<WaterfrontMessages>, WaterfrontHtmlPath {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The application type for WaTerFront, e.g. used by access context. */
    protected static final String APP_TYPE = "WTF"; // #change_it_first

    /** The user type for Member, e.g. used by access context. */
    protected static final String USER_TYPE = "M"; // #change_it_first

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private RequestManager requestManager;
    @Resource
    private WaterfrontConfig config;
    @Resource
    private AccessContextLogic accessContextLogic;
    @Resource
    private WaterfrontLoginAssist loginAssist;

    // ===================================================================================
    //                                                                               Hook
    //                                                                              ======
    // to suppress unexpected override by sub-class
    // you should remove the 'final' if you need to override this
    @Override
    public final ActionResponse godHandPrologue(ActionRuntime runtime) {
        return super.godHandPrologue(runtime);
    }

    @Override
    public final ActionResponse godHandMonologue(ActionRuntime runtime) {
        return super.godHandMonologue(runtime);
    }

    @Override
    public final void godHandEpilogue(ActionRuntime runtime) {
        super.godHandEpilogue(runtime);
    }

    // #app_customize you can customize the action hook
    @Override
    public ActionResponse hookBefore(ActionRuntime runtime) { // application may override
        return super.hookBefore(runtime);
    }

    @Override
    public void hookFinally(ActionRuntime runtime) { // application may override
        if (runtime.isForwardToHtml()) {
            runtime.registerData("headerBean", getUserBean().map(userBean -> {
                return new WaterfrontHeaderBean(userBean);
            }).orElse(WaterfrontHeaderBean.empty()));
        }
        super.hookFinally(runtime);
    }

    // ===================================================================================
    //                                                                      Access Context
    //                                                                      ==============
    @Override
    protected AccessContextArranger newAccessContextArranger() { // for framework
        return resource -> {
            return accessContextLogic.create(resource, () -> myUserType(), () -> getUserBean().map(userBean -> {
                return userBean.getUserId(); // as user expression
            }), () -> myAppType());
        };
    }

    // ===================================================================================
    //                                                                           User Info
    //                                                                           =========
    // -----------------------------------------------------
    //                                      Application Info
    //                                      ----------------
    @Override
    protected String myAppType() { // for framework
        return APP_TYPE;
    }

    // -----------------------------------------------------
    //                                            Login Info
    //                                            ----------
    // #app_customize return empty if login is unused
    @Override
    protected OptionalThing<WaterfrontUserBean> getUserBean() { // application may call, overriding for co-variant
        return loginAssist.getSavedUserBean();
    }

    @Override
    protected OptionalThing<String> myUserType() { // for framework
        return OptionalObject.of(USER_TYPE);
    }

    @Override
    protected OptionalThing<LoginManager> myLoginManager() { // for framework
        return OptionalThing.of(loginAssist);
    }

    // ===================================================================================
    //                                                                          Validation
    //                                                                          ==========
    @SuppressWarnings("unchecked")
    @Override
    public ActionValidator<WaterfrontMessages> createValidator() { // for co-variant
        return super.createValidator();
    }

    @Override
    public WaterfrontMessages createMessages() { // application may call
        return new WaterfrontMessages(); // overriding to change return type to concrete-class
    }

    // ===================================================================================
    //                                                                            Document
    //                                                                            ========
    // #app_customize you should override javadoc when you add new methods for sub class at super class.
    ///**
    // * {@inheritDoc} <br>
    // * Application Native Methods:
    // * <pre>
    // * <span style="font-size: 130%; color: #553000">[xxx]</span>
    // * o xxx() <span style="color: #3F7E5E">// xxx</span>
    // * </pre>
    // */
    //@Override
    //public void document1_CallableSuperMethod() {
    //    super.document1_CallableSuperMethod();
    //}
}
