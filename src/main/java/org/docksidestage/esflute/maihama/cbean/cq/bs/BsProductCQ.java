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
package org.docksidestage.esflute.maihama.cbean.cq.bs;

import java.time.*;
import java.util.Collection;

import org.docksidestage.esflute.maihama.allcommon.EsAbstractConditionQuery;
import org.docksidestage.esflute.maihama.cbean.cq.ProductCQ;
import org.dbflute.cbean.ckey.ConditionKey;
import org.dbflute.exception.IllegalConditionBeanOperationException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.NotQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;


/**
 * @author ESFlute (using FreeGen)
 */
public abstract class BsProductCQ extends EsAbstractConditionQuery {

    protected static final Class<?> suppressUnusedImportLocalDateTime = LocalDateTime.class;

    // ===================================================================================
    //                                                                       Name Override
    //                                                                       =============
    @Override
    public String asTableDbName() {
        return "product";
    }

    @Override
    public String xgetAliasName() {
        return "product";
    }

    // ===================================================================================
    //                                                                       Query Control
    //                                                                       =============
    public void filtered(FilteredCall<ProductCQ, ProductCQ> filteredLambda) {
        filtered(filteredLambda, null);
    }

    public void filtered(FilteredCall<ProductCQ, ProductCQ> filteredLambda,
            ConditionOptionCall<BoolQueryBuilder> opLambda) {
        bool((must, should, mustNot, filter)->{
            filteredLambda.callback(must, filter);
        }, opLambda);
    }

    public void not(OperatorCall<ProductCQ> notLambda) {
        not(notLambda, null);
    }

    public void not(OperatorCall<ProductCQ> notLambda, ConditionOptionCall<NotQueryBuilder> opLambda) {
        ProductCQ notQuery = new ProductCQ();
        notLambda.callback(notQuery);
        if (notQuery.hasQueries()) {
            if (notQuery.getQueryBuilderList().size() > 1) {
                final String msg = "not query must be one query.";
                throw new IllegalConditionBeanOperationException(msg);
            }
            NotQueryBuilder builder = QueryBuilders.notQuery(notQuery.getQueryBuilderList().get(0));
            if (opLambda != null) {
                opLambda.callback(builder);
            }
        }
    }

    public void bool(BoolCall<ProductCQ> boolLambda) {
        bool(boolLambda, null);
    }

    public void bool(BoolCall<ProductCQ> boolLambda, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        ProductCQ mustQuery = new ProductCQ();
        ProductCQ shouldQuery = new ProductCQ();
        ProductCQ mustNotQuery = new ProductCQ();
        ProductCQ filterQuery = new ProductCQ();
        boolLambda.callback(mustQuery, shouldQuery, mustNotQuery, filterQuery);
        if (mustQuery.hasQueries() || shouldQuery.hasQueries() || mustNotQuery.hasQueries() || filterQuery.hasQueries()) {
            BoolQueryBuilder builder = regBoolCQ(mustQuery.getQueryBuilderList(), shouldQuery.getQueryBuilderList(), mustNotQuery.getQueryBuilderList(), filterQuery.getQueryBuilderList());
            if (opLambda != null) {
                opLambda.callback(builder);
            }
        }
    }

    // ===================================================================================
    //                                                                           Query Set
    //                                                                           =========
    public void setId_Equal(String id) {
        setId_Term(id, null);
    }

    public void setId_Equal(String id, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setId_Term(id, opLambda);
    }

    public void setId_Term(String id) {
        setId_Term(id, null);
    }

