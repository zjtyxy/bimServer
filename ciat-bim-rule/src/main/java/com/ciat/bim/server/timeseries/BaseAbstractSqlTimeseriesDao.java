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

import com.ciat.bim.server.dao.sql.JpaAbstractDaoListeningExecutorService;
import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.jeecg.modules.device.entity.TsKv;
import org.springframework.beans.factory.annotation.Autowired;


import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Slf4j
public abstract class BaseAbstractSqlTimeseriesDao extends JpaAbstractDaoListeningExecutorService {

    private final ConcurrentMap<String, Integer> tsKvDictionaryMap = new ConcurrentHashMap<>();

    protected static final ReentrantLock tsCreationLock = new ReentrantLock();

//    @Autowired
//    protected TsKvDictionaryRepository dictionaryRepository;

    protected Integer getOrSaveKeyId(String strKey) {
        Integer keyId = tsKvDictionaryMap.get(strKey);
//        if (keyId == null) {
//            Optional<TsKvDictionary> tsKvDictionaryOptional;
//            tsKvDictionaryOptional = dictionaryRepository.findById(new TsKvDictionaryCompositeKey(strKey));
//            if (!tsKvDictionaryOptional.isPresent()) {
//                tsCreationLock.lock();
//                try {
//                    tsKvDictionaryOptional = dictionaryRepository.findById(new TsKvDictionaryCompositeKey(strKey));
//                    if (!tsKvDictionaryOptional.isPresent()) {
//                        TsKvDictionary tsKvDictionary = new TsKvDictionary();
//                        tsKvDictionary.setKey(strKey);
//                        try {
//                            TsKvDictionary saved = dictionaryRepository.save(tsKvDictionary);
//                            tsKvDictionaryMap.put(saved.getKey(), saved.getKeyId());
//                            keyId = saved.getKeyId();
//                        } catch (ConstraintViolationException e) {
//                            tsKvDictionaryOptional = dictionaryRepository.findById(new TsKvDictionaryCompositeKey(strKey));
//                            TsKvDictionary dictionary = tsKvDictionaryOptional.orElseThrow(() -> new RuntimeException("Failed to get TsKvDictionary entity from DB!"));
//                            tsKvDictionaryMap.put(dictionary.getKey(), dictionary.getKeyId());
//                            keyId = dictionary.getKeyId();
//                        }
//                    } else {
//                        keyId = tsKvDictionaryOptional.get().getKeyId();
//                    }
//                } finally {
//                    tsCreationLock.unlock();
//                }
//            } else {
//                keyId = tsKvDictionaryOptional.get().getKeyId();
//                tsKvDictionaryMap.put(strKey, keyId);
//            }
//        }
        return keyId;
    }

    protected ListenableFuture<List<TsKv>> getTskvEntriesFuture(ListenableFuture<List<Optional<TsKv>>> future) {
        return Futures.transform(future, new Function<List<Optional<TsKv>>, List<TsKv>>() {
            @Nullable
            @Override
            public List<TsKv> apply(@Nullable List<Optional<TsKv>> results) {
                if (results == null || results.isEmpty()) {
                    return null;
                }
                return results.stream()
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());
            }
        }, service);
    }
}
