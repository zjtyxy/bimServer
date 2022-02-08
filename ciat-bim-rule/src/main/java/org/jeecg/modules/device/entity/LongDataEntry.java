package org.jeecg.modules.device.entity;

public class LongDataEntry extends AttributeKv{
   public  LongDataEntry(String key ,Long value)
   {
      this.setAttributeType(DataType.LONG);
      this.setAttributeKey(key);
      this.setLongValue(value);
   }

}
