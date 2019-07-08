package sse.bupt.androidwifichatroom;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientHelper {
    private final int port = 14396;
    private MainActivity mainActivity;

    public ClientHelper(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    private String receive(int port) throws IOException {
        byte[] buffer = new byte[65507];
        DatagramSocket datagramSocket = new DatagramSocket(port);
        final DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        datagramSocket.receive(packet);
        datagramSocket.close();
        final String s = new String(packet.getData(), 0, packet.getLength());

        mainActivity.log("Receive: "+packet.getAddress().toString()+":"+ packet.getPort() + " => " + s);

        return s;
    }

    public void send(String msg, int port) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        DatagramPacket datagramPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length,
                InetAddress.getByName("172.20.10.4"), port);
        datagramSocket.send(datagramPacket);
        datagramSocket.close();
    }

    public void listenMessage(){
        new Thread(){
            @Override
            public void run() {
                while(true) {
                    try {
                        final String s = receive(port);
                        MsgP msgP = MsgP.fromString(s);
                        switch(msgP.type){
                            case MsgP.MESSAGE_BROADCAST_MESSAGE:
                                mainActivity.showMsg(msgP._);
                            default:
                                mainActivity.showMsg("Unknown message: "+msgP._);
                        }
                    } catch (Exception e) {
                        mainActivity.log("Receive Exception: " + e.toString());
                    }
                }
            }
        }.start();
    }

}
