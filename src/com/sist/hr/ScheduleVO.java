package com.sist.hr;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class ScheduleVO implements Comparable {

	private Calendar tripDate;
	private String diary;
	private ArrayList<String> location;
	
	public ScheduleVO(){
	} // end of constructor
	
	public Calendar getTripDate() {
		return tripDate;
	}
	public void setTripDate(Calendar tripDate) {
		this.tripDate = tripDate;
	}
	public String getDiary() {
		return diary;
	}
	public void setDiary(String diary) {
		this.diary = diary;
	}
	public ArrayList<String> getLocation() {
		return location;
	}
	public void setLocation(ArrayList<String> location) {
		this.location = location;
	}
	
	public String getVoToString(){
		return getTripDateToString() + "," + getDiary() + "," +  getLocationToString();
	}

	public String getLocationToString(){
		String locToString = "";
		Object[] locArr = this.getLocation().toArray();
		
		for(int i = 0 ; i < locArr.length ; i++){
			if(i == locArr.length -1){
				locToString += (locArr[i] +"");
			} else {
				locToString += (locArr[i] + "+");
			}
				
		}
		return locToString;
	}
	
	public String getTripDateToString(){
		
		int tmpMonth = tripDate.get(Calendar.MONTH) + 1;
		String tmpMonthStr = null;

		if(tmpMonth < 10){
			tmpMonthStr = 0 + Integer.toString(tmpMonth);
		} else {
			tmpMonthStr = Integer.toString(tmpMonth);
		}
		
		int tmpDayOfMonth = tripDate.get(Calendar.DAY_OF_MONTH);
		String tmpDayOfMonthStr = null;
		if(tmpDayOfMonth < 10){
			tmpDayOfMonthStr = 0 + Integer.toString(tmpDayOfMonth);
		} else {
			tmpDayOfMonthStr = Integer.toString(tmpDayOfMonth);
		}
		
		return tripDate.get(Calendar.YEAR) + "/" + (tmpMonthStr) + "/" + tmpDayOfMonthStr;
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof ScheduleVO){
			ScheduleVO c = (ScheduleVO) o;
			return c.getTripDate().compareTo(this.getTripDate())*(-1);
		}
		else return 0;
	}

	
} // end of class