    public void setId_Term(String id, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("_id", id);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setId_NotEqual(String id) {
        setId_NotTerm(id, null);
    }

    public void setId_NotEqual(String id, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setId_NotTerm(id, opLambda);
    }

    public void setId_NotTerm(String id) {
        setId_NotTerm(id, null);
    }

    public void setId_NotTerm(String id, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("_id", id));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setId_Terms(Collection<String> idList) {
        setId_Terms(idList, null);
    }

    public void setId_Terms(Collection<String> idList, ConditionOptionCall<IdsQueryBuilder> opLambda) {
        IdsQueryBuilder builder = regIdsQ(idList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setId_InScope(Collection<String> idList) {
        setId_Terms(idList, null);
    }

    public void setId_InScope(Collection<String> idList, ConditionOptionCall<IdsQueryBuilder> opLambda) {
        setId_Terms(idList, opLambda);
    }

    public BsProductCQ addOrderBy_Id_Asc() {
        regOBA("_id");
        return this;
    }

    public BsProductCQ addOrderBy_Id_Desc() {
        regOBD("_id");
        return this;
    }

    public void setCategoryCode_Equal(String categoryCode) {
        setCategoryCode_Term(categoryCode, null);
    }

    public void setCategoryCode_Equal(String categoryCode, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setCategoryCode_Term(categoryCode, opLambda);
    }

    public void setCategoryCode_Term(String categoryCode) {
        setCategoryCode_Term(categoryCode, null);
    }

    public void setCategoryCode_Term(String categoryCode, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("category_code", categoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCategoryCode_NotEqual(String categoryCode) {
        setCategoryCode_NotTerm(categoryCode, null);
    }

    public void setCategoryCode_NotEqual(String categoryCode, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setCategoryCode_NotTerm(categoryCode, opLambda);
    }

    public void setCategoryCode_NotTerm(String categoryCode) {
        setCategoryCode_NotTerm(categoryCode, null);
    }

    public void setCategoryCode_NotTerm(String categoryCode, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("category_code", categoryCode));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCategoryCode_Terms(Collection<String> categoryCodeList) {
        setCategoryCode_Terms(categoryCodeList, null);
    }

    public void setCategoryCode_Terms(Collection<String> categoryCodeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("category_code", categoryCodeList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCategoryCode_InScope(Collection<String> categoryCodeList) {
        setCategoryCode_Terms(categoryCodeList, null);
    }

    public void setCategoryCode_InScope(Collection<String> categoryCodeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setCategoryCode_Terms(categoryCodeList, opLambda);
    }

    public void setCategoryCode_Match(String categoryCode) {
        setCategoryCode_Match(categoryCode, null);
    }

    public void setCategoryCode_Match(String categoryCode, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("category_code", categoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCategoryCode_MatchPhrase(String categoryCode) {
        setCategoryCode_MatchPhrase(categoryCode, null);
    }

    public void setCategoryCode_MatchPhrase(String categoryCode, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhraseQ("category_code", categoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCategoryCode_MatchPhrasePrefix(String categoryCode) {
        setCategoryCode_MatchPhrasePrefix(categoryCode, null);
    }

    public void setCategoryCode_MatchPhrasePrefix(String categoryCode, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhrasePrefixQ("category_code", categoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCategoryCode_Fuzzy(String categoryCode) {
        setCategoryCode_Fuzzy(categoryCode, null);
    }

    public void setCategoryCode_Fuzzy(String categoryCode, ConditionOptionCall<FuzzyQueryBuilder> opLambda) {
        FuzzyQueryBuilder builder = regFuzzyQ("category_code", categoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCategoryCode_Prefix(String categoryCode) {
        setCategoryCode_Prefix(categoryCode, null);
    }

    public void setCategoryCode_Prefix(String categoryCode, ConditionOptionCall<PrefixQueryBuilder> opLambda) {
        PrefixQueryBuilder builder = regPrefixQ("category_code", categoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCategoryCode_GreaterThan(String categoryCode) {
        setCategoryCode_GreaterThan(categoryCode, null);
    }

    public void setCategoryCode_GreaterThan(String categoryCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("category_code", ConditionKey.CK_GREATER_THAN, categoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCategoryCode_LessThan(String categoryCode) {
        setCategoryCode_LessThan(categoryCode, null);
    }

    public void setCategoryCode_LessThan(String categoryCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("category_code", ConditionKey.CK_LESS_THAN, categoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCategoryCode_GreaterEqual(String categoryCode) {
        setCategoryCode_GreaterEqual(categoryCode, null);
    }

    public void setCategoryCode_GreaterEqual(String categoryCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("category_code", ConditionKey.CK_GREATER_EQUAL, categoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCategoryCode_LessEqual(String categoryCode) {
        setCategoryCode_LessEqual(categoryCode, null);
    }

    public void setCategoryCode_LessEqual(String categoryCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("category_code", ConditionKey.CK_LESS_EQUAL, categoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_CategoryCode_Asc() {
        regOBA("category_code");
        return this;
    }

    public BsProductCQ addOrderBy_CategoryCode_Desc() {
        regOBD("category_code");
        return this;
    }

    public void setDescription_Equal(String description) {
        setDescription_Term(description, null);
    }

    public void setDescription_Equal(String description, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setDescription_Term(description, opLambda);
    }

    public void setDescription_Term(String description) {
        setDescription_Term(description, null);
    }

    public void setDescription_Term(String description, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("description", description);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDescription_NotEqual(String description) {
        setDescription_NotTerm(description, null);
    }

    public void setDescription_NotEqual(String description, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setDescription_NotTerm(description, opLambda);
    }

    public void setDescription_NotTerm(String description) {
        setDescription_NotTerm(description, null);
    }

    public void setDescription_NotTerm(String description, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("description", description));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDescription_Terms(Collection<String> descriptionList) {
        setDescription_Terms(descriptionList, null);
    }

    public void setDescription_Terms(Collection<String> descriptionList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("description", descriptionList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDescription_InScope(Collection<String> descriptionList) {
        setDescription_Terms(descriptionList, null);
    }

    public void setDescription_InScope(Collection<String> descriptionList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setDescription_Terms(descriptionList, opLambda);
    }

    public void setDescription_Match(String description) {
        setDescription_Match(description, null);
    }

    public void setDescription_Match(String description, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("description", description);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDescription_MatchPhrase(String description) {
        setDescription_MatchPhrase(description, null);
    }

    public void setDescription_MatchPhrase(String description, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhraseQ("description", description);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDescription_MatchPhrasePrefix(String description) {
        setDescription_MatchPhrasePrefix(description, null);
    }

    public void setDescription_MatchPhrasePrefix(String description, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhrasePrefixQ("description", description);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDescription_Fuzzy(String description) {
        setDescription_Fuzzy(description, null);
    }

    public void setDescription_Fuzzy(String description, ConditionOptionCall<FuzzyQueryBuilder> opLambda) {
        FuzzyQueryBuilder builder = regFuzzyQ("description", description);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDescription_Prefix(String description) {
        setDescription_Prefix(description, null);
    }

    public void setDescription_Prefix(String description, ConditionOptionCall<PrefixQueryBuilder> opLambda) {
        PrefixQueryBuilder builder = regPrefixQ("description", description);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDescription_GreaterThan(String description) {
        setDescription_GreaterThan(description, null);
    }

    public void setDescription_GreaterThan(String description, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("description", ConditionKey.CK_GREATER_THAN, description);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDescription_LessThan(String description) {
        setDescription_LessThan(description, null);
    }

    public void setDescription_LessThan(String description, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("description", ConditionKey.CK_LESS_THAN, description);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDescription_GreaterEqual(String description) {
        setDescription_GreaterEqual(description, null);
    }

    public void setDescription_GreaterEqual(String description, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("description", ConditionKey.CK_GREATER_EQUAL, description);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDescription_LessEqual(String description) {
        setDescription_LessEqual(description, null);
    }

    public void setDescription_LessEqual(String description, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("description", ConditionKey.CK_LESS_EQUAL, description);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_Description_Asc() {
        regOBA("description");
        return this;
    }

    public BsProductCQ addOrderBy_Description_Desc() {
        regOBD("description");
        return this;
    }

    public void setHandleCode_Equal(String handleCode) {
        setHandleCode_Term(handleCode, null);
    }

    public void setHandleCode_Equal(String handleCode, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setHandleCode_Term(handleCode, opLambda);
    }

    public void setHandleCode_Term(String handleCode) {
        setHandleCode_Term(handleCode, null);
    }

    public void setHandleCode_Term(String handleCode, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("handle_code", handleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setHandleCode_NotEqual(String handleCode) {
        setHandleCode_NotTerm(handleCode, null);
    }

    public void setHandleCode_NotEqual(String handleCode, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setHandleCode_NotTerm(handleCode, opLambda);
    }

    public void setHandleCode_NotTerm(String handleCode) {
        setHandleCode_NotTerm(handleCode, null);
    }

    public void setHandleCode_NotTerm(String handleCode, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("handle_code", handleCode));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setHandleCode_Terms(Collection<String> handleCodeList) {
        setHandleCode_Terms(handleCodeList, null);
    }

    public void setHandleCode_Terms(Collection<String> handleCodeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("handle_code", handleCodeList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setHandleCode_InScope(Collection<String> handleCodeList) {
        setHandleCode_Terms(handleCodeList, null);
    }

    public void setHandleCode_InScope(Collection<String> handleCodeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setHandleCode_Terms(handleCodeList, opLambda);
    }

    public void setHandleCode_Match(String handleCode) {
        setHandleCode_Match(handleCode, null);
    }

    public void setHandleCode_Match(String handleCode, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("handle_code", handleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setHandleCode_MatchPhrase(String handleCode) {
        setHandleCode_MatchPhrase(handleCode, null);
    }

    public void setHandleCode_MatchPhrase(String handleCode, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhraseQ("handle_code", handleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setHandleCode_MatchPhrasePrefix(String handleCode) {
        setHandleCode_MatchPhrasePrefix(handleCode, null);
    }

    public void setHandleCode_MatchPhrasePrefix(String handleCode, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhrasePrefixQ("handle_code", handleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setHandleCode_Fuzzy(String handleCode) {
        setHandleCode_Fuzzy(handleCode, null);
    }

    public void setHandleCode_Fuzzy(String handleCode, ConditionOptionCall<FuzzyQueryBuilder> opLambda) {
        FuzzyQueryBuilder builder = regFuzzyQ("handle_code", handleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setHandleCode_Prefix(String handleCode) {
        setHandleCode_Prefix(handleCode, null);
    }

    public void setHandleCode_Prefix(String handleCode, ConditionOptionCall<PrefixQueryBuilder> opLambda) {
        PrefixQueryBuilder builder = regPrefixQ("handle_code", handleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setHandleCode_GreaterThan(String handleCode) {
        setHandleCode_GreaterThan(handleCode, null);
    }

    public void setHandleCode_GreaterThan(String handleCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("handle_code", ConditionKey.CK_GREATER_THAN, handleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setHandleCode_LessThan(String handleCode) {
        setHandleCode_LessThan(handleCode, null);
    }

    public void setHandleCode_LessThan(String handleCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("handle_code", ConditionKey.CK_LESS_THAN, handleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setHandleCode_GreaterEqual(String handleCode) {
        setHandleCode_GreaterEqual(handleCode, null);
    }

    public void setHandleCode_GreaterEqual(String handleCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("handle_code", ConditionKey.CK_GREATER_EQUAL, handleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setHandleCode_LessEqual(String handleCode) {
        setHandleCode_LessEqual(handleCode, null);
    }

    public void setHandleCode_LessEqual(String handleCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("handle_code", ConditionKey.CK_LESS_EQUAL, handleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_HandleCode_Asc() {
        regOBA("handle_code");
        return this;
    }

    public BsProductCQ addOrderBy_HandleCode_Desc() {
        regOBD("handle_code");
        return this;
    }

    public void setName_Equal(String name) {
        setName_Term(name, null);
    }

    public void setName_Equal(String name, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setName_Term(name, opLambda);
    }

    public void setName_Term(String name) {
        setName_Term(name, null);
    }

    public void setName_Term(String name, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("name", name);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setName_NotEqual(String name) {
        setName_NotTerm(name, null);
    }

    public void setName_NotEqual(String name, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setName_NotTerm(name, opLambda);
    }

    public void setName_NotTerm(String name) {
        setName_NotTerm(name, null);
    }

    public void setName_NotTerm(String name, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("name", name));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setName_Terms(Collection<String> nameList) {
        setName_Terms(nameList, null);
    }

    public void setName_Terms(Collection<String> nameList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("name", nameList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setName_InScope(Collection<String> nameList) {
        setName_Terms(nameList, null);
    }

    public void setName_InScope(Collection<String> nameList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setName_Terms(nameList, opLambda);
    }

    public void setName_Match(String name) {
        setName_Match(name, null);
    }

    public void setName_Match(String name, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("name", name);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setName_MatchPhrase(String name) {
        setName_MatchPhrase(name, null);
    }

    public void setName_MatchPhrase(String name, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhraseQ("name", name);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setName_MatchPhrasePrefix(String name) {
        setName_MatchPhrasePrefix(name, null);
    }

    public void setName_MatchPhrasePrefix(String name, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhrasePrefixQ("name", name);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setName_Fuzzy(String name) {
        setName_Fuzzy(name, null);
    }

    public void setName_Fuzzy(String name, ConditionOptionCall<FuzzyQueryBuilder> opLambda) {
        FuzzyQueryBuilder builder = regFuzzyQ("name", name);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setName_Prefix(String name) {
        setName_Prefix(name, null);
    }

    public void setName_Prefix(String name, ConditionOptionCall<PrefixQueryBuilder> opLambda) {
        PrefixQueryBuilder builder = regPrefixQ("name", name);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setName_GreaterThan(String name) {
        setName_GreaterThan(name, null);
    }

    public void setName_GreaterThan(String name, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("name", ConditionKey.CK_GREATER_THAN, name);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setName_LessThan(String name) {
        setName_LessThan(name, null);
    }

    public void setName_LessThan(String name, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("name", ConditionKey.CK_LESS_THAN, name);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setName_GreaterEqual(String name) {
        setName_GreaterEqual(name, null);
    }

    public void setName_GreaterEqual(String name, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("name", ConditionKey.CK_GREATER_EQUAL, name);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setName_LessEqual(String name) {
        setName_LessEqual(name, null);
    }

    public void setName_LessEqual(String name, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("name", ConditionKey.CK_LESS_EQUAL, name);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_Name_Asc() {
        regOBA("name");
        return this;
    }

    public BsProductCQ addOrderBy_Name_Desc() {
        regOBD("name");
        return this;
    }

    public void setRegisterDatetime_Equal(LocalDateTime registerDatetime) {
        setRegisterDatetime_Term(registerDatetime, null);
    }

    public void setRegisterDatetime_Equal(LocalDateTime registerDatetime, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setRegisterDatetime_Term(registerDatetime, opLambda);
    }

    public void setRegisterDatetime_Term(LocalDateTime registerDatetime) {
        setRegisterDatetime_Term(registerDatetime, null);
    }

    public void setRegisterDatetime_Term(LocalDateTime registerDatetime, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("register_datetime", registerDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_NotEqual(LocalDateTime registerDatetime) {
        setRegisterDatetime_NotTerm(registerDatetime, null);
    }

    public void setRegisterDatetime_NotEqual(LocalDateTime registerDatetime, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setRegisterDatetime_NotTerm(registerDatetime, opLambda);
    }

    public void setRegisterDatetime_NotTerm(LocalDateTime registerDatetime) {
        setRegisterDatetime_NotTerm(registerDatetime, null);
    }

    public void setRegisterDatetime_NotTerm(LocalDateTime registerDatetime, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("register_datetime", registerDatetime));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_Terms(Collection<LocalDateTime> registerDatetimeList) {
        setRegisterDatetime_Terms(registerDatetimeList, null);
    }

    public void setRegisterDatetime_Terms(Collection<LocalDateTime> registerDatetimeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("register_datetime", registerDatetimeList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_InScope(Collection<LocalDateTime> registerDatetimeList) {
        setRegisterDatetime_Terms(registerDatetimeList, null);
    }

    public void setRegisterDatetime_InScope(Collection<LocalDateTime> registerDatetimeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setRegisterDatetime_Terms(registerDatetimeList, opLambda);
    }

    public void setRegisterDatetime_Match(LocalDateTime registerDatetime) {
        setRegisterDatetime_Match(registerDatetime, null);
    }

    public void setRegisterDatetime_Match(LocalDateTime registerDatetime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("register_datetime", registerDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_MatchPhrase(LocalDateTime registerDatetime) {
        setRegisterDatetime_MatchPhrase(registerDatetime, null);
    }

    public void setRegisterDatetime_MatchPhrase(LocalDateTime registerDatetime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhraseQ("register_datetime", registerDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_MatchPhrasePrefix(LocalDateTime registerDatetime) {
        setRegisterDatetime_MatchPhrasePrefix(registerDatetime, null);
    }

    public void setRegisterDatetime_MatchPhrasePrefix(LocalDateTime registerDatetime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhrasePrefixQ("register_datetime", registerDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_Fuzzy(LocalDateTime registerDatetime) {
        setRegisterDatetime_Fuzzy(registerDatetime, null);
    }

    public void setRegisterDatetime_Fuzzy(LocalDateTime registerDatetime, ConditionOptionCall<FuzzyQueryBuilder> opLambda) {
        FuzzyQueryBuilder builder = regFuzzyQ("register_datetime", registerDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_GreaterThan(LocalDateTime registerDatetime) {
        setRegisterDatetime_GreaterThan(registerDatetime, null);
    }

    public void setRegisterDatetime_GreaterThan(LocalDateTime registerDatetime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("register_datetime", ConditionKey.CK_GREATER_THAN, registerDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_LessThan(LocalDateTime registerDatetime) {
        setRegisterDatetime_LessThan(registerDatetime, null);
    }

    public void setRegisterDatetime_LessThan(LocalDateTime registerDatetime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("register_datetime", ConditionKey.CK_LESS_THAN, registerDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_GreaterEqual(LocalDateTime registerDatetime) {
        setRegisterDatetime_GreaterEqual(registerDatetime, null);
    }

    public void setRegisterDatetime_GreaterEqual(LocalDateTime registerDatetime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("register_datetime", ConditionKey.CK_GREATER_EQUAL, registerDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_LessEqual(LocalDateTime registerDatetime) {
        setRegisterDatetime_LessEqual(registerDatetime, null);
    }

    public void setRegisterDatetime_LessEqual(LocalDateTime registerDatetime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("register_datetime", ConditionKey.CK_LESS_EQUAL, registerDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_RegisterDatetime_Asc() {
        regOBA("register_datetime");
        return this;
    }

    public BsProductCQ addOrderBy_RegisterDatetime_Desc() {
        regOBD("register_datetime");
        return this;
    }

    public void setRegisterUser_Equal(String registerUser) {
        setRegisterUser_Term(registerUser, null);
    }

    public void setRegisterUser_Equal(String registerUser, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setRegisterUser_Term(registerUser, opLambda);
    }

    public void setRegisterUser_Term(String registerUser) {
        setRegisterUser_Term(registerUser, null);
    }

    public void setRegisterUser_Term(String registerUser, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("register_user", registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_NotEqual(String registerUser) {
        setRegisterUser_NotTerm(registerUser, null);
    }

    public void setRegisterUser_NotEqual(String registerUser, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setRegisterUser_NotTerm(registerUser, opLambda);
    }

    public void setRegisterUser_NotTerm(String registerUser) {
        setRegisterUser_NotTerm(registerUser, null);
    }

    public void setRegisterUser_NotTerm(String registerUser, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("register_user", registerUser));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_Terms(Collection<String> registerUserList) {
        setRegisterUser_Terms(registerUserList, null);
    }

    public void setRegisterUser_Terms(Collection<String> registerUserList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("register_user", registerUserList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_InScope(Collection<String> registerUserList) {
        setRegisterUser_Terms(registerUserList, null);
    }

    public void setRegisterUser_InScope(Collection<String> registerUserList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setRegisterUser_Terms(registerUserList, opLambda);
    }

    public void setRegisterUser_Match(String registerUser) {
        setRegisterUser_Match(registerUser, null);
    }

    public void setRegisterUser_Match(String registerUser, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("register_user", registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_MatchPhrase(String registerUser) {
        setRegisterUser_MatchPhrase(registerUser, null);
    }

    public void setRegisterUser_MatchPhrase(String registerUser, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhraseQ("register_user", registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_MatchPhrasePrefix(String registerUser) {
        setRegisterUser_MatchPhrasePrefix(registerUser, null);
    }

    public void setRegisterUser_MatchPhrasePrefix(String registerUser, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhrasePrefixQ("register_user", registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_Fuzzy(String registerUser) {
        setRegisterUser_Fuzzy(registerUser, null);
    }

    public void setRegisterUser_Fuzzy(String registerUser, ConditionOptionCall<FuzzyQueryBuilder> opLambda) {
        FuzzyQueryBuilder builder = regFuzzyQ("register_user", registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_Prefix(String registerUser) {
        setRegisterUser_Prefix(registerUser, null);
    }

    public void setRegisterUser_Prefix(String registerUser, ConditionOptionCall<PrefixQueryBuilder> opLambda) {
        PrefixQueryBuilder builder = regPrefixQ("register_user", registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_GreaterThan(String registerUser) {
        setRegisterUser_GreaterThan(registerUser, null);
    }

    public void setRegisterUser_GreaterThan(String registerUser, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("register_user", ConditionKey.CK_GREATER_THAN, registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_LessThan(String registerUser) {
        setRegisterUser_LessThan(registerUser, null);
    }

    public void setRegisterUser_LessThan(String registerUser, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("register_user", ConditionKey.CK_LESS_THAN, registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_GreaterEqual(String registerUser) {
        setRegisterUser_GreaterEqual(registerUser, null);
    }

    public void setRegisterUser_GreaterEqual(String registerUser, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("register_user", ConditionKey.CK_GREATER_EQUAL, registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_LessEqual(String registerUser) {
        setRegisterUser_LessEqual(registerUser, null);
    }

    public void setRegisterUser_LessEqual(String registerUser, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("register_user", ConditionKey.CK_LESS_EQUAL, registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_RegisterUser_Asc() {
        regOBA("register_user");
        return this;
    }

    public BsProductCQ addOrderBy_RegisterUser_Desc() {
        regOBD("register_user");
        return this;
    }

    public void setRegularPrice_Equal(Integer regularPrice) {
        setRegularPrice_Term(regularPrice, null);
    }

    public void setRegularPrice_Equal(Integer regularPrice, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setRegularPrice_Term(regularPrice, opLambda);
    }

    public void setRegularPrice_Term(Integer regularPrice) {
        setRegularPrice_Term(regularPrice, null);
    }

    public void setRegularPrice_Term(Integer regularPrice, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("regular_price", regularPrice);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_NotEqual(Integer regularPrice) {
        setRegularPrice_NotTerm(regularPrice, null);
    }

    public void setRegularPrice_NotEqual(Integer regularPrice, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setRegularPrice_NotTerm(regularPrice, opLambda);
    }

    public void setRegularPrice_NotTerm(Integer regularPrice) {
        setRegularPrice_NotTerm(regularPrice, null);
    }

    public void setRegularPrice_NotTerm(Integer regularPrice, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("regular_price", regularPrice));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_Terms(Collection<Integer> regularPriceList) {
        setRegularPrice_Terms(regularPriceList, null);
    }

    public void setRegularPrice_Terms(Collection<Integer> regularPriceList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("regular_price", regularPriceList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_InScope(Collection<Integer> regularPriceList) {
        setRegularPrice_Terms(regularPriceList, null);
    }

    public void setRegularPrice_InScope(Collection<Integer> regularPriceList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setRegularPrice_Terms(regularPriceList, opLambda);
    }

    public void setRegularPrice_Match(Integer regularPrice) {
        setRegularPrice_Match(regularPrice, null);
    }

    public void setRegularPrice_Match(Integer regularPrice, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("regular_price", regularPrice);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_MatchPhrase(Integer regularPrice) {
        setRegularPrice_MatchPhrase(regularPrice, null);
    }

    public void setRegularPrice_MatchPhrase(Integer regularPrice, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhraseQ("regular_price", regularPrice);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_MatchPhrasePrefix(Integer regularPrice) {
        setRegularPrice_MatchPhrasePrefix(regularPrice, null);
    }

    public void setRegularPrice_MatchPhrasePrefix(Integer regularPrice, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhrasePrefixQ("regular_price", regularPrice);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_Fuzzy(Integer regularPrice) {
        setRegularPrice_Fuzzy(regularPrice, null);
    }

    public void setRegularPrice_Fuzzy(Integer regularPrice, ConditionOptionCall<FuzzyQueryBuilder> opLambda) {
        FuzzyQueryBuilder builder = regFuzzyQ("regular_price", regularPrice);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_GreaterThan(Integer regularPrice) {
        setRegularPrice_GreaterThan(regularPrice, null);
    }

    public void setRegularPrice_GreaterThan(Integer regularPrice, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("regular_price", ConditionKey.CK_GREATER_THAN, regularPrice);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_LessThan(Integer regularPrice) {
        setRegularPrice_LessThan(regularPrice, null);
    }

    public void setRegularPrice_LessThan(Integer regularPrice, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("regular_price", ConditionKey.CK_LESS_THAN, regularPrice);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_GreaterEqual(Integer regularPrice) {
        setRegularPrice_GreaterEqual(regularPrice, null);
    }

    public void setRegularPrice_GreaterEqual(Integer regularPrice, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("regular_price", ConditionKey.CK_GREATER_EQUAL, regularPrice);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_LessEqual(Integer regularPrice) {
        setRegularPrice_LessEqual(regularPrice, null);
    }

    public void setRegularPrice_LessEqual(Integer regularPrice, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("regular_price", ConditionKey.CK_LESS_EQUAL, regularPrice);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_RegularPrice_Asc() {
        regOBA("regular_price");
        return this;
    }

    public BsProductCQ addOrderBy_RegularPrice_Desc() {
        regOBD("regular_price");
        return this;
    }

    public void setStatus_Equal(String status) {
        setStatus_Term(status, null);
    }

    public void setStatus_Equal(String status, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setStatus_Term(status, opLambda);
    }

    public void setStatus_Term(String status) {
        setStatus_Term(status, null);
    }

    public void setStatus_Term(String status, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("status", status);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setStatus_NotEqual(String status) {
        setStatus_NotTerm(status, null);
    }

    public void setStatus_NotEqual(String status, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setStatus_NotTerm(status, opLambda);
    }

    public void setStatus_NotTerm(String status) {
        setStatus_NotTerm(status, null);
    }

    public void setStatus_NotTerm(String status, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("status", status));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setStatus_Terms(Collection<String> statusList) {
        setStatus_Terms(statusList, null);
    }

    public void setStatus_Terms(Collection<String> statusList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("status", statusList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setStatus_InScope(Collection<String> statusList) {
        setStatus_Terms(statusList, null);
    }

    public void setStatus_InScope(Collection<String> statusList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setStatus_Terms(statusList, opLambda);
    }

    public void setStatus_Match(String status) {
        setStatus_Match(status, null);
    }

    public void setStatus_Match(String status, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("status", status);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setStatus_MatchPhrase(String status) {
        setStatus_MatchPhrase(status, null);
    }

    public void setStatus_MatchPhrase(String status, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhraseQ("status", status);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setStatus_MatchPhrasePrefix(String status) {
        setStatus_MatchPhrasePrefix(status, null);
    }

    public void setStatus_MatchPhrasePrefix(String status, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhrasePrefixQ("status", status);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setStatus_Fuzzy(String status) {
        setStatus_Fuzzy(status, null);
    }

    public void setStatus_Fuzzy(String status, ConditionOptionCall<FuzzyQueryBuilder> opLambda) {
        FuzzyQueryBuilder builder = regFuzzyQ("status", status);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setStatus_Prefix(String status) {
        setStatus_Prefix(status, null);
    }

    public void setStatus_Prefix(String status, ConditionOptionCall<PrefixQueryBuilder> opLambda) {
        PrefixQueryBuilder builder = regPrefixQ("status", status);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setStatus_GreaterThan(String status) {
        setStatus_GreaterThan(status, null);
    }

    public void setStatus_GreaterThan(String status, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("status", ConditionKey.CK_GREATER_THAN, status);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setStatus_LessThan(String status) {
        setStatus_LessThan(status, null);
    }

    public void setStatus_LessThan(String status, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("status", ConditionKey.CK_LESS_THAN, status);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setStatus_GreaterEqual(String status) {
        setStatus_GreaterEqual(status, null);
    }

    public void setStatus_GreaterEqual(String status, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("status", ConditionKey.CK_GREATER_EQUAL, status);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setStatus_LessEqual(String status) {
        setStatus_LessEqual(status, null);
    }

    public void setStatus_LessEqual(String status, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("status", ConditionKey.CK_LESS_EQUAL, status);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_Status_Asc() {
        regOBA("status");
        return this;
    }

    public BsProductCQ addOrderBy_Status_Desc() {
        regOBD("status");
        return this;
    }

    public void setUpdateDatetime_Equal(LocalDateTime updateDatetime) {
        setUpdateDatetime_Term(updateDatetime, null);
    }

    public void setUpdateDatetime_Equal(LocalDateTime updateDatetime, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setUpdateDatetime_Term(updateDatetime, opLambda);
    }

    public void setUpdateDatetime_Term(LocalDateTime updateDatetime) {
        setUpdateDatetime_Term(updateDatetime, null);
    }

    public void setUpdateDatetime_Term(LocalDateTime updateDatetime, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("update_datetime", updateDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_NotEqual(LocalDateTime updateDatetime) {
        setUpdateDatetime_NotTerm(updateDatetime, null);
    }

    public void setUpdateDatetime_NotEqual(LocalDateTime updateDatetime, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setUpdateDatetime_NotTerm(updateDatetime, opLambda);
    }

    public void setUpdateDatetime_NotTerm(LocalDateTime updateDatetime) {
        setUpdateDatetime_NotTerm(updateDatetime, null);
    }

    public void setUpdateDatetime_NotTerm(LocalDateTime updateDatetime, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("update_datetime", updateDatetime));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_Terms(Collection<LocalDateTime> updateDatetimeList) {
        setUpdateDatetime_Terms(updateDatetimeList, null);
    }

    public void setUpdateDatetime_Terms(Collection<LocalDateTime> updateDatetimeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("update_datetime", updateDatetimeList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_InScope(Collection<LocalDateTime> updateDatetimeList) {
        setUpdateDatetime_Terms(updateDatetimeList, null);
    }

    public void setUpdateDatetime_InScope(Collection<LocalDateTime> updateDatetimeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setUpdateDatetime_Terms(updateDatetimeList, opLambda);
    }

    public void setUpdateDatetime_Match(LocalDateTime updateDatetime) {
        setUpdateDatetime_Match(updateDatetime, null);
    }

    public void setUpdateDatetime_Match(LocalDateTime updateDatetime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("update_datetime", updateDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_MatchPhrase(LocalDateTime updateDatetime) {
        setUpdateDatetime_MatchPhrase(updateDatetime, null);
    }

    public void setUpdateDatetime_MatchPhrase(LocalDateTime updateDatetime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhraseQ("update_datetime", updateDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_MatchPhrasePrefix(LocalDateTime updateDatetime) {
        setUpdateDatetime_MatchPhrasePrefix(updateDatetime, null);
    }

    public void setUpdateDatetime_MatchPhrasePrefix(LocalDateTime updateDatetime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhrasePrefixQ("update_datetime", updateDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_Fuzzy(LocalDateTime updateDatetime) {
        setUpdateDatetime_Fuzzy(updateDatetime, null);
    }

    public void setUpdateDatetime_Fuzzy(LocalDateTime updateDatetime, ConditionOptionCall<FuzzyQueryBuilder> opLambda) {
        FuzzyQueryBuilder builder = regFuzzyQ("update_datetime", updateDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_GreaterThan(LocalDateTime updateDatetime) {
        setUpdateDatetime_GreaterThan(updateDatetime, null);
    }

    public void setUpdateDatetime_GreaterThan(LocalDateTime updateDatetime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("update_datetime", ConditionKey.CK_GREATER_THAN, updateDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_LessThan(LocalDateTime updateDatetime) {
        setUpdateDatetime_LessThan(updateDatetime, null);
    }

    public void setUpdateDatetime_LessThan(LocalDateTime updateDatetime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("update_datetime", ConditionKey.CK_LESS_THAN, updateDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_GreaterEqual(LocalDateTime updateDatetime) {
        setUpdateDatetime_GreaterEqual(updateDatetime, null);
    }

    public void setUpdateDatetime_GreaterEqual(LocalDateTime updateDatetime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("update_datetime", ConditionKey.CK_GREATER_EQUAL, updateDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_LessEqual(LocalDateTime updateDatetime) {
        setUpdateDatetime_LessEqual(updateDatetime, null);
    }

    public void setUpdateDatetime_LessEqual(LocalDateTime updateDatetime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("update_datetime", ConditionKey.CK_LESS_EQUAL, updateDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_UpdateDatetime_Asc() {
        regOBA("update_datetime");
        return this;
    }

    public BsProductCQ addOrderBy_UpdateDatetime_Desc() {
        regOBD("update_datetime");
        return this;
    }

    public void setUpdateUser_Equal(String updateUser) {
        setUpdateUser_Term(updateUser, null);
    }

    public void setUpdateUser_Equal(String updateUser, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setUpdateUser_Term(updateUser, opLambda);
    }

    public void setUpdateUser_Term(String updateUser) {
        setUpdateUser_Term(updateUser, null);
    }

    public void setUpdateUser_Term(String updateUser, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("update_user", updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_NotEqual(String updateUser) {
        setUpdateUser_NotTerm(updateUser, null);
    }

    public void setUpdateUser_NotEqual(String updateUser, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setUpdateUser_NotTerm(updateUser, opLambda);
    }

    public void setUpdateUser_NotTerm(String updateUser) {
        setUpdateUser_NotTerm(updateUser, null);
    }

    public void setUpdateUser_NotTerm(String updateUser, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("update_user", updateUser));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_Terms(Collection<String> updateUserList) {
        setUpdateUser_Terms(updateUserList, null);
    }

    public void setUpdateUser_Terms(Collection<String> updateUserList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("update_user", updateUserList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_InScope(Collection<String> updateUserList) {
        setUpdateUser_Terms(updateUserList, null);
    }

    public void setUpdateUser_InScope(Collection<String> updateUserList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setUpdateUser_Terms(updateUserList, opLambda);
    }

    public void setUpdateUser_Match(String updateUser) {
        setUpdateUser_Match(updateUser, null);
    }

    public void setUpdateUser_Match(String updateUser, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("update_user", updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_MatchPhrase(String updateUser) {
        setUpdateUser_MatchPhrase(updateUser, null);
    }

    public void setUpdateUser_MatchPhrase(String updateUser, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhraseQ("update_user", updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_MatchPhrasePrefix(String updateUser) {
        setUpdateUser_MatchPhrasePrefix(updateUser, null);
    }

    public void setUpdateUser_MatchPhrasePrefix(String updateUser, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhrasePrefixQ("update_user", updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_Fuzzy(String updateUser) {
        setUpdateUser_Fuzzy(updateUser, null);
    }

    public void setUpdateUser_Fuzzy(String updateUser, ConditionOptionCall<FuzzyQueryBuilder> opLambda) {
        FuzzyQueryBuilder builder = regFuzzyQ("update_user", updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_Prefix(String updateUser) {
        setUpdateUser_Prefix(updateUser, null);
    }

    public void setUpdateUser_Prefix(String updateUser, ConditionOptionCall<PrefixQueryBuilder> opLambda) {
        PrefixQueryBuilder builder = regPrefixQ("update_user", updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_GreaterThan(String updateUser) {
        setUpdateUser_GreaterThan(updateUser, null);
    }

    public void setUpdateUser_GreaterThan(String updateUser, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("update_user", ConditionKey.CK_GREATER_THAN, updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_LessThan(String updateUser) {
        setUpdateUser_LessThan(updateUser, null);
    }

    public void setUpdateUser_LessThan(String updateUser, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("update_user", ConditionKey.CK_LESS_THAN, updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_GreaterEqual(String updateUser) {
        setUpdateUser_GreaterEqual(updateUser, null);
    }

    public void setUpdateUser_GreaterEqual(String updateUser, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("update_user", ConditionKey.CK_GREATER_EQUAL, updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_LessEqual(String updateUser) {
        setUpdateUser_LessEqual(updateUser, null);
    }

    public void setUpdateUser_LessEqual(String updateUser, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("update_user", ConditionKey.CK_LESS_EQUAL, updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_UpdateUser_Asc() {
        regOBA("update_user");
        return this;
    }

    public BsProductCQ addOrderBy_UpdateUser_Desc() {
        regOBD("update_user");
        return this;
    }

}
