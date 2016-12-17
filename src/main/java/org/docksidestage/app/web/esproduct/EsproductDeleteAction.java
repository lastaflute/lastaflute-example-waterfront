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
package org.docksidestage.app.web.esproduct;

import javax.annotation.Resource;

import org.docksidestage.app.web.base.WaterfrontBaseAction;
import org.docksidestage.esflute.maihama.exbhv.ProductBhv;
import org.lastaflute.web.Execute;
import org.lastaflute.web.login.AllowAnyoneAccess;
import org.lastaflute.web.response.HtmlResponse;
import org.lastaflute.web.servlet.request.ResponseManager;

/**
 * @author shinsuke
 * @author jflute
 */
@AllowAnyoneAccess
public class EsproductDeleteAction extends WaterfrontBaseAction {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private ResponseManager responseManager;
    @Resource
    private ProductBhv productBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======

    @Execute
    public HtmlResponse index(EsproductDeleteForm form) {
        validate(form, messages -> {}, () -> {
            return asHtml(path_Esproduct_EsproductDetailJsp);
        });
        productBhv.selectByPK(form.productId).ifPresent(entity -> {
            productBhv.delete(entity, op -> {
                op.setRefreshPolicy("true");
            });
        }).orElse(() -> {
            throw responseManager.new404("Not found the product: " + form.productId);
        });
        return redirect(EsproductListAction.class);
    }
}
