package GUI;

import Backend.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class ReviewPage extends JFrameTwo {
	private JTextField rateTxt;
	private JFrame j;
	
	public ReviewPage(Dimension size, Point pos, Account user, ArrayList<Item> items, int page, Item i, JFrame prev) {
		super(user, prev);
		j = this;
		
		setResizable(false);
		setVisible(true);
		setTitle("Teqnifiii");
		setSize(size);
		setLocation(pos);
		
		JLabel title = new JLabel("Review " + i.getTitle());
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(title, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		JLabel rateLbl = new JLabel("Please rate this item out of 5 stars:");
		rateLbl.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(rateLbl);
		
		rateTxt = new JTextField();
		panel.add(rateTxt);
		rateTxt.setColumns(10);
		
		JLabel descLbl = new JLabel("Please leave a description");
		descLbl.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(descLbl);
		
		JTextPane descTxt = new JTextPane();
		panel.add(descTxt);
		
		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		
		int max = 2500;
		
		JButton submit = new JButton("Submit");
		panel.add(submit);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(descTxt.getText().length() <= max) {
					try{
						if(Double.valueOf(rateTxt.getText()) <= 5.0 && Double.valueOf(rateTxt.getText()) >= 0.0) {
							i.addReview(user, Double.valueOf(rateTxt.getText()), descTxt.getText());
							System.out.println("HeYo");
							Dimension sizeReturn = getSize();
							Point posReturn = getLocation();
							//System.out.println("HeY");
							Home.run(null, sizeReturn, posReturn, user, prev);
							dispose();
						} else if(Double.valueOf(rateTxt.getText()) > 5.0 || Double.valueOf(rateTxt.getText()) < 0.0) {
							JOptionPane.showMessageDialog(null,"Please enter a rating between 0 and 5 stars");
						} 
					}catch(Exception e1) {
						JOptionPane.showMessageDialog(null,"Please enter a numerical rating");
					}
				} else {
					JOptionPane.showMessageDialog(null,"Please enter a review with less than " + max + " characters");
				}
			}
		});
		
		JPanel panel_13 = new JPanel();
		getContentPane().add(panel_13, BorderLayout.EAST);
		panel_13.setLayout(new GridLayout(0, 1, 50, 0));
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_3.getLayout();
		flowLayout_1.setHgap(100);
		panel_13.add(panel_3);
		
		JPanel panel_2 = new JPanel();
		panel_13.add(panel_2);
		
		JPanel panel_4 = new JPanel();
		panel_13.add(panel_4);
		
		JButton cancelButton = new JButton("Cancel");
		panel_13.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dimension sizeReturn = getSize();
				Point posReturn = getLocation();
				Home.run(null, sizeReturn, posReturn, user, prev);
				dispose();
			}
		});
		
		JPanel panel_12 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_12.getLayout();
		flowLayout.setHgap(100);
		getContentPane().add(panel_12, BorderLayout.WEST);
		
		addWindowListener(new java.awt.event.WindowAdapter(){
			@Override public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				user.logOut();
				user.restock();
				System.exit(0);
			}
		});
	}
	
	public static void run(String args, Dimension size, Point pos, Account user, ArrayList<Item> items, int page, Item i, JFrame prev){
		ReviewPage r = new ReviewPage(size, pos, user, items, page, i, prev);
	}
}