package com.vandong.gamebatchu02082017;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nvd.database.ManagerSqlite;
import com.nvd.database.UploadImage;
import com.nvd.objcauhoi.Cauhoi;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;

public class GAME extends Activity implements OnClickListener {
	// g�?i lớp
	private ManagerSqlite managersqllite;

	private int size_ChuDaChon;

	private Cauhoi CAUHOI;
	// khai bao da tien trinh
	private Handler handler;
	private Runmusic runmusics;

	//
	private ArrayList<String> dsOChuDapAn;
	public static int level;
	public static int diem;
	public TextView btn_numberlevel;
	public TextView btn_diem;
	private Typeface face;
	private ImageView home;
	private ImageView ic_audio_off;
	private ImageView ic_audio_on;
	private SharedPreferences pre;
	//
	public ImageView img_cauhoi;
	private ImageView icon_help;
	private ImageView icon_swap;
	private ImageView ic_people;
	private ImageView ic_key;
	//
	public Button btn_okq_1;
	public Button btn_okq_2;
	public Button btn_okq_3;
	public Button btn_okq_4;
	public Button btn_okq_5;
	public Button btn_okq_6;
	public Button btn_okq_7;
	public Button btn_okq_8;
	public Button btn_okq_9;
	public Button btn_okq_10;
	public Button btn_okq_11;
	public Button btn_okq_12;
	public Button btn_okq_13;
	public Button btn_okq_14;
	//
	public Button btn_ogy_1;
	public Button btn_ogy_2;
	public Button btn_ogy_3;
	public Button btn_ogy_4;
	public Button btn_ogy_5;
	public Button btn_ogy_6;
	public Button btn_ogy_7;
	public Button btn_ogy_8;
	public Button btn_ogy_9;
	public Button btn_ogy_10;
	public Button btn_ogy_11;
	public Button btn_ogy_12;
	public Button btn_ogy_13;
	public Button btn_ogy_14;
	//
	private Dialog dialog;
	private Button btn_oke_dialog_help;

	private int number_help;
	public MediaPlayer nhacnen;
	public MediaPlayer nhacchienthang;
	// quang cáo
	AdView adview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		// Quảng cáo
		adview = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice("754DB6521943676637AE86202C5ACE52").build();
		adview.loadAd(adRequest);
		// khai báo lớp manager
		// Khai báo nhạc n�?n
		new MediaPlayer();
		nhacnen = MediaPlayer.create(this, R.raw.nhactronggame);
		// vào hàm ánh xạ
		AnhXa();

		if (pre.getBoolean("welcome", true)) {
			SharedPreferences.Editor edit = pre.edit();
			edit.putBoolean("welcome", false);
			showwelcome("wecom");
			edit.commit();
		}

		// bắt đầu game với câu đang chơi
		// GetLevel(level);

		/*
		 * sự kiện trên thạnh công cụng trên cùng home , âm thanh , trợ giúp
		 */
		icon_help.setOnClickListener(this);
		home.setOnClickListener(this);
		ic_audio_off.setOnClickListener(this);
		ic_audio_on.setOnClickListener(this);
		icon_swap.setOnClickListener(this);
		ic_key.setOnClickListener(this);

