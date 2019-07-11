package sse.bupt.androidwifichatroom;

import android.util.Log;
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
    private int port;

    ServerSocketHelper(ChatActivity chatActivity, int port){
        this.chatActivity = chatActivity;
        this.port = port;
    }

    private void broadcast(final String msg, final int port){
        new Thread(){
            @Override
            public void run() {
                try {
                    DatagramSocket datagramSocket = new DatagramSocket();
                    String address = "";
                    if(address.isEmpty()){
                        address = "255.255.255.255";
                    }
                    Log.d("TAG", "Broadcast to address: "+address);
                    if(chatActivity!=null) {
                        chatActivity.log("Broadcast to address: " + address);
                    }
                    DatagramPacket datagramPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length,
                            InetAddress.getByName(address), port);
                    datagramSocket.send(datagramPacket);
                    datagramSocket.close();
                } catch (Exception e) {
                    Log.d("TAG", "Broadcast Exception："+e.toString());
                    if(chatActivity!=null) {
                        chatActivity.log("Broadcast exception: " + e.toString());
                    }
                }
            }
        }.start();
    }

    void broadcastMessage(final String msg) {
        new Thread() {
            public void run() {
                Log.d("TAG", "Broadcast: "+msg);
                broadcast(msg, port);
                if(chatActivity!=null) {
                    chatActivity.log("Broadcast: " + msg);
                }
            }
        }.start();
    }

    public void sendReply(final String msg, final String ip){
        new Thread(){
            @Override
            public void run() {
                try {
                    DatagramSocket datagramSocket = new DatagramSocket();
                    String address = ip;
                    Log.d("TAG", "Send reply to address: " + address);
                    if(chatActivity!=null) {
                        chatActivity.log("Send reply to address: " + address);
                    }
                    DatagramPacket datagramPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length,
                            InetAddress.getByName(address), port);
                    datagramSocket.send(datagramPacket);
                    datagramSocket.close();
                } catch (Exception e) {
                    Log.d("TAG", "回复 Exception："+e.toString());
                    if(chatActivity!=null) {
                        chatActivity.log("Reply exception: " + e.toString());
                    }
                }
            }
        }.start();
    }

    public void sendInit(final String msg){
        new Thread(){
            @Override
            public void run() {
                try {
                    DatagramSocket datagramSocket = new DatagramSocket();
                    String address = "255.255.255.255";
                    Log.d("TAG", "Send init to address: " + address);
                    if(chatActivity!=null) {
                        chatActivity.log("Send init to address: " + address);
                    }
                    DatagramPacket datagramPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length,
                            InetAddress.getByName(address), port);
                    datagramSocket.send(datagramPacket);
                    datagramSocket.close();
                } catch (Exception e) {
                    Log.d("TAG", "我发 Exception："+e.toString());
                    if(chatActivity!=null) {
                        chatActivity.log("Init exception: " + e.toString());
                    }
                }
            }
        }.start();
    }
}
