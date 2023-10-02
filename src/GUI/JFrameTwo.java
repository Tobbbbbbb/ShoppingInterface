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

public class JFrameTwo extends JFramePointFive {
	private JFrame prevFrame;
	
	public JFrameTwo(Account user, JFrameTwo j) {
		super(user);
		prevFrame = j;
	}

	public JFrameTwo(Account user, JFrame j) {
		super(user);
		prevFrame = j;
	}
	
	public void loadPrevFrame() {
		prevFrame.setVisible(true);
	}
	
	public boolean isPrevNull() {
		return Objects.isNull(prevFrame);
	}
	
	public JFrame getPrevFrame() {
		return prevFrame;
	}
}
