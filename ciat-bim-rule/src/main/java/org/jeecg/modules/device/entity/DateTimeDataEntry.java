package org.jeecg.modules.device.entity;

public class DateTimeDataEntry extends TsKv{
   public DateTimeDataEntry(String key , Long value)
   {
      this.setDataType(DataType.DATETIME);
      this.setEntityKey(key);
      this.setLongValue(value);
   }

}
