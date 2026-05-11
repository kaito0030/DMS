<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<title>ログイン</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="login-body">

	<div class="login-box">

		<h1 class="login-title">文書管理システム</h1>

		<c:if test="${not empty errorMessage}">
			<div class="error-text">${errorMessage}</div>
		</c:if>

		<form action="${pageContext.request.contextPath}/login" method="post">

			<div class="input-group">
				<label>ユーザ名</label> <input type="text" name="userName">
			</div>

			<div class="input-group">
				<label>パスワード</label> <input type="password" name="password">
			</div>

			<button type="submit" class="login-button">ログイン</button>

		</form>

	</div>

</body>
</html>