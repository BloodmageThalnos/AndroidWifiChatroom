package sse.bupt.androidwifichatroom;

import java.util.Random;

/**
 * Created by Administrator on 7/8/2019.
 */

class ChatService {
    private ChatActivity chatActivity;
    private String name;

    ChatService(ChatActivity chatActivity) {
        this.chatActivity = chatActivity;
        Random random = new Random(System.currentTimeMillis());
        String[] names = {"Ana", "Bob", "Kai", "Alice", "Dva", "Messi", "Lucio", "Jennie", "Dembele", "Doomfist", "Solder76"};
        name = names[(((int)System.currentTimeMillis() % names.length)+names.length)%names.length];
    }

    void sendMessage(String msg) {
        MsgP ms = new MsgP();
        ms.contenT = name + ": " + msg;
        ms.type = MsgP.MESSAGE_BROADCAST_MESSAGE;

        ServerSocketHelper ssh = new ServerSocketHelper(chatActivity);
        ssh.broadcastMessage(ms.toString());
    }

    void startListening() {
        ClientSocketHelper clientSocketHelper = new ClientSocketHelper(chatActivity){
            @Override
            void processMessage(String s){
                MsgP msgP = MsgP.fromString(s);
                chatActivity.log(s);

                switch(msgP.type){
                    case MsgP.MESSAGE_BROADCAST_MESSAGE:
                        MsgQ msgQ = new MsgQ(msgP.contenT,MsgQ.TYPE_RECEIVED);

                        // hack: 收到自己广播的消息，不显示
                        if(msgP.contenT.startsWith(name)){
                            break;
                        }

                        chatActivity.showMsg(msgQ);
                        break;
                    default:
                        chatActivity.log("Unknown message: "+msgP.contenT);
                }
            }
        };
        clientSocketHelper.listenMessage();
    }
}
