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
import org.docksidestage.esflute.maihama.bsentity.dbmeta.ProductDbm;

/**
 * ${table.comment}
 * @author ESFlute (using FreeGen)
 */
public class BsProduct extends EsAbstractEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final long serialVersionUID = 1L;
    protected static final Class<?> suppressUnusedImportLocalDateTime = LocalDateTime.class;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** category_code */
    protected String categoryCode;

    /** description */
    protected String description;

    /** handle_code */
    protected String handleCode;

    /** name */
    protected String name;

    /** register_datetime */
    protected LocalDateTime registerDatetime;

    /** register_user */
    protected String registerUser;

    /** regular_price */
    protected Integer regularPrice;

    /** status */
    protected String status;

    /** update_datetime */
    protected LocalDateTime updateDatetime;

    /** update_user */
    protected String updateUser;

    // [Referrers] *comment only

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    @Override
    public ProductDbm asDBMeta() {
        return ProductDbm.getInstance();
    }

    @Override
    public String asTableDbName() {
        return "product";
    }

    // ===================================================================================
    //                                                                              Source
    //                                                                              ======
    @Override
    public Map<String, Object> toSource() {
        Map<String, Object> sourceMap = new HashMap<>();
        if (categoryCode != null) {
            sourceMap.put("category_code", categoryCode);
        }
        if (description != null) {
            sourceMap.put("description", description);
        }
        if (handleCode != null) {
            sourceMap.put("handle_code", handleCode);
        }
        if (name != null) {
            sourceMap.put("name", name);
        }
        if (registerDatetime != null) {
            sourceMap.put("register_datetime", registerDatetime);
        }
        if (registerUser != null) {
            sourceMap.put("register_user", registerUser);
        }
        if (regularPrice != null) {
            sourceMap.put("regular_price", regularPrice);
        }
        if (status != null) {
            sourceMap.put("status", status);
        }
        if (updateDatetime != null) {
            sourceMap.put("update_datetime", updateDatetime);
        }
        if (updateUser != null) {
            sourceMap.put("update_user", updateUser);
        }
        return sourceMap;
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(categoryCode);
        sb.append(dm).append(description);
        sb.append(dm).append(handleCode);
        sb.append(dm).append(name);
        sb.append(dm).append(registerDatetime);
        sb.append(dm).append(registerUser);
        sb.append(dm).append(regularPrice);
        sb.append(dm).append(status);
        sb.append(dm).append(updateDatetime);
        sb.append(dm).append(updateUser);
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public String getCategoryCode() {
        checkSpecifiedProperty("categoryCode");
        return convertEmptyToNull(categoryCode);
    }

    public void setCategoryCode(String value) {
        registerModifiedProperty("categoryCode");
        this.categoryCode = value;
    }

    public String getDescription() {
        checkSpecifiedProperty("description");
        return convertEmptyToNull(description);
    }

    public void setDescription(String value) {
        registerModifiedProperty("description");
        this.description = value;
    }

    public String getHandleCode() {
        checkSpecifiedProperty("handleCode");
        return convertEmptyToNull(handleCode);
    }

    public void setHandleCode(String value) {
        registerModifiedProperty("handleCode");
        this.handleCode = value;
    }

    public String getName() {
        checkSpecifiedProperty("name");
        return convertEmptyToNull(name);
    }

    public void setName(String value) {
        registerModifiedProperty("name");
        this.name = value;
    }

    public LocalDateTime getRegisterDatetime() {
        checkSpecifiedProperty("registerDatetime");
        return registerDatetime;
    }

    public void setRegisterDatetime(LocalDateTime value) {
        registerModifiedProperty("registerDatetime");
        this.registerDatetime = value;
    }

    public String getRegisterUser() {
        checkSpecifiedProperty("registerUser");
        return convertEmptyToNull(registerUser);
    }

    public void setRegisterUser(String value) {
        registerModifiedProperty("registerUser");
        this.registerUser = value;
    }

    public Integer getRegularPrice() {
        checkSpecifiedProperty("regularPrice");
        return regularPrice;
    }

    public void setRegularPrice(Integer value) {
        registerModifiedProperty("regularPrice");
        this.regularPrice = value;
    }

    public String getStatus() {
        checkSpecifiedProperty("status");
        return convertEmptyToNull(status);
    }

    public void setStatus(String value) {
        registerModifiedProperty("status");
        this.status = value;
    }

    public LocalDateTime getUpdateDatetime() {
        checkSpecifiedProperty("updateDatetime");
        return updateDatetime;
    }

    public void setUpdateDatetime(LocalDateTime value) {
        registerModifiedProperty("updateDatetime");
        this.updateDatetime = value;
    }

    public String getUpdateUser() {
        checkSpecifiedProperty("updateUser");
        return convertEmptyToNull(updateUser);
    }

    public void setUpdateUser(String value) {
        registerModifiedProperty("updateUser");
        this.updateUser = value;
    }
}
