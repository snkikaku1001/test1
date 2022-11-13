package model.data;

import java.io.Serializable;

/**
 * 商品の検索条件を管理するJavaBeansクラス
 */
public class SearchCondition implements Serializable {
	String shohinMei = "";
	String shohinBunrui = "";

	public SearchCondition() {}

	public SearchCondition(String shohinMei, String shohinBunrui) {
		this.shohinMei = shohinMei;
		this.shohinBunrui = shohinBunrui;
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
}