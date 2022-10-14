<%@page import="model.data.ShohinParam"%>
<%@page import="model.data.Shohin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
ShohinParam shohinParam = (ShohinParam)request.getAttribute("shohinParam");

// 結果メッセージをリクエストスコープから取得
String message = (String)request.getAttribute("message");
if(message == null) {
	message = "";
}

String[] selectBunrui = {"", "", ""};
if(shohinParam != null ) {
	if("衣服".equals(shohinParam.getShohinBunrui())) {
		selectBunrui[0] = "checked";
	} else if("キッチン用品".equals(shohinParam.getShohinBunrui())) {
		selectBunrui[1] = "checked";
	} else if("事務用品".equals(shohinParam.getShohinBunrui())) {
		selectBunrui[2] = "checked";
	}
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品更新</title>
</head>
<body>
<h1>商品更新</h1>
<%= message %>
<form action="/shohinKanri_ex/ShohinUpdate" method="post">
商品ID：<input type="text" name="i_shohinid" value="<%= shohinParam.getShohinId() %>" disabled="disabled"><br>
商品名：<input type="text" name="i_shohinmei" value="<%= shohinParam.getShohinMei() %>"><br>
商品分類：
<input type="radio" name="i_bunrui" value="衣服" <%= selectBunrui[0] %>>衣服
<input type="radio" name="i_bunrui" value="キッチン用品" <%= selectBunrui[1] %>>キッチン用品
<input type="radio" name="i_bunrui" value="事務用品" <%= selectBunrui[2] %>>事務用品<br>
販売単価：<input type="text" name="i_hanbaitanka" value="<%= shohinParam.getHanbaiTanka() %>"><br>
仕入単価：<input type="text" name="i_shiiretanka" value="<%= shohinParam.getShiireTanka() %>"><br>

<%-- disabledで無効化されているテキストボックスの値はリクエストパラメータ
     で送られないため、代わりにhiddenパラメータで商品IDを送る --%>
<input type="hidden" name="h_shohinid" value="<%= shohinParam.getShohinId() %>">
<br>
<input type="submit" value="更新">
</form>
<a href="/shohinKanri_ex/ShohinSearch">戻る</a>
</body>
</html>