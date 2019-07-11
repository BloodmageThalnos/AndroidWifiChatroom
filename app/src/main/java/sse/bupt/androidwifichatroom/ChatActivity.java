package sse.bupt.androidwifichatroom;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JennieFucker and Lang22 and Littledva and Aodacat.
 *
 * Main Activity
 *   The access of the main program.
 *   Creates chatService.
 *   Creates and maintains mHandler.
 *   Creates and maintains the onclick functions of mButton.
 */
public class ChatActivity extends AppCompatActivity {

    private List<MsgQ> msgList = new ArrayList<>();
    private EditText inputText;
    private TextView send;
    private RecyclerView msgRecycleView;
    private Button btnFacetime;
    private TextView FriendName;
    private MsgAdapter msgAdapter;
    private Handler mHandler;
    private FacetimeActivity facetimeActivity;
    public static ChatService chatService;
    public static final int MESSAGE_LOG = 0x400 + 1;
    public static final int MESSAGE_NEWMSG = 0x400 + 2;
    public boolean show_log = true;

    public void log(String string) {
        //MsgQ message = mHandler.obtainMessage(MESSAGE_LOG, string);
        //message.sendToTarget();
        Log.d("日志：", string);
    }

    public void showMsg(final MsgQ msg) {
        Message message = mHandler.obtainMessage(MESSAGE_NEWMSG, msg);
        message.sendToTarget();
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this,
                        (String[]) permissions.toArray(new String[0]),
                        1437);
                return false;
            }
        }

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);
        checkPermission();
        FriendName = (TextView) findViewById(R.id.opposite_name);
        inputText = (EditText) findViewById(R.id.inputText);
        msgRecycleView = (RecyclerView)findViewById(R.id.chat_window);
        LinearLayoutManager mlayoutManager = new LinearLayoutManager(this);
//        mlayoutManager.setStackFromEnd(true);
        //更新顶部名字栏UI
        Intent intent = this.getIntent();
        final Friend fInfo = (Friend) intent.getSerializableExtra("Info");
        FriendName.setText(fInfo.getName());

        //抓取聊天记录
        msgList = ChatRecords.getRecords(fInfo.getName());
//        Log.d("聊天记录",msgList.get(1).getContent());

        //
        msgRecycleView.setLayoutManager(mlayoutManager);
        msgAdapter= new MsgAdapter(msgList);
        msgRecycleView.setAdapter(msgAdapter);


        MsgQ m1 = new MsgQ("saoing",MsgQ.TYPE_RECEIVED);
        msgList.add(m1);

        btnFacetime = findViewById(R.id.facetime);
        btnFacetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, FacetimeActivity.class);
                startActivity(intent);
            }
        });


        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {

                switch(message.what){
                    case MESSAGE_LOG:
                        break;
                    case MESSAGE_NEWMSG:
                        MsgQ msg = (MsgQ) message.obj;
//                        msg.setMyTX(fInfo.getTx());

                        msgList.add(msg);
                        // TODO: 一会回来看看。
                        msgAdapter.notifyItemChanged(msgList.size()-1); //刷新消息
                        msgRecycleView.scrollToPosition(msgList.size()-1);      //定位到最后一行
                        break;
                }
            }
        };


        send = (TextView)findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if(!"".equals(content)){
                    MsgQ msg = new MsgQ(content, MsgQ.TYPE_SEND);
                    showMsg(msg);
                    inputText.setText("");//clear

                    chatService.sendMessage(content);
                }
            }
        });

        chatService = new ChatService(this);
        chatService.startListening();
        log("Program started.");

    }

}
