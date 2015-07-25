package com.example.ezplanner;

import java.util.Calendar;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class FragmentStart extends Fragment implements OnClickListener, DatePickerDialog.OnDateSetListener{
	
	static final int DATE_DIALOG_ID = 0;	
	int clickCount = 0, detailsCount = 0;
	Button btn_add, btn_test;
	static boolean backBug = true;
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//initButtons();
		view = inflater.inflate(R.layout.activity_fragment_start, container, false);
		btn_add = (Button) view.findViewById(R.id.btn_add);
		btn_add.setOnClickListener(this);
		btn_test = (Button) view.findViewById(R.id.btn_save_editText);
		btn_test.setOnClickListener(this);
		
		MainActivity.viewpager.setCurrentItem(1, false);
		
		System.out.println("fragment start oncreate");
		
		return view;
		
	}
	
	
	
	//all button clicks
	//@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v){
		if (v == btn_add){		
		
			detailsCount = 0;
			
			final Calendar cal = Calendar.getInstance();
			Helper.month = cal.get(Calendar.MONTH);
			Helper.day = cal.get(Calendar.DAY_OF_MONTH);
			Helper.year = cal.get(Calendar.YEAR);
			
			backBug = false;
			
			Dialog d = onCreateDialog();
			d.setOnDismissListener(mOnDismissListener);
			d.show();
			//new DatePickerDialog(getActivity(), dateSetListener, Helper.year, Helper.month, Helper.day).show();
						
		}	
		if (v == btn_test){
			Toast.makeText(getActivity(), "dont PRESS ME!", Toast.LENGTH_SHORT).show();
			System.out.println("size of list is: " + ListItems.listItems.size());
			ListItems.printListItems();
			
			
		}
	}
	
	
	public void onBackPressed(){
		System.out.println("back button pressed");
		backBug = true;
	
	}
	
	public Dialog onCreateDialog(){
		return new DatePickerDialog(getActivity(),this, Helper.year, Helper.month, Helper.day);
		
	}
	
	
	//if back is pressed return true
	/*
	public boolean onKey(int keyCode, KeyEvent event){
		
		if (keyCode == KeyEvent.KEYCODE_BACK){
			backBug = true;
			//onBackPressed();
			return true;
		}return false;//super.onKeyDown(keyCode, event);
	}
	*/
	private DialogInterface.OnDismissListener mOnDismissListener = new DialogInterface.OnDismissListener(){
		//@Override
		public void onDismiss(DialogInterface dialog){
			backBug = true;
			System.out.println("dismissed pressed");
			dialog.dismiss();
		}
	};
	
	//private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {		
		
		//@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {		
			
			
			Helper.year = year;
			Helper.month = monthOfYear;
			Helper.day = dayOfMonth;
			
									
			System.out.println("backBug: " +backBug);
			if (detailsCount == 0 && backBug == false){
				Helper.newItem = true;
				Intent intent = new Intent(getActivity(), Details.class);				
				startActivity(intent);
				detailsCount += 1;
			}
		}	
	//};




	
}
	


