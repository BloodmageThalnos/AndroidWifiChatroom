package sse.bupt.androidwifichatroom;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Handler mHandler;
    public static final int MESSAGE_LOG = 0x400 + 1;

    public void log(String string) {
        if(mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message message) {
                    switch(message.what){
                        case MESSAGE_LOG:
                            EditText editText = (EditText)findViewById(R.id.editText3);
                            editText.append((String)message.obj+"\n");
                    }
                }
            };
        }
        Message message = mHandler.obtainMessage(MESSAGE_LOG, string);
        message.sendToTarget();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log("Program started.");

        // 选择创建房间（作为服务器）
        String roomName = "Littledva's room";
        ServerSocketHelper serverSocketHelper = new ServerSocketHelper(this);
        serverSocketHelper.startBroadcast(roomName);


    }
}
