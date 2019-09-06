package demo03.server;

/**
 * @ClassName TimeServer
 * @Description TODO
 * @Author liyuhong
 * @Date 2019/8/12 10:40
 **/
public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        if(args != null && args.length >0){
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                //遗异常就用默认值
            }
        }
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer,"NIO-MultiplexerTimeServer-001").start();
    }
}
