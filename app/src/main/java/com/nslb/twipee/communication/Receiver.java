package com.nslb.twipee.communication;

import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver implements Runnable {
	private Socket sock;
	private ObjectInputStream ois;
	private DataPacketAnalysis DPA = new DataPacketAnalysis();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				sock = Connect.getInstance().ClientSocket;
				if (sock != null){
					ois = new ObjectInputStream(sock.getInputStream());
					byte[] RecvPacket = (byte[]) ois.readObject();
					DPA.PacketAnalysis(RecvPacket);
				}
			} catch (Exception e) {
				// 	TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
