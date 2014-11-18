package com.example.mywork.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;

/**
 * 读取本地文件工具
 * 
 * @author hc360
 * 
 */
public class AssetsUtil {

	// 读取assets中的文件
	public static String getFromAssets(Context c, String fileName) {
		try {
			InputStreamReader inputReader = new InputStreamReader(
					c.getResources().getAssets()
							.open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			String Result = "";
			while ((line = bufReader.readLine()) != null)
				Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String baseReadAssets(Context c, String txtname) {
		String text;
		try {
			InputStream is = c.getAssets().open(
					txtname);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			text = new String(buffer, "utf-8");

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return text;
	}

}
