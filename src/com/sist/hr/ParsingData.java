package com.sist.hr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ParsingData extends Common {
	public static int currPage = 1;
	public static int from;

	int maxPage = 10;

	Map<String, String> keyMap;
	String keyword;

	String url;
	String path = vo.getFilePathFavs();

	private String urlRestaurant;// keyword encode
	private String urlPlace;// keyword 영어

	List<ParsingVO> data;

	SHLFrame frame = new SHLFrame();
	JList list;
	JScrollPane scrollList;
	JScrollPane scrollPane;
	JPanel bp;
	JPanel scrollPaneOuter = new JPanel();

	DefaultListModel model;
	DefaultListModel pModel = new DefaultListModel<>();
	DefaultListCellRenderer render = new DefaultListCellRenderer();

	JPanel paneBtn;
	JButton btnFirstPage;
	JButton btnLastPage;
	JLabel pageLabel;
	JButton btnBeforePage;
	JButton btnNextPage;
	JButton btnSearchMap;

	public ParsingData(int i) {
		this.from = i;
		setListLayout(i);
	}

	public static void main(String[] args) {
		new ParsingData(from);

	}

	public void setListLayout(int i) {
		// keyword sort
		setKeyword();
		//

		list = new JList();
		scrollList = new JScrollPane(list);

		paneBtn = new JPanel(null);
		btnFirstPage = new JButton("<<");
		JButton btnLastPage = new JButton(">>");
		JLabel pageLabel = new JLabel(currPage + "", JLabel.CENTER);
		JButton btnBeforePage = new JButton("<");
		JButton btnNextPage = new JButton(">");
		btnSearchMap = new JButton("장소 검색");
		if (i != 2) {
			btnSearchMap.setVisible(false);
		} else {
			btnSearchMap.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new SearchMap(true);

				}
			});
			btnSearchMap.setBounds(400, 5, 90, 50);
			frame.topMenu.add(btnSearchMap);
		}

		switch (i) {
		case 2:
			// frame.body.setLayout(new FlowLayout());
			frame.body.setLayout(null);
			keyword = keyMap.get(keyword);
			frame.menuTitle.setText("관광지");
			frame.menuTitle.setBounds(205, 10, 90, 40);

			data = request(place);

			int sizeOfRow = data.size();
			bp = new JPanel();

			bp.setLayout(new GridLayout(sizeOfRow, 1));

			JPanel[] p = new JPanel[sizeOfRow];

			for (int j = 0; j < sizeOfRow; j++) {
				System.out.println(j);
				p[j] = new JPanel(new BorderLayout());
				p[j].setPreferredSize(new Dimension(450, 150));
				p[j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

				String tmpT = data.get(j).getTitle();
				String titleStr = (tmpT.length() < 20) ? tmpT : tmpT.substring(0, 20) + "...";

				JLabel title = new JLabel(titleStr);

				System.out.println("getTitle" + data.get(j).getTitle());

				JLabel img = new JLabel(setImage(data.get(j).getImg()));
				JTextArea desc = new JTextArea(data.get(j).getDescribe());
				// desc.setSize(100, 50);
				JLabel point = new JLabel(data.get(j).getPoint());
				JButton b = new JButton("★");

				String t = data.get(j).getTitle();
				String h = data.get(j).getHref();

				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						List file = readFile(path);
						int index = 0;
						boolean check = false;
						for (int i = file.size() - 1; i > -1; i--) {
							String line = (String) file.get(i);
							String[] f = line.split(",");
							if (f[1].equals(t)) {
								check = true;
								index = i;
							}
						}
						if (check) {
							file.remove(index);
							if (saveFile(path, file) == successResult) {
								JOptionPane.showMessageDialog(b, "삭제되었습니다.");
							}
						} else {
							if (t.contains(",")) {
								String[] var = t.split(",");
								String ti = var[0];
								System.out.println(ti);
								String v = from + "," + ti + "," + h;
								System.out.println(v);
								file.add(v);
							} else {
								String v = from + "," + t + "," + h;

								System.out.println(v);
								file.add(v);
							}
							if (saveFile(path, file) == successResult) {
								JOptionPane.showMessageDialog(b, "저장되었습니다.");
							}
						}
					}
				});

				desc.setLineWrap(true);
				desc.setFont(SHLfont.deriveFont(14f));

				title.setFont(SHLfont.deriveFont(18f));

				JPanel lab1 = new JPanel();
				JPanel lab2 = new JPanel(new GridLayout(1, 2));
				lab1.add(title);
				lab1.add(point);
				lab1.add(b);

				lab2.add(img);
				lab2.add(desc);

				p[j].add(lab1, BorderLayout.NORTH);
				p[j].add(lab2, BorderLayout.CENTER);

				bp.add(p[j]);
			} // end of for (int j = 0; j < data.size(); j++)

			scrollPaneOuter.setLayout(null);

			scrollPane = new JScrollPane(bp);
			scrollPane.setBounds(10, 9, 470, 480);
			// scrollPane.setBackground(Color.green);

			scrollPane.setVerticalScrollBarPolicy(scrollPane.VERTICAL_SCROLLBAR_ALWAYS);

			scrollPaneOuter.setBounds(10, 10 + 60, 480, 490);
			scrollPaneOuter.add(scrollPane);
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					scrollPane.getVerticalScrollBar().setValue(0);
				}
			});

			frame.body.add(scrollPaneOuter);
			scrollPane.setViewportView(bp);

			break;

		case 3:
			frame.body.setLayout(null);
			try {
				keyword = URLEncoder.encode(this.keyword, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frame.menuTitle.setText("맛집");
			render.setHorizontalAlignment(DefaultListCellRenderer.CENTER);

			scrollList.setBounds(11, 10 + 60, 470, 490);
			scrollList.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

			list.setCellRenderer(render);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setFixedCellHeight(45);
			list.setFont(SHLfont.deriveFont(20f));
			data = request(restaurant);

			frame.body.add(scrollList);

			model = new DefaultListModel();

			list.setModel(model);

			for (int j = 0; j < data.size(); j++) {
				ParsingVO vo = (ParsingVO) data.get(j);
				model.addElement(vo.getTitle());
			}

			break;

		}

		paneBtn.setBounds(11, 500 + 60, 470, 50);
		btnFirstPage.setBounds(10, 5, 90, 40);
		btnBeforePage.setBounds(110, 5, 90, 40);
		pageLabel.setBounds(210, 5, 50, 40);
		btnNextPage.setBounds(270, 05, 90, 40);
		btnLastPage.setBounds(370, 05, 90, 40);

		pageLabel.setFont(new Font("Serif", Font.BOLD, 25));

		paneBtn.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));

		paneBtn.add(btnFirstPage);
		paneBtn.add(btnBeforePage);
		paneBtn.add(pageLabel);
		paneBtn.add(btnNextPage);
		paneBtn.add(btnLastPage);

		frame.body.add(paneBtn);
		frame.body.setBounds(0, 60, 494, 590);
		frame.setVisible(true);

		frame.backBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				currPage = 1;
				MenuMain before = new MenuMain();
			}
		});
		btnFirstPage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				currPage = 1;
				pageLabel.setText(currPage + "");
				// data = request(from);
				setListLayout(from);
			}
		});
		btnBeforePage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				currPage = currPage > 1 ? --currPage : 1;
				pageLabel.setText(currPage + "");
				// data = request(from);
				System.out.println("currPage : " + currPage);
				frame.dispose();
				// setListLayout(from);
				new ParsingData(from);
			}
		});
		btnNextPage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (vo.getKeyword().equals("울산") || vo.getKeyword().equals("대전")) {
					currPage = currPage < maxPage - 1 ? ++currPage : maxPage - 1;

				} else {
					currPage = currPage < 10 ? ++currPage : 10;
				}
				pageLabel.setText(currPage + "");
				frame.dispose();
				new ParsingData(from);
			}
		});
		btnLastPage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				currPage = maxPage - 1;
				System.out.println(currPage);
				pageLabel.setText(currPage + "");
				frame.dispose();
				new ParsingData(from);
			}
		});
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				JList tmpList = (JList) e.getSource();
				int selectedIndex = tmpList.getSelectedIndex();
				System.out.println("selectedIndex = " + selectedIndex);
				String h = data.get(selectedIndex).getHref();
				frame.dispose();
				DetailParsing detail = new DetailParsing(fromData, h);
			}
		});
	}

	public List request(int kind) {
		List result = new ArrayList<>();
		switch (kind) {
		case 2:
			try {
				System.out.println("data requeseted. currPage:" + currPage);
				url = "https://www.wishbeen.co.kr/city?serviceType=" + keyword
						+ "&tab=attraction&cat2=001f2309b8ef4a82&viewPageNum=" + currPage + "&perPage=20";
				System.out.println("currpagen in request" + currPage);
				System.out.println(url);
				Document place = Jsoup.connect(url).get();

				Elements spotImg = place.select("div.spots-contents span.spot-img a img");
				Elements spotName = place.select("h4.spot-name");
				Elements spotState = place.select("span.spot-state");
				Elements tag = place.select("div.tag a.spot-cat3-item");
				Elements wish = place.select("span.count");
				Elements paging = place.select("div.paging ul li");
				maxPage = paging.size();
				System.out.println(maxPage);

				for (int i = 0; i < 20; i++) {
					String img = spotImg.get(i).attr("abs:src");
					String title = spotName.get(i).text();
					String describe = spotState.get(i).text();
					String point = wish.get(i).text();
					ParsingVO vo = new ParsingVO(from, img, title, point, describe, url + "@" + i);
					result.add(vo);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("----request.case2.IOException");
				e1.printStackTrace();
			}
			break;
		case 3:
			System.out.println("data requeseted. currPage:" + currPage);
			try {
				url = "https://www.mangoplate.com/search/?keyword=" + keyword + "&page=" + currPage;
				System.out.println(url);

				Document page = Jsoup.connect(url).get();
				Elements photo = page.select("img.center-croping");
				Elements titles = page.select("div.info h2.title");
				Elements describes = page.select("p.etc");
				Elements hrefsRes = page.select("div.info a");

				for (int i = 0; i < 10; i++) {
					String img = photo.get(i).text();
					String title = titles.get(i).text();
					String desc = describes.get(i).text();
					String hrefRes = hrefsRes.get(i).attr("abs:href");
					ParsingVO vo = new ParsingVO(from, img, title, desc, hrefRes);
					result.add(vo);
				}

			} catch (UnsupportedEncodingException e) {

				System.out.println("----request.case3.UnsupportedEncodingException");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("----request.case3.IOException");
				e.printStackTrace();
			}

			break;

		}
		return result;
	}

	public Icon setImage(String src) {
		URL url;
		Image img;
		ImageIcon icon = new ImageIcon();
		try {
			url = new URL(src);
			img = ImageIO.read(url);
			icon.setImage(img.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("----setImage.IOException");
			e.printStackTrace();
		}
		return icon;
	}

	public void setKeyword() {
		keyMap = new HashMap<>();
		keyMap.put("제주", "jeju");
		keyMap.put("서울", "seoul");
		keyMap.put("인천", "incheon");
		keyMap.put("대전", "daejeon");
		keyMap.put("대구", "daegu");
		keyMap.put("부산", "busan");
		keyMap.put("광주", "gwangju");
		keyMap.put("울산", "ulsan");

		keyword = vo.getKeyword();
		keyword = keyword.substring(0, 2);
		System.out.println(keyword);
	}

}
