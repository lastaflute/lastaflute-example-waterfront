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

import org.docksidestage.esflute.maihama.exentity.ParkSea;

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
public class ParkSeaDbm extends AbstractDBMeta {

    protected static final Class<?> suppressUnusedImportLocalDateTime = LocalDateTime.class;

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final ParkSeaDbm _instance = new ParkSeaDbm();

    private ParkSeaDbm() {
    }

    public static ParkSeaDbm getInstance() {
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
        setupEpg(_epgMap, et-> ((ParkSea)et).getCode(),(et,vl)->((ParkSea) et).setCode(DfTypeUtil.toString(vl)), "code");
        setupEpg(_epgMap, et-> ((ParkSea)et).getCreatedTime(),(et,vl)->((ParkSea) et).setCreatedTime(DfTypeUtil.toLong(vl)), "createdTime");
        setupEpg(_epgMap, et-> ((ParkSea)et).getUpdatedTime(),(et,vl)->((ParkSea) et).setUpdatedTime(DfTypeUtil.toLong(vl)), "updatedTime");
        setupEpg(_epgMap, et-> ((ParkSea)et).getDefaultDate(),(et,vl)->((ParkSea) et).setDefaultDate(DfTypeUtil.toLocalDateTime(vl)), "defaultDate");
        setupEpg(_epgMap, et-> ((ParkSea)et).getFormatDate(),(et,vl)->((ParkSea) et).setFormatDate(DfTypeUtil.toLocalDate(vl)), "formatDate");
        setupEpg(_epgMap, et-> ((ParkSea)et).getFormatDateTime(),(et,vl)->((ParkSea) et).setFormatDateTime(DfTypeUtil.toLocalDateTime(vl)), "formatDateTime");
        setupEpg(_epgMap, et-> ((ParkSea)et).getFormatTime(),(et,vl)->((ParkSea) et).setFormatTime(DfTypeUtil.toLocalTime(vl)), "formatTime");
        setupEpg(_epgMap, et-> ((ParkSea)et).getFormatDateOptionalTime(),(et,vl)->((ParkSea) et).setFormatDateOptionalTime(DfTypeUtil.toLocalDateTime(vl)), "formatDateOptionalTime");
    }

    @Override
    public PropertyGateway findPropertyGateway(final String prop) {
        return doFindEpg(_epgMap, prop);
    }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "park_sea";
    protected final String _tableDispName = "park_sea";
    protected final String _tablePropertyName = "ParkSea";
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
    protected final ColumnInfo _columnCode = cci("code", "code", null, null, String.class, "code", null, false, false, false, "String", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnCreatedTime = cci("createdTime", "createdTime", null, null, Long.class, "createdTime", null, false, false, false, "Long", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdatedTime = cci("updatedTime", "updatedTime", null, null, Long.class, "updatedTime", null, false, false, false, "Long", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnDefaultDate = cci("defaultDate", "defaultDate", null, null, LocalDateTime.class, "defaultDate", null, false, false, false, "LocalDateTime", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnFormatDate = cci("formatDate", "formatDate", null, null, LocalDate.class, "formatDate", null, false, false, false, "LocalDate", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnFormatDateTime = cci("formatDateTime", "formatDateTime", null, null, LocalDateTime.class, "formatDateTime", null, false, false, false, "LocalDateTime", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnFormatTime = cci("formatTime", "formatTime", null, null, LocalTime.class, "formatTime", null, false, false, false, "LocalTime", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnFormatDateOptionalTime = cci("formatDateOptionalTime", "formatDateOptionalTime", null, null, LocalDateTime.class, "formatDateOptionalTime", null, false, false, false, "LocalDateTime", 0, 0, null, false, null, null, null, null, null, false);

    public ColumnInfo columnCode() { return _columnCode; }
    public ColumnInfo columnCreatedTime() { return _columnCreatedTime; }
    public ColumnInfo columnUpdatedTime() { return _columnUpdatedTime; }
    public ColumnInfo columnDefaultDate() { return _columnDefaultDate; }
    public ColumnInfo columnFormatDate() { return _columnFormatDate; }
    public ColumnInfo columnFormatDateTime() { return _columnFormatDateTime; }
    public ColumnInfo columnFormatTime() { return _columnFormatTime; }
    public ColumnInfo columnFormatDateOptionalTime() { return _columnFormatDateOptionalTime; }

    protected List<ColumnInfo> ccil() {
        List<ColumnInfo> ls = newArrayList();
        ls.add(columnCode());
        ls.add(columnCreatedTime());
        ls.add(columnUpdatedTime());
        ls.add(columnDefaultDate());
        ls.add(columnFormatDate());
        ls.add(columnFormatDateTime());
        ls.add(columnFormatTime());
        ls.add(columnFormatDateOptionalTime());
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
        return "org.docksidestage.esflute.maihama.exentity.ParkSea";
    }

    @Override
    public String getConditionBeanTypeName() {
        return "org.docksidestage.esflute.maihama.cbean.ParkSeaCB";
    }

    @Override
    public String getBehaviorTypeName() {
        return "org.docksidestage.esflute.maihama.exbhv.ParkSeaBhv";
    }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    @Override
    public Class<? extends Entity> getEntityType() {
        return ParkSea.class;
    }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    @Override
    public Entity newEntity() {
        return new ParkSea();
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

