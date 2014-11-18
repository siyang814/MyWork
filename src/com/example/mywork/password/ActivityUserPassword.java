package com.example.mywork.password;

import java.util.Random;

import com.example.mywork.ActivityBase;
import com.example.mywork.R;
import com.example.mywork.util.Common;


import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;


public class ActivityUserPassword extends ActivityBase {

	public static boolean isShowing = false;

	public static EditText edit;
	//输入密码长度
	private static int cursorNum = 12;

	int[] seed = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	// 符号按钮
	int[] btn_FuHao = { R.id.fuhao_0, R.id.fuhao_1, R.id.fuhao_2, R.id.fuhao_3,
			R.id.fuhao_4, R.id.fuhao_5, R.id.fuhao_6, R.id.fuhao_7,
			R.id.fuhao_8, R.id.fuhao_9, R.id.fuhao_10, R.id.fuhao_12,
			R.id.fuhao_13, R.id.fuhao_14, R.id.fuhao_16, R.id.fuhao_18,
			R.id.fuhao_19, R.id.fuhao_23 };

	// 符号按钮1
	int[] btn_FuHao1 = { R.id.fuhao1_0, R.id.fuhao1_1, R.id.fuhao1_2,
			R.id.fuhao1_3, R.id.fuhao1_4, R.id.fuhao1_5, R.id.fuhao1_6,
			R.id.fuhao1_7, R.id.fuhao1_8, R.id.fuhao1_9, R.id.fuhao1_10,
			R.id.fuhao1_11, R.id.fuhao1_12, R.id.fuhao1_13, R.id.fuhao1_14,
			R.id.fuhao1_15, R.id.fuhao1_19, R.id.fuhao1_23 };

	// ABC
	int[] btn_Code = { R.id.btn_q, R.id.btn_w, R.id.btn_e, R.id.btn_r,
			R.id.btn_t, R.id.btn_y, R.id.btn_u, R.id.btn_i, R.id.btn_o,
			R.id.btn_p, R.id.btn_a, R.id.btn_s, R.id.btn_d, R.id.btn_f,
			R.id.btn_g, R.id.btn_h, R.id.btn_j, R.id.btn_k, R.id.btn_l,
			R.id.btn_z, R.id.btn_x, R.id.btn_c, R.id.btn_v, R.id.btn_b,
			R.id.btn_n, R.id.btn_m, R.id.btn_enter };

	ImageButton btnDelete;
	ImageButton fuhao11;
	ImageButton fuhao21;
	ImageButton btnShift;

	int[] numberRandom;

	public int[] buttonNames = { R.id.Button1, R.id.Button2, R.id.Button3,
			R.id.Button4, R.id.Button5, R.id.Button6, R.id.Button7,
			R.id.Button8, R.id.Button9, R.id.Button10 };
	// 大写
	String[] KEY_ABC = new String[] { "Q", "W", "E", "R", "T", "Y", "U", "I",
			"O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X",
			"C", "V", "B", "N", "M" };
	// //小写
	// String[] KEY_abc = new String[]{"Q", "W","E", "R","T", "Y","U", "I","O",
	// "P","A", "S","D", "F","G", "H","J", "K","L", "Z","X", "C","V", "B","N",
	// "M" };
	// 大写字母
	boolean UPPERCASE = false;
	boolean isUPFlag = false;

	Button[] buttonNum, btn_Abc, btn_Fuhao, btn_Fuhao1;

	ImageButton buttonC;

	Button buttonOK;

	EditText editPassword;

	LinearLayout fuhao, abc, LayoutButton, currentLayout, fuhao1;

	RelativeLayout relative_main;

	Button text_num, text_code, text_fuhao;

	public static ActivityUserPassword instance;

	// 安全输入提示
	public LinearLayout safeLayout;

	LinearLayout linearMain;

