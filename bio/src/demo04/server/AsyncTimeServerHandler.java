package demo04.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName AsyncTimeServerHandler
 * @Description TODO
 * @Author liyuhong
 * @Date 2019/8/12 19:54
 **/
public class AsyncTimeServerHandler implements Runnable {
    private Integer port;
    CountDownLatch latch;
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;
    public AsyncTimeServerHandler(Integer port){
        this.port = port;
        try {
            this.asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            this.asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("the time server is start in port :" + port);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        doAccept();
        try{
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    private void doAccept() {
        asynchronousServerSocketChannel.accept(this,new AcceptCompletionHandler());
    }
}
