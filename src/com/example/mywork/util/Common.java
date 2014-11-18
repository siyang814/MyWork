package com.example.mywork.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.PatternSyntaxException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.example.mywork.MyApplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;

public class Common {
	public static int WIDTH;
	public static int HEIGHT;
	// 对话框
	private static AlertDialog.Builder dialogBuilder;
	private static Dialog builderDialog;

	public static void toast(String text) {
		Toast t = Toast.makeText(MyApplication.instance.getContext(), text,
				Toast.LENGTH_SHORT);
		// t.setGravity(Gravity.CENTER, 0, 0);
		t.show();
	}

	// 是否有SDCARD
	public static final boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNull(String... str) {
		for (String s : str) {
			if (null == s || "".equals(s)) {
				return true;
			}
		}
		return false;
	}

	// 显示提示信息对话框
	public static void showHintDialog(Context context, String title,
			String content) {
		showHintDialog(context, title, content, false);
	}

	// 显示提示信息对话框
	public static void showHintDialog(Context context, String content) {
		showHintDialog(context, "提示", content, false);
	}

	// 显示提示信息对话框
	public static void showHintDialog(Context context, int id) {
		showHintDialog(context, "提示", getStringById(id), false);
	}

	// 显示提示信息对话框
	public static void showHintDialog(Context context, String content,
			boolean isCancelFinish) {
		showHintDialog(context, "提示", content, isCancelFinish);
	}

	// 取消提示框
	public static void cancelLoading() {
		try {
			if (builderDialog != null) {
				builderDialog.cancel();
				builderDialog = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 显示提示信息对话框
	public static void showHintDialog(final Context context, String title,
			String content, final boolean isCancelFinish) {
		// if (dialogBuilder == null) {
		dialogBuilder = new AlertDialog.Builder(context);
		// }
		dialogBuilder.setTitle(title).setMessage(content)
				.setCancelable(isCancelFinish)
				.setNegativeButton("返回", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builderDialog = dialogBuilder.show();
	}

	// 获取string id对应字符串
	public static final String getStringById(int stringId) {
		return getStringById(MyApplication.instance, stringId);
	}

	// 获取string id对应字符串
	public static final String getStringById(Context context, int stringId) {
		try {
			return context.getResources().getString(stringId);
		} catch (NotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

	// 日志输出
	public static void log(String str) {
		// if (Constant.TEST) {
		Log.w("HCCGT", str);
		// }
	}

	// 日志输出
	public static void logMy(String str) {
		// if (Constant.TEST) {
		Log.w("MYUTIL", str);
		// }
	}

	/**
	 * 测量view宽度
	 * 
	 * @param view
	 * @return
	 */
	public static int getWidth(final View view) {

		int width = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);

		int height = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);

		view.measure(width, height);

		// int height = view.getMeasuredHeight();

		int viewWidth = view.getMeasuredWidth();

		return viewWidth;
		// int width = 0;
		// ViewTreeObserver vto2 = v.getViewTreeObserver();
		// vto2.addOnGlobalLayoutListener( new OnGlobalLayoutListener() {
		//
		// @Override
		// public void onGlobalLayout() {
		// // TODO Auto-generated method stub
		// view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
		//
		//
		// int height =view.getMeasuredHeight();
		//
		// int width =view.getMeasuredWidth();
		//
		// }
		// });
	}

	/**
	 * 测量view高度
	 * 
	 * @param view
	 * @return
	 */
	public static int getHeight(final View view) {

		int width = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);

		int height = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);

		view.measure(width, height);

		int viewHeight = view.getMeasuredHeight();

		return viewHeight;
	}

	private static PopupWindow pop;

	// /**
	// * 搜索里的PopWindow, 需要在界面关闭的时候， 调用dismiss
	// */
	// public static PopupWindow showPopWindowInSearch(View parent,
	// final OnSuccessListener listener) {
	// if (pop != null && pop.isShowing()) {
	// pop.dismiss();
	// return pop;
	// }
	// View view = View.inflate(MyApplication.instance,
	// R.layout.popwindow_in_search, null);
	// Button btn_shop = (Button) view.findViewById(R.id.btn_shop);
	// Button btn_test = (Button) view.findViewById(R.id.btn_test);
	// OnClickListener click = new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// listener.onSuccess(null, 0, ((Button) v).getText().toString());
	// pop.dismiss();
	// }
	// };
	// btn_shop.setOnClickListener(click);
	// btn_test.setOnClickListener(click);
	// pop = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
	// LayoutParams.WRAP_CONTENT);
	// pop.setOutsideTouchable(true);
	// pop.setBackgroundDrawable(new BitmapDrawable());
	// pop.setFocusable(true);
	// pop.showAsDropDown(parent);
	//
	// // WindowManager.LayoutParams lp =
	// MyApplication.instance.getWindow().getAttributes();
	// // lp.alpha = 0.5f;
	// // MyApplication.instance.getWindow().setAttributes(lp);
	//
	// return pop;
	// }

	private static PopupWindow titlepop;

	private static PopupWindow popmenu;

	/**
	 * 获取数组是否已有此数据
	 * 
	 * @param str
	 * @param add
	 * @return
	 */
	public static boolean ListHave(String[] str, String add) {
		if (str == null)
			return false;
		for (int i = 0; i < str.length; i++) {
			if (add.equals(str[i])) {
				return true;
			}
		}
		return false;
	}

	// /**
	// * 没有继承ActivityBase的设置title方法
	// *
	// * @param a
	// * @param str
	// */
	// public static void setTitle(final Activity a, String str) {
	// TextView backbtn = (TextView) a.findViewById(R.id.backbtn);
	// TextView titlename = (TextView) a.findViewById(R.id.titlename);
	// if (titlename != null) {
	// titlename.setText(str == null ? "" : str);
	// }
	// if (backbtn != null) {
	// backbtn.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// a.finish();
	// }
	// });
	// }
	// }
	/**
	 * 点击EditText 跳转界面
	 * 
	 * @param a
	 * @param edit
	 * @param c
	 */
	public static void setSearchEdit(final Activity a, final EditText edit,
			final Class<?> c) {

		edit.setInputType(InputType.TYPE_NULL);

		edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 点击跳转
				// Common.log("点击跳转");
				// a.startActivityForResult(new Intent(a,
				// ActivitySearchMain.class), ActivityBase.REQUEST_CODE);
				Intent intent = new Intent(a, c);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("searchName", edit.getText().toString());
				a.startActivity(intent);
			}
		});
	}

	/**
	 * String 2 int
	 * 
	 * @param str
	 * @return
	 */
	public static int String2Int(String str) {
		int num = 0;
		try {
			if (!Common.isNull(str)) {
				num = Integer.parseInt(str);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return num;
	}

	/**
	 * 根据毫秒转换对应日期
	 * 
	 * @param millis
	 * @return
	 */
	public static String Millis2Date(String millis) {
		DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(Long.parseLong(millis));
		return date.format(c.getTime());
	}

	/**
	 * 拨打电话
	 * 
	 * @param a
	 * @param phoneNum
	 * @return
	 */
	public static boolean Tel(Activity a, String phoneNum, String error) {
		if (Common.isNull(phoneNum)) {
			Common.toast(error);
			return false;
		}
		Uri uri = Uri.parse("tel:" + phoneNum);
		Intent intent = new Intent(Intent.ACTION_CALL, uri);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		a.startActivity(intent);
		return true;
	}

	private static Boolean isExit = false;
	private static Boolean hasTask = false;

	public static void exitApp(Activity a) {

		Timer tExit = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				isExit = false;
				hasTask = true;
			}
		};

		if (isExit == false) {
			isExit = true;
			toast("再按一次退出");
			if (!hasTask) {
				tExit.schedule(task, 3000);
			}
		} else {
			MyApplication.instance.cancel();
			exitAppNow(a);
		}
	}
	/**
	 * 		立刻退出
	 * @param a
	 */
	public static void exitAppNow (Activity a )
	{
		Intent startMain = new Intent(
				Intent.ACTION_MAIN);
		startMain
				.addCategory(Intent.CATEGORY_HOME);
		startMain
				.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		a.startActivity(startMain);
		a.finish();
		System.exit(0);
	}

	/**
	 * UTF-8 转 GBK
	 * 
	 * @return
	 */
	public static String UTF82GBK(String url) {
		try {
			return new String(url.getBytes("UTF-8"), "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return url;
		}
	}

	/**
	 * dip 转px
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * px转dip
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 初始数据加载
	 * 
	 * @param c
	 */
	public static void setWidthAndHeight(Context c) {
		// 获取屏幕宽高
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) c).getWindowManager().getDefaultDisplay().getMetrics(dm);
		Common.WIDTH = dm.widthPixels;
		Common.HEIGHT = dm.heightPixels;
	}

	/**
	 * 详情用到
	 * 
	 * @param str
	 * @return
	 */
	public static double String2Double(String str) {
		double d = -1;
		try {
			d = Double.parseDouble(str);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return d;
	}

	// 判断是否存在特殊字符
	public static final boolean isExistSpecial(String str) {
		if (Common.isNull(str)) {
			return false;
		}
		try {
			return (str.contains(">") || str.contains("<") || str.contains("&")
					|| str.contains("\"") || str.contains("'") || str
						.contains(":"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 短信验证码计时
	@SuppressLint("ResourceAsColor")
	public static void setSmsButton(final Button btn) {
		if (!btn.isEnabled())
			return;
		btn.setEnabled(false);
		// btn.setBackgroundColor(R.color.gray);
		final int time = 59;
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = time; i >= 0; i--) {
					final int temp = i;

					btn.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							if (temp == 0) {
								// btn.setBackgroundResource(R.drawable.bindbtn);
								btn.setEnabled(true);
								btn.setText("获取验证码");
							} else {
								btn.setText("重新发送(" + temp + ")");
							}
						}
					});

					try {
						Thread.sleep(988);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}).start();

	}

	// 设置全屏
	public static final void setFullScreen(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	// 设置禁止屏幕休眠
	public static final void setWakeLuck(Activity activity) {
		activity.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	/**
	 * 获取运行内存
	 * 
	 * @return
	 */
	public static long getMemory() {
		return Runtime.getRuntime().maxMemory();
	}

	private static String SDURL;

	public static String getSdCardPath(Context context) {
		String path = "";
		StorageManager sm = (StorageManager) context
				.getSystemService(Context.STORAGE_SERVICE);
		// 获取sdcard的路径：外置和内置
		String[] paths;
		try {
			// 第一种 0是内部储存， 后面是外部储存
			paths = (String[]) sm.getClass().getMethod("getVolumePaths", null)
					.invoke(sm, null);
			// String temp = getSdCardPathAPI();
			for (int i = 0; i < paths.length; i++) {
				log(paths[i]);
			}
			if (paths.length > 1 && writeSD(paths[1])) {
				path = paths[1];
			} else // if (paths.length == 1 )
			{
				path = paths[0];
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log(e.getMessage());
		}
		SDURL = path;
		return path;
	}

	// 系统PAI获取SD卡路径
	public static String getSdCardPathAPI() {
		return Environment.getExternalStorageDirectory().toString();
	}

	// 判断路径是否有效
	public static boolean writeSD(String path) {
		boolean b = false;

		File f = new File(path, "ff");
		if (f.mkdirs()) {
			b = true;
			f.delete();
		}
		return b;
	}

	// 安装APK
	public static final void installAPK(Activity activity, String fileURL) {
		try {
			String path = Environment.getExternalStorageDirectory().getPath()
					+ fileURL;
			Common.log("apk path = " + path);
			File file = new File(path);
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(Intent.ACTION_VIEW);
			String type = "application/vnd.android.package-archive";
			intent.setDataAndType(Uri.fromFile(file), type);
			activity.startActivity(intent);
		} catch (Exception e) {
			Common.log("installAPK Exception = " + e.toString());
		}
	}

	/**
	 * 获取签名信息
	 * 
	 * @param c
	 */
	public static void getSingInfo(Context c) {
		try {
			PackageInfo packageInfo = c.getPackageManager().getPackageInfo(
					c.getPackageName(), PackageManager.GET_SIGNATURES);
			Signature[] signs = packageInfo.signatures;
			Signature sign = signs[0];
			parseSignature(sign.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void parseSignature(byte[] signature) {
		try {
			CertificateFactory certFactory = CertificateFactory
					.getInstance("X.509");
			X509Certificate cert = (X509Certificate) certFactory
					.generateCertificate(new ByteArrayInputStream(signature));
			String pubKey = cert.getPublicKey().toString();
			String signNumber = cert.getSerialNumber().toString();
			System.out.println("signName:" + cert.getSigAlgName());
			System.out.println("pubKey:" + pubKey);
			System.out.println("signNumber:" + signNumber);
			System.out.println("subjectDN:" + cert.getSubjectDN().toString());
		} catch (CertificateException e) {
			e.printStackTrace();
		}
	}

}
