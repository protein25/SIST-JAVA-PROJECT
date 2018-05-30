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

	private String userId; 		// �����ID
	private String name; 		// �̸�
	// private String email ;	//�̸���
	private String passwd;		// ���
	// private String lvl ; 	//���
	// private String grp ; 	//�׷�
	private String birthday;	// �������
	private String gender; 		// ����
	// private String regDt ;	//�����

	public Register() {

		sFrame.body.setLayout(null);
		sFrame.body.setBounds(0, 60, 500, 561);

		JLabel labelID = new JLabel("\uC544  \uC774  \uB514"); 					// ���̵�
		JLabel labelPw = new JLabel("\uBE44 \uBC00 \uBC88 \uD638"); 			// ��й�ȣ
		JLabel labelPwc = new JLabel("\uBE44\uBC00\uBC88\uD638\uD655\uC778"); 	// ��й�ȣȮ��
		JLabel labelName = new JLabel("\uC774       \uB984");					// �̸�
		JLabel labelBirth = new JLabel("\uC0DD \uB144 \uC6D4 \uC77C"); 			// �������
		JLabel labelGender = new JLabel("\uC131       \uBCC4"); 				// ����
		JLabel labelIDEx = new JLabel("*10\uC790\uB9AC\uC774\uB0B4"); 			// *10�����̳�
		JLabel labelBirthEx = new JLabel("ex)20180101"); 						// ex)20180101
		JButton buttonCheck = new JButton("\uC911\uBCF5\uD655\uC778"); 			// �ߺ�Ȯ��
		JButton buttonOK = new JButton("\uD655\uC778"); 						// Ȯ��
		JButton buttonCancel = new JButton("\uCDE8\uC18C"); 					// ���
		radioButtonMale = new JRadioButton("\uB0A8\uC131"); 					// ����
		radioButtonFemale = new JRadioButton("\uC5EC\uC131"); 					// ����

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

		sFrame.menuTitle.setText("ȸ �� �� ��");
		sFrame.menuTitle.setBounds(175, 10, 150, 40);// 220, 10, 60, 40

		// �ߺ�Ȯ�ι�ư �׼�
		buttonCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == buttonCheck) {
					idCheck();
				}
			}
		});
		// Ȯ�ι�ư �׼�
		buttonOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == buttonOK) {
					ok();
				}
			}
		});
		// ��ҹ�ư �׼�
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == buttonCancel) {
					sFrame.dispose();
					Login login = new Login();
				}
			}
		});
		// ���θ޴� �ڷΰ����ư �׼�
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
	
	public void idCheck() {//�ߺ�Ȯ�� �޼ҵ�

		MemberDao dao = new MemberDao();
		MemberVO loginData = new MemberVO();

		userId = textFieldID.getText();
		loginData.setUserId(userId);

		int msg = dao.checkUserId(loginData.getUserId()); // msg 1:�ߺ� / 0:����
		if (userId.isEmpty()) {
			JOptionPane.showMessageDialog(null, "���̵� Ȯ���ϼ���.");
		} 
		if (userId.length() > 10) {
			JOptionPane.showMessageDialog(null, "���̵� Ȯ���ϼ���");
		} 
		if (msg == 1) {
			JOptionPane.showMessageDialog(null, "���̵� �ߺ��Ǿ����ϴ�.");
		} 
		if (msg == 0)
			JOptionPane.showMessageDialog(null, "��� ���� �մϴ�.");
			this.idc = true;		
	}

	public void ok() {//Ȯ�� �޼ҵ�

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
			loginData.setGender("2"); // ���� : 2
		} else if (radioButtonMale.isSelected()) {
			loginData.setGender("1"); // ���� : 1
		}
		loginData.setEmail("abcd@mail.com");
		loginData.setLvl("1");
		loginData.setGrp("4");
		loginData.setRegDt("11111111");

		int msg = dao.checkUserId(loginData.getUserId()); // msg 1:�ߺ� / 0:����

		String pwc = passwordFieldPwc.getText();// pwȮ�ΰ�
		
		if (idc == false) {//�ߺ�Ȯ�� �ǽ��ߴ��� Ȯ��
			JOptionPane.showMessageDialog(null, "�ߺ�üũ�� �ϼ���.");
			return;
		}
		if (!(msg == 0) || userId.length() > 10 || userId.isEmpty()) {//���̵� Ȯ��
			JOptionPane.showMessageDialog(null, "���̵� Ȯ���ϼ���");
			return;
		}
		if (!passwd.equals(pwc) || passwd.isEmpty() || pwc.isEmpty()) {//��� Ȯ��
			JOptionPane.showMessageDialog(null, "��й�ȣ�� Ȯ���ϼ���");
			return;
		}
		if (name.isEmpty()) {//�̸� Ȯ��
			JOptionPane.showMessageDialog(null, "�̸��� Ȯ���ϼ���");
			return;
		}
		if (!(birthday.length() == 8) || birthday.isEmpty()) {//������� Ȯ��
			JOptionPane.showMessageDialog(null, "��������� Ȯ���ϼ���");
			return;
		}
		if ((radioButtonMale.isSelected() == true && radioButtonFemale.isSelected() == true)
				|| (radioButtonMale.isSelected() == false && radioButtonFemale.isSelected() == false)) {//���� Ȯ��
			JOptionPane.showMessageDialog(null, "������ Ȯ���ϼ���");
			return;
		}

		MemberVO registerSuccess = dao.memberJoin(loginData);// ȸ������

		JOptionPane.showMessageDialog(null, "ȯ���մϴ�." + loginData.getName() + "��\n�ٽ� �α��� �ϼ���.");

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
