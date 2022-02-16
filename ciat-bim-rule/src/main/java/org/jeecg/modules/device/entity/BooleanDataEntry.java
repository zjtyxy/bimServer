package org.jeecg.modules.device.entity;

public class BooleanDataEntry extends TsKv{
    public BooleanDataEntry(String key,Boolean value)
    {
        this.setDataType(DataType.BOOLEAN);
        this.setEntityKey(key);
        this.setBooleanValue(value?"Y":"N");
    }

}
