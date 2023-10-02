package GUI;

import Backend.*;

import java.awt.Color;
import java.awt.Component;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class SideButtons extends javax.swing.JPanel {
	
	public SideButtons(JFrame j, Account user, Item i, int p) {
		setLayout(new GridLayout(3, 1, 0, 0));
		
		JLabel priceLbl = new JLabel("Price of " + user.getItemQuantity(i) + ": $" + Math.round(user.getItemQuantity(i)*i.getPrice() * 100)/(double)100);
		add(priceLbl);
		
		JButton addBtn = new JButton("Add To Cart");
		add(addBtn);
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String quantityString = JOptionPane.showInputDialog(null, "How many?");
				int quantity = Integer.valueOf(quantityString);
				if(quantity > 0) {
					if (quantity <= i.getQuantity()) {
						i.setQuantity(i.getQuantity()-quantity);
						user.removeFromCart(i, -1*quantity);
						Dimension sizeReturn = j.getSize();
						Point posReturn = j.getLocation();
						DisplayItems.run(null, sizeReturn, posReturn, user, user.getCart().getItems(), p, true, -1, -1, j, 0, new ArrayList<Integer>(), false, false);
						j.setVisible(false);
					} else {
						JOptionPane.showMessageDialog(null,"There are not enough items available to fill your request");
					}
				} else {
					JOptionPane.showMessageDialog(null,"Please order an integer number of items");
				}
			}
		});
		
		JButton removeBtn = new JButton("Remove From Cart");
		add(removeBtn);
		removeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String quantityString = JOptionPane.showInputDialog(null, "How many?");
				int quantity = Integer.valueOf(quantityString);
				if(quantity > 0) {
					if (quantity <= user.getItemQuantity(i)) {
						i.setQuantity(i.getQuantity()+quantity);
						user.removeFromCart(i, quantity);
						Dimension sizeReturn = j.getSize();
						Point posReturn = j.getLocation();
						DisplayItems.run(null, sizeReturn, posReturn, user, user.getCart().getItems(), p, true, -1, -1, j, 0, new ArrayList<Integer>(), false, false);
						j.setVisible(false);
					} else {
						JOptionPane.showMessageDialog(null,"There are not enough items in your cart to fill your request");
					}
				} else {
					JOptionPane.showMessageDialog(null,"Please order an integer number of items");
				}
			}
		});
	}
	
}