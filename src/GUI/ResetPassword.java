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

public class ResetPassword extends JFrame {
	private static JTextField emailText;
	private static int screen = 0;
	public ResetPassword(Dimension size, Point pos) {
		
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
		
		JLabel emaillbl = new JLabel("Please enter your email");
		emaillbl.setHorizontalAlignment(SwingConstants.CENTER);
		emaillbl.setVisible(true);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		
		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		panel.add(emaillbl);
		
		emailText = new JTextField();
		panel.add(emailText);
		emailText.setColumns(10);
		
		JButton submit = new JButton("Submit");
		submit.setVisible(true);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel.add(submit);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Account.emailExists(emailText.getText())){
					Dimension sizeReturn = getSize();
					Point posReturn = getLocation();
					run(null, sizeReturn, posReturn);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null,"No account exists with this email");
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
			ResetPassword u = new ResetPassword(size, pos);
		}
		if(screen == 2) {
			/*String trueEmail = "";
			for(int i = 0; i < email.length(); i++) {
				if(email.substring(i,i+1) == ".") {
					trueEmail = email.substring(0,i+1);
					i = email.length();
				}
			}*/
			ResetPasswordII u = new ResetPasswordII(emailText.getText(), size, pos);
		}
		if(screen == 3) {
			ResetPasswordIII u = new ResetPasswordIII(args, size, pos);
		}
	}
}