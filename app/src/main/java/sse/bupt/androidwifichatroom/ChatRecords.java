package sse.bupt.androidwifichatroom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lang22 on 2019/7/11.
 */

public class ChatRecords {
    private static List<Friend> friends = new ArrayList<Friend>();

    public static List<Friend> getFriends(){return friends;}

    public static void update(Friend NewFriend){
        for(Friend friend:friends){
            if(friend.getName().equals(NewFriend.getName())){
                friend.setMsgList(NewFriend.getMsgList());
                return;
            }
        }
        friends.add(NewFriend);
    }

    public static List<MsgQ> getRecords(String name){
        for(Friend friend:friends){
            if(friend.getName().equals(name)){
                return friend.getMsgList();
            }
        }
        return null;
    }
}
