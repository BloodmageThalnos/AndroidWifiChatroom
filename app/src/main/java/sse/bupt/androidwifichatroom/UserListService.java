package sse.bupt.androidwifichatroom;

import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 7/11/2019.
 */

public class UserListService {
    public static UserListService userListService;

    public UserListService(){
    }

    public void sendInitMessage() {
        MsgP ms = new MsgP();
        ms.contenT = MyInfo.getMyName();
        ms.type = MsgP.MESSAGE_BROADCAST_INIT;
        Log.d("TAG", "我发了："+ms.toString());

        ServerSocketHelper serverSocketHelper = new ServerSocketHelper(null, 14399);
        serverSocketHelper.sendInit(ms.toString());
    }

    public void startListening() {
        ClientSocketHelper clientSocketHelper = new ClientSocketHelper(null, 14399){
            @Override
            void processMessage(String s, String ip){
                Log.d("TAG", "Receive Message:"+s);

                MsgP msgP = MsgP.fromString(s);
                switch(msgP.type){
                    case MsgP.MESSAGE_BROADCAST_INIT:
                        if(msgP.contenT.equals(MyInfo.getMyName())){ // 不处理自己
                            break;
                        }
                        final Friend friend = new Friend(msgP.contenT, null);
                        FriendListService.setIPtoFriend(ip, friend);

                        MsgP ms = new MsgP();
                        ms.contenT = MyInfo.getMyName();
                        ms.type = MsgP.MESSAGE_BROADCAST_REPLY;
                        Log.d("TAG", "我回复了："+ms.toString());

                        ServerSocketHelper serverSocketHelper = new ServerSocketHelper(null, 14399);
                        serverSocketHelper.sendReply(ms.toString(), ip);
                        //friendListActivity.needUpdate=true;
                        break;
                    case MsgP.MESSAGE_BROADCAST_REPLY:
                        final Friend friend2 = new Friend(msgP.contenT, null);
                        FriendListService.setIPtoFriend(ip, friend2);
                        //friendListActivity.needUpdate=true;
                        break;
                }
            }
        };
        clientSocketHelper.listenMessage();
    }
}
