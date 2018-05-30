package com.sist.hr;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Chatting Client ������
 * 
 * @Class Name : MainClient.java
 * @Description : MainClient Class
 * @Modification Information
 * @ @ ������ ������ �������� @ --------- --------- ------------------------------- @
 *   2018.03.02 ���ʻ���
 *
 * @author SIST
 * @since 2018.03.12
 * @version 0.3
 * @see
 *
 * 		Copyright (C) by KnJ All right reserved.
 */
public class MainClient extends JFrame implements Runnable, ActionListener, MouseListener {

	// ��ſ� �ʿ��� ����
	Socket s;
	BufferedReader in;
	OutputStream out;

	String name; 
	// getId
	String host = "127.0.0.1";
//	String host = "211.238.142.101"; // server
	//String host = ����IP (svn�ڸ�)
	
	String title;
//String title = getKeyword�� ��������

//	JButton createB = new JButton("�� �����");
//	JButton roominB = new JButton("�����");
//	JButton exitB = new JButton("����");
	

	Cursor cur = new Cursor(Cursor.HAND_CURSOR);

	String[][] data1 = new String[0][2];
	String[] cols1 = { "�� ����", "�ο�" };

	String[][] data2 = new String[0][1];
	String[] cols2 = { "��ȭ��" };

	String[][] data3 = new String[0][2];
	String[] cols3 = { "��ȭ��", "��ġ" };

	DefaultTableModel model1 = new DefaultTableModel(data1, cols1);
	DefaultTableModel model2 = new DefaultTableModel(data2, cols2);
	DefaultTableModel model3 = new DefaultTableModel(data3, cols3);

	JTable table1 = new JTable(model1); // ��
	JTable table2 = new JTable(model2); // ���� �ο� ����
	JTable table3 = new JTable(model3); // ���

	JScrollPane waitroom = new JScrollPane(table1);
	JScrollPane waitroominfo = new JScrollPane(table2);
	JScrollPane waitinfo = new JScrollPane(table3);

	RoomClient client = new RoomClient(); // chatting client

	/**
	 * MainClient �ʱ�ȭ
	 */
	public MainClient() {
		
		
		setCursor(cur);
		setTitle("ä��_Main");
		
		setSize(613, 565);

		// Control ��ġ
		Font cFont = new Font("Dialog", Font.PLAIN, 12);
		Color cColor = new Color(255, 207, 95);
		Color cBtnColor = new Color(85, 214, 211);

		table1.getTableHeader().setReorderingAllowed(false);
		table2.getTableHeader().setReorderingAllowed(false);
		table3.getTableHeader().setReorderingAllowed(false);

		table1.getTableHeader().setBackground(cColor);
		table2.getTableHeader().setBackground(cColor);
		table3.getTableHeader().setBackground(cColor);

		getContentPane().setLayout(null);

		getContentPane().add(waitroom).setBounds(10, 10, 400, 250);
		getContentPane().add(waitroominfo).setBounds(415, 10, 168, 250);
		getContentPane().add(waitinfo).setBounds(10, 270, 400, 197);

//		name = JOptionPane.showInputDialog(null, "�̸��� �Է��ϼ���", "�̸� �Է�", JOptionPane.YES_NO_OPTION);
//		if (null == name || name.length() < 1)
//			return;
		
		name = Common.vo.getId();
		
//		title = JOptionPane.showInputDialog(null, "���ø� �Է��ϼ���", "���� �Է�", JOptionPane.YES_NO_OPTION);
//		if (null == title || title.length() < 1)
//			return;
		
		title = Common.vo.getKeyword();

		// event ����
		eventUp();
		

		

		// Server ����
		connectServer();

		//MainClient ȭ���� ���� �ʿ����.
		//setVisible(true);
//		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                //mediaPlayerComponent.release();
//                System.exit(0);
//            }
//        });
	} // end of Constructor

