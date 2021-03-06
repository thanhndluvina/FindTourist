package com.luvinat3h;

import java.awt.Color;

import javax.swing.JFrame;


import com.luvinat3h.icommon.ICommon;
import javax.swing.JFileChooser;
import java.awt.BorderLayout;

public class GUI extends JFrame implements ICommon{
	public static final int HEIGHT =400;
	public static final int WIDTH = 800;
	private MenuPanel menuPanel;
	
	public GUI(){
		init();
		addComps();
	}
	@Override
	public void init() {
		setTitle("Server");
		setSize( WIDTH,HEIGHT);
		getContentPane().setBackground(Color.BLUE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
	}
	@Override
	public void addComps() {
		menuPanel = new MenuPanel();
		getContentPane().add(menuPanel);
		
	}
}
