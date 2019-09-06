package demo06;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * @ClassName TestUserInfo
 * @Description TODO
 * @Author liyuhong
 * @Date 2019/8/29 11:42
 **/
public class TestUserInfo {
    public static void main(String[] args) throws IOException {
        testTime();
        testSpace();
    }
    public static void testSpace() throws IOException{
        UserInfo info = new UserInfo();
        info.buildUserID(100).buildUserName("welcome to netty");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(info);
        os.flush();
        os.close();
        byte[] b = bos.toByteArray();
        System.out.println("The jdk serializable length is :" + b.length);
        bos.close();
        System.out.println("-------------------");
        System.out.println("The byte array serializaable length is:"+info.codeC(ByteBuffer.allocate(1024)).length);
    }
    public static void testTime() throws IOException{
        UserInfo info = new UserInfo();
        info.buildUserID(100).buildUserName("welcome to netty");
        int loop = 1000000;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        long startTime = System.currentTimeMillis();
        for(int i = 0;i< loop ; i++){
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(info);
            os.flush();
            os.close();
            byte[] b = bos.toByteArray();
            bos.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("The jdk serializable cost time is :"+(endTime-startTime)+"ms");
        System.out.println("------------------------");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        startTime = System.currentTimeMillis();
        for(int i=0;i<loop;i++){
            byte[] b = info.codeC(buffer);
        }
        endTime = System.currentTimeMillis();
        System.out.println("The byte array serializable cost time is :" +(endTime-startTime)+"ms");
    }
}
