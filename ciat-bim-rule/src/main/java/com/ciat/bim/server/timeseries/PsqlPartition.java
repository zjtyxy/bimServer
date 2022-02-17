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
package com.ciat.bim.server.timeseries;

import lombok.Data;

@Data
public class PsqlPartition {

    private static final String TABLE_REGEX = "ts_kv_";

    private long start;
    private long end;
    private String partitionDate;
    private String query;

    public PsqlPartition(long start, long end, String partitionDate) {
        this.start = start;
        this.end = end;
        this.partitionDate = partitionDate;
        this.query = createStatement(start, end, partitionDate);
    }

    private String createStatement(long start, long end, String partitionDate) {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_REGEX + partitionDate + " PARTITION OF ts_kv FOR VALUES FROM (" + start + ") TO (" + end + ")";
    }
}
