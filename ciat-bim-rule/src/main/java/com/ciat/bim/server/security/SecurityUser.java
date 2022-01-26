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
package com.ciat.bim.server.security;



import com.ciat.bim.data.id.CustomerId;
import com.ciat.bim.data.id.TenantId;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SecurityUser {

    private static final long serialVersionUID = -797397440703066079L;

    public CustomerId getCustomerId() {
        return null;
    }

    public TenantId getTenantId() {
        return null;
    }

//    private Collection<GrantedAuthority> authorities;
//    private boolean enabled;
//    private UserPrincipal userPrincipal;
//
//    public SecurityUser() {
//        super();
//    }
//
//    public SecurityUser(UserId id) {
//        super(id);
//    }
//
//    public SecurityUser(User user, boolean enabled, UserPrincipal userPrincipal) {
//        super(user);
//        this.enabled = enabled;
//        this.userPrincipal = userPrincipal;
//    }
//
//    public Collection<GrantedAuthority> getAuthorities() {
//        if (authorities == null) {
//            authorities = Stream.of(SecurityUser.this.getAuthority())
//                    .map(authority -> new SimpleGrantedAuthority(authority.name()))
//                    .collect(Collectors.toList());
//        }
//        return authorities;
//    }
//
//    public boolean isEnabled() {
//        return enabled;
//    }
//
//    public UserPrincipal getUserPrincipal() {
//        return userPrincipal;
//    }
//
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }
//
//    public void setUserPrincipal(UserPrincipal userPrincipal) {
//        this.userPrincipal = userPrincipal;
//    }

}
