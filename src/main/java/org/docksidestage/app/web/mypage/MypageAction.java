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
package org.docksidestage.app.web.mypage;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.app.web.base.WaterfrontBaseAction;
import org.docksidestage.dbflute.exbhv.ProductBhv;
import org.docksidestage.dbflute.exentity.Product;
import org.lastaflute.web.Execute;
import org.lastaflute.web.response.HtmlResponse;
import org.lastaflute.web.response.JsonResponse;

/**
 * @author jflute
 */
public class MypageAction extends WaterfrontBaseAction {

    @Resource
    private ProductBhv productBhv;

    @Execute
    public HtmlResponse index() {
        ListResultBean<Product> productList = productBhv.selectList(cb -> {
            cb.query().addOrderBy_RegularPrice_Desc();
            cb.fetchFirst(3);
        });
        List<MypageProductBean> beans = productList.stream().map(member -> {
            return new MypageProductBean(member);
        }).collect(Collectors.toList());
        return asHtml(path_Mypage_MypageJsp).renderWith(data -> {
            data.register("beans", beans);
        });
    }

    @Execute
    public JsonResponse<List<MypageProductBean>> indexJson() {
        ListResultBean<Product> memberList = productBhv.selectList(cb -> {
            cb.query().addOrderBy_RegularPrice_Desc();
            cb.fetchFirst(3);
        });
        List<MypageProductBean> beans = memberList.stream().map(member -> {
            return new MypageProductBean(member);
        }).collect(Collectors.toList());
        return asJson(beans);
    }
}
