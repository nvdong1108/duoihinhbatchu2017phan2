package com.vandong.gamebatchu02082017;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.app.Activity;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChienThang extends Activity implements OnClickListener {

	private LinearLayout btn_next;
	private TextView txt_dapan;
	private Typeface face;

	// private InterstitialAd inters;
	AdView adview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chien_thang);
		//
		adview = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice("754DB6521943676637AE86202C5ACE52").build();
		adview.loadAd(adRequest);
		//
		AnhXa();

		Bundle extras = getIntent().getExtras();
		extras.getString("dapan");
		txt_dapan.setText(extras.getString("dapan"));
		btn_next.setOnClickListener(this);

	}

	public void AnhXa() {
		btn_next = (LinearLayout) findViewById(R.id.btn_next_leve);
		txt_dapan = (TextView) findViewById(R.id.txt_ketqua);
		face = Typeface.createFromAsset(getAssets(), "font/TIMESBD.TTF");
		txt_dapan.setTypeface(face);
	}
	@Override
	public void onClick(View v) {
		if (v == btn_next) {
			Intent next_level = new Intent(ChienThang.this, GAME.class);
			startActivity(next_level);
		} else {

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

				// Khoi tao lai Activity main

				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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

}