	private void connectServer() {

		try {
			this.s = new Socket(host, ChatConst.PORT);

			// inputStream
			this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			// outputStream
			this.out = s.getOutputStream();
			
			out.write((name + "|" + title + "\n").getBytes());
			
			if (null == title || title.length() < 1)
				return;
			
			try {
				out.write(("210|" + title + "\n").getBytes());
				
				
			} catch (IOException e1) {
				System.out.println("actionPerformed=�����=====================");
				e1.printStackTrace();
			}


		
			// Thread Start : thread�� ��ӹ��� �ʰ� runnable �������̽��� �����߱� ������ �̷��� �ؾ�
			// �Ѵ�.
			new Thread(this).start();

		} catch (UnknownHostException e) {
			System.out.println("***Server Connection Failed");
			System.out.println("---connectServer.UnknownHostException");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("***Server Connection Failed");
			System.out.println("---connectServer.IOException");
			e.printStackTrace();
		}

	} // end of connectServer

	private void eventUp() {

		// RoomClient
		client.tf.addActionListener(this); // ä�� �Է�:�Ϸ�
		client.nickB.addActionListener(this); // ��ȭ����
		client.sayB.addActionListener(this); // �ӼӸ�:�Ϸ�
		client.outB.addActionListener(this); // ������:�Ϸ�
		client.backBtn.addActionListener(this);

		table1.addMouseListener(this); // �� ���� Ŭ���ϸ� �� ���� �����ֱ�:�Ϸ�

	} // end of eventUp

	public static void main(String[] args) {
		new MainClient();

	} // end of main

