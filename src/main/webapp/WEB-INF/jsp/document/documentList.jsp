<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h1>文書一覧</h1>

<form action="${pageContext.request.contextPath}/document-list"
	method="get" class="list-search-form">

	<select name="searchColumn" class="search-select">

		<option value="documentId"
			${searchColumn == 'documentId' ? 'selected' : ''}>文書ID</option>

		<option value="author" ${searchColumn == 'author' ? 'selected' : ''}>
			著者</option>

		<option value="title" ${searchColumn == 'title' ? 'selected' : ''}>
			タイトル</option>

	</select> <input type="text" name="keyword" class="search-input"
		value="${keyword}" placeholder="検索文字列">

	<button type="submit" class="primary-button">検索</button>

</form>

<div class="table-container">

	<table class="data-table">

		<thead>
			<tr>
				<th onclick="sortTable(0)" class="sortable-th">文書ID</th>

				<th onclick="sortTable(1)" class="sortable-th">文書タイトル</th>

				<th onclick="sortTable(2)" class="sortable-th">著者</th>

				<th onclick="sortTable(3)" class="sortable-th">発行年</th>
				<th>詳細</th>
				<th>許可登録</th>
			</tr>
		</thead>

		<tbody>

			<c:forEach var="document" items="${documentList}">
				<tr>
					<td>${document.documentId}</td>

					<td>${document.title}</td>

					<td>${document.author}</td>

					<td>${document.publishYear}</td>
					<td><a
						href="${pageContext.request.contextPath}/document-detail?documentId=${document.documentId}"
						class="detail-button"> 詳細 </a></td>

					<td><a
						href="${pageContext.request.contextPath}/permission-register?documentId=${document.documentId}"
						class="action-button"> 許可登録 </a></td>

				</tr>
			</c:forEach>

		</tbody>

	</table>

</div>

<div class="page-button-area">
	<a href="${pageContext.request.contextPath}/document-register"
		class="primary-button">新規文書登録 </a>
</div>

<script>
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

			// 発行年だけ数値比較
			if (columnIndex === 3) {

				const aNum = parseInt(aText, 10);
				const bNum = parseInt(bText, 10);

				if (direction === "asc") {
					return aNum - bNum;
				} else {
					return bNum - aNum;
				}
			}

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
</script>