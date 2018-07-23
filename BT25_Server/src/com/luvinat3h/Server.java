package com.luvinat3h;


import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server {
	private ServerSocket server;
	private String ip;
	private String name;
	private String ipClient,nameClient;
	public static final int PORT = 5555;
	private Socket client;

	public Server() {
		try {
			server = new ServerSocket(PORT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showIPInfo(JTextField txIP, JTextField txPort) {
		try {
			InetAddress info = InetAddress.getLocalHost();
			name = info.getHostName();
			ip = info.getHostAddress();
			txIP.setText(ip);
			txPort.setText(PORT + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openConn(JTextArea txa) {
		try{
			client = server.accept();
			InetAddress inetClient = client.getInetAddress();
			nameClient = inetClient.getHostName();
			ipClient = inetClient.getHostAddress();
			txa.setText("\r\n" + "Client Name: " + nameClient + "\r\n Client IP: " + ipClient);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendSms(String sms) {
		try {
			client.getOutputStream().write(sms.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void receiveSms() {

		try {
			byte buff[] = new byte[1024];
			int len = client.getInputStream().read(buff);
			String sms = new String(buff, 0, len);

//			System.out.println("Client <: " + sms);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    public void receiveSms(JTextArea tASms) {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        byte buff[] = new byte[1024];
                        int len = client.getInputStream().read(buff);
                        String sms = new String(buff, 0, len);
                        tASms.append("\nClient <: " + sms);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


	public void close() {
		if (client.isConnected()) {
			try {
				client.close();
				server.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String getIp() {
		return ip;
	}

	public String getName() {
		return name;
	}

	public String getIpClient() {
		return ipClient;
	}

	public String getNameClient() {
		return nameClient;
	}

}
