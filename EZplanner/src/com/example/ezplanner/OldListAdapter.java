package com.example.ezplanner;

import java.util.List;

import com.example.ezplanner.ListItems.ListViewItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class OldListAdapter extends ArrayAdapter<ListViewItem>{

	//LayoutInflater inflater;
	List<ListViewItem> listItems;
	Context context;
	
	public OldListAdapter(Context ctx, List<ListViewItem> lstItems){
		//super();
		super(ctx, R.layout.item_row);
				
		this.listItems = lstItems;
		this.context = ctx;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return OldList.listItems.size();
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
				
		ListViewItem item = OldList.listItems.get(position);
		//View view = convertView;
		//ListItems.ListViewItem newItem;// = new ListItems.ListViewItem();
		
		if (convertView == null){
			//view = inflater.inflate(R.layout.item_row, null);
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_row, parent, false);
		}
		/*
		else{
			newItem = new ListItems.ListViewItem();
			newItem = (ListItems.ListViewItem)view.getTag();
		}*/
		
		
		//TextView txtTitle = (TextView) convertView.findViewById(R.id.tv_time);
		TextView txtSubtitle = (TextView) convertView.findViewById(R.id.tv_times);
		TextView date = (TextView) convertView.findViewById(R.id.tv_date);
		TextView dayOfWeek = (TextView) convertView.findViewById(R.id.tv_DayItem_dayOfWeek);
		//txtTitle.setText(item.date);
		
		
		
		date.setText(item.date);
		txtSubtitle.setText(Helper.getAllTime(item));
		dayOfWeek.setText(item.dayOfWeek);
		
		
		
		
		
		return convertView;
	}

}
