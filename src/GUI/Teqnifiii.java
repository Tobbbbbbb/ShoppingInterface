package GUI;

import Backend.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.FlowLayout;

public class Teqnifiii extends JFrame {
	public Teqnifiii(Dimension size, Point pos) {
		String args = "new";
		
		setResizable(false);
		setTitle("Teqnifiii");
		setSize(size);
		setLocation(pos);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setHgap(100);
		getContentPane().add(panel_1, BorderLayout.WEST);
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_3.getLayout();
		flowLayout_1.setHgap(100);
		getContentPane().add(panel_3, BorderLayout.EAST);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton newAccount = new JButton("Create New Account");
		panel.add(newAccount);
		newAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dimension sizeReturn = getSize();
				Point posReturn = getLocation();
				CreateUser.run(args, sizeReturn, posReturn);
				dispose();
			}
		});
		
		JButton logIn = new JButton("Log In");
		panel.add(logIn);
		logIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dimension sizeReturn = getSize();
				Point posReturn = getLocation();
				LogIn.run(args, sizeReturn, posReturn);
				dispose();
			}
		});
		
		JButton reset = new JButton("Reset Password");
		panel.add(reset);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dimension sizeReturn = getSize();
				Point posReturn = getLocation();
				ResetPassword.run(args, sizeReturn, posReturn);
				dispose();
			}
		});
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public static void main(String args[]) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = 960;
		int y = 540;
		Teqnifiii t = new Teqnifiii(new Dimension(x, y), new Point((int)(screenSize.getWidth()-x)/2, (int)(screenSize.getHeight()-y)/2));
	}
	
	public static void run(String args, Dimension size, Point pos) {
		Teqnifiii menu = new Teqnifiii(size, pos);
	}
}