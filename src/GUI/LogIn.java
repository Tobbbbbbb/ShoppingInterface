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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.FlowLayout;

public class LogIn extends JFrame {
	private static JTextField emailText;
	private static int screen = 0;
	private JPasswordField passwordField;
	
	public LogIn(Dimension size, Point pos) {
		
		setResizable(false);
		setTitle("Teqnifiii");
		setSize(size);
		setLocation(pos);
		
		JPanel panel_13 = new JPanel();
		getContentPane().add(panel_13, BorderLayout.EAST);
		panel_13.setLayout(new GridLayout(0, 1, 50, 0));
		
		JPanel panel_2 = new JPanel();
		panel_13.add(panel_2);
		
		JPanel panel_4 = new JPanel();
		panel_13.add(panel_4);
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_3.getLayout();
		flowLayout_1.setHgap(100);
		panel_13.add(panel_3);
		
		JButton cancelButton = new JButton("Cancel");
		panel_13.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dimension sizeReturn = getSize();
				Point posReturn = getLocation();
				Teqnifiii.run(null, sizeReturn, posReturn);
				dispose();
			}
		});
		
		JPanel panel_12 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_12.getLayout();
		flowLayout.setHgap(100);
		getContentPane().add(panel_12, BorderLayout.WEST);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		
		JLabel title = new JLabel("Log In");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(title);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel userlbl = new JLabel("Please enter your password");
		userlbl.setHorizontalAlignment(SwingConstants.CENTER);
		userlbl.setVisible(true);
		
		JPanel panel_6 = new JPanel();
		panel.add(panel_6);
		
		JLabel emaillbl = new JLabel("Please enter your email");
		emaillbl.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(emaillbl);
		
		emailText = new JTextField();
		panel.add(emailText);
		emailText.setColumns(10);
		panel.add(userlbl);
		
		passwordField = new JPasswordField();
		panel.add(passwordField);
		
		JButton submit = new JButton("Submit");
		submit.setVisible(true);
		
		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		panel.add(submit);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Account user = new Account(emailText.getText());
				String pass = CreateUser.convertPassword(passwordField);
				int result = user.logIn(pass);
				if(result == 0) {
					//JOptionPane.showMessageDialog(null,"You have successfully logged in");
					Dimension sizeReturn = getSize();
					Point posReturn = getLocation();
					//user.loadAccount();
					Home.run(null, sizeReturn, posReturn, user, null);
					dispose();
				} else if (result == 1) {
					JOptionPane.showMessageDialog(null,"No account exists with the given email");
				} else if (result == 2) {
					JOptionPane.showMessageDialog(null,"Your password is incorrect");
				}
			}
		});
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public static void run(String args, Dimension size, Point pos) {
		if(args != null && args.equals("new")) {
			screen = 0;
		}
		screen++;
		if(screen == 1) {
			LogIn u = new LogIn(size, pos);
		}
		if(screen == 2) {
			
		}
	}
}