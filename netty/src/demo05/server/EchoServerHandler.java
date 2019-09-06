package demo05.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName EchoServerHandler
 * @Description TODO
 * @Author liyuhong
 * @Date 2019/8/29 11:11
 **/
public class EchoServerHandler extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close(); //发生异常，关闭链路
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive client : ["+ msg +"]");
    }
}
