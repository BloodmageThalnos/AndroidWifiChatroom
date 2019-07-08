package sse.bupt.androidwifichatroom;

/**
 * Created by Administrator on 7/7/2019.
 */

public class MsgP {
    public int type;
    public String _;
    static final int MESSAGE_BROADCAST = 0;
    static final int MESSAGE_BROADCAST_MESSAGE = 1;

    static public MsgP fromString(String str) {
        MsgP it = new MsgP();
        it.type = Integer.parseInt(str.substring(0,1));
        it._ = str.substring(1);
        return it;
    }
    public String toString() {
        String sType = "" + type;
        return sType + _;
    }
}
