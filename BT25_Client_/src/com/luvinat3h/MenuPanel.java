package com.luvinat3h;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private static final String ACTION_CONECT = "kết nối";
	private static final String ACTION_SEND = "gửi tin nhắn";
	private JLabel lbIP, lbPORT;
	private JTextField tfIP, tfPORT, tfSend;
	private JTextArea taKhungChat;
	private JButton btConect, btSend;
	private Client client;

	private Font font = new Font("Tahoma", Font.PLAIN, 14);

	public MenuPanel() {
		init();
		addComps();
		tuongTacChat();
	}

	private void tuongTacChat() {
		client = new Client();
		btConect.setActionCommand(ACTION_CONECT);
		btConect.addActionListener(this);
		btSend.setActionCommand(ACTION_SEND);
		btSend.addActionListener(this);
	}

	@Override
	public void init() {
		setLayout(null);
		setBackground(Color.WHITE);
		setVisible(true);
	}

	@Override
	public void addComps() {
		addLabel();
		addJTextField();
		addJTextArena();
		addJButton();

	}

	private void addJTextArena() {
		taKhungChat = new JTextArea();
		taKhungChat.setFont(font);
		Border lineBorder = BorderFactory.createLineBorder(Color.blue);
		taKhungChat.setBorder(lineBorder);
		taKhungChat.setBounds(lbIP.getX(), lbIP.getY() + 20, tfPORT.getX() - lbIP.getX() + tfPORT.getWidth() + 200,
				250);
		add(taKhungChat);
	}

	private void addJButton() {
		btConect = new JButton("Conect");
		btConect.setFont(font);
		btConect.setBounds(tfPORT.getX() + tfPORT.getWidth() + 20, tfIP.getY(), 180, tfIP.getHeight());
		add(btConect);

		btSend = new JButton("SEND");
		btSend.setFont(font);
		btSend.setBounds(btConect.getX(), tfSend.getY(), btConect.getWidth(), tfSend.getHeight());
		btSend.setEnabled(false);
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
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case ACTION_CONECT:
			try {
				String ip = tfIP.getText();
				int PORT = Integer.parseInt(tfPORT.getText());
				client.connect(ip, PORT);
				btSend.setEnabled(true);
				client.receiveSms(taKhungChat);
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "Không đúng PORT hoặc IP");
			}
			break;
		case ACTION_SEND:
			client.sendSms(tfSend.getText());
		}

	}

}
