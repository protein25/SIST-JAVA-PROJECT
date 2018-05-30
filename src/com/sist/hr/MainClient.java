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
 * Chatting Client 시작점
 * 
 * @Class Name : MainClient.java
 * @Description : MainClient Class
 * @Modification Information
 * @ @ 수정일 수정자 수정내용 @ --------- --------- ------------------------------- @
 *   2018.03.02 최초생성
 *
 * @author SIST
 * @since 2018.03.12
 * @version 0.3
 * @see
 *
 * 		Copyright (C) by KnJ All right reserved.
 */
public class MainClient extends JFrame implements Runnable, ActionListener, MouseListener {

	// 통신에 필요한 변수
	Socket s;
	BufferedReader in;
	OutputStream out;

	String name; 
	// getId
	String host = "127.0.0.1";
//	String host = "211.238.142.101"; // server
	//String host = 고정IP (svn자리)
	
	String title;
//String title = getKeyword로 가져오기

//	JButton createB = new JButton("방 만들기");
//	JButton roominB = new JButton("방들어가기");
//	JButton exitB = new JButton("종료");
	

	Cursor cur = new Cursor(Cursor.HAND_CURSOR);

	String[][] data1 = new String[0][2];
	String[] cols1 = { "방 제목", "인원" };

	String[][] data2 = new String[0][1];
	String[] cols2 = { "대화명" };

	String[][] data3 = new String[0][2];
	String[] cols3 = { "대화명", "위치" };

	DefaultTableModel model1 = new DefaultTableModel(data1, cols1);
	DefaultTableModel model2 = new DefaultTableModel(data2, cols2);
	DefaultTableModel model3 = new DefaultTableModel(data3, cols3);

	JTable table1 = new JTable(model1); // 방
	JTable table2 = new JTable(model2); // 방의 인원 정보
	JTable table3 = new JTable(model3); // 대기

	JScrollPane waitroom = new JScrollPane(table1);
	JScrollPane waitroominfo = new JScrollPane(table2);
	JScrollPane waitinfo = new JScrollPane(table3);

	RoomClient client = new RoomClient(); // chatting client

	/**
	 * MainClient 초기화
	 */
	public MainClient() {
		
		
		setCursor(cur);
		setTitle("채팅_Main");
		
		setSize(613, 565);

		// Control 배치
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

//		name = JOptionPane.showInputDialog(null, "이름을 입력하세요", "이름 입력", JOptionPane.YES_NO_OPTION);
//		if (null == name || name.length() < 1)
//			return;
		
		name = Common.vo.getId();
		
//		title = JOptionPane.showInputDialog(null, "도시를 입력하세요", "도시 입력", JOptionPane.YES_NO_OPTION);
//		if (null == title || title.length() < 1)
//			return;
		
		title = Common.vo.getKeyword();

		// event 감지
		eventUp();
		

		

		// Server 연결
		connectServer();

		//MainClient 화면은 보일 필요없음.
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
				System.out.println("actionPerformed=방들어가기=====================");
				e1.printStackTrace();
			}


		
			// Thread Start : thread를 상속받지 않고 runnable 인터페이스를 구현했기 때문에 이렇게 해야
			// 한다.
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
		client.tf.addActionListener(this); // 채팅 입력:완료
		client.nickB.addActionListener(this); // 대화명변경
		client.sayB.addActionListener(this); // 귓속말:완료
		client.outB.addActionListener(this); // 나가기:완료
		client.backBtn.addActionListener(this);

