package com.sist.hr;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Server extends JFrame implements Runnable {

	Vector globalVc = new Vector(); // ��� �����
	Vector roomVc = new Vector(); // �� ����
	JTextArea ta = new JTextArea();
	JScrollPane jscroll = new JScrollPane(ta);

	ServerSocket ss; // ��������

	public Server() {
		super("Server");
		Font cFont = new Font("Dialog", Font.PLAIN, 12);
		ta.setFont(cFont);
		this.add(jscroll, BorderLayout.CENTER);

		setSize(600, 400);
		setVisible(true);
	}// --Server()

	public static void main(String[] args) {
		
		Server server = new Server();
		new Thread(server).start();

	} // end of main

	class Service extends Thread {
		BufferedReader in;
		OutputStream out;
		String name;// ��ȭ��
		RoomVo myroom = new RoomVo();// ������

		public Service(Socket s) {
			try {

				out = s.getOutputStream();
				in = new BufferedReader(new InputStreamReader(s.getInputStream()));

				String msg = in.readLine();
				StringTokenizer st = new StringTokenizer(msg, "|");
				name = st.nextToken();
				myroom.title = st.nextToken();
				

			} catch (IOException e) {
				ta.append("Service Ŭ���̾�Ʈ ���� ���� \n");
				try {
					s.close();
					in.close();
					out.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}

			// ����
			for (int i = 0; i < globalVc.size(); i++) {
				try {
					Service ser = (Service) globalVc.get(i);
					// 100|+ser.name|ser.myroom.title
					String msg = "100|" + ser.name + "|" + ser.myroom.title;

					putMessageTo(msg);
				} catch (Exception e) {
					System.out.println("globalVc====================");
					System.out.println(e.getMessage());
					System.out.println("globalVc====================");
					globalVc.removeElementAt(i--);
				}
			}

			// �濡 ��� ���ִ� ������
			for (int i = 0; i < roomVc.size(); i++) {
				RoomVo r = (RoomVo) roomVc.get(i);
				// /"200|"+title+"\n"
				String msg = "200|" + r.title + "|" + r.inwon;
				try {
					putMessageTo(msg);
				} catch (Exception e) {
					System.out.println("roomVc====================");
					System.out.println(e.getMessage());
					System.out.println("roomVc====================");
					roomVc.removeElementAt(i--);
				}
			}

			globalVc.addElement(this);

			// ��� ����ڿ��� �˸�!
			putMessageAll("100|" + name + "|" + myroom.title);
			this.start();// run ����
		} // --Service()

		/**
		 * Service.run Client �޽��� ����
		 * 
		 * @return void
		 */
		@Override
		public void run() {
			while (true) {
				try {
					if(in == null)return;
					String msg = in.readLine();
		
					
					// msg return
					if (null == msg)
						return;

					ta.append(name + ">" + msg + "\n");

					StringTokenizer st = new StringTokenizer(msg, "|");
					String protocol = st.nextToken();
					switch (protocol) {
					case "200":// "200|"+title+"\n"
					{
						myroom = new RoomVo(st.nextToken(), name, 1);
						myroom.uservc.addElement(this);

						roomVc.addElement(myroom);

						// ������ ����
						putMessageAll("200|" + myroom.title + "|" + 0);
						// ���� -> ������ ������� ����
						putMessageAll("205|" + name + "|" + myroom.title);
					}
						break;

					case "210":// "210|"+title+"\n"
					{
						String t = st.nextToken();
						for (int i = 0; i < roomVc.size(); i++) {
							RoomVo r = (RoomVo) roomVc.elementAt(i);
							if (t.equals(r.title)) {
								myroom = r;
								myroom.uservc.addElement(this);
								myroom.inwon++;
								putMessageAll("205|" + name + "|" + myroom.title);
								break;
							}
						}

					}
						break;

					case "290": {// "290|\n" �泪����
						myroom.uservc.removeElement(this);
						myroom.inwon = myroom.uservc.size();

						if (myroom.inwon == 0) {
							roomVc.removeElement(myroom);
						}

						putMessageAll("290|" + name + "|" + myroom.title);
						myroom = new RoomVo();
						myroom.title = "����";

					}
						break;

					case "400":// "400|"+msg+"\n" ü��
					{
						putMessageRoom("400|" + name + ">>" + st.nextToken());
					}
						break;

					case "450"://// "450|"+sayNm+"|"+say+"\n"(�ͼӸ�)
					{
						String sayNm = st.nextToken();// �̸�
						String sayMsg = st.nextToken();// �޽���

						for (int i = 0; i < myroom.uservc.size(); i++) {
							Service ser = (Service) myroom.uservc.elementAt(i);
							if (sayNm.equals(ser.name) == true) {
								try {
									ser.putMessageTo("450|" + name + "|" + sayMsg);
								} catch (Exception e) {
									System.out.println("450==================");
									myroom.uservc.removeElement(ser);
									System.out.println("450==================");
								}
							}
						}

					}
						break;

					case "900":// "900|\n" ����
						globalVc.removeElement(this);// ��ü ����� �������� ����
						putMessageAll("900|" + name + "|" + myroom.title);

						if (!myroom.title.equals("����")) {
							myroom.uservc.removeElement(this);
							if (--myroom.inwon == 0) {
								roomVc.removeElement(myroom);
							}
						}

						// stream close
						closeProcess();
						// --stream close
						break;

					}// --Service.run switch

				} catch (IOException io) {
					System.out.println("Service run===============================");
					try {
						in.close();
						out.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					io.printStackTrace();
					System.out.println("Service run===============================");
				}
			} // --Service.run.while
		} // --Service.run

		
		public void closeProcess(){
			// stream close
			try {
				in.close();
				out.close();
			} catch (IOException io) {
				System.out.println("=900===================");
				io.printStackTrace();
				System.out.println("=900===================");
			}
		}
		
		/**
		 * �濡 �ִ� ����ڿ��� �˸�
		 * 
		 * @param msg("400|"+name+">>"+st.nextToken())
		 * @return void
		 */
		public void putMessageRoom(String msg) {

			synchronized (this) {

				System.out.println("myroom.uservc.size()" + myroom.uservc.size());
				for (int i = 0; i < myroom.uservc.size(); i++) {
					try {
						Service ser = (Service) myroom.uservc.elementAt(i);
						ser.putMessageTo(msg);
					} catch (Exception e) {
						System.out.println("putMessageRoom=====================");
						myroom.uservc.removeElementAt(i--);
						e.printStackTrace();
						System.out.println("putMessageRoom=====================");
					}
				}

			}
		}

		/**
		 * ��� ����ڿ��� �˸�
		 * 
		 * @param msg
		 * @return void
		 */
		public void putMessageAll(String msg) {
			synchronized (this) {
				for (int i = 0; i < globalVc.size(); i++) {
					Service ser = (Service) globalVc.get(i);
					try {
						ser.putMessageTo(msg);
					} catch (Exception e) {
						System.out.println("=putMessageAll===========================");
						globalVc.removeElementAt(i--);
						System.out.println("=putMessageAll===========================");
					}
				}

			}
		}

		/**
		 * Ư�� User���� Msg����
		 * 
		 * @param msg
		 * @return void
		 */
		public void putMessageTo(String msg) {
			//
			synchronized (this) {
				try {
					out.write((msg + "\r\n").getBytes());
				} catch (IOException e) {
					System.out.println("=putMessageTo===========================");
					e.printStackTrace();
					System.out.println("=putMessageTo===========================");
				}
			}
		}

	} // --Service Class

	@Override
	public void run() {
		
		roomVc.addElement(new RoomVo("����","0",0));
		roomVc.addElement(new RoomVo("����","0",0));
		roomVc.addElement(new RoomVo("�뱸","0",0));
		roomVc.addElement(new RoomVo("�λ�","0",0));
		roomVc.addElement(new RoomVo("����","0",0));
		roomVc.addElement(new RoomVo("����","0",0));
		roomVc.addElement(new RoomVo("���","0",0));
		

		try {
			ss = new ServerSocket(ChatConst.PORT);

			ta.append("Server Start...\n");

		} catch (IOException e) {
			ta.append("Server Start Fail \n");
			e.printStackTrace();
			System.exit(0);
		}

		ta.append("���� ����.. Ŭ���̾�Ʈ ���� ����� \n");
		ta.setCaretPosition(ta.getDocument().getLength()); // �ǾƷ��� ��ũ���Ѵ�.
		Socket s = null;
		while (true) {

			try {
				s = ss.accept();
				ta.append("Ŭ���̾�Ʈ ���� " + s + "\n");
				ta.setCaretPosition(ta.getDocument().getLength()); // �ǾƷ���
																	// ��ũ���Ѵ�.
				Service ser = new Service(s);
			} catch (IOException e) {
				ta.append("Ŭ���̾�Ʈ ���� ���� \n");
				e.printStackTrace();
			}

		}

	} // --run

}