	Button btnEnter;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		isShowing = false;
		btn_Abc = null;
		btn_Fuhao = null;
		buttonNum = null;
	}

	public boolean isShrink = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Common.setWakeLuck(this);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		if (Common.WIDTH <= 240) {
			isShrink = true;
		}
		if (isShrink) {
			setContentView(R.layout.user_password_min);
		} else {
			setContentView(R.layout.user_password);
		}

		editPassword = (EditText) findViewById(R.id.EditPassword);
		if (edit.getTag() != null && !Common.isNull(edit.getText().toString()) )
			realPass = (String) edit.getTag();

		// Intent intent = this.getIntent();
		// Bundle bundle = intent.getExtras();
		// String password = bundle.getString("password");
		// realPass=password;
		// editPassword.setText(getMac(realPass.length()));

		buttonC = (ImageButton) findViewById(R.id.ButtonC);
		buttonC.setOnClickListener(listenerC);

		buttonOK = (Button) findViewById(R.id.ButtonOK);
		buttonOK.setOnClickListener(listenerOK);

		btnDelete = (ImageButton) findViewById(R.id.btn_delete);
		btnDelete.setOnClickListener(listenerC);

		fuhao11 = (ImageButton) findViewById(R.id.fuhao_11);
		fuhao11.setOnClickListener(listenerC);

		fuhao21 = (ImageButton) findViewById(R.id.fuhao2_11);
		fuhao21.setOnClickListener(listenerC);

		// buttonC.setBackgroundResource(R.drawable.btn_text_background_normal);
		// buttonOK.setBackgroundResource(R.drawable.btn_text_background_normal);
		relative_main = (RelativeLayout) findViewById(R.id.relative_main);

		linearMain = (LinearLayout) findViewById(R.id.linear_main);
		linearMain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isShowing = false;
				ActivityUserPassword.this.finish();
			}
		});

		init();
		initButtonNum(isShrink);

		safeLayout = (LinearLayout) findViewById(R.id.safe_layout);
		safeLayout.setVisibility(View.VISIBLE);

		btnShift = (ImageButton) findViewById(R.id.btn_shift);
		btnShift.setOnClickListener(listener_Btn_Shift);
		// initOverlay();
		// 初始化数字
		text_num.setBackgroundResource(R.drawable.pass_more);
		text_code.setBackgroundResource(R.drawable.pass_number);
		text_fuhao.setBackgroundResource(R.drawable.pass_number);
		text_fuhao.setPadding(0, 0, 0, 20);
		text_code.setPadding(0, 0, 0, 20);
		text_num.setPadding(0, 0, 0, 20);
	}

	String realPass = "";

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		instance = this;
		// ActivityLogo.currentActivity = this;

	}

	private void init() {
		text_code = (Button) findViewById(R.id.text_code);
		text_fuhao = (Button) findViewById(R.id.text_fuhao);
		text_num = (Button) findViewById(R.id.text_num);

		text_code.setOnClickListener(listenerView);
		text_fuhao.setOnClickListener(listenerView);
		text_num.setOnClickListener(listenerView);

		fuhao = (LinearLayout) findViewById(R.id.fuhao);
		fuhao1 = (LinearLayout) findViewById(R.id.fuhao1);
		abc = (LinearLayout) findViewById(R.id.abc);

		btnEnter = (Button) findViewById(R.id.btn_enter);

		LayoutButton = (LinearLayout) findViewById(R.id.LayoutButton);

		currentLayout = LayoutButton;
	}

	final void initButtonNum(boolean isShrink) {
		int len = seed.length;
		numberRandom = new int[len];
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			// 得到一个位置
			int r = random.nextInt(len - i);
			// 得到那个位置的数值
			numberRandom[i] = seed[r];
			// 将最后一个未用的数字放到这里
			seed[r] = seed[len - 1 - i];
		}

		buttonNum = new Button[buttonNames.length];
		for (int i = 0; i < buttonNames.length; i++) {
			buttonNum[i] = (Button) findViewById(buttonNames[i]);
			buttonNum[i].setText(String.valueOf(numberRandom[i]));
			buttonNum[i].setOnClickListener(listenerNumber);
			// buttonNum[i].setOnTouchListener(listenerTouchNum);
			// if(isShrink){
			// buttonNum[i].setPadding(0, 0, 0, 20);
			// }
		}

		btn_Abc = new Button[btn_Code.length];
		for (int i = 0; i < btn_Abc.length; i++) {
			btn_Abc[i] = (Button) findViewById(btn_Code[i]);
			if (btn_Code[i] == R.id.btn_delete) {
				btn_Abc[i].setOnClickListener(listenerC);
				continue;
			} else if (btn_Code[i] == R.id.btn_enter) {
				btn_Abc[i].setOnClickListener(listenerOK);
				continue;
			}
			// else if (btn_Code[i] == R.id.btn_shift) {
			// btn_Abc[i].setOnClickListener(listener_Btn_Shift);
			// continue;
			// }
			// if(isShrink){
			// btn_Abc[i].setPadding(0, 0, 0, 20);
			// }
			btn_Abc[i].setOnClickListener(listenerNumber);
		}

		btn_Fuhao = new Button[btn_FuHao.length];

		for (int i = 0; i < btn_Fuhao.length; i++) {
			btn_Fuhao[i] = (Button) findViewById(btn_FuHao[i]);
			// if (btn_FuHao[i] == R.id.fuhao_11) {
			// btn_Fuhao[i].setOnClickListener(listenerC);
			// continue;
			// }
			if (btn_FuHao[i] == R.id.fuhao_19) {
				btn_Fuhao[i].setOnClickListener(listenerOK);
				continue;
			} else if (btn_FuHao[i] == R.id.fuhao_23) // 切换符号
			{
				btn_Fuhao[i].setOnClickListener(listenerView);
				continue;
			}
			// if(isShrink){
			// btn_Fuhao[i].setPadding(0, 0, 0, 20);
			// }
			btn_Fuhao[i].setOnClickListener(listenerNumber);
		}

		btn_Fuhao1 = new Button[btn_FuHao1.length];

		for (int i = 0; i < btn_Fuhao1.length; i++) {
			btn_Fuhao1[i] = (Button) findViewById(btn_FuHao1[i]);
			// if (btn_FuHao1[i] == R.id.fuhao2_11) {
			// btn_Fuhao1[i].setOnClickListener(listenerC);
			// continue;
			// }
			if (btn_FuHao1[i] == R.id.fuhao1_19) {
				btn_Fuhao1[i].setOnClickListener(listenerOK);
				continue;
			} else if (btn_FuHao1[i] == R.id.fuhao1_23) // 切换符号
			{
				btn_Fuhao1[i].setOnClickListener(listenerView);
				continue;
			}
			// if(isShrink){
			// btn_Fuhao1[i].setPadding(0, 0, 0, 20);
			// }
			btn_Fuhao1[i].setOnClickListener(listenerNumber);
		}

	}

	public final void sendResult() {
		Intent i = new Intent();
		Bundle b = new Bundle();
		b.putString("password", realPass);
		// Common.log("Pass : "+realPass);
		i.putExtras(b);
		this.setResult(RESULT_OK, i);
		this.finish();
	}

	/**
	 * 布局切换
	 */
	OnClickListener listenerView = new OnClickListener() {

		@Override
		public void onClick(View v) {

			safeLayout.setVisibility(View.GONE);

			// TODO Auto-generated method stub
			if (v.equals(text_fuhao) && !currentLayout.equals(fuhao)) {
				// if (v.equals(text_fuhao)) {
				currentLayout.setVisibility(View.GONE);
				currentLayout = fuhao;
				currentLayout.setVisibility(View.VISIBLE);
				text_fuhao.setBackgroundResource(R.drawable.pass_more);
				text_code.setBackgroundResource(R.drawable.pass_number);
				text_num.setBackgroundResource(R.drawable.pass_number);
				btnEnter.setVisibility(View.VISIBLE);
				btnEnter.setVisibility(View.GONE);
			} else if (v.equals(text_code) && !currentLayout.equals(abc)) {
				// else if (v.equals(text_code)) {
				currentLayout.setVisibility(View.GONE);
				currentLayout = abc;
				currentLayout.setVisibility(View.VISIBLE);
				text_code.setBackgroundResource(R.drawable.pass_more);
				text_fuhao.setBackgroundResource(R.drawable.pass_number);
				text_num.setBackgroundResource(R.drawable.pass_number);
				btnEnter.setVisibility(View.VISIBLE);
			} else if (v.equals(text_num)
					&& !currentLayout.equals(LayoutButton)) {
				// else if (v.equals(text_num)) {
				currentLayout.setVisibility(View.GONE);
				currentLayout = LayoutButton;
				currentLayout.setVisibility(View.VISIBLE);
				text_num.setBackgroundResource(R.drawable.pass_more);
				text_code.setBackgroundResource(R.drawable.pass_number);
				text_fuhao.setBackgroundResource(R.drawable.pass_number);
				btnEnter.setVisibility(View.GONE);
			} else if (R.id.fuhao_23 == v.getId()
					&& !currentLayout.equals(fuhao1)) {
				// else if (R.id.fuhao_23 == v.getId()) {
				currentLayout.setVisibility(View.GONE);
				currentLayout = fuhao1;
				currentLayout.setVisibility(View.VISIBLE);
			} else if (R.id.fuhao1_23 == v.getId()
					&& !currentLayout.equals(fuhao)) {
				// else if (R.id.fuhao1_23 == v.getId()) {
				currentLayout.setVisibility(View.GONE);
				currentLayout = fuhao;
				currentLayout.setVisibility(View.VISIBLE);
			}

			text_fuhao.setPadding(0, 0, 0, 20);
			text_code.setPadding(0, 0, 0, 20);
			text_num.setPadding(0, 0, 0, 20);
			btnEnter.setPadding(0, 0, 0, 20);
		}
	};

	OnClickListener listener_Btn_Shift = new OnClickListener() {

		@Override
		public void onClick(View v) {

			safeLayout.setVisibility(View.GONE);

			UPPERCASE = !UPPERCASE;
			isUPFlag = !isUPFlag;

			if (isUPFlag) {
				btnShift.setImageResource(R.drawable.pass_shift2);
			} else {
				btnShift.setImageResource(R.drawable.pass_shift1);
			}

			if (UPPERCASE) {
				for (int i = 0; i < KEY_ABC.length; i++) {
					btn_Abc[i].setText(KEY_ABC[i]);
				}
			} else {
				for (int i = 0; i < KEY_ABC.length; i++) {
					btn_Abc[i].setText(KEY_ABC[i].toLowerCase());
				}
			}
		}
	};

	public TextView overlay;
	public WindowManager windowManager;
	private Handler handler;
	private OverlayThread overlayThread;

	WindowManager.LayoutParams lp;

	// 初始化字母弹出提示框
	private void initOverlay() {

		handler = new Handler();
		overlayThread = new OverlayThread();

		final LayoutInflater inflater = LayoutInflater.from(this);
		overlay = (TextView) inflater.inflate(R.layout.pass_overlay, null);
		overlay.setVisibility(View.INVISIBLE);
		lp = new WindowManager.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay, lp);
	}

	/**
	 * 设置overlay不可见
	 */
	public class OverlayThread implements Runnable {

		public void run() {
			overlay.setVisibility(View.GONE);
		}
	}

	OnTouchListener listenerTouchNum = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// int x=(int) event.getRawX();
			// int y=(int) event.getRawY();
			int x = (int) event.getX();
			int y = (int) event.getY() + 200;
			lp.x = x;
			lp.y = y;
			windowManager.updateViewLayout(overlay, lp);
			return false;
		}
	};

	OnClickListener listenerNumber = new OnClickListener() {

		@Override
		public void onClick(View v) {

			safeLayout.setVisibility(View.GONE);
			Button button = (Button) v;
			 if(realPass.length()<cursorNum){
			editPassword.append("*");
			// Common.showToast(ActivityUserPassword.this,
			// button.getText().toString(), false);

			// overlay.setText(button.getText().toString());
			// overlay.setVisibility(View.VISIBLE);
			// handler.removeCallbacks(overlayThread);
			// // 延迟一秒后执行，让overlay为不可见
			// handler.postDelayed(overlayThread, 1000);

			realPass += button.getText().toString();
			if (edit != null) {
				// edit.append(button.getText().toString());
				edit.append("*");
				edit.setTag(realPass);
				seteditPassword(realPass);
			}
			 }

		}
	};
