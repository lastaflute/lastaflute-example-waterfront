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
package org.docksidestage.esflute.maihama.bsentity;

import java.time.*;
import java.util.HashMap;
import java.util.Map;

import org.docksidestage.esflute.maihama.allcommon.EsAbstractEntity;
import org.docksidestage.esflute.maihama.bsentity.dbmeta.ParkSeaDbm;

/**
 * ${table.comment}
 * @author ESFlute (using FreeGen)
 */
public class BsParkSea extends EsAbstractEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final long serialVersionUID = 1L;
    protected static final Class<?> suppressUnusedImportLocalDateTime = LocalDateTime.class;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** code */
    protected String code;

    /** createdTime */
    protected Long createdTime;

    /** updatedTime */
    protected Long updatedTime;

    /** defaultDate */
    protected LocalDateTime defaultDate;

    /** formatDate */
    protected LocalDate formatDate;

    /** formatDateTime */
    protected LocalDateTime formatDateTime;

    /** formatTime */
    protected LocalTime formatTime;

    /** formatDateOptionalTime */
    protected LocalDateTime formatDateOptionalTime;

    // [Referrers] *comment only

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    @Override
    public ParkSeaDbm asDBMeta() {
        return ParkSeaDbm.getInstance();
    }

    @Override
    public String asTableDbName() {
        return "park_sea";
    }

    // ===================================================================================
    //                                                                              Source
    //                                                                              ======
    @Override
    public Map<String, Object> toSource() {
        Map<String, Object> sourceMap = new HashMap<>();
        if (code != null) {
            sourceMap.put("code", code);
        }
        if (createdTime != null) {
            sourceMap.put("createdTime", createdTime);
        }
        if (updatedTime != null) {
            sourceMap.put("updatedTime", updatedTime);
        }
        if (defaultDate != null) {
            sourceMap.put("defaultDate", defaultDate);
        }
        if (formatDate != null) {
            sourceMap.put("formatDate", formatDate);
        }
        if (formatDateTime != null) {
            sourceMap.put("formatDateTime", formatDateTime);
        }
        if (formatTime != null) {
            sourceMap.put("formatTime", formatTime);
        }
        if (formatDateOptionalTime != null) {
            sourceMap.put("formatDateOptionalTime", formatDateOptionalTime);
        }
        return sourceMap;
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(code);
        sb.append(dm).append(createdTime);
        sb.append(dm).append(updatedTime);
        sb.append(dm).append(defaultDate);
        sb.append(dm).append(formatDate);
        sb.append(dm).append(formatDateTime);
        sb.append(dm).append(formatTime);
        sb.append(dm).append(formatDateOptionalTime);
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public String getCode() {
        checkSpecifiedProperty("code");
        return convertEmptyToNull(code);
    }

    public void setCode(String value) {
        registerModifiedProperty("code");
        this.code = value;
    }

    public Long getCreatedTime() {
        checkSpecifiedProperty("createdTime");
        return createdTime;
    }

    public void setCreatedTime(Long value) {
        registerModifiedProperty("createdTime");
        this.createdTime = value;
    }

    public Long getUpdatedTime() {
        checkSpecifiedProperty("updatedTime");
        return updatedTime;
    }

    public void setUpdatedTime(Long value) {
        registerModifiedProperty("updatedTime");
        this.updatedTime = value;
    }

    public LocalDateTime getDefaultDate() {
        checkSpecifiedProperty("defaultDate");
        return defaultDate;
    }

    public void setDefaultDate(LocalDateTime value) {
        registerModifiedProperty("defaultDate");
        this.defaultDate = value;
    }

    public LocalDate getFormatDate() {
        checkSpecifiedProperty("formatDate");
        return formatDate;
    }

    public void setFormatDate(LocalDate value) {
        registerModifiedProperty("formatDate");
        this.formatDate = value;
    }

    public LocalDateTime getFormatDateTime() {
        checkSpecifiedProperty("formatDateTime");
        return formatDateTime;
    }

    public void setFormatDateTime(LocalDateTime value) {
        registerModifiedProperty("formatDateTime");
        this.formatDateTime = value;
    }

    public LocalTime getFormatTime() {
        checkSpecifiedProperty("formatTime");
        return formatTime;
    }

    public void setFormatTime(LocalTime value) {
        registerModifiedProperty("formatTime");
        this.formatTime = value;
    }

    public LocalDateTime getFormatDateOptionalTime() {
        checkSpecifiedProperty("formatDateOptionalTime");
        return formatDateOptionalTime;
    }

    public void setFormatDateOptionalTime(LocalDateTime value) {
        registerModifiedProperty("formatDateOptionalTime");
        this.formatDateOptionalTime = value;
    }
}
