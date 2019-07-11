package sse.bupt.androidwifichatroom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 7/11/2019.
 */

public class FriendListService {
    public static FriendListService friendListService;

    public ConcurrentHashMap<String, Friend> friendList;

    public FriendListService(){
        friendList = new ConcurrentHashMap<String,Friend>();
    }

    public static void setIPtoFriend(String ip, Friend friend){
        friendListService.friendList.put(ip, friend);
    }

    public static ArrayList<Friend> getFriendList_v1(){
        ArrayList<Friend> ret = new ArrayList<Friend>();
        Set<String> keys=friendListService.friendList.keySet();
        Iterator<String> iterator1=keys.iterator();
        while (iterator1.hasNext()){
            String s = iterator1.next();
            Friend f = friendListService.friendList.get(s);
            ret.add(f);
        }
        return ret;
    }
}
