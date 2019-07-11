package sse.bupt.androidwifichatroom;

import android.widget.EditText;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Littledva on 7/7/2019.
 */

public class ServerSocketHelper {
    private ChatActivity chatActivity;
    private int port = 14396;

    ServerSocketHelper(ChatActivity chatActivity){
        this.chatActivity = chatActivity;
    }

    private void broadcast(String msg, int port) throws IOException{
        DatagramSocket datagramSocket = new DatagramSocket();
        String address = "";
        if(address.isEmpty()){
            address = "255.255.255.255";
        }
        chatActivity.log("Broadcast to address: "+address);
        DatagramPacket datagramPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length,
                InetAddress.getByName(address), port);
        datagramSocket.send(datagramPacket);
        datagramSocket.close();
    }

    void broadcastMessage(final String msg) {
        new Thread() {
            public void run() {
                try {
                    broadcast(msg, port);
                    chatActivity.log("Broadcast: " + msg);
                } catch (Exception e) {
                    chatActivity.log("Broadcast exception: " + e.toString());
                }
            }
        }.start();
    }
}
