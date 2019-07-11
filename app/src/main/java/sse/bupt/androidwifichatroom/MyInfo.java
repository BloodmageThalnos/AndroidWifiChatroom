package sse.bupt.androidwifichatroom;

import android.app.Application;
import android.widget.ImageView;

/**
 * Created by lang22 on 2019/7/11.
 */

public class MyInfo {

    private static String myName;

    public static String getMyName(){ return myName;}

    public static void setMyName(String newName){ myName = newName;}

    private static ImageView myTX;

    public static ImageView getMyTX(){ return myTX;}

    public static void setMyTX(ImageView newTX){ myTX = newTX;}
}
