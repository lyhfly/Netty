package demo02.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName TimeServer
 * @Description TODO
 * @Author liyuhong
 * @Date 2019/8/10 15:19
 **/
public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = 8082;
        if(args != null && args.length >0){
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                //使用默认端口
            }
        }
        ServerSocket server = null;
        try{
            server = new ServerSocket(port);
            System.out.println("The time server is start in port:"+port);
            Socket socket = null;
            TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(5,10000);
            while (true){
                socket = server.accept();
                singleExecutor.execute(new TimeServerHandler(socket));
            }
        }finally {
            if(server != null){
                System.out.println("The time server close");
                server.close();
                server = null;
            }
        }
    }
}
