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

public class ResetPasswordIII extends JFrame {
	private JPasswordField passwordField;
	
	public ResetPasswordIII(String emailInput, Dimension size, Point pos) {		
		Account user = new Account(emailInput);
		user.loadAccount();
		
		setResizable(false);
		setVisible(true);
		setTitle("Teqnifiii");
		setSize(size);
		setLocation(pos);
		
		JPanel panel_13 = new JPanel();
		getContentPane().add(panel_13, BorderLayout.EAST);
		panel_13.setLayout(new GridLayout(0, 1, 50, 0));
		
		JPanel panel_6 = new JPanel();
		panel_13.add(panel_6);
		
		JPanel panel_7 = new JPanel();
		panel_13.add(panel_7);
		
		JPanel panel_8 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_8.getLayout();
		flowLayout_1.setHgap(100);
		panel_13.add(panel_8);
		
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
		
		JLabel title = new JLabel("Reset Password");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(title);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel passlbl = new JLabel("Please type in your new password");
		passlbl.setHorizontalAlignment(SwingConstants.CENTER);
		passlbl.setVisible(true);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		
		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		panel.add(passlbl);
		
		JButton submit = new JButton("Submit");
		submit.setVisible(true);
		
		passwordField = new JPasswordField();
		panel.add(passwordField);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel.add(submit);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pass = CreateUser.convertPassword(passwordField);
				if(pass.length() == 0) {
					JOptionPane.showMessageDialog(null,"Please enter a password");
				} else {
					user.setPassword(pass);
					JOptionPane.showMessageDialog(null,"Your password has been reset");
					Dimension sizeReturn = getSize();
					Point posReturn = getLocation();
					Teqnifiii.run(null, sizeReturn, posReturn);
					dispose();
				}
			}
		});
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}