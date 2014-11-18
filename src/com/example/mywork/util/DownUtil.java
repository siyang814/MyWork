package com.example.mywork.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
/**
 * 				应用更新， 下载类
 * @author Ruby
 *
 */
public class DownUtil {

	private static DownUtil instance;
	
	private Activity activity;
	
	private ProgressDialog progressDialog; // 进度条对话框引用
	private int provalue = 0;
	private int downloadedSize = 0;
	/**
	 * 提示更新
	 */
	public static final int DIALOG_YES_MESSAGE = -1;
	/**
	 * 提示更新， 并下载
	 */
	public static final int DIALOG_YES_NO_MESSAGE2 = -2;
	/**
	 * 提示更新， 并下载(必须)
	 */
	public static final int DIALOG_YES_NO_MESSAGE = -3;
	// APK名称， 下载地址， 文件夹名称
	private String apkName, downURL, folderName;
	
	public static DownUtil getInstance ()
	{
		if ( instance == null ) instance = new DownUtil();
		return instance;
	}
	/**
	 * 				必须调用， 传入 activity
	 * @param activity
	 * @param apkName			apk名称
	 * @param folderName		文件夹名称
	 * @param downURL			下载地址
	 */
	public void setActivity ( Activity activity, String apkName, String folderName, String downURL )
	{
		this.activity = activity;
		this.apkName = apkName;
		this.folderName = folderName;
		this.downURL = downURL;
	}
	
	
	public void onCreateDialog(int id) {
		switch (id) {
		case DIALOG_YES_MESSAGE:
			new AlertDialog.Builder(activity)
					.setTitle("提示")
					.setMessage(
							"发现新的客户端版本，请进入电子市场下载新版客户端。为避免下载失败，建议您在有Wi-Fi网络时完成升级。")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
								}
							}).create().show();
			break;
		case DIALOG_YES_NO_MESSAGE2:
			new AlertDialog.Builder(activity)
					.setTitle("提示")
					.setMessage("有新版本发布，是否更新？ \n 版本过低可能影响您正常使用！")
					.setPositiveButton("现在更新",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									if (Common.hasSDCard()) {
										Common.showHintDialog(activity, "请稍后...");
										new DownLoadAPK().execute(new String[] {
												folderName, apkName,
												downURL });
									} else {
										Common.showHintDialog(
												activity, "提示",
												"请插入SD卡！");
									}
								}
							})
					.setNegativeButton("不更新",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									dialog.dismiss();
								}
							}).create().show();
			break;
		case DIALOG_YES_NO_MESSAGE:
			new AlertDialog.Builder(activity)

					.setTitle("提示")
					.setMessage("有新版本发布，是否更新？ \n 版本过低可能影响您正常使用！")
					.setPositiveButton("现在更新",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

									if (Common.hasSDCard()) {
										Common.showHintDialog(activity, "请稍后...");
										new DownLoadAPK().execute(new String[] {
												folderName, apkName,
												downURL });
									} else {
										Common.showHintDialog(
												activity, "提示",
												"请插入SD卡！");
									}
								}
							})
					.setNegativeButton("不更新",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
//									if (isMustUpdate) {
										activity.finish();
										try {

											Common.exitAppNow(activity);
										} catch (Exception e) {
											ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
											am.restartPackage(activity.getPackageName());
										}
//									} else {
//									}

								}
							}).create().show();
			break;
		}
	}
	

	// 下载APK
	class DownLoadAPK extends AsyncTask<String, Integer, Boolean> {

		String filePath;
		String name;
		String url;
		Boolean result = true;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(activity);
			progressDialog.setMax(100);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setCancelable(false);// 进度条不能回退
			progressDialog.setTitle("下载更新");// 设置标题
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			progressDialog.setProgress(values[0]);
			super.onProgressUpdate(values);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			filePath = params[0];
			name = params[1];
			url = params[2];
			if (Common.hasSDCard()) {
				String folderName = Environment.getExternalStorageDirectory()
						.getPath() + "/" + filePath;
				String fullPath = folderName + "/" + name;
				File file = new File(fullPath);
				if (file.exists()) {
					file.delete();
				}
				file = new File(folderName);
				if (!file.exists()) {
					file.mkdir();
				}
				try {
					HttpGet httpGet = new HttpGet(url);
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						InputStream is = httpResponse.getEntity().getContent();
						long sizeoffile = httpResponse.getEntity()
								.getContentLength();
						if (sizeoffile < 0) {
							result = false;
						} else {
							FileOutputStream fos = new FileOutputStream(
									fullPath);
							byte[] buffer = new byte[4096];
							int count = 0;
							while ((count = is.read(buffer)) != -1) {
								fos.write(buffer, 0, count);
								downloadedSize += count;
								provalue = (int) (downloadedSize * 100.0 / sizeoffile);
								publishProgress(provalue);
							}
							fos.flush();
							fos.close();
							is.close();
							if (provalue < 100) {
								result = false;
							} else {
								result = true;
							}
						}

					}
				} catch (Exception e) {
					Common.log("downloadFile Exception = " + e.toString());
					result = false;
				}
			} else {
				Common.showHintDialog(activity, "错误信息", "没有SD卡");
				result = false;
			}
			Common.log("result:" + result + "<><><><><>");
			return result;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressDialog.dismiss();
			if (result) {
				Common.cancelLoading();
				String path = "/" + filePath + "/" + name;
				Common.installAPK(activity, path);
			} else {
				Common.cancelLoading();
				Common.showHintDialog(activity, "提示", "更新失败");
			}
		}
	}
	
	
}