		table1.addMouseListener(this); // 방 제목 클릭하면 방 정보 보여주기:완료

	} // end of eventUp

	public static void main(String[] args) {
		new MainClient();

	} // end of main

	/**
	 * Server Msg 수신
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
				case "100": // 대기실
					String[] data = { st.nextToken(), st.nextToken() };
					this.model3.addRow(data);
					break;
				// "200|"+myroom.title+"|"+0
				case "200": // 방만들기(방정보)
					String ob[] = { st.nextToken(), st.nextToken() };
					model1.addRow(ob);
					break;
				// "205|"+name+"|"+myroom.title
				case "205": // 방만들기(대기실)
					// 대기실
					String n = st.nextToken();// 대화명
					String t = st.nextToken();// 신규 생성 방

					// waitinfo
					for (int i = 0; i < model3.getRowCount(); i++) {
						String daehwaName = (String) model3.getValueAt(i, 0);// 대화명
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

					// waitroominfo:model2 방의 인원 정보
					for (int i = 0; i < model2.getRowCount(); i++) {
						model2.removeRow(i);
					}

					// Local과 Server 대화명 동일시 방들어 가기
					if (n.equals(name) == true) {
						roomInProcess();

					} else if (t.equals(title)) {
						Object[] obD = { n };
						client.model1.addRow(obD);
						client.inwon.setText(client.model1.getRowCount() + "");

						client.ta.append("***[" + n + "]님이 입장 하셨습니다.&^&\n");
					}

					break;
				case "290":// 290|"+name+"|"+myroom.title
				{
					String userNm = st.nextToken();
					String roomT = st.nextToken();

					// waitroom: model1
					for (int i = 0; i < model1.getRowCount(); i++) {
						String tmpRoom = (String) model1.getValueAt(i, 0);// 방
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
							model3.setValueAt("대기실", i, 1);
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
						client.ta.append(" *** ["+userNm+"]님이 퇴장하셨습니다.\n");
						
					}

				}
					break;
				case "400":// "400|"+name+">>"+st.nextToken())
				{
					client.ta.append(" " + st.nextToken() + "\n");
				}
					break;

				case "450":// "450|"+sayNm+"|"+say+"\n"(귀속말)
					String nm = st.nextToken();
					String msgStr = st.nextToken();
					
					client.ta.setForeground(Color.BLUE);
					client.ta.append("[귀속말]"+nm+">>"+msgStr+"\n");

					break;
					
				case "900"://"900|" + name + "|" + myroom.title(종료)
					String userNm = st.nextToken();//대화명
					String roomNm = st.nextToken();//방이름
					
					// waitinfo: model3
					for(int i=0;i<model3.getRowCount();i++){
						String tmpNm = (String) model3.getValueAt(i, 0);//대화명
						if(tmpNm.equals(userNm)){
							model3.removeRow(i);
							break;
						}
					}
					
					// waitroom: model1
					if(!roomNm.equals("대기실")){
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
					client.ta.append(" *** ["+userNm+"]님이 퇴장 하셨습니다.***\n");
					
					//화면 닫기
					closeStream();
					setVisible(false);
					this.dispose();
					System.exit(0);
					
					break;
					
				default:
					System.out.println("프로토콜을 확인하세요. protocol = " + protocol);
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
	 * 방나가기
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
	 * 방들어 가기
	 */
	public void roomInProcess() {
		client.setVisible(true);
		client.ta.append("*** [ " + title + "]방에 입장 하셨습니다.\n");

		// 현재 방에 들어 와있는 사람들 추출:waitInfo
		for (int i = 0; i < model3.getRowCount(); i++) {
			String waitInforRoom = (String) model3.getValueAt(i, 1);// 방이름
			if (waitInforRoom.equals(title) == true) {
				Object[] ob = { model3.getValueAt(i, 0) };// 대화명
				client.model1.addRow(ob);
			}
		}

		client.inwon.setText(client.model1.getRowCount() + "");

	}

	/**
	 * 방정보 조회
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
	 * 방나가기
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
	 * Button Event처리
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
	
		
		if (ob == client.sayB) {// 귀속말
			int row = client.table1.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(client, "귀속말 보낼 대상을 선택 하세요.");
				return;
			}

			String sayNm = client.model1.getValueAt(row, 0).toString();
			String say = JOptionPane.showInputDialog(client, "전하고 싶은 말을 입력하세요.", "귀속말", JOptionPane.YES_NO_OPTION);

			if (null == say || say.length() < 1)
				return;

			try {
				out.write(("450|" + sayNm + "|" + say + "\n").getBytes());
				client.ta.append("[귀속말(to)]" + sayNm + ">>" + say + "\n");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (ob == client.outB) {// 방나가기
			requestRoomOut();
			model1.setRowCount(0);
			model2.setRowCount(0);
			model3.setRowCount(0);
			
			closeProcess();
		}

		if (ob == client.tf) {// 대화하기

			String msg = client.tf.getText().trim();
			if (msg.length() < 1)
				return;

			try {
				out.write(("400|" + msg + "\n").getBytes());
			} catch (IOException e1) {
				System.out.println("actionPerformed=대화하기=====================");
				e1.printStackTrace();
				System.out.println("actionPerformed=대화하기=====================");
			}
			//메시지 초기화 
			client.tf.setText(null);
		}

		
		}

	}


