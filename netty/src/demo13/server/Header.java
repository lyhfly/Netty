package demo13.server;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Header
 * @Description TODO
 * @Author liyuhong
 * @Date 2019/9/5 20:39
 **/
public final class Header {
    /**
     * Netty消息的校验码
     * 0xABEF:固定值,表明该消息是Netty协议消息,2个字节
     * 主版本号: 1~255,1个字节
     * 次版本号: 1~255,1个字节
    */
    private int crcCode = 0xabef0101;

    /**
     * 消息长度,整个消息,包括消息头和消息体
     */
    private int length;

    /**
     * 集群节点内全局唯一,由会话ID生成器生成
     */
    private long sessionID;

    /**
     * 消息类型
     * 0:业务请求消息
     * 1:业务响应消息
     * 2:业务ONE WAY消息(即是请求又是响应消息)
     * 3:握手请求消息
     * 4:握手应答消息
     * 5:心跳请求消息
     * 6:心跳应答消息
     */
    private byte type;

    /**
     * 消息优先级 0~255
     */
    private byte priority;

    /**
     * 变长
     * 可选字段,用于扩展消息头
     */
    private Map<String,Object> attachment = new HashMap<String,Object>();

    public final int getCrcCode() {
        return crcCode;
    }

    public final void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public final int getLength() {
        return length;
    }

    public final void setLength(int length) {
        this.length = length;
    }

    public final long getSessionID() {
        return sessionID;
    }

    public final void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public final byte getType() {
        return type;
    }

    public final void setType(byte type) {
        this.type = type;
    }

    public final byte getPriority() {
        return priority;
    }

    public final void setPriority(byte priority) {
        this.priority = priority;
    }

    public final Map<String, Object> getAttachment() {
        return attachment;
    }

    public final void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Header[" +
                "crcCode=" + crcCode +
                ", length=" + length +
                ", sessionID=" + sessionID +
                ", type=" + type +
                ", priority=" + priority +
                ", attachment=" + attachment +
                ']';
    }
}