//删除
	OnClickListener listenerC = new OnClickListener() {

		@Override
		public void onClick(View v) {

			safeLayout.setVisibility(View.GONE);
			// Editable text = editPassword.getText();
			StringBuffer sb = new StringBuffer(realPass);
			int st = sb.length();
			if (st < 1)
				return;
			sb = sb.delete(st - 1, st);
			realPass = sb.toString();
			editPassword.setText(getMac(realPass.length()));
			if (edit != null) {
				// edit.setText(realPass);
				edit.setText(getMac(realPass.length()));
				edit.setTag(realPass);
				seteditPassword(realPass);
			}
		}
	};

	public String getMac(int num) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < num; i++) {
			sb.append("*");
		}
		return sb.toString();
	}

	OnClickListener listenerOK = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String input = editPassword.getText().toString();
			sendResult();
			isShowing = false;
			// if(Common.isNullOrEmpty(input) || input.length()<6){
			// Common.showHintDialog(ActivityRandomPassword.this, "提示",
			// "请输入六位数字的密码！");
			// }else {
			// sendResult();
			// isShowing=false;
			// }
		}
	};

	/**
	 * 设置光标
	 * 
	 * @param str
	 */
	public static void seteditPassword(String str) {
		int length = str.length();
		if (length < cursorNum) {
			edit.setSelection(length);
		} else {
			edit.setSelection(cursorNum);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		// 按下的如果是BACK，同时没有重复
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			ActivityUserPassword.this.finish();
			isShowing = false;
			return true;
		} else {
			isShowing = false;
			return super.onKeyUp(keyCode, event);
		}
	}
}
