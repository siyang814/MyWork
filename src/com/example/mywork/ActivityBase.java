package com.example.mywork;

import android.app.Activity;
import android.os.Bundle;

public class ActivityBase extends Activity{

	public static Activity currentActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		currentActivity = this;
	}
	
}
