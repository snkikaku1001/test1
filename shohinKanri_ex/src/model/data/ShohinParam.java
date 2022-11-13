package model.data;

import java.io.Serializable;

/**
 * 商品更新画面の入力データを現すJavaBeansクラス
 */
public class ShohinParam implements Serializable {

	private String shohinId = "";
	private String shohinMei = "";
	private String shohinBunrui = "";
	private String hanbaiTanka = "";
	private String shiireTanka = "";

	public ShohinParam() {}

	public ShohinParam(String shohinId, String shohinMei, String shohinBunrui, String hanbaiTanka, String shiireTanka) {
		this.shohinId = shohinId;
		this.shohinMei = shohinMei;
		this.shohinBunrui = shohinBunrui;
		this.hanbaiTanka = hanbaiTanka;
		this.shiireTanka = shiireTanka;
	}

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

	public String getHanbaiTanka() {
		return hanbaiTanka;
	}

	public void setHanbaiTanka(String hanbaiTanka) {
		this.hanbaiTanka = hanbaiTanka;
	}

	public String getShiireTanka() {
		return shiireTanka;
	}

	public void setShiireTanka(String shiireTanka) {
		this.shiireTanka = shiireTanka;
	}
}
