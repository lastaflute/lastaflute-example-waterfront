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
import org.docksidestage.esflute.maihama.cbean.cq.MenberCQ;
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
public abstract class BsMenberCQ extends EsAbstractConditionQuery {

    protected static final Class<?> suppressUnusedImportLocalDateTime = LocalDateTime.class;

    // ===================================================================================
    //                                                                       Name Override
    //                                                                       =============
    @Override
    public String asTableDbName() {
        return "menber";
    }

    @Override
    public String xgetAliasName() {
        return "menber";
    }

    // ===================================================================================
    //                                                                       Query Control
    //                                                                       =============
    public void filtered(FilteredCall<MenberCQ, MenberCQ> filteredLambda) {
        filtered(filteredLambda, null);
    }

    public void filtered(FilteredCall<MenberCQ, MenberCQ> filteredLambda,
            ConditionOptionCall<BoolQueryBuilder> opLambda) {
        bool((must, should, mustNot, filter)->{
            filteredLambda.callback(must, filter);
        }, opLambda);
    }

    public void not(OperatorCall<MenberCQ> notLambda) {
        not(notLambda, null);
    }

    public void not(OperatorCall<MenberCQ> notLambda, ConditionOptionCall<NotQueryBuilder> opLambda) {
        MenberCQ notQuery = new MenberCQ();
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

    public void bool(BoolCall<MenberCQ> boolLambda) {
        bool(boolLambda, null);
    }

    public void bool(BoolCall<MenberCQ> boolLambda, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        MenberCQ mustQuery = new MenberCQ();
        MenberCQ shouldQuery = new MenberCQ();
        MenberCQ mustNotQuery = new MenberCQ();
        MenberCQ filterQuery = new MenberCQ();
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

    public BsMenberCQ addOrderBy_Id_Asc() {
        regOBA("_id");
        return this;
    }

    public BsMenberCQ addOrderBy_Id_Desc() {
        regOBD("_id");
        return this;
    }

    public void setAccount_Equal(String account) {
        setAccount_Term(account, null);
    }

    public void setAccount_Equal(String account, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setAccount_Term(account, opLambda);
    }

    public void setAccount_Term(String account) {
        setAccount_Term(account, null);
    }

    public void setAccount_Term(String account, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("account", account);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setAccount_NotEqual(String account) {
        setAccount_NotTerm(account, null);
    }

    public void setAccount_NotEqual(String account, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setAccount_NotTerm(account, opLambda);
    }

    public void setAccount_NotTerm(String account) {
        setAccount_NotTerm(account, null);
    }

    public void setAccount_NotTerm(String account, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("account", account));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setAccount_Terms(Collection<String> accountList) {
        setAccount_Terms(accountList, null);
    }

    public void setAccount_Terms(Collection<String> accountList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("account", accountList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setAccount_InScope(Collection<String> accountList) {
        setAccount_Terms(accountList, null);
    }

    public void setAccount_InScope(Collection<String> accountList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setAccount_Terms(accountList, opLambda);
    }

    public void setAccount_Match(String account) {
        setAccount_Match(account, null);
    }

    public void setAccount_Match(String account, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("account", account);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setAccount_MatchPhrase(String account) {
        setAccount_MatchPhrase(account, null);
    }

    public void setAccount_MatchPhrase(String account, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhraseQ("account", account);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setAccount_MatchPhrasePrefix(String account) {
        setAccount_MatchPhrasePrefix(account, null);
    }

    public void setAccount_MatchPhrasePrefix(String account, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhrasePrefixQ("account", account);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setAccount_Fuzzy(String account) {
        setAccount_Fuzzy(account, null);
    }

    public void setAccount_Fuzzy(String account, ConditionOptionCall<FuzzyQueryBuilder> opLambda) {
        FuzzyQueryBuilder builder = regFuzzyQ("account", account);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setAccount_Prefix(String account) {
        setAccount_Prefix(account, null);
    }

    public void setAccount_Prefix(String account, ConditionOptionCall<PrefixQueryBuilder> opLambda) {
        PrefixQueryBuilder builder = regPrefixQ("account", account);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setAccount_GreaterThan(String account) {
        setAccount_GreaterThan(account, null);
    }

    public void setAccount_GreaterThan(String account, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("account", ConditionKey.CK_GREATER_THAN, account);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setAccount_LessThan(String account) {
        setAccount_LessThan(account, null);
    }

    public void setAccount_LessThan(String account, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("account", ConditionKey.CK_LESS_THAN, account);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setAccount_GreaterEqual(String account) {
        setAccount_GreaterEqual(account, null);
    }

    public void setAccount_GreaterEqual(String account, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("account", ConditionKey.CK_GREATER_EQUAL, account);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setAccount_LessEqual(String account) {
        setAccount_LessEqual(account, null);
    }

    public void setAccount_LessEqual(String account, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("account", ConditionKey.CK_LESS_EQUAL, account);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsMenberCQ addOrderBy_Account_Asc() {
        regOBA("account");
        return this;
    }

    public BsMenberCQ addOrderBy_Account_Desc() {
        regOBD("account");
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

    public BsMenberCQ addOrderBy_Name_Asc() {
        regOBA("name");
        return this;
    }

    public BsMenberCQ addOrderBy_Name_Desc() {
        regOBD("name");
        return this;
    }

}
