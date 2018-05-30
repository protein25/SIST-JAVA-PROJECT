package com.sist.hr;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class Cost extends Common {
	String path;
	SHLFrame sFrame = new SHLFrame();

	private JPanel frame;
	private JTextField moneyField;
	private JLabel totalCost;
	private JTable totalCostList;
	private JTable costSavedList;
	private JScrollPane scrollPane;
	private JTable groupList;
	private JButton costSave;
	private JButton costDelete;
	private JComboBox ChoiceBox;
	private ImageIcon icon;
	DefaultTableModel defaultTableModel;
	DefaultTableModel defaultTableMode03;

	List file;
	Vector<String[]> data = new Vector<String[]>();
	DecimalFormat dc = new DecimalFormat("###,###,###");
	int sumFood;
	int sumEntrance;
	int sumHotel;
	int sumEtc;
	int sumTraffic;

	String d_sumFood;
	String d_sumEntrance;
	String d_sumHotel;
	String d_sumEtc;
	String d_sumTraffic;

	public Cost() {
		// vo.setKeyword("인천");
		// System.out.println(vo.getKeyword());
		path = vo.getFilePathCost();
		file = readFile(path);
		cutList();
		setSum();

		sFrame.menuTitle.setText("비용");
		sFrame.body.setLayout(new GridLayout(1, 3, 20, 20));

		// 뒤로가기 버튼
		sFrame.backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sFrame.dispose();
				new MenuMain();
			}
		});

		icon = new ImageIcon("D:\\HRD_AN\\01_JAVA\\workspace\\SHL\\src\\com\\sist\\hr\\sky5.jpg");
		// 집에서 Test
		// icon = new ImageIcon("C:\\Users\\안소현\\Desktop\\java
		// workspace\\SHL\\src\\com\\sist\\hr\\sky5.jpg");
		frame = new JPanel(null) {
			public void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		// frame.setBackground(Color.PINK);
		// frame.getContentPane().setLayout(null);//

		// 삭제 버튼
		costDelete = new JButton("삭제");
		costDelete.setForeground(Color.BLACK);
		costDelete.setBackground(Color.WHITE);
		costDelete.setBounds(358, 495, 70, 37);
		frame.add(costDelete);

		costDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int row = costSavedList.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "삭제 할 항목을 선택해주세요.");
					return;
				}

				file.remove(row);
				if (saveFile(path, file) == successResult) {
					JOptionPane.showMessageDialog(costDelete, "삭제되었습니다.");
				}

				// saveFile(path, file);

				defaultTableModel.removeRow(row);

				sFrame.dispose();
				new Cost();
			}
		});

		// 저장 버튼
		costSave = new JButton("저장");
		costSave.setBackground(Color.WHITE);
		costSave.setForeground(Color.BLACK);
		costSave.setBounds(287, 495, 70, 37);
		frame.add(costSave);

		costSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 한글 입력했는지 확인 후 한글입력시 오류창
				boolean a = Pattern.matches("^[0-9]*$", moneyField.getText());
				if (a != true) {
					JOptionPane.showMessageDialog(null, "숫자로만 작성해주세요");
					moneyField.setText("");

				}
				if (moneyField.getText() == null || moneyField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "가격을 입력하세요.");
					moneyField.requestFocus();
					return;
				}

				String vo = ChoiceBox.getSelectedItem().toString() + "," + Integer.parseInt(moneyField.getText());
				String[] v = { ChoiceBox.getSelectedItem().toString(), moneyField.getText() };

				file.add(vo);
				defaultTableModel.addRow(v);
				if (saveFile(path, file) == successResult) {
					JOptionPane.showMessageDialog(costSave, "저장되었습니다.");
				}
				sFrame.dispose();
				new Cost();
			}
		});

		// 가격 입력 창
		moneyField = new JTextField();
		moneyField.setBounds(155, 495, 130, 37);
		moneyField.setToolTipText("가격을 입력하세요");
		moneyField.setHorizontalAlignment(SwingConstants.RIGHT);
		moneyField.setColumns(10);
		frame.add(moneyField);

		// "총 사용 금액" 글씨
		totalCost = new JLabel("총 사용금액");
		totalCost.setFont(new Font("휴먼매직체", Font.PLAIN, 25));
		totalCost.setBackground(Color.WHITE);
		totalCost.setForeground(Color.BLACK);
		totalCost.setBounds(25, 25, 141, 37);
		frame.add(totalCost);

		// 글씨 언더바
		JSeparator separator = new JSeparator();
		separator.setBounds(20, 60, 120, 2);
		frame.add(separator);

		// 분류 콤보박스
		String[] costGroup = { "식비", "교통비", "숙박비", "입장료", "기타" };
		ChoiceBox = new JComboBox(costGroup);
		ChoiceBox.setForeground(Color.BLACK);
		ChoiceBox.setBackground(Color.WHITE);
		ChoiceBox.setBounds(75, 495, 78, 37);

		frame.add(ChoiceBox);

		// 총 합계 출력 테이블 (totalCostList)
		String[] columnNames03 = { " " };
		Object[][] rowData03 = { { d_sumFood }, { d_sumTraffic }, { d_sumHotel }, { d_sumEntrance }, { d_sumEtc } };

		defaultTableMode03 = new DefaultTableModel(rowData03, columnNames03) {
			public boolean isCellEditable(int row, int column) {
				return false; // 테이블 글 고정==>더블 클릭시 수정안됨.
			}
		};
		totalCostList = new JTable(defaultTableMode03);
		totalCostList.setRowHeight(35);// 테이블 셀 높이
		totalCostList.setBounds(242, 80, 192, 175);
		frame.add(totalCostList);

		/**
		 * costSavedList 가운데 정렬
		 */
		// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
		DefaultTableCellRenderer tScheduleCellRenderer3 = new DefaultTableCellRenderer();
		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
		tScheduleCellRenderer3.setHorizontalAlignment(SwingConstants.RIGHT);
		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcmCost3 = totalCostList.getColumnModel();
		// 반복문을 이용하여 테이블을 가운데 정렬로 지정
		for (int i = 0; i < tcmCost3.getColumnCount(); i++) {
			tcmCost3.getColumn(i).setCellRenderer(tScheduleCellRenderer3);
		}
		/**
		 * costSavedList 가운데 정렬 끝
		 */

		// 사용내역 스크롤
		scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 290, 384, 170);
		frame.add(scrollPane);

		// 사용내역 출력(costSavedList)
		String[] columnNames01 = { "분류", "비용" };

		Object[][] rowData01 = new String[data.size()][2];
		for (int i = 0; i < data.size(); i++) {
			rowData01[i][0] = data.get(i)[0];
			rowData01[i][1] = data.get(i)[1];

		}

		defaultTableModel = new DefaultTableModel(rowData01, columnNames01) {
			public boolean isCellEditable(int row, int column) {
				return false; // 테이블 글 고정==>더블 클릭시 수정안됨.
			}
		};
		costSavedList = new JTable(defaultTableModel);
		costSavedList.getTableHeader().setReorderingAllowed(false);// 칼럼 헤더 이동
																	// 안되게 하기
		costSavedList.setRowHeight(25);// 테이블 셀 높이
		scrollPane.setViewportView(costSavedList);

		/**
		 * costSavedList 가운데 정렬
		 */
		// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
		DefaultTableCellRenderer tScheduleCellRenderer1 = new DefaultTableCellRenderer();
		DefaultTableCellRenderer tScheduleCellRenderer10 = new DefaultTableCellRenderer();

		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
		tScheduleCellRenderer1.setHorizontalAlignment(SwingConstants.CENTER);
		tScheduleCellRenderer10.setHorizontalAlignment(SwingConstants.RIGHT);

		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcmCost = costSavedList.getColumnModel();

		tcmCost.getColumn(0).setCellRenderer(tScheduleCellRenderer1);
		tcmCost.getColumn(1).setCellRenderer(tScheduleCellRenderer10);

		/**
		 * costSavedList 가운데 정렬 끝
		 */

		// 총 합계 부분 분류 테이블(groupList)
		String[] columnNames02 = { " " };
		Object[][] rowData02 = { { "식비" }, { "교통비" }, { "숙박비" }, { "입장료" }, { "기타" } };

		DefaultTableModel defaultTableModel02 = new DefaultTableModel(rowData02, columnNames02) {
			public boolean isCellEditable(int row, int column) {
				return false; // 테이블 글 고정==>더블 클릭시 수정안됨.
			}
		};
		groupList = new JTable(defaultTableModel02);
		groupList.setBounds(50, 80, 192, 175);
		groupList.setRowHeight(35);// 테이블 셀 높이

		/**
		 * groupList 가운데 정렬
		 */
		// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
		DefaultTableCellRenderer tScheduleCellRenderer2 = new DefaultTableCellRenderer();
		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
		tScheduleCellRenderer2.setHorizontalAlignment(SwingConstants.CENTER);
		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcmCost1 = groupList.getColumnModel();
		// 반복문을 이용하여 테이블을 가운데 정렬로 지정
		for (int i = 0; i < tcmCost1.getColumnCount(); i++) {
			tcmCost1.getColumn(i).setCellRenderer(tScheduleCellRenderer2);
		}
		/**
		 * groupList 가운데 정렬 끝
		 */

		frame.add(groupList);

		sFrame.body.setLayout(null);
		frame.setBounds(0, 60, 500, 650 - 60);
		sFrame.body.add(frame);

		sFrame.setVisible(true);

	}

	// 합계

	public void setSum() {
		for (int i = 0; i < data.size(); i++) {
			String[] v = data.get(i);

			if (v[0].equals("식비")) {
				sumFood += Integer.parseInt(v[1]);
				d_sumFood = dc.format(sumFood);
			} else if (v[0].equals("교통비")) {
				sumTraffic += Integer.parseInt(v[1]);
				d_sumTraffic = dc.format(sumTraffic);
			} else if (v[0].equals("숙박비")) {
				sumHotel += Integer.parseInt(v[1]);
				d_sumHotel = dc.format(sumHotel);
			} else if (v[0].equals("입장료")) {
				sumEntrance += Integer.parseInt(v[1]);
				d_sumEntrance = dc.format(sumEntrance);
			} else {
				sumEtc += Integer.parseInt(v[1]);
				d_sumEtc = dc.format(sumEtc);
			}
		}

	}

	// 잘라주기
	public void cutList() {
		for (int i = 0; i < file.size(); i++) {
			String row = (String) file.get(i);
			String[] group;

			group = row.split(",");
			data.add(group);

		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Cost();
	}

}
