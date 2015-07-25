package com.example.ezplanner;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SingleItemView extends Activity implements OnClickListener{
	
	TextView date, time, noteE, desc, dayOfWeek;
	//EditText noteE;
	Button btn_delete;
	String str_note, str_desc;
	View layoutView;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_item_view);
		
		
		date = (TextView) findViewById(R.id.tv_time);
		noteE = (TextView) findViewById(R.id.note);
		time = (TextView) findViewById(R.id.time);
		desc = (TextView) findViewById(R.id.description);
		dayOfWeek = (TextView) findViewById(R.id.tv_SingleView_dayOfWeek);
		btn_delete = (Button) findViewById(R.id.btn_delete);
		
		btn_delete.setOnClickListener(this);
		
		str_note = Helper.listItem.dayItem.get(Helper.dayItemPos).note;
		str_desc = Helper.listItem.dayItem.get(Helper.dayItemPos).description;
		
		date.setText(Helper.listItem.date);
		dayOfWeek.setText(Helper.listItem.dayOfWeek);
		noteE.setText(str_note);
		desc.setText(str_desc);
		time.setText(Helper.listItem.dayItem.get(Helper.dayItemPos).time);
		
		noteE.setOnClickListener(this);
		desc.setOnClickListener(this);
		time.setOnClickListener(this);
		date.setOnClickListener(this);
		
		/*
		layoutView = findViewById(R.layout.activity_single_item_view);
		layoutView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		*/
	}
	
	private DatePickerDialog.OnDateSetListener datePickListener = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			
			Helper.year = year;
			Helper.day = dayOfMonth;
			Helper.month = monthOfYear;
			date.setText(Helper.getDate());
			
			if (Helper.newItem == false && view.isShown()){
				//Helper.listItem.date = Helper.getDate();
				
				ListItems.DayItem day = new ListItems.DayItem();
				day = ListItems.listItems.get(Helper.listItemPos).dayItem.get(Helper.dayItemPos);	
				int dateIndex = Helper.dateIsNew(Helper.getDate());
				
				//if date is not new, add day item to existing ListViewItem
				//else make new ListViewItem for that day
				if (dateIndex != -1){
					ListItems.listItems.get(dateIndex).dayItem.add(day);
					Helper.sortByTime(ListItems.listItems.get(dateIndex));
					ListItems.listItems.get(Helper.listItemPos).dayItem.remove(Helper.dayItemPos);
				}
				else{
					ListItems.ListViewItem lvi = new ListItems.ListViewItem();
					lvi.dayItem = new ArrayList<ListItems.DayItem>();
					
					lvi.date = Helper.getDate();
					lvi.setDayOfWeek(lvi.getDateObject());
					lvi.dayItem.add(day);
					
					ListItems.listItems.add(lvi);
					
					ListItems.listItems.get(Helper.listItemPos).dayItem.remove(Helper.dayItemPos);
					ListItems.sortByDate();
				}
				
				if (ListItems.listItems.get(Helper.listItemPos).dayItem.size() == 0){
					ListItems.listItems.remove(Helper.listItemPos);
				}
				
				finish();
				System.out.println("item removed");
			}
			
		}
		
	};
	
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
			time.setText(Helper.time);
			
			if (Helper.newItem == false){
				ListItems.listItems.get(Helper.listItemPos).dayItem.get(Helper.dayItemPos).time = Helper.time;
				/*
				ListItems.DayItem oldDay = ListItems.listItems.get(Helper.listItemPos).dayItem.get(Helper.dayItemPos);
				
				ListItems.DayItem day = new ListItems.DayItem();
				day.description = oldDay.description;
				day.note = oldDay.note;
				day.time = oldDay.time;
				
				ListItems.listItems.get(Helper.listItemPos).dayItem.remove(oldDay);
				ListItems.listItems.get(Helper.listItemPos).dayItem.add(day);
				*/
				//Helper.sortByTime(ListItems.listItems.get(Helper.listItemPos));
			}
		}
	};
	/*
	public void onBackPressed(){
		
		if (!str_note.equals(noteE.getText().toString()) && !str_desc.equals(desc.getText().toString())){
			Helper.listItem.dayItem.get(Helper.dayItemPos).note = noteE.getText().toString(); 
			Helper.listItem.dayItem.get(Helper.dayItemPos).description = desc.getText().toString();
			Toast.makeText(this, "Note and Description saved", Toast.LENGTH_LONG).show();
		}
		else if(!str_note.equals(noteE.getText().toString())){
			Helper.listItem.dayItem.get(Helper.dayItemPos).note = noteE.getText().toString();
			Toast.makeText(this, "Note saved", Toast.LENGTH_LONG).show();
		}
		else if (!str_desc.equals(desc.getText().toString())){
			Helper.listItem.dayItem.get(Helper.dayItemPos).description = desc.getText().toString();
			Toast.makeText(this, "Description saved", Toast.LENGTH_LONG).show();
		
		}
		finish();
	}
*/
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener(){
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch(which){
			case DialogInterface.BUTTON_POSITIVE:
				ListItems.listItems.get(Helper.listItemPos).dayItem.remove(Helper.dayItemPos);
				
				if (ListItems.listItems.get(Helper.listItemPos).dayItem.size() < 1){
					ListItems.listItems.remove(Helper.listItemPos);
				}
				finish();
				break;
			
			case DialogInterface.BUTTON_NEGATIVE:
				dialog.dismiss();
				break;
			}
		}
	};
	@Override
	public void onClick(View v) {
		if (v == btn_delete){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Delete this item?").setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();
		}
		else if(v == noteE){
			Helper.editText = str_note;
			Helper.noteOrDes = "note";
			Intent intent = new Intent(this, TextEditor.class);				
			startActivity(intent);
		}
		else if(v == desc){
			Helper.editText = str_desc;
			Helper.noteOrDes = "des";
			Intent intent = new Intent(this, TextEditor.class);				
			startActivity(intent);
		}
		else if (v == time){
			String oldTime[] = Helper.parseDate(time.getText().toString());					
			
			
			if (Helper.isMilitaryTime == false){
				TimePickerDialog dialog = new TimePickerDialog(SingleItemView.this, timePickListener, Integer.parseInt(oldTime[0]), Integer.parseInt(oldTime[1]), false);
				dialog.show();
			}
			
		}
		else if (v == date){
			String oldDate[] = Helper.parseDate(date.getText().toString());
				DatePickerDialog dialog = new DatePickerDialog(SingleItemView.this, datePickListener, Integer.parseInt(oldDate[2]), Integer.parseInt(oldDate[0]) - 1, Integer.parseInt(oldDate[1]));
				dialog.show();
				
		}
	}

	public void onResume(){
		super.onResume();
		
		str_note = Helper.listItem.dayItem.get(Helper.dayItemPos).note;
		str_desc = Helper.listItem.dayItem.get(Helper.dayItemPos).description;
		
		noteE.setText(str_note);
		desc.setText(str_desc);
	}
		

}