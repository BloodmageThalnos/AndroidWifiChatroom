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

//    static class TxView extends android.support.v7.widget.AppCompatImageView implements Serializable{
//
//        public TxView(Context context) {
//            super(context);
//        }
//    }
//    private TxView tx;
    private List<MsgQ> msgList;

    public Friend(String name,ImageView tx){
        this.name = name;
//        this.tx = (TxView) tx;
    }

    public String getName(){
        return name;
    }

//    public ImageView getTx(){
//        return tx;
//    }

    public void setName(String newName){
        name = newName;
    }

//    public void setTx(ImageView newTx){
//        tx = (TxView)newTx;
//    }

    public List<MsgQ> getMsgList(){ return msgList; }

    public void setMsgList(List<MsgQ> newMsgList){ msgList = newMsgList; }

    private String Id = name+System.currentTimeMillis();

    public String getId(){ return Id; }
}
