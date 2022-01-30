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
package com.ciat.bim.transport.ssl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;


import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;

/**
 * @author Valerii Sosliuk
 */
@Slf4j
public class SslUtil {

    private SslUtil() {
    }

    public static String getCertificateString(Certificate cert)
            throws CertificateEncodingException {
        return EncryptionUtil.trimNewLines(Base64Utils.encodeToString(cert.getEncoded()));
    }
}
