package com.sist.hr;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MenuMain extends Common implements ActionListener {

	SHLFrame mainFrame = new SHLFrame();
	private JButton place;
	private JButton restaurant;
	private JButton schedule;
	private JButton cost;
	private JButton favs;
	private JButton	tcl;
	private JButton chat;

	public MenuMain() {
		setMainMenuLayout();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MenuMain();
	}

	private void setMainMenuLayout() {
		mainFrame.setVisible(true);
		mainFrame.menuTitle.setText("메인");

		JPanel body = mainFrame.body;

		body.setLayout(null);

		mainFrame.backBtn.addActionListener(this);

		place = new JButton("관광지");
		restaurant = new JButton("맛집");
		schedule = new JButton("일정");
		cost = new JButton("비용");
		favs = new JButton("즐겨찾기");
		tcl = new JButton("체크리스트");
		chat = new JButton("채팅");

		place.setFont(SHLfont.deriveFont(24f));
		restaurant.setFont(SHLfont.deriveFont(24f));
		schedule.setFont(SHLfont.deriveFont(24f));
		cost.setFont(SHLfont.deriveFont(24f));
		favs.setFont(SHLfont.deriveFont(24f));
		tcl.setFont(SHLfont.deriveFont(24f));

		place.setBounds(7, 10+60, 480, 85);
		restaurant.setBounds(7, 100+60, 480, 85);
		schedule.setBounds(7, 190+60, 480, 85);
		cost.setBounds(7, 280+60, 480, 85);
		favs.setBounds(7, 372+60, 480, 85);
		tcl.setBounds(7, 465+60, 480, 85);
		chat.setBounds(10,10,50,30);
		
		place.addActionListener(this);
		restaurant.addActionListener(this);
		schedule.addActionListener(this);
		cost.addActionListener(this);
		favs.addActionListener(this);
		tcl.addActionListener(this);
		chat.addActionListener(this);
		
		chat.setBounds(425, 5, 60, 50);
		mainFrame.topMenu.add(chat);
		body.add(place);
		body.add(restaurant);
		body.add(schedule);
		body.add(cost);
		body.add(favs);
		body.add(tcl);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton b = (JButton) e.getSource();
		if (b==mainFrame.backBtn) {
			mainFrame.dispose();
			CreateTrip beforeMain = new CreateTrip();
		} else if (b == place) {
			mainFrame.dispose();
			ParsingData place = new ParsingData(Common.place);
		} else if (b == restaurant) {
			mainFrame.dispose();
			ParsingData restaurant = new ParsingData(Common.restaurant);
		} else if (b == schedule) {
			mainFrame.dispose();
			Schedule nextSche = new Schedule();
		} else if (b == cost) {
			mainFrame.dispose();
			Cost nextCost = new Cost();
		} else if (b == favs) {
			mainFrame.dispose();
			Favs nextFavs = new Favs(fromMenu);
		} else if (b == tcl) {
			mainFrame.dispose();
			CheckList nextTcl = new CheckList();
		} else if (b==chat){
			mainFrame.dispose();
			MainClient mclient = new MainClient();
		}
	}

}