	/**
	 * Server Msg ����
	 */
	@Override
	public void run() {
		// xxx|data1|data2|...
		// ex) 100|data1|data2|...

		while (true) {
			try {
				String msg = this.in.readLine();
				if (null == msg || msg.length() < 0)
					return;

				System.out.println("0=======================");
				System.out.println(msg);
				System.out.println("0=======================");

				StringTokenizer st = new StringTokenizer(msg, "|");
				String protocol = st.nextToken();

				switch (protocol) {
				// "100|"+name+"|"+myroom.title
				case "100": // ����
					String[] data = { st.nextToken(), st.nextToken() };
					this.model3.addRow(data);
					break;
				// "200|"+myroom.title+"|"+0
				case "200": // �游���(������)
					String ob[] = { st.nextToken(), st.nextToken() };
					model1.addRow(ob);
					break;
				// "205|"+name+"|"+myroom.title
				case "205": // �游���(����)
					// ����
					String n = st.nextToken();// ��ȭ��
					String t = st.nextToken();// �ű� ���� ��

					// waitinfo
					for (int i = 0; i < model3.getRowCount(); i++) {
						String daehwaName = (String) model3.getValueAt(i, 0);// ��ȭ��
						if (n.equals(daehwaName) == true) {
							model3.setValueAt(t, i, 1);
							break;
						}
					}

					// waitroom: model1
					for (int i = 0; i < model1.getRowCount(); i++) {
						String roomName = (String) model1.getValueAt(i, 0);
						if (t.equals(roomName) == true) {
							int inWon = Integer.parseInt(model1.getValueAt(i, 1).toString());
							model1.setValueAt(++inWon, i, 1);
							break;
						}
					}

					// waitroominfo:model2 ���� �ο� ����
					for (int i = 0; i < model2.getRowCount(); i++) {
						model2.removeRow(i);
					}

					// Local�� Server ��ȭ�� ���Ͻ� ���� ����
					if (n.equals(name) == true) {
						roomInProcess();

					} else if (t.equals(title)) {
						Object[] obD = { n };
						client.model1.addRow(obD);
						client.inwon.setText(client.model1.getRowCount() + "");

						client.ta.append("***[" + n + "]���� ���� �ϼ̽��ϴ�.&^&\n");
					}

					break;
				case "290":// 290|"+name+"|"+myroom.title
				{
					String userNm = st.nextToken();
					String roomT = st.nextToken();

					// waitroom: model1
					for (int i = 0; i < model1.getRowCount(); i++) {
						String tmpRoom = (String) model1.getValueAt(i, 0);// ��
						if (roomT.equals(tmpRoom) == true) {
							int inwon = Integer.parseInt(model1.getValueAt(i, 1).toString());
							if (inwon == 1) {
								model1.removeRow(i);
							} else {
								model1.setValueAt(--inwon, i, 1);
							}
							break;
						}
					}

					// waitinfo: model3
					for (int i = 0; i < model3.getRowCount(); i++) {
						String tmpNm = (String) model3.getValueAt(i, 0);
						if (userNm.equals(tmpNm) == true) {
							model3.setValueAt("����", i, 1);
						}
					}

					if (userNm.equals(name) == true) {
						roomOutProcess();
					} else if (roomT.equals(title) == true) {
						// client.model1
						for (int i = 0; i < client.model1.getRowCount(); i++) {
							String roomUserNm = (String) client.model1.getValueAt(i, 0);
							if(roomUserNm.equals(userNm)==true){
								client.model1.removeRow(i);
								break;
							}
						}
						
						client.inwon.setText(client.model1.getRowCount()+"");
						client.ta.append(" *** ["+userNm+"]���� �����ϼ̽��ϴ�.\n");
						
					}

				}
					break;
				case "400":// "400|"+name+">>"+st.nextToken())
				{
					client.ta.append(" " + st.nextToken() + "\n");
				}
					break;

				case "450":// "450|"+sayNm+"|"+say+"\n"(�ͼӸ�)
					String nm = st.nextToken();
					String msgStr = st.nextToken();
					
					client.ta.setForeground(Color.BLUE);
					client.ta.append("[�ͼӸ�]"+nm+">>"+msgStr+"\n");

					break;
					
				case "900"://"900|" + name + "|" + myroom.title(����)
					String userNm = st.nextToken();//��ȭ��
					String roomNm = st.nextToken();//���̸�
					
					// waitinfo: model3
					for(int i=0;i<model3.getRowCount();i++){
						String tmpNm = (String) model3.getValueAt(i, 0);//��ȭ��
						if(tmpNm.equals(userNm)){
							model3.removeRow(i);
							break;
						}
					}
					
					// waitroom: model1
					if(!roomNm.equals("����")){
						for(int i=0;i<model1.getRowCount();i++){
							String tmpRoomNm = (String) model1.getValueAt(i, 0);
							if(roomNm.equals(tmpRoomNm)){
								int tmpInwon = Integer.parseInt(model1.getValueAt(i, 1).toString());
								if(--tmpInwon == 0){
									model1.removeRow(i);
								}else{
									model1.setValueAt(tmpInwon, i, 1);
								}
								break;
							}
						}
					}
					
					//RoomClient model1
					if(roomNm.equals(title)){
						for(int i=0;i<client.model1.getRowCount();i++){
							String tmpNm = (String) client.model1.getValueAt(i, 0);
							if(tmpNm.equals(userNm)){
								client.model1.removeRow(i);
								break;
							}
						}
					}
					
					//model2
					
					for(int i=0;i<model2.getRowCount();i++){
						int rowCnt = model2.getRowCount();
						if(rowCnt == 0){
							return;
						}else{
							model2.removeRow(i);
						}
					}
					
					client.inwon.setText(client.model1.getRowCount()+"");
					client.ta.setForeground(Color.RED);
					client.ta.append(" *** ["+userNm+"]���� ���� �ϼ̽��ϴ�.***\n");
					
					//ȭ�� �ݱ�
					closeStream();
					setVisible(false);
					this.dispose();
					System.exit(0);
					
					break;
					
				default:
					System.out.println("���������� Ȯ���ϼ���. protocol = " + protocol);
				} // end of switch(protocol)

			} catch (IOException e) {
				System.out.println("================");
				closeStream();
				e.printStackTrace();
				System.out.println("================");
				
			}
		} // end of while(true)

	}

