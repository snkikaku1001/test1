<%@page import="model.data.SearchCondition"%>
<%@page import="model.data.Shohin"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
List<Shohin> shohinList = (List<Shohin>)request.getAttribute("shohinList");

SearchCondition sc = (SearchCondition)session.getAttribute("SearchCondition");
if(sc == null) {
	sc = new SearchCondition();
}

String[] selectBunrui = {"", "", ""};
if("衣服".equals(sc.getShohinBunrui())) {
	selectBunrui[0] = "selected";
} else if("キッチン用品".equals(sc.getShohinBunrui())) {
	selectBunrui[1] = "selected";
} else if("事務用品".equals(sc.getShohinBunrui())) {
	selectBunrui[2] = "selected";
}


//結果メッセージをリクエストスコープから取得
String message = (String)request.getAttribute("message");
if(message == null) {
	message = "";
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品検索</title>
</head>
<body>
<h1>商品検索</h1>
<%=message%>
<form action="/shohinKanri_ex/ShohinSearch" method="get">
<table border="1">
	<tr>
		<th>商品名</th>
		<th><input type="text" name="s_syohinmei" value="<%=sc.getShohinMei()%>"></th>
		<th>商品分類</th>
		<th>
			<select name="s_bunrui">
				<option value="" ></option>
				<option value="衣服" <%=selectBunrui[0]%>>衣服</option>
				<option value="キッチン用品" <%=selectBunrui[1]%>>キッチン用品</option>
				<option value="事務用品" <%=selectBunrui[2]%>>事務用品</option>
			</select>
		</th>
	</tr>
</table>
<input type="submit" value="検索">
<input type="hidden" name="action" value="search">
</form>
<br>
<br>
<form action="/shohinKanri_ex/ShohinSearch" method="post">
<table border="1">
	<tr>
		<th>選択</th>
		<th>商品ID</th>
		<th>商品名</th>
		<th>商品分類</th>
		<th>販売単価</th>
		<th>仕入単価</th>
	</tr>
<%
if(shohinList != null) {
	for(Shohin shohin : shohinList) {
%>
	<tr>
		<td><input type="radio" name="select" value="<%= shohin.getShohinId() %>"></td>
		<td><%= shohin.getShohinId() %></td>
		<td><%= shohin.getShohinMei() %></td>
		<td><%= shohin.getShohinBunrui() %></td>
		<td><%= shohin.getHanbaiTanka() %></td>
		<td><%= shohin.getShiireTanka() %></td>
	</tr>
<%  }
}%>
</table>
<a href="/shohinKanri_ex/ShohinAdd">追加</a>
<% if(shohinList != null && shohinList.size() != 0) { %>
	<input type="submit" name="btn_edit" value="更新">
	<input type="submit" name="btn_edit" value="削除">
<% } %>
<input type="submit" name="btn_edit" value="CSV全件出力">
</form>
<br>
</body>
</html>