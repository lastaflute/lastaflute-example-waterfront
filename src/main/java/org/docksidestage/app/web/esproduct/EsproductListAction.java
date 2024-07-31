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

import java.util.List;

import jakarta.annotation.Resource;

import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.optional.OptionalThing;
import org.docksidestage.app.web.base.WaterfrontBaseAction;
import org.docksidestage.app.web.base.paging.PagingAssist;
import org.docksidestage.esflute.maihama.exbhv.ProductBhv;
import org.docksidestage.esflute.maihama.exentity.Product;
import org.lastaflute.web.Execute;
import org.lastaflute.web.login.AllowAnyoneAccess;
import org.lastaflute.web.response.HtmlResponse;
import org.opensearch.index.IndexNotFoundException;

/**
 * @author jflute
 * @author shinsuke
 */
@AllowAnyoneAccess
public class EsproductListAction extends WaterfrontBaseAction {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private ProductBhv productBhv;
    @Resource
    private PagingAssist pagingAssist;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @Execute
    public HtmlResponse index(OptionalThing<Integer> pageNumber, EsproductSearchForm form) {
        validate(form, messages -> {}, () -> {
            return asHtml(path_Esproduct_EsproductListJsp);
        });
        try {
            PagingResultBean<Product> page = selectProductPage(pageNumber.orElse(1), form);
            List<EsproductSearchRowBean> beans = page.mappingList(product -> {
                return mappingToBean(product);
            });
            return asHtml(path_Esproduct_EsproductListJsp).renderWith(data -> {
                data.register("totalCount", productBhv.selectCount(cb -> {}));
                data.register("beans", beans);
                pagingAssist.registerPagingNavi(data, page, form);
            });
        } catch (IndexNotFoundException e) {
            return asHtml(path_Esproduct_EsproductListJsp).renderWith(data -> {
                data.register("totalCount", 0);
            });
        }
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private PagingResultBean<Product> selectProductPage(int pageNumber, EsproductSearchForm form) {
        verifyOrClientError("The pageNumber should be positive number: " + pageNumber, pageNumber > 0);
        return productBhv.selectPage(cb -> {
            cb.ignoreNullOrEmptyQuery(); // TODO support
            //            cb.setupSelect_ProductStatus();
            //            cb.setupSelect_ProductCategory();
            //            cb.specify().derivedPurchase().count(purchaseCB -> {
            //                purchaseCB.specify().columnPurchaseId();
            //            } , Product.ALIAS_purchaseCount);
            if (form.productName != null) {
                cb.query().setProductName_MatchPhrase(form.productName);
            }
            //            final String purchaseMemberName = form.purchaseMemberName;
            //            if (isNotEmpty(purchaseMemberName)) {
            //                cb.query().existsPurchase(purchaseCB -> {
            //                    purchaseCB.query().queryMember().setMemberName_LikeSearch(purchaseMemberName, op -> op.likeContain());
            //                });
            //            }
            if (form.productStatus != null) {
                cb.query().setProductStatusCode_Equal(form.productStatus);
            }
            cb.query().addOrderBy_ProductName_raw_Asc(); // TODO multifield support
            cb.query().addOrderBy_Id_Asc();
            cb.paging(4, pageNumber);
        });
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private EsproductSearchRowBean mappingToBean(Product product) {
        EsproductSearchRowBean bean = new EsproductSearchRowBean();
        bean.productId = product.asDocMeta().id();
        bean.productName = product.getProductName();
        bean.regularPrice = product.getRegularPrice();
        bean.productStatus = product.getProductStatus();
        bean.productCategory = product.getProductCategory();
        bean.latestPurchaseDate = product.getLatestPurchaseDate() != null ? product.getLatestPurchaseDate().toLocalDate() : null;
        return bean;
    }
}
