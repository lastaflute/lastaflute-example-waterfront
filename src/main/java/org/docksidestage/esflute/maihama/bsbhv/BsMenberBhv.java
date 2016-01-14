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
import org.docksidestage.esflute.maihama.bsentity.dbmeta.MenberDbm;
import org.docksidestage.esflute.maihama.cbean.MenberCB;
import org.docksidestage.esflute.maihama.exentity.Menber;
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
public abstract class BsMenberBhv extends EsAbstractBehavior<Menber, MenberCB> {

    // ===================================================================================
    //                                                                    Control Override
    //                                                                    ================
    @Override
    public String asTableDbName() {
        return asEsIndexType();
    }

    @Override
    protected String asEsIndex() {
        return "maihama";
    }

    @Override
    public String asEsIndexType() {
        return "menber";
    }

    @Override
    public String asEsSearchType() {
        return "menber";
    }

    @Override
    public MenberDbm asDBMeta() {
        return MenberDbm.getInstance();
    }

    @Override
    protected <RESULT extends Menber> RESULT createEntity(Map<String, Object> source, Class<? extends RESULT> entityType) {
        try {
            final RESULT result = entityType.newInstance();
            result.setAccount(DfTypeUtil.toString(source.get("account")));
            result.setName(DfTypeUtil.toString(source.get("name")));
            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            final String msg = "Cannot create a new instance: " + entityType.getName();
            throw new IllegalBehaviorStateException(msg, e);
        }
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    public int selectCount(CBCall<MenberCB> cbLambda) {
        return facadeSelectCount(createCB(cbLambda));
    }

    public OptionalEntity<Menber> selectEntity(CBCall<MenberCB> cbLambda) {
        return facadeSelectEntity(createCB(cbLambda));
    }

    protected OptionalEntity<Menber> facadeSelectEntity(MenberCB cb) {
        return doSelectOptionalEntity(cb, typeOfSelectedEntity());
    }

    protected <ENTITY extends Menber> OptionalEntity<ENTITY> doSelectOptionalEntity(MenberCB cb,
            Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectEntity(cb, tp), cb);
    }

    @Override
    public MenberCB newConditionBean() {
        return new MenberCB();
    }

    @Override
    protected Entity doReadEntity(ConditionBean cb) {
        return facadeSelectEntity(downcast(cb)).orElse(null);
    }

    public Menber selectEntityWithDeletedCheck(CBCall<MenberCB> cbLambda) {
        return facadeSelectEntityWithDeletedCheck(createCB(cbLambda));
    }

    public OptionalEntity<Menber> selectByPK(String id) {
        return facadeSelectByPK(id);
    }

    protected OptionalEntity<Menber> facadeSelectByPK(String id) {
        return doSelectOptionalByPK(id, typeOfSelectedEntity());
    }

    protected <ENTITY extends Menber> ENTITY doSelectByPK(String id, Class<? extends ENTITY> tp) {
        return doSelectEntity(xprepareCBAsPK(id), tp);
    }

    protected MenberCB xprepareCBAsPK(String id) {
        assertObjectNotNull("id", id);
        return newConditionBean().acceptPK(id);
    }

    protected <ENTITY extends Menber> OptionalEntity<ENTITY> doSelectOptionalByPK(String id, Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectByPK(id, tp), id);
    }

    @Override
    protected Class<? extends Menber> typeOfSelectedEntity() {
        return Menber.class;
    }

    @Override
    protected Class<Menber> typeOfHandlingEntity() {
        return Menber.class;
    }

    @Override
    protected Class<MenberCB> typeOfHandlingConditionBean() {
        return MenberCB.class;
    }

    public ListResultBean<Menber> selectList(CBCall<MenberCB> cbLambda) {
        return facadeSelectList(createCB(cbLambda));
    }

    public PagingResultBean<Menber> selectPage(CBCall<MenberCB> cbLambda) {
        // #pending same?
        return (PagingResultBean<Menber>) facadeSelectList(createCB(cbLambda));
    }

    public void selectCursor(CBCall<MenberCB> cbLambda, EntityRowHandler<Menber> entityLambda) {
        facadeSelectCursor(createCB(cbLambda), entityLambda);
    }

    public void selectBulk(CBCall<MenberCB> cbLambda, EntityRowHandler<List<Menber>> entityLambda) {
        delegateSelectBulk(createCB(cbLambda), entityLambda,typeOfSelectedEntity());
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    public void insert(Menber entity) {
        doInsert(entity, null);
    }

    public void insert(Menber entity, RequestOptionCall<IndexRequestBuilder> opLambda) {
        if (entity instanceof EsAbstractEntity) {
            entity.asDocMeta().indexOption(opLambda);
        }
        doInsert(entity, null);
    }

    public void update(Menber entity) {
        doUpdate(entity, null);
    }

    public void update(Menber entity, RequestOptionCall<IndexRequestBuilder> opLambda) {
        if (entity instanceof EsAbstractEntity) {
            entity.asDocMeta().indexOption(opLambda);
        }
        doUpdate(entity, null);
    }

    public void insertOrUpdate(Menber entity) {
        doInsertOrUpdate(entity, null, null);
    }

    public void insertOrUpdate(Menber entity, RequestOptionCall<IndexRequestBuilder> opLambda) {
        if (entity instanceof EsAbstractEntity) {
            entity.asDocMeta().indexOption(opLambda);
        }
        doInsertOrUpdate(entity, null, null);
    }

    public void delete(Menber entity) {
        doDelete(entity, null);
    }

    public void delete(Menber entity, RequestOptionCall<DeleteRequestBuilder> opLambda) {
        if (entity instanceof EsAbstractEntity) {
            entity.asDocMeta().deleteOption(opLambda);
        }
        doDelete(entity, null);
    }

    public int queryDelete(CBCall<MenberCB> cbLambda) {
        return doQueryDelete(createCB(cbLambda), null);
    }

    public int[] batchInsert(List<Menber> list) {
        return batchInsert(list, null, null);
    }

    public int[] batchInsert(List<Menber> list, RequestOptionCall<BulkRequestBuilder> call) {
        return batchInsert(list, call, null);
    }

    public int[] batchInsert(List<Menber> list, RequestOptionCall<BulkRequestBuilder> call,
            RequestOptionCall<IndexRequestBuilder> entityCall) {
        return doBatchInsert(new BulkList<>(list, call, entityCall), null);
    }

    public int[] batchUpdate(List<Menber> list) {
        return batchUpdate(list, null, null);
    }

    public int[] batchUpdate(List<Menber> list, RequestOptionCall<BulkRequestBuilder> call) {
        return batchUpdate(list, call, null);
    }

    public int[] batchUpdate(List<Menber> list, RequestOptionCall<BulkRequestBuilder> call,
            RequestOptionCall<IndexRequestBuilder> entityCall) {
        return doBatchUpdate(new BulkList<>(list, call, entityCall), null);
    }

    public int[] batchDelete(List<Menber> list) {
        return batchDelete(list, null, null);
    }

    public int[] batchDelete(List<Menber> list, RequestOptionCall<BulkRequestBuilder> call) {
        return batchDelete(list, call, null);
    }

    public int[] batchDelete(List<Menber> list, RequestOptionCall<BulkRequestBuilder> call,
            RequestOptionCall<IndexRequestBuilder> entityCall) {
        return doBatchDelete(new BulkList<>(list, call, entityCall), null);
    }

    // #pending create, modify, remove
}

