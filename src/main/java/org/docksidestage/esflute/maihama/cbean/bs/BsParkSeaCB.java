package org.docksidestage.esflute.maihama.cbean.bs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.docksidestage.esflute.maihama.allcommon.EsAbstractConditionBean;
import org.docksidestage.esflute.maihama.bsentity.dbmeta.ParkSeaDbm;
import org.docksidestage.esflute.maihama.cbean.ParkSeaCB;
import org.docksidestage.esflute.maihama.cbean.cq.ParkSeaCQ;
import org.docksidestage.esflute.maihama.cbean.cq.bs.BsParkSeaCQ;
import org.dbflute.cbean.ConditionQuery;
import org.elasticsearch.action.count.CountRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author ESFlute (using FreeGen)
 */
public class BsParkSeaCB extends EsAbstractConditionBean {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected BsParkSeaCQ _conditionQuery;
    protected HpSpecification _specification;

    // ===================================================================================
    //                                                                             Control
    //                                                                             =======
    @Override
    public ParkSeaDbm asDBMeta() {
        return ParkSeaDbm.getInstance();
    }

    @Override
    public String asTableDbName() {
        return "park_sea";
    }

    @Override
    public boolean hasSpecifiedColumn() {
        return _specification != null;
    }

    @Override
    public ConditionQuery localCQ() {
        return doGetConditionQuery();
    }

    // ===================================================================================
    //                                                                         Primary Key
    //                                                                         ===========
    public ParkSeaCB acceptPK(String id) {
        assertObjectNotNull("id", id);
        BsParkSeaCB cb = this;
        cb.query().docMeta().setId_Equal(id);
        return (ParkSeaCB) this;
    }

    @Override
    public void acceptPrimaryKeyMap(Map<String, ? extends Object> primaryKeyMap) {
        acceptPK((String)primaryKeyMap.get("_id"));
    }

    // ===================================================================================
    //                                                                               Build
    //                                                                               =====
    @Override
    public CountRequestBuilder build(CountRequestBuilder builder) {
        if (_conditionQuery != null) {
            QueryBuilder queryBuilder = _conditionQuery.getQuery();
            if (queryBuilder != null) {
                builder.setQuery(queryBuilder);
            }
        }
        return builder;
    }

    @Override
    public SearchRequestBuilder build(SearchRequestBuilder builder) {
        if (_conditionQuery != null) {
            QueryBuilder queryBuilder = _conditionQuery.getQuery();
            if (queryBuilder != null) {
                builder.setQuery(queryBuilder);
            }
            _conditionQuery.getFieldSortBuilderList().forEach(sort -> {
                builder.addSort(sort);
            });
        }

        if (_specification != null) {
            builder.setFetchSource(_specification.columnList.toArray(new String[_specification.columnList.size()]), null);
        }

        return builder;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    public BsParkSeaCQ query() {
        assertQueryPurpose();
        return doGetConditionQuery();
    }

    protected BsParkSeaCQ doGetConditionQuery() {
        if (_conditionQuery == null) {
            _conditionQuery = createLocalCQ();
        }
        return _conditionQuery;
    }

    protected BsParkSeaCQ createLocalCQ() {
        return new ParkSeaCQ();
    }

    // ===================================================================================
    //                                                                             Specify
    //                                                                             =======
    public HpSpecification specify() {
        assertSpecifyPurpose();
        if (_specification == null) {
            _specification = new HpSpecification();
        }
        return _specification;
    }

    protected void assertQueryPurpose() {
    }

    protected void assertSpecifyPurpose() {
    }

    public static class HpSpecification {
        private List<String> columnList = new ArrayList<>();

        private void doColumn(String name) {
            columnList.add(name);
        }

        public void columnId() {
            doColumn("_id");
        }

        public void columnCode() {
            doColumn("code");
        }
        public void columnCreatedTime() {
            doColumn("createdTime");
        }
        public void columnUpdatedTime() {
            doColumn("updatedTime");
        }
        public void columnDefaultDate() {
            doColumn("defaultDate");
        }
        public void columnFormatDate() {
            doColumn("formatDate");
        }
        public void columnFormatDateTime() {
            doColumn("formatDateTime");
        }
        public void columnFormatTime() {
            doColumn("formatTime");
        }
        public void columnFormatDateOptionalTime() {
            doColumn("formatDateOptionalTime");
        }
    }
}
