package com.example.ezplanner;

import java.util.ArrayList;
import java.util.List;

import com.example.ezplanner.ListItems.ListViewItem;

//import com.example.listsubitem.MainActivity.ListViewItem;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Details extends ActionBarActivity implements OnClickListener{

	TextView tv_date, pt_note, pt_description, tv_time;
	Button btn_save;
	View view;
	
	public Details(){
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		System.out.println("oncreate called");
		//initButtons();
		//initTextClick();
		tv_time = (TextView)findViewById(R.id.tv_date);
		tv_date = (TextView)findViewById(R.id.tv_notes);
		pt_note = (TextView)findViewById(R.id.pt_note);
		pt_description = (TextView)findViewById(R.id.pt_description);
		btn_save = (Button)findViewById(R.id.btn_save);
		
		tv_time.setOnClickListener(this);
		tv_date.setOnClickListener(this);
		btn_save.setOnClickListener(this);
		
		if (Helper.newItem == true){
			tv_date.setText(Helper.getDate());
			tv_time.setText(Helper.defaultTime);
		}
		else{
			tv_date.setText(Helper.listItem.date);
			//pt_note.setText(Helper.listItem.dayItem.note);
			//pt_description.setText(Helper.dayItems.description);
			//tv_time.setText(Helper.dayItems.time);
		}
	}
	
	@Override
	public void onClick(View v) {
		
		if (v == btn_save){
			if (Helper.newItem == true){
					Helper.note = pt_note.getText().toString();		
					Helper.description = pt_description.getText().toString();	
				
				int dateIndex = Helper.dateIsNew(tv_date.getText().toString());
				
				//If item has new date, create new ListViewItem, new = -1
				if (-1 == dateIndex){
					ListItems.listItems.add(new ListItems.ListViewItem()
					{{
						date = Helper.getDate();
						setDayOfWeek(getDateObject());
						dayItem = new ArrayList<ListItems.DayItem>();
						
						dayItem.add(new ListItems.DayItem()
						{{
							time = tv_time.getText().toString();
							note = pt_note.getText().toString();
							description = pt_description.getText().toString();
						}});
					}});
				}
				
				//If item date exists, add to dayItem list
				else{
					ListItems.listItems.get(dateIndex).dayItem.add(new ListItems.DayItem()
					{{
						time = tv_time.getText().toString();
						note = pt_note.getText().toString();
						description = pt_description.getText().toString();
					}});
					Helper.sortByTime(ListItems.listItems.get(dateIndex));
				}
				Toast.makeText(Details.this, Helper.getDate()+ " added!", Toast.LENGTH_SHORT).show();
				}
			else{
				Helper.listItem.dayItem.get(Helper.dayItemPos).note = pt_note.getText().toString();
				Helper.listItem.dayItem.get(Helper.dayItemPos).description = pt_description.getText().toString();
			}
			
			ListItems.sortByDate();
			Details.this.finish();
		}
		
		else if (v == tv_date){
			String oldDate[] = Helper.parseDate(tv_date.getText().toString());
				DatePickerDialog dialog = new DatePickerDialog(Details.this, datePickListener, Integer.parseInt(oldDate[2]), Integer.parseInt(oldDate[0]) - 1, Integer.parseInt(oldDate[1]));
				dialog.show();
				
		}
		else if (v == tv_time){
			String oldTime[] = Helper.parseDate(tv_time.getText().toString());					
			
			
			if (Helper.isMilitaryTime == false){
				TimePickerDialog dialog = new TimePickerDialog(Details.this, timePickListener, Integer.parseInt(oldTime[0]), Integer.parseInt(oldTime[1]), false);
				dialog.show();
			}
		}
		
	}
	
	private TimePickerDialog.OnTimeSetListener timePickListener = new TimePickerDialog.OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

			boolean isAm;
			//12pm is noon 12am is midnight
			if ((float)hourOfDay / 12 < 1 | (float)hourOfDay / 12 == 2)
				isAm = true;
			else
				isAm = false;
			
			if (!(hourOfDay == 12 | hourOfDay == 24) && Helper.isMilitaryTime == false){
				hourOfDay = hourOfDay % 12;
			}
			if (hourOfDay == 0){
				hourOfDay = 12;
			}
			
			//sets Helper.listItems.time = a string hr:min:ampm
			
			Helper.setTime(hourOfDay, minute, isAm);
			
			tv_time.setText(Helper.time);
			
			if (Helper.newItem == false){
				Helper.listItem.dayItem.get(Helper.dayItemPos).time = Helper.time;
			}
			
		}
	};
	
	private DatePickerDialog.OnDateSetListener datePickListener = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			
			Helper.year = year;
			Helper.day = dayOfMonth;
			Helper.month = monthOfYear;
			tv_date.setText(Helper.getDate());
			
			if (Helper.newItem == false){
				Helper.listItem.date = Helper.getDate();
			}
			
		}
	};
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
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

	
}
