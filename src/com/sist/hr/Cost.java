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
		// vo.setKeyword("��õ");
		// System.out.println(vo.getKeyword());
		path = vo.getFilePathCost();
		file = readFile(path);
		cutList();
		setSum();

		sFrame.menuTitle.setText("���");
		sFrame.body.setLayout(new GridLayout(1, 3, 20, 20));

		// �ڷΰ��� ��ư
		sFrame.backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sFrame.dispose();
				new MenuMain();
			}
		});

		icon = new ImageIcon("D:\\HRD_AN\\01_JAVA\\workspace\\SHL\\src\\com\\sist\\hr\\sky5.jpg");
		// ������ Test
		// icon = new ImageIcon("C:\\Users\\�ȼ���\\Desktop\\java
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

		// ���� ��ư
		costDelete = new JButton("����");
		costDelete.setForeground(Color.BLACK);
		costDelete.setBackground(Color.WHITE);
		costDelete.setBounds(358, 495, 70, 37);
		frame.add(costDelete);

		costDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int row = costSavedList.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "���� �� �׸��� �������ּ���.");
					return;
				}

				file.remove(row);
				if (saveFile(path, file) == successResult) {
					JOptionPane.showMessageDialog(costDelete, "�����Ǿ����ϴ�.");
				}

				// saveFile(path, file);

				defaultTableModel.removeRow(row);

				sFrame.dispose();
				new Cost();
			}
		});

		// ���� ��ư
		costSave = new JButton("����");
		costSave.setBackground(Color.WHITE);
		costSave.setForeground(Color.BLACK);
		costSave.setBounds(287, 495, 70, 37);
		frame.add(costSave);

		costSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �ѱ� �Է��ߴ��� Ȯ�� �� �ѱ��Է½� ����â
				boolean a = Pattern.matches("^[0-9]*$", moneyField.getText());
				if (a != true) {
					JOptionPane.showMessageDialog(null, "���ڷθ� �ۼ����ּ���");
					moneyField.setText("");

				}
				if (moneyField.getText() == null || moneyField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "������ �Է��ϼ���.");
					moneyField.requestFocus();
					return;
				}

				String vo = ChoiceBox.getSelectedItem().toString() + "," + Integer.parseInt(moneyField.getText());
				String[] v = { ChoiceBox.getSelectedItem().toString(), moneyField.getText() };

				file.add(vo);
				defaultTableModel.addRow(v);
				if (saveFile(path, file) == successResult) {
					JOptionPane.showMessageDialog(costSave, "����Ǿ����ϴ�.");
				}
				sFrame.dispose();
				new Cost();
			}
		});

		// ���� �Է� â
		moneyField = new JTextField();
		moneyField.setBounds(155, 495, 130, 37);
		moneyField.setToolTipText("������ �Է��ϼ���");
		moneyField.setHorizontalAlignment(SwingConstants.RIGHT);
		moneyField.setColumns(10);
		frame.add(moneyField);

		// "�� ��� �ݾ�" �۾�
		totalCost = new JLabel("�� ���ݾ�");
		totalCost.setFont(new Font("�޸ո���ü", Font.PLAIN, 25));
		totalCost.setBackground(Color.WHITE);
		totalCost.setForeground(Color.BLACK);
		totalCost.setBounds(25, 25, 141, 37);
		frame.add(totalCost);

		// �۾� �����
		JSeparator separator = new JSeparator();
		separator.setBounds(20, 60, 120, 2);
		frame.add(separator);

		// �з� �޺��ڽ�
		String[] costGroup = { "�ĺ�", "�����", "���ں�", "�����", "��Ÿ" };
		ChoiceBox = new JComboBox(costGroup);
		ChoiceBox.setForeground(Color.BLACK);
		ChoiceBox.setBackground(Color.WHITE);
		ChoiceBox.setBounds(75, 495, 78, 37);

		frame.add(ChoiceBox);

		// �� �հ� ��� ���̺� (totalCostList)
		String[] columnNames03 = { " " };
		Object[][] rowData03 = { { d_sumFood }, { d_sumTraffic }, { d_sumHotel }, { d_sumEntrance }, { d_sumEtc } };

		defaultTableMode03 = new DefaultTableModel(rowData03, columnNames03) {
			public boolean isCellEditable(int row, int column) {
				return false; // ���̺� �� ����==>���� Ŭ���� �����ȵ�.
			}
		};
		totalCostList = new JTable(defaultTableMode03);
		totalCostList.setRowHeight(35);// ���̺� �� ����
		totalCostList.setBounds(242, 80, 192, 175);
		frame.add(totalCostList);

		/**
		 * costSavedList ��� ����
		 */
		// DefaultTableCellHeaderRenderer ���� (��� ������ ����)
		DefaultTableCellRenderer tScheduleCellRenderer3 = new DefaultTableCellRenderer();
		// DefaultTableCellHeaderRenderer�� ������ ��� ���ķ� ����
		tScheduleCellRenderer3.setHorizontalAlignment(SwingConstants.RIGHT);
		// ������ ���̺��� ColumnModel�� ������
		TableColumnModel tcmCost3 = totalCostList.getColumnModel();
		// �ݺ����� �̿��Ͽ� ���̺��� ��� ���ķ� ����
		for (int i = 0; i < tcmCost3.getColumnCount(); i++) {
			tcmCost3.getColumn(i).setCellRenderer(tScheduleCellRenderer3);
		}
		/**
		 * costSavedList ��� ���� ��
		 */

		// ��볻�� ��ũ��
		scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 290, 384, 170);
		frame.add(scrollPane);

		// ��볻�� ���(costSavedList)
		String[] columnNames01 = { "�з�", "���" };

		Object[][] rowData01 = new String[data.size()][2];
		for (int i = 0; i < data.size(); i++) {
			rowData01[i][0] = data.get(i)[0];
			rowData01[i][1] = data.get(i)[1];

		}

		defaultTableModel = new DefaultTableModel(rowData01, columnNames01) {
			public boolean isCellEditable(int row, int column) {
				return false; // ���̺� �� ����==>���� Ŭ���� �����ȵ�.
			}
		};
		costSavedList = new JTable(defaultTableModel);
		costSavedList.getTableHeader().setReorderingAllowed(false);// Į�� ��� �̵�
																	// �ȵǰ� �ϱ�
		costSavedList.setRowHeight(25);// ���̺� �� ����
		scrollPane.setViewportView(costSavedList);

		/**
		 * costSavedList ��� ����
		 */
		// DefaultTableCellHeaderRenderer ���� (��� ������ ����)
		DefaultTableCellRenderer tScheduleCellRenderer1 = new DefaultTableCellRenderer();
		DefaultTableCellRenderer tScheduleCellRenderer10 = new DefaultTableCellRenderer();

		// DefaultTableCellHeaderRenderer�� ������ ��� ���ķ� ����
		tScheduleCellRenderer1.setHorizontalAlignment(SwingConstants.CENTER);
		tScheduleCellRenderer10.setHorizontalAlignment(SwingConstants.RIGHT);

		// ������ ���̺��� ColumnModel�� ������
		TableColumnModel tcmCost = costSavedList.getColumnModel();

		tcmCost.getColumn(0).setCellRenderer(tScheduleCellRenderer1);
		tcmCost.getColumn(1).setCellRenderer(tScheduleCellRenderer10);

		/**
		 * costSavedList ��� ���� ��
		 */

		// �� �հ� �κ� �з� ���̺�(groupList)
		String[] columnNames02 = { " " };
		Object[][] rowData02 = { { "�ĺ�" }, { "�����" }, { "���ں�" }, { "�����" }, { "��Ÿ" } };

		DefaultTableModel defaultTableModel02 = new DefaultTableModel(rowData02, columnNames02) {
			public boolean isCellEditable(int row, int column) {
				return false; // ���̺� �� ����==>���� Ŭ���� �����ȵ�.
			}
		};
		groupList = new JTable(defaultTableModel02);
		groupList.setBounds(50, 80, 192, 175);
		groupList.setRowHeight(35);// ���̺� �� ����

		/**
		 * groupList ��� ����
		 */
		// DefaultTableCellHeaderRenderer ���� (��� ������ ����)
		DefaultTableCellRenderer tScheduleCellRenderer2 = new DefaultTableCellRenderer();
		// DefaultTableCellHeaderRenderer�� ������ ��� ���ķ� ����
		tScheduleCellRenderer2.setHorizontalAlignment(SwingConstants.CENTER);
		// ������ ���̺��� ColumnModel�� ������
		TableColumnModel tcmCost1 = groupList.getColumnModel();
		// �ݺ����� �̿��Ͽ� ���̺��� ��� ���ķ� ����
		for (int i = 0; i < tcmCost1.getColumnCount(); i++) {
			tcmCost1.getColumn(i).setCellRenderer(tScheduleCellRenderer2);
		}
		/**
		 * groupList ��� ���� ��
		 */

		frame.add(groupList);

		sFrame.body.setLayout(null);
		frame.setBounds(0, 60, 500, 650 - 60);
		sFrame.body.add(frame);

		sFrame.setVisible(true);

	}

	// �հ�

	public void setSum() {
		for (int i = 0; i < data.size(); i++) {
			String[] v = data.get(i);

			if (v[0].equals("�ĺ�")) {
				sumFood += Integer.parseInt(v[1]);
				d_sumFood = dc.format(sumFood);
			} else if (v[0].equals("�����")) {
				sumTraffic += Integer.parseInt(v[1]);
				d_sumTraffic = dc.format(sumTraffic);
			} else if (v[0].equals("���ں�")) {
				sumHotel += Integer.parseInt(v[1]);
				d_sumHotel = dc.format(sumHotel);
			} else if (v[0].equals("�����")) {
				sumEntrance += Integer.parseInt(v[1]);
				d_sumEntrance = dc.format(sumEntrance);
			} else {
				sumEtc += Integer.parseInt(v[1]);
				d_sumEtc = dc.format(sumEtc);
			}
		}

	}

	// �߶��ֱ�
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
