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
    private MainActivity mainActivity;
    private String serverIP;
    private int port = 14396;

    public ServerSocketHelper(MainActivity mainActivity_){
        mainActivity = mainActivity_;
    }

    private void broadcast(String msg, int port) throws IOException{
        DatagramSocket datagramSocket = new DatagramSocket();
        //String address = ((EditText)mainActivity.findViewById(R.id.editText4)).getText().toString();
        String address = "";
        if(address.isEmpty()){
            address = "255.255.255.255";
        }
        mainActivity.log("Broadcast to address: "+address);
        DatagramPacket datagramPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length,
                InetAddress.getByName(address), port);
        datagramSocket.send(datagramPacket);
        datagramSocket.close();
    }

    public void broadcastMessage(final String msg) {
        new Thread() {
            public void run() {
                try {
                    MsgP ms = new MsgP();
                    ms._ = msg;
                    ms.type = MsgP.MESSAGE_BROADCAST_MESSAGE;
                    broadcast(ms.toString(), port);
                    mainActivity.log("Broadcast: " + msg);
                } catch (Exception e) {
                    mainActivity.log("Broadcast Exception: " + e.toString());
                }
            }
        }.start();
    }

    public void broadcastIP(final String msg) {
        new Thread() {
            public void run() {
                int i = 0;
                while (i < 400) {
                    i++;
                    try {
                        MsgP ms = new MsgP();
                        ms._ = msg;
                        ms.type = MsgP.MESSAGE_BROADCAST;
                        broadcast(msg, port);
                        mainActivity.log("Broadcast: " + msg);
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        mainActivity.log("Exception: " + e.toString());
                    }
                }
            }
        }.start();
    }
}
