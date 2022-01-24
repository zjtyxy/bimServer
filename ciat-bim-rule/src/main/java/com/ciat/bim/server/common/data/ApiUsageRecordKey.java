/**
 * Copyright © 2016-2021 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ciat.bim.server.common.data;

import lombok.Getter;

public enum ApiUsageRecordKey {

    TRANSPORT_MSG_COUNT(ApiFeature.TRANSPORT, "transportMsgCount", "transportMsgLimit"),
    TRANSPORT_DP_COUNT(ApiFeature.TRANSPORT, "transportDataPointsCount", "transportDataPointsLimit"),
    STORAGE_DP_COUNT(ApiFeature.DB, "storageDataPointsCount", "storageDataPointsLimit"),
    RE_EXEC_COUNT(ApiFeature.RE, "ruleEngineExecutionCount", "ruleEngineExecutionLimit"),
    JS_EXEC_COUNT(ApiFeature.JS, "jsExecutionCount", "jsExecutionLimit"),
    EMAIL_EXEC_COUNT(ApiFeature.EMAIL, "emailCount", "emailLimit"),
    SMS_EXEC_COUNT(ApiFeature.SMS, "smsCount", "smsLimit"),
    CREATED_ALARMS_COUNT(ApiFeature.ALARM, "createdAlarmsCount", "createdAlarmsLimit");

    private static final ApiUsageRecordKey[] JS_RECORD_KEYS = {JS_EXEC_COUNT};
    private static final ApiUsageRecordKey[] RE_RECORD_KEYS = {RE_EXEC_COUNT};
    private static final ApiUsageRecordKey[] DB_RECORD_KEYS = {STORAGE_DP_COUNT};
    private static final ApiUsageRecordKey[] TRANSPORT_RECORD_KEYS = {TRANSPORT_MSG_COUNT, TRANSPORT_DP_COUNT};
    private static final ApiUsageRecordKey[] EMAIL_RECORD_KEYS = {EMAIL_EXEC_COUNT};
    private static final ApiUsageRecordKey[] SMS_RECORD_KEYS = {SMS_EXEC_COUNT};
    private static final ApiUsageRecordKey[] ALARM_RECORD_KEYS = {CREATED_ALARMS_COUNT};

    @Getter
    private final ApiFeature apiFeature;
    @Getter
    private final String apiCountKey;
    @Getter
    private final String apiLimitKey;

    ApiUsageRecordKey(ApiFeature apiFeature, String apiCountKey, String apiLimitKey) {
        this.apiFeature = apiFeature;
        this.apiCountKey = apiCountKey;
        this.apiLimitKey = apiLimitKey;
    }

    public static ApiUsageRecordKey[] getKeys(ApiFeature feature) {
        switch (feature) {
            case TRANSPORT:
                return TRANSPORT_RECORD_KEYS;
            case DB:
                return DB_RECORD_KEYS;
            case RE:
                return RE_RECORD_KEYS;
            case JS:
                return JS_RECORD_KEYS;
            case EMAIL:
                return EMAIL_RECORD_KEYS;
            case SMS:
                return SMS_RECORD_KEYS;
            case ALARM:
                return ALARM_RECORD_KEYS;
            default:
                return new ApiUsageRecordKey[]{};
        }
    }

}
