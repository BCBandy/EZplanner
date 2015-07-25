package com.example.ezplanner;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class PagerAdapter extends FragmentPagerAdapter{
	
	private final List<Fragment> mFragments = new ArrayList<Fragment>();
	
	public PagerAdapter(FragmentManager fragmentManager){
		super(fragmentManager);
		
	}
	/*
	@Override
	public Fragment getItem(int arg0){
		switch (arg0){
		case 0:
			return new FragmentStart();
		case 1:						
			return new ListItems();
		}
		return null;
	}
	*/
	public Fragment getItem(int pos){
		return mFragments.get(pos);
	}
	public static void getCurrentFrag(){
		//getActivity().getFragmentManager()
	}
	
	public static String makeFragmentName(int viewId, int index){
		return "android:switcher:" + viewId + ":" + index;
	}
	
	public int getItemPosition(Object o){
		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mFragments.size();
	}
	
	public void addFragment(Fragment frag){
		mFragments.add(frag);
		notifyDataSetChanged();
	}
	
	
}
