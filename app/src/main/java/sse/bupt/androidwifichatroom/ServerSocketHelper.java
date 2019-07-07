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

    private void receive() throws IOException {
        byte[] buffer = new byte[65507];
        @SuppressWarnings("resource")
        DatagramSocket datagramSocket = new DatagramSocket(port);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        while (true) {
            datagramSocket.receive(packet);
            String s = new String(packet.getData(), 0, packet.getLength());
            serverIP=packet.getAddress().toString();
            System.out.println(packet.getAddress() + ":" + packet.getPort() + "    →    " + s);
        }
    }

    public void broadcast(String msg, int port) throws IOException{
        DatagramSocket datagramSocket = new DatagramSocket();
        String address = ((EditText)mainActivity.findViewById(R.id.editText4)).getText().toString();
        if(address.isEmpty()){
            address = "255.255.255.255";
        }
        mainActivity.log("To address: "+address);
        DatagramPacket datagramPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length,
                InetAddress.getByName(address), port);
        datagramSocket.send(datagramPacket);
        datagramSocket.close();
    }

    public void startBroadcast(final String msg) {
        new Thread() {
            public void run() {
                int i = 0;
                while (i < 400) {
                    i++;
                    try {
                        Message ms = new Message();
                        ms._ = msg;
                        ms.type = Message.MESSAGE_BROADCAST;
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
