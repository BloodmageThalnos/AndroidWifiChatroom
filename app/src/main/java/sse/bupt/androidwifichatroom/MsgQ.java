package sse.bupt.androidwifichatroom;

import android.widget.ImageView;

/**
 * Created by lang22 on 2019/7/8.
 */

public class MsgQ {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND = 1;
    private String content;
    private int type;

    public MsgQ(String content, int type){
        this.content = content;
        this.type = type;
    }

    public MsgQ(ImageView tx,String content, int type){
//        this.TX = tx;
        this.content = content;
        this.type = type;
    }
    public String getContent(){
        return this.content;
    }

    public int getType(){
        return this.type;
    }
//
//    private ImageView TX;
//
//    public ImageView getMyTX(){ return TX;}
//
//    public void setMyTX(ImageView newTX){ TX = newTX;}

}
