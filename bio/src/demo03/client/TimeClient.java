package demo03.client;

/**
 * @ClassName TimeClient
 * @Description TODO
 * @Author liyuhong
 * @Date 2019/8/12 16:52
 **/
public class TimeClient {
    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length>0){
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                //采用默认值
            }
        }
        new Thread(new TimeClientHandle("127.0.0.1",port),"TimeClient-001").start();
    }
}
