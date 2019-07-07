package sse.bupt.androidwifichatroom;

/**
 * Created by Administrator on 7/7/2019.
 */

public class Message {
    public int type;
    public String _;
    static final int MESSAGE_BROADCAST = 0;

    static public Message fromString(String str) {
        Message it = new Message();
        it.type = Integer.parseInt(str.substring(0,1));
        it._ = str.substring(1);
        return it;
    }
    public String toString() {
        String sType = "" + type;
        return sType + _;
    }
}
