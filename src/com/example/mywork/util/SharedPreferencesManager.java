package com.example.mywork.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 保存到本地数据类
 * 
 * @author hc360
 * 
 */
public class SharedPreferencesManager {

	private static SharedPreferences sharedPreferences;

	// 保存SP信息
	public static void setPreferenceSP(Context c, String key, String value) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();

		editor.putString(key, value);

		editor.commit();

	}

	/**
	 * 保存首页分类
	 * 
	 * @param c
	 * @param key
	 * @param value
	 */
	public static void setPreferenceCusname(Context c, String cusname) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();

		editor.putString("cusname", cusname);

		editor.commit();

	}

	public static String getetPreferenceCusname(Context c) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		return sharedPreferences.getString("cusname", "");
	}

	/**
	 * 保存custom某一项
	 * 
	 * @param c
	 * @return
	 */
	public static void setPreferenceCustomPos(Context c, String custom) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("custompos", custom);
		editor.commit();
	}

	/**
	 * 读取custom某一项
	 * 
	 * @param c
	 * @return
	 */
	public static String getPreferenceCustomPos(Context c) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		return sharedPreferences.getString("custompos", "");
	}

	/**
	 * 保存custom
	 * 
	 * @param c
	 * @return
	 */
	public static void setPreferenceCustom(Context c, String custom) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("custom", custom);
		editor.commit();
	}

	/**
	 * 读取custom
	 * 
	 * @param c
	 * @return
	 */
	public static String getPreferenceCustom(Context c) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		return sharedPreferences.getString("custom", "");
	}

	/**
	 * 保存ishelp
	 * 
	 * @param c
	 * @param b
	 */
	public static void setPreferenceIsHelp(Context c, boolean b) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();

		editor.putBoolean("ishelp", b);

		editor.commit();
	}

	/**
	 * 保存这一版本是否进入过
	 * 
	 * @param c
	 * @param b
	 */
	public static void setPreferenceVerson(Context c, boolean b) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();

		editor.putBoolean("verson", b);

		editor.commit();
	}

	/**
	 * 读取ishelp
	 * 
	 * @param c
	 * @param b
	 */
	public static boolean getPreferenceVerson(Context c) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		return sharedPreferences.getBoolean("verson", false);
	}

	/**
	 * 读取ishelp
	 * 
	 * @param c
	 * @param b
	 */
	public static boolean getPreferenceIsHelp(Context c) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		return sharedPreferences.getBoolean("ishelp", false);
	}

	/**
	 * 保存用户帐号密码
	 * 
	 * @param c
	 * @param name
	 * @param password
	 */
	public static void setPreferenceLogin(Context c, String name,
			String password) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();

		editor.putString("username", name);

		editor.putString("pass", password);

		editor.commit();
	}

	/**
	 * 获取用户名
	 * 
	 * @param c
	 * @return
	 */
	public static String getPreferenceUserName(Context c) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		return sharedPreferences.getString("username", "");
	}

	/**
	 * 获取用户密码
	 * 
	 * @param c
	 * @return
	 */
	public static String getPreferenceUserPass(Context c) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		return sharedPreferences.getString("pass", "");
	}

	/**
	 * 保存NewsMain
	 * 
	 * @param c
	 */
	public static void setPreferenceNewsMain(Context c, String newsmain) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();

		editor.putString("newsmain", newsmain);

		editor.commit();
	}

	/**
	 * \ 获取newsMain
	 * 
	 * @param c
	 * @return
	 */
	public static String getPreferenceNewsMain(Context c) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		return sharedPreferences.getString("newsmain", "");
	}

	/**
	 * 保存关键字第一级
	 * 
	 * @param c
	 * @param cate
	 */
	public static void setPreferenceCate(Context c, String cate) {

		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);

		Editor editor = sharedPreferences.edit();

		editor.putString("cate", cate);

		editor.commit();
	}

	/**
	 * 保存关键字第二级
	 * 
	 * @param c
	 * @param cate
	 */
	public static void setPreferenceKeword(Context c, String keyword) {

		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);

		Editor editor = sharedPreferences.edit();

		editor.putString("keyword", keyword);

		editor.commit();
	}

	/**
	 * 获取关键字第二级
	 * 
	 * @param c
	 * @return
	 */

	public static String getPreferenceKeyWord(Context c) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		return sharedPreferences.getString("keyword", "");
	}

	/**
	 * \ 获取关键字第一级
	 * 
	 * @param c
	 * @return
	 */
	public static String getPreferenceCate(Context c) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		return sharedPreferences.getString("cate", "");
	}

	/**
	 * 保存城市
	 * 
	 * @param c
	 * @param city
	 */
	public static void setPreferenceCity(Context c, String city) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();

		editor.putString("city", city);

		editor.commit();

	}

	/**
	 * 保存首页关键字
	 * 
	 * @param c
	 * @param second
	 */
	public static void setPreferenceSecondCate(Context c, String secondcate) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();

		editor.putString("secondcate", secondcate);

		editor.commit();
	}

	/**
	 * 获取首页关键字
	 * 
	 * 
	 * 
	 */
	public static String getPreferenceSecondcate(Context c) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		return sharedPreferences.getString("secondcate", "");
	}

	/**
	 * 保存首页分类
	 * 
	 * @param c
	 * @param second
	 */
	public static void setPreferenceSecondEntity(Context c, String second) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();

		editor.putString("second", second);

		editor.commit();
	}

	/**
	 * 获取首页分类
	 * 
	 * 
	 * 
	 */
	public static String getPreferenceSecondentity(Context c) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		return sharedPreferences.getString("second", "");
	}

	/**
	 * 保存城市地區
	 * 
	 * @param c
	 * @param pcity
	 */
	public static void setPreferencePCity(Context c, String pcity) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();

		editor.putString("pcity", pcity);

		editor.commit();
	}

	/**
	 * 获取城市地區
	 * 
	 * @param c
	 * @param city
	 */
	public static String getPreferencePCity(Context c) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		return sharedPreferences.getString("pcity", "");
	}

	/**
	 * 获取城市
	 * 
	 * @param c
	 * @param city
	 */
	public static String getPreferenceCity(Context c) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		return sharedPreferences.getString("city", "");
	}

	/**
	 * 获取是否弹出选择
	 * 
	 * @param c
	 * @return
	 */
	public static boolean getPreferenceIslog(Context c) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		return sharedPreferences.getBoolean("islog", false);

	}

	/**
	 * 保存是否弹出选择
	 * 
	 * @param c
	 * @param b
	 */
	public static void setPreferenceIslog(Context c, boolean b) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("islog", b);
		editor.commit();
	}

	/**
	 * 清空本地储存
	 * 
	 * @param c
	 */
	public static void clearPreferenceAll(Context c) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * 保存筛选结果
	 * 
	 * @param mContext
	 * @param data
	 */
	public static void setPreferenceSearch(Context mContext, String data) {
		sharedPreferences = mContext.getSharedPreferences("SP",
				mContext.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("search", data);
		editor.commit();
	}

	/**
	 * 获取筛选结果
	 * 
	 * @param mContext
	 */
	public static String getPreferenceSearch(Context mContext) {
		sharedPreferences = mContext.getSharedPreferences("SP",
				mContext.MODE_PRIVATE);
		return sharedPreferences.getString("search", "");
	}

	/**
	 * 保存一键重发时间
	 * 
	 * @param mContext
	 * @param data
	 */
	public static void setPreferenceRepeat(Context mContext, String data) {
		sharedPreferences = mContext.getSharedPreferences("SP",
				mContext.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("repeat", data);
		editor.commit();
	}

	/**
	 * 获取一键重发时间
	 * 
	 * @param mContext
	 */
	public static String getPreferenceRepeat(Context mContext) {
		sharedPreferences = mContext.getSharedPreferences("SP",
				mContext.MODE_PRIVATE);
		return sharedPreferences.getString("repeat", "");
	}

	/**
	 * 保存custom
	 * 
	 * @param c
	 * @return
	 */
	public static void setPreferenceMessage(Context c, boolean isnewmessage) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("isnewmessage", isnewmessage);
		editor.commit();
	}

	/**
	 * 读取custom
	 * 
	 * @param c
	 * @return
	 */
	public static boolean getPreferenceMessage(Context c) {
		sharedPreferences = c.getSharedPreferences("SP", c.MODE_PRIVATE);
		return sharedPreferences.getBoolean("isnewmessage", false);
	}
}
