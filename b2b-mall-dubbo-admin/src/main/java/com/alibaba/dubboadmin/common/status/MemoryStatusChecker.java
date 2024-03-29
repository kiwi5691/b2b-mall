/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubboadmin.common.status;

import com.alibaba.dubbo.common.status.Status;
import com.alibaba.dubbo.common.status.StatusChecker;

/**
 * MemoryStatus
 *
 */
public class MemoryStatusChecker implements StatusChecker {

    public Status check() {
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.freeMemory();
        long totalMemory = runtime.totalMemory();
        long maxMemory = runtime.maxMemory();
        boolean ok = (maxMemory - (totalMemory - freeMemory) > 2048); // Alarm when spare memory < 2M
        String msg = "Max:" + (maxMemory / 1024 / 1024) + "M, Total:"
                + (totalMemory / 1024 / 1024) + "M, Free:" + (freeMemory / 1024 / 1024)
                + "M, Use:" + ((totalMemory / 1024 / 1024) - (freeMemory / 1024 / 1024)) + "M";
        return new Status(ok ? Status.Level.OK : Status.Level.WARN, msg);
    }

}
