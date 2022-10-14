package model.logic;

import java.util.List;

import dao.ShohinDAO;
import model.data.Shohin;

/**
 * 商品CSVを表したクラス
 *
 */
public class ShohinCsv {
	public String getCsvData() {
		StringBuffer sb = new StringBuffer();

		// 商品テーブルから、全商品データを検索
		ShohinDAO shohinDAO = new ShohinDAO();
		List<Shohin> shohinList = shohinDAO.select("", "");

		// タイトル行を追加
		sb.append("商品ID,商品名,商品分類,販売単価,仕入単価");

		// 検索結果からCSV形式の文字列を生成し、追加
		for (int i = 0; i < shohinList.size(); i++) {
			sb.append("\n");

			sb.append(shohinList.get(i).getShohinId());
			sb.append(",");
			sb.append(shohinList.get(i).getShohinMei());
			sb.append(",");
			sb.append(shohinList.get(i).getShohinBunrui());
			sb.append(",");
			sb.append(shohinList.get(i).getHanbaiTanka());
			sb.append(",");
			sb.append(shohinList.get(i).getShiireTanka());
		}

		return sb.toString();
	}
}
