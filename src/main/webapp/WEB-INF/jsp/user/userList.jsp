<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h1>ユーザ一覧</h1>

<form action="${pageContext.request.contextPath}/user-list" method="get"
	class="list-search-form">

	<select name="searchColumn" id="userSearchColumn" class="search-select"
		onchange="changeUserSearchInput()">

		<option value="userName"
			${searchColumn == 'userName' ? 'selected' : ''}>ユーザID</option>

		<option value="realName"
			${searchColumn == 'realName' ? 'selected' : ''}>名前</option>

		<option value="userType"
			${searchColumn == 'userType' ? 'selected' : ''}>ユーザ区分</option>

	</select> <input type="text" name="keyword" id="userKeywordInput"
		class="search-input" value="${keyword}" placeholder="検索文字列"> <select
		name="keyword" id="userTypeSelect" class="search-input" disabled
		style="display: none;">

		<option value="">選択してください</option>

		<option value="admin" ${keyword == 'admin' ? 'selected' : ''}>
			管理ユーザ</option>

		<option value="general" ${keyword == 'general' ? 'selected' : ''}>
			一般ユーザ</option>

	</select>

	<button type="submit" class="primary-button">検索</button>

</form>

<div class="table-container">

	<table class="data-table">

		<thead>
			<tr>
				<th onclick="sortTable(0)" class="sortable-th">ユーザID</th>

				<th onclick="sortTable(1)" class="sortable-th">名前</th>

				<th onclick="sortTable(2)" class="sortable-th">ユーザ区分</th>

				<th>許可登録</th>
				<th>閲覧可能文書</th>
				<th>編集</th>
			</tr>
		</thead>

		<tbody>

			<c:forEach var="user" items="${userList}">
				<tr>
					<td>${user.userName}</td>

					<td>${user.realName}</td>

					<td><c:choose>
							<c:when test="${user.admin}">
                            管理ユーザ
                        </c:when>
							<c:otherwise>
                            一般ユーザ
                        </c:otherwise>
						</c:choose></td>

					<td><a
						href="${pageContext.request.contextPath}/permission-register?userName=${user.userName}"
						class="action-button"> 許可登録 </a></td>

					<td><a
						href="${pageContext.request.contextPath}/user-permission-list?userName=${user.userName}"
						class="detail-button"> 閲覧可能文書 </a></td>

					<td><a
						href="${pageContext.request.contextPath}/user-edit?userName=${user.userName}"
						class="edit-button"> 編集 </a></td>
				</tr>
			</c:forEach>

		</tbody>

	</table>

</div>

<div class="page-button-area">
	<a href="${pageContext.request.contextPath}/user-register"
		class="primary-button"> 新規ユーザ登録 </a>
</div>

<script>
	function changeUserSearchInput() {

		const column = document.getElementById("userSearchColumn").value;
		const textInput = document.getElementById("userKeywordInput");
		const typeSelect = document.getElementById("userTypeSelect");

		if (column === "userType") {

			textInput.disabled = true;
			textInput.style.display = "none";

			typeSelect.disabled = false;
			typeSelect.style.display = "inline-block";

		} else {

			textInput.disabled = false;
			textInput.style.display = "inline-block";

			typeSelect.disabled = true;
			typeSelect.style.display = "none";
		}
	}

	let sortDirection = {};

	function sortTable(columnIndex) {

		const table = document.querySelector(".data-table");
		const tbody = table.querySelector("tbody");
		const rows = Array.from(tbody.querySelectorAll("tr"));

		const direction = sortDirection[columnIndex] === "asc" ? "desc" : "asc";

		sortDirection[columnIndex] = direction;

		rows.sort(function(a, b) {

			const aText = a.children[columnIndex].textContent.trim();

			const bText = b.children[columnIndex].textContent.trim();

			if (direction === "asc") {
				return aText.localeCompare(bText, "ja");
			} else {
				return bText.localeCompare(aText, "ja");
			}
		});

		rows.forEach(function(row) {
			tbody.appendChild(row);
		});
	}

	window.addEventListener("load", changeUserSearchInput);
</script>