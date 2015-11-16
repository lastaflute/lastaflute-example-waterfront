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
package org.docksidestage.mylasta.action;

import org.lastaflute.web.response.next.HtmlNext;

/**
 * The path definition of HTML.
 * @author FreeGen
 */
public interface WaterfrontHtmlPath {

    /** The path of the HTML: /error/show_errors.jsp */
    HtmlNext path_Error_ShowErrorsJsp = new HtmlNext("/error/show_errors.jsp");

    /** The path of the HTML: /esproduct/esproduct_detail.jsp */
    HtmlNext path_Esproduct_EsproductDetailJsp = new HtmlNext("/esproduct/esproduct_detail.jsp");

    /** The path of the HTML: /esproduct/esproduct_list.jsp */
    HtmlNext path_Esproduct_EsproductListJsp = new HtmlNext("/esproduct/esproduct_list.jsp");

    /** The path of the HTML: /mypage/mypage.jsp */
    HtmlNext path_Mypage_MypageJsp = new HtmlNext("/mypage/mypage.jsp");

    /** The path of the HTML: /product/product_detail.jsp */
    HtmlNext path_Product_ProductDetailJsp = new HtmlNext("/product/product_detail.jsp");

    /** The path of the HTML: /product/product_list.jsp */
    HtmlNext path_Product_ProductListJsp = new HtmlNext("/product/product_list.jsp");

    /** The path of the HTML: /signin/signin.jsp */
    HtmlNext path_Signin_SigninJsp = new HtmlNext("/signin/signin.jsp");

    /** The path of the HTML: /signup/signup.jsp */
    HtmlNext path_Signup_SignupJsp = new HtmlNext("/signup/signup.jsp");

    /** The path of the HTML: /withdrawal/withdrawal.jsp */
    HtmlNext path_Withdrawal_WithdrawalJsp = new HtmlNext("/withdrawal/withdrawal.jsp");

    /** The path of the HTML: /withdrawal/withdrawal_confirm.jsp */
    HtmlNext path_Withdrawal_WithdrawalConfirmJsp = new HtmlNext("/withdrawal/withdrawal_confirm.jsp");
}
