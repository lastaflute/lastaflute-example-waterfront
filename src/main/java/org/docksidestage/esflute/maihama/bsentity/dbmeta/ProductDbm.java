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
package org.docksidestage.esflute.maihama.bsentity.dbmeta;

import java.time.*;
import java.util.List;
import java.util.Map;

import org.docksidestage.esflute.maihama.exentity.Product;

import org.dbflute.Entity;
import org.dbflute.dbmeta.AbstractDBMeta;
import org.dbflute.dbmeta.info.ColumnInfo;
import org.dbflute.dbmeta.info.UniqueInfo;
import org.dbflute.dbmeta.name.TableSqlName;
import org.dbflute.dbmeta.property.PropertyGateway;
import org.dbflute.dbway.DBDef;
import org.dbflute.util.DfTypeUtil;

/**
 * @author ESFlute (using FreeGen)
 */
public class ProductDbm extends AbstractDBMeta {

    protected static final Class<?> suppressUnusedImportLocalDateTime = LocalDateTime.class;

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final ProductDbm _instance = new ProductDbm();

    private ProductDbm() {
    }

    public static ProductDbm getInstance() {
        return _instance;
    }

    // ===================================================================================
    //                                                                       Current DBDef
    //                                                                       =============
    @Override
    public String getProjectName() {
        return null;
    }

    @Override
    public String getProjectPrefix() {
        return null;
    }

    @Override
    public String getGenerationGapBasePrefix() {
        return null;
    }

    @Override
    public DBDef getCurrentDBDef() {
        return null;
    }

    // ===================================================================================
    //                                                                    Property Gateway
    //                                                                    ================
    // -----------------------------------------------------
    //                                       Column Property
    //                                       ---------------
    protected final Map<String, PropertyGateway> _epgMap = newHashMap();
    {
        setupEpg(_epgMap, et-> ((Product)et).getCategoryCode(),(et,vl)->((Product) et).setCategoryCode(DfTypeUtil.toString(vl)), "categoryCode");
        setupEpg(_epgMap, et-> ((Product)et).getDescription(),(et,vl)->((Product) et).setDescription(DfTypeUtil.toString(vl)), "description");
        setupEpg(_epgMap, et-> ((Product)et).getHandleCode(),(et,vl)->((Product) et).setHandleCode(DfTypeUtil.toString(vl)), "handleCode");
        setupEpg(_epgMap, et-> ((Product)et).getName(),(et,vl)->((Product) et).setName(DfTypeUtil.toString(vl)), "name");
        setupEpg(_epgMap, et-> ((Product)et).getRegisterDatetime(),(et,vl)->((Product) et).setRegisterDatetime(DfTypeUtil.toLocalDateTime(vl)), "registerDatetime");
        setupEpg(_epgMap, et-> ((Product)et).getRegisterUser(),(et,vl)->((Product) et).setRegisterUser(DfTypeUtil.toString(vl)), "registerUser");
        setupEpg(_epgMap, et-> ((Product)et).getRegularPrice(),(et,vl)->((Product) et).setRegularPrice(DfTypeUtil.toInteger(vl)), "regularPrice");
        setupEpg(_epgMap, et-> ((Product)et).getStatus(),(et,vl)->((Product) et).setStatus(DfTypeUtil.toString(vl)), "status");
        setupEpg(_epgMap, et-> ((Product)et).getUpdateDatetime(),(et,vl)->((Product) et).setUpdateDatetime(DfTypeUtil.toLocalDateTime(vl)), "updateDatetime");
        setupEpg(_epgMap, et-> ((Product)et).getUpdateUser(),(et,vl)->((Product) et).setUpdateUser(DfTypeUtil.toString(vl)), "updateUser");
    }

