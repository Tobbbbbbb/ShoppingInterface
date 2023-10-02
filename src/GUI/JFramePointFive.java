package GUI;

import Backend.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.*;
import java.util.Timer;

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

public class JFramePointFive extends javax.swing.JFrame {
	
	private static Timer timer;
	private Account user;
	private TimerTask task = new TimerTask() {
		public void run() {
			user.restock();
			JOptionPane.showMessageDialog(null,"Your Cart has been Cleared because you finished ");
		}
	};
	
	public JFramePointFive(Account user) {
		this.user = user;
		timer = new Timer();
		timer.schedule(task, 3600000);
	}
	
}