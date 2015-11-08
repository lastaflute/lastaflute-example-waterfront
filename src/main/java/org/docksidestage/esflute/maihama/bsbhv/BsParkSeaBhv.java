package org.docksidestage.esflute.maihama.bsbhv;

import java.util.List;
import java.util.Map;

import org.docksidestage.esflute.maihama.allcommon.EsAbstractBehavior;
import org.docksidestage.esflute.maihama.allcommon.EsAbstractEntity;
import org.docksidestage.esflute.maihama.allcommon.EsAbstractEntity.RequestOptionCall;
import org.docksidestage.esflute.maihama.bsentity.dbmeta.ParkSeaDbm;
import org.docksidestage.esflute.maihama.cbean.ParkSeaCB;
import org.docksidestage.esflute.maihama.exentity.ParkSea;
import org.dbflute.Entity;
import org.dbflute.bhv.readable.CBCall;
import org.dbflute.bhv.readable.EntityRowHandler;
import org.dbflute.cbean.ConditionBean;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.exception.IllegalBehaviorStateException;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.util.DfTypeUtil;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;

/**
 * @author ESFlute (using FreeGen)
 */
public abstract class BsParkSeaBhv extends EsAbstractBehavior<ParkSea, ParkSeaCB> {

    // ===================================================================================
    //                                                                    Control Override
    //                                                                    ================
    @Override
    public String asTableDbName() {
        return asEsIndexType();
    }

    @Override
    protected String asEsIndex() {
        return ".maihama_index";
    }

    @Override
    public String asEsIndexType() {
        return "park_sea";
    }

    @Override
    public String asEsSearchType() {
        return "park_sea";
    }

    @Override
    public ParkSeaDbm asDBMeta() {
        return ParkSeaDbm.getInstance();
    }

    @Override
    protected <RESULT extends ParkSea> RESULT createEntity(Map<String, Object> source, Class<? extends RESULT> entityType) {
        try {
            final RESULT result = entityType.newInstance();
            result.setCode(DfTypeUtil.toString(source.get("code")));
            result.setCreatedTime(DfTypeUtil.toLong(source.get("createdTime")));
            result.setUpdatedTime(DfTypeUtil.toLong(source.get("updatedTime")));
            result.setDefaultDate(DfTypeUtil.toLocalDateTime(source.get("defaultDate")));
            result.setFormatDate(DfTypeUtil.toLocalDate(source.get("formatDate")));
            result.setFormatDateTime(DfTypeUtil.toLocalDateTime(source.get("formatDateTime")));
            result.setFormatTime(DfTypeUtil.toLocalTime(source.get("formatTime")));
            result.setFormatDateOptionalTime(DfTypeUtil.toLocalDateTime(source.get("formatDateOptionalTime")));
            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            final String msg = "Cannot create a new instance: " + entityType.getName();
            throw new IllegalBehaviorStateException(msg, e);
        }
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    public int selectCount(CBCall<ParkSeaCB> cbLambda) {
        return facadeSelectCount(createCB(cbLambda));
    }

    public OptionalEntity<ParkSea> selectEntity(CBCall<ParkSeaCB> cbLambda) {
        return facadeSelectEntity(createCB(cbLambda));
    }

    protected OptionalEntity<ParkSea> facadeSelectEntity(ParkSeaCB cb) {
        return doSelectOptionalEntity(cb, typeOfSelectedEntity());
    }

    protected <ENTITY extends ParkSea> OptionalEntity<ENTITY> doSelectOptionalEntity(ParkSeaCB cb,
            Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectEntity(cb, tp), cb);
    }

    @Override
    public ParkSeaCB newConditionBean() {
        return new ParkSeaCB();
    }

    @Override
    protected Entity doReadEntity(ConditionBean cb) {
        return facadeSelectEntity(downcast(cb)).orElse(null);
    }

    public ParkSea selectEntityWithDeletedCheck(CBCall<ParkSeaCB> cbLambda) {
        return facadeSelectEntityWithDeletedCheck(createCB(cbLambda));
    }

    public OptionalEntity<ParkSea> selectByPK(String id) {
        return facadeSelectByPK(id);
    }

    protected OptionalEntity<ParkSea> facadeSelectByPK(String id) {
        return doSelectOptionalByPK(id, typeOfSelectedEntity());
    }

