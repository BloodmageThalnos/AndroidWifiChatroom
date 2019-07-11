package sse.bupt.androidwifichatroom;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientSocketHelper {
    private final int port;
    private ChatActivity chatActivity;
    private String lastIP;

    ClientSocketHelper(ChatActivity chatActivity, int port) {
        this.chatActivity = chatActivity;
        this.port = port;
    }

    private String receive(int port) throws IOException {
        byte[] buffer = new byte[65507];
        DatagramSocket datagramSocket = new DatagramSocket(port);
        final DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        datagramSocket.receive(packet);
        datagramSocket.close();
        final String s = new String(packet.getData(), 0, packet.getLength());

        Log.d("TAG", "Client Receive: ："+s);
        if(chatActivity!=null) {
            chatActivity.log("Receive: " + packet.getAddress().toString() + ":" + packet.getPort() + " => " + s);
        }

        lastIP = packet.getAddress().getHostAddress();

        return s;
    }

    void listenMessage(){
        new Thread(){
            @Override
            public void run() {
                while(true) {
                    try {
                        final String s = receive(port);
                        processMessage(s, lastIP);
                    } catch (Exception e) {
                        Log.d("TAG", "监听 Exception："+e.toString());
                        if(chatActivity!=null) {
                            chatActivity.log("Receive Exception: " + e.toString());
                        }
                    }
                }
            }
        }.start();
    }

    void processMessage(String msg, String IP){
    }

    private void broadcast(String msg, int port) throws IOException{
        DatagramSocket datagramSocket = new DatagramSocket();
        //String address = ((EditText)mainActivity.findViewById(R.id.editText4)).getText().toString();
        String address = "";
        if(address.isEmpty()) {
            address = "255.255.255.255";
        }
        if(chatActivity!=null) {
            chatActivity.log("Broadcast to address: " + address);
        }
        DatagramPacket datagramPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length,
                InetAddress.getByName(address), port);
        datagramSocket.send(datagramPacket);
        datagramSocket.close();
    }

}
