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

public class PageOfReviews extends JFrameTwo {
	
	private JFrame f;
	private int newStart;
	
	public PageOfReviews(Dimension size, Point pos, Item it, JFrame prev, int start, ArrayList<Integer> starts, Account user) {
		super(user, prev);
		
		int lim = 20;
		
		starts.add(start);
		newStart = start;
		f = this;
		
		setResizable(false);
		setVisible(true);
		setTitle("Teqnifiii");
		setSize(new Dimension(960, 540));
		setLocation(pos);
		
		int scaleFactor = 50;
		
		JPanel panel = new JPanel();
		//getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {(int) size.getWidth()};
		gbl_panel.rowHeights = new int[] {(int) (size.getHeight()/2)};
		gbl_panel.columnWeights = new double[]{};
		gbl_panel.rowWeights = new double[]{1.0};
		panel.setLayout(gbl_panel);
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new GridLayout(0, 3, 0, 0));
		
		JButton cancelButton = new JButton("Back to Item");
		panel_2.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadPrevFrame();
				dispose();
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
				PageOfReviews.run(null, sizeReturn, posReturn, it, prev, starts.get(starts.size() - 2), startsNew, user);
				dispose();
			}
		});
		
		JButton nextBtn = new JButton("Next");
		panel_2.add(nextBtn);
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dimension sizeReturn = getSize();
				Point posReturn = getLocation();
				PageOfReviews.run(null, sizeReturn, posReturn, it, prev, newStart, starts, user);
				dispose();
			}
		});
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		
		JLabel title = new JLabel("Reviews for " + it.getTitle());
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(title);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(scrollPane, BorderLayout.EAST);
		
		ArrayList<Review> revs = it.getReviews();
		GridBagConstraints gbc_constraints = new GridBagConstraints();
		gbc_constraints.gridx = 0;
		
		for(int i = 0; i < lim && i < (revs.size() - start); i++) {
			//System.out.println("heyo");
				//gbc_constraints.fill = GridBagConstraints.BOTH;
				//gbc_constraints.insets = new Insets(0, 0, 5, 0);
				gbc_constraints.gridy = i;
				Review r = revs.get(start + i);
				JTextPane temp = new JTextPane();
				temp.setText(r.getReviewer().getEmail() + "\n\n" + r.getRating() + "\n\n" + r.getDisplay());
				Dimension pref = temp.getPreferredSize();
				pref.width = (int)(0.95*this.getWidth());
				temp.setPreferredSize(pref);
				temp.setBackground(new Color(238, 238, 238));
				temp.setEditable(false);
				//System.out.println(itemSlotList.get(i).getTitle());
				panel.add(temp, gbc_constraints);
			newStart = i + 1;
			if(i == (revs.size()-start-1)) {
				nextBtn.setEnabled(false);
			}
		}
	scrollPane.setViewportView(panel);
	
	setVisible(true);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public static void run(String args, Dimension size, Point pos, Item i, JFrame prev, int start, ArrayList<Integer> starts, Account user) {
		PageOfReviews p = new PageOfReviews(size, pos, i, prev, start, starts, user);
	}
	
}