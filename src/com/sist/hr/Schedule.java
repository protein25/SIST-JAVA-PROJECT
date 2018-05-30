package com.sist.hr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

public class Schedule extends Common implements ActionListener {

	private SHLFrame sFrame = new SHLFrame();
	private JPanel allDayPanel = new JPanel(); // ���� ���� �г��� ��� ���� �ϳ��� �г�

	private JScrollPane scrollPane;
	private final JButton newDayBtn = new JButton("�� ����");

	ScheduleVO[] tmpVO;

	// ****������ - ������Ʈ���� �����ϰ� �� ��¥ �г��� DayPanel���� �׷��� �����´�.****
	public Schedule() {

		// ȭ���� �׸� ������ ������ �о�� �Ѵ�. (������� Ȯ��)
		tmpVO = readScFile();

		int dayCount = tmpVO.length;
		DayPanel[] dp = new DayPanel[dayCount];

		for (int i = 0; i < dayCount; i++) {
			dp[i] = new DayPanel(tmpVO[i].getTripDate());
		}

		sFrame.menuTitle.setText("����");
		newDayBtn.setBounds(391, 19, 97, 23);

		sFrame.topMenu.add(newDayBtn);
		allDayPanel.setBorder(null);
		allDayPanel.setLayout(new GridLayout(dayCount, 1, 20, 20));

		for (int i = 0; i < dayCount; i++) {
			allDayPanel.add(dp[i]);
		}

		scrollPane = new JScrollPane(allDayPanel);

		// �� ���� ��ư
		newDayBtn.addActionListener(this);

		// �� ������ ���� ����/����/���� ��ư
		for (int i = 0; i < dayCount; i++) {

			dp[i].showBtn.addActionListener(this);
			dp[i].updateBtn.addActionListener(this);
			dp[i].deleteBtn.addActionListener(this);

		}

		scrollPane.setBorder(null);
		scrollPane.setBounds(12, 70, 470, 452);
		scrollPane.setViewportView(allDayPanel);

		// ����ȭ - ��ũ�ѹٰ� ����� ���� ���ܼ� �ּ�ó��
		// allDayPanel.setBackground(new Color(0,0,0,0));
		// scrollPane.setOpaque(false);
		// scrollPane.getViewport().setOpaque(false);

		sFrame.getContentPane().add(scrollPane);
		sFrame.backBtn.addActionListener(this);

		sFrame.setVisible(true);
		sFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	} // --Schedule()

	public static void main(String[] args) {
		// new Schedule();
	} // end of main

	// **** �̺�Ʈ ó�� : �ڷΰ���, �� ����, ���뺸��, ����, ����
	public void actionPerformed(ActionEvent e) {
		JButton tmpBtn = (JButton) e.getSource();

		if (tmpBtn.equals(sFrame.backBtn)) {
			sFrame.dispose();
			new MenuMain();
		} else if (tmpBtn.getText() == "���뺸��") { // ***�� ������ ���� ����***
			String btnDateStr = tmpBtn.getToolTipText();
			System.out.println("btnDateStr" + btnDateStr);
			for (int i = 0; i < tmpVO.length; i++) {
				if (btnDateStr.equals(tmpVO[i].getTripDateToString())) {
					sFrame.dispose();
					System.out.println("getTripDateToString" + tmpVO[i].getTripDateToString());
					new ScheduleDayFrame(tmpVO[i], 2);
				}
			}
		} else if (tmpBtn.getText() == "����") { // ***�� ������ ����***
			// �̹� �ִ� ���� ����
			sFrame.dispose();
			String btnDateStr = tmpBtn.getToolTipText();
			for (int i = 0; i < tmpVO.length; i++) {
				if (btnDateStr.equals(tmpVO[i].getTripDateToString())) {
					new ScheduleDayFrame(tmpVO[i], 3);
				}
			}
		} else if (tmpBtn.getText() == "����") {// ***�� ������ ����***
			// deleteAlert.setVisible(true);
			int answer = JOptionPane.showConfirmDialog(tmpBtn, "�����Ͻðڽ��ϱ�?", "���� Ȯ��", JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION) {

				String btnDateStr = tmpBtn.getToolTipText();
				for (int i = 0; i < tmpVO.length; i++) {
					if (btnDateStr.equals(tmpVO[i].getTripDateToString())) {
						tmpVO[i] = null;
					}
				}

				// ���� ����

				// ���� ������ ���� vo�� List<String>���� �����
				List tmpList = new ArrayList();

				for (int i = 0; i < tmpVO.length; i++) {
					if (tmpVO[i] != null) {
						tmpList.add(tmpVO[i].getVoToString());
					}
				}
				Common.saveFile(Common.vo.getFilePathSchedule(), tmpList);

				sFrame.dispose();
				new Schedule();

			} else if (answer == JOptionPane.NO_OPTION) {
				return;
			}
		} else if (tmpBtn.equals(newDayBtn)) {
			// �� ����
			sFrame.dispose();
			new ScheduleDayFrame(null, 1);
		}
	} // end of actionPerformed

