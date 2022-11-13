package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.data.Shohin;

/**
 * 「商品」テーブルへのアクセスを担当するDAOクラスです。
 */
public class ShohinDAO {
    private final String URL = "jdbc:postgresql://localhost:5432/shop";
    private final String USER = "postgres";
    private final String PASSWORD = "test";

	// コンストラクタ
	public ShohinDAO() {
		/* JDBCドライバの準備 */
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 「商品」テーブルから商品名と商品分類でデータを検索し、検索結果を返します。
	 */
    public List<Shohin> select(String shohinMei, String shohinBunrui) {
        List<Shohin> shohinList = null;

		/* 1) SQL文の準備 */
		String sql = "SELECT * ";
		sql += "FROM Shohin ";
		sql += "WHERE shohin_mei LIKE ? ";
		sql += "AND shohin_bunrui LIKE ? ";
		sql += "ORDER BY shohin_id;";

		/* 2) PostgreSQLへの接続 */
		try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement st = con.prepareStatement(sql);) {

			/* 3) SQL文の?部分を置き換え */
			st.setString(1, "%" + shohinMei + "%");
			st.setString(2, "%" + shohinBunrui + "%");

			/* 4) SQL文の実行 */
			ResultSet rs = st.executeQuery();

			/* 5) 結果をリストに移し替える */
			shohinList = makeShohinList(rs);

		} catch(Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}

		return shohinList;
    }

	/**
	 * 「商品」テーブルから商品IDでデータを検索し、検索結果を返します。
	 */
    public Shohin select(String shohinId) {
        List<Shohin> shohinList = null;

		/* 1) SQL文の準備 */
		String sql = "SELECT * ";
		sql += "FROM Shohin ";
		sql += "WHERE shohin_id = ? ";
		sql += "ORDER BY shohin_id;";

		/* 2) PostgreSQLへの接続 */
		try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement st = con.prepareStatement(sql);) {

			/* 3) SQL文の?部分を置き換え */
			st.setString(1, shohinId);

			/* 4) SQL文の実行 */
			ResultSet rs = st.executeQuery();

			/* 5) 結果をリストに移し替える */
			shohinList = makeShohinList(rs);

		} catch(Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}

		// 検索結果が1件の場合（主キーでの検索のため、結果は最大でも1件）
		// は検索結果をShohinRecordインスタンスとして返す。
		// 検索結果がない場合はnullを返す。
		if(shohinList != null && shohinList.size() == 1) {
        	return shohinList.get(0);
		} else {
			return null;
		}
	}

    /**
     * 「商品」テーブルに1件データを登録します。
     */
    public int insert(String shohinId,
    					String shohinMei,
    					String shohinBunrui,
    					String hanbaiTanka,
    					String shiireTanka) {
        int insCnt = 0;		// 更新件数

		/* 1) SQL文の準備 */
		String sql = "";
		sql = "INSERT INTO shohin(shohin_id, shohin_mei, shohin_bunrui, hanbai_tanka, shiire_tanka, torokubi) ";
		sql += "VALUES(?, ?, ?, ?, ?, ?);";

		/* 2) PostgreSQLへの接続 */
		try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement st = con.prepareStatement(sql);) {

			/* 3) SQL文の?部分を置き換え */
			st.setString(1, shohinId);
			st.setString(2, shohinMei);
			st.setString(3, shohinBunrui);
			if("".equals(hanbaiTanka)) {
				st.setInt(4, 0);
			} else {
				st.setInt(4, Integer.parseInt(hanbaiTanka));
			}
			if("".equals(shiireTanka)) {
				st.setInt(5, 0);
			} else {
				st.setInt(5, Integer.parseInt(shiireTanka));
			}
			st.setDate(6, new java.sql.Date(new Date().getTime()));

			/* 4) SQL文の実行 */
			insCnt = st.executeUpdate();

		} catch(Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}

		return insCnt;
    }

    /**
     * 「商品」テーブルから商品IDでデータを削除します。
     */
    public int delete(String id) {
        int delCnt = 0;		// 削除件数

		/* 1) SQL文の準備 */
		String sql = "";
		sql = "DELETE FROM Shohin ";
		sql += "WHERE shohin_id = ?;";

		/* 2) PostgreSQLへの接続 */
		try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement st = con.prepareStatement(sql);) {

			/* 3) SQL文の?部分を置き換え */
			st.setString(1, id);

			/* 4) SQL文の実行 */
			delCnt = st.executeUpdate();

		} catch(Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}

		return delCnt;
    }

    /**
     * 「商品」テーブルのデータを1件更新します。
     */
    public int update(String shohinId,
    					String shohinMei,
    					String shohinBunrui,
    					String hanbaiTanka,
    					String shiireTanka) {
        int insCnt = 0;		// 更新件数

		/* 1) SQL文の準備 */
		String sql = "";
		sql = "UPDATE shohin ";
		sql += "SET shohin_mei = ?, ";
		sql += "    shohin_bunrui = ?, ";
		sql += "    hanbai_tanka = ?, ";
		sql += "    shiire_tanka = ?, ";
		sql += "    torokubi = ? ";
		sql += "WHERE shohin_id = ?;";

		/* 2) PostgreSQLへの接続 */
		try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement st = con.prepareStatement(sql);) {

			/* 3) SQL文の?部分を置き換え */
			st.setString(1, shohinMei);
			st.setString(2, shohinBunrui);
			if("".equals(hanbaiTanka)) {
				st.setInt(3, 0);
			} else {
				st.setInt(3, Integer.parseInt(hanbaiTanka));
			}
			if("".equals(shiireTanka)) {
				st.setInt(4, 0);
			} else {
				st.setInt(4, Integer.parseInt(shiireTanka));
			}
			st.setDate(5, new java.sql.Date(new Date().getTime()));
			st.setString(6, shohinId);

			/* 4) SQL文の実行 */
			insCnt = st.executeUpdate();

		} catch(Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}

		return insCnt;
    }

    /**
     * 検索結果をリストで返します。
     */
    public ArrayList<Shohin> makeShohinList(ResultSet rs) throws Exception {

		// 全検索結果を格納するリストを準備
		ArrayList<Shohin> shohinList = new ArrayList<Shohin>();

		while(rs.next()) {
			// 1行分のデータを読込む
			String shohinId = rs.getString("shohin_id");
			String shohinMei = rs.getString("shohin_mei");
			String shohinBunrui = rs.getString("shohin_bunrui");
			int hanbaiTanka = rs.getInt("hanbai_tanka");
			int shiireTanka = rs.getInt("shiire_tanka");
			String torokubi = rs.getString("torokubi");

			// 1行分のデータを格納するインスタンス
			Shohin shohin = new Shohin(shohinId,
										shohinMei,
										shohinBunrui,
										hanbaiTanka,
										shiireTanka,
										torokubi);

			// リストに1行分のデータを追加する
			shohinList.add(shohin);
		}
		return shohinList;
	}
}