package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ShohinDAO;
import model.logic.ValueCheck;

/**
 * 商品追加画面のコントローラ
 */
@WebServlet("/ShohinAdd")
public class ShohinAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Getリクエストを処理します。
	 * ①商品検索画面から「追加」リンクから遷移してきた場合
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 商品検索画面へフォワード
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/WEB-INF/jsp/shohinAdd.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Postリクエストを処理します。
	 * ①商品追加画面で「登録」ボタンが押された場合
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータから検索条件の取得
		request.setCharacterEncoding("UTF-8");
		String iShohinId = request.getParameter("i_shohinid");
		String iShohinMei = request.getParameter("i_shohinmei");
		String iBunrui = request.getParameter("i_bunrui");
		String iHanbaiTanka = request.getParameter("i_hanbaitanka");
		String iShiireTanka = request.getParameter("i_shiiretanka");

		String message = "";	// レスポンス後に画面に表示する結果メッセージ

		// 入力値のチェック
		String errMsg = ValueCheck.checkAdd(iShohinId,
											iShohinMei,
											iBunrui,
											iHanbaiTanka,
											iShiireTanka);

		// 入力値チェックに問題がなければ商品登録処理を行う
		if("".equals(errMsg)) {
			// 商品テーブルへ商品情報を登録
			ShohinDAO shohinDAO = new ShohinDAO();
			int insCnt = shohinDAO.insert(iShohinId, iShohinMei, iBunrui, iHanbaiTanka, iShiireTanka);

			// 登録結果から結果メッセージを設定
			if(insCnt == 1) {
				message = "登録が完了しました";
			} else {
				message = "登録処理時に問題が発生しました";
			}
		} else {
			// 入力値チェックに問題がある場合は、メッセージにエラーメッセージを設定
			message = errMsg;
		}

		// 結果messageをリクエストスコープに保存
		request.setAttribute("message", message);

		// 商品追加画面（自画面）へフォワード
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/WEB-INF/jsp/shohinAdd.jsp");
		dispatcher.forward(request, response);
	}

}
