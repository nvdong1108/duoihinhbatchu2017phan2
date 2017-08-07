package com.nvd.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

import com.nvd.objcauhoi.Cauhoi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class ManagerSqlite extends SQLiteOpenHelper {
	SQLiteDatabase database = null;
	private static final String name = "data_nhinhinhdoanchu.sqlite";
	private static final String path = "/data/data/com.vandong.gamebatchu02082017/databases/";
	Context mContex;

	public ManagerSqlite(Context context) {
		super(context, name, null, 1);
		this.mContex = context;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	public void opentdatabase() {
		if (!checkDataBase()) {
			try {
				copydatabase();
				String urn = path + name;
				database = SQLiteDatabase.openDatabase(urn, null, SQLiteDatabase.CREATE_IF_NECESSARY);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			String urn = path + name;
			database = SQLiteDatabase.openDatabase(urn, null, SQLiteDatabase.CREATE_IF_NECESSARY);
		}

	}

	public void UploadCauHoi(int ID1, int ID2, Cauhoi cauhoi1, Cauhoi cauhoi2) {
		// ID1 = ID2
		String sql1 = "UPDATE cauhoiversion2 SET hinh = " + cauhoi2.getHinhanh() + " WHERE id =" + ID1 + "";
		String sql2 = "UPDATE cauhoiversion2 SET dapan = '" + cauhoi2.getDapan() + "' WHERE id =" + ID1 + "";
		String sql3 = "UPDATE cauhoiversion2 SET goiy = '" + cauhoi2.getGoiy() + "' WHERE id =" + ID1 + "";
		String sql4 = "UPDATE cauhoiversion2 SET ketqua = '" + cauhoi2.getKetqua() + "' WHERE id =" + ID1 + "";
		// ID2 = ID1
		String sql5 = "UPDATE cauhoiversion2 SET hinh = " + cauhoi1.getHinhanh() + " WHERE id =" + ID2 + "";
		String sql6 = "UPDATE cauhoiversion2 SET dapan = '" + cauhoi1.getDapan() + "' WHERE id =" + ID2 + "";
		String sql7 = "UPDATE cauhoiversion2 SET goiy = '" + cauhoi1.getGoiy() + "' WHERE id =" + ID2 + "";
		String sql8 = "UPDATE cauhoiversion2 SET ketqua = '" + cauhoi1.getKetqua() + "' WHERE id =" + ID2 + "";

		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.execSQL(sql3);
		db.execSQL(sql4);
		//
		db.execSQL(sql5);
		db.execSQL(sql6);
		db.execSQL(sql7);
		db.execSQL(sql8);

	}

//	public void UpdateImageView(int img, int id) {
//		String sql = "UPDATE cauhoi SET hinh = " + img + " WHERE id = " + id + "";
//		SQLiteDatabase db = getWritableDatabase();
//		db.execSQL(sql);
//
//	}

	//
	public void UpdateImageViewVersion2(int img, String dapan , String goiy) {
		String sql = "UPDATE cauhoiversion2 SET hinh = " + img + " WHERE dapan = '" + dapan + "' AND goiy = '"+goiy+"'";
		SQLiteDatabase db = getWritableDatabase();
		  db.execSQL(sql);

	}

	public void UpdateOgoiy(String dapan) {
		//
		ArrayList<String> M = new ArrayList<String>();

		char[] ds = { 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A' };

		ds = dapan.toCharArray();
		for (int i = 0; i < ds.length; i++) {
			M.add(ds[i] + "");
		}

		Random rd = new Random();
		for (int i = ds.length; i < 14; i++) {
			M.add((char) (rd.nextInt(25) + 65) + "");
		}
		// đảo mảng
		String tmp;
		int a, b, c;
		for (int i = 0; i < 20; i++) {
			a = rd.nextInt(7);
			b = rd.nextInt(14);
			tmp = M.get(a);
			M.set(a, M.get(b));
			M.set(b, tmp);
		}
		String retun = "";
		for (int i = 0; i < 14; i++) {
			retun = retun + M.get(i);
		}
		//
		String sql = "UPDATE cauhoiversion2 SET goiy = '" + retun + "' WHERE  dapan = '" + dapan + "'";
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(sql);
	}

	public Cauhoi getCauHoi(int id) {
		Cauhoi ch;
		Cursor c = null;
		c = database.query("cauhoiversion2", null, "id=" + id + "", null, null, null, null);
		c.moveToNext();
		ch = new Cauhoi(c.getInt(0), c.getInt(1), c.getString(2), c.getString(3), c.getString(4), "");
		c.close();
		return ch;
	}

	public boolean getKey(int key) {
		Cursor c = null;
		c = database.query("key", null, "key=" + key + "", null, null, null, null);
		if (!c.moveToFirst() || c.getCount() == 0) {
			c.close();
			return false;
		}
		c.close();
		return true;
	}

	public void deleteKey(int key) {

		database.delete("key", "key = " + key + "", null);
	}

	public ArrayList<Cauhoi> getALL() {
		ArrayList<Cauhoi> arr = new ArrayList<Cauhoi>();
		Cursor c = null;
		c = database.query("cauhoiversion2", null, null, null, null, null, null);
		while (c.moveToNext()) {
			Cauhoi ch;
			ch = new Cauhoi(c.getInt(0), c.getInt(1), c.getString(2), "", c.getString(4), "");
			arr.add(ch);
		}

		c.close();
		return arr;
	}

	public boolean checkDataBase() {
		File dbFile = new File(path + name);
		Log.v("dbFile", dbFile + "   " + dbFile.exists());
		return dbFile.exists();
	}

	public void copydatabase() throws IOException {
		if (!checkDataBase()) {
			OutputStream myOutput = new FileOutputStream(path + name);
			byte[] buffer = new byte[1024];
			int length;
			InputStream myInput = mContex.getAssets().open("data_nhinhinhdoanchu.sqlite");

			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
			myInput.close();
			myOutput.flush();
			myOutput.close();
			Toast.makeText(mContex, "*** Copy data thành công ***", Toast.LENGTH_LONG).show();
		} else
			Toast.makeText(mContex, "Khong Copy", Toast.LENGTH_LONG).show();

	}

}
