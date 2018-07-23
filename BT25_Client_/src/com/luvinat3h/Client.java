package com.luvinat3h;

import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Client {
	private Socket client;

	public Client() {

	}

	public void connect(String ipServer, int portServer) {
		try {
			client = new Socket(ipServer, portServer);
			JOptionPane.showMessageDialog(null, "Kết nối thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Kết nối thất bại", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void sendSms(String sms) {
		try {
			client.getOutputStream().write(sms.getBytes());
			System.out.println("Client : " + sms);
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
                        tASms.append("\nSever : " + sms);
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
