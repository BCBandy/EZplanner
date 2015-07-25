package com.example.ezplanner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ezplanner.ListItems.ListViewItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


//import android.app.ListFragment;
import android.support.v4.app.ListFragment;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
//import android.support.v7.internal.widget.AdapterViewCompat;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
//import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
//import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
//import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;

public class ListItems extends ListFragment{ // implements AdapterView.OnItemClickListener {//ActionBarActivity

	TextView emptyList;
	static DateFormat df;
	ListView listView;
	CustomListViewAdapter customListViewAdapter;
	static ArrayList<ListViewItem> listItems;
	public static final String LIST_ITEMS = "listItems";
	View emptyView;
	
	public ListItems() {
		//ListItems.listItems = listItems;
	}

	public void printCreate(){
		System.out.println("ListItems onCreateView");
	}
	
		
	public void updateList(){
		setListAdapter(customListViewAdapter);
	}
	
	//public void onLongListItemClick(ListViewItem lvi, )
	
	public void onListItemClick(ListView lv, View v, int pos, long id){
		Toast.makeText(getActivity(), Integer.toString(pos), Toast.LENGTH_SHORT).show();
		
		Helper.listItem = listItems.get(pos);
		//Helper.dayItems = listItems.get(pos).dayItems;
		Helper.newItem = false;
		Helper.listItemPos = pos;
		//Intent intent = new Intent(getActivity(), Details.class);
		//startActivity(intent);
		
		if (listItems.get(pos).dayItem.size() == 1){
			Helper.dayItemPos = 0;
			Intent intent = new Intent(getActivity(), SingleItemView.class);
			startActivity(intent);
		}
		else{
			Intent intent = new Intent(getActivity(), DayItemActivity.class);
			startActivity(intent);
		}
	}
	/*
	public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id){
		Toast.makeText(getActivity(), "delete item?", Toast.LENGTH_SHORT).show();

		return false;
	}
	*/
	
