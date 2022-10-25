package com.ruoyi.datanetty.protocal;


public class BinlogMessage {

    private OpType type;

    private int contentLength;

    private Object content;

    public static BinlogMessage PingMessage() {
        BinlogMessage m = new BinlogMessage();
        m.setType(OpType.PING);
        return m;
    }

    public static BinlogMessage PongMessage() {
        BinlogMessage m = new BinlogMessage();
        m.setType(OpType.PONG);
        return m;
    }

    public static BinlogMessage StringMessage(String txt) {
        BinlogMessage m = new BinlogMessage();
        m.setType(OpType.DATA);
        m.setContent(txt);
        return m;
    }

	public OpType getType() {
		return type;
	}

	public void setType(OpType type) {
		this.type = type;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
    
    
    

}
