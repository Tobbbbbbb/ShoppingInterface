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

public class NewItem extends JFrameTwo {
	private JTextField textField;
	private JTextField titleField;
	private JTextField priceField;
	private JTextField quantityField;
	private File imgFile;
	
	private JFrame j;
	
	public NewItem(Dimension size, Point pos, Account user, boolean edit, Item it, JFrame prev) {
		super(user, prev);
		j = this;
		
		setResizable(false);
		setVisible(true);
		setTitle("Teqnifiii");
		setSize(size);
		setLocation(pos);
		
		//Home.ToolBar(this, user, textField);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.WEST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {375};
		gbl_panel.rowHeights = new int[] {300, 150};
		gbl_panel.columnWeights = new double[]{0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0};
		panel.setLayout(gbl_panel);
		
		JLabel imageLabel = new JLabel("");
		GridBagConstraints gbc_imageLabel = new GridBagConstraints();
		gbc_imageLabel.insets = new Insets(0, 0, 5, 0);
		gbc_imageLabel.gridx = 0;
		gbc_imageLabel.gridy = 0;
		panel.add(imageLabel, gbc_imageLabel);
		if(edit) {
			imgFile = it.getImage();
			loadImg(it.getImage(), imageLabel, 200);
		}
		
		JButton imageButton = new JButton("Upload Image");
		GridBagConstraints gbc_imageButton = new GridBagConstraints();
		gbc_imageButton.insets = new Insets(0, 0, 5, 0);
		gbc_imageButton.fill = GridBagConstraints.VERTICAL;
		gbc_imageButton.gridx = 0;
		gbc_imageButton.gridy = 1;
		panel.add(imageButton, gbc_imageButton);
		imageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "JPG & PNG Files", "jpg", "jpeg", "png");
			    fileChooser.setFileFilter(filter);
			    int returnVal = fileChooser.showOpenDialog(getParent());
			    imgFile = fileChooser.getSelectedFile();
			    loadImg(imgFile, imageLabel, 200);
			}
		});
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{588, 0};
		gbl_panel_1.rowHeights = new int[] {90, 264, 90};
		gbl_panel_1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0};
		panel_1.setLayout(gbl_panel_1);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		panel_1.add(panel_3, gbc_panel_3);
		panel_3.setLayout(new GridLayout(0, 3, 0, 0));
		
		JLabel titleLabel = new JLabel("Title:");
		panel_3.add(titleLabel);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel priceLabel = new JLabel("Price:");
		panel_3.add(priceLabel);
		priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel quantityLabel = new JLabel("Quantity:");
		quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(quantityLabel);
		
		titleField = new JTextField();
		if(edit) {
			titleField.setText(it.getTitle());
		}
		panel_3.add(titleField);
		titleField.setColumns(10);
		
		priceField = new JTextField();
		if(edit) {
			priceField.setText(Double.toString(it.getPrice()));
		}
		panel_3.add(priceField);
		priceField.setColumns(10);
		
		quantityField = new JTextField();
		if(edit) {
			quantityField.setText(Integer.toString(it.getQuantity()));
		}
		quantityField.setEnabled(!edit);
		panel_3.add(quantityField);
		quantityField.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 1;
		panel_1.add(panel_4, gbc_panel_4);
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel descriptionLabel = new JLabel("Description:");
		panel_4.add(descriptionLabel);
		descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JTextPane descriptionField = new JTextPane();
		if(edit) {
			descriptionField.setText(it.getDescription());
		}
		panel_4.add(descriptionField);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 2;
		panel_1.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton submitButton = new JButton("Submit");
		panel_2.add(submitButton);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean newItem = true;
				try {
					Double.valueOf(priceField.getText());
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null,"Please enter a valid price");
					newItem = false;
				}
				try {
					Integer.valueOf(quantityField.getText());
				} catch(Exception ex) {
					if(!edit) {
					JOptionPane.showMessageDialog(null,"Please enter an integer quantity");
					newItem = false;
					}
				}
				String titleText = titleField.getText();
				String descriptionText = descriptionField.getText();
				ArrayList<Item> myItems = user.getMyItems();
				boolean included = false;
				for(Item j : myItems) {
					//System.out.println(j.getTitle());
					if((j.getTitle().equals(titleText) && !edit) || (j.getTitle().equals(titleText) && edit && !j.getTitle().equals(it.getTitle()))) {
						included = true;
					}
				}
				if(newItem && titleText.length() > 0 && descriptionText.length() > 0 && !included) {
					Item i = new Item(titleText, descriptionText, Double.valueOf(priceField.getText()), Integer.valueOf(quantityField.getText()), user);
					if(!Item.doesDatabaseExist()) {
						Item.newItemDatabase();
					}
					if(!edit) {
						i.createNewFile();
						if(!Objects.isNull(imgFile)) {
							i.setImage(imgFile);
						} else {
							i.setImage(Item.getDefault());
						}
					} else {
						ArrayList<String> data = new ArrayList<String>();
						data.add(titleText);
						data.add(descriptionText);
						data.add(priceField.getText());
						it.updateFile(data);
						it.resetImage(imgFile);
						it.loadItem();
					}
					Dimension sizeReturn = getSize();
					Point posReturn = getLocation();
					user.loadAccount();
					Home.run(null, sizeReturn, posReturn, user, j);
					dispose();
				} else if(titleText.length() <= 0) {
					JOptionPane.showMessageDialog(null,"Please enter a title");
				} else if(descriptionText.length() <= 0) {
					JOptionPane.showMessageDialog(null,"Please enter a description");
				} else if(included) {
					JOptionPane.showMessageDialog(null,"You are already selling an item with this name");
				}
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		panel_2.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dimension sizeReturn = getSize();
				Point posReturn = getLocation();
				Home.run(null, sizeReturn, posReturn, user, j);
				dispose();
			}
		});
		
		JPanel panel_5 = new JPanel();
		getContentPane().add(panel_5, BorderLayout.NORTH);
		
		JLabel title = new JLabel("Create New Item");
		if(edit) {
			title.setText("Edit Item");
		}
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		panel_5.add(title);
		
		addWindowListener(new java.awt.event.WindowAdapter(){
			@Override public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				user.logOut();
				user.restock();
				System.exit(0);
			}
		});
	}
	
	public static void loadImg(File imgFile, JLabel imageLabel, int scalar) {
		try {
			Image image = ImageIO.read(imgFile);
			Image resized = image.getScaledInstance(scalar, image.getHeight(null)*scalar/image.getWidth(null), Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(resized);
			imageLabel.setIcon(icon);
		} catch (IOException e1) {
		}
	}
	
	public static void run(String args, Dimension size, Point pos, Account user, boolean edit, Item i, JFrame prev) {
		NewItem n = new NewItem(size, pos, user, edit, i, prev);
	}
	
}
