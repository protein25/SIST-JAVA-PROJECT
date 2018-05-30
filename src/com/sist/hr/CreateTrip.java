package com.sist.hr;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class CreateTrip extends Common {
	SHLFrame frame = new SHLFrame();
	JTable tableList;

	String cities[] = { "서울", "인천", "대전", "대구", "부산", "광주", "울산", "제주" };
	JComboBox comboBoxNewTrip = new JComboBox();

	Set keySet = readDir();
	Object[] keywordSet = keySet.toArray();
	Object[][] data = new Object[keySet.size()][1];
	DefaultTableModel model;

	public CreateTrip() {
		System.out.println("test아이디 확인 :" + vo.getId());

		frame.body.setLayout(null);

		frame.menuTitle.setText("\uC5EC \uD589");
		JLabel labelList = new JLabel("\uC5EC\uD589 \uBAA9\uB85D");
		JLabel labelNewTrip = new JLabel("\uC0C8 \uC5EC\uD589");
		JButton buttonSave = new JButton("\uCD94 \uAC00");
		JButton buttonOK = new JButton("\uD655 \uC778");

		JButton buttonDel = new JButton("\uC0AD \uC81C");

		comboBoxNewTrip = new JComboBox(cities);

		// gui
		labelList.setHorizontalAlignment(SwingConstants.CENTER);
		labelList.setFont(SHLfont.deriveFont(25f));
		labelList.setBounds(40, 10+60, 120, 30);
		JScrollPane scrollPaneList = new JScrollPane();
		scrollPaneList.setBounds(20, 50+60, 340, 350);
		frame.menuTitle.setHorizontalAlignment(SwingConstants.CENTER);
		frame.menuTitle.setFont(SHLfont.deriveFont(25f));
		frame.menuTitle.setBounds(220, 10+60, 89, 40);
		frame.body.setBounds(0, 60, 494, 561);
		labelNewTrip.setHorizontalAlignment(SwingConstants.CENTER);
		labelNewTrip.setFont(SHLfont.deriveFont(25f));
		labelNewTrip.setBounds(40, 420+60, 100, 30);
		comboBoxNewTrip.setBackground(Color.WHITE);
		comboBoxNewTrip.setFont(SHLfont.deriveFont(30f));
		comboBoxNewTrip.setForeground(Color.BLACK);
		comboBoxNewTrip.setBounds(20, 460+60, 340, 70);
		buttonSave.setBounds(379, 470+60, 95, 50);
		buttonOK.setBounds(379, 147+60, 95, 50);
		buttonDel.setBounds(379, 236+60, 95, 50);

		frame.body.add(buttonOK);
		frame.body.add(buttonDel);
		frame.body.add(labelList);
		frame.body.add(scrollPaneList);

		tableList = new JTable();
		tableList.setFont(SHLfont.deriveFont(30f));
		tableList.setRowHeight(50);
		scrollPaneList.setViewportView(tableList);
		for (int i = 0; i < keywordSet.length; i++) {
			data[i][0] = (String) keywordSet[i];
		}
		model = new DefaultTableModel(data, new String[] { "도시명" }) {
			public boolean isCellEditable(int row, int column) {
				return false; // 더블 클릭시 수정안됨.
			}
		};
		
		tableList.setModel(model);
		tableList.setForeground(Color.BLACK);
		frame.body.add(labelNewTrip);
		frame.body.add(comboBoxNewTrip);
		frame.body.add(buttonSave);
		frame.backBtn.setText("로그아웃");

		// 메인메뉴 백버튼 액션
		frame.backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btLogOut();
			}
		});
		// 추가버튼 액션
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == buttonSave) {
					String city = (String) comboBoxNewTrip.getSelectedItem();
					city = check(city);
					Object[] v = { city };
					model.addRow(v);
				}
			}
		});
		// 확인버튼 액션
		buttonOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableList.getSelectedRow();
				System.out.println(tableList.getSelectedRow());
				if (row <= -1) {// 아무것도 없는 상태가 -1이라서 일단 -1 넣어놈
					System.out.println("CreatTrip list 아무것도 없음"+row);
				} else
					goMenu();
			}
		});
		// 삭제버튼 액션
		buttonDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = tableList.getSelectedRow();
				int c = tableList.getSelectedColumn();
				System.out.println("select table row : " + n);
				
				if (n == -1){ 
					System.out.println("CreatTrip delete btn selectRow error : "+n);
					return;}
				
				String selKeyword = (String) tableList.getValueAt(n, c);
				vo.setKeyword(selKeyword);
				deleteFile(vo.getFilePathCost());
				deleteFile(vo.getFilePathFavs());
				deleteFile(vo.getFilePathSchedule());
				deleteFile(vo.getFilePathTCL());
				
				model.removeRow(n);
			}
		});

		frame.backBtn.setBounds(5, 5, 89, 50);
		frame.setVisible(true);
	}

	// 메인메뉴 버튼 로그아웃 메소드
	private void btLogOut() {
		int select = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);
		if (select == JOptionPane.YES_OPTION) {

			vo.setId(null);
			vo.setKeyword(null);

			frame.dispose();
			Login login = new Login();
		}
	}

	// 확인버튼 메소드
	private void goMenu() {
		int row = tableList.getSelectedRow();
		int col = tableList.getSelectedColumn();
		String value = (String) tableList.getValueAt(row, col);
		vo.setKeyword(value);
		System.out.println("setkeyword:" + vo.getKeyword());
		
		frame.dispose();
		MenuMain mm = new MenuMain();

	}

	public static void main(String[] args) {
		new CreateTrip();
	}

	public String check(String keyword) {
		String nKey = "";

		int num = 0;
		if (keySet.contains(keyword)) {
			nKey = keyword + "(" + (++num) + ")";
			while (keySet.contains(nKey)) {
				nKey = keyword + "(" + (++num) + ")";
				System.out.println("nKey : " + nKey);
			}
			keySet.add(nKey);
			return nKey;
		}
		keySet.add(keyword);
		return keyword;
	}
}
