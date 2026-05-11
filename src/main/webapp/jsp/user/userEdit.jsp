<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h1>ユーザ編集</h1>

<div class="form-card">

	<form action="${pageContext.request.contextPath}/user-edit"
		method="post">

		<input type="hidden" name="oldUserName" value="${user.userName}">

		<div class="form-row">
			<label>本名</label> <input type="text" name="realName"
				value="${user.realName}">
		</div>

		<div class="form-row">
			<label>ユーザ名</label> <input type="text" name="userName"
				value="${user.userName}">

			<c:if test="${not empty userNameError}">
				<div class="error-text">${userNameError}</div>
			</c:if>
		</div>

		<div class="form-row">
			<label>新パスワード</label> <input type="password" name="password">
		</div>
		<c:if test="${not empty passwordRuleError}">
			<div class="error-message">${passwordRuleError}</div>
		</c:if>
		<div class="form-row">
			<label>新パスワード確認</label> <input type="password" name="passwordConfirm">

			<c:if test="${not empty passwordError}">
				<div class="error-text">${passwordError}</div>
			</c:if>
		</div>
		<div class="form-row">

			<label>管理者権限</label>

			<div class="radio-row">

				<label> <input type="radio" name="admin" value="true"
					${user.admin ? "checked" : ""}> 有
				</label> <label> <input type="radio" name="admin" value="false"
					${!user.admin ? "checked" : ""}> 無
				</label>

			</div>

		</div>
		<div class="button-row">

			<button type="submit" class="primary-button">登録</button>

			<button type="submit" name="action" value="delete"
				class="danger-button">削除</button>

			<a href="${pageContext.request.contextPath}/user-list">
				<button type="button" class="secondary-button">戻る</button>
			</a>

		</div>

	</form>

</div>