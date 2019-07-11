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
    private Button backToFather;
    private TextView FriendName;
    private MsgAdapter msgAdapter;
    private Handler mHandler;
    private FacetimeActivity facetimeActivity;
    public static final int MESSAGE_LOG = 0x400 + 1;
    public static final int MESSAGE_NEWMSG = 0x400 + 2;
    public boolean show_log = true;
    public Friend fInfo;

    public void log(String string) {
        //MsgQ message = mHandler.obtainMessage(MESSAGE_LOG, string);
        //message.sendToTarget();
        Log.d("日志：", string);
    }

    public void showMsg(final MsgQ msg) {
        Message message = mHandler.obtainMessage(MESSAGE_NEWMSG, msg);
        message.sendToTarget();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);
        backToFather = (Button) findViewById(R.id.back);
        FriendName = (TextView) findViewById(R.id.opposite_name);
        inputText = (EditText) findViewById(R.id.inputText);
        msgRecycleView = (RecyclerView)findViewById(R.id.chat_window);
        LinearLayoutManager mlayoutManager = new LinearLayoutManager(this);
//        mlayoutManager.setStackFromEnd(true);
        //更新顶部名字栏UI

        Intent intent = this.getIntent();
        fInfo = (Friend) intent.getSerializableExtra("Info");
        FriendName.setText(fInfo.getName());

        //抓取聊天记录
       // msgList = ChatRecords.getRecords(fInfo.getName());
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

        //返回上层


        backToFather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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

                    ChatService.chatService.sendMessage(content);
                }
            }
        });

        ChatService.chatService.chatActivity = this;

        log("Program started.");

    }

    protected void onDestroy() {
        super.onDestroy();;
        Log.d("TAG", "关了");
    }

    @Override
    public void onBackPressed(){
//        Friend friend = new Friend(friendName,msgList);
        Intent  intent1 = new Intent();
//        intent1.putExtra("records",friend);
        setResult(RESULT_OK,intent1);
        super.onBackPressed();
        finish();
    }
}
