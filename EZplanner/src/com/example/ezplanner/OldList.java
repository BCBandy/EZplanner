package com.example.ezplanner;

import java.util.ArrayList;

import com.example.ezplanner.ListItems.DayItem;
import com.example.ezplanner.ListItems.ListViewItem;

import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class OldList extends ListFragment {

	ListView listView;
	OldListAdapter oldListAdapter;
	static ArrayList<ListViewItem> listItems = new ArrayList<ListItems.ListViewItem>();
	int lviPos = 0, dayItemPos = 0;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		listView = (ListView) getActivity().findViewById(R.id.mainListView);
		oldListAdapter = new OldListAdapter(getActivity(), listItems);
		listItems = new ArrayList<ListViewItem>();

		initilizeListItems();
		//getOutdatedItems();
		
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
	}
	
	public void onResume(){
		super.onResume();
		
		
	}
	
	public void getOutdatedItems(){
		for(ListItems.ListViewItem lvi : ListItems.listItems){
			for(ListItems.DayItem dayItem : lvi.dayItem){
				
				//convert time from standard to military
				String milTime = convertToMilitaryTime(dayItem.time);
				//if day item has arrived or is old it will be added to oldList - left screen swipe
				if(dayItem.getDateTimeLong(milTime, lvi.date) < System.currentTimeMillis()){
					addToOldList(dayItem, lvi);
				}
				dayItemPos++;
			}
			dayItemPos = 0;
			lviPos++;
		}
		lviPos = 0;
	}
	
	public int dateIsNew(String newDate){
		if (listItems != null){
			for (int i = 0; i < listItems.size(); i++){
				if (listItems.get(i).date.equals(newDate)){
					return i;
				}
			}
		}
		return -1;
	}
	
	public void addToOldList(ListItems.DayItem dayItem, ListItems.ListViewItem lvi){
		
		int dateIndex = dateIsNew(lvi.date);
		//if old list doesn't have date, add it and the dayItem
		if(dateIndex == -1){
			ListItems.ListViewItem newLvi = new ListItems.ListViewItem();
			newLvi.date = lvi.date;
			newLvi.dayOfWeek = lvi.dayOfWeek;
			
			newLvi.dayItem = new ArrayList<ListItems.DayItem>();
			newLvi.dayItem.add(dayItem);
			
			listItems.add(newLvi);
			ListItems.listItems.get(lviPos).dayItem.remove(dayItemPos);
		}
		
		//if date exists, add dayItem to existing date
		else{
			listItems.get(dateIndex).dayItem.add(dayItem);
			ListItems.listItems.get(lviPos).dayItem.remove(dayItemPos);
		}
		
		//remove date from list if day items are size() 0
		int listIndex = Helper.dateIsNew(lvi.date);
		if (listIndex != -1){
			if (ListItems.listItems.get(listIndex).dayItem.size() == 0){
				ListItems.listItems.remove(listIndex);
			}
		}
	}
	
	public String convertToMilitaryTime(String time){
		String ampm;
		if(time.contains("am")){
			ampm = "am";
		}
		else{
			ampm = "pm";
		}
		
		String[] parseTime = time.replaceAll("amp", "").split(":");
		if (Integer.parseInt(parseTime[0]) != 12 && ampm.equals("pm")){
			int tmp = Integer.parseInt(parseTime[0]) + 12;
			parseTime[0] = Integer.toString(tmp);
		}
		else if(Integer.parseInt(parseTime[0]) == 12 && ampm.equals("am")){
			parseTime[0] = "0";
		}
		
		return parseTime[0] + ":" + parseTime[1];
	}
	
	public void initilizeListItems(){
		//ListItems.ListViewItem lvi = new ListItems.ListViewItem();
		//ListItems.DayItem day = new ListItems.DayItem();
		//lvi.dayItem = new ArrayList <ListItems.DayItem>();
		//lvi.date = "test";
		//listItems.add(lvi);
	}
	
	public void updateList(){
		setListAdapter(oldListAdapter);
	}
	
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle b){
		View v = inflater.inflate(R.layout.activity_empty_list, container, false);
		return v;
	}
	*/
/*
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.empty_list, menu);
		return true;
	}
*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
