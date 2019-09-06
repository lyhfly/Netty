package demo13.server;

/**
 * @ClassName NettyMessage
 * @Description TODO
 * @Author liyuhong
 * @Date 2019/9/5 20:35
 **/
public final class NettyMessage {
    private Header header; //消息头
    private Object body; //消息体
    public final Header getHeader(){
        return this.header;
    }
    public final void setHeader(Header header){
        this.header = header;
    }

    public final Object getBody() {
        return body;
    }

    public final void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "NettyMessage [header="+header+"]";
    }
}
