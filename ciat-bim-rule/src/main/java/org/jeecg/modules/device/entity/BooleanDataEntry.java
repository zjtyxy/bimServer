package org.jeecg.modules.device.entity;

public class BooleanDataEntry extends AttributeKv{
    public BooleanDataEntry(String key,Boolean value)
    {
        this.setAttributeType(DataType.BOOLEAN);
        this.setAttributeKey(key);
        this.setBooleanValue(value?1:0);
    }

}
