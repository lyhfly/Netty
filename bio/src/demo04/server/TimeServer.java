package demo04.server;

/**
 * @ClassName server
 * @Description TODO
 * @Author liyuhong
 * @Date 2019/8/12 19:49
 **/
public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        if(args!=null && args.length >0){
            try{
                port = Integer.valueOf(args[0]);
            }catch(NumberFormatException e){
                //采用默认值
            }
        }
        AsyncTimeServerHandler timeServerHandler = new AsyncTimeServerHandler(port);
        new Thread(timeServerHandler,"AIO-AsyncTimeServerHandler-001").start();
    }
}
