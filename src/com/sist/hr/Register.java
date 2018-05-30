package com.sist.hr;

import javax.swing.SwingConstants;

import com.sist.login.MemberDao;
import com.sist.login.MemberVO;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;

public class Register extends Common {

	private SHLFrame sFrame = new SHLFrame();
	private JTextField textFieldID;
	private JTextField textFieldName;
	private JPasswordField passwordFieldPw;
	private JPasswordField passwordFieldPwc;
	private JTextField textFieldBirth;
	private JRadioButton radioButtonMale;
	private JRadioButton radioButtonFemale;

	private String userId; 		// 사용자ID
	private String name; 		// 이름
	// private String email ;	//이메일
	private String passwd;		// 비번
	// private String lvl ; 	//등급
	// private String grp ; 	//그룹
	private String birthday;	// 생년월일
	private String gender; 		// 성별
	// private String regDt ;	//등록일

	public Register() {

		sFrame.body.setLayout(null);
		sFrame.body.setBounds(0, 60, 500, 561);

		JLabel labelID = new JLabel("\uC544  \uC774  \uB514"); 					// 아이디
		JLabel labelPw = new JLabel("\uBE44 \uBC00 \uBC88 \uD638"); 			// 비밀번호
		JLabel labelPwc = new JLabel("\uBE44\uBC00\uBC88\uD638\uD655\uC778"); 	// 비밀번호확인
		JLabel labelName = new JLabel("\uC774       \uB984");					// 이름
		JLabel labelBirth = new JLabel("\uC0DD \uB144 \uC6D4 \uC77C"); 			// 생년월일
		JLabel labelGender = new JLabel("\uC131       \uBCC4"); 				// 성별
		JLabel labelIDEx = new JLabel("*10\uC790\uB9AC\uC774\uB0B4"); 			// *10글자이내
		JLabel labelBirthEx = new JLabel("ex)20180101"); 						// ex)20180101
		JButton buttonCheck = new JButton("\uC911\uBCF5\uD655\uC778"); 			// 중복확인
		JButton buttonOK = new JButton("\uD655\uC778"); 						// 확인
		JButton buttonCancel = new JButton("\uCDE8\uC18C"); 					// 취소
		radioButtonMale = new JRadioButton("\uB0A8\uC131"); 					// 남성
		radioButtonFemale = new JRadioButton("\uC5EC\uC131"); 					// 여성

		labelID.setHorizontalAlignment(SwingConstants.CENTER);
		labelID.setFont(SHLfont.deriveFont(25f));
		labelID.setBounds(55, 50 + 60, 150, 30);
		labelPw.setHorizontalAlignment(SwingConstants.CENTER);
		labelPw.setFont(SHLfont.deriveFont(25f));
		labelPw.setBounds(55, 120 + 60, 150, 30);
		labelPwc.setHorizontalAlignment(SwingConstants.CENTER);
		labelPwc.setFont(SHLfont.deriveFont(23f));
		labelPwc.setBounds(55, 190 + 60, 150, 30);
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		labelName.setFont(SHLfont.deriveFont(25f));
		labelName.setBounds(55, 260 + 60, 150, 30);
		labelBirth.setHorizontalAlignment(SwingConstants.CENTER);
		labelBirth.setFont(SHLfont.deriveFont(25f));
		labelBirth.setBounds(55, 330 + 60, 150, 30);
		labelGender.setHorizontalAlignment(SwingConstants.CENTER);
		labelGender.setFont(SHLfont.deriveFont(25f));
		labelGender.setBounds(55, 390 + 60, 150, 30);
		labelIDEx.setBounds(250, 79 + 60, 72, 15);
		labelBirthEx.setBounds(250, 359 + 60, 90, 15);
		buttonCheck.setFont(SHLfont.deriveFont(9f));
		buttonCheck.setBounds(380, 50 + 60, 72, 30);
		buttonOK.setBounds(125, 465 + 60, 100, 30);
		buttonCancel.setBounds(275, 465 + 60, 100, 30);
		radioButtonMale.setFont(SHLfont.deriveFont(20f));
		radioButtonMale.setOpaque(false);
		radioButtonMale.setBounds(275, 390 + 60, 65, 30);
		radioButtonFemale.setFont(SHLfont.deriveFont(20f));
		radioButtonFemale.setOpaque(false);
		radioButtonFemale.setBounds(370, 390 + 60, 65, 30);

		textFieldID = new JTextField();
		textFieldName = new JTextField();
		passwordFieldPw = new JPasswordField();
		passwordFieldPwc = new JPasswordField();

		textFieldID.setColumns(10);
		textFieldID.setBounds(250, 50 + 60, 125, 30);
		textFieldName.setColumns(10);
		textFieldName.setBounds(250, 260 + 60, 202, 30);
		passwordFieldPw.setBounds(250, 120 + 60, 202, 30);
		passwordFieldPwc.setBounds(250, 190 + 60, 202, 30);

		textFieldBirth = new JTextField();
		textFieldBirth.setColumns(10);
		textFieldBirth.setBounds(250, 330 + 60, 202, 30);

		sFrame.body.add(labelID);
		sFrame.body.add(labelPw);
		sFrame.body.add(labelPwc);
		sFrame.body.add(labelName);
		sFrame.body.add(labelBirth);
		sFrame.body.add(labelGender);
		sFrame.body.add(labelIDEx);
		sFrame.body.add(labelBirthEx);
		sFrame.body.add(textFieldID);
		sFrame.body.add(textFieldName);
		sFrame.body.add(textFieldBirth);
		sFrame.body.add(passwordFieldPw);
		sFrame.body.add(passwordFieldPwc);
		sFrame.body.add(textFieldID);
		sFrame.body.add(textFieldName);
		sFrame.body.add(textFieldBirth);
		sFrame.body.add(passwordFieldPw);
		sFrame.body.add(passwordFieldPwc);
		sFrame.body.add(buttonCheck);
		sFrame.body.add(buttonOK);
		sFrame.body.add(buttonCancel);
		sFrame.body.add(radioButtonMale);
		sFrame.body.add(radioButtonFemale);

		sFrame.menuTitle.setHorizontalAlignment(SwingConstants.CENTER);
		sFrame.menuTitle.setBounds(175, 10, 150, 40);
		sFrame.menuTitle.setText("\uD68C \uC6D0 \uAC00 \uC785");

		sFrame.menuTitle.setText("회 원 가 입");
		sFrame.menuTitle.setBounds(175, 10, 150, 40);// 220, 10, 60, 40

		// 중복확인버튼 액션
		buttonCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == buttonCheck) {
					idCheck();
				}
			}
		});
		// 확인버튼 액션
		buttonOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == buttonOK) {
					ok();
				}
			}
		});
		// 취소버튼 액션
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == buttonCancel) {
					sFrame.dispose();
					Login login = new Login();
				}
			}
		});
		// 메인메뉴 뒤로가기버튼 액션
		sFrame.backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == sFrame.backBtn) {
					sFrame.dispose();
					Login login = new Login();
				}
			}
		});

		sFrame.setVisible(true);
	}

	boolean idc = false;
	
	public void idCheck() {//중복확인 메소드

		MemberDao dao = new MemberDao();
		MemberVO loginData = new MemberVO();

		userId = textFieldID.getText();
		loginData.setUserId(userId);

		int msg = dao.checkUserId(loginData.getUserId()); // msg 1:중복 / 0:없음
		if (userId.isEmpty()) {
			JOptionPane.showMessageDialog(null, "아이디를 확인하세요.");
		} 
		if (userId.length() > 10) {
			JOptionPane.showMessageDialog(null, "아이디를 확인하세요");
		} 
		if (msg == 1) {
			JOptionPane.showMessageDialog(null, "아이디가 중복되었습니다.");
		} 
		if (msg == 0)
			JOptionPane.showMessageDialog(null, "사용 가능 합니다.");
			this.idc = true;		
	}

	public void ok() {//확인 메소드

		MemberDao dao = new MemberDao();
		MemberVO loginData = new MemberVO();
		userId = textFieldID.getText();
		loginData.setUserId(userId);
		passwd = passwordFieldPw.getText();
		loginData.setPasswd(passwd);
		name = textFieldName.getText();
		loginData.setName(name);
		birthday = textFieldBirth.getText();
		loginData.setBirthday(birthday);
		if (radioButtonFemale.isSelected()) {
			loginData.setGender("2"); // 여성 : 2
		} else if (radioButtonMale.isSelected()) {
			loginData.setGender("1"); // 남성 : 1
		}
		loginData.setEmail("abcd@mail.com");
		loginData.setLvl("1");
		loginData.setGrp("4");
		loginData.setRegDt("11111111");

		int msg = dao.checkUserId(loginData.getUserId()); // msg 1:중복 / 0:없음

		String pwc = passwordFieldPwc.getText();// pw확인값
		
		if (idc == false) {//중복확인 실시했는지 확인
			JOptionPane.showMessageDialog(null, "중복체크를 하세요.");
			return;
		}
		if (!(msg == 0) || userId.length() > 10 || userId.isEmpty()) {//아이디 확인
			JOptionPane.showMessageDialog(null, "아이디를 확인하세요");
			return;
		}
		if (!passwd.equals(pwc) || passwd.isEmpty() || pwc.isEmpty()) {//비번 확인
			JOptionPane.showMessageDialog(null, "비밀번호를 확인하세요");
			return;
		}
		if (name.isEmpty()) {//이름 확인
			JOptionPane.showMessageDialog(null, "이름을 확인하세요");
			return;
		}
		if (!(birthday.length() == 8) || birthday.isEmpty()) {//생년월일 확인
			JOptionPane.showMessageDialog(null, "생년월일을 확인하세요");
			return;
		}
		if ((radioButtonMale.isSelected() == true && radioButtonFemale.isSelected() == true)
				|| (radioButtonMale.isSelected() == false && radioButtonFemale.isSelected() == false)) {//성별 확인
			JOptionPane.showMessageDialog(null, "성별을 확인하세요");
			return;
		}

		MemberVO registerSuccess = dao.memberJoin(loginData);// 회원가입

		JOptionPane.showMessageDialog(null, "환영합니다." + loginData.getName() + "님\n다시 로그인 하세요.");

		sFrame.dispose();
		Login login = new Login();

	}

	public static void main(String[] args) {
		new Register();
	}

	public void readFile() {

	}

	public void writeFile() {

	}

	public void updateFile() {

	}

}
