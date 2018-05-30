package com.sist.hr;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SHLFrame extends JFrame {

	public JPanel topMenu = new JPanel();
	public JButton backBtn;
	public JPanel body = new BackgroundImage();
	public JLabel menuTitle = new JLabel();
	public Font font;
	
	public SHLFrame() {
		
		String workSpace = (System.getProperty("user.dir"));
		String sep = File.separator;
		String fontFileName = workSpace + sep + "fonts" + sep + "NANUMGOTHICEXTRABOLD.ttf";
		File fontFile = new File(fontFileName);

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		this.setTitle("여기");
		this.setBounds(200, 200, 500, 650);
		this.setLayout(null);

		topMenu.setLayout(null);

		backBtn = new JButton("<");
		backBtn.setBounds(5, 5, 60, 50);

		menuTitle.setFont(font.deriveFont(25f));
		menuTitle.setBounds(200, 10, 100, 40);

		topMenu.add(backBtn);
		topMenu.add(menuTitle);

		this.setContentPane(body);
		this.getContentPane().setLayout(null);
		topMenu.setBounds(0, 0, 500, 60);
		this.getContentPane().add(topMenu);


//		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	
	public static void main(String[] args) {
//		new SHLFrame();
	}
}

class BackgroundImage extends JPanel {
	private BufferedImage img;
	
	public BackgroundImage(){
        // load the background image
        try {
        	//상대경로
    		String workSpace = (System.getProperty("user.dir"));
    		String sep = File.separator;
    		String fileName = workSpace + sep + "img" + sep + "sky5.jpg";
            img = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	 @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        // paint the background image and scale it to fill the entire space
	        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	    }
}
