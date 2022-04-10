package com.ciat.bim.server.queue.provider;

import com.ciat.bim.server.queue.TbQueueMsg;
import com.ciat.bim.server.queue.queue.TbQueueMsgHeaders;

public class CmdQueueMsg implements TbQueueMsg {
    @Override
    public String getKey() {
        return null;
    }

    @Override
    public TbQueueMsgHeaders getHeaders() {
        return null;
    }

    @Override
    public byte[] getData() {
        return new byte[0];
    }
}
