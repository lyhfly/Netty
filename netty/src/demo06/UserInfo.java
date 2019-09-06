package demo06;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @ClassName UserInfo
 * @Description TODO
 * @Author liyuhong
 * @Date 2019/8/29 11:29
 **/
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 7689900180844916032L;
    private String userName;
    private int userID;
    public UserInfo buildUserName(String userName){
        this.userName = userName;
        return this;
    }
    public UserInfo buildUserID(int userID){
        this.userID = userID;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public byte[] codeC(ByteBuffer buffer){
        buffer.clear();
        byte[] value = this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userID);
        buffer.flip();
        value = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }
}
