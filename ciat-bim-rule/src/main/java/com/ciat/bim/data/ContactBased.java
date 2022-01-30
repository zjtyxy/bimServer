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
package com.ciat.bim.data;

import com.ciat.bim.data.id.HasName;
import com.ciat.bim.data.id.UUIDBased;
import lombok.Data;



@Data
public abstract class ContactBased<I extends UUIDBased> extends SearchTextBasedWithAdditionalInfo<I> implements HasName {

    private static final long serialVersionUID = 5047448057830660988L;


    protected String country;

    protected String state;

    protected String city;

    protected String address;

    protected String address2;


    protected String zip;


    protected String phone;

    protected String email;

    public ContactBased() {
        super();
    }

    public ContactBased(I id) {
        super(id);
    }

    public ContactBased(ContactBased<I> contact) {
        super(contact);
        this.country = contact.getCountry();
        this.state = contact.getState();
        this.city = contact.getCity();
        this.address = contact.getAddress();
        this.address2 = contact.getAddress2();
        this.zip = contact.getZip();
        this.phone = contact.getPhone();
        this.email = contact.getEmail();
    }



}
