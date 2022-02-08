package org.jeecg.modules.device.entity;

public class JsonDataEntry extends AttributeKv{
    public JsonDataEntry(String key,String value)
    {
        this.setAttributeType(DataType.JSON);
        this.setAttributeKey(key);
        this.setJsonValue(value);
    }
}
