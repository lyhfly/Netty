package demo11.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;


/**
 * @ClassName ChineseProverClientHandler
 * @Description TODO
 * @Author liyuhong
 * @Date 2019/9/4 21:44
 **/
public class ChineseProverClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        String resp = datagramPacket.content().toString(CharsetUtil.UTF_8);
        if(resp.startsWith("谚语查询结果:")){
            System.out.println(resp);
            channelHandlerContext.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}
