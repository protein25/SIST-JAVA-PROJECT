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

	Vector globalVc = new Vector(); // 모든 사용자
	Vector roomVc = new Vector(); // 방 정보
	JTextArea ta = new JTextArea();
	JScrollPane jscroll = new JScrollPane(ta);

	ServerSocket ss; // 서버소켓

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
		String name;// 대화명
		RoomVo myroom = new RoomVo();// 방정보

		public Service(Socket s) {
			try {

				out = s.getOutputStream();
				in = new BufferedReader(new InputStreamReader(s.getInputStream()));

				String msg = in.readLine();
				StringTokenizer st = new StringTokenizer(msg, "|");
				name = st.nextToken();
				myroom.title = st.nextToken();
				

			} catch (IOException e) {
				ta.append("Service 클라이언트 접속 오류 \n");
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

			// 전원
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

			// 방에 들어 와있는 참가자
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

			// 모든 사용자에게 알림!
			putMessageAll("100|" + name + "|" + myroom.title);
			this.start();// run 실행
		} // --Service()

		/**
		 * Service.run Client 메시지 수신
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

						// 방정보 생성
						putMessageAll("200|" + myroom.title + "|" + 0);
						// 대기실 -> 생성한 방명으로 변경
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

					case "290": {// "290|\n" 방나가기
						myroom.uservc.removeElement(this);
						myroom.inwon = myroom.uservc.size();

						if (myroom.inwon == 0) {
							roomVc.removeElement(myroom);
						}

						putMessageAll("290|" + name + "|" + myroom.title);
						myroom = new RoomVo();
						myroom.title = "대기실";

					}
						break;

					case "400":// "400|"+msg+"\n" 체팅
					{
						putMessageRoom("400|" + name + ">>" + st.nextToken());
					}
						break;

					case "450"://// "450|"+sayNm+"|"+say+"\n"(귀속말)
					{
						String sayNm = st.nextToken();// 이름
						String sayMsg = st.nextToken();// 메시지

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

					case "900":// "900|\n" 종료
						globalVc.removeElement(this);// 전체 사용자 정보에서 제거
						putMessageAll("900|" + name + "|" + myroom.title);

						if (!myroom.title.equals("대기실")) {
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
		 * 방에 있는 사용자에게 알림
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
		 * 모든 사용자에게 알림
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
		 * 특정 User에게 Msg전송
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
		
		roomVc.addElement(new RoomVo("서울","0",0));
		roomVc.addElement(new RoomVo("대전","0",0));
		roomVc.addElement(new RoomVo("대구","0",0));
		roomVc.addElement(new RoomVo("부산","0",0));
		roomVc.addElement(new RoomVo("제주","0",0));
		roomVc.addElement(new RoomVo("광주","0",0));
		roomVc.addElement(new RoomVo("울산","0",0));
		

		try {
			ss = new ServerSocket(ChatConst.PORT);

			ta.append("Server Start...\n");

		} catch (IOException e) {
			ta.append("Server Start Fail \n");
			e.printStackTrace();
			System.exit(0);
		}

		ta.append("서버 실행.. 클라이언트 연결 대기중 \n");
		ta.setCaretPosition(ta.getDocument().getLength()); // 맨아래로 스크롤한다.
		Socket s = null;
		while (true) {

			try {
				s = ss.accept();
				ta.append("클라이언트 접속 " + s + "\n");
				ta.setCaretPosition(ta.getDocument().getLength()); // 맨아래로
																	// 스크롤한다.
				Service ser = new Service(s);
			} catch (IOException e) {
				ta.append("클라이언트 접속 오류 \n");
				e.printStackTrace();
			}

		}

	} // --run

}
