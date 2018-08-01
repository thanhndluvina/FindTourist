package com.luvinat3h;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.luvinat3h.icommon.ICommon;

public class MenuPanel extends JPanel implements ICommon, ActionListener {
	private static final String ACTION_OPEN_CONECT = "action_conect";
	private static final String ACTION_SEN = "action_send";
	private JLabel lbIP, lbPORT;
	private JTextField tfIP, tfPORT, tfSend;
	private JTextArea taKhungChat, taHienThiThongTin;
	private JButton btConect, btSend;
	private Server server;

	private Font font = new Font("Tahoma", Font.PLAIN, 14);

	public MenuPanel() {
		init();
		addComps();
		server.showIPInfo(tfIP, tfPORT);
	}
	@Override
	public void init() {
		setLayout(null);
		setBackground(Color.WHITE);
		setVisible(true);
	}

	@Override
	public void addComps() {
		server = new Server();
		addLabel();
		addJTextField();
		addJTextArena();
		addJButton();

	}

	private void addJTextArena() {
		taHienThiThongTin = new JTextArea();
		taHienThiThongTin.setFont(font);
		taHienThiThongTin.setBackground(Color.lightGray);
		taHienThiThongTin.setBounds(lbIP.getX(), lbIP.getY() + 30,
				tfPORT.getX() - lbIP.getX() + tfPORT.getWidth() + 200, 60);
		add(taHienThiThongTin);

		taKhungChat = new JTextArea();
		taKhungChat.setFont(font);
		Border lineBorder = BorderFactory.createLineBorder(Color.blue);
		taKhungChat.setBorder(lineBorder);
		taKhungChat.setBounds(lbIP.getX(), taHienThiThongTin.getY() + 20 + taHienThiThongTin.getHeight(),
				taHienThiThongTin.getWidth(), 170);
		add(taKhungChat);

	}

	private void addJButton() {
		btConect = new JButton("Open Conect");
		btConect.setFont(font);
		btConect.setBounds(tfPORT.getX() + tfPORT.getWidth() + 20, tfIP.getY(), 180, tfIP.getHeight());
		btConect.setActionCommand(ACTION_OPEN_CONECT);
		btConect.addActionListener(this);
		add(btConect);

		btSend = new JButton("SEND");
		btSend.setFont(font);
		btSend.setBounds(btConect.getX(), tfSend.getY(), btConect.getWidth(), tfSend.getHeight());
		btSend.setActionCommand(ACTION_SEN);
		btSend.addActionListener(this);
		add(btSend);
		
		

	}

	private void addJTextField() {
		tfIP = new JTextField();
		tfPORT = new JTextField();
		tfSend = new JTextField();

		int x = lbIP.getX() + 60;
		int y = lbIP.getY();
		int w = 200;
		int h = lbIP.getHeight();
		addTungJTextField(tfIP, x, y, w, h);
		addTungJTextField(tfPORT, lbPORT.getX() + 50, y, w, h);
		addTungJTextField(tfSend, lbIP.getX(), 300, 550, 50);

	}

	private void addTungJTextField(JTextField tfH, int x2, int y2, int w, int h) {
		tfH.setBackground(Color.WHITE);
		tfH.setBounds(x2, y2, w, h);
		add(tfH);

	}

	private void addLabel() {
		lbIP = new JLabel("IP Sever: ");
		lbPORT = new JLabel("PORT:");
		int x = 20, y = 10;
		addTungLabel(lbIP, x, y);
		addTungLabel(lbPORT, x + 300, y);

	}

	private void addTungLabel(JLabel lbH, int x1, int y1) {
		lbH.setFont(font);
		FontMetrics metrics = getFontMetrics(font);
		int width = metrics.stringWidth(lbH.getText());
		int height = metrics.getHeight();
		lbH.setBounds(x1, y1, 140, height);
		add(lbH);

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
			
		case ACTION_OPEN_CONECT:
			server.openConn(taHienThiThongTin);
			btSend.setEnabled(true);
			server.receiveSms(taKhungChat);
			server.receiveImg();
			break;
		case ACTION_SEN:
			server.sendSms(tfSend.getText());
			taKhungChat.setText(tfSend.getText());
		default:
			break;
		}
		
	}

}