    protected <ENTITY extends ParkSea> ENTITY doSelectByPK(String id, Class<? extends ENTITY> tp) {
        return doSelectEntity(xprepareCBAsPK(id), tp);
    }

    protected ParkSeaCB xprepareCBAsPK(String id) {
        assertObjectNotNull("id", id);
        return newConditionBean().acceptPK(id);
    }

    protected <ENTITY extends ParkSea> OptionalEntity<ENTITY> doSelectOptionalByPK(String id, Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectByPK(id, tp), id);
    }

    @Override
    protected Class<? extends ParkSea> typeOfSelectedEntity() {
        return ParkSea.class;
    }

    @Override
    protected Class<ParkSea> typeOfHandlingEntity() {
        return ParkSea.class;
    }

    @Override
    protected Class<ParkSeaCB> typeOfHandlingConditionBean() {
        return ParkSeaCB.class;
    }

    public ListResultBean<ParkSea> selectList(CBCall<ParkSeaCB> cbLambda) {
        return facadeSelectList(createCB(cbLambda));
    }

    public PagingResultBean<ParkSea> selectPage(CBCall<ParkSeaCB> cbLambda) {
        // #pending same?
        return (PagingResultBean<ParkSea>) facadeSelectList(createCB(cbLambda));
    }

    public void selectCursor(CBCall<ParkSeaCB> cbLambda, EntityRowHandler<ParkSea> entityLambda) {
        facadeSelectCursor(createCB(cbLambda), entityLambda);
    }

    public void selectBulk(CBCall<ParkSeaCB> cbLambda, EntityRowHandler<List<ParkSea>> entityLambda) {
        delegateSelectBulk(createCB(cbLambda), entityLambda,typeOfSelectedEntity());
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    public void insert(ParkSea entity) {
        doInsert(entity, null);
    }

    public void insert(ParkSea entity, RequestOptionCall<IndexRequestBuilder> opLambda) {
        if (entity instanceof EsAbstractEntity) {
            entity.asDocMeta().indexOption(opLambda);
        }
        doInsert(entity, null);
    }

    public void update(ParkSea entity) {
        doUpdate(entity, null);
    }

    public void update(ParkSea entity, RequestOptionCall<IndexRequestBuilder> opLambda) {
        if (entity instanceof EsAbstractEntity) {
            entity.asDocMeta().indexOption(opLambda);
        }
        doUpdate(entity, null);
    }

    public void insertOrUpdate(ParkSea entity) {
        doInsertOrUpdate(entity, null, null);
    }

    public void insertOrUpdate(ParkSea entity, RequestOptionCall<IndexRequestBuilder> opLambda) {
        if (entity instanceof EsAbstractEntity) {
            entity.asDocMeta().indexOption(opLambda);
        }
        doInsertOrUpdate(entity, null, null);
    }

    public void delete(ParkSea entity) {
        doDelete(entity, null);
    }

    public void delete(ParkSea entity, RequestOptionCall<DeleteRequestBuilder> opLambda) {
        if (entity instanceof EsAbstractEntity) {
            entity.asDocMeta().deleteOption(opLambda);
        }
        doDelete(entity, null);
    }

    public int queryDelete(CBCall<ParkSeaCB> cbLambda) {
        return doQueryDelete(createCB(cbLambda), null);
    }

    public int[] batchInsert(List<ParkSea> list) {
        return batchInsert(list, null);
    }

    public int[] batchInsert(List<ParkSea> list, RequestOptionCall<BulkRequestBuilder> call) {
        return doBatchInsert(new BulkList<>(list, call), null);
    }

    public int[] batchUpdate(List<ParkSea> list) {
        return batchUpdate(list, null);
    }

    public int[] batchUpdate(List<ParkSea> list, RequestOptionCall<BulkRequestBuilder> call) {
        return doBatchUpdate(new BulkList<>(list, call), null);
    }

    public int[] batchDelete(List<ParkSea> list) {
        return batchDelete(list, null);
    }

    public int[] batchDelete(List<ParkSea> list, RequestOptionCall<BulkRequestBuilder> call) {
        return doBatchDelete(new BulkList<>(list, call), null);
    }

    // #pending create, modify, remove
}

