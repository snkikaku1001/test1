package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ShohinDAO;
import model.data.ShohinParam;
import model.logic.ValueCheck;

/**
 * 商品更新画面のコントローラ
 */
@WebServlet("/ShohinUpdate")
public class ShohinUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Postリクエストを処理します。
	 * ①商品更新画面で「変更」ボタンが押された場合
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータから検索条件の取得
		request.setCharacterEncoding("UTF-8");
		String iShohinId = request.getParameter("h_shohinid");
		String iShohinMei = request.getParameter("i_shohinmei");
		String iBunrui = request.getParameter("i_bunrui");
		String iHanbaiTanka = request.getParameter("i_hanbaitanka");
		String iShiireTanka = request.getParameter("i_shiiretanka");

		// 画面入力値をリクエストスコープに保存
		ShohinParam shohinParam = new ShohinParam(iShohinId,
												iShohinMei,
												iBunrui,
												iHanbaiTanka,
												iShiireTanka);
		request.setAttribute("shohinParam", shohinParam);

		String message = "";	// レスポンス後に画面に表示する結果メッセージ

		// 入力値のチェック
		String errMsg = ValueCheck.checkUpdate(iShohinMei,
												iHanbaiTanka,
												iShiireTanka);

		// 入力値チェックに問題がなければ商品情報更新処理を行う
		if("".equals(errMsg)) {
			// 商品テーブルの商品情報を更新
			ShohinDAO shohinDAO = new ShohinDAO();
			int insCnt = shohinDAO.update(iShohinId, iShohinMei, iBunrui, iHanbaiTanka, iShiireTanka);

			// 更新結果から結果メッセージを設定
			if(insCnt == 1) {
				message = "更新が完了しました";
			} else {
				message = "更新処理時に問題が発生しました";
			}
		} else {
			// 入力値チェックに問題がある場合は、メッセージにエラーメッセージを設定
			message = errMsg;
		}

		// 結果messageをリクエストスコープに保存
		request.setAttribute("message", message);

		// 商品更新画面（自画面）へフォワード
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/WEB-INF/jsp/shohinUpdate.jsp");
		dispatcher.forward(request, response);
	}

}
