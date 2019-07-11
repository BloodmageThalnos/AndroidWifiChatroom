package sse.bupt.androidwifichatroom;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientSocketHelper {
    private final int port = 14396;
    private ChatActivity chatActivity;

    ClientSocketHelper(ChatActivity chatActivity) {
        this.chatActivity = chatActivity;
    }

    private String receive(int port) throws IOException {
        byte[] buffer = new byte[65507];
        DatagramSocket datagramSocket = new DatagramSocket(port);
        final DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        datagramSocket.receive(packet);
        datagramSocket.close();
        final String s = new String(packet.getData(), 0, packet.getLength());

        chatActivity.log("Receive: "+packet.getAddress().toString()+":"+ packet.getPort() + " => " + s);

        return s;
    }

    void listenMessage(){
        new Thread(){
            @Override
            public void run() {
                while(true) {
                    try {
                        final String s = receive(port);
                        processMessage(s);
                    } catch (Exception e) {
                        chatActivity.log("Receive Exception: " + e.toString());
                    }
                }
            }
        }.start();
    }

    void processMessage(String msg){
    }

    private void broadcast(String msg, int port) throws IOException{
        DatagramSocket datagramSocket = new DatagramSocket();
        //String address = ((EditText)mainActivity.findViewById(R.id.editText4)).getText().toString();
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

}
