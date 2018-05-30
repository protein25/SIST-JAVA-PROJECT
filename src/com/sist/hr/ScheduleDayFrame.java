package com.sist.hr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class ScheduleDayFrame extends Common implements ActionListener {

	//기본 Frame과 VO, 새 일정 추가/기존 일정 수정 여부(newSchedule)
	SHLFrame dayFrame = new SHLFrame();
	ScheduleVO svo;
	JPanel topMenu = dayFrame.topMenu;
	int addShowUpdate; // 1 - 새 일정, 2 - 일정 보기, 3 - 수정
	
	JButton deleteCh = new JButton("삭제");
	// List 보이기 위해 임시 코드
	ArrayList<String> tmpLocList;
	ArrayList<String> tmpSelectedDeleteList;
	// 여행 날짜 작성 UI
	JPanel addDayPanel = new JPanel();
	
	JLabel addDayLabel = new JLabel();
	DateFormat tripDayFormat = new SimpleDateFormat("yyyy/MM/dd");
	JFormattedDateTextField addTripDayField = new JFormattedDateTextField();

	// 여행지/맛집 추가 UI
	JPanel addLocationPanel = new JPanel();
	
	JList addLocationList = new JList();
	JScrollPane addLocationListPane;
	
	JLabel addLocationLabel = new JLabel();
	JButton addLocationBtn = new JButton("가져오기");
	
	// 일기 쓰기 UI
	JPanel addDiaryPanel = new JPanel();
	
	JLabel addDiaryLabel = new JLabel();
	JTextArea addDiaryArea = new JTextArea(10, 10);

	// 저장 버튼
	JButton saveBtn = new JButton("저장");

	public ScheduleDayFrame() {
	}

	@SuppressWarnings("deprecation")
	public ScheduleDayFrame(ScheduleVO svo, int addShowUpdate) {

		
		
		String menuTitle = "";
		if (svo == null && addShowUpdate == 1) { //새 일정 추가 (메뉴에서)
			this.addShowUpdate = addShowUpdate;
			this.svo = new ScheduleVO();

			menuTitle = "새 일정";
			
			addTripDayField.setValue(new Date()); //기본값은 오늘 날짜
		} else if ((svo != null) && addShowUpdate == 1){ //새 일정 추가 - Favs에서 호출
			this.addShowUpdate = addShowUpdate;
			this.svo = svo;
			menuTitle = "새 일정";
			Calendar c = svo.getTripDate();
			Date tmpDate = (c==null)?new Date():c.getTime();
			addTripDayField.setValue(tmpDate);
			ArrayList tmpList = (svo.getLocation()==null)?new ArrayList():svo.getLocation();
			addLocationList.setListData(tmpList.toArray());
			addDiaryArea.setText(svo.getDiary());
		} else if ((svo != null) && addShowUpdate == 2) { //일정 보기만 하기
			this.addShowUpdate = addShowUpdate;
			this.svo = svo;

			menuTitle = "일정 보기";
			
			// 읽어온 데이터 보여주기
			// 인자로 들어온 svo로부터 읽어서 화면에 그린다.

			Calendar c = svo.getTripDate();
			Date tmpDate = c.getTime();
			addTripDayField.setValue(tmpDate);

			addDiaryArea.setText(svo.getDiary());
			addLocationList.setListData(svo.getLocation().toArray());
			
			//수정 불가능
			addTripDayField.setEditable(false); //날짜를 키 값으로 쓸 것이므로 날짜는 수정X
			addDiaryArea.setEditable(false);

		} else if ((svo != null) && addShowUpdate == 3){ //기존 일정 수정
			this.addShowUpdate = addShowUpdate;
			this.svo = svo;

			menuTitle = "일정 수정";
			addTripDayField.setEditable(false); //날짜를 키 값으로 쓸 것이므로 날짜는 수정X
			
			// 읽어온 데이터 보여주기
			// 인자로 들어온 svo로부터 읽어서 화면에 그린다.

			Calendar c = svo.getTripDate();
			Date tmpDate = c.getTime();
			addTripDayField.setValue(tmpDate);

			addDiaryArea.setText(svo.getDiary());
			addLocationList.setListData(svo.getLocation().toArray());
		}
		

		deleteCh.setBounds(201, 10, 70, 35);
		deleteCh.setOpaque(false);
		dayFrame.menuTitle.setText(menuTitle);
		dayFrame.backBtn.addActionListener(this); 		// 뒤로가기 버튼
		
		addLocationPanel.setBounds(12, 146, 459, 207);

		addDiaryPanel.setBounds(12, 389, 459, 189);

		
		addDayPanel.setLayout(null);
		addDayPanel.setBounds(12, 10+60, 459, 74);
		addTripDayField.setFont(SHLfont.deriveFont(25f));
		addDayPanel.add(addTripDayField);
		addLocationPanel.setLayout(null);
		addLocationLabel.setBounds(31, 10, 216, 29);
		addLocationLabel.setFont(SHLfont.deriveFont(25f));

		addLocationLabel.setText("여행지/맛집");
		if(addShowUpdate != 2){
		addLocationPanel.add(deleteCh);
		}
		addLocationPanel.add(addLocationLabel);
		addLocationList.setBounds(1, 1, 256, 128);
		addLocationPanel.add(addLocationList);
		if(svo != null){
			ArrayList tmpList = (svo.getLocation()==null)?new ArrayList():svo.getLocation();
			addLocationList.setListData(tmpList.toArray());
		} else {
			ArrayList tmpList = new ArrayList();
			addLocationList.setListData(tmpList.toArray());
		}
		addLocationListPane = new JScrollPane(addLocationList);
		addLocationListPane.setBounds(102, 49, 263, 148);
		addLocationBtn.setFont(SHLfont.deriveFont(20f));
		addLocationBtn.setBounds(322, 6, 125, 23);

		addLocationPanel.add(addLocationBtn);

		addLocationPanel.add(addLocationListPane);

		addLocationBtn.addActionListener(this);
		
		addDiaryPanel.setLayout(null);
		addDiaryLabel.setFont(SHLfont.deriveFont(25f));
		addDiaryLabel.setBounds(34, 10, 105, 30);

		addDiaryLabel.setText("\uC77C\uAE30\uC4F0\uAE30");
		addDiaryPanel.add(addDiaryLabel);
		addDiaryArea.setBounds(101, 50, 277, 129);
		addDiaryPanel.add(addDiaryArea);

		dayFrame.getContentPane().add(topMenu);
		dayFrame.body.setLayout(null);


		addTripDayField.setToolTipText("\uC5EC\uD589 \uB0A0\uC9DC\uB97C \uC785\uB825\uD558\uC138\uC694. (\uC608: 2018/02/20)");
		addTripDayField.setBounds(192, 16, 156, 43);
		addDayPanel.add(addTripDayField);
		

		addLocationPanel.setOpaque(false);
		addDayPanel.setOpaque(false);
		addDiaryPanel.setOpaque(false);
		dayFrame.getContentPane().add(addLocationPanel);
		dayFrame.getContentPane().add(addDayPanel);
		addDayLabel.setBounds(36, 10, 156, 54);
		addDayPanel.add(addDayLabel);
		addDayLabel.setFont(SHLfont.deriveFont(25f));
		addDayLabel.setText("여행 날짜");
		dayFrame.getContentPane().add(addDiaryPanel);
		saveBtn.setFont(SHLfont.deriveFont(20f));

		saveBtn.setBounds(183, 588, 97, 23);
		dayFrame.getContentPane().add(saveBtn);

		//삭제 이벤트 처리
		deleteCh.addActionListener(this);

		// 저장 이벤트 처리
		saveBtn.addActionListener(this);
		dayFrame.menuTitle.setBounds(124, 10, 261, 40);
		dayFrame.menuTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		//화면 보여주기
		dayFrame.setVisible(true);
		dayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	} // end of constructor

	public static void main(String[] args) {		
	}
	
	public void saveDayFile(ScheduleVO svo) {
		if (svo.getDiary() == null) {
			svo.setDiary("");
		}
		
		if (svo.getLocation() == null) {
			ArrayList<String> tmpBlankList = new ArrayList<String>();
			tmpBlankList.add("");
			svo.setLocation(tmpBlankList);
		}
		
		if (addShowUpdate == 1) { // 새 일정 추가 ----------------------------------------------------------
		
			ScheduleVO[] tmpFileDataAdd = Schedule.readScFile();
			ScheduleVO[] tmpFileDataForSave = new ScheduleVO[tmpFileDataAdd.length+1]; //일정을 하나 추가하므로 길이가 +1인 새로운 배열 생성

			//배열 복사
			System.arraycopy(tmpFileDataAdd, 0, tmpFileDataForSave, 0, tmpFileDataAdd.length);

			//배열의 마지막 원소에 svo의 내용을 추가
			tmpFileDataForSave[tmpFileDataAdd.length] = new ScheduleVO();
			tmpFileDataForSave[tmpFileDataAdd.length].setDiary(svo.getDiary());
			tmpFileDataForSave[tmpFileDataAdd.length].setLocation(svo.getLocation());
			tmpFileDataForSave[tmpFileDataAdd.length].setTripDate(svo.getTripDate());
			
			//저장할 때 sorting
			Arrays.sort(tmpFileDataForSave);
			
			//ScheduleVO[] -> List<String>
			List<String> tmpListForSave = new ArrayList<String>();
			for(int i = 0 ; i < tmpFileDataForSave.length ; i++){
				tmpListForSave.add(tmpFileDataForSave[i].getVoToString());
			}
			
			Common.saveFile(Common.vo.getFilePathSchedule(), tmpListForSave);
			
		} else if (addShowUpdate == 3) { // 기존 일정 수정 --------------------------------------------------------------------
			ScheduleVO[] tmpFileData = Schedule.readScFile();
			List tmpListForSave = new ArrayList(); 	//List로 바꿔서 Common.saveFile을 이용한 저장
			
			for(int i = 0 ; i < tmpFileData.length ; i++){

				if(tmpFileData[i].getTripDateToString().equals(svo.getTripDateToString())) //키값 - 날짜
				{
					tmpFileData[i].setDiary(svo.getDiary());
					tmpFileData[i].setLocation(svo.getLocation());
					
					tmpListForSave.add(tmpFileData[i].getVoToString());
					
				} else {
					tmpListForSave.add(tmpFileData[i].getVoToString());
				}

			} // end of for

			Common.saveFile(Common.vo.getFilePathSchedule(), tmpListForSave);
			
		} // end of if-else-if
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton tmpBtn = (JButton) e.getSource();
		if(tmpBtn.equals(deleteCh)){ //Location 삭제 이벤트 처리
			
			if(addShowUpdate != 2){ //일정 보기가 아닐 때만 삭제 가능하도록
				
				addLocationList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				int[] selItem = addLocationList.getSelectedIndices();

				for(int i = 0 ; i < selItem.length ; i++){
					System.out.println(selItem[i]);
				}
				ArrayList<String> tmpList = svo.getLocation();
				
				for(int i = selItem.length -1 ; i > -1 ; i--){
					tmpList.remove(selItem[i]);
				}
				
				tmpSelectedDeleteList = tmpList;
			}
			svo.setLocation(tmpSelectedDeleteList);
			JOptionPane.showMessageDialog(deleteCh, "삭제되었습니다.");
			addLocationList.setListData(svo.getLocation().toArray());
			saveDayFile(svo);
		} 
		if (tmpBtn.equals(saveBtn)) { //****저장하기 버튼******
			
			//textfield, textArea의 내용을 가져와서 svo에 넣어준다.
			String tmpDayStr = addTripDayField.getText();
			
			ScheduleVO[] tmpFileData = Schedule.readScFile();
			System.out.println("tmpDayStr:" + tmpDayStr);
			
			for(int i = 0 ; i < tmpFileData.length ; i++){
				if(tmpDayStr.equals(tmpFileData[i].getTripDateToString())){
					if(addShowUpdate == 1) {
					JOptionPane.showMessageDialog(tmpBtn, "중복된 날짜입니다", "경고", JOptionPane.PLAIN_MESSAGE);
					return;
					}
				} else { // 날짜가 중복되지 않았을 경우
				}}
					Calendar c = Calendar.getInstance();
					int tmpYear = Integer.parseInt(tmpDayStr.substring(0, 4));
					int tmpMonth = Integer.parseInt(tmpDayStr.substring(5, 7))-1;
					int tmpDayOfMonth = Integer.parseInt(tmpDayStr.substring(8));

					c.set(tmpYear,tmpMonth,tmpDayOfMonth);
					svo.setTripDate(c);

					String tmpDiary = addDiaryArea.getText();
					svo.setDiary(tmpDiary.replaceAll(",", "，"));
					
					ListModel tmpListModel = addLocationList.getModel();
					ArrayList<String> tmpList = new ArrayList<String>();

					for(int j = 0 ; j < tmpListModel.getSize() ; j++){
						tmpList.add((String)tmpListModel.getElementAt(j));
					}
					svo.setLocation(tmpList);

					saveDayFile(svo); //svo를 파일에 쓴다
					
					dayFrame.dispose();
					new Schedule();

		} else if (tmpBtn.equals(addLocationBtn)) { //****가져오기 버튼******
			//즐겨찾기에서 장소 불러오기
			if (addShowUpdate == 1) {  //새일정
				
				//textField의 내용 svo에 담기
				String tmpDayStr = addTripDayField.getText();
				Calendar c = Calendar.getInstance();
				int tmpYear = Integer.parseInt(tmpDayStr.substring(0, 4));
				int tmpMonth = Integer.parseInt(tmpDayStr.substring(5, 7))-1;
				int tmpDayOfMonth = Integer.parseInt(tmpDayStr.substring(8));

				c.set(tmpYear,tmpMonth,tmpDayOfMonth);
				svo.setTripDate(c);
				String tmpDiary = addDiaryArea.getText();
				svo.setDiary(tmpDiary.replaceAll(",", "，"));
				dayFrame.setVisible(false);
				new Favs(svo, Common.fromScheduleAdd);
				dayFrame.dispose();
			} else if (addShowUpdate == 3) { //일정 수정

				//textField의 내용 svo에 담기
				String tmpDayStr = addTripDayField.getText();
				Calendar c = Calendar.getInstance();
				int tmpYear = Integer.parseInt(tmpDayStr.substring(0, 4));
				int tmpMonth = Integer.parseInt(tmpDayStr.substring(5, 7))-1;
				int tmpDayOfMonth = Integer.parseInt(tmpDayStr.substring(8));

				c.set(tmpYear,tmpMonth,tmpDayOfMonth);
				svo.setTripDate(c);
				String tmpDiary = addDiaryArea.getText();
				svo.setDiary(tmpDiary.replaceAll(",", "，"));

				dayFrame.setVisible(false);
				new Favs(svo, Common.fromScheduleUpdate);
				dayFrame.dispose();
			} else if (addShowUpdate == 2) { //일정 보기
				//Favs에서 목록을 선택했을 때 디테일이 보이게끔
				//WORK
			}
		} else if (tmpBtn.equals(dayFrame.backBtn)) { // ****뒤로가기 버튼*****
			
			//WORK: 수정된 내용 있는데 저장하지 않았을 시 경고
			
			dayFrame.dispose();
			new Schedule();


		}
	}
}

