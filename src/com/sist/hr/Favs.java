package com.sist.hr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Favs extends Common {
	static int checkView;
	SHLFrame frame = new SHLFrame();
	// place view var
	JPanel panePlace;
	JLabel textPlace;
	JList listPlace;
	DefaultListModel modelP = new DefaultListModel<>();
	JScrollPane scrollPlace;
	JCheckBox pCh;
	JButton btnScLocPlaceAdd;
	
	// restaurant view var
	JPanel paneRestaurant;
	JLabel textRestaurant;
	JList listRestaurant;
	DefaultListModel modelR = new DefaultListModel<>();
	JScrollPane scrollRestaurant;
	JCheckBox rCh;
	JButton btnScLocRestaurantAdd;
	// 파일을 읽고 쓰기 위한 변수값
	String path = vo.getFilePathFavs();
	List<String> file = readFile(path);
	ScheduleVO svo;
	
	Map mapIndex;
	Map mapHref;
	Map<String, String> mapHrefRes;

	public Favs(int i) {
		// TODO Auto-generated constructor stub
		this.checkView = i;
		setLayoutData();
		setFavsLayout();
	}
	
	public Favs(ScheduleVO svo, int i){
		this.checkView = i;
		this.svo = svo;
		setLayoutData();
		setFavsLayout();
	}

	public static void main(String[] args) {
		//new Favs(checkView);
	}

	public void setFavsLayout() {
		
		
		
		panePlace = new JPanel(null);
		textPlace = new JLabel("관광지");
		listPlace = new JList<>();
		scrollPlace = new JScrollPane(listPlace);

		paneRestaurant = new JPanel(null);
		textRestaurant = new JLabel("맛집");
		listRestaurant = new JList<>();
		scrollRestaurant = new JScrollPane(listRestaurant);

		frame.body.setLayout(null);
		frame.menuTitle.setText("즐겨찾기");

		listPlace.setFixedCellHeight(30);
		listRestaurant.setFixedCellHeight(30);
		listPlace.setFont(SHLfont.deriveFont(15f));
		listRestaurant.setFont(SHLfont.deriveFont(15f));

		listPlace.setModel(modelP);
		listRestaurant.setModel(modelR);

		textPlace.setFont(SHLfont.deriveFont(25f));
		textPlace.setBounds(30, 10, 90, 40);
		scrollPlace.setBounds(30, 60, 420, 180);
		pCh = new JCheckBox("삭제", false);
		pCh.setBounds(360, 10, 90, 40);
		pCh.setOpaque(false);
		
		panePlace.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
		panePlace.add(textPlace);
		panePlace.add(pCh);
		panePlace.add(scrollPlace);
		panePlace.setBounds(10, 15+60, 475, 260);

		textRestaurant.setFont(SHLfont.deriveFont(25f));
		textRestaurant.setBounds(30, 10, 90, 40);
		scrollRestaurant.setBounds(30, 60, 420, 180);
		rCh = new JCheckBox("삭제", false);
		rCh.setBounds(360, 10, 90, 40);
		rCh.setOpaque(false);

		paneRestaurant.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
		paneRestaurant.add(textRestaurant);
		paneRestaurant.add(rCh);
		paneRestaurant.add(scrollRestaurant);
		paneRestaurant.setBounds(10, 285+60, 475, 260);

		if (checkView == fromScheduleAdd || checkView == fromScheduleUpdate){
			btnScLocPlaceAdd = new JButton("일정에 추가");
			btnScLocRestaurantAdd = new JButton("일정에 추가");
			btnScLocPlaceAdd.setBounds(200, 10, 120, 40);
			btnScLocRestaurantAdd.setBounds(200, 10, 120, 40);
			panePlace.add(btnScLocPlaceAdd);
			paneRestaurant.add(btnScLocRestaurantAdd);
			
			btnScLocPlaceAdd.addActionListener(new ActionListener(){
				
				ArrayList<String> tmpListP = new ArrayList<String>();

				@Override
				public void actionPerformed(ActionEvent e) {
					int[] arr = {};
					arr = listPlace.getSelectedIndices();
					for(int i = 0 ; i < arr.length ; i++){
						tmpListP.add((String) modelP.getElementAt(arr[i]));
					}
					ArrayList<String> currentList = (svo.getLocation() == null)?new ArrayList<String>():(svo.getLocation());
					currentList.addAll(tmpListP);
					JOptionPane.showMessageDialog(btnScLocPlaceAdd, "추가되었습니다.");
					svo.setLocation(currentList);
					
				}});
			
			btnScLocRestaurantAdd.addActionListener(new ActionListener() {
				
				 ArrayList<String> tmpListR = new  ArrayList<String>();
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int[] arr = {};
					arr = listRestaurant.getSelectedIndices();
					for(int i = 0 ; i < arr.length ; i++){
						tmpListR.add((String) modelR.getElementAt(arr[i]));
					}
					ArrayList<String> currentList = (svo.getLocation() == null)?new ArrayList<String>():(svo.getLocation());
					currentList.addAll(tmpListR);
					JOptionPane.showMessageDialog(btnScLocRestaurantAdd, "추가되었습니다.");
					svo.setLocation(currentList);
				}
			});
		}
		
		panePlace.setOpaque(false);
		paneRestaurant.setOpaque(false);
		frame.body.add(panePlace);
		frame.body.add(paneRestaurant);

		frame.setVisible(true);

		frame.backBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (checkView == fromScheduleAdd) {
					new ScheduleDayFrame(svo, 1);
					frame.dispose();
				} else if (checkView == fromScheduleUpdate) {
					new ScheduleDayFrame(svo, 3);
					frame.dispose();
				} else if (checkView == fromMenu) {
					new MenuMain();
					frame.dispose();
				} else if (checkView == fromDetail) {
					new MenuMain();
					frame.dispose();
				}

			}
		});
		listPlace.addListSelectionListener(new placeListListener());
		listRestaurant.addListSelectionListener(new resListListner());

	}

	public void setLayoutData() {
		file = readFile(path);
		mapIndex = new HashMap<>();
		mapHref = new HashMap<>();
		mapHrefRes = new HashMap<>();

		for (int i = 0; i < file.size(); i++) {
			String line = file.get(i) + "," + i;
			String[] v = line.split(",");

			switch (v[0]) {
			case "2":
				modelP.addElement(v[1]);
				String href = v[2];
				String[] h = href.split("@");
				mapIndex.put(v[1], Integer.parseInt(h[1]));
				mapHref.put(v[1], h[0]);
				break;

			case "3":
				modelR.addElement(v[1]);
				mapHrefRes.put(v[1], v[2]);
				break;
			}
		}
	}

	class placeListListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(checkView != fromScheduleAdd && checkView != fromScheduleUpdate){
			JList list = (JList) e.getSource();
			if (pCh.isSelected()) {
				for (int i = file.size() - 1; i > -1; i--) {
					list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
					int[] selItem = list.getSelectedIndices();

					String[] v = file.get(i).split(",");
					if (v[1].equals(list.getSelectedValue())) {
						file.remove(i);
						String log = saveFile(vo.getFilePathFavs(), file) == successResult ? "파일저장 성공" : "파일저장 실패";
						System.out.println(log);
						modelP.removeElement(v[1]);
					}
				}
			} else {
				String href = (String) mapHref.get(list.getSelectedValue());
				int index = (int) mapIndex.get(list.getSelectedValue());

				JOptionPane dialog = new JOptionPane();
				dialog.showMessageDialog(null, getPanel(href, index),(String)list.getSelectedValue(),JOptionPane.PLAIN_MESSAGE);

			}
		}}
	}

	class resListListner implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(checkView != fromScheduleAdd && checkView != fromScheduleUpdate){
			JList list = (JList) e.getSource();
			if (rCh.isSelected()) {
				for (int i = file.size() - 1; i > -1; i--) {
					list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
					int[] selItem = list.getSelectedIndices();

					String[] v = file.get(i).split(",");
					if (v[1].equals(list.getSelectedValue())) {
						file.remove(i);
						String log = saveFile(vo.getFilePathFavs(), file) == successResult ? "파일저장 성공" : "파일저장 실패";
						System.out.println(log);
						modelR.removeElement(v[1]);
					}
				}
			} else {
				String key = (String) list.getSelectedValue();
				frame.dispose();
				new DetailParsing(fromFavs, mapHrefRes.get(key));

			}
		}}

	}

	private JPanel getPanel(String url, int i) {
		String title = "";
		String img = "";
		String desc = "";
		try {
			Document place = Jsoup.connect(url).get();

			Elements spotImg = place.select("div.spots-contents span.spot-img a img");
			Elements spotName = place.select("h4.spot-name");
			Elements spotState = place.select("span.spot-state");

			img = spotImg.get(i).attr("abs:src");
			title = spotName.get(i).text();
			desc = spotState.get(i).text();

			System.out.println(title);
			System.out.println(img);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.white);
		panel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));

		JLabel labelImg = new JLabel(setImage(img));
		JLabel labelTitle = new JLabel(title);
		JTextArea labelDesc = new JTextArea(desc);

		labelTitle.setFont(new Font("궁서", Font.BOLD, 25));
		labelDesc.setFont(new Font("궁서", Font.PLAIN, 15));

		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelDesc.setLineWrap(true);

		panel.add(labelImg, BorderLayout.CENTER);
		panel.add(labelTitle, BorderLayout.NORTH);
		panel.add(labelDesc, BorderLayout.SOUTH);

		return panel;
	}

	public Icon setImage(String src) {
		URL url;
		Image img;
		ImageIcon icon = new ImageIcon();
		try {
			url = new URL(src);
			img = ImageIO.read(url);
			icon.setImage(img.getScaledInstance(200, 200, Image.SCALE_SMOOTH));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return icon;
	}

}
