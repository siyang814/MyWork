package com.example.mywork;

import com.example.mywork.util.Common;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

public class MyApplication extends Application{

	public static String downloadDir = "/MyWork/";// 安装目录
	public static MyApplication instance;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
	}
	
	public Context getContext ()
	{
		return getApplicationContext();
	}
	
	public String getDirPath ()
	{
		if ( Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) )
		{
			return Environment.getExternalStorageDirectory() + downloadDir;
		}
		else
		{
			Common.toast("no sd Card");
			return null;
		}
	}
	
	public void cancel()
	{
		
	}
	
}
