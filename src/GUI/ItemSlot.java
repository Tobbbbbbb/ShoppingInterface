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

public class ItemSlot extends javax.swing.JPanel {
	private JLabel titleLabel;
	private JLabel sellerLabel;
	private JLabel priceLabel;
	private JLabel stockLabel;
	private JLabel reviewLabel;
	private JTextPane descriptionLabel;
	
	public ItemSlot(Dimension size, Point pos, Account user, ArrayList<Item> items, int page, Item i, JFrame f, boolean clickable, boolean left) {
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_2 = new JPanel();
		add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5);
		panel_5.setLayout(new GridLayout(0, 1, 0, 0));
		
		titleLabel = new JLabel(i.getTitle());
		panel_5.add(titleLabel);
		if(clickable) {
			titleLabel.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					ItemPage.run(null, size, pos, user, items, page, i, f);
					f.dispose();
				}
			});
		}
		
		sellerLabel = new JLabel(i.getSeller().getUsername());
		panel_5.add(sellerLabel);
		
		JPanel panel_6 = new JPanel();
		panel_3.add(panel_6);
		panel_6.setLayout(new GridLayout(0, 1, 0, 0));
		
		priceLabel = new JLabel(String.valueOf("$" + i.getPrice()));
		priceLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		panel_6.add(priceLabel);
		
		if(left) {
			stockLabel = new JLabel(String.valueOf(i.getQuantity()) + " left");
		} else {
			stockLabel = new JLabel(String.valueOf(user.getItemQuantity(i)) + " in cart");
		}
		stockLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		panel_6.add(stockLabel);
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		
		descriptionLabel = new JTextPane();
		descriptionLabel.setBackground(new Color(238, 238, 238));
		/*if(clickable) {
			descriptionLabel.setText(separate(i.getDescription(), 100, 3));
		} else {
			descriptionLabel.setText(separate(i.getDescription(), 150));
		}*/
		descriptionLabel.setText(i.getDisplay());
		System.out.println(i.getDescription() + "E");
		System.out.println(i.getDisplay() + "E");
		descriptionLabel.setEditable(false);
		panel_4.add(descriptionLabel);
		if(i.getAverageReview() != -1) {
			reviewLabel = new JLabel(String.valueOf(i.getAverageReview()) + " out of 5 stars");
		} else {
			reviewLabel = new JLabel("Unrated");
		}
		panel_4.add(reviewLabel);
		
		this.setVisible(true);
	}
	
	public String separate(String text, int numChar){
        String[] splitText = text.split(" ");
        int lineLength = 0;
        int curLine = 0;
        boolean newLine = true;
        ArrayList<String> toReturn = new ArrayList<String>();
        toReturn.add("");
        for(int i = 0; i < splitText.length; i++){
            if(splitText[i].length() + toReturn.get(curLine).length() < numChar && newLine){
                String line = toReturn.get(curLine);
                toReturn.set(curLine, line + splitText[i]);
                lineLength += splitText[i].length();
                newLine = false;
            }
            else if(splitText[i].length() + toReturn.get(curLine).length() < numChar - 1 && !newLine){
                String line = toReturn.get(curLine);
                toReturn.set(curLine, line + " " + splitText[i]);
                lineLength += splitText[i].length() + 1;
            }
            else {
                String line = toReturn.get(curLine);
                toReturn.set(curLine, line + "\n");
                curLine++;
                i--;
                lineLength = 0;
                newLine = true;
                if(i != splitText.length - 1){
                    toReturn.add("");
                }
            }
        }
        String finalReturn = "";
        for(int i = 0; i < toReturn.size(); i++){
            finalReturn += toReturn.get(i);
        }
        return finalReturn;
    }
	
	public String separate(String text, int numChar, int numLines){
        String[] splitText = text.split(" ");
        int lineLength = 0;
        int curLine = 0;
        boolean newLine = true;
        int section = 0;
        ArrayList<String> toReturn = new ArrayList<String>();
        toReturn.add("");
        for(int i = 0; i < splitText.length; i++){
            //System.out.println(curLine);
            if(splitText[i].length() + toReturn.get(curLine).length() < numChar && newLine){
                String line = toReturn.get(curLine);
                toReturn.set(curLine, line + splitText[i]);
                lineLength += splitText[i].length();
                newLine = false;
            }
            else if(splitText[i].length() + toReturn.get(curLine).length() < numChar - 1 && !newLine){
                String line = toReturn.get(curLine);
                toReturn.set(curLine, line + " " + splitText[i]);
                lineLength += splitText[i].length() + 1;
            }
            else if(newLine){
                if(curLine == numLines - 1){
                    if(splitText[i].length() > section+numChar-3){
                        toReturn.set(curLine, splitText[i].substring(section, section+numChar-3) + "...");
                    }
                    else
                        toReturn.set(curLine, splitText[i].substring(section) + "...");
                    break;
                }
                else if(splitText[i].length() > section+numChar-1){
                    toReturn.set(curLine, splitText[i].substring(section, section+numChar-1) + "-\n");
                    i--;
                    curLine++;
                    section += numChar-1;
                    lineLength = 0;
                    toReturn.add("");
                }
                else{
                    toReturn.set(curLine, splitText[i].substring(section));
                    lineLength += splitText[i].substring(section).length();
                    section = 0;
                    newLine = false;
                }
            }
            else{
                //System.out.println("oof");
                String line = toReturn.get(curLine);
                if(curLine == numLines - 1){
                    if(line.length() >= numChar-3){
                        line = line.substring(0, numChar-3);
                    }
                    toReturn.set(curLine, line + "...");
                    break;
                }
                toReturn.set(curLine, line + "\n");
                curLine++;
                i--;
                lineLength = 0;
                newLine = true;
                if(i != splitText.length - 1){
                    toReturn.add("");
                }
            }
        }
        String finalReturn = "";
        for(int i = 0; i < toReturn.size(); i++){
            finalReturn += toReturn.get(i);
        }
        return finalReturn;
    }
	
	public void setTitle(String txt) {
		titleLabel.setText(txt);
	}

	public void setSeller(String txt) {
		sellerLabel.setText(txt);
	}
	
	public void setDescription(String txt) {
		descriptionLabel.setText(txt);
	}
	
	public void setPrice(String txt) {
		priceLabel.setText(txt);
	}
	
	public void setReview(String txt) {
		reviewLabel.setText(txt);
	}
	
	public void setStock(String txt) {
		stockLabel.setText(txt);
	}
	
	public String getTitle() {
		return titleLabel.getText();
	}
}