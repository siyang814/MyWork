package com.example.mywork.util;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MobileUtil {

	// 获取IMEI(手机串号)
			public static String getIMEI(Context context) {
				TelephonyManager tm = (TelephonyManager) context
						.getSystemService(Context.TELEPHONY_SERVICE);
				String imei = tm.getDeviceId();
				String tel = tm.getLine1Number();
				String simid = tm.getSimSerialNumber();
				String imsi = tm.getSubscriberId();
				log("imei = " + imei);
				// String info = "DeviceId = " + imei +"\nLine1Number = " + tel
				// +"\nSimSerialNumber = " + imsi+"\nSubscriberId = " + SubscriberId;
				return imei;
			}

			// 获取IMSI(SIM卡串号)
			public static String getIMSI(Context context) {
				TelephonyManager tm = (TelephonyManager) context
						.getSystemService(Context.TELEPHONY_SERVICE);
				String imei = tm.getDeviceId();
				String tel = tm.getLine1Number();
				String simid = tm.getSimSerialNumber();
				String imsi = tm.getSubscriberId();
				log("imsi = " + imsi);
				// String info = "DeviceId = " + imei +"\nLine1Number = " + tel
				// +"\nSimSerialNumber = " + imsi+"\nSubscriberId = " + SubscriberId;
				return imsi;
			}
			
			//获取机型
					public static String getModal(){
						String modal = android.os.Build.MODEL;
						log("MODEL = " + modal);
						return modal;
					}
					
					//获取SDK级别代号
					public static String getSDKLevel(){
						int sdkLevel = android.os.Build.VERSION.SDK_INT;
						log("SDK Levle = " + sdkLevel);
						return ""+ sdkLevel;
					}
					
					//获取系统版本号
					public static int getSystemVersionCode(Context context) {
						int versionCode = 0;
						try {
							versionCode = context.getPackageManager().getPackageInfo(
									context.getApplicationInfo().packageName, 0).versionCode;
						} catch (NameNotFoundException e) {
							e.printStackTrace();
						}
						log("VersionCode = " + versionCode);
						return versionCode;
					}
					
					// 获取系统版本号显示
					public static String getSystemVersionName(Context context) {
						String versionName = "";
						try {
							versionName = context.getPackageManager().getPackageInfo(
									context.getApplicationInfo().packageName, 0).versionName;
						} catch (NameNotFoundException e) {
							e.printStackTrace();
						}
						log("VersionName = " + versionName);
						return versionName;
					}
					
					// 获取系统版本号
					public static String getSystemVersion() {
						String version = android.os.Build.VERSION.RELEASE;
						log("VERSION RELEASE = " + version);
						return version;
					}
					
					// 获取SIM卡运行商
					public static String getIMSIType(Context context) {
						String providersName = "";
						try {
							TelephonyManager telephonyManager = (TelephonyManager) context
								.getSystemService(Context.TELEPHONY_SERVICE);
							String IMSI = telephonyManager.getSubscriberId();
							// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
							if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
								providersName = "中国移动";
							} else if (IMSI.startsWith("46001")) {
								providersName = "中国联通";
							} else if (IMSI.startsWith("46003")) {
								providersName = "中国电信";
							} else if (telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY){
								providersName = telephonyManager.getSimOperatorName();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						return providersName;
					}
					
					public static void log ( String s )
					{
						Log.w("MYUTIL", s);
					}
	
}
