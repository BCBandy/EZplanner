package com.example.ezplanner;

import java.util.List;

import com.example.ezplanner.ListItems.DayItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DayItemAdapter extends BaseAdapter {
	
	Context context;
	List<DayItem> dayItems;
	LayoutInflater inflater;

	public DayItemAdapter(Context ctx, List<DayItem> dItems) {
		//super(ctx, R.layout.day_item_row);
		
		this.context = ctx;
		this.dayItems = dItems;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//return Helper.listItem.dayItem.size();
		return dayItems.size();
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
				
		//DayItem items = Helper.listItem.dayItem.get(position);
		DayItem item = dayItems.get(position);
		
		if (convertView == null){
			//view = inflater.inflate(R.layout.item_row, null);
			//LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.day_item_row, parent, false);
			
		}
		
		
		TextView txtTime = (TextView) convertView.findViewById(R.id.tv_time);
		TextView txtNote = (TextView) convertView.findViewById(R.id.tv_times);
		//TextView txtDate = (TextView) convertView.findViewById(R.id.tv_date);
		
		
		
		txtTime.setText(item.time);
		//txtDate.setText(Helper.listItem.date);
		txtNote.setText(item.note);
		
		
		return convertView;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dayItems.get(position);
	}

}
