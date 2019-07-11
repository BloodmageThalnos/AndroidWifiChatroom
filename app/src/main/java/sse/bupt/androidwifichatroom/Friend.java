package sse.bupt.androidwifichatroom;

import android.content.Context;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lang22 on 2019/7/10.
 */

public class Friend implements Serializable {
    private String name;
    private List<MsgQ> msgList;
    private String ip;

    public Friend(String name,ImageView tx){
        this.name = name;
    }
    public Friend(String name){ this.name = name;}

    public String getName(){
        return name;
    }

    public void setName(String newName){
        name = newName;
    }

    public List<MsgQ> getMsgList(){ return msgList; }

    public void setMsgList(List<MsgQ> newMsgList){ msgList = newMsgList; }

    private String Id = name+System.currentTimeMillis();

    public String getId(){ return Id; }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
