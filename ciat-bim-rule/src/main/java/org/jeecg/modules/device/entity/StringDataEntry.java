package org.jeecg.modules.device.entity;

public class StringDataEntry extends AttributeKv {
    public StringDataEntry(String key, String value) {
        this.setAttributeType(DataType.STRING);
        this.setAttributeKey(key);
        this.setStrValue(value);
    }
}
