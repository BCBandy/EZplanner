package com.example.ezplanner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.DatePicker;



public class MainActivity extends FragmentActivity {
		
	static ViewPager viewpager;
	PagerAdapter pAdapter;
	boolean exitDialog = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		viewpager = (ViewPager) findViewById(R.id.pager);
		pAdapter = new PagerAdapter(getSupportFragmentManager());
		
		OldList frag0 = new OldList();
		FragmentStart frag1 = new FragmentStart();
		ListItems frag2 = new ListItems();
		
		pAdapter.addFragment(frag0);
		pAdapter.addFragment(frag1);
		pAdapter.addFragment(frag2);
		
		//viewpager.setCurrentItem(1, false);
		viewpager.setAdapter(pAdapter);
	}
	
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener(){
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch(which){
			case DialogInterface.BUTTON_POSITIVE:
				finish();
				break;
			
			case DialogInterface.BUTTON_NEGATIVE:
				dialog.dismiss();
				break;
			}
		}
	};

	public void onBackPressed(){
		
		System.out.println("back button pressed");
		//FragmentStart.backBug = true;
		//finish();
		
		if (exitDialog == true){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Exit the app?").setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();
		}
		else{
			finish();
		}
	
		//super.onBackPressed();
	}
}
	/*
	public Fragment getActiveFragment(ViewPager container, int position){
		String name = makeFragmentName(container.getId(), position);
		FragmentManager frag = getSupportFragmentManager();
		return frag.findFragmentByTag(name);
	}
	
	public static String makeFragmentName(int viewId, int index){
		return "android:switcher:" + viewId + ":" + index;
	}
	
	/*
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		if (keyCode == KeyEvent.KEYCODE_BACK){
			FragmentStart.backBug = true;
			//onBackPressed();
			return true;
		}return super.onKeyDown(keyCode, event);
	}
	*/
	//@Override
	
	
	


