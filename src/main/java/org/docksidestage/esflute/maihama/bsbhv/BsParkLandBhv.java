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
package org.docksidestage.esflute.maihama.bsbhv;

import java.util.List;
import java.util.Map;

import org.docksidestage.esflute.maihama.allcommon.EsAbstractBehavior;
import org.docksidestage.esflute.maihama.allcommon.EsAbstractEntity;
import org.docksidestage.esflute.maihama.allcommon.EsAbstractEntity.RequestOptionCall;
import org.docksidestage.esflute.maihama.bsentity.dbmeta.ParkLandDbm;
import org.docksidestage.esflute.maihama.cbean.ParkLandCB;
import org.docksidestage.esflute.maihama.exentity.ParkLand;
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
public abstract class BsParkLandBhv extends EsAbstractBehavior<ParkLand, ParkLandCB> {

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
        return "park_land";
    }

    @Override
    public String asEsSearchType() {
        return "park_land";
    }

    @Override
    public ParkLandDbm asDBMeta() {
        return ParkLandDbm.getInstance();
    }

    @Override
    protected <RESULT extends ParkLand> RESULT createEntity(Map<String, Object> source, Class<? extends RESULT> entityType) {
        try {
            final RESULT result = entityType.newInstance();
            result.setLabelTypeId(DfTypeUtil.toString(source.get("labelTypeId")));
            result.setWebConfigId(DfTypeUtil.toString(source.get("webConfigId")));
            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            final String msg = "Cannot create a new instance: " + entityType.getName();
            throw new IllegalBehaviorStateException(msg, e);
        }
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    public int selectCount(CBCall<ParkLandCB> cbLambda) {
        return facadeSelectCount(createCB(cbLambda));
    }

    public OptionalEntity<ParkLand> selectEntity(CBCall<ParkLandCB> cbLambda) {
        return facadeSelectEntity(createCB(cbLambda));
    }

    protected OptionalEntity<ParkLand> facadeSelectEntity(ParkLandCB cb) {
        return doSelectOptionalEntity(cb, typeOfSelectedEntity());
    }

    protected <ENTITY extends ParkLand> OptionalEntity<ENTITY> doSelectOptionalEntity(ParkLandCB cb,
            Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectEntity(cb, tp), cb);
    }

    @Override
    public ParkLandCB newConditionBean() {
        return new ParkLandCB();
    }

    @Override
    protected Entity doReadEntity(ConditionBean cb) {
        return facadeSelectEntity(downcast(cb)).orElse(null);
    }

    public ParkLand selectEntityWithDeletedCheck(CBCall<ParkLandCB> cbLambda) {
        return facadeSelectEntityWithDeletedCheck(createCB(cbLambda));
    }

    public OptionalEntity<ParkLand> selectByPK(String id) {
        return facadeSelectByPK(id);
    }

    protected OptionalEntity<ParkLand> facadeSelectByPK(String id) {
        return doSelectOptionalByPK(id, typeOfSelectedEntity());
    }

    protected <ENTITY extends ParkLand> ENTITY doSelectByPK(String id, Class<? extends ENTITY> tp) {
        return doSelectEntity(xprepareCBAsPK(id), tp);
    }

    protected ParkLandCB xprepareCBAsPK(String id) {
        assertObjectNotNull("id", id);
        return newConditionBean().acceptPK(id);
    }

    protected <ENTITY extends ParkLand> OptionalEntity<ENTITY> doSelectOptionalByPK(String id, Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectByPK(id, tp), id);
    }

    @Override
    protected Class<? extends ParkLand> typeOfSelectedEntity() {
        return ParkLand.class;
    }

    @Override
    protected Class<ParkLand> typeOfHandlingEntity() {
        return ParkLand.class;
    }

    @Override
    protected Class<ParkLandCB> typeOfHandlingConditionBean() {
        return ParkLandCB.class;
    }

    public ListResultBean<ParkLand> selectList(CBCall<ParkLandCB> cbLambda) {
        return facadeSelectList(createCB(cbLambda));
    }

    public PagingResultBean<ParkLand> selectPage(CBCall<ParkLandCB> cbLambda) {
        // #pending same?
        return (PagingResultBean<ParkLand>) facadeSelectList(createCB(cbLambda));
    }

    public void selectCursor(CBCall<ParkLandCB> cbLambda, EntityRowHandler<ParkLand> entityLambda) {
        facadeSelectCursor(createCB(cbLambda), entityLambda);
    }

    public void selectBulk(CBCall<ParkLandCB> cbLambda, EntityRowHandler<List<ParkLand>> entityLambda) {
        delegateSelectBulk(createCB(cbLambda), entityLambda,typeOfSelectedEntity());
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    public void insert(ParkLand entity) {
        doInsert(entity, null);
    }

    public void insert(ParkLand entity, RequestOptionCall<IndexRequestBuilder> opLambda) {
        if (entity instanceof EsAbstractEntity) {
            entity.asDocMeta().indexOption(opLambda);
        }
        doInsert(entity, null);
    }

    public void update(ParkLand entity) {
        doUpdate(entity, null);
    }

    public void update(ParkLand entity, RequestOptionCall<IndexRequestBuilder> opLambda) {
        if (entity instanceof EsAbstractEntity) {
            entity.asDocMeta().indexOption(opLambda);
        }
        doUpdate(entity, null);
    }

    public void insertOrUpdate(ParkLand entity) {
        doInsertOrUpdate(entity, null, null);
    }

    public void insertOrUpdate(ParkLand entity, RequestOptionCall<IndexRequestBuilder> opLambda) {
        if (entity instanceof EsAbstractEntity) {
            entity.asDocMeta().indexOption(opLambda);
        }
        doInsertOrUpdate(entity, null, null);
    }

    public void delete(ParkLand entity) {
        doDelete(entity, null);
    }

    public void delete(ParkLand entity, RequestOptionCall<DeleteRequestBuilder> opLambda) {
        if (entity instanceof EsAbstractEntity) {
            entity.asDocMeta().deleteOption(opLambda);
        }
        doDelete(entity, null);
    }

    public int queryDelete(CBCall<ParkLandCB> cbLambda) {
        return doQueryDelete(createCB(cbLambda), null);
    }

    public int[] batchInsert(List<ParkLand> list) {
        return batchInsert(list, null);
    }

    public int[] batchInsert(List<ParkLand> list, RequestOptionCall<BulkRequestBuilder> call) {
        return doBatchInsert(new BulkList<>(list, call), null);
    }

    public int[] batchUpdate(List<ParkLand> list) {
        return batchUpdate(list, null);
    }

    public int[] batchUpdate(List<ParkLand> list, RequestOptionCall<BulkRequestBuilder> call) {
        return doBatchUpdate(new BulkList<>(list, call), null);
    }

    public int[] batchDelete(List<ParkLand> list) {
        return batchDelete(list, null);
    }

    public int[] batchDelete(List<ParkLand> list, RequestOptionCall<BulkRequestBuilder> call) {
        return doBatchDelete(new BulkList<>(list, call), null);
    }

    // #pending create, modify, remove
}

