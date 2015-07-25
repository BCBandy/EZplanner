package com.example.ezplanner;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DayItemActivity extends Activity{

	ListView listView;
	DayItemAdapter dayListAdapter;
	TextView date, dayOfWeek;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_day_item);
		
		date = (TextView) findViewById(R.id.dayItemDate);
		dayOfWeek = (TextView) findViewById(R.id.tv_DayItem_dayOfWeek);
		
		date.setText(Helper.listItem.date);
		dayOfWeek.setText(Helper.listItem.dayOfWeek);
		
		
		listView = (ListView)findViewById(R.id.dayListView);
		//listView = getListView();
		dayListAdapter = new DayItemAdapter(DayItemActivity.this, ListItems.listItems.get(Helper.listItemPos).dayItem);//Helper.listItem.dayItem);
		listView.setAdapter(dayListAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(DayItemActivity.this, "clicked "+ position, Toast.LENGTH_SHORT).show();
				
				Helper.dayItemPos = position;
				Intent intent = new Intent(DayItemActivity.this, SingleItemView.class);
				startActivity(intent);
			}
		});
	}

	public void onResume(){
		super.onResume();
		System.out.println("onResume DayItemActivity");
		
		if (Helper.listItem.dayItem.size() == 0){
			finish();
		}
		//Helper.listItem.dayItem = ListItems.listItems.get(Helper.listItemPos).dayItem;
		//dayListAdapter = new DayItemAdapter(DayItemActivity.this, ListItems.listItems.get(Helper.listItemPos).dayItem);		
		Helper.sortByTime(ListItems.listItems.get(Helper.listItemPos));
		listView.setAdapter(dayListAdapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.day_item, menu);
		return true;
	}

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
/*
	public void onListItemClick(ListView listView, View view, int position,	long id){
		
		//Helper.listItem = Helper.listItem.dayItem.get(position);
		Helper.dayItemPos = position;
		
		Intent intent = new Intent(this, SingleItemViewer.class);
		startActivity(intent);
		
		Toast.makeText(this, "clicked", 1).show();
	}
*/

/*
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Helper.dayItemPos = position;
		
		Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
		
		Intent intent = new Intent(this, SingleItemViewer.class);
		startActivity(intent);
		
		
		
	}
*/
}