    @Override
    public PropertyGateway findPropertyGateway(final String prop) {
        return doFindEpg(_epgMap, prop);
    }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "product";
    protected final String _tableDispName = "product";
    protected final String _tablePropertyName = "Product";
    public String getTableDbName() { return _tableDbName; }
    @Override
    public String getTableDispName() { return _tableDispName; }
    @Override
    public String getTablePropertyName() { return _tablePropertyName; }
    @Override
    public TableSqlName getTableSqlName() { return null; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnCategoryCode = cci("category_code", "category_code", null, null, String.class, "categoryCode", null, false, false, false, "String", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnDescription = cci("description", "description", null, null, String.class, "description", null, false, false, false, "String", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnHandleCode = cci("handle_code", "handle_code", null, null, String.class, "handleCode", null, false, false, false, "String", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnName = cci("name", "name", null, null, String.class, "name", null, false, false, false, "String", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterDatetime = cci("register_datetime", "register_datetime", null, null, LocalDateTime.class, "registerDatetime", null, false, false, false, "LocalDateTime", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterUser = cci("register_user", "register_user", null, null, String.class, "registerUser", null, false, false, false, "String", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegularPrice = cci("regular_price", "regular_price", null, null, Integer.class, "regularPrice", null, false, false, false, "Integer", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnStatus = cci("status", "status", null, null, String.class, "status", null, false, false, false, "String", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateDatetime = cci("update_datetime", "update_datetime", null, null, LocalDateTime.class, "updateDatetime", null, false, false, false, "LocalDateTime", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateUser = cci("update_user", "update_user", null, null, String.class, "updateUser", null, false, false, false, "String", 0, 0, null, false, null, null, null, null, null, false);

    public ColumnInfo columnCategoryCode() { return _columnCategoryCode; }
    public ColumnInfo columnDescription() { return _columnDescription; }
    public ColumnInfo columnHandleCode() { return _columnHandleCode; }
    public ColumnInfo columnName() { return _columnName; }
    public ColumnInfo columnRegisterDatetime() { return _columnRegisterDatetime; }
    public ColumnInfo columnRegisterUser() { return _columnRegisterUser; }
    public ColumnInfo columnRegularPrice() { return _columnRegularPrice; }
    public ColumnInfo columnStatus() { return _columnStatus; }
    public ColumnInfo columnUpdateDatetime() { return _columnUpdateDatetime; }
    public ColumnInfo columnUpdateUser() { return _columnUpdateUser; }

    protected List<ColumnInfo> ccil() {
        List<ColumnInfo> ls = newArrayList();
        ls.add(columnCategoryCode());
        ls.add(columnDescription());
        ls.add(columnHandleCode());
        ls.add(columnName());
        ls.add(columnRegisterDatetime());
        ls.add(columnRegisterUser());
        ls.add(columnRegularPrice());
        ls.add(columnStatus());
        ls.add(columnUpdateDatetime());
        ls.add(columnUpdateUser());
        return ls;
    }

    // ===================================================================================
    //                                                                         Unique Info
    //                                                                         ===========
    @Override
    public boolean hasPrimaryKey() {
        return false;
    }

    @Override
    public boolean hasCompoundPrimaryKey() {
        return false;
    }

    @Override
    protected UniqueInfo cpui() {
        return null;
    }

    // ===================================================================================
    //                                                                           Type Name
    //                                                                           =========
    @Override
    public String getEntityTypeName() {
        return "org.docksidestage.esflute.maihama.exentity.Product";
    }

    @Override
    public String getConditionBeanTypeName() {
        return "org.docksidestage.esflute.maihama.cbean.ProductCB";
    }

    @Override
    public String getBehaviorTypeName() {
        return "org.docksidestage.esflute.maihama.exbhv.ProductBhv";
    }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    @Override
    public Class<? extends Entity> getEntityType() {
        return Product.class;
    }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    @Override
    public Entity newEntity() {
        return new Product();
    }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    @Override
    public void acceptPrimaryKeyMap(Entity entity, Map<String, ? extends Object> primaryKeyMap) {
    }

    @Override
    public void acceptAllColumnMap(Entity entity, Map<String, ? extends Object> allColumnMap) {
    }

    @Override
    public Map<String, Object> extractPrimaryKeyMap(Entity entity) {
        return null;
    }

    @Override
    public Map<String, Object> extractAllColumnMap(Entity entity) {
        return null;
    }
}

