package sse.bupt.androidwifichatroom;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Handler mHandler;
    Button mButton;
    public static final int MESSAGE_LOG = 0x400 + 1;
    public static final int MESSAGE_NEWMSG = 0x400 + 2;
    public boolean showLog = true;

    public void log(String string) {
        Message message = mHandler.obtainMessage(MESSAGE_LOG, string);
        message.sendToTarget();
    }

    public void showMsg(String string) {
        Message message = mHandler.obtainMessage(MESSAGE_NEWMSG, string);
        message.sendToTarget();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                switch(message.what){
                    case MESSAGE_LOG:
                        if(showLog) {
                            EditText editText = (EditText) findViewById(R.id.editText);
                            editText.append("#LOG: " + (String) message.obj + "\n");
                        }
                        break;
                    case MESSAGE_NEWMSG:
                        EditText editText = (EditText) findViewById(R.id.editText);
                        editText.append((String) message.obj + "\n");
                        break;
                }
            }
        };

        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView mTextView3 = (TextView) findViewById(R.id.editText3);
                final String s = mTextView3.getText().toString();
                ServerSocketHelper ssh = new ServerSocketHelper(MainActivity.this);
                ssh.broadcastMessage(s);
            }
        });

        ClientHelper clientHelper = new ClientHelper(this);
        clientHelper.listenMessage();

        log("Program started.");

        // 选择创建房间（作为服务器）
        String roomName = "Littledva's room";
        ServerSocketHelper serverSocketHelper = new ServerSocketHelper(this);
        //serverSocketHelper.startBroadcast(roomName);


    }
}
