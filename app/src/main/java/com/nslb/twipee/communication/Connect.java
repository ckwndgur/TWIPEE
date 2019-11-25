package com.nslb.twipee.communication;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class Connect extends Thread {
	public Socket ClientSocket;
	private DefineValue DEFINE = new DefineValue();
	private String IP = "202.31.137.123";
	private int port = 5987;

	private static Connect SocketValue = new Connect();

	private Connect() {

	}

	public static Connect getInstance() {
		return SocketValue;
	}

	public Socket ConnectwithServer() throws UnknownHostException, IOException {
		ClientSocket = new Socket(IP, port);
		return ClientSocket;

	}

	public void run() {
		String NewIP = null;
		String PreviousIP = null;
		int counter = 0;

		while (true) {
			try {

				if (counter == 0) {
					NewIP = getLocalIP();
					PreviousIP = NewIP;
					ClientSocket = ConnectwithServer();
					counter++;
				} else if (counter != 0) {
					NewIP = getLocalIP();
					if (!PreviousIP.equals(NewIP)) {
						//ClientSocket.close();
						ClientSocket = ConnectwithServer();
						//guiInterface.Reconnect_Sender("whddn");
						counter = DEFINE.CONNECT_CLEAR;
					} else {
						PreviousIP = NewIP;
						counter = DEFINE.CONNECT_CLEAR;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String getLocalIP() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
		}
		return null;
	}
}