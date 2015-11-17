/*
 * Copyright 2014-2015 the original author or authors.
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
import org.docksidestage.esflute.maihama.exentity.Product;
import org.lastaflute.web.Execute;
import org.lastaflute.web.login.AllowAnyoneAccess;
import org.lastaflute.web.response.HtmlResponse;

/**
 * @author jflute
 * @author shinsuke
 */
@AllowAnyoneAccess
public class EsproductDetailAction extends WaterfrontBaseAction {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // -----------------------------------------------------
    //                                          DI Component
    //                                          ------------
    @Resource
    private ProductBhv productBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @Execute
    public HtmlResponse index(String productId) {
        validate(productId, messages -> {}, () -> {
            return asHtml(path_Esproduct_EsproductListJsp);
        });
        Product product = selectProduct(productId);
        return asHtml(path_Esproduct_EsproductDetailJsp).renderWith(data -> {
            data.register("product", mappingToBean(product));
        }).useForm(EsproductDeleteForm.class, op -> {
            op.setup(form -> {
                form.productId = productId;
            });
        });
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private Product selectProduct(String productId) {
        return productBhv.selectEntity(cb -> {
            cb.query().setId_Equal(productId);
        }).orElseThrow(() -> {
            return of404("Not found the product: " + productId); // mistake or user joke
        });
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private EsproductDetailBean mappingToBean(Product product) {
        EsproductDetailBean bean = new EsproductDetailBean();
        bean.productId = product.asDocMeta().id();
        bean.productName = product.getProductName();
        bean.regularPrice = product.getRegularPrice();
        bean.productHandleCode = product.getProductHandleCode();
        bean.categoryName = product.getProductCategory();
        return bean;
    }
}
