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

public class CreateUser extends JFrame {
	private static JPasswordField passwordField;
	private static JTextField nameText;
	private static JTextField emailText;
	private static int screen = 0;
	
	public CreateUser(Dimension size, Point pos) {
		setResizable(false);
		setVisible(true);
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
		
		JLabel title = new JLabel("Create New User");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(title);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel userlbl = new JLabel("Please pick a username");
		userlbl.setHorizontalAlignment(SwingConstants.CENTER);
		userlbl.setVisible(true);
		
		JLabel emaillbl = new JLabel("Please enter your email");
		emaillbl.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(emaillbl);
		
		emailText = new JTextField();
		panel.add(emailText);
		emailText.setColumns(10);
		panel.add(userlbl);
		
		JLabel passlbl = new JLabel("Please pick a password");
		passlbl.setHorizontalAlignment(SwingConstants.CENTER);
		passlbl.setVisible(true);
		passlbl.setVisible(true);
		
		nameText = new JTextField();
		panel.add(nameText);
		nameText.setColumns(10);
		panel.add(passlbl);
		
		passwordField = new JPasswordField();
		passwordField.setVisible(true);
		panel.add(passwordField);
		
		JButton submit = new JButton("Submit");
		submit.setVisible(true);
		panel.add(submit);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean at = false;
				boolean dot = false;
				boolean noSpace = true;
				for(int i = 0; i < emailText.getText().length(); i++) {
					if(emailText.getText().substring(i,i+1).equals("@")) at = true;
					if(emailText.getText().substring(i,i+1).equals(".") && at) dot = true;
					if(emailText.getText().substring(i,i+1).equals(" ")) noSpace = false;
				}
				if(at &&  dot && noSpace) {
					int result = Account.checkUsers(emailText.getText(), nameText.getText());
					if(result == 1) {
						JOptionPane.showMessageDialog(null,"An account already exists with this email");
					} else if(result == 2) {
						JOptionPane.showMessageDialog(null,"An account already exists with this username");
					} else if(result == 0) {
						String passString = convertPassword(passwordField);
						if(nameText.getText().length() == 0)
						{
							JOptionPane.showMessageDialog(null,"Please enter a username");
						} else if (passString.length() == 0) {
							JOptionPane.showMessageDialog(null,"Please enter a password");
						} else {
							Dimension sizeReturn = getSize();
							Point posReturn = getLocation();
							run(null, sizeReturn, posReturn);
							dispose();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null,"The email provided is invalid");
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
			CreateUser u = new CreateUser(size, pos);
		}
		if(screen == 2) {
			String passString = convertPassword(passwordField);
			/*String email = emailText.getText();
			String trueEmail = "";
			for(int i = 0; i < email.length(); i++) {
				if(email.substring(i,i+1) == ".") {
					trueEmail = email.substring(0,i+1);
					i = email.length();
				}
			}*/
			CreateUserII u = new CreateUserII(emailText.getText(), nameText.getText(), passString, size, pos);
		}
	}
	
	public static String convertPassword(JPasswordField passwordField) {
		char[] password =  passwordField.getPassword();
		String passString = "";
		for(int i = 0; i < password.length; i++) {
			passString+=password[i];
		}
		return passString;
	}
}