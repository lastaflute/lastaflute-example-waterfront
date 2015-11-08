package org.docksidestage.esflute.maihama.cbean.cq.bs;

import java.time.*;
import java.util.Collection;

import org.docksidestage.esflute.maihama.allcommon.EsAbstractConditionQuery;
import org.docksidestage.esflute.maihama.cbean.cq.ParkSeaCQ;
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
public abstract class BsParkSeaCQ extends EsAbstractConditionQuery {

    protected static final Class<?> suppressUnusedImportLocalDateTime = LocalDateTime.class;

    // ===================================================================================
    //                                                                       Name Override
    //                                                                       =============
    @Override
    public String asTableDbName() {
        return "park_sea";
    }

    @Override
    public String xgetAliasName() {
        return "park_sea";
    }

    // ===================================================================================
    //                                                                       Query Control
    //                                                                       =============
    public void filtered(FilteredCall<ParkSeaCQ, ParkSeaCQ> filteredLambda) {
        filtered(filteredLambda, null);
    }

    public void filtered(FilteredCall<ParkSeaCQ, ParkSeaCQ> filteredLambda,
            ConditionOptionCall<BoolQueryBuilder> opLambda) {
        bool((must, should, mustNot, filter)->{
            filteredLambda.callback(must, filter);
        }, opLambda);
    }

    public void not(OperatorCall<ParkSeaCQ> notLambda) {
        not(notLambda, null);
    }

