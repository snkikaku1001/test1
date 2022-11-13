package model.data;

import java.io.Serializable;

/**
 * Shohinテーブルの1レコードを表すJavaBeans
 */
public class Shohin implements Serializable {

	private String shohinId;
	private String shohinMei;
	private String shohinBunrui;
	private int hanbaiTanka;
	private int shiireTanka;
	private String torokubi;

	public Shohin() {}

	public Shohin(String shohinId, String shohinMei, String shohinBunrui, int hanbaiTanka, int shiireTanka, String torokubi) {
		this.shohinId = shohinId;
		this.shohinMei = shohinMei;
		this.shohinBunrui = shohinBunrui;
		this.hanbaiTanka = hanbaiTanka;
		this.shiireTanka = shiireTanka;
		this.torokubi = torokubi;
	}

	// getter、setter
	public String getShohinId() {
		return shohinId;
	}
	public void setShohinId(String shohinId) {
		this.shohinId = shohinId;
	}
	public String getShohinMei() {
		return shohinMei;
	}
	public void setShohinMei(String shohinMei) {
		this.shohinMei = shohinMei;
	}
	public String getShohinBunrui() {
		return shohinBunrui;
	}
	public void setShohinBunrui(String shohinBunrui) {
		this.shohinBunrui = shohinBunrui;
	}
	public int getHanbaiTanka() {
		return hanbaiTanka;
	}
	public void setHanbaiTanka(int hanbaiTanka) {
		this.hanbaiTanka = hanbaiTanka;
	}
	public int getShiireTanka() {
		return shiireTanka;
	}
	public void setShiireTanka(int shiireTanka) {
		this.shiireTanka = shiireTanka;
	}
	public String getTorokubi() {
		return torokubi;
	}
	public void setTorokubi(String torokubi) {
		this.torokubi = torokubi;
	}
}
