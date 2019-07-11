package sse.bupt.androidwifichatroom;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import sse.bupt.androidwifichatroom.FriendAdapter.OnItemClickListener;

/**
 * Created by lang22 on 2019/7/10.
 */
public class FriendListActivity extends AppCompatActivity {

    private RecyclerView fRecycleView;
    private LinearLayout fLinearLayout;
    private ImageView fImageView;
    private TextView fTextView;
    private List<Friend> friendList = new ArrayList<Friend>();
    private FriendAdapter friendAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list);

        LinearLayout groupLinerLayout = findViewById(R.id.group_chat_room);
        groupLinerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendListActivity.this,ChatActivity.class);
                intent.putExtra("Info", new Friend("群聊", null));//将头像和名称传递给chatactivity
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        updAteFriendList();
    }

    private void updAteFriendList(){
        friendList =  FriendListService.getFriendList_v1();

        fRecycleView = (RecyclerView) findViewById(R.id.friend_list_window);
        LinearLayoutManager mlayoutManager = new LinearLayoutManager(this);
//        mlayoutManager.setStackFromEnd(true);
        fRecycleView.setLayoutManager(mlayoutManager);
        friendAdapter = new FriendAdapter(friendList);
        friendAdapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(FriendListActivity.this, ChatActivity.class);
                intent.putExtra("Info", friendList.get(position));//将头像和名称传递给chatactivity
                startActivity(intent);
            }
        });
        fRecycleView.setAdapter(friendAdapter);
    }

    private void initFriendList() {
        ImageView tx1 = new ImageView(this);
        tx1.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.circle_arrow_left));
        Friend f1 = new Friend("邢凯",tx1);
        Log.d("骚凯", "initFriendList: "+friendList.toString());
        friendList.add(f1);
        ChatRecords.update(f1);

        ImageView tx2 = new ImageView(this);
        tx2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.circle_arrow_right));
        Friend f2 = new Friend("程元",tx2);
        friendList.add(f2);
        ChatRecords.update(f1);

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent intent){
        Log.d("到这了1",requestCode+"");
        switch (requestCode){
            case 1:
                Log.d("到这了2",resultCode+"");
                if(resultCode == RESULT_OK){
                    Log.d("到这了3",resultCode+"");
                    Intent intent1 = this.getIntent();
//                    Friend f = (Friend) intent1.getSerializableExtra("records");
//                    ChatRecords.update(f);
//                    Log.d("返回",ChatRecords.getRecords(f.getName()).toString());
                }
                break;
            default:
        }
    }
}
