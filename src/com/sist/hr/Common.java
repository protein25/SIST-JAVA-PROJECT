package com.sist.hr;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Common extends JFrame {

	public static final int failResult = 0;
	public static final int successResult = 1;
	public static final int place = 2;
	public static final int restaurant = 3;
	public static final int fromScheduleAdd = 4;
	public static final int fromScheduleUpdate = 5;
	public static final int fromMenu = 6;
	public static final int fromFavs = 7;
	public static final int fromDetail = 8;
	public static final int fromData=9;
	public static final int fileUpdate = 33;
	public static final int fileSave = 34;

	public JPanel topMenu = new JPanel();
	public JButton backBtn;
	public JPanel body = new JPanel();
	public JLabel menuTitle = new JLabel();
	public static Font SHLfont;
	
	public static CommonVO vo = new CommonVO();

	public Common() {
		
		String workSpace = (System.getProperty("user.dir"));
		String sep = File.separator;
		String fontFileName = workSpace + sep + "fonts" + sep + "NANUMGOTHICEXTRABOLD.ttf";
		File fontFile = new File(fontFileName);

		try {
			SHLfont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setTitle("여기");
		this.setBounds(200, 200, 500, 650);
		this.setLayout(null);

		topMenu.setBounds(0, 0, 500, 60);
		topMenu.setLayout(null);

		backBtn = new JButton("<");
		backBtn.setBounds(5, 5, 60, 50);

		menuTitle.setFont(SHLfont.deriveFont(25f));
		menuTitle.setBounds(220, 10, 60, 40);

		topMenu.add(backBtn);
		topMenu.add(menuTitle);

		body.setBounds(0, 60, 500, 590);
		body.setBackground(Color.pink);

		this.add(topMenu);
		this.add(body);
		this.setBackground(Color.WHITE);

		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * 파일에 데이터가 있을 경우 List<String>으로 반환
	 * @param path vo.getFilePath
	 * @return List
	 */
	public static List readFile(String path) {
		File tmpFile = new File(path);
		List<String> result = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(tmpFile))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				result.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Common readFile : FileNotFound Error");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @param path
	 * @param list
	 * @return 저장 성공 ->successResult:1 실패->failResult:0
	 */
	public static int saveFile(String path, List vo) {
		int result = failResult;
		int check = vo.size();

		deleteFile(path);

		File tmpFile = new File(path);
		List data = vo;

		try (BufferedWriter br = new BufferedWriter(new FileWriter(tmpFile))) {
			for (int i = 0; i < data.size(); i++) {
				String line = data.get(i) + "\n";
				br.write(line);
				check--;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return (check == 0 ? result = successResult : result);
	}

	/**
	 * 
	 * @param path
	 * @return 삭제 성공->successResult:1 실패->failResult:0
	 */
	public static int deleteFile(String path) {
		File tmpFile = new File(path);
		int result = failResult;
		if (tmpFile.delete()) {
			System.out.println("FILE delete SUCCESS");
			result = successResult;
		} else {
			System.out.println("FILE delete FAIL");
		}

		return result;
	}

	public static Set readDir() {
		Set keySet = new HashSet();
		File dir = new File(vo.getDirPath());
		System.out.println("Common readDir dirPath : "+vo.getDirPath());
		File[] listOfFiles = dir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				dir.getName().indexOf("Favs");
				dir.getName().indexOf("Schedule");
				dir.getName().indexOf("Cost");
				dir.getName().indexOf("TCL");
				return name.startsWith("Favs") || name.startsWith("Schedule") || name.startsWith("Cost")||name.startsWith("TCL");
			}
		});
		for (int i = 0; i < listOfFiles.length; i++) {
			String key = listOfFiles[i].getName();
			System.out.println("Common readDir fileName"+key);

			int start = key.lastIndexOf('_') + 1;
			int end = key.lastIndexOf('.');
			key = key.substring(start, end);
			System.out.println("Common readDir fileName keyword : "+key);

			keySet.add(key);
		}
		System.out.println("readDir keySet : "+keySet);
		return keySet;
	}
}
