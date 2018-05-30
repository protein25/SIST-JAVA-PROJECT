package com.sist.hr;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import com.sist.login.HRConst;
import com.sist.login.MemberDao;
import com.sist.login.MemberVO;

/**
 * 
 * @author sist1
 *
 *         //Loginó�� //ex) //MemberDao dao=new MemberDao(); //MemberVO
 *         loginData=new MemberVO(); //loginData.setUserId(id);
 *         //loginData.setPasswd(pw);
 *         //--------------------------------------------- MemberDao dao=new
 *         MemberDao(); MemberVO loginData=new MemberVO();
 *         loginData.setUserId(id); loginData.setPasswd(pw);
 * 
 *         MemberVO loginSuccess = dao.do_login(loginData); String msg = "";
 *         if(!loginSuccess.getMessage().equals("")){ msg =
 *         loginSuccess.getMessage(); }else{ msg =loginSuccess.getName()+"�� �α���
 *         �Ǿ����ϴ�."; } System.out.println(loginSuccess);
 * 
 *         // �α��� ������ ��� test JOptionPane.showMessageDialog(null, msg);
 * 
 *         //Member����� ���� ���� HRConst.session = loginSuccess;
 * 
 * 
 *         //ȸ������ //ȸ�����Կ� �ʿ��� ����
 * 
 *         private String userId ; �����ID private String name ; �̸� private String
 *         email ; �̸��� private String passwd ; ��� private String lvl ; ���
 *         private String grp ; �׷� private String birthday ; ������� private String
 *         gender ; ���� private String regDt ; �����
 * 
 * 
 *         dao.memberJoin(loginData);
 * 
 *         //ȸ������ : ������ ���� dao.do_update(loginData);
 * 
 *         //ȸ������ dao.do_update(loginData);
 * 
 *         //ID�� ������ 1, �׷�ġ ������ 0 dao.checkUserId(loginData.getUserId());
 * 
 *         //ID�� ������ 1, �׷�ġ ������ 0 dao.checkPasswd(loginData.getUserId(),
 *         loginData.getPasswd());
 * 
 */
public class Login extends Common {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField_1;

	public static void main(String[] args) {

		new Login();

	}

	public Login() {

		frame = new JFrame();
		frame.setBounds(100, 100, 500, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblId = new JLabel("ID");
		lblId.setForeground(Color.WHITE);
		lblId.setFont(SHLfont.deriveFont(25f));
		lblId.setBounds(83, 244, 59, 39);
		lblId.setBorder(new LineBorder(Color.white, 2));
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblId);

		JLabel lblPw = new JLabel("PW");
		lblPw.setForeground(Color.WHITE);
		lblPw.setFont(SHLfont.deriveFont(25f));
		lblPw.setBounds(83, 293, 59, 39);
		lblPw.setBorder(new LineBorder(Color.white, 2));
		lblPw.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblPw);

		JButton button = new JButton("�α���");
		button.setForeground(Color.BLACK);
		button.setFont(SHLfont.deriveFont(25f));
		button.setBounds(142, 388, 200, 39);
		// button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setBorder(new LineBorder(Color.white, 2));
		frame.getContentPane().add(button);

		JButton button1 = new JButton("ȸ������");
		button1.setFont(SHLfont.deriveFont(25f));
		button1.setBounds(142, 437, 200, 39);
		// button1.setContentAreaFilled(false);
		button1.setFocusPainted(false);
		button1.setBorder(new LineBorder(Color.white, 2));
		frame.getContentPane().add(button1);

		JButton button2 = new JButton("����");
		button2.setFont(SHLfont.deriveFont(25f));
		button2.setBounds(142, 543, 200, 39);
		// button2.setContentAreaFilled(false);
		button2.setFocusPainted(false);
		button2.setBorder(new LineBorder(Color.white, 2));
		frame.getContentPane().add(button2);

		// ID �Է�
		textField = new JTextField();
		textField.setBounds(154, 244, 220, 39);
		textField.setBorder(new LineBorder(Color.ORANGE, 2));
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		textField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					enter();
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});

		// PW �Է�
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(154, 293, 220, 39);
		passwordField_1.setBorder(new LineBorder(Color.ORANGE, 2));
		frame.getContentPane().add(passwordField_1);

		passwordField_1.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					enter();
				}
			}
		});

		// ����̹���

		String workSpace = (System.getProperty("user.dir"));
		String sep = File.separator;
		int bgNo = (int)(Math.random()*3 +1); //1 2 3
		String bgNoStr = bgNo + "";
		String bgFileName = workSpace + sep + "img" + sep + "bg" + bgNoStr + ".jpg";
		
		JButton btnNewButton = new JButton(new ImageIcon(bgFileName));

		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBounds(0, 0, 484, 611);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setFocusPainted(false);
		
		// ȸ������
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == button1) {
					frame.dispose();
					Register register = new Register();
				}
			}
		});

		// ����
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// �α���

		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == button) {

					enter();

				}

			}

		});

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	private void goCT() {
		frame.dispose();
		CreateTrip ct = new CreateTrip();
	}

	public void enter() {
		String id = textField.getText();
		String pwc = passwordField_1.getText();

		// ���̵�üũ
		if (null == id || id.equals("") == true) {
			JOptionPane.showMessageDialog(null, "���̵� �Է� �ϼ���");
			textField.requestFocus();
			return;
		}

		// ���̵� �ڸ���üũ
		if (id.length() > 11) {
			JOptionPane.showMessageDialog(null, "ID�� 10�ڸ��� �Է°����մϴ�.");
			textField.requestFocus();
			return;
		}

		// ��й�ȣ üũ
		if (null == pwc || pwc.equals("") == true) {
			JOptionPane.showMessageDialog(null, "��� ��ȣ�� Ʋ�Ƚ��ϴ�.");
			passwordField_1.requestFocus();
			return;
		}

		MemberDao dao = new MemberDao();
		MemberVO loginData = new MemberVO();
		loginData.setUserId(textField.getText());
		char[] pass = passwordField_1.getPassword();
		String pw = new String(pass);
		loginData.setPasswd(pw);

		MemberVO loginSuccess = dao.do_login(loginData);
		String msg = "";

		if (!loginSuccess.getMessageDiv().equals("11")) { // getMessageDiv
			msg = loginSuccess.getMessage();
			JOptionPane.showMessageDialog(null, msg);
			return;
		} else {
			HRConst.session = loginSuccess;
			msg = loginSuccess.getName() + "�� �α��� �Ǿ����ϴ�.";
			JOptionPane.showMessageDialog(null, msg);

			vo.setId(loginData.getUserId());

			System.out.println(loginSuccess);
			// �α���â ����
			frame.dispose();
			// ����������
			CreateTrip ct = new CreateTrip();

		}
	}

}