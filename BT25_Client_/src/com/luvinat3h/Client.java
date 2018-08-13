package com.luvinat3h;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Client {
	private Socket client;
	BufferedInputStream bis;
    BufferedOutputStream bos;

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

	public void sendImage(File fileImange) {
		new Thread() {
			@Override
			public void run() {
				 try {
					 	bis=new BufferedInputStream(new FileInputStream(fileImange));
			            bos=new BufferedOutputStream(client.getOutputStream());
				        int ch=bis.read();
				        int counter=0;
				        while(counter<fileImange.length())
				        {
				            bos.write(ch);
				            ch=bis.read();
				            counter++;
				        }

				        bos.write(ch);
				        bos.flush();
				    
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
			}
		}.start();
	}
}
