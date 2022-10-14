<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// アプリ起動時に、前回の検索結果がセッションスコープに残っていれば削除しておく。
// （ブラウザを閉じずに、再度アクセスされた時の対策処理）
session.removeAttribute("SearchCondition");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="refresh" content="0; URL=/shohinKanri_ex/ShohinSearch">
<title></title>
</head>
<body></body>
</html>