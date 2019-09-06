package demo07.server;

import demo07.SubscribeReq;
import demo07.SubscribeResp;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName SubReqServerHandler
 * @Description TODO
 * @Author liyuhong
 * @Date 2019/8/29 20:53
 **/
public class SubReqServerHandler extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close(); //发生异常，关闭链路
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReq req = (SubscribeReq) msg;
        if("Liyuhong".equalsIgnoreCase(req.getUserName())){
            System.out.println("Server accpet client subscribe req : ["+req.toString()+"]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }

    private SubscribeResp resp(int subReqID) {
        SubscribeResp resp = new SubscribeResp();
        resp.setSubReqID(subReqID);
        resp.setRespCode(0);
        resp.setDesc("Netty book order succeed,3 days later,sent to the designated address");
        return resp;
    }
}
