package com.sist.hr;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.UIManager;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class DetailParsing extends Common {
	// static String href = "https://www.mangoplate.com/restaurants/a6KR_fF0jA";
	static String href;
	SHLFrame frame;
	String title;
	String path;
	int from;

	JPanel imgPanel;
	JScrollPane imgScrollPane;
	JLabel labelImg;
	JLabel labelImg01;
	JLabel labelImg02;
	JLabel labelImg03;
	JLabel labelImg04;

	JPanel infoPanel;
	JScrollPane infoScrollPane;
	JLabel labelPhone;
	JLabel labelTime;
	JLabel labelAddr;
	JLabel labelParking;
	// JLabel labelMenu;
	JTextArea labelMenu;
	JLabel labelKind;
	JLabel labelCost;
	JLabel labelWeekend;

	JButton btnAddFavs;

	JPanel reviewPanel;
	JScrollPane repleScrollPane;
	JLabel user;
	JTextArea reple;
	JLabel replePanel;

	URL url;

	List<String> link;// ���� link ��� list
	Map<String, String> map = new HashMap();// �Ľ� ������ ��� ��
	Vector review = new Vector();

	public DetailParsing(int i, String href) {
		from = i;
		path = vo.getFilePathFavs();
		System.out.println();
		this.href = href;
		setData(href);
		setDatailLayout();
		frame.menuTitle.setText(title);
	}

	public static void main(String[] args) {
		new DetailParsing(restaurant, href);
	}

	public void setDatailLayout() {
		System.out.println("path" + path);
		frame = new SHLFrame();
		frame.body.setBounds(0, 62, 494, 588);

		frame.menuTitle.setBounds(100, 10, 300, 40);
		frame.menuTitle.setHorizontalAlignment(JLabel.CENTER);

		System.out.println(map.values().size());

		infoPanel = new JPanel(new GridLayout(8, 1, 5, 5));
		infoPanel.setBounds(12, 237, 476, 208);
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1, true));

		Font infoFont = SHLfont.deriveFont(15f);

		if (map.containsKey("�ּ�")) {
			labelAddr = new JLabel("�ּ� : " + map.get("�ּ�"));
			labelAddr.setFont(infoFont);
			infoPanel.add(labelAddr);
		}
		if (map.containsKey("��ȭ��ȣ")) {
			labelAddr = new JLabel("��ȭ��ȣ : " + map.get("��ȭ��ȣ"));
			labelAddr.setFont(infoFont);
			infoPanel.add(labelAddr);
		}
		if (map.containsKey("���� ����")) {
			labelAddr = new JLabel("���� ���� : " + map.get("���� ����"));
			labelAddr.setFont(infoFont);
			infoPanel.add(labelAddr);
		}
		if (map.containsKey("���ݴ�")) {
			labelAddr = new JLabel("���ݴ� : " + map.get("���ݴ�"));
			labelAddr.setFont(infoFont);
			infoPanel.add(labelAddr);
		}
		if (map.containsKey("����")) {
			labelAddr = new JLabel("���� : " + map.get("����"));
			labelAddr.setFont(infoFont);
			infoPanel.add(labelAddr);
		}
		if (map.containsKey("�����ð�")) {
			labelAddr = new JLabel("�����ð� : " + map.get("�����ð�"));
			labelAddr.setFont(infoFont);
			infoPanel.add(labelAddr);
		}
		if (map.containsKey("����")) {
			labelAddr = new JLabel("���� : " + map.get("����"));
			labelAddr.setFont(infoFont);
			infoPanel.add(labelAddr);
		}
		if (map.containsKey("�޴�")) {
			labelMenu = new JTextArea("�޴� : " + map.get("�޴�"));
			labelMenu.setFont(infoFont);
			labelMenu.setLineWrap(true);
			labelMenu.setBackground(UIManager.getColor("Label.background"));
			labelMenu.setSize(300, 200);
			infoPanel.add(labelMenu);
		}
		btnAddFavs = new JButton("��");
		btnAddFavs.setBounds(430, 5, 60, 50);
		btnAddFavs.setToolTipText("���ã�⿡ �߰�");
		btnAddFavs.setBackground(Color.GRAY);
		frame.topMenu.add(btnAddFavs);

		// review
		reviewPanel = new JPanel();
		reviewPanel.setBounds(12, 438 + 60, 470, 114);

		// scroll = new JScrollPane(panel);
		// scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// scroll.setBounds(0, 0, 500, 220);

		imgPanel = new JPanel();
		imgPanel.setBounds(0, 0, 1000, 200);
		imgPanel.setBackground(Color.white);

		for (int i = 0; i < link.size(); i++) {
			labelImg = new JLabel();
			labelImg.setIcon(setImage(link.get(i)));
			imgPanel.add(labelImg);
		}

		System.out.println("review vector size : "+review.size());
		if (review.size() > 0) {
			int reviewNum = review.size() / 2;
			int userNum = (int) (Math.random() * reviewNum);

			user = new JLabel("Reviewer : " + (String) review.get(userNum * 2));
			user.setBounds(0, 0, 470, 32);
			user.setHorizontalAlignment(SwingConstants.CENTER);
			user.setFont(SHLfont.deriveFont(15f));
			reple = new JTextArea();
			reple.setBounds(0, 34, 470, 80);
			reple.setBackground(Color.WHITE);
			reple.setRows(10);
			reple.setEditable(false);

			// 210�� �̻��� ����� �ڸ�
			String tmpReple = (String) review.get((userNum * 2) + 1);
			// String tmpReple = (String) review.get(1);

			if (tmpReple.length() > 190) {
				tmpReple = tmpReple.substring(0, 190);
				tmpReple += "...";
			}
			reple.setText(tmpReple);
			reple.setLineWrap(true);
			reviewPanel.setLayout(null);

			reviewPanel.add(user);
			System.out.println(user.getText());
			reviewPanel.add(reple);
		}
		imgScrollPane = new JScrollPane(imgPanel);
		imgScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		imgScrollPane.setBounds(12, 65, 470, 205);

		infoScrollPane = new JScrollPane(infoPanel);
		infoScrollPane.setBounds(12, 220 + 60, 470, 205);
		frame.body.setLayout(null);
		// scroll.add(panel);
		frame.body.add(imgScrollPane);
		frame.body.add(infoScrollPane);
		frame.body.add(reviewPanel);

		frame.setVisible(true);
		btnAddFavs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("DetailParsing filePath : " + path);
				List file = readFile(path);
				int index = 0;
				boolean check = false;
				for (int i = file.size() - 1; i > -1; i--) {
					String line = (String) file.get(i);
					String[] f = line.split(",");
					btnAddFavs.setBackground(Color.GRAY);
					if (f[1].equals(title)) {
						check = true;
						index = i;
					}
				}
				if (check) {
					file.remove(index);
					if (saveFile(path, file) == successResult) {
						JOptionPane.showMessageDialog(btnAddFavs, "���ã�⿡�� �����Ǿ����ϴ�.");
					}
				} else {
					String v = restaurant + "," + title + "," + href;
					System.out.println(v);
					file.add(v);
					btnAddFavs.setBackground(Color.BLUE);
					if (saveFile(path, file) == successResult) {
						JOptionPane.showMessageDialog(btnAddFavs, "����Ǿ����ϴ�.");
					}
				}
			}
		});

		frame.backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				switch (from) {
				case fromMenu:
					frame.dispose();
					new MenuMain();
					break;
				case fromFavs:
					frame.dispose();
					new Favs(fromDetail);
					break;
				case fromData:
					frame.dispose();
					new ParsingData(restaurant);
					break;
				}
			}
		});
	}

	public Icon setImage(String src) {
		URL url;
		Image img;
		ImageIcon icon = new ImageIcon();
		try {
			url = new URL(src);
			img = ImageIO.read(url);
			icon.setImage(img.getScaledInstance(200, 200, Image.SCALE_SMOOTH));
			System.out.println("�̹��� ����");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return icon;
	}

	public void setData(String href) {
		link = new ArrayList<String>();
		try {
			Document detail = Jsoup.connect(href).get();
			title = detail.select("section.restaurant-detail header span.title h1.restaurant_name").text();
			Elements th = detail.select("table.info tr th");
			Elements td = detail.select("table.info tr td");
			Elements figure = detail.select("div.list-photo_wrap figure.list-photo meta");
			Elements reviewers = detail.select("div.review-content figcaption");
			Elements reviews = detail.select("div.review_wraper span.short_review");

			for (int i = 0; i < th.size(); i++) {
				map.put(th.get(i).text(), td.get(i).text());
			}
			System.out.println(map);
			for (int i = 0; i < figure.size(); i++) {
				link.add(figure.get(i).attr("abs:content"));
			}
			for (int i = 0; i < reviews.size(); i++) {
				review.add(reviewers.get(i).text());
				review.add(reviews.get(i).text());
			}
			System.out.println(review);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
