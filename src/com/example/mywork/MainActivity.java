package com.example.mywork;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;

import com.example.mywork.password.PassWordUtil;
import com.example.mywork.util.Common;

public class MainActivity extends Activity {

	EditText edit;
	
	PassWordUtil pass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Common.setWidthAndHeight(this);
		
		pass = new PassWordUtil();
		
		edit = (EditText) findViewById(R.id.edit);
		
		
		pass.setEditText(edit, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
