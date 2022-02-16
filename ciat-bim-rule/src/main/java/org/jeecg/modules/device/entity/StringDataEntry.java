package org.jeecg.modules.device.entity;

public class StringDataEntry extends TsKv {
    public StringDataEntry(String key, String value) {
        this.setDataType(DataType.STRING);
        this.setEntityKey(key);
        this.setStrValue(value);
    }
}
