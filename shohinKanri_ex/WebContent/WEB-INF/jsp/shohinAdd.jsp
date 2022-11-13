<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 結果メッセージをリクエストスコープから取得
String message = (String)request.getAttribute("message");
if(message == null) {
	message = "";
}

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品追加</title>
</head>
<body>
<h1>商品追加</h1>
<%= message %>
<form action="/shohinKanri_ex/ShohinAdd" method="post">
商品ID：<input type="text" name="i_shohinid"><br>
商品名：<input type="text" name="i_shohinmei"><br>
商品分類：
<input type="radio" name="i_bunrui" value="衣服">衣服
<input type="radio" name="i_bunrui" value="キッチン用品">キッチン用品
<input type="radio" name="i_bunrui" value="事務用品">事務用品<br>
販売単価：<input type="text" name="i_hanbaitanka"><br>
仕入単価：<input type="text" name="i_shiiretanka"><br>
<br>
<input type="submit" value="登録">
</form>
<a href="/shohinKanri_ex/ShohinSearch">戻る</a>
</body>
</html>