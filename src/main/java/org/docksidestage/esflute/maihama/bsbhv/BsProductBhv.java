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
import org.docksidestage.esflute.maihama.bsentity.dbmeta.ProductDbm;
import org.docksidestage.esflute.maihama.cbean.ProductCB;
import org.docksidestage.esflute.maihama.exentity.Product;
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
public abstract class BsProductBhv extends EsAbstractBehavior<Product, ProductCB> {

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
        return "product";
    }

    @Override
    public String asEsSearchType() {
        return "product";
    }

    @Override
    public ProductDbm asDBMeta() {
        return ProductDbm.getInstance();
    }

    @Override
    protected <RESULT extends Product> RESULT createEntity(Map<String, Object> source, Class<? extends RESULT> entityType) {
        try {
            final RESULT result = entityType.newInstance();
            result.setCategoryCode(DfTypeUtil.toString(source.get("category_code")));
            result.setDescription(DfTypeUtil.toString(source.get("description")));
            result.setHandleCode(DfTypeUtil.toString(source.get("handle_code")));
            result.setName(DfTypeUtil.toString(source.get("name")));
            result.setRegisterDatetime(DfTypeUtil.toLocalDateTime(source.get("register_datetime")));
            result.setRegisterUser(DfTypeUtil.toString(source.get("register_user")));
            result.setRegularPrice(DfTypeUtil.toInteger(source.get("regular_price")));
            result.setStatus(DfTypeUtil.toString(source.get("status")));
            result.setUpdateDatetime(DfTypeUtil.toLocalDateTime(source.get("update_datetime")));
            result.setUpdateUser(DfTypeUtil.toString(source.get("update_user")));
            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            final String msg = "Cannot create a new instance: " + entityType.getName();
            throw new IllegalBehaviorStateException(msg, e);
        }
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    public int selectCount(CBCall<ProductCB> cbLambda) {
        return facadeSelectCount(createCB(cbLambda));
    }

    public OptionalEntity<Product> selectEntity(CBCall<ProductCB> cbLambda) {
        return facadeSelectEntity(createCB(cbLambda));
    }

    protected OptionalEntity<Product> facadeSelectEntity(ProductCB cb) {
        return doSelectOptionalEntity(cb, typeOfSelectedEntity());
    }

    protected <ENTITY extends Product> OptionalEntity<ENTITY> doSelectOptionalEntity(ProductCB cb,
            Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectEntity(cb, tp), cb);
    }

    @Override
    public ProductCB newConditionBean() {
        return new ProductCB();
    }

    @Override
    protected Entity doReadEntity(ConditionBean cb) {
        return facadeSelectEntity(downcast(cb)).orElse(null);
    }

    public Product selectEntityWithDeletedCheck(CBCall<ProductCB> cbLambda) {
        return facadeSelectEntityWithDeletedCheck(createCB(cbLambda));
    }

    public OptionalEntity<Product> selectByPK(String id) {
        return facadeSelectByPK(id);
    }

    protected OptionalEntity<Product> facadeSelectByPK(String id) {
        return doSelectOptionalByPK(id, typeOfSelectedEntity());
    }

    protected <ENTITY extends Product> ENTITY doSelectByPK(String id, Class<? extends ENTITY> tp) {
        return doSelectEntity(xprepareCBAsPK(id), tp);
    }

    protected ProductCB xprepareCBAsPK(String id) {
        assertObjectNotNull("id", id);
        return newConditionBean().acceptPK(id);
    }

    protected <ENTITY extends Product> OptionalEntity<ENTITY> doSelectOptionalByPK(String id, Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectByPK(id, tp), id);
    }

    @Override
    protected Class<? extends Product> typeOfSelectedEntity() {
        return Product.class;
    }

    @Override
    protected Class<Product> typeOfHandlingEntity() {
        return Product.class;
    }

    @Override
    protected Class<ProductCB> typeOfHandlingConditionBean() {
        return ProductCB.class;
    }

    public ListResultBean<Product> selectList(CBCall<ProductCB> cbLambda) {
        return facadeSelectList(createCB(cbLambda));
    }

    public PagingResultBean<Product> selectPage(CBCall<ProductCB> cbLambda) {
        // #pending same?
        return (PagingResultBean<Product>) facadeSelectList(createCB(cbLambda));
    }

    public void selectCursor(CBCall<ProductCB> cbLambda, EntityRowHandler<Product> entityLambda) {
        facadeSelectCursor(createCB(cbLambda), entityLambda);
    }

    public void selectBulk(CBCall<ProductCB> cbLambda, EntityRowHandler<List<Product>> entityLambda) {
        delegateSelectBulk(createCB(cbLambda), entityLambda,typeOfSelectedEntity());
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    public void insert(Product entity) {
        doInsert(entity, null);
    }

    public void insert(Product entity, RequestOptionCall<IndexRequestBuilder> opLambda) {
        if (entity instanceof EsAbstractEntity) {
            entity.asDocMeta().indexOption(opLambda);
        }
        doInsert(entity, null);
    }

    public void update(Product entity) {
        doUpdate(entity, null);
    }

    public void update(Product entity, RequestOptionCall<IndexRequestBuilder> opLambda) {
        if (entity instanceof EsAbstractEntity) {
            entity.asDocMeta().indexOption(opLambda);
        }
        doUpdate(entity, null);
    }

    public void insertOrUpdate(Product entity) {
        doInsertOrUpdate(entity, null, null);
    }

    public void insertOrUpdate(Product entity, RequestOptionCall<IndexRequestBuilder> opLambda) {
        if (entity instanceof EsAbstractEntity) {
            entity.asDocMeta().indexOption(opLambda);
        }
        doInsertOrUpdate(entity, null, null);
    }

    public void delete(Product entity) {
        doDelete(entity, null);
    }

    public void delete(Product entity, RequestOptionCall<DeleteRequestBuilder> opLambda) {
        if (entity instanceof EsAbstractEntity) {
            entity.asDocMeta().deleteOption(opLambda);
        }
        doDelete(entity, null);
    }

    public int queryDelete(CBCall<ProductCB> cbLambda) {
        return doQueryDelete(createCB(cbLambda), null);
    }

    public int[] batchInsert(List<Product> list) {
        return batchInsert(list, null);
    }

    public int[] batchInsert(List<Product> list, RequestOptionCall<BulkRequestBuilder> call) {
        return doBatchInsert(new BulkList<>(list, call), null);
    }

    public int[] batchUpdate(List<Product> list) {
        return batchUpdate(list, null);
    }

    public int[] batchUpdate(List<Product> list, RequestOptionCall<BulkRequestBuilder> call) {
        return doBatchUpdate(new BulkList<>(list, call), null);
    }

    public int[] batchDelete(List<Product> list) {
        return batchDelete(list, null);
    }

    public int[] batchDelete(List<Product> list, RequestOptionCall<BulkRequestBuilder> call) {
        return doBatchDelete(new BulkList<>(list, call), null);
    }

    // #pending create, modify, remove
}

