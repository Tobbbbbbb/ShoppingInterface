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

public class CreateUserII extends JFrame {
	private JTextField answerText;
	private String[] questions = {
			"What is your favorite Herb?",
			"What was the nickname for your first car?",
			"Where did you take your first vacation?",
			"What was the name of your first pet?",
			"Where was your father born?",
			"What was your grandmother's maiden name?",
			"What elementary school did you go to?",
			"What was the name of your favorite teacher?"
	};
	
	public CreateUserII(String email, String username, String password, Dimension size, Point pos) {
		
		setResizable(false);
		setVisible(true);
		setTitle("Teqnifiii");
		setSize(size);
		setLocation(pos);
		
		JPanel panel_13 = new JPanel();
		getContentPane().add(panel_13, BorderLayout.EAST);
		panel_13.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_2_1 = new JPanel();
		panel_13.add(panel_2_1);
		
		JPanel panel_4 = new JPanel();
		panel_13.add(panel_4);
		
		JPanel panel_3_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_3_1.getLayout();
		flowLayout_1.setHgap(100);
		panel_13.add(panel_3_1);
		
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
		
		JLabel answerlbl = new JLabel("Please answer the question above");
		answerlbl.setHorizontalAlignment(SwingConstants.CENTER);
		answerlbl.setVisible(true);
		
		JLabel questionlbl = new JLabel("Please choose a security question");
		questionlbl.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(questionlbl);
		
		JComboBox comboBox = new JComboBox(questions);
		comboBox.setSelectedIndex(-1);
		panel.add(comboBox);
		panel.add(answerlbl);
		
		answerText = new JTextField();
		panel.add(answerText);
		answerText.setColumns(10);
		
		JButton submit = new JButton("Submit");
		submit.setVisible(true);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel.add(submit);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null,"Please select a question");
				}
				else if(answerText.getText().length() == 0) {
					JOptionPane.showMessageDialog(null,"Please answer the question");
				}
				else {
					Account newUser = new Account(email, username, password, comboBox.getSelectedIndex() , answerText.getText());
					if(!Account.doesDatabaseExist()) {
						Account.newUserDatabase();
					}
					newUser.createNewFile();
					JOptionPane.showMessageDialog(null,"Your account has been successfully created");
					
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