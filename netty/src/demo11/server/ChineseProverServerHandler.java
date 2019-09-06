package demo11.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @ClassName ChineseProverServerHandler
 * @Description TODO
 * @Author liyuhong
 * @Date 2019/9/4 21:17
 **/
public class ChineseProverServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    //谚语列表
    private static final String[] DICTIONARY = {"只要功夫深,铁棒磨成针","旧时王谢堂前燕,飞入寻常百姓家"
            ,"洛阳亲友如想问,一片冰心在玉壶","一寸光阴一寸金,存进难买寸光阴",
            "老骥伏枥,志在千里。烈士暮年,壮心不已."};

    private String nextQuote(){
        int quoteId = ThreadLocalRandom.current().nextInt(DICTIONARY.length);
        return DICTIONARY[quoteId];
    }
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        String req = datagramPacket.content().toString(CharsetUtil.UTF_8);
        System.out.println(req);
        if("谚语字典查询?".equals(req)){
            channelHandlerContext.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("谚语查询结果:"+nextQuote(),CharsetUtil.UTF_8),datagramPacket.sender()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}
