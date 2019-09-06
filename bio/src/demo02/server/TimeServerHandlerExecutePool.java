package demo02.server;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName TimeServerHandlerExecutePool
 * @Description TODO
 * @Author liyuhong
 * @Date 2019/8/10 15:41
 **/
public class TimeServerHandlerExecutePool {
    private ExecutorService executor;

    public TimeServerHandlerExecutePool(int maxPoolSize,int queueSize){
        executor = new ThreadPoolExecutor(maxPoolSize,maxPoolSize,120L,
                TimeUnit.SECONDS,new ArrayBlockingQueue<>(queueSize));
    }

    public void execute(Runnable task){
        executor.execute(task);
    }

}