	/**
	 * �泪����
	 */
	public void roomOutProcess() {
		client.ta.setText("");
		client.tf.setText("");
		client.inwon.setText("");

		for (int i = 0; i < client.model1.getRowCount(); i++) {
			client.model1.removeRow(i);
		}

		client.model1.setRowCount(0);
		client.inwon.setText(client.model1.getRowCount() + "");

		client.setVisible(false);
		client.dispose();

		System.gc();

	}

	/**
	 * ���� ����
	 */
	public void roomInProcess() {
		client.setVisible(true);
		client.ta.append("*** [ " + title + "]�濡 ���� �ϼ̽��ϴ�.\n");

		// ���� �濡 ��� ���ִ� ����� ����:waitInfo
		for (int i = 0; i < model3.getRowCount(); i++) {
			String waitInforRoom = (String) model3.getValueAt(i, 1);// ���̸�
			if (waitInforRoom.equals(title) == true) {
				Object[] ob = { model3.getValueAt(i, 0) };// ��ȭ��
				client.model1.addRow(ob);
			}
		}

		client.inwon.setText(client.model1.getRowCount() + "");

	}

	/**
	 * ������ ��ȸ
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		model2.setRowCount(0);

		int row = table1.getSelectedRow();
		String roomTitle = (String) model1.getValueAt(row, 0);

		for (int i = 0; i < model3.getRowCount(); i++) {
			String selectedRoomT = (String) model3.getValueAt(i, 1);
			if (selectedRoomT.equals(roomTitle) == true) {
				Object[] ob = { model3.getValueAt(i, 0) };
				model2.addRow(ob);
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * �泪����
	 */
	public void requestRoomOut() {
		
		client.dispose();
		try {
			out.write(("290|\n").getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		closeProcess();
	}

	public void closeProcess(){

		
		try {
			if(client.isVisible()==true){
				client.setVisible(false);
				client.dispose();
			}
			out.write(("900|\n").getBytes());
			//--stream close
		} catch (IOException e) {
			System.out.println("-closeProcess------------------");
			e.printStackTrace();
			System.out.println("-closeProcess------------------");
		}
	}
	
	
	public void closeStream(){
		try{
			s.close();
			in.close();
			out.close();
		}catch(Exception e){
			System.out.println("-closeProcess-finally-----------------");
			e.printStackTrace();
			System.out.println("-closeProcess-finally-----------------");
		}
	}
	
	
	/**
	 * Button Eventó��
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();

		if (ob == client.backBtn){
			requestRoomOut();
			model1.setRowCount(0);
			model2.setRowCount(0);
			model3.setRowCount(0);
			
			closeProcess();
			new MenuMain();
		}
	
		
		if (ob == client.sayB) {// �ͼӸ�
			int row = client.table1.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(client, "�ͼӸ� ���� ����� ���� �ϼ���.");
				return;
			}

			String sayNm = client.model1.getValueAt(row, 0).toString();
			String say = JOptionPane.showInputDialog(client, "���ϰ� ���� ���� �Է��ϼ���.", "�ͼӸ�", JOptionPane.YES_NO_OPTION);

			if (null == say || say.length() < 1)
				return;

			try {
				out.write(("450|" + sayNm + "|" + say + "\n").getBytes());
				client.ta.append("[�ͼӸ�(to)]" + sayNm + ">>" + say + "\n");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (ob == client.outB) {// �泪����
			requestRoomOut();
			model1.setRowCount(0);
			model2.setRowCount(0);
			model3.setRowCount(0);
			
			closeProcess();
		}

		if (ob == client.tf) {// ��ȭ�ϱ�

			String msg = client.tf.getText().trim();
			if (msg.length() < 1)
				return;

			try {
				out.write(("400|" + msg + "\n").getBytes());
			} catch (IOException e1) {
				System.out.println("actionPerformed=��ȭ�ϱ�=====================");
				e1.printStackTrace();
				System.out.println("actionPerformed=��ȭ�ϱ�=====================");
			}
			//�޽��� �ʱ�ȭ 
			client.tf.setText(null);
		}

		
		}

	}


