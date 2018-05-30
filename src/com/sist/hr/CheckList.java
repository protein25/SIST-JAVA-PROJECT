package com.sist.hr;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class CheckList extends Common {
	String path = vo.getFilePathTCL();
	List file = readFile(path);
	List update;

	private JButton saveBtn;
	SHLFrame sFrame = new SHLFrame();
	JPanel n_panel = new JPanel();
	JPanel c_panel = new JPanel();
	JPanel m_panel = new JPanel();
	JPanel w_panel = new JPanel();
	JPanel e_panel = new JPanel();

	JPanel normal = new JPanel();
	JPanel wash = new JPanel();
	JPanel medicine = new JPanel();
	JPanel cloth = new JPanel();
	JPanel etc = new JPanel();

	JLabel n_title = new JLabel("기본 준비물");
	JLabel c_title = new JLabel("의류 & 화장품");
	JLabel w_title = new JLabel("세안도구");
	JLabel m_title = new JLabel("의약품");
	JLabel e_title = new JLabel("기타");

	String[] data1 = { "여권", "지갑", "볼펜", "카메라", "충전기" };
	String[] data2 = { "옷", "속옷", "잠옷", "양말", "겉옷", "화장품", "선크림" };
	String[] data3 = { "샴푸", "린스", "바디워시", "면도기", "수건", "머리끈", "폼클렌징", "면봉" };
	String[] data4 = { "모기약", "소화제", "두통약", "후시딘", "데일밴드", "생리대" };
	String[] data5 = { "선글라스", "물티슈", "우산", "티슈", "렌즈", "손톱깎기", "셀카봉", "삼각대" };

	JCheckBox[] checkBox1 = new JCheckBox[data1.length];
	JCheckBox[] checkBox2 = new JCheckBox[data2.length];
	JCheckBox[] checkBox3 = new JCheckBox[data3.length];
	JCheckBox[] checkBox4 = new JCheckBox[data4.length];
	JCheckBox[] checkBox5 = new JCheckBox[data5.length];

	public CheckList() {
		path = vo.getFilePathTCL();
		file = readFile(path);
		
		sFrame.menuTitle.setText("여행 체크리스트");
		sFrame.menuTitle.setBounds(180, 10, 200, 40);
		sFrame.body.setLayout(null);

		// 뒤로가기
		sFrame.backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sFrame.dispose();
				new MenuMain();
			}
		});
		// 기본
		n_panel.setBounds(25, 115, 135, 215);
		n_title.setBounds(25, 90, 135, 25);
		n_title.setFont(SHLfont.deriveFont(15f));
		n_title.setHorizontalAlignment(SwingConstants.CENTER);
		n_title.setBorder(new LineBorder(Color.white, 2));
		normal = new JPanel(new GridLayout(data1.length, 1));
		for (int i = 0; i < data1.length; i++) {
			checkBox1[i] = new JCheckBox(data1[i]);
			normal.add(checkBox1[i]);

		}
		n_panel.add(normal);
		sFrame.body.add(n_panel);
		sFrame.body.add(n_title);

		// 의류
		c_panel.setBounds(177, 115, 135, 215);
		c_title.setBounds(177, 90, 135, 25);
		c_title.setFont(SHLfont.deriveFont(15f));
		c_title.setHorizontalAlignment(SwingConstants.CENTER);
		c_title.setBorder(new LineBorder(Color.white, 2));
		cloth = new JPanel(new GridLayout(data2.length, 1));
		for (int i = 0; i < data2.length; i++) {
			checkBox2[i] = new JCheckBox(data2[i]);
			cloth.add(checkBox2[i]);

		}
		c_panel.add(cloth);
		sFrame.body.add(c_title);
		sFrame.body.add(c_panel);

		// 세면
		w_panel.setBounds(330, 115, 135, 215);
		w_title.setBounds(330, 90, 135, 25);
		w_title.setFont(SHLfont.deriveFont(15f));
		w_title.setHorizontalAlignment(SwingConstants.CENTER);
		w_title.setBorder(new LineBorder(Color.white, 2));
		wash = new JPanel(new GridLayout(data3.length, 1));
		for (int i = 0; i < data3.length; i++) {
			checkBox3[i] = new JCheckBox(data3[i]);

			wash.add(checkBox3[i]);

		}
		w_panel.add(wash);
		sFrame.body.add(w_title);
		sFrame.body.add(w_panel);

		// 의약품
		m_panel.setBounds(25, 380, 135, 215);
		m_title.setBounds(25, 355, 135, 25);
		m_title.setFont(SHLfont.deriveFont(15f));
		m_title.setHorizontalAlignment(SwingConstants.CENTER);
		m_title.setBorder(new LineBorder(Color.white, 2));
		medicine = new JPanel(new GridLayout(data4.length, 1));
		for (int i = 0; i < data4.length; i++) {
			checkBox4[i] = new JCheckBox(data4[i]);
			medicine.add(checkBox4[i]);

		}
		m_panel.add(medicine);
		sFrame.body.add(m_title);
		sFrame.body.add(m_panel);

		// 기타
		e_panel.setBounds(330, 380, 135, 215);
		e_title.setBounds(330, 355, 135, 25);
		e_title.setFont(SHLfont.deriveFont(15f));
		e_title.setHorizontalAlignment(SwingConstants.CENTER);
		e_title.setBorder(new LineBorder(Color.white, 2));
		etc = new JPanel(new GridLayout(data5.length, 1));
		for (int i = 0; i < data5.length; i++) {
			checkBox5[i] = new JCheckBox(data5[i]);
			etc.add(checkBox5[i]);

		}
		
		setData();
		
		e_panel.add(etc);
		sFrame.body.add(e_title);
		sFrame.body.add(e_panel);
		sFrame.setVisible(true);
		// setData();
		// 저장버튼
		saveBtn = new JButton("저장");
		saveBtn.setBounds(195, 560, 97, 25);
		sFrame.body.add(saveBtn);

		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update = new ArrayList();
				for (int i = 0; i < checkBox1.length; i++) {
					if (checkBox1[i].isSelected()) {
						String x = checkBox1[i].getText();
						update.add(x);

					}
				}
				for (int i = 0; i < checkBox2.length; i++) {

					if (checkBox2[i].isSelected()) {
						String x = checkBox2[i].getText();
						update.add(x);
					}
				}
				for (int i = 0; i < checkBox3.length; i++) {
					if (checkBox3[i].isSelected()) {
						String x = checkBox3[i].getText();
						update.add(x);

					}
				}
				for (int i = 0; i < checkBox4.length; i++) {
					if (checkBox4[i].isSelected()) {
						String x = checkBox4[i].getText();
						update.add(x);

					}
				}
				for (int i = 0; i < checkBox5.length; i++) {
					if (checkBox5[i].isSelected()) {
						String x = checkBox5[i].getText();
						update.add(x);

					}
				}

				if (saveFile(path, update) == successResult) {
					JOptionPane.showMessageDialog(saveBtn, "저장되었습니다.");
				}
			}
		});
	}

	public void setData() {
		if (file.size() > -1) {
			for (int i = 0; i < checkBox1.length; i++) {
				for (int x = 0; x < file.size(); x++) {
//					System.out.println(file.get(x));
//					System.out.println(checkBox1[i]);
					if (checkBox1[i].getText().equals(file.get(x))) {
						checkBox1[i].setSelected(true);
					}
				}
			}
			for (int i = 0; i < checkBox2.length; i++) {
				for (int x = 0; x < file.size(); x++) {
					if (checkBox2[i].getText().equals(file.get(x))) {
						checkBox2[i].setSelected(true);
					}
				}
			}
			for (int i = 0; i < checkBox3.length; i++) {
				for (int x = 0; x < file.size(); x++) {
					if (checkBox3[i].getText().equals(file.get(x))) {
						checkBox3[i].setSelected(true);
					}
				}
			}
			for (int i = 0; i < checkBox4.length; i++) {
				for (int x = 0; x < file.size(); x++) {
					if (checkBox4[i].getText().equals(file.get(x))) {
						checkBox4[i].setSelected(true);
					}
				}
			}
			for (int i = 0; i < checkBox5.length; i++) {
				for (int x = 0; x < file.size(); x++) {
					if (checkBox5[i].getText().equals(file.get(x))) {
						checkBox5[i].setSelected(true);
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		new CheckList();

	}

}
