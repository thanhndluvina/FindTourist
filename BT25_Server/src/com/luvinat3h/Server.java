package com.luvinat3h;


import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.imageio.ImageIO;
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
	FileOutputStream fout;
    BufferedInputStream bis;
    BufferedReader br;
    Scanner sc;
    BufferedReader br1;
    BufferedInputStream bis1;
    String filename;

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

	public void receiveImg() {
		new Thread() {
            @Override
            public void run() {
            	try{
                    filename=br1.readLine();

                    System.out.println(filename);
                    fout=new FileOutputStream(filename);
                    sc=new Scanner(client.getInputStream());
                    int size=sc.nextInt();
                    System.out.println("file created");
                    int ch=bis.read();
                    System.out.println(ch);
                    System.out.println("after bis.read()");

                    while(size>0)
                    {
                        fout.write(ch);
                        System.out.println(ch);
                        ch=bis1.read();
                        size--;
                    }

                    System.out.println(ch);
                    System.out.println("data written");
                    PrintWriter pw1=new PrintWriter(client.getOutputStream(),true);
                    pw1.println("DATA UPLOADED SUCCESSFULLY");


                }catch(Exception e)
                {
                    System.out.println("Error"+e);
                }finally{
                    if(fout!=null)
                        try {
                            fout.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    System.out.println("IN Finally");
                }
            }
        }.start();
	}

}
