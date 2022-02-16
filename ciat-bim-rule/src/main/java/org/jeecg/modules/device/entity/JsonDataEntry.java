package org.jeecg.modules.device.entity;

public class JsonDataEntry extends TsKv{
    public JsonDataEntry(String key,String value)
    {
        this.setDataType(DataType.JSON);
        this.setEntityKey(key);
        this.setJsonValue(value);
    }
}
