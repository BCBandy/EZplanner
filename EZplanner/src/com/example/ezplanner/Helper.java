package com.example.ezplanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ezplanner.ListItems.ListViewItem;

public class Helper {
	
	
	static List<ListViewItem> listItems = new ArrayList<ListViewItem>();;	
	//static ArrayList<String> listItems = new ArrayList<String>();
	
	static List<String> tmp = new ArrayList<String>();
	static List<String> header = new ArrayList<String>();
	static List<String> label = new ArrayList<String>();	
	
	static List<Map<String, String>> listWithSub = new ArrayList<Map<String,String>>();
	
	static int month, day, year, dayItemPos, listItemPos;
	static String note, description, time,noteOrDes, editText;
	static String defaultTime = "10:00am";
	
	//static List<DayItems> dayItems;
	static ListViewItem listItem;
	static boolean newItem;
	static boolean isMilitaryTime = false;
	
	public static int getTimeAsInt(String time){
		int totalMinutes = 0;
		String ampm;
		String[] parse = time.replaceAll("[amp]", "").split(":");
		
		if (time.contains("pm"))
				ampm = "pm";
		else
			ampm = "am";
		
		if (ampm.equals("pm") && !parse[0].equals("12"))
			totalMinutes += 12*60;
		else if (ampm.equals("am") && parse[0].equals("12")){
			parse[0] = "0";
		}
		
		totalMinutes += Integer.parseInt(parse[0])*60 + Integer.parseInt(parse[1]);
		
		return totalMinutes;
	}
	
	public static void sortByTime(ListItems.ListViewItem day){
		Collections.sort(day.dayItem, new Comparator<ListItems.DayItem>(){
			public int compare (ListItems.DayItem o1, ListItems.DayItem o2){
				return compareTo(getTimeAsInt(o1.time),getTimeAsInt(o2.time));
				//return getTimeAsInt(o1.time).compareTo(getTimeAsInt(o2.time));
			}
		});
	}
	/*
	public int compareTo(int o2){
		return (this).compareTo(o2);
	}
	*/
	public static int compareTo(int time1, int time2){
		
		if (time1 < time2)
			return -1;
		if (time1 > time2)
			return 1;
		return 0;
		
		//return this - time2;
		
	}
	
	//returns -1 if date is new, returns date index if existing
	public static int dateIsNew(String newDate){
		if (ListItems.listItems != null){
			for (int i = 0; i < ListItems.listItems.size(); i++){
				if (ListItems.listItems.get(i).date.equals(newDate)){
					return i;
				}
			}
		}
		return -1;
	}
	
	public static String getAllTime(ListViewItem item){
		String allTimes = "";
		
		for (int i = 0; i < item.dayItem.size(); i++){
			allTimes += item.dayItem.get(i).time;
			allTimes += " ";
		}
		
		return allTimes;
		
	}
	
	public String convertToMilitaryTime(String oldTime){
		String newTime;
		newTime = oldTime;
		
		return newTime;
	}
	
	public static void setTime(int hour, int minute, boolean isAm){
		
		if (isMilitaryTime == true)
			time = hour+":"+String.format("%02d", minute);
		else if (isAm == true)		
			time = hour+":"+String.format("%02d",minute)+"am";
		else {
			time = hour+":"+String.format("%02d",minute)+"pm";
		}
	}
	
	public static String getDate(){
				
		return Integer.toString(month+1) + "/" + Integer.toString(day) + "/" + Integer.toString(year);
	}
	
	public static String getNewDate(){
		return Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year);
	}
	
	public static String[] parseDate(String date){
		String parsedDate[] = new String[3];
		
		parsedDate = date.replaceAll("[amp]", "").split("/|:");		
		
		return parsedDate;
	}
	
	
	
}
