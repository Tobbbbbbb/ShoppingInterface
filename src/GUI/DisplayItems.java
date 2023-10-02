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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class DisplayItems extends JFrameTwo {
	
	private String[] choices = {
			"Price (Low to High)",
			"Price (High to Low)",
			"Featured",
			"Average Review"
	};
	
	private String[] choices2 = {
			"$25 and under",
			"$25 - $50",
			"$50 - $100",
			"$100 - $200",
			"$200 and above",
			"all"
	};
	
	private JFrame f;
	private ArrayList<Item> itemss;
	private int newStart;
	
	public DisplayItems(Dimension size, Point pos, Account user, ArrayList<Item> items, int pageLength, boolean cart, int filter, int sort, JFrame prev, int start, ArrayList<Integer> starts, boolean hist, boolean myItems) {
		super(user, prev);
		starts.add(start);
		itemss = items;
		newStart = start;
		f = this;
		
		setResizable(false);
		setVisible(true);
		setTitle("Teqnifiii");
		setSize(size);
		setLocation(pos);
		
		//System.out.println("bruh");
		Home.ToolBar(this, user, "displayItems", "myItems", 0, hist);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(scrollPane, BorderLayout.EAST);
		
		int imgWidth = 250;
		int scaleFactor = 50;
		int buttonWidth = 150;
		
		JPanel panel = new JPanel();
		//getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		if(items.size() == 0) {
			gbl_panel.columnWidths = new int[] {(int) size.getWidth()};
		} else if(!cart) {
			gbl_panel.columnWidths = new int[] {imgWidth + scaleFactor, (int) (size.getWidth()-imgWidth - scaleFactor)};
		} else {
			gbl_panel.columnWidths = new int[] {imgWidth + scaleFactor, (int) (size.getWidth()-imgWidth - buttonWidth - scaleFactor), buttonWidth};
		}
		gbl_panel.rowHeights = new int[] {(int) (size.getHeight()/2)};
		gbl_panel.columnWeights = new double[]{};
		gbl_panel.rowWeights = new double[]{1.0};
		panel.setLayout(gbl_panel);
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new GridLayout(0, 7, 0, 0));
		
		JLabel SortBy = new JLabel("Sort By:");
		SortBy.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(SortBy);
		
		JComboBox comboBox = new JComboBox(choices);
		comboBox.setSelectedIndex(-1);
		panel_2.add(comboBox);
		
		JLabel filterBy = new JLabel("Filter By:");
		filterBy.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(filterBy);
		
		JComboBox comboBox_1 = new JComboBox(choices2);
		comboBox_1.setSelectedIndex(-1);
		panel_2.add(comboBox_1);
		
		JButton submitBtn = new JButton("Submit");
		panel_2.add(submitBtn);
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dimension sizeReturn = getSize();
				Point posReturn = getLocation();
				DisplayItems.run(null, sizeReturn, posReturn, user, items, 0, false, comboBox_1.getSelectedIndex(), comboBox.getSelectedIndex(), prev, start, starts, hist, myItems);
				setVisible(false);
			}
		});
		
		JButton backBtn = new JButton("Back");
		panel_2.add(backBtn);
		if(start == 0) {
			backBtn.setEnabled(false);
		}
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Integer> startsNew = new ArrayList<Integer>();
				for(int i = 0; i < starts.size() - 2; i++) {
					startsNew.add(starts.get(i));
				}
				Dimension sizeReturn = getSize();
				Point posReturn = getLocation();
				DisplayItems.run(null, sizeReturn, posReturn, user, items, 0, false, -1, -1, prev, starts.get(starts.size() - 2), startsNew, hist, myItems);
				dispose();
			}
		});
		
		JButton nextBtn = new JButton("Next");
		panel_2.add(nextBtn);
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dimension sizeReturn = getSize();
				Point posReturn = getLocation();
				DisplayItems.run(null, sizeReturn, posReturn, user, items, 0, false, -1, -1, prev, newStart, starts, hist, myItems);
				dispose();
			}
		});
		
		//how many items per pageLength
		int lim = 20;
		
		ArrayList<ItemSlot> itemSlotList = new ArrayList<ItemSlot>();
		ArrayList<JLabel> itemImages = new ArrayList<JLabel>();
		GridBagConstraints gbc_constraints = new GridBagConstraints();
		ArrayList<Item> itemsTemp;
		if(filter == 0) {
			itemsTemp = Item.filterPrice(items, 0, 25);
		} else if (filter == 1) {
			itemsTemp = Item.filterPrice(items, 25, 50);
		} else if (filter == 2) {
			itemsTemp = Item.filterPrice(items, 50, 100);
		} else if (filter == 3) {
			itemsTemp = Item.filterPrice(items, 100, 200);
		} else if (filter == 4){
			itemsTemp = Item.filterPrice(items, 200, Integer.MAX_VALUE);
		} else {
			itemsTemp = items;
		}
		if(sort == 0) {
			itemsTemp = Item.sortPriceLow(itemsTemp);
		} else if (sort == 1) {
			itemsTemp = Item.sortPriceHigh(itemsTemp);
		} else if (sort == 3) {
			itemsTemp = Item.sortReview(itemsTemp);
		} else if(!cart && !hist){
			itemsTemp = Item.sortValue(itemsTemp);
		} else if(!hist){
			itemsTemp = Item.sortQuantity(itemsTemp);
		}
		if(itemsTemp.size() != 0) {
			for(int i = 0; i < lim && i < (itemsTemp.size() - start); i++) {
				//System.out.println("heyo");
				if(itemsTemp.get(start + i).getEnabled()) {
					//gbc_constraints.fill = GridBagConstraints.BOTH;
					//gbc_constraints.insets = new Insets(0, 0, 5, 0);
					gbc_constraints.gridx = 0;
					gbc_constraints.gridy = i;
					Item j = itemsTemp.get(start + i);
					ItemSlot temp;
					if(!cart) {
						temp = new ItemSlot(size, pos, user, itemsTemp, pageLength, j, this, true, true);
					} else {
						temp = new ItemSlot(size, pos, user, itemsTemp, pageLength, j, this, true, false);
					}
					if(!cart) {
						temp.setPreferredSize(new Dimension((int)size.getWidth() - (int)(1.5*imgWidth), (int)size.getHeight()/2));
					} else {
						temp.setPreferredSize(new Dimension((int)size.getWidth() - (int)(1.5*(imgWidth+buttonWidth)), (int)size.getHeight()/2));
					}
					//System.out.println(temp.getTitle());
					itemSlotList.add(temp);
					
					//System.out.println(itemSlotList.get(i).getTitle());
					JLabel imgLbl = new JLabel();
						imgLbl.addMouseListener(new MouseAdapter() {
							public void mouseReleased(MouseEvent e) {
								ItemPage.run(null, size, pos, user, items, pageLength, j, new DisplayItems(size, pos, user, items, pageLength, cart, -1, -1, prev, 0, new ArrayList<Integer>(), hist, myItems));
								dispose();
							}
						});
					NewItem.loadImg(j.getImage(), imgLbl, imgWidth);
					itemImages.add(imgLbl);
					panel.add(imgLbl, gbc_constraints);
					gbc_constraints.gridx = 1;
					panel.add(temp, gbc_constraints);
					if(cart) {
						gbc_constraints.gridx = 2;
						panel.add(new SideButtons(this, user, j, pageLength), gbc_constraints);
					}
				} else {
					lim++;
				}
				newStart = i + 1;
				if(i == (itemsTemp.size()-start-1)) {
					nextBtn.setEnabled(false);
				}
			}
		} else {
			JLabel lbl = new JLabel();
			if(myItems) {
				lbl = new JLabel("You Are Currently Selling No Items");
			} else if(!cart) {
				lbl = new JLabel("No Items Fit Your Search");
			} else {
				lbl = new JLabel("No Items Are In Your Cart");
			}
			panel.add(lbl, 0);
		}
		scrollPane.setViewportView(panel);
		
		
		if(cart) {
			JPanel panel_1 = new JPanel();
			getContentPane().add(panel_1, BorderLayout.SOUTH);
			panel_1.setLayout(new GridLayout(0, 2, 0, 0));
			
			JLabel priceLabel = new JLabel("Total Price: $" + user.getCartTotal());
			priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
			panel_1.add(priceLabel);
			
			JButton checkOutButton = new JButton("Check Out");
			panel_1.add(checkOutButton);
			
			checkOutButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					user.checkOut();
					user.loadAccount();
					Dimension sizeReturn = getSize();
					Point posReturn = getLocation();
					Home.run(null, sizeReturn, posReturn, user, f);
					dispose();
				}
			});
		}
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}
	
	public ArrayList<Item> getItems() {
		return itemss;
	}
	
	public static void run(String args, Dimension size, Point pos, Account user, ArrayList<Item> items, int pageLength, boolean cart, int filter, int sort, JFrame prev, int start, ArrayList<Integer> starts, boolean hist, boolean myItems) {
		DisplayItems d = new DisplayItems(size, pos, user, items, pageLength, cart, filter, sort, prev, start, starts, hist, myItems);
	}
	
}