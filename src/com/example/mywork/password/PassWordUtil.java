package com.example.mywork.password;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;


public class PassWordUtil {

	// private static PassWordUtil util;

	public static final int PASSWORD = 1999;

	// public static PassWordUtil Instance ()
	// {
	// if ( util == null ) util = new PassWordUtil();
	// return util;
	// }

	public void setEditText(final EditText edit, final Activity activity) {
		
		edit.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_UP) {
					v.requestFocus();
					return showPasswordKeyBoard(edit, v, activity);
				}
				return true;
			}
		});
		edit.setFocusableInTouchMode(true);
		edit.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					showPasswordKeyBoard(edit, v, activity);
				}
			}
		});
	}
	
	public void setEditText(final EditText edit, final Activity activity, final LinearLayout layout) {
		edit.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_UP) {
					v.requestFocus();
					layout.scrollBy(0, 100);
					return showPasswordKeyBoard(edit, v, activity);
				}
				return true;
			}
		});
		edit.setFocusableInTouchMode(true);
		edit.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					showPasswordKeyBoard(edit, v, activity);
				}
			}
		});
	}

	private static boolean showPasswordKeyBoard(EditText edit, View v,
			Activity activity) {
		if (ActivityUserPassword.isShowing) {
			return true;
		}
		ActivityUserPassword.edit = edit;
		ActivityUserPassword.isShowing = true;

		InputMethodManager m = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		m.hideSoftInputFromWindow(v.getWindowToken(),
				InputMethodManager.RESULT_HIDDEN);

		Intent intent = new Intent(activity, ActivityUserPassword.class);

		activity.startActivity(intent);

		return ActivityUserPassword.isShowing;
	}

}
