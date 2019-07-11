package sse.bupt.androidwifichatroom;

/**
 * Created by Administrator on 7/7/2019.
 */

public class MsgP {
    public int type;
    public String contenT;
    static final int MESSAGE_BROADCAST = 0;
    static final int MESSAGE_BROADCAST_MESSAGE = 1;
    static final int MESSAGE_BROADCAST_INIT = 2;
    static final int MESSAGE_BROADCAST_REPLY = 3;
    static final int MESSAGE_PRIVATE = 4;

    static public MsgP fromString(String str) {
        MsgP it = new MsgP();
        it.type = Integer.parseInt(str.substring(0,1));
        it.contenT = str.substring(1);
        return it;
    }
    public String toString() {
        String sType = "" + type;
        return sType + contenT;
    }
}
