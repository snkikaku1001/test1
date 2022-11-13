package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ShohinDAO;
import model.data.SearchCondition;
import model.data.Shohin;
import model.data.ShohinParam;
import model.logic.ShohinCsv;

/**
 * 商品検索画面のコントローラ
 */
@WebServlet("/ShohinSearch")
public class ShohinSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Getリクエストを処理します。
	 * ①ブラウザからURLを入力され、index.jspから転送されてきた場合
	 * ②別画面から「戻る」リンクで戻ってきた場合
	 * ③「検索」ボタンを押された時の処理
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータより処理種別を取得
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		// 処理種別がsearch（検索）の場合、検索処理を実施
		if("search".equals(action)) {
			// リクエストパラメータから検索条件の取得
			String sSyohinMei = request.getParameter("s_syohinmei");
			String sBunrui = request.getParameter("s_bunrui");

			// 検索条件をセッションスコープに保存
			SearchCondition sc = new SearchCondition(sSyohinMei, sBunrui);
			HttpSession session = request.getSession();
			session.setAttribute("SearchCondition", sc);
		}

		// 商品検索（もしくは再検索）処理
		search(request);

		// 商品検索画面へフォワード
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/WEB-INF/jsp/shohinSearch.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Postリクエストを処理します。
	 * ①「更新」ボタンを押された時の処理
	 * ②「削除」ボタンを押された時の処理
	 * ③「CSV全件出力」ボタンを押された時の処理
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = "";	// レスポンス後に画面に表示する結果メッセージ
		String forwardPath = "";

		// リクエストパラメータより、押されたボタンのvalue値を取得
		request.setCharacterEncoding("UTF-8");
		String submitName = request.getParameter("btn_edit");

		// 押されたボタンにより処理を分岐
		if("更新".equals(submitName)) {
			// リクエストパラメータより、削除対象のidを取得
			String id = request.getParameter("select");

			if(id == null) {
				// 結果メッセージを設定
				message = "商品を選択してください";

				// 商品の再検索を行う
				search(request);

				// 商品検索画面（自画面）へフォワードするためのパスを準備
				forwardPath = "/WEB-INF/jsp/shohinSearch.jsp";

			} else {
				// 更新対象の商品情報を商品テーブルから検索
				ShohinDAO shohinDAO = new ShohinDAO();
				Shohin shohinRecord = shohinDAO.select(id);
				ShohinParam shohinParam = new ShohinParam(shohinRecord.getShohinId(),
														shohinRecord.getShohinMei(),
														shohinRecord.getShohinBunrui(),
														String.valueOf(shohinRecord.getHanbaiTanka()),
														String.valueOf(shohinRecord.getShiireTanka()));


				// 次画面（商品更新画面）で使用する更新対象の商品情報をリクエストスコープに保存
				// （主キーで検索しているため、検索結果は確実に1件となる）
				request.setAttribute("shohinParam", shohinParam);

				forwardPath = "/WEB-INF/jsp/shohinUpdate.jsp";
			}
		} else if("削除".equals(submitName)) {
			String id = request.getParameter("select");

			// 検索条件に該当する商品情報を商品テーブルから検索
			ShohinDAO shohinDAO = new ShohinDAO();
			int delCnt = shohinDAO.delete(id);

			// 削除結果から結果メッセージを設定
			if(delCnt == 1) {
				message = "削除が完了しました";
			}

			// 商品の再検索を行う
			search(request);

			// 商品検索画面（自画面）へフォワードするためのパスを準備
			forwardPath = "/WEB-INF/jsp/shohinSearch.jsp";

		} else if("CSV全件出力".equals(submitName)) {
			ShohinCsv shohinCsv = new ShohinCsv();
			String csvData = shohinCsv.getCsvData();

			// CSV情報をレスポンスに書き出し。
			// （ContentTypeヘッダをtext/csvとすることで、ブラウザは
			//   送り返されるレスポンス情報を、	HTMLではなくCSVであると認識する）
			response.setContentType("text/csv; charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=shohin.csv");
			PrintWriter out = response.getWriter();
			out.print(csvData);

			// 商品の再検索を行う
			search(request);
		}

		// 結果メッセージをリクエストスコープに保存
		request.setAttribute("message", message);

		// フォワード処理
		if(!"".equals(forwardPath)) {
			RequestDispatcher dispatcher =
					request.getRequestDispatcher(forwardPath);
			dispatcher.forward(request, response);
		}
	}

	/**
	 * 検索結果リスト表示用のデータを準備します。
	 * （セッションスコープに保存されている検索条件を使用し商品を検索し、
	 * 検索結果をリクエストスコープに保存します。）
	 */
	public void search(HttpServletRequest request) {
		// 検索条件をセッションスコープから取得
		HttpSession session = request.getSession();
		SearchCondition sc = (SearchCondition)session.getAttribute("SearchCondition");

		// セッションスコープから検索条件を取得できた場合は、検索処理を実行する
		if(sc != null) {
			// 検索条件に該当する商品情報を商品テーブルから検索
			ShohinDAO shohinDAO = new ShohinDAO();
			List<Shohin> shohinList = shohinDAO.select(sc.getShohinMei(), sc.getShohinBunrui());

			// 検索結果をリクエストスコープに保存
			request.setAttribute("shohinList", shohinList);
		}
	}
}
