package com.nvd.objcauhoi;

public class Cauhoi {
	private int id;
	private int hinhanh;
	private String dapan;
	private String goiy;
	private String ketqua;
	private String giaithich;

	public Cauhoi(int id, int hinhanh, String dapan, String goiy,
			String ketqua, String giaithich) {
		super();
		this.id = id;
		this.hinhanh = hinhanh;
		this.dapan = dapan;
		this.goiy = goiy;
		this.ketqua = ketqua;
		this.giaithich = giaithich;
	}

	public Cauhoi() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHinhanh() {
		return hinhanh;
	}

	public void setHinhanh(int hinhanh) {
		this.hinhanh = hinhanh;
	}

	public String getDapan() {
		return dapan;
	}

	public void setDapan(String dapan) {
		this.dapan = dapan;
	}

	public String getGoiy() {
		return goiy;
	}

	public void setGoiy(String goiy) {
		this.goiy = goiy;
	}

	public String getKetqua() {
		return ketqua;
	}

	public void setKetqua(String ketqua) {
		this.ketqua = ketqua;
	}

	public String getGiaithich() {
		return giaithich;
	}

	public void setGiaithich(String giaithich) {
		this.giaithich = giaithich;
	}

}
