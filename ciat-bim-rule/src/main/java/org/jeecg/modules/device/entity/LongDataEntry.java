package org.jeecg.modules.device.entity;

public class LongDataEntry extends TsKv{
   public  LongDataEntry(String key ,Long value)
   {
      this.setDataType(DataType.LONG);
      this.setEntityKey(key);
      this.setLongValue(value);
   }

}
