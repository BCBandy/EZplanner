package com.example.ezplanner;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TextEditor extends Activity implements OnClickListener{

	EditText et;
	Button btn_save;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_editor);
		
		btn_save = (Button) findViewById(R.id.btn_save_editText);
		btn_save.setOnClickListener(this);
		
		
		et = (EditText) findViewById(R.id.et_theText);
		et.setText(Helper.editText);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.text_editor, menu);
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

	@Override
	public void onClick(View v) {
		if (Helper.noteOrDes.equals("note")){
			Helper.listItem.dayItem.get(Helper.dayItemPos).note = et.getText().toString();
		}
		else if(Helper.noteOrDes.equals("des")){
			Helper.listItem.dayItem.get(Helper.dayItemPos).description = et.getText().toString();
		}
		finish();
		
	}
}
