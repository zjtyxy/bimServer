package com.ciat.bim.server.utils;

import com.ciat.bim.rule.engine.action.TbCreateAlarmNode;
import com.ciat.bim.rule.engine.filter.TbMsgTypeSwitchNode;
import com.ciat.bim.rule.engine.profile.TbDeviceProfileNode;
import com.ciat.bim.rule.engine.telemetry.TbMsgAttributesNode;
import com.ciat.bim.rule.engine.telemetry.TbMsgTimeseriesNode;

import java.util.HashMap;
import java.util.Map;

public class RuleNodeTypeUtils {
    private static Map<String,Class>  typeClassMap = new HashMap<>();
    static {
        typeClassMap.put(TbDeviceProfileNode.class.getSimpleName(),TbDeviceProfileNode.class);
        typeClassMap.put(TbMsgAttributesNode.class.getSimpleName(),TbMsgAttributesNode.class);
        typeClassMap.put(TbMsgTimeseriesNode.class.getSimpleName(),TbMsgTimeseriesNode.class);
        typeClassMap.put(TbMsgTypeSwitchNode.class.getSimpleName(),TbMsgTypeSwitchNode.class);
        typeClassMap.put(TbCreateAlarmNode.class.getSimpleName(),TbCreateAlarmNode.class);
    }

    public static Class getNodeType(String type)
    {
        return  typeClassMap.get(type);
    }

}