    public void not(OperatorCall<ParkSeaCQ> notLambda, ConditionOptionCall<NotQueryBuilder> opLambda) {
        ParkSeaCQ notQuery = new ParkSeaCQ();
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

    public void bool(BoolCall<ParkSeaCQ> boolLambda) {
        bool(boolLambda, null);
    }

    public void bool(BoolCall<ParkSeaCQ> boolLambda, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        ParkSeaCQ mustQuery = new ParkSeaCQ();
        ParkSeaCQ shouldQuery = new ParkSeaCQ();
        ParkSeaCQ mustNotQuery = new ParkSeaCQ();
        ParkSeaCQ filterQuery = new ParkSeaCQ();
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

    public BsParkSeaCQ addOrderBy_Id_Asc() {
        regOBA("_id");
        return this;
    }

    public BsParkSeaCQ addOrderBy_Id_Desc() {
        regOBD("_id");
        return this;
    }

    public void setCode_Equal(String code) {
        setCode_Term(code, null);
    }

    public void setCode_Equal(String code, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setCode_Term(code, opLambda);
    }

    public void setCode_Term(String code) {
        setCode_Term(code, null);
    }

    public void setCode_Term(String code, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("code", code);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCode_NotEqual(String code) {
        setCode_NotTerm(code, null);
    }

    public void setCode_NotEqual(String code, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setCode_NotTerm(code, opLambda);
    }

    public void setCode_NotTerm(String code) {
        setCode_NotTerm(code, null);
    }

    public void setCode_NotTerm(String code, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("code", code));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCode_Terms(Collection<String> codeList) {
        setCode_Terms(codeList, null);
    }

    public void setCode_Terms(Collection<String> codeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("code", codeList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCode_InScope(Collection<String> codeList) {
        setCode_Terms(codeList, null);
    }

    public void setCode_InScope(Collection<String> codeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setCode_Terms(codeList, opLambda);
    }

    public void setCode_Match(String code) {
        setCode_Match(code, null);
    }

    public void setCode_Match(String code, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("code", code);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCode_MatchPhrase(String code) {
        setCode_MatchPhrase(code, null);
    }

    public void setCode_MatchPhrase(String code, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhraseQ("code", code);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCode_MatchPhrasePrefix(String code) {
        setCode_MatchPhrasePrefix(code, null);
    }

    public void setCode_MatchPhrasePrefix(String code, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhrasePrefixQ("code", code);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCode_Fuzzy(String code) {
        setCode_Fuzzy(code, null);
    }

    public void setCode_Fuzzy(String code, ConditionOptionCall<FuzzyQueryBuilder> opLambda) {
        FuzzyQueryBuilder builder = regFuzzyQ("code", code);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCode_Prefix(String code) {
        setCode_Prefix(code, null);
    }

    public void setCode_Prefix(String code, ConditionOptionCall<PrefixQueryBuilder> opLambda) {
        PrefixQueryBuilder builder = regPrefixQ("code", code);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCode_GreaterThan(String code) {
        setCode_GreaterThan(code, null);
    }

    public void setCode_GreaterThan(String code, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("code", ConditionKey.CK_GREATER_THAN, code);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCode_LessThan(String code) {
        setCode_LessThan(code, null);
    }

    public void setCode_LessThan(String code, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("code", ConditionKey.CK_LESS_THAN, code);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCode_GreaterEqual(String code) {
        setCode_GreaterEqual(code, null);
    }

    public void setCode_GreaterEqual(String code, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("code", ConditionKey.CK_GREATER_EQUAL, code);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCode_LessEqual(String code) {
        setCode_LessEqual(code, null);
    }

    public void setCode_LessEqual(String code, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("code", ConditionKey.CK_LESS_EQUAL, code);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsParkSeaCQ addOrderBy_Code_Asc() {
        regOBA("code");
        return this;
    }

    public BsParkSeaCQ addOrderBy_Code_Desc() {
        regOBD("code");
        return this;
    }

    public void setCreatedTime_Equal(Long createdTime) {
        setCreatedTime_Term(createdTime, null);
    }

    public void setCreatedTime_Equal(Long createdTime, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setCreatedTime_Term(createdTime, opLambda);
    }

    public void setCreatedTime_Term(Long createdTime) {
        setCreatedTime_Term(createdTime, null);
    }

    public void setCreatedTime_Term(Long createdTime, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("createdTime", createdTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCreatedTime_NotEqual(Long createdTime) {
        setCreatedTime_NotTerm(createdTime, null);
    }

    public void setCreatedTime_NotEqual(Long createdTime, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setCreatedTime_NotTerm(createdTime, opLambda);
    }

    public void setCreatedTime_NotTerm(Long createdTime) {
        setCreatedTime_NotTerm(createdTime, null);
    }

    public void setCreatedTime_NotTerm(Long createdTime, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("createdTime", createdTime));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCreatedTime_Terms(Collection<Long> createdTimeList) {
        setCreatedTime_Terms(createdTimeList, null);
    }

    public void setCreatedTime_Terms(Collection<Long> createdTimeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("createdTime", createdTimeList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCreatedTime_InScope(Collection<Long> createdTimeList) {
        setCreatedTime_Terms(createdTimeList, null);
    }

    public void setCreatedTime_InScope(Collection<Long> createdTimeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setCreatedTime_Terms(createdTimeList, opLambda);
    }

    public void setCreatedTime_Match(Long createdTime) {
        setCreatedTime_Match(createdTime, null);
    }

    public void setCreatedTime_Match(Long createdTime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("createdTime", createdTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCreatedTime_MatchPhrase(Long createdTime) {
        setCreatedTime_MatchPhrase(createdTime, null);
    }

    public void setCreatedTime_MatchPhrase(Long createdTime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhraseQ("createdTime", createdTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCreatedTime_MatchPhrasePrefix(Long createdTime) {
        setCreatedTime_MatchPhrasePrefix(createdTime, null);
    }

    public void setCreatedTime_MatchPhrasePrefix(Long createdTime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhrasePrefixQ("createdTime", createdTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCreatedTime_Fuzzy(Long createdTime) {
        setCreatedTime_Fuzzy(createdTime, null);
    }

    public void setCreatedTime_Fuzzy(Long createdTime, ConditionOptionCall<FuzzyQueryBuilder> opLambda) {
        FuzzyQueryBuilder builder = regFuzzyQ("createdTime", createdTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCreatedTime_GreaterThan(Long createdTime) {
        setCreatedTime_GreaterThan(createdTime, null);
    }

    public void setCreatedTime_GreaterThan(Long createdTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("createdTime", ConditionKey.CK_GREATER_THAN, createdTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCreatedTime_LessThan(Long createdTime) {
        setCreatedTime_LessThan(createdTime, null);
    }

    public void setCreatedTime_LessThan(Long createdTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("createdTime", ConditionKey.CK_LESS_THAN, createdTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCreatedTime_GreaterEqual(Long createdTime) {
        setCreatedTime_GreaterEqual(createdTime, null);
    }

    public void setCreatedTime_GreaterEqual(Long createdTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("createdTime", ConditionKey.CK_GREATER_EQUAL, createdTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setCreatedTime_LessEqual(Long createdTime) {
        setCreatedTime_LessEqual(createdTime, null);
    }

    public void setCreatedTime_LessEqual(Long createdTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("createdTime", ConditionKey.CK_LESS_EQUAL, createdTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsParkSeaCQ addOrderBy_CreatedTime_Asc() {
        regOBA("createdTime");
        return this;
    }

    public BsParkSeaCQ addOrderBy_CreatedTime_Desc() {
        regOBD("createdTime");
        return this;
    }

    public void setUpdatedTime_Equal(Long updatedTime) {
        setUpdatedTime_Term(updatedTime, null);
    }

    public void setUpdatedTime_Equal(Long updatedTime, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setUpdatedTime_Term(updatedTime, opLambda);
    }

    public void setUpdatedTime_Term(Long updatedTime) {
        setUpdatedTime_Term(updatedTime, null);
    }

    public void setUpdatedTime_Term(Long updatedTime, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("updatedTime", updatedTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdatedTime_NotEqual(Long updatedTime) {
        setUpdatedTime_NotTerm(updatedTime, null);
    }

    public void setUpdatedTime_NotEqual(Long updatedTime, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setUpdatedTime_NotTerm(updatedTime, opLambda);
    }

    public void setUpdatedTime_NotTerm(Long updatedTime) {
        setUpdatedTime_NotTerm(updatedTime, null);
    }

    public void setUpdatedTime_NotTerm(Long updatedTime, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("updatedTime", updatedTime));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdatedTime_Terms(Collection<Long> updatedTimeList) {
        setUpdatedTime_Terms(updatedTimeList, null);
    }

    public void setUpdatedTime_Terms(Collection<Long> updatedTimeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("updatedTime", updatedTimeList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdatedTime_InScope(Collection<Long> updatedTimeList) {
        setUpdatedTime_Terms(updatedTimeList, null);
    }

    public void setUpdatedTime_InScope(Collection<Long> updatedTimeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setUpdatedTime_Terms(updatedTimeList, opLambda);
    }

    public void setUpdatedTime_Match(Long updatedTime) {
        setUpdatedTime_Match(updatedTime, null);
    }

    public void setUpdatedTime_Match(Long updatedTime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("updatedTime", updatedTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdatedTime_MatchPhrase(Long updatedTime) {
        setUpdatedTime_MatchPhrase(updatedTime, null);
    }

    public void setUpdatedTime_MatchPhrase(Long updatedTime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhraseQ("updatedTime", updatedTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdatedTime_MatchPhrasePrefix(Long updatedTime) {
        setUpdatedTime_MatchPhrasePrefix(updatedTime, null);
    }

    public void setUpdatedTime_MatchPhrasePrefix(Long updatedTime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhrasePrefixQ("updatedTime", updatedTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdatedTime_Fuzzy(Long updatedTime) {
        setUpdatedTime_Fuzzy(updatedTime, null);
    }

    public void setUpdatedTime_Fuzzy(Long updatedTime, ConditionOptionCall<FuzzyQueryBuilder> opLambda) {
        FuzzyQueryBuilder builder = regFuzzyQ("updatedTime", updatedTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdatedTime_GreaterThan(Long updatedTime) {
        setUpdatedTime_GreaterThan(updatedTime, null);
    }

    public void setUpdatedTime_GreaterThan(Long updatedTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("updatedTime", ConditionKey.CK_GREATER_THAN, updatedTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdatedTime_LessThan(Long updatedTime) {
        setUpdatedTime_LessThan(updatedTime, null);
    }

    public void setUpdatedTime_LessThan(Long updatedTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("updatedTime", ConditionKey.CK_LESS_THAN, updatedTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdatedTime_GreaterEqual(Long updatedTime) {
        setUpdatedTime_GreaterEqual(updatedTime, null);
    }

    public void setUpdatedTime_GreaterEqual(Long updatedTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("updatedTime", ConditionKey.CK_GREATER_EQUAL, updatedTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdatedTime_LessEqual(Long updatedTime) {
        setUpdatedTime_LessEqual(updatedTime, null);
    }

    public void setUpdatedTime_LessEqual(Long updatedTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("updatedTime", ConditionKey.CK_LESS_EQUAL, updatedTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsParkSeaCQ addOrderBy_UpdatedTime_Asc() {
        regOBA("updatedTime");
        return this;
    }

    public BsParkSeaCQ addOrderBy_UpdatedTime_Desc() {
        regOBD("updatedTime");
        return this;
    }

    public void setDefaultDate_Equal(LocalDateTime defaultDate) {
        setDefaultDate_Term(defaultDate, null);
    }

    public void setDefaultDate_Equal(LocalDateTime defaultDate, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setDefaultDate_Term(defaultDate, opLambda);
    }

    public void setDefaultDate_Term(LocalDateTime defaultDate) {
        setDefaultDate_Term(defaultDate, null);
    }

    public void setDefaultDate_Term(LocalDateTime defaultDate, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("defaultDate", defaultDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDefaultDate_NotEqual(LocalDateTime defaultDate) {
        setDefaultDate_NotTerm(defaultDate, null);
    }

    public void setDefaultDate_NotEqual(LocalDateTime defaultDate, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setDefaultDate_NotTerm(defaultDate, opLambda);
    }

    public void setDefaultDate_NotTerm(LocalDateTime defaultDate) {
        setDefaultDate_NotTerm(defaultDate, null);
    }

    public void setDefaultDate_NotTerm(LocalDateTime defaultDate, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("defaultDate", defaultDate));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDefaultDate_Terms(Collection<LocalDateTime> defaultDateList) {
        setDefaultDate_Terms(defaultDateList, null);
    }

    public void setDefaultDate_Terms(Collection<LocalDateTime> defaultDateList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("defaultDate", defaultDateList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDefaultDate_InScope(Collection<LocalDateTime> defaultDateList) {
        setDefaultDate_Terms(defaultDateList, null);
    }

    public void setDefaultDate_InScope(Collection<LocalDateTime> defaultDateList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setDefaultDate_Terms(defaultDateList, opLambda);
    }

    public void setDefaultDate_Match(LocalDateTime defaultDate) {
        setDefaultDate_Match(defaultDate, null);
    }

    public void setDefaultDate_Match(LocalDateTime defaultDate, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("defaultDate", defaultDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDefaultDate_MatchPhrase(LocalDateTime defaultDate) {
        setDefaultDate_MatchPhrase(defaultDate, null);
    }

    public void setDefaultDate_MatchPhrase(LocalDateTime defaultDate, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhraseQ("defaultDate", defaultDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDefaultDate_MatchPhrasePrefix(LocalDateTime defaultDate) {
        setDefaultDate_MatchPhrasePrefix(defaultDate, null);
    }

    public void setDefaultDate_MatchPhrasePrefix(LocalDateTime defaultDate, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhrasePrefixQ("defaultDate", defaultDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDefaultDate_Fuzzy(LocalDateTime defaultDate) {
        setDefaultDate_Fuzzy(defaultDate, null);
    }

    public void setDefaultDate_Fuzzy(LocalDateTime defaultDate, ConditionOptionCall<FuzzyQueryBuilder> opLambda) {
        FuzzyQueryBuilder builder = regFuzzyQ("defaultDate", defaultDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDefaultDate_GreaterThan(LocalDateTime defaultDate) {
        setDefaultDate_GreaterThan(defaultDate, null);
    }

    public void setDefaultDate_GreaterThan(LocalDateTime defaultDate, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("defaultDate", ConditionKey.CK_GREATER_THAN, defaultDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDefaultDate_LessThan(LocalDateTime defaultDate) {
        setDefaultDate_LessThan(defaultDate, null);
    }

    public void setDefaultDate_LessThan(LocalDateTime defaultDate, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("defaultDate", ConditionKey.CK_LESS_THAN, defaultDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDefaultDate_GreaterEqual(LocalDateTime defaultDate) {
        setDefaultDate_GreaterEqual(defaultDate, null);
    }

    public void setDefaultDate_GreaterEqual(LocalDateTime defaultDate, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("defaultDate", ConditionKey.CK_GREATER_EQUAL, defaultDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setDefaultDate_LessEqual(LocalDateTime defaultDate) {
        setDefaultDate_LessEqual(defaultDate, null);
    }

    public void setDefaultDate_LessEqual(LocalDateTime defaultDate, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("defaultDate", ConditionKey.CK_LESS_EQUAL, defaultDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsParkSeaCQ addOrderBy_DefaultDate_Asc() {
        regOBA("defaultDate");
        return this;
    }

    public BsParkSeaCQ addOrderBy_DefaultDate_Desc() {
        regOBD("defaultDate");
        return this;
    }

    public void setFormatDate_Equal(LocalDate formatDate) {
        setFormatDate_Term(formatDate, null);
    }

    public void setFormatDate_Equal(LocalDate formatDate, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setFormatDate_Term(formatDate, opLambda);
    }

    public void setFormatDate_Term(LocalDate formatDate) {
        setFormatDate_Term(formatDate, null);
    }

    public void setFormatDate_Term(LocalDate formatDate, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("formatDate", formatDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDate_NotEqual(LocalDate formatDate) {
        setFormatDate_NotTerm(formatDate, null);
    }

    public void setFormatDate_NotEqual(LocalDate formatDate, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setFormatDate_NotTerm(formatDate, opLambda);
    }

    public void setFormatDate_NotTerm(LocalDate formatDate) {
        setFormatDate_NotTerm(formatDate, null);
    }

    public void setFormatDate_NotTerm(LocalDate formatDate, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("formatDate", formatDate));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDate_Terms(Collection<LocalDate> formatDateList) {
        setFormatDate_Terms(formatDateList, null);
    }

    public void setFormatDate_Terms(Collection<LocalDate> formatDateList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("formatDate", formatDateList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDate_InScope(Collection<LocalDate> formatDateList) {
        setFormatDate_Terms(formatDateList, null);
    }

    public void setFormatDate_InScope(Collection<LocalDate> formatDateList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setFormatDate_Terms(formatDateList, opLambda);
    }

    public void setFormatDate_Match(LocalDate formatDate) {
        setFormatDate_Match(formatDate, null);
    }

    public void setFormatDate_Match(LocalDate formatDate, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("formatDate", formatDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDate_MatchPhrase(LocalDate formatDate) {
        setFormatDate_MatchPhrase(formatDate, null);
    }

    public void setFormatDate_MatchPhrase(LocalDate formatDate, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhraseQ("formatDate", formatDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDate_MatchPhrasePrefix(LocalDate formatDate) {
        setFormatDate_MatchPhrasePrefix(formatDate, null);
    }

    public void setFormatDate_MatchPhrasePrefix(LocalDate formatDate, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhrasePrefixQ("formatDate", formatDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDate_Fuzzy(LocalDate formatDate) {
        setFormatDate_Fuzzy(formatDate, null);
    }

    public void setFormatDate_Fuzzy(LocalDate formatDate, ConditionOptionCall<FuzzyQueryBuilder> opLambda) {
        FuzzyQueryBuilder builder = regFuzzyQ("formatDate", formatDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDate_GreaterThan(LocalDate formatDate) {
        setFormatDate_GreaterThan(formatDate, null);
    }

    public void setFormatDate_GreaterThan(LocalDate formatDate, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("formatDate", ConditionKey.CK_GREATER_THAN, formatDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDate_LessThan(LocalDate formatDate) {
        setFormatDate_LessThan(formatDate, null);
    }

    public void setFormatDate_LessThan(LocalDate formatDate, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("formatDate", ConditionKey.CK_LESS_THAN, formatDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDate_GreaterEqual(LocalDate formatDate) {
        setFormatDate_GreaterEqual(formatDate, null);
    }

    public void setFormatDate_GreaterEqual(LocalDate formatDate, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("formatDate", ConditionKey.CK_GREATER_EQUAL, formatDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDate_LessEqual(LocalDate formatDate) {
        setFormatDate_LessEqual(formatDate, null);
    }

    public void setFormatDate_LessEqual(LocalDate formatDate, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("formatDate", ConditionKey.CK_LESS_EQUAL, formatDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsParkSeaCQ addOrderBy_FormatDate_Asc() {
        regOBA("formatDate");
        return this;
    }

    public BsParkSeaCQ addOrderBy_FormatDate_Desc() {
        regOBD("formatDate");
        return this;
    }

    public void setFormatDateTime_Equal(LocalDateTime formatDateTime) {
        setFormatDateTime_Term(formatDateTime, null);
    }

    public void setFormatDateTime_Equal(LocalDateTime formatDateTime, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setFormatDateTime_Term(formatDateTime, opLambda);
    }

    public void setFormatDateTime_Term(LocalDateTime formatDateTime) {
        setFormatDateTime_Term(formatDateTime, null);
    }

    public void setFormatDateTime_Term(LocalDateTime formatDateTime, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("formatDateTime", formatDateTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateTime_NotEqual(LocalDateTime formatDateTime) {
        setFormatDateTime_NotTerm(formatDateTime, null);
    }

    public void setFormatDateTime_NotEqual(LocalDateTime formatDateTime, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setFormatDateTime_NotTerm(formatDateTime, opLambda);
    }

    public void setFormatDateTime_NotTerm(LocalDateTime formatDateTime) {
        setFormatDateTime_NotTerm(formatDateTime, null);
    }

    public void setFormatDateTime_NotTerm(LocalDateTime formatDateTime, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("formatDateTime", formatDateTime));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateTime_Terms(Collection<LocalDateTime> formatDateTimeList) {
        setFormatDateTime_Terms(formatDateTimeList, null);
    }

    public void setFormatDateTime_Terms(Collection<LocalDateTime> formatDateTimeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("formatDateTime", formatDateTimeList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateTime_InScope(Collection<LocalDateTime> formatDateTimeList) {
        setFormatDateTime_Terms(formatDateTimeList, null);
    }

    public void setFormatDateTime_InScope(Collection<LocalDateTime> formatDateTimeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setFormatDateTime_Terms(formatDateTimeList, opLambda);
    }

    public void setFormatDateTime_Match(LocalDateTime formatDateTime) {
        setFormatDateTime_Match(formatDateTime, null);
    }

    public void setFormatDateTime_Match(LocalDateTime formatDateTime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("formatDateTime", formatDateTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateTime_MatchPhrase(LocalDateTime formatDateTime) {
        setFormatDateTime_MatchPhrase(formatDateTime, null);
    }

    public void setFormatDateTime_MatchPhrase(LocalDateTime formatDateTime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhraseQ("formatDateTime", formatDateTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateTime_MatchPhrasePrefix(LocalDateTime formatDateTime) {
        setFormatDateTime_MatchPhrasePrefix(formatDateTime, null);
    }

    public void setFormatDateTime_MatchPhrasePrefix(LocalDateTime formatDateTime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhrasePrefixQ("formatDateTime", formatDateTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateTime_Fuzzy(LocalDateTime formatDateTime) {
        setFormatDateTime_Fuzzy(formatDateTime, null);
    }

    public void setFormatDateTime_Fuzzy(LocalDateTime formatDateTime, ConditionOptionCall<FuzzyQueryBuilder> opLambda) {
        FuzzyQueryBuilder builder = regFuzzyQ("formatDateTime", formatDateTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateTime_GreaterThan(LocalDateTime formatDateTime) {
        setFormatDateTime_GreaterThan(formatDateTime, null);
    }

    public void setFormatDateTime_GreaterThan(LocalDateTime formatDateTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("formatDateTime", ConditionKey.CK_GREATER_THAN, formatDateTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateTime_LessThan(LocalDateTime formatDateTime) {
        setFormatDateTime_LessThan(formatDateTime, null);
    }

    public void setFormatDateTime_LessThan(LocalDateTime formatDateTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("formatDateTime", ConditionKey.CK_LESS_THAN, formatDateTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateTime_GreaterEqual(LocalDateTime formatDateTime) {
        setFormatDateTime_GreaterEqual(formatDateTime, null);
    }

    public void setFormatDateTime_GreaterEqual(LocalDateTime formatDateTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("formatDateTime", ConditionKey.CK_GREATER_EQUAL, formatDateTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateTime_LessEqual(LocalDateTime formatDateTime) {
        setFormatDateTime_LessEqual(formatDateTime, null);
    }

    public void setFormatDateTime_LessEqual(LocalDateTime formatDateTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("formatDateTime", ConditionKey.CK_LESS_EQUAL, formatDateTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsParkSeaCQ addOrderBy_FormatDateTime_Asc() {
        regOBA("formatDateTime");
        return this;
    }

    public BsParkSeaCQ addOrderBy_FormatDateTime_Desc() {
        regOBD("formatDateTime");
        return this;
    }

    public void setFormatTime_Equal(LocalTime formatTime) {
        setFormatTime_Term(formatTime, null);
    }

    public void setFormatTime_Equal(LocalTime formatTime, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setFormatTime_Term(formatTime, opLambda);
    }

    public void setFormatTime_Term(LocalTime formatTime) {
        setFormatTime_Term(formatTime, null);
    }

    public void setFormatTime_Term(LocalTime formatTime, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("formatTime", formatTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatTime_NotEqual(LocalTime formatTime) {
        setFormatTime_NotTerm(formatTime, null);
    }

    public void setFormatTime_NotEqual(LocalTime formatTime, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setFormatTime_NotTerm(formatTime, opLambda);
    }

    public void setFormatTime_NotTerm(LocalTime formatTime) {
        setFormatTime_NotTerm(formatTime, null);
    }

    public void setFormatTime_NotTerm(LocalTime formatTime, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("formatTime", formatTime));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatTime_Terms(Collection<LocalTime> formatTimeList) {
        setFormatTime_Terms(formatTimeList, null);
    }

    public void setFormatTime_Terms(Collection<LocalTime> formatTimeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("formatTime", formatTimeList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatTime_InScope(Collection<LocalTime> formatTimeList) {
        setFormatTime_Terms(formatTimeList, null);
    }

    public void setFormatTime_InScope(Collection<LocalTime> formatTimeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setFormatTime_Terms(formatTimeList, opLambda);
    }

    public void setFormatTime_Match(LocalTime formatTime) {
        setFormatTime_Match(formatTime, null);
    }

    public void setFormatTime_Match(LocalTime formatTime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("formatTime", formatTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatTime_MatchPhrase(LocalTime formatTime) {
        setFormatTime_MatchPhrase(formatTime, null);
    }

    public void setFormatTime_MatchPhrase(LocalTime formatTime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhraseQ("formatTime", formatTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatTime_MatchPhrasePrefix(LocalTime formatTime) {
        setFormatTime_MatchPhrasePrefix(formatTime, null);
    }

    public void setFormatTime_MatchPhrasePrefix(LocalTime formatTime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhrasePrefixQ("formatTime", formatTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatTime_Fuzzy(LocalTime formatTime) {
        setFormatTime_Fuzzy(formatTime, null);
    }

    public void setFormatTime_Fuzzy(LocalTime formatTime, ConditionOptionCall<FuzzyQueryBuilder> opLambda) {
        FuzzyQueryBuilder builder = regFuzzyQ("formatTime", formatTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatTime_GreaterThan(LocalTime formatTime) {
        setFormatTime_GreaterThan(formatTime, null);
    }

    public void setFormatTime_GreaterThan(LocalTime formatTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("formatTime", ConditionKey.CK_GREATER_THAN, formatTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatTime_LessThan(LocalTime formatTime) {
        setFormatTime_LessThan(formatTime, null);
    }

    public void setFormatTime_LessThan(LocalTime formatTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("formatTime", ConditionKey.CK_LESS_THAN, formatTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatTime_GreaterEqual(LocalTime formatTime) {
        setFormatTime_GreaterEqual(formatTime, null);
    }

    public void setFormatTime_GreaterEqual(LocalTime formatTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("formatTime", ConditionKey.CK_GREATER_EQUAL, formatTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatTime_LessEqual(LocalTime formatTime) {
        setFormatTime_LessEqual(formatTime, null);
    }

    public void setFormatTime_LessEqual(LocalTime formatTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("formatTime", ConditionKey.CK_LESS_EQUAL, formatTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsParkSeaCQ addOrderBy_FormatTime_Asc() {
        regOBA("formatTime");
        return this;
    }

    public BsParkSeaCQ addOrderBy_FormatTime_Desc() {
        regOBD("formatTime");
        return this;
    }

    public void setFormatDateOptionalTime_Equal(LocalDateTime formatDateOptionalTime) {
        setFormatDateOptionalTime_Term(formatDateOptionalTime, null);
    }

    public void setFormatDateOptionalTime_Equal(LocalDateTime formatDateOptionalTime, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setFormatDateOptionalTime_Term(formatDateOptionalTime, opLambda);
    }

    public void setFormatDateOptionalTime_Term(LocalDateTime formatDateOptionalTime) {
        setFormatDateOptionalTime_Term(formatDateOptionalTime, null);
    }

    public void setFormatDateOptionalTime_Term(LocalDateTime formatDateOptionalTime, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("formatDateOptionalTime", formatDateOptionalTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateOptionalTime_NotEqual(LocalDateTime formatDateOptionalTime) {
        setFormatDateOptionalTime_NotTerm(formatDateOptionalTime, null);
    }

    public void setFormatDateOptionalTime_NotEqual(LocalDateTime formatDateOptionalTime, ConditionOptionCall<NotQueryBuilder> opLambda) {
        setFormatDateOptionalTime_NotTerm(formatDateOptionalTime, opLambda);
    }

    public void setFormatDateOptionalTime_NotTerm(LocalDateTime formatDateOptionalTime) {
        setFormatDateOptionalTime_NotTerm(formatDateOptionalTime, null);
    }

    public void setFormatDateOptionalTime_NotTerm(LocalDateTime formatDateOptionalTime, ConditionOptionCall<NotQueryBuilder> opLambda) {
        NotQueryBuilder builder = QueryBuilders.notQuery(regTermQ("formatDateOptionalTime", formatDateOptionalTime));
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateOptionalTime_Terms(Collection<LocalDateTime> formatDateOptionalTimeList) {
        setFormatDateOptionalTime_Terms(formatDateOptionalTimeList, null);
    }

    public void setFormatDateOptionalTime_Terms(Collection<LocalDateTime> formatDateOptionalTimeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("formatDateOptionalTime", formatDateOptionalTimeList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateOptionalTime_InScope(Collection<LocalDateTime> formatDateOptionalTimeList) {
        setFormatDateOptionalTime_Terms(formatDateOptionalTimeList, null);
    }

    public void setFormatDateOptionalTime_InScope(Collection<LocalDateTime> formatDateOptionalTimeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setFormatDateOptionalTime_Terms(formatDateOptionalTimeList, opLambda);
    }

    public void setFormatDateOptionalTime_Match(LocalDateTime formatDateOptionalTime) {
        setFormatDateOptionalTime_Match(formatDateOptionalTime, null);
    }

    public void setFormatDateOptionalTime_Match(LocalDateTime formatDateOptionalTime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("formatDateOptionalTime", formatDateOptionalTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateOptionalTime_MatchPhrase(LocalDateTime formatDateOptionalTime) {
        setFormatDateOptionalTime_MatchPhrase(formatDateOptionalTime, null);
    }

    public void setFormatDateOptionalTime_MatchPhrase(LocalDateTime formatDateOptionalTime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhraseQ("formatDateOptionalTime", formatDateOptionalTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateOptionalTime_MatchPhrasePrefix(LocalDateTime formatDateOptionalTime) {
        setFormatDateOptionalTime_MatchPhrasePrefix(formatDateOptionalTime, null);
    }

    public void setFormatDateOptionalTime_MatchPhrasePrefix(LocalDateTime formatDateOptionalTime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchPhrasePrefixQ("formatDateOptionalTime", formatDateOptionalTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateOptionalTime_Fuzzy(LocalDateTime formatDateOptionalTime) {
        setFormatDateOptionalTime_Fuzzy(formatDateOptionalTime, null);
    }

    public void setFormatDateOptionalTime_Fuzzy(LocalDateTime formatDateOptionalTime, ConditionOptionCall<FuzzyQueryBuilder> opLambda) {
        FuzzyQueryBuilder builder = regFuzzyQ("formatDateOptionalTime", formatDateOptionalTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateOptionalTime_GreaterThan(LocalDateTime formatDateOptionalTime) {
        setFormatDateOptionalTime_GreaterThan(formatDateOptionalTime, null);
    }

    public void setFormatDateOptionalTime_GreaterThan(LocalDateTime formatDateOptionalTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("formatDateOptionalTime", ConditionKey.CK_GREATER_THAN, formatDateOptionalTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateOptionalTime_LessThan(LocalDateTime formatDateOptionalTime) {
        setFormatDateOptionalTime_LessThan(formatDateOptionalTime, null);
    }

    public void setFormatDateOptionalTime_LessThan(LocalDateTime formatDateOptionalTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("formatDateOptionalTime", ConditionKey.CK_LESS_THAN, formatDateOptionalTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateOptionalTime_GreaterEqual(LocalDateTime formatDateOptionalTime) {
        setFormatDateOptionalTime_GreaterEqual(formatDateOptionalTime, null);
    }

    public void setFormatDateOptionalTime_GreaterEqual(LocalDateTime formatDateOptionalTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("formatDateOptionalTime", ConditionKey.CK_GREATER_EQUAL, formatDateOptionalTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setFormatDateOptionalTime_LessEqual(LocalDateTime formatDateOptionalTime) {
        setFormatDateOptionalTime_LessEqual(formatDateOptionalTime, null);
    }

    public void setFormatDateOptionalTime_LessEqual(LocalDateTime formatDateOptionalTime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        RangeQueryBuilder builder = regRangeQ("formatDateOptionalTime", ConditionKey.CK_LESS_EQUAL, formatDateOptionalTime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsParkSeaCQ addOrderBy_FormatDateOptionalTime_Asc() {
        regOBA("formatDateOptionalTime");
        return this;
    }

    public BsParkSeaCQ addOrderBy_FormatDateOptionalTime_Desc() {
        regOBD("formatDateOptionalTime");
        return this;
    }

}
