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

public class ItemPage extends JFrameTwo {
	
	private JFrame f;
	
	public ItemPage(Dimension size, Point pos, Account user, ArrayList<Item> items, int page, Item i, JFrame prev) {
		super(user, prev);
		f = this;
		
		setResizable(false);
		setVisible(true);
		setTitle("Teqnifiii");
		setSize(size);
		setLocation(pos);
		
		Home.ToolBar(this, user, "displayItems", "myItems", 0, false);
		
		int imgWidth = 300;
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 8, 0, 0));
		
		JButton cartBtn = new JButton("Add To Cart");
		panel.add(cartBtn);
		
		cartBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String quantityString = JOptionPane.showInputDialog(null, "How many?");
				int quantity;
				try {
					quantity = Integer.valueOf(quantityString);
					if(quantity > 0) {
						if (quantity <= i.getQuantity()) {
							user.addToCart(i, quantity);
							i.setQuantity(i.getQuantity()-quantity);
							Dimension sizeReturn = getSize();
							Point posReturn = getLocation();
							Home.run(null, sizeReturn, posReturn, user, f);
							setVisible(false);
						} else {
							JOptionPane.showMessageDialog(null,"There are not enough items available to fill your request");
						}
					} else {
						JOptionPane.showMessageDialog(null,"Please order a positive number of items");
					}
				} catch (Exception ee) {
					JOptionPane.showMessageDialog(null,"Please order a positive integer number of items");
				}
			}
		});
		
		JButton reviewBtn = new JButton("Leave a Review");
		ArrayList<Item> purch = user.getPurchaseHistory();
		reviewBtn.setEnabled(false);
		for(int j = 0; j < purch.size(); j++) {
			if(i.equals(purch.get(j))) {
				reviewBtn.setEnabled(true);
				break;
			}
		}
		
		JButton accessRevBtn = new JButton("Reviews");
		if(i.getNumReviews() == 0) {
			accessRevBtn.setEnabled(false);
		} else {
			accessRevBtn.setEnabled(true);
		}
		panel.add(accessRevBtn);
		panel.add(reviewBtn);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel imgLabel = new JLabel("");
		imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		NewItem.loadImg(i.getImage(), imgLabel, imgWidth);
		panel_1.add(imgLabel);
		
		ItemSlot itemInfo = new ItemSlot(size, pos, user, items, page, i, this, false, true);
		panel_1.add(itemInfo);
		
		if(i.getSeller().equals(user)) {
			JButton restockBtn = new JButton("Restock");
			panel.add(restockBtn);
				restockBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String quantityString = JOptionPane.showInputDialog(null, "Your current stock is " + i.getQuantity() + ". What would you like to set it to?");
						try {
							int val = Integer.valueOf(quantityString);
							if(val >= 0) {
								i.setQuantity(val);
								JOptionPane.showMessageDialog(null,"The stock of your item has been updated");
								itemInfo.setStock(String.valueOf(i.getQuantity()) + " left");
							} else {
								JOptionPane.showMessageDialog(null,"Please set your stock to a positive amount");
							}
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null,"Please set your stock to a positive integer amount");
						}
					}
				});
		
		JButton editBtn = new JButton("Edit Item");
		panel.add(editBtn);
		editBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dimension sizeReturn = getSize();
				Point posReturn = getLocation();
				NewItem.run(null, sizeReturn, posReturn, user, true, i, f);
			}
		});
		
		JButton deleteBtn = new JButton("Delete Item");
		panel.add(deleteBtn);
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				i.deleteItem();
				i.loadItem();
				Dimension sizeReturn = getSize();
				Point posReturn = getLocation();
				Home.run(null, sizeReturn, posReturn, user, f);
				setVisible(false);
			}
		});
		
		}
		
		reviewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dimension sizeReturn = getSize();
				Point posReturn = getLocation();
				ReviewPage.run(null, sizeReturn, posReturn, user, items, page, i, f);
				setVisible(false);
			}
		});
		
		accessRevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dimension sizeReturn = getSize();
				Point posReturn = getLocation();
				PageOfReviews.run(null, sizeReturn, posReturn, i, f, 0, new ArrayList<Integer>(), user);
				setVisible(false);
			}
		});
		
	}
	
	public static void run(String args, Dimension size, Point pos, Account user, ArrayList<Item> items, int page, Item i, JFrame prev) {
		ItemPage itemPage = new ItemPage(size, pos, user, items, page, i, prev);
	}

}