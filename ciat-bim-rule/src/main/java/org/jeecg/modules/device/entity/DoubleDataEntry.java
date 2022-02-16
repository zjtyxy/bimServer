package org.jeecg.modules.device.entity;

public class DoubleDataEntry extends TsKv{
    public  DoubleDataEntry(String key ,Double value)
    {
        this.setDataType(DataType.DOUBLE);
        this.setEntityKey(key);
        this.setDoubleValue(value);
    }

}