	/*
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		restoreData();
		
		View v = inflater.inflate(R.layout.activity_list_items,  container, false);
		
		return v;
		
	}
	*/
	@SuppressWarnings("unchecked")
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);		
				
		listView = (ListView) getActivity().findViewById(R.id.mainListView);
		customListViewAdapter = new CustomListViewAdapter(getActivity(), listItems);
		listItems = new ArrayList<ListViewItem>();
		df = new SimpleDateFormat("MM/dd/yyyy");
		setEmptyText("You have nothing planned!");
		
		restoreData();
		
		getListView().setOnItemLongClickListener(new OnItemLongClickListener(){
			public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id){
				
				Helper.listItemPos = pos;
				
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage("Delete this day?").setPositiveButton("Yes", dialogClickListener)
					.setNegativeButton("No", dialogClickListener).show();
				
				return true;
			}
		});
		
		updateList();
		printCreate();
	}
	
	
	
	public void saveData(){
		
		JSONArray jArray = new JSONArray();
		JSONObject jObj = new JSONObject();
		try {
			for (int i = 0; i < listItems.size(); i++){
				jObj.put("date", listItems.get(i).date);
				jObj.put("dayOfWeek", listItems.get(i).dayOfWeek);
				jObj.put("dayItem", listItems.get(i).getDayItemJsonStr());
				
				jArray.put(jObj);
				jObj = new JSONObject();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String jsonListItems = jArray.toString();
		
		SharedPreferences pref;
		pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString("listItems", jsonListItems);
		editor.commit();
		
		System.out.println("json: " + jsonListItems);
	}
	
	public void restoreData(){
		SharedPreferences prefs = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String listItemsString = "";
        listItemsString = prefs.getString("listItems", listItemsString);
        
        try {
        	JSONArray jArray = new JSONArray(listItemsString);
			JSONObject jObj;
			
			System.out.println("jArray size: " + jArray.length());		
			
			for (int i = 0; i < jArray.length(); i++){
				
				jObj = jArray.getJSONObject(i);
				
				ListViewItem lvi = new ListViewItem();
				lvi.date = jObj.getString("date");
				lvi.dayOfWeek = jObj.getString("dayOfWeek");
				lvi.dayItem = lvi.getDayItemFromJsonStr(jObj.getString("dayItem"));
				
				listItems.add(lvi);
	       }
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
        /**/
	}
	
	public void onDestroy(){
		super.onDestroy();
		
		saveData();
		//Gson gson = new Gson();
		//String json = gson.toJson(listItems);
		
		//SharedPreferences sp = getActivity().getSharedPreferences("savedData", 0);
		//SharedPreferences.Editor editor = settings.edit();
		//PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString("savedData", json).commit();
		//editor.putString(json, value)
		
		System.out.println("onDestroy");
	}
	
	public void onStop(){
		super.onStop();
		
		
		System.out.println("onStop");
	}
	
	public void onPause(){
		super.onPause();
		
		//saveData();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		//restoreData();
		updateList();	
	}
	
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener(){
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch(which){
			case DialogInterface.BUTTON_POSITIVE:
				listItems.remove(Helper.listItemPos);
				updateList();
				Toast.makeText(getActivity(), "Day deleted", Toast.LENGTH_SHORT).show();

				break;
			
			case DialogInterface.BUTTON_NEGATIVE:
				dialog.dismiss();
				break;
			}
		}
	};
	
	
	
	
	/*
	public void onSavedInstanceState(Bundle outState){
			
		
		//outState.putParcelableArrayList(LIST_ITEMS, listItems);
		super.onSaveInstanceState(outState);	
		//editor.putString(LIST_ITEMS, listItems.toString());
		//editor.commit();
		//outState.putParcelableArrayList(LIST_ITEMS, listItems);
	}
	*//*
	public ListItems(Parcel in){
		//ListItems.listItems = new ArrayList<ListViewItem>();
		ListItems.listItems = in.readArrayList(null);
		//in.readTypedList(listItems, ListViewItem.CREATOR);
	}
	
	public static final Parcelable.Creator<ListItems> CREATOR = new Parcelable.Creator<ListItems>() {
		public ListItems createFromParcel(Parcel in){
			//Log.i("log", "create from parcel: save listItems");
			return new ListItems(in);
		}
		
		public ListItems[] newArray(int size){
			return new ListItems[size];
		}
	};
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeList(listItems);
	}
	*/
	public static void printListItems(){
		for (ListViewItem lvi : listItems){
			System.out.println(lvi.date);
		}
	}
	
	public static void sortByDate(){
		Collections.sort(listItems, new Comparator<ListViewItem>(){
			public int compare(ListViewItem o1, ListViewItem o2){
				
				return o1.getDateObject().compareTo(o2.getDateObject());
			}
		});
		//Helper.listItems = listItems;
	}
	
	public static class DayItem{
		String time, note, description;
		SimpleDateFormat dateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		
		public long getDateTimeLong(String time, String date){
			//Date dt;
			try {
				Date dt = dateTime.parse(date +" " + time);
				return dt.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	public static class ListViewItem{
		
		SimpleDateFormat dayAbbreviation = new SimpleDateFormat("E");
		
		String date, dayOfWeek;
		ArrayList<DayItem> dayItem;
		
		public ArrayList<DayItem> getDayItemFromJsonStr(String json)
		{
			ArrayList<DayItem> dayList = new ArrayList<DayItem>();
			
			try {
	        	JSONArray jArray = new JSONArray(json);
				JSONObject jObj;
				
				System.out.println("jArray size: " + jArray.length());		
				
				for (int i = 0; i < jArray.length(); i++){
					
					jObj = jArray.getJSONObject(i);
					
					DayItem day = new DayItem();
					day.note = jObj.getString("note");
					day.description = jObj.getString("desc");
					day.time = jObj.getString("time");
					
					dayList.add(day);
		       }
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return dayList;
		}
		
		public String getDayItemJsonStr(){
			String json = "";
			JSONArray jArray = new JSONArray();
			JSONObject jObj = new JSONObject();
			
			for (int i = 0; i < dayItem.size(); i++)
			{
				try {
					jObj.put("time", dayItem.get(i).time);
					jObj.put("note",  dayItem.get(i).note);
					jObj.put("desc",  dayItem.get(i).description);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				jArray.put(jObj);
				jObj = new JSONObject();
			}
			json = jArray.toString();
			return json;
		}
		
		public void setDayOfWeek(Date d){
			this.dayOfWeek = dayAbbreviation.format(d);
			
		}
		
		public Date getDateObject(){
			Date d = new Date();
			
			try {
				d = df.parse(this.date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return d;
			
		}
		
	}

}
