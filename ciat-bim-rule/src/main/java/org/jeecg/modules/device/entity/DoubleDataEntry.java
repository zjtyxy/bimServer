package org.jeecg.modules.device.entity;

public class DoubleDataEntry extends AttributeKv{
    public  DoubleDataEntry(String key ,Double value)
    {
        this.setAttributeType(DataType.DOUBLE);
        this.setAttributeKey(key);
        this.setDoubleValue(value);
    }

}