		// bắt sự kiện
		ic_people.setOnClickListener(this);
		btn_ogy_1.setOnClickListener(this);
		btn_ogy_2.setOnClickListener(this);
		btn_ogy_3.setOnClickListener(this);
		btn_ogy_4.setOnClickListener(this);
		btn_ogy_5.setOnClickListener(this);
		btn_ogy_6.setOnClickListener(this);
		btn_ogy_7.setOnClickListener(this);
		btn_ogy_8.setOnClickListener(this);
		btn_ogy_9.setOnClickListener(this);
		btn_ogy_10.setOnClickListener(this);
		btn_ogy_11.setOnClickListener(this);
		btn_ogy_12.setOnClickListener(this);
		btn_ogy_13.setOnClickListener(this);
		btn_ogy_14.setOnClickListener(this);
		//
		btn_okq_1.setOnClickListener(this);
		btn_okq_2.setOnClickListener(this);
		btn_okq_3.setOnClickListener(this);
		btn_okq_4.setOnClickListener(this);
		btn_okq_5.setOnClickListener(this);
		btn_okq_6.setOnClickListener(this);
		btn_okq_7.setOnClickListener(this);
		btn_okq_8.setOnClickListener(this);
		btn_okq_9.setOnClickListener(this);
		btn_okq_10.setOnClickListener(this);
		btn_okq_11.setOnClickListener(this);
		btn_okq_12.setOnClickListener(this);
		btn_okq_13.setOnClickListener(this);
		btn_okq_14.setOnClickListener(this);
		//
		registerForContextMenu(icon_help);
		showgame(level);
	}

	// lop tao da tien trinh chay nha khi ket thuc
	public class Runmusic implements Runnable {

		@Override
		public void run() {
			nhacnen.start();
			handler.postDelayed(runmusics, 15000);
		}

	}

	private void showwelcome(String type) {
		final Dialog dl;
		dl = new Dialog(GAME.this, R.style.My_Dialog_Theme);
		dl.setContentView(R.layout.gioithieu);
		dl.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dl.setCanceledOnTouchOutside(false);
		dl.setCancelable(false);
		Button btndong = (Button) dl.findViewById(R.id.btn_dong_dialog_welcome);
//		TextView txtview = dl.findViewById(R.id.tx)
		dl.show();
		btndong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dl.dismiss();

			}
		});

	}

	@Override
	protected void onPause() {
		super.onPause();
		pre = getSharedPreferences("mydata", MODE_PRIVATE);
		SharedPreferences.Editor edit = pre.edit();
		edit.putInt("numberlevel", level);
		edit.putInt("numberDiem", diem);
		edit.putInt("num_help", number_help);
		edit.commit();
	}

	@Override
	protected void onStop() {

		super.onStop();
		pre = getSharedPreferences("mydata", MODE_PRIVATE);
		SharedPreferences.Editor edit = pre.edit();
		edit.putInt("numberlevel", level);
		edit.putInt("numberDiem", diem);
		edit.putInt("num_help", number_help);
		edit.commit();
	}

	/*
	 * 
	 */
	public boolean AnhXa() {
		// lớp runmusic
		runmusics = new Runmusic();
		handler = new Handler();
		// ẢNH
		pre = getSharedPreferences("mydata", MODE_PRIVATE);
		//
		img_cauhoi = (ImageView) findViewById(R.id.img_cauhoi);
		ic_audio_off = (ImageView) findViewById(R.id.icon_audio_off);
		ic_audio_on = (ImageView) findViewById(R.id.icon_audio_on);
		ic_people = (ImageView) findViewById(R.id.ic_people);
		ic_key = (ImageView) findViewById(R.id.ic_key);
		//
		btn_numberlevel = (TextView) findViewById(R.id.btn_level);
		btn_diem = (TextView) findViewById(R.id.btn_diem);
		// Himh Ảnh
		icon_help = (ImageView) findViewById(R.id.icon_help);
		home = (ImageView) findViewById(R.id.icon_home);
		icon_swap = (ImageView) findViewById(R.id.ic_swap);
		//

		// Ô CHỮ
		btn_okq_1 = (Button) findViewById(R.id.btn_okq_1);
		btn_okq_2 = (Button) findViewById(R.id.btn_okq_2);
		btn_okq_3 = (Button) findViewById(R.id.btn_okq_3);
		btn_okq_4 = (Button) findViewById(R.id.btn_okq_4);
		btn_okq_5 = (Button) findViewById(R.id.btn_okq_5);
		btn_okq_6 = (Button) findViewById(R.id.btn_okq_6);
		btn_okq_7 = (Button) findViewById(R.id.btn_okq_7);
		btn_okq_8 = (Button) findViewById(R.id.btn_okq_8);
		btn_okq_9 = (Button) findViewById(R.id.btn_okq_9);
		btn_okq_10 = (Button) findViewById(R.id.btn_okq_10);
		btn_okq_11 = (Button) findViewById(R.id.btn_okq_11);
		btn_okq_12 = (Button) findViewById(R.id.btn_okq_12);
		btn_okq_13 = (Button) findViewById(R.id.btn_okq_13);
		btn_okq_14 = (Button) findViewById(R.id.btn_okq_14);

		btn_ogy_1 = (Button) findViewById(R.id.btn_ogy_1);
		btn_ogy_2 = (Button) findViewById(R.id.btn_ogy_2);
		btn_ogy_3 = (Button) findViewById(R.id.btn_ogy_3);
		btn_ogy_4 = (Button) findViewById(R.id.btn_ogy_4);
		btn_ogy_5 = (Button) findViewById(R.id.btn_ogy_5);
		btn_ogy_6 = (Button) findViewById(R.id.btn_ogy_6);
		btn_ogy_7 = (Button) findViewById(R.id.btn_ogy_7);
		btn_ogy_8 = (Button) findViewById(R.id.btn_ogy_8);
		btn_ogy_9 = (Button) findViewById(R.id.btn_ogy_9);
		btn_ogy_10 = (Button) findViewById(R.id.btn_ogy_10);
		btn_ogy_11 = (Button) findViewById(R.id.btn_ogy_11);
		btn_ogy_12 = (Button) findViewById(R.id.btn_ogy_12);
		btn_ogy_13 = (Button) findViewById(R.id.btn_ogy_13);
		btn_ogy_14 = (Button) findViewById(R.id.btn_ogy_14);
		//
		// tạo forn chữ
		face = Typeface.createFromAsset(getAssets(), "font/ARIBLK.TTF");
		//
		btn_ogy_1.setTypeface(face);
		btn_ogy_2.setTypeface(face);
		btn_ogy_3.setTypeface(face);
		btn_ogy_4.setTypeface(face);
		btn_ogy_5.setTypeface(face);
		btn_ogy_6.setTypeface(face);
		btn_ogy_7.setTypeface(face);
		btn_ogy_8.setTypeface(face);
		btn_ogy_9.setTypeface(face);
		btn_ogy_10.setTypeface(face);
		btn_ogy_11.setTypeface(face);
		btn_ogy_12.setTypeface(face);
		btn_ogy_13.setTypeface(face);
		btn_ogy_14.setTypeface(face);
		//
		btn_okq_1.setTextColor(Color.parseColor("#009900"));
		btn_okq_2.setTextColor(Color.parseColor("#009900"));
		btn_okq_3.setTextColor(Color.parseColor("#009900"));
		btn_okq_4.setTextColor(Color.parseColor("#009900"));
		btn_okq_5.setTextColor(Color.parseColor("#009900"));
		btn_okq_6.setTextColor(Color.parseColor("#009900"));
		btn_okq_7.setTextColor(Color.parseColor("#009900"));
		btn_okq_8.setTextColor(Color.parseColor("#009900"));
		btn_okq_9.setTextColor(Color.parseColor("#009900"));
		btn_okq_10.setTextColor(Color.parseColor("#009900"));
		btn_okq_11.setTextColor(Color.parseColor("#009900"));
		btn_okq_12.setTextColor(Color.parseColor("#009900"));
		btn_okq_13.setTextColor(Color.parseColor("#009900"));
		btn_okq_14.setTextColor(Color.parseColor("#009900"));
		//
		btn_okq_1.setTypeface(face);
		btn_okq_2.setTypeface(face);
		btn_okq_3.setTypeface(face);
		btn_okq_4.setTypeface(face);
		btn_okq_5.setTypeface(face);
		btn_okq_6.setTypeface(face);
		btn_okq_7.setTypeface(face);
		btn_okq_8.setTypeface(face);
		btn_okq_9.setTypeface(face);
		btn_okq_10.setTypeface(face);
		btn_okq_11.setTypeface(face);
		btn_okq_12.setTypeface(face);
		btn_okq_13.setTypeface(face);
		btn_okq_14.setTypeface(face);
		//
		/*
		 * check speak on or off
		 */

		return true;
	}

	public void Click_help() {

		char[] M = CAUHOI.getDapan().toCharArray();

		switch (number_help) {
		case 1:
			btn_okq_1.setText(M[0] + "");
			btn_okq_1.setOnClickListener(null);
			btn_okq_1.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[0] + "");
			break;
		case 2:
			btn_okq_1.setText(M[0] + "");
			btn_okq_1.setOnClickListener(null);
			btn_okq_1.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[0] + "");
			//
			btn_okq_2.setText(M[1] + "");
			btn_okq_2.setOnClickListener(null);
			btn_okq_2.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[1] + "");
			break;
		case 3:
			btn_okq_1.setText(M[0] + "");
			btn_okq_1.setOnClickListener(null);
			btn_okq_1.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[0] + "");
			//
			btn_okq_2.setText(M[1] + "");
			btn_okq_2.setOnClickListener(null);
			btn_okq_2.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[1] + "");
			//
			btn_okq_3.setText(M[2] + "");
			btn_okq_3.setOnClickListener(null);
			btn_okq_3.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[2] + "");

			break;
		case 4:
			btn_okq_1.setText(M[0] + "");
			btn_okq_1.setOnClickListener(null);
			btn_okq_1.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[0] + "");
			//
			btn_okq_2.setText(M[1] + "");
			btn_okq_2.setOnClickListener(null);
			btn_okq_2.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[1] + "");
			//
			btn_okq_3.setText(M[2] + "");
			btn_okq_3.setOnClickListener(null);
			btn_okq_3.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[2] + "");
			//
			btn_okq_4.setText(M[3] + "");
			btn_okq_4.setOnClickListener(null);
			btn_okq_4.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[3] + "");
			break;
		case 5:
			btn_okq_1.setText(M[0] + "");
			btn_okq_1.setOnClickListener(null);
			btn_okq_1.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[0] + "");
			//
			btn_okq_2.setText(M[1] + "");
			btn_okq_2.setOnClickListener(null);
			btn_okq_2.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[1] + "");
			//
			btn_okq_3.setText(M[2] + "");
			btn_okq_3.setOnClickListener(null);
			btn_okq_3.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[2] + "");
			//
			btn_okq_4.setText(M[3] + "");
			btn_okq_4.setOnClickListener(null);
			btn_okq_4.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[3] + "");
			//
			btn_okq_5.setText(M[4] + "");
			btn_okq_5.setOnClickListener(null);
			btn_okq_5.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[4] + "");
			break;
		case 6:
			btn_okq_1.setText(M[0] + "");
			btn_okq_1.setOnClickListener(null);
			btn_okq_1.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[0] + "");
			//
			btn_okq_2.setText(M[1] + "");
			btn_okq_2.setOnClickListener(null);
			btn_okq_2.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[1] + "");
			//
			btn_okq_3.setText(M[2] + "");
			btn_okq_3.setOnClickListener(null);
			btn_okq_3.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[2] + "");
			//
			btn_okq_4.setText(M[3] + "");
			btn_okq_4.setOnClickListener(null);
			btn_okq_4.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[3] + "");
			//
			btn_okq_5.setText(M[4] + "");
			btn_okq_5.setOnClickListener(null);
			btn_okq_5.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[4] + "");
			//
			btn_okq_6.setText(M[5] + "");
			btn_okq_6.setOnClickListener(null);
			btn_okq_6.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[5] + "");
			break;
		case 7:
			btn_okq_1.setText(M[0] + "");
			btn_okq_1.setOnClickListener(null);
			btn_okq_1.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[0] + "");
			//
			btn_okq_2.setText(M[1] + "");
			btn_okq_2.setOnClickListener(null);
			btn_okq_2.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[1] + "");
			//
			btn_okq_3.setText(M[2] + "");
			btn_okq_3.setOnClickListener(null);
			btn_okq_3.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[2] + "");
			//
			btn_okq_4.setText(M[3] + "");
			btn_okq_4.setOnClickListener(null);
			btn_okq_4.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[3] + "");
			//
			btn_okq_5.setText(M[4] + "");
			btn_okq_5.setOnClickListener(null);
			btn_okq_5.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[4] + "");
			//
			btn_okq_6.setText(M[5] + "");
			btn_okq_6.setOnClickListener(null);
			btn_okq_6.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[5] + "");
			//
			btn_okq_7.setText(M[6] + "");
			btn_okq_7.setOnClickListener(null);
			btn_okq_7.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[6] + "");
			break;
		case 8:
			btn_okq_1.setText(M[0] + "");
			btn_okq_1.setOnClickListener(null);
			btn_okq_1.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[0] + "");
			//
			btn_okq_2.setText(M[1] + "");
			btn_okq_2.setOnClickListener(null);
			btn_okq_2.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[1] + "");
			//
			btn_okq_3.setText(M[2] + "");
			btn_okq_3.setOnClickListener(null);
			btn_okq_3.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[2] + "");
			//
			btn_okq_4.setText(M[3] + "");
			btn_okq_4.setOnClickListener(null);
			btn_okq_4.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[3] + "");
			//
			btn_okq_5.setText(M[4] + "");
			btn_okq_5.setOnClickListener(null);
			btn_okq_5.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[4] + "");
			//
			btn_okq_6.setText(M[5] + "");
			btn_okq_6.setOnClickListener(null);
			btn_okq_6.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[5] + "");
			//
			btn_okq_7.setText(M[6] + "");
			btn_okq_7.setOnClickListener(null);
			btn_okq_7.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[6] + "");
			//
			btn_okq_8.setText(M[7] + "");
			btn_okq_8.setOnClickListener(null);
			btn_okq_8.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[7] + "");
			//

			break;
		case 9:
			btn_okq_1.setText(M[0] + "");
			btn_okq_1.setOnClickListener(null);
			btn_okq_1.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[0] + "");
			//
			btn_okq_2.setText(M[1] + "");
			btn_okq_2.setOnClickListener(null);
			btn_okq_2.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[1] + "");
			//
			btn_okq_3.setText(M[2] + "");
			btn_okq_3.setOnClickListener(null);
			btn_okq_3.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[2] + "");
			//
			btn_okq_4.setText(M[3] + "");
			btn_okq_4.setOnClickListener(null);
			btn_okq_4.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[3] + "");
			//
			btn_okq_5.setText(M[4] + "");
			btn_okq_5.setOnClickListener(null);
			btn_okq_5.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[4] + "");
			//
			btn_okq_6.setText(M[5] + "");
			btn_okq_6.setOnClickListener(null);
			btn_okq_6.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[5] + "");
			//
			btn_okq_7.setText(M[6] + "");
			btn_okq_7.setOnClickListener(null);
			btn_okq_7.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[6] + "");
			//
			btn_okq_8.setText(M[7] + "");
			btn_okq_8.setOnClickListener(null);
			btn_okq_8.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[7] + "");
			//
			btn_okq_9.setText(M[8] + "");
			btn_okq_9.setOnClickListener(null);
			btn_okq_9.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[8] + "");
			break;
		case 10:
			btn_okq_1.setText(M[0] + "");
			btn_okq_1.setOnClickListener(null);
			btn_okq_1.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[0] + "");
			//
			btn_okq_2.setText(M[1] + "");
			btn_okq_2.setOnClickListener(null);
			btn_okq_2.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[1] + "");
			//
			btn_okq_3.setText(M[2] + "");
			btn_okq_3.setOnClickListener(null);
			btn_okq_3.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[2] + "");
			//
			btn_okq_4.setText(M[3] + "");
			btn_okq_4.setOnClickListener(null);
			btn_okq_4.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[3] + "");
			//
			btn_okq_5.setText(M[4] + "");
			btn_okq_5.setOnClickListener(null);
			btn_okq_5.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[4] + "");
			//
			btn_okq_6.setText(M[5] + "");
			btn_okq_6.setOnClickListener(null);
			btn_okq_6.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[5] + "");
			//
			btn_okq_7.setText(M[6] + "");
			btn_okq_7.setOnClickListener(null);
			btn_okq_7.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[6] + "");
			//
			btn_okq_8.setText(M[7] + "");
			btn_okq_8.setOnClickListener(null);
			btn_okq_8.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[7] + "");
			//
			btn_okq_9.setText(M[8] + "");
			btn_okq_9.setOnClickListener(null);
			btn_okq_9.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[8] + "");
			//
			btn_okq_10.setText(M[9] + "");
			btn_okq_10.setOnClickListener(null);
			btn_okq_10.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[9] + "");
			break;
		case 11:
			btn_okq_1.setText(M[0] + "");
			btn_okq_1.setOnClickListener(null);
			btn_okq_1.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[0] + "");
			//
			btn_okq_2.setText(M[1] + "");
			btn_okq_2.setOnClickListener(null);
			btn_okq_2.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[1] + "");
			//
			btn_okq_3.setText(M[2] + "");
			btn_okq_3.setOnClickListener(null);
			btn_okq_3.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[2] + "");
			//
			btn_okq_4.setText(M[3] + "");
			btn_okq_4.setOnClickListener(null);
			btn_okq_4.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[3] + "");
			//
			btn_okq_5.setText(M[4] + "");
			btn_okq_5.setOnClickListener(null);
			btn_okq_5.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[4] + "");
			//
			btn_okq_6.setText(M[5] + "");
			btn_okq_6.setOnClickListener(null);
			btn_okq_6.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[5] + "");
			//
			btn_okq_7.setText(M[6] + "");
			btn_okq_7.setOnClickListener(null);
			btn_okq_7.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[6] + "");
			//
			btn_okq_8.setText(M[7] + "");
			btn_okq_8.setOnClickListener(null);
			btn_okq_8.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[7] + "");
			//
			btn_okq_9.setText(M[8] + "");
			btn_okq_9.setOnClickListener(null);
			btn_okq_9.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[8] + "");
			//
			btn_okq_10.setText(M[9] + "");
			btn_okq_10.setOnClickListener(null);
			btn_okq_10.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[9] + "");
			//
			btn_okq_11.setText(M[10] + "");
			btn_okq_11.setOnClickListener(null);
			btn_okq_11.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[10] + "");
			break;
		case 12:
			btn_okq_1.setText(M[0] + "");
			btn_okq_1.setOnClickListener(null);
			btn_okq_1.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[0] + "");
			//
			btn_okq_2.setText(M[1] + "");
			btn_okq_2.setOnClickListener(null);
			btn_okq_2.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[1] + "");
			//
			btn_okq_3.setText(M[2] + "");
			btn_okq_3.setOnClickListener(null);
			btn_okq_3.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[2] + "");
			//
			btn_okq_4.setText(M[3] + "");
			btn_okq_4.setOnClickListener(null);
			btn_okq_4.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[3] + "");
			//
			btn_okq_5.setText(M[4] + "");
			btn_okq_5.setOnClickListener(null);
			btn_okq_5.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[4] + "");
			//
			btn_okq_6.setText(M[5] + "");
			btn_okq_6.setOnClickListener(null);
			btn_okq_6.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[5] + "");
			//
			btn_okq_7.setText(M[6] + "");
			btn_okq_7.setOnClickListener(null);
			btn_okq_7.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[6] + "");
			//
			btn_okq_8.setText(M[7] + "");
			btn_okq_8.setOnClickListener(null);
			btn_okq_8.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[7] + "");
			//
			btn_okq_9.setText(M[8] + "");
			btn_okq_9.setOnClickListener(null);
			btn_okq_9.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[8] + "");
			//
			btn_okq_10.setText(M[9] + "");
			btn_okq_10.setOnClickListener(null);
			btn_okq_10.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[9] + "");
			//
			btn_okq_11.setText(M[10] + "");
			btn_okq_11.setOnClickListener(null);
			btn_okq_11.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[10] + "");
			//
			btn_okq_12.setText(M[10] + "");
			btn_okq_12.setOnClickListener(null);
			btn_okq_12.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[11] + "");
			break;
		case 13:
			btn_okq_1.setText(M[0] + "");
			btn_okq_1.setOnClickListener(null);
			btn_okq_1.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[0] + "");
			//
			btn_okq_2.setText(M[1] + "");
			btn_okq_2.setOnClickListener(null);
			btn_okq_2.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[1] + "");
			//
			btn_okq_3.setText(M[2] + "");
			btn_okq_3.setOnClickListener(null);
			btn_okq_3.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[2] + "");
			//
			btn_okq_4.setText(M[3] + "");
			btn_okq_4.setOnClickListener(null);
			btn_okq_4.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[3] + "");
			//
			btn_okq_5.setText(M[4] + "");
			btn_okq_5.setOnClickListener(null);
			btn_okq_5.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[4] + "");
			//
			btn_okq_6.setText(M[5] + "");
			btn_okq_6.setOnClickListener(null);
			btn_okq_6.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[5] + "");
			//
			btn_okq_7.setText(M[6] + "");
			btn_okq_7.setOnClickListener(null);
			btn_okq_7.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[6] + "");
			//
			btn_okq_8.setText(M[7] + "");
			btn_okq_8.setOnClickListener(null);
			btn_okq_8.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[7] + "");
			//
			btn_okq_9.setText(M[8] + "");
			btn_okq_9.setOnClickListener(null);
			btn_okq_9.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[8] + "");
			//
			btn_okq_10.setText(M[9] + "");
			btn_okq_10.setOnClickListener(null);
			btn_okq_10.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[9] + "");
			//
			btn_okq_11.setText(M[10] + "");
			btn_okq_11.setOnClickListener(null);
			btn_okq_11.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[10] + "");
			//
			btn_okq_12.setText(M[10] + "");
			btn_okq_12.setOnClickListener(null);
			btn_okq_12.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[11] + "");
			//
			btn_okq_13.setText(M[10] + "");
			btn_okq_13.setOnClickListener(null);
			btn_okq_13.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[12] + "");
			break;
		case 14:
			btn_okq_1.setText(M[0] + "");
			btn_okq_1.setOnClickListener(null);
			btn_okq_1.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[0] + "");
			//
			btn_okq_2.setText(M[1] + "");
			btn_okq_2.setOnClickListener(null);
			btn_okq_2.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[1] + "");
			//
			btn_okq_3.setText(M[2] + "");
			btn_okq_3.setOnClickListener(null);
			btn_okq_3.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[2] + "");
			//
			btn_okq_4.setText(M[3] + "");
			btn_okq_4.setOnClickListener(null);
			btn_okq_4.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[3] + "");
			//
			btn_okq_5.setText(M[4] + "");
			btn_okq_5.setOnClickListener(null);
			btn_okq_5.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[4] + "");
			//
			btn_okq_6.setText(M[5] + "");
			btn_okq_6.setOnClickListener(null);
			btn_okq_6.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[5] + "");
			//
			btn_okq_7.setText(M[6] + "");
			btn_okq_7.setOnClickListener(null);
			btn_okq_7.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[6] + "");
			//
			btn_okq_8.setText(M[7] + "");
			btn_okq_8.setOnClickListener(null);
			btn_okq_8.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[7] + "");
			//
			btn_okq_9.setText(M[8] + "");
			btn_okq_9.setOnClickListener(null);
			btn_okq_9.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[8] + "");
			//
			btn_okq_10.setText(M[9] + "");
			btn_okq_10.setOnClickListener(null);
			btn_okq_10.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[9] + "");
			//
			btn_okq_11.setText(M[10] + "");
			btn_okq_11.setOnClickListener(null);
			btn_okq_11.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[10] + "");
			//
			btn_okq_12.setText(M[10] + "");
			btn_okq_12.setOnClickListener(null);
			btn_okq_12.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[11] + "");
			//
			btn_okq_13.setText(M[10] + "");
			btn_okq_13.setOnClickListener(null);
			btn_okq_13.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[12] + "");
			//
			btn_okq_14.setText(M[10] + "");
			btn_okq_14.setOnClickListener(null);
			btn_okq_14.setTextColor(Color.parseColor("#FFFF00"));
			XoaButton_help(M[13] + "");
			break;
		default:
			break;
		}

	}

	//
	public Bitmap takeScreenshot() {
		View rootView = findViewById(android.R.id.content).getRootView();
		rootView.setDrawingCacheEnabled(true);
		return rootView.getDrawingCache();
	}

	private File imagePath;

	public void saveBitmap(Bitmap bitmap) {
		imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(imagePath);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			Log.e("GREC", e.getMessage(), e);
		} catch (IOException e) {
			Log.e("GREC", e.getMessage(), e);
		}
	}

	private void shareIt() {
		Uri uri = Uri.fromFile(imagePath);
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("image/*");
		String shareBody = "In Tweecher, My highest score with screen shot";
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Tweecher score");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

		startActivity(Intent.createChooser(sharingIntent, "Chia sẻ cho m�?i ngư�?i "));
	}

	@Override
	public void onClick(View v) {
		if (v == ic_key) {
			/*
			 * sự kiện nhập key 
			 */
			final Dialog dl_inkey = new Dialog(GAME.this, R.style.My_Dialog_Theme);
			dl_inkey.setContentView(R.layout.dialog_input_key);
			dl_inkey.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			dl_inkey.setCanceledOnTouchOutside(false);
			dl_inkey.setCancelable(false);
			dl_inkey.show();
			final EditText edit_input_key = (EditText) dl_inkey.findViewById(R.id.edit_iputKey);
			Button btn_xacnhan_ipk = (Button) dl_inkey.findViewById(R.id.btn_xacnhan_inputkey);
			Button btn_thoat_ipk = (Button) dl_inkey.findViewById(R.id.btn_thoat_inputkey);
			// bat su kien
			edit_input_key.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					edit_input_key.setText("");

				}
			});
			btn_xacnhan_ipk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// nhập key
					/*
					 * 
					 */
					managersqllite.opentdatabase();
					int key = Integer.parseInt(edit_input_key.getText().toString());

					if (managersqllite.getKey(key)) {

						diem = diem + 50;
						btn_diem.setText(diem + "");
						dl_inkey.dismiss();
						Toast.makeText(getApplicationContext(), "Nhập KEY thành công", Toast.LENGTH_LONG).show();
						managersqllite.deleteKey(key);
						managersqllite.close();
					} else {
						Toast.makeText(getApplicationContext(), "KEY nhấp không đúng", Toast.LENGTH_LONG).show();
					}

				}
			});
			btn_thoat_ipk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					dl_inkey.dismiss();
				}
			});
		} else if (v == ic_people) {
			Bitmap bitmap = takeScreenshot();
			saveBitmap(bitmap);
			shareIt();
		} else if (v == icon_help || v == icon_swap) {
			/*
			 * sự kiện chọn trợ giúp hoặc đổi câu. 
			 */

			if (diem < 15) {
				Toast.makeText(getApplicationContext(), "Bạn cần có 15 điểm để được trợ giúp nhé !!! ",
						Toast.LENGTH_LONG).show();
			} else {

				dialog = new Dialog(GAME.this, R.style.My_Dialog_Theme);
				dialog.setContentView(R.layout.help);
				dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				dialog.setCanceledOnTouchOutside(false);
				dialog.setCancelable(false);
				dialog.show();
				btn_oke_dialog_help = (Button) dialog.findViewById(R.id.ic_oke_dialog_help);

				Button btn_dong = (Button) dialog.findViewById(R.id.ic_no_dialog_help);
				TextView txt_help_dialong = (TextView) dialog.findViewById(R.id.txt_dialog_help);
				final int help;

				if (v == icon_help) {
					help = 0;
					txt_help_dialong.setText(
							"Mở một ô chữ trong đáp án, bạn sẽ bị trừ 15 điểm. Bạn có chắc chắn muốn mở không ?");
				} else {
					help = 1;
					txt_help_dialong.setText(
							"Đổi qua câu hỏi khác bạn sẽ bị trừ 15 điểm . Bạn có chắc chắn muốn đổi không ?");

				}
				btn_dong.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
					}
				});

				btn_oke_dialog_help.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (help == 0) {
							/*
							 * trợ giúp mở ô chữ 
							 */
							HienOGoiy(CAUHOI.getGoiy());
							HienButtonODapAn(CAUHOI.getDapan().length());
							number_help++;
							Click_help();
							size_ChuDaChon = CAUHOI.getDapan().length() - number_help;
							if (size_ChuDaChon < 1)
								KiemTraDapAn();
							dialog.dismiss();
							diem = diem - 15;
							btn_diem.setText(diem + "");
							//
							SharedPreferences.Editor edit = pre.edit();
							edit.putInt("num_help", number_help);
							edit.commit();
						} else {
							/*
							 * đổi câu  hỏi 
							 */
							Random rd = new Random();
							int INDEX_ID_RANDOM;
							do {
								INDEX_ID_RANDOM = rd.nextInt((301 - level)) + level;
							} while (INDEX_ID_RANDOM == level);
							//kiểm tra xem câu hỏi đó đã được upload hình ảnh chưa ? 
							int updateTMP = INDEX_ID_RANDOM/50 ; 
							updateTMP = updateTMP < 1 ? 1 : (updateTMP*50) ;
							UploadImage upload = new UploadImage(getApplication(),updateTMP);
							//<--
							Cauhoi cauhoi2 = new Cauhoi();
							Cauhoi cauhoi1 = new Cauhoi();
							cauhoi1 = managersqllite.getCauHoi(level);
							cauhoi2 = managersqllite.getCauHoi(INDEX_ID_RANDOM);
							managersqllite.UploadCauHoi(level, INDEX_ID_RANDOM, cauhoi1, cauhoi2);
							managersqllite.close();
							// SAVE DATA
							diem = diem - 15;
							// btn_diem.setText(diem + "");
							number_help = 0;
							pre = getSharedPreferences("mydata", MODE_PRIVATE);
							SharedPreferences.Editor edit = pre.edit();
							edit.putInt("num_help", number_help);
							edit.commit();
							// NEXT CÂU HỎI
							Intent intent = new Intent(getApplicationContext(), GAME.class);
							startActivity(intent);

							dialog.dismiss();

						}

					}
				});

			}

		} else if (v == home) {
			// click vao icon home
			nhacnen.stop();
			Intent home = new Intent(this, MainActivity.class);
			startActivity(home);
		}
		if (v == ic_audio_on) {
			ic_audio_on.setVisibility(View.GONE);
			ic_audio_off.setVisibility(View.VISIBLE);
			nhacnen.pause();
			handler.removeCallbacks(runmusics);
			SharedPreferences.Editor edit = pre.edit();
			edit.putBoolean("speak", false);
			edit.commit();

		}
		if (v == ic_audio_off) {
			ic_audio_on.setVisibility(View.VISIBLE);
			ic_audio_off.setVisibility(View.GONE);
			nhacnen.start();
			handler.postDelayed(runmusics, 7000);
			SharedPreferences.Editor edit = pre.edit();
			edit.putBoolean("speak", true);
			edit.commit();
		}
		//
		if (v == btn_ogy_1 && GhiKetQua(btn_ogy_1.getText().toString())) {
			btn_ogy_1.setVisibility(View.INVISIBLE);
			size_ChuDaChon--;
			if (size_ChuDaChon < 1)
				KiemTraDapAn();
		} else if (v == btn_ogy_2 && GhiKetQua(btn_ogy_2.getText().toString())) {
			btn_ogy_2.setVisibility(View.INVISIBLE);
			size_ChuDaChon--;
			if (size_ChuDaChon < 1)
				KiemTraDapAn();
		} else if (v == btn_ogy_3 && GhiKetQua(btn_ogy_3.getText().toString())) {
			btn_ogy_3.setVisibility(View.INVISIBLE);
			size_ChuDaChon--;
			if (size_ChuDaChon < 1)
				KiemTraDapAn();
		} else if (v == btn_ogy_4 && GhiKetQua(btn_ogy_4.getText().toString())) {
			btn_ogy_4.setVisibility(View.INVISIBLE);
			size_ChuDaChon--;
			if (size_ChuDaChon < 1)
				KiemTraDapAn();
		} else if (v == btn_ogy_5 && GhiKetQua(btn_ogy_5.getText().toString())) {
			btn_ogy_5.setVisibility(View.INVISIBLE);
			size_ChuDaChon--;
			if (size_ChuDaChon < 1)
				KiemTraDapAn();
		} else if (v == btn_ogy_6 && GhiKetQua(btn_ogy_6.getText().toString())) {
			btn_ogy_6.setVisibility(View.INVISIBLE);
			size_ChuDaChon--;
			if (size_ChuDaChon < 1)
				KiemTraDapAn();
		} else if (v == btn_ogy_7 && GhiKetQua(btn_ogy_7.getText().toString())) {
			btn_ogy_7.setVisibility(View.INVISIBLE);
			size_ChuDaChon--;
			if (size_ChuDaChon < 1)
				KiemTraDapAn();
		} else if (v == btn_ogy_8 && GhiKetQua(btn_ogy_8.getText().toString())) {
			btn_ogy_8.setVisibility(View.INVISIBLE);
			size_ChuDaChon--;
			if (size_ChuDaChon < 1)
				KiemTraDapAn();
		} else if (v == btn_ogy_9 && GhiKetQua(btn_ogy_9.getText().toString())) {
			btn_ogy_9.setVisibility(View.INVISIBLE);
			size_ChuDaChon--;
			if (size_ChuDaChon < 1)
				KiemTraDapAn();
		} else if (v == btn_ogy_10 && GhiKetQua(btn_ogy_10.getText().toString())) {
			btn_ogy_10.setVisibility(View.INVISIBLE);
			size_ChuDaChon--;
			if (size_ChuDaChon < 1)
				KiemTraDapAn();
		} else if (v == btn_ogy_11 && GhiKetQua(btn_ogy_11.getText().toString())) {
			btn_ogy_11.setVisibility(View.INVISIBLE);
			size_ChuDaChon--;
			if (size_ChuDaChon < 1)
				KiemTraDapAn();
		} else if (v == btn_ogy_12 && GhiKetQua(btn_ogy_12.getText().toString())) {
			btn_ogy_12.setVisibility(View.INVISIBLE);
			size_ChuDaChon--;
			if (size_ChuDaChon < 1)
				KiemTraDapAn();
		} else if (v == btn_ogy_13 && GhiKetQua(btn_ogy_13.getText().toString())) {
			btn_ogy_13.setVisibility(View.INVISIBLE);
			size_ChuDaChon--;
			if (size_ChuDaChon < 1)
				KiemTraDapAn();
		} else if (v == btn_ogy_14 && GhiKetQua(btn_ogy_14.getText().toString())) {
			btn_ogy_14.setVisibility(View.INVISIBLE);
			size_ChuDaChon--;
			if (size_ChuDaChon < 1)
				KiemTraDapAn();
		}
		//
		//
		else if (v == btn_okq_1 && XoaOChu(btn_okq_1.getText().toString())) {
			btn_okq_1.setText("");
			size_ChuDaChon++;

		} else if (v == btn_okq_2 && XoaOChu(btn_okq_2.getText().toString())) {
			btn_okq_2.setText("");
			size_ChuDaChon++;

		} else if (v == btn_okq_3 && XoaOChu(btn_okq_3.getText().toString())) {
			btn_okq_3.setText("");
			size_ChuDaChon++;

		} else if (v == btn_okq_4 && XoaOChu(btn_okq_4.getText().toString())) {
			btn_okq_4.setText("");
			size_ChuDaChon++;

		} else if (v == btn_okq_5 && XoaOChu(btn_okq_5.getText().toString())) {
			btn_okq_5.setText("");
			size_ChuDaChon++;

		} else if (v == btn_okq_6 && XoaOChu(btn_okq_6.getText().toString())) {
			btn_okq_6.setText("");
			size_ChuDaChon++;

		} else if (v == btn_okq_7 && XoaOChu(btn_okq_7.getText().toString())) {
			btn_okq_7.setText("");
			size_ChuDaChon++;

		} else if (v == btn_okq_8 && XoaOChu(btn_okq_8.getText().toString())) {
			btn_okq_8.setText("");
			size_ChuDaChon++;

		} else if (v == btn_okq_9 && XoaOChu(btn_okq_9.getText().toString())) {
			btn_okq_9.setText("");
			size_ChuDaChon++;

		} else if (v == btn_okq_10 && XoaOChu(btn_okq_10.getText().toString())) {
			btn_okq_10.setText("");
			size_ChuDaChon++;

		} else if (v == btn_okq_11 && XoaOChu(btn_okq_11.getText().toString())) {
			btn_okq_11.setText("");
			size_ChuDaChon++;

		} else if (v == btn_okq_12 && XoaOChu(btn_okq_12.getText().toString())) {
			btn_okq_12.setText("");
			size_ChuDaChon++;

		} else if (v == btn_okq_13 && XoaOChu(btn_okq_13.getText().toString())) {
			btn_okq_13.setText("");
			size_ChuDaChon++;

		} else if (v == btn_okq_14 && XoaOChu(btn_okq_14.getText().toString())) {
			btn_okq_14.setText("");
			size_ChuDaChon++;

		} else {

		}

	}

	public void showgame(int id) {
		//
		managersqllite = new ManagerSqlite(getApplication());
		managersqllite.opentdatabase();

		//
		level = pre.getInt("numberlevel", 1);
		if(level==1 || level%50==0)
		{
			// upload ảnh khi bắt đầu chơi hoặc mỗi khi chơi đk 50 câu. 
			UploadImage upload = new UploadImage(getApplication(),level); 
		}
		diem = pre.getInt("numberDiem", 100);
		number_help = pre.getInt("num_help", 0);
		//
		CAUHOI = new Cauhoi();
		CAUHOI = managersqllite.getCauHoi(level);
		// // set dữ liệu cho câu h�?i
		pre = getSharedPreferences("mydata", MODE_PRIVATE);
		//

		size_ChuDaChon = CAUHOI.getDapan().length();
		//
		btn_diem.setText(diem + "");
		btn_numberlevel.setText(CAUHOI.getId() + "");
		//
		if (pre.getBoolean("speak", true)) {
			ic_audio_off.setVisibility(View.GONE);
			ic_audio_on.setVisibility(View.VISIBLE);
			nhacnen.start();
			handler.postDelayed(runmusics, 15000);
		} else {
			ic_audio_off.setVisibility(View.VISIBLE);
			ic_audio_on.setVisibility(View.GONE);

		}

		// set level cho game la số id của câu h�?i
		// // set hình ảnh cho câu h�?i
		Glide.with(this).load(CAUHOI.getHinhanh()).into(img_cauhoi);
//		img_cauhoi.setImageResource(CAUHOI.getHinhanh());
		// // set số lượng ô số cho câu h�?i

		HienButtonODapAn(CAUHOI.getDapan().length());
		// hiện ô chữ gợi ý
		HienOGoiy(CAUHOI.getGoiy());
		//

		if (number_help > 0) {
			Click_help();
			size_ChuDaChon = size_ChuDaChon - number_help;
		}

	}

	// 13 ,12 ,11,10,9,8
	public void HienOGoiy(String dapan) {

		char[] M = dapan.toCharArray();

		btn_ogy_1.setVisibility(View.VISIBLE);
		btn_ogy_2.setVisibility(View.VISIBLE);
		btn_ogy_3.setVisibility(View.VISIBLE);
		btn_ogy_4.setVisibility(View.VISIBLE);
		btn_ogy_5.setVisibility(View.VISIBLE);
		btn_ogy_6.setVisibility(View.VISIBLE);
		btn_ogy_7.setVisibility(View.VISIBLE);
		btn_ogy_8.setVisibility(View.VISIBLE);
		btn_ogy_9.setVisibility(View.VISIBLE);
		btn_ogy_10.setVisibility(View.VISIBLE);
		btn_ogy_11.setVisibility(View.VISIBLE);
		btn_ogy_12.setVisibility(View.VISIBLE);
		btn_ogy_13.setVisibility(View.VISIBLE);
		btn_ogy_14.setVisibility(View.VISIBLE);
		//
		btn_ogy_1.setText(M[0] + "");
		btn_ogy_2.setText(M[1] + "");
		btn_ogy_3.setText(M[2] + "");
		btn_ogy_4.setText(M[3] + "");
		btn_ogy_5.setText(M[4] + "");
		btn_ogy_6.setText(M[5] + "");
		btn_ogy_7.setText(M[6] + "");
		btn_ogy_8.setText(M[7] + "");
		btn_ogy_9.setText(M[8] + "");
		btn_ogy_10.setText(M[9] + "");
		btn_ogy_11.setText(M[10] + "");
		btn_ogy_12.setText(M[11] + "");
		btn_ogy_13.setText(M[12] + "");
		btn_ogy_14.setText(M[13] + "");

	}

	public void HienButtonODapAn(int SoOChu) {
		btn_okq_1.setVisibility(View.GONE);
		btn_okq_2.setVisibility(View.GONE);
		btn_okq_3.setVisibility(View.GONE);
		btn_okq_4.setVisibility(View.GONE);
		btn_okq_5.setVisibility(View.GONE);
		btn_okq_6.setVisibility(View.GONE);
		btn_okq_7.setVisibility(View.GONE);
		btn_okq_8.setVisibility(View.GONE);
		btn_okq_9.setVisibility(View.GONE);
		btn_okq_10.setVisibility(View.GONE);
		btn_okq_11.setVisibility(View.GONE);
		btn_okq_12.setVisibility(View.GONE);
		btn_okq_13.setVisibility(View.GONE);
		btn_okq_14.setVisibility(View.GONE);
		//
		btn_okq_1.setText("");
		btn_okq_2.setText("");
		btn_okq_3.setText("");
		btn_okq_4.setText("");
		btn_okq_5.setText("");
		btn_okq_6.setText("");
		btn_okq_7.setText("");
		btn_okq_8.setText("");
		btn_okq_9.setText("");
		btn_okq_10.setText("");
		btn_okq_11.setText("");
		btn_okq_12.setText("");
		btn_okq_13.setText("");
		btn_okq_14.setText("");
		//
		while (SoOChu >= 1) {
			switch (SoOChu) {

			case 1:
				btn_okq_1.setVisibility(View.VISIBLE);
				break;
			case 2:
				btn_okq_2.setVisibility(View.VISIBLE);
				break;
			case 3:
				btn_okq_3.setVisibility(View.VISIBLE);
				break;
			case 4:
				btn_okq_4.setVisibility(View.VISIBLE);
				break;
			case 5:
				btn_okq_5.setVisibility(View.VISIBLE);
				break;
			case 6:
				btn_okq_6.setVisibility(View.VISIBLE);
				break;
			case 7:
				btn_okq_7.setVisibility(View.VISIBLE);
				break;
			case 8:
				btn_okq_8.setVisibility(View.VISIBLE);
				break;
			case 9:
				btn_okq_9.setVisibility(View.VISIBLE);
				break;
			case 10:
				btn_okq_10.setVisibility(View.VISIBLE);
				break;
			case 11:
				btn_okq_11.setVisibility(View.VISIBLE);
				break;
			case 12:
				btn_okq_12.setVisibility(View.VISIBLE);
				break;
			case 13:
				btn_okq_13.setVisibility(View.VISIBLE);
				break;
			case 14:
				btn_okq_14.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
			SoOChu--;
		}
	}

	public boolean GhiKetQua(String key) {
		if (btn_okq_1.getText().toString().equals("") && btn_okq_1.getVisibility() == View.VISIBLE) {
			btn_okq_1.setText(key);
		} else if (btn_okq_2.getText().toString().equals("") && btn_okq_2.getVisibility() == View.VISIBLE) {
			btn_okq_2.setText(key);
		} else if (btn_okq_3.getText().toString().equals("") && btn_okq_3.getVisibility() == View.VISIBLE) {
			btn_okq_3.setText(key);
		} else if (btn_okq_4.getText().toString().equals("") && btn_okq_4.getVisibility() == View.VISIBLE) {
			btn_okq_4.setText(key);
		} else if (btn_okq_5.getText().toString().equals("") && btn_okq_5.getVisibility() == View.VISIBLE) {
			btn_okq_5.setText(key);
		} else if (btn_okq_6.getText().toString().equals("") && btn_okq_6.getVisibility() == View.VISIBLE) {
			btn_okq_6.setText(key);
		} else if (btn_okq_7.getText().toString().equals("") && btn_okq_7.getVisibility() == View.VISIBLE) {
			btn_okq_7.setText(key);
		} else if (btn_okq_8.getText().toString().equals("") && btn_okq_8.getVisibility() == View.VISIBLE) {
			btn_okq_8.setText(key);
		} else if (btn_okq_9.getText().toString().equals("") && btn_okq_9.getVisibility() == View.VISIBLE) {
			btn_okq_9.setText(key);
		} else if (btn_okq_10.getText().toString().equals("") && btn_okq_10.getVisibility() == View.VISIBLE) {
			btn_okq_10.setText(key);
		} else if (btn_okq_11.getText().toString().equals("") && btn_okq_11.getVisibility() == View.VISIBLE) {
			btn_okq_11.setText(key);
		} else if (btn_okq_12.getText().toString().equals("") && btn_okq_12.getVisibility() == View.VISIBLE) {
			btn_okq_12.setText(key);
		} else if (btn_okq_13.getText().toString().equals("") && btn_okq_13.getVisibility() == View.VISIBLE) {
			btn_okq_13.setText(key);
		} else if (btn_okq_14.getText().toString().equals("") && btn_okq_14.getVisibility() == View.VISIBLE) {
			btn_okq_14.setText(key);
		} else { // ô chữ đã đầy không thể ghi thêm vào được
			return false;
		}
		return true;

	}

	public boolean XoaOChu(String key) {
		if (btn_ogy_1.getVisibility() == View.INVISIBLE && btn_ogy_1.getText().equals(key)) {
			btn_ogy_1.setVisibility(View.VISIBLE);
		} else if (btn_ogy_2.getVisibility() == View.INVISIBLE && btn_ogy_2.getText().equals(key)) {
			btn_ogy_2.setVisibility(View.VISIBLE);
		} else if (btn_ogy_3.getVisibility() == View.INVISIBLE && btn_ogy_3.getText().equals(key)) {
			btn_ogy_3.setVisibility(View.VISIBLE);
		} else if (btn_ogy_4.getVisibility() == View.INVISIBLE && btn_ogy_4.getText().equals(key)) {
			btn_ogy_4.setVisibility(View.VISIBLE);
		} else if (btn_ogy_5.getVisibility() == View.INVISIBLE && btn_ogy_5.getText().equals(key)) {
			btn_ogy_5.setVisibility(View.VISIBLE);
		} else if (btn_ogy_6.getVisibility() == View.INVISIBLE && btn_ogy_6.getText().equals(key)) {
			btn_ogy_6.setVisibility(View.VISIBLE);
		} else if (btn_ogy_7.getVisibility() == View.INVISIBLE && btn_ogy_7.getText().equals(key)) {
			btn_ogy_7.setVisibility(View.VISIBLE);
		} else if (btn_ogy_8.getVisibility() == View.INVISIBLE && btn_ogy_8.getText().equals(key)) {
			btn_ogy_8.setVisibility(View.VISIBLE);
		} else if (btn_ogy_9.getVisibility() == View.INVISIBLE && btn_ogy_9.getText().equals(key)) {
			btn_ogy_9.setVisibility(View.VISIBLE);
		} else if (btn_ogy_10.getVisibility() == View.INVISIBLE && btn_ogy_10.getText().equals(key)) {
			btn_ogy_10.setVisibility(View.VISIBLE);
		} else if (btn_ogy_11.getVisibility() == View.INVISIBLE && btn_ogy_11.getText().equals(key)) {
			btn_ogy_11.setVisibility(View.VISIBLE);
		} else if (btn_ogy_12.getVisibility() == View.INVISIBLE && btn_ogy_12.getText().equals(key)) {
			btn_ogy_12.setVisibility(View.VISIBLE);
		} else if (btn_ogy_13.getVisibility() == View.INVISIBLE && btn_ogy_13.getText().equals(key)) {
			btn_ogy_13.setVisibility(View.VISIBLE);
		} else if (btn_ogy_14.getVisibility() == View.INVISIBLE && btn_ogy_14.getText().equals(key)) {
			btn_ogy_14.setVisibility(View.VISIBLE);
		} else {
			return false;
		}
		return true;
	}

	@SuppressLint("InflateParams")
	public void KiemTraDapAn() {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.mycustomtoast_layout, null);

		//
		String dapan = btn_okq_1.getText().toString() + btn_okq_2.getText().toString() + btn_okq_3.getText().toString()
				+ btn_okq_4.getText().toString() + btn_okq_5.getText().toString() + btn_okq_6.getText().toString()
				+ btn_okq_7.getText().toString() + btn_okq_8.getText().toString() + btn_okq_9.getText().toString()
				+ btn_okq_10.getText().toString() + btn_okq_11.getText().toString() + btn_okq_12.getText().toString()
				+ btn_okq_13.getText().toString() + btn_okq_14.getText().toString() + "";
		if (dapan.equals(CAUHOI.getDapan())) {
			/*
			 * thong báo kết quả
			 */
			//
			level = level + 1;
			diem = diem + 10;
			nhacnen.stop();
			if (pre.getBoolean("speak", true)) {
				new MediaPlayer();
				nhacchienthang = MediaPlayer.create(this, R.raw.musicwin);
				nhacchienthang.start();
			}
			// luu help
			number_help = 0;
			pre = getSharedPreferences("mydata", MODE_PRIVATE);
			SharedPreferences.Editor edit = pre.edit();
			edit.putInt("num_help", number_help);
			edit.commit();

			// medi

			Intent good = new Intent(GAME.this, ChienThang.class);
			good.putExtra("id", CAUHOI.getId());
			good.putExtra("dapan", CAUHOI.getKetqua());
			startActivity(good);

		} else {
			Animation anima = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
			btn_okq_1.startAnimation(anima);
			btn_okq_2.startAnimation(anima);
			btn_okq_3.startAnimation(anima);
			btn_okq_4.startAnimation(anima);
			btn_okq_5.startAnimation(anima);
			btn_okq_6.startAnimation(anima);
			btn_okq_7.startAnimation(anima);
			btn_okq_8.startAnimation(anima);
			btn_okq_9.startAnimation(anima);
			btn_okq_10.startAnimation(anima);
			btn_okq_11.startAnimation(anima);
			btn_okq_12.startAnimation(anima);
			btn_okq_13.startAnimation(anima);
			btn_okq_14.startAnimation(anima);
			//
			Toast toat = new Toast(this);
			toat.setGravity(Gravity.CENTER, 0, 0);
			toat.setDuration(Toast.LENGTH_LONG);
			toat.setView(layout);
			toat.show();
		}

	}

	@Override
	public void onBackPressed() {

		final Dialog dialog = new Dialog(this, R.style.My_Dialog_Theme);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(R.layout.dialog_back);
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialog.show();
		Button btb_co = (Button) dialog.findViewById(R.id.btb_co);
		Button btb_khong = (Button) dialog.findViewById(R.id.btb_khong);
		btb_co.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				nhacnen.stop();
				// Khoi tao lai Activity main

				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				pre = getSharedPreferences("mydata", MODE_PRIVATE);
				SharedPreferences.Editor edit = pre.edit();
				edit.putInt("numberlevel", level);
				edit.putInt("numberDiem", diem);
				edit.putInt("num_help", number_help);
				edit.commit();
				startActivity(intent);
				// Tao su kien ket thuc app
				Intent startMain = new Intent(Intent.ACTION_MAIN);
				startMain.addCategory(Intent.CATEGORY_HOME);
				startActivity(startMain);
				finish();

			}
		});
		btb_khong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				dialog.dismiss();
			}
		});

	}

	public ArrayList<String> getDsOChuDapAn() {
		return dsOChuDapAn;
	}

	public void setDsOChuDapAn(ArrayList<String> dsOChuDapAn) {
		this.dsOChuDapAn = dsOChuDapAn;
	}

	public void XoaButton_help(String key) {
		if (btn_ogy_1.getText().equals(key)) {
			btn_ogy_1.setVisibility(View.INVISIBLE);
		} else if (btn_ogy_2.getText().equals(key)) {
			btn_ogy_2.setVisibility(View.INVISIBLE);
		} else if (btn_ogy_3.getText().equals(key)) {
			btn_ogy_3.setVisibility(View.INVISIBLE);
		} else if (btn_ogy_4.getText().equals(key)) {
			btn_ogy_4.setVisibility(View.INVISIBLE);
		} else if (btn_ogy_5.getText().equals(key)) {
			btn_ogy_5.setVisibility(View.INVISIBLE);
		} else if (btn_ogy_6.getText().equals(key)) {
			btn_ogy_6.setVisibility(View.INVISIBLE);
		} else if (btn_ogy_7.getText().equals(key)) {
			btn_ogy_7.setVisibility(View.INVISIBLE);
		} else if (btn_ogy_8.getText().equals(key)) {
			btn_ogy_8.setVisibility(View.INVISIBLE);
		} else if (btn_ogy_9.getText().equals(key)) {
			btn_ogy_9.setVisibility(View.INVISIBLE);
		} else if (btn_ogy_10.getText().equals(key)) {
			btn_ogy_10.setVisibility(View.INVISIBLE);
		} else if (btn_ogy_11.getText().equals(key)) {
			btn_ogy_11.setVisibility(View.INVISIBLE);
		} else if (btn_ogy_12.getText().equals(key)) {
			btn_ogy_12.setVisibility(View.INVISIBLE);
		} else if (btn_ogy_13.getText().equals(key)) {
			btn_ogy_13.setVisibility(View.INVISIBLE);
		} else if (btn_ogy_14.getText().equals(key)) {
			btn_ogy_14.setVisibility(View.INVISIBLE);
		} else {

		}
	}

}
