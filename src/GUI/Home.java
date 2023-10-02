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

public class Home extends JFrameTwo {
	private JTextField textField;
	
	public Home(Dimension size, Point pos, Account user, JFrame prev) {
		super(user, prev);
		
		setResizable(false);
		setVisible(true);
		setTitle("Teqnifiii");
		setSize(size);
		setLocation(pos);
		
		ToolBar(this, user, "home", null, 0, false);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void run(String args, Dimension size, Point pos, Account user, JFrame prev) {
		Home h = new Home(size, pos, user, prev);
	}
	
	public static void ToolBar(JFrameTwo f, Account user, String page, String conditions, int pageNumber, boolean hist) {
		JTextField textField;
		//Logout and clear cart when program is closed
				f.addWindowListener(new java.awt.event.WindowAdapter(){
					@Override public void windowClosing(java.awt.event.WindowEvent windowEvent) {
						user.logOut();
						user.restock();
						System.exit(0);
					}
				});
				
				JPanel panel = new JPanel();
				f.getContentPane().add(panel, BorderLayout.NORTH);
				panel.setLayout(new GridLayout(0, 8, 0, 30));
				
				JButton backBtn = new JButton("Back");
				if(f.isPrevNull()) {
					backBtn.setEnabled(false);
				}
				panel.add(backBtn);
				backBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						f.loadPrevFrame();
						f.dispose();
					}
				});
				
				textField = new JTextField();
				panel.add(textField);
				//textField.setColumns(10);
				
				JButton searchBtn = new JButton("Search");
				if(hist) {
					searchBtn.setText("Search History");
				}
				panel.add(searchBtn);
				searchBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String text = textField.getText();
						String[] temp = text.split(" ");
						ArrayList<Item> temp2;
						if(hist) {
							temp2 = Item.historySearch(user, temp);
						} else {
							temp2 = Item.search(temp);
						}
						Dimension sizeReturn = f.getSize();
						Point posReturn = f.getLocation();
						DisplayItems.run(null, sizeReturn, posReturn, user, temp2, 0, false, -1, -1, f, 0, new ArrayList<Integer>(), hist, false);
						f.dispose();
					}
				});
				
				JButton logOutBtn = new JButton("Log Out");
				panel.add(logOutBtn);
				logOutBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						user.restock();
						user.logOut();
						Dimension sizeReturn = f.getSize();
						Point posReturn = f.getLocation();
						Teqnifiii.run(null, sizeReturn, posReturn);
						f.dispose();
					}
				});
				
				JButton purchaseBtn = new JButton("History");
				panel.add(purchaseBtn);
				purchaseBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Dimension sizeReturn = f.getSize();
						Point posReturn = f.getLocation();
						DisplayItems.run(null, sizeReturn, posReturn, user, user.getPurchaseHistory(), 0, false, -1, -1, f, 0, new ArrayList<Integer>(), true, false);
						f.dispose();
					}
				});
				
				JButton myItemsBtn = new JButton("My Items");
				panel.add(myItemsBtn);
				myItemsBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Dimension sizeReturn = f.getSize();
						Point posReturn = f.getLocation();
						/*for(int i = 0; i < user.getMyItems().size(); i++) {
							System.out.println("Æ" + user.getMyItems().get(i).getItemNum());
						}*/
						DisplayItems.run(null, sizeReturn, posReturn, user, user.getMyItems(), 0, false, -1, -1, f, 0, new ArrayList<Integer>(), false, true);
						f.dispose();
					}
				});
				
				JButton newItemBtn = new JButton("New Item");
				panel.add(newItemBtn);
				newItemBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Dimension sizeReturn = f.getSize();
						Point posReturn = f.getLocation();
						NewItem.run(null, sizeReturn, posReturn, user, false, null, f);
						f.dispose();
					}
				});
				
				JButton checkOutBtn = new JButton("Cart");
				panel.add(checkOutBtn);
				checkOutBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Dimension sizeReturn = f.getSize();
						Point posReturn = f.getLocation();
						DisplayItems.run(null, sizeReturn, posReturn, user, user.getCartItems(), 0, true, -1, -1, f, 0, new ArrayList<Integer>(), false, true);
						f.dispose();
					}
				});
	}
}