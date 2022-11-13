package model.logic;

/**
 * 画面入力値のチェックを行うモデル
 */
public class ValueCheck {

	/**
	 * 登録処理時の入力値チェックを行います。
	 * このメソッドはチェック結果に応じて以下の内容を返します。
	 * 問題なし：空文字
	 * 問題あり：エラーメッセージ
	 */
	public static String checkAdd(String shohinId,
									String shohinMei,
									String shohinBunrui,
									String hanbaiTanka,
									String shiireTanka) {
		String errMessage = "";

		// 必須項目チェック
		if("".equals(shohinId)) {
			errMessage += "商品IDは必須項目です。<br>";
		}

		if("".equals(shohinMei)) {
			errMessage += "商品名は必須項目です。<br>";
		}

		if(shohinBunrui == null) {
			errMessage += "商品分類は必須項目です。<br>";
		}

		// 文字数チェック
		if(shohinId.length() > 4) {
			errMessage += "商品IDは4文字以下で入力してください。<br>";
		}

		// 整数チェック
		if(!isConvertInt(hanbaiTanka)) {
			errMessage += "販売単価には整数を入力してください。<br>";
		}

		if(!isConvertInt(shiireTanka)) {
			errMessage += "仕入単価には整数を入力してください。<br>";
		}

		return errMessage;
	}

	/**
	 * 更新処理時の入力値チェックを行います。
	 * このメソッドはチェック結果に応じて以下の内容を返します。
	 * 問題なし：空文字
	 * 問題あり：エラーメッセージ
	 */
	public static String checkUpdate(String shohinMei,
									String hanbaiTanka,
									String shiireTanka) {
		// 商品IDのチェックは不要なため、ダミー値を渡して値をチェック。
		return checkAdd("0000", shohinMei, "dummy", hanbaiTanka, shiireTanka);

	}

	/**
	 * 渡された文字列がint型に変換可能かチェックします。
	 * このメソッドはチェック結果に応じて以下の内容を返します。
	 * 空文字、または変換可能	：true
	 * 変換不可能				：false
	 */
	public static boolean isConvertInt(String val) {

		if(!"".equals(val)) {
			try {
				Integer.parseInt(val);
			} catch(NumberFormatException e) {
				return false;
			}
		}

		return true;
	}
}
