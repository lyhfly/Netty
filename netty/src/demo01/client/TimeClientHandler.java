package demo01.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

/**
 * @ClassName TimeClientHandler
 * @Description TODO
 * @Author liyuhong
 * @Date 2019/8/16 15:33
 **/
public class TimeClientHandler extends ChannelHandlerAdapter {
    private static  final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());
    private final ByteBuf firstMessage;

    public TimeClientHandler(){
        byte[] req = "QUERY TIME ORDER".getBytes();
        firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //释放资源
        logger.warning("Unexpected execption from downstream :" + cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       ByteBuf buf = (ByteBuf)msg;
       byte[] resp = new byte[buf.readableBytes()];
       buf.readBytes(resp);
       String body = new String(resp,"UTF-8");
       System.out.println("Now is :" + body);
    }
}
