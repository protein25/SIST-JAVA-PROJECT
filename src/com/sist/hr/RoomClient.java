package com.sist.hr;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Chatting Room
 * @Class Name : RoomClient.java
 * @Description : RoomClient Class
 * @Modification Information
 * @ @ ������ ������ �������� @ --------- --------- ------------------------------- @
 *   2018.03.02 ���ʻ���
 *
 * @author SIST
 * @since 2018.03.12
 * @version 0.3
 * @see
 *
 *   Copyright (C) by KnJ All right reserved.
 */

public class RoomClient extends JFrame {

	SHLFrame frame = new SHLFrame();
	JButton backBtn = frame.backBtn;
	
	//��ȭ�� Table
	String[][] data1 = new String[0][1];
	String[] col1 = {"��ȭ��"};
	DefaultTableModel model1 = new DefaultTableModel(data1, col1);
	JTable table1 = new JTable(model1);
	JScrollPane roomInfo = new JScrollPane(table1);
	//--��ȭ�� Table
	
	JLabel lab1 = new JLabel("�����ο�:");
	JLabel inwon = new JLabel("0");
	
	JTextArea ta = new JTextArea(); // ä�� ���� ���
	JScrollPane jscroll=new JScrollPane(ta);
	JTextField tf = new JTextField(); // ä�� ���� �Է�
	
	JButton colorB = new JButton("���ڻ�����");
	JButton nickB = new JButton("��ȭ����");
	JButton sayB = new JButton("�ӼӸ�");
	JButton outB = new JButton("������");
	
	 /**
	  * ä�ù��� �ʱ�ȭ�Ѵ�.
	  * @param
	  */
	public RoomClient(){
//		super("RoomClient");
//		//Layout �ʱ�ȭ: null Layout
//		getContentPane().setLayout(null);
		//Control ��ġ
		Font cFont = new Font("Dialog",Font.BOLD,12);
		Color cColor = new Color(85, 214, 211);
		
		frame.menuTitle.setText("ä��");
		
		//JButton
		colorB.setBackground(cColor);
		colorB.setFont(cFont);
		nickB.setBackground(cColor);
		nickB.setFont(cFont);
		sayB.setBackground(cColor);
		sayB.setFont(cFont);
		outB.setBackground(cColor);
		outB.setFont(cFont);
		//--JButton
		

		//JLabel
		lab1.setFont(cFont);
		inwon.setFont(cFont);
		//--JLabel
		
		//JTable
		table1.getTableHeader().setReorderingAllowed(false);
		table1.getTableHeader().setBackground(new Color(61,232,205));
		//--JTable
		
//		backBtn.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				frame.dispose();
//				new MenuMain();
//				
//			}
//		});
		
		
		ta.setEditable(false);
		frame.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e){
				tf.requestFocus();
			}
		});

		//--Control ��ġ
//		setSize(610,520);
//		setVisible(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame.getContentPane().add(jscroll).setBounds(10,70,300,450);
		frame.getContentPane().add(tf).setBounds(10,520,300,30);
		frame.getContentPane().add(roomInfo).setBounds(320, 70, 168, 200);
		frame.getContentPane().add(lab1).setBounds(315, 270, 80, 30);
		frame.getContentPane().add(inwon).setBounds(400, 270, 80, 30);

		frame.setVisible(true);

	}

	 /** TODO: Commentó�� �� �� 
	  * ä�ù��� �ʱ�ȭ�Ѵ�.
	  * @param args
	  * @return void
	  */
//	public static void main(String[] args) {
//		new RoomClient();
//	} // end of main

}