	public static ScheduleVO stringToVO(String tmpDataLine) {
		// ���ο���, ������ �����͸� �߶� ScheduleVO�� �־��ش�.

		ScheduleVO tmpVO = new ScheduleVO();

		// ******* VO��ü�� tripDate �־��ֱ� *******
		int firstIndex = tmpDataLine.indexOf(',');
		String firstData = tmpDataLine.substring(0, firstIndex);

		int yearIndex = firstData.indexOf('/');
		int tmpYear = Integer.parseInt(firstData.substring(0, yearIndex));

		int monthIndex = firstData.indexOf('/', yearIndex + 1);
		int tmpMonth = Integer.parseInt(firstData.substring(yearIndex + 1, monthIndex)) - 1;

		int tmpDayOfMonth = Integer.parseInt(firstData.substring(monthIndex + 1));

		Calendar c = Calendar.getInstance();
		c.set(tmpYear, tmpMonth, tmpDayOfMonth);
		// tmpMonth : 0~11
		tmpVO.setTripDate(c);
		
		// ******* VO��ü�� location �־��ֱ� *******
		String thirdData = tmpDataLine.substring(tmpDataLine.lastIndexOf(',') + 1);
		int thirdIndex = tmpDataLine.lastIndexOf(',');

		// + �����ڷ� �߶� ArrayList�� �ֱ�

		ArrayList tmpLocList = new ArrayList();

		if (!(thirdData.isEmpty())) {
			int numOfLoc = 0;
			int pos = 0;
			int dest = 0;
			while (pos <= dest) {
				pos = (dest <= 0) ? (0) : (dest + 1);
				dest = (thirdData.indexOf('+', pos) < 0) ? thirdData.length() : thirdData.indexOf('+', pos);

				if (pos <= dest) {
					tmpLocList.add(thirdData.substring(pos, dest));
					numOfLoc++;
				}
			} // end of while

		} // end of if(!thirdData.equals(null))
		tmpVO.setLocation(tmpLocList);
		
		// ******* VO��ü�� diary �־��ֱ� *******
		String secondData = tmpDataLine.substring(firstIndex + 1, thirdIndex);
		tmpVO.setDiary(secondData);
		
		System.out.println("getTripDateToString"+tmpVO.getTripDateToString());
		System.out.println("getLocationToString"+tmpVO.getLocationToString());
		System.out.println("getDiary"+tmpVO.getDiary());

		return tmpVO;
	}

	public static ScheduleVO[] readScFile() {
//		String scFilePath = ;

		List tmpData = Common.readFile(Common.vo.getFilePathSchedule());

		int scFileLineCount = 0;
		scFileLineCount = tmpData.size();

		// ScheduleVO �����
		ScheduleVO[] tmpVO = new ScheduleVO[scFileLineCount];

		for (int i = 0; i < scFileLineCount; i++) {
			String tmpDataLine = (String) tmpData.get(i);
			tmpVO[i] = stringToVO(tmpDataLine);
		} // end

		return tmpVO;

	} // end of readFile

	
} // end of class Schedule