// -------------------------------------------------------------------------
// 검색해서 가져온 코드 - JFormattedDateTextField
// 주의! 날짜를 Date 포맷으로 주어야 한다 (월 하나 밀리는 것 조심)

class JFormattedDateTextField extends JFormattedTextField {
	   Format format = new SimpleDateFormat("yyyy/MM/dd");
	  
	   public JFormattedDateTextField() {
	      super();
	      MaskFormatter maskFormatter = null;
	      try {
	         maskFormatter = new MaskFormatter("####/##/##");
	      } catch (ParseException e) {
	         e.printStackTrace();
	      }
	  
	      maskFormatter.setPlaceholderCharacter('_');
	      setFormatterFactory(new DefaultFormatterFactory(maskFormatter));
	      this.addFocusListener(new FocusAdapter() {
	         public void focusGained(FocusEvent e) {
	            if (getFocusLostBehavior() == JFormattedTextField.PERSIST)
	               setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
	            }
	   
	            public void focusLost(FocusEvent e) {
	               try {
	                  Date date = (Date) format.parseObject(getText());
	                  setValue(format.format(date));
	               } catch (ParseException pe) {
	                  setFocusLostBehavior(JFormattedTextField.PERSIST);
	                  setText("");
	                  setValue(null);
	               }
	            }
	      });
	   }
	  
	   public void setValue(Date date) {
	      super.setValue(toString(date));
	   }
	  
	   private Date toDate(String sDate) {
	      Date date = null;
	      if (sDate == null) return null;
	      try {
	         date = (Date) format.parseObject(sDate);
	      }
	      catch (ParseException pe) {
	         // ignore
	      }
	  
	      return date;
	   }
	  
	   private String toString(Date date) {
	      try {
	         return format.format(date);
	      } catch (Exception e) {
	         return "";
	      }
	   }
	}