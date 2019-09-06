package demo10.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @ClassName WebSocketServerHandler
 * @Description TODO
 * @Author liyuhong
 * @Date 2019/9/3 20:35
 **/
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger logger = Logger.getLogger(WebSocketServerHandler.class.getName());
    private WebSocketServerHandshaker handshaker;

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FullHttpRequest){//传统的HTTP接入
            System.out.println("request message body:"+msg);
            handleHttpRequest(ctx,(FullHttpRequest) msg);
        }else if(msg instanceof WebSocketFrame){//websocket接入
            System.out.println("server message body:"+msg);
            handleWebSocketFrame(ctx,(WebSocketFrame)msg);
        }
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        //如果HTTP解码失败，返回http异常
        if(!req.getDecoderResult().isSuccess()
                ||(!"websocket".equals(req.headers().get("Upgrade")))){
            sendHttpResponse(ctx,req,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        //构造握手相应返回，本机测试
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket",null,false);
        handshaker = wsFactory.newHandshaker(req);
        if(handshaker == null){
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        }else{
            handshaker.handshake(ctx.channel(),req);
        }
    }
    private void handleWebSocketFrame(ChannelHandlerContext ctx,WebSocketFrame frame){
        //判断是否关闭链路的指令
        if(frame instanceof CloseWebSocketFrame){
            handshaker.close(ctx.channel(),((CloseWebSocketFrame) frame).retain());
            return;
        }
        //判断是否是Ping的消息
        if(frame instanceof PingWebSocketFrame){
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        //本例程仅支持文本消息，不支持二进制消息
        if(!(frame instanceof TextWebSocketFrame)){
            throw new UnsupportedOperationException(String.format("%s frame type not supported",frame.getClass().getName()));
        }
        //返回应答消息
        String request = ((TextWebSocketFrame) frame).text();
        if(logger.isLoggable(Level.FINE)){
            logger.fine(String.format("%s received %s",ctx.channel(),request));
        }
        String respMsg = request+" ,欢迎使用Netty WebSocket服务，现在时刻："+ new Date().toString();
        logger.info(respMsg+":"+respMsg.length());
        ctx.channel().write(new TextWebSocketFrame(respMsg));
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx,FullHttpRequest req,FullHttpResponse resp){
        //返回应答给客户端
        if(resp.getStatus().code() != 200){
            ByteBuf buf = Unpooled.copiedBuffer(resp.getStatus().toString(), CharsetUtil.UTF_8);
            resp.content().writeBytes(buf);
            buf.release();
            HttpHeaders.setContentLength(resp,resp.content().readableBytes());
        }
        //如果是非keep-Alive，关闭连接
        ChannelFuture f =ctx.channel().writeAndFlush(resp);
        if(!HttpHeaders.isKeepAlive(req) || resp.getStatus().code()!= 200){
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
