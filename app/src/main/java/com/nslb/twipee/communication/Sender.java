package com.nslb.twipee.communication;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class Sender implements Runnable {
    private Socket sock;
    private ObjectOutputStream oos;

    byte[] SendPacket;
    public Sender(byte[] SendPacket){
        this.SendPacket = SendPacket;
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            sock = Connect.getInstance().ClientSocket;
            oos = new ObjectOutputStream(sock.getOutputStream());
            oos.writeObject(this.SendPacket);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
