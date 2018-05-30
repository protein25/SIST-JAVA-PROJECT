package com.sist.hr;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * ���� ���� �ϳ� �׸���
 * 
 * @author ������
 *
 */

public class DayPanel extends JPanel {
	Calendar cDate;
	private JLabel dayLabel = new JLabel("��¥:");
	private JLabel dateLabel;
	JButton showBtn = new JButton("���뺸��");
	JButton updateBtn = new JButton("����");
	JButton deleteBtn = new JButton("����");
	Font font;
	
	public DayPanel(Calendar cDate) {
		
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
		
		this.cDate = cDate;

		this.add(dayLabel, BorderLayout.WEST);
		dateLabel = new JLabel(cDateToString(cDate));

		// --��Ʈ
		dayLabel.setFont(font.deriveFont(15f));
		dateLabel.setFont(font.deriveFont(15f));
		showBtn.setFont(font.deriveFont(15f));
		updateBtn.setFont(font.deriveFont(15f));
		deleteBtn.setFont(font.deriveFont(15f));

		this.add(dateLabel, BorderLayout.WEST);
		this.add(showBtn, BorderLayout.EAST);
		this.add(updateBtn, BorderLayout.EAST);
		this.add(deleteBtn, BorderLayout.EAST);

		this.setSize(100, 100);
//		dayLabel.setOpaque(false);
//		dateLabel.setOpaque(false);
		
//		this.setOpaque(false);
		showBtn.setToolTipText(cDateToString(cDate));
		updateBtn.setToolTipText(cDateToString(cDate));
		deleteBtn.setToolTipText(cDateToString(cDate));

	} // end of constructor

	public DayPanel() {
	}

	public String cDateToString(Calendar cDate) {

		int tmpMonth = cDate.get(Calendar.MONTH) + 1;
		String tmpMonthStr = null;

		if (tmpMonth < 10) {
			tmpMonthStr = 0 + Integer.toString(tmpMonth);
		} else {
			tmpMonthStr = Integer.toString(tmpMonth);
		}

		int tmpDayOfMonth = cDate.get(Calendar.DAY_OF_MONTH);
		String tmpDayOfMonthStr = null;
		if (tmpDayOfMonth < 10) {
			tmpDayOfMonthStr = 0 + Integer.toString(tmpDayOfMonth);
		} else {
			tmpDayOfMonthStr = Integer.toString(tmpDayOfMonth);
		}

		return cDate.get(Calendar.YEAR) + "/" + (tmpMonthStr) + "/" + tmpDayOfMonthStr;
	}

	;

} // end of class
