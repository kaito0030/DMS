<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h1>${targetUserName}の閲覧可能文書</h1>

<div class="table-container">

	<table class="data-table">

		<thead>
			<tr>
				<th>文書ID</th>
				<th>タイトル</th>
				<th>著者</th>
				<th>停止</th>
			</tr>
		</thead>

		<tbody>

			<c:forEach var="document" items="${documentList}">

				<tr>

					<td>${document.documentId}</td>

					<td>${document.title}</td>

					<td>${document.author}</td>

					<td>

						<form action="${pageContext.request.contextPath}/permission-stop"
							method="post">

							<input type="hidden" name="userName" value="${targetUserName}">

							<input type="hidden" name="documentId"
								value="${document.documentId}">

							<button type="submit" class="danger-button">停止</button>

						</form>

					</td>

				</tr>

			</c:forEach>

		</tbody>

	</table>

</div>
<div class="bottom-area">
	<div class="bottom-left">


		<a href="${pageContext.request.contextPath}/user-list"
			class="secondary-button">戻る </a>

	</div>
	<div class="bottom-center">

		<c:if test="${currentPage > 1}">
			<a class="secondary-button"
				href="${pageContext.request.contextPath}/user-permission-list?userName=${targetUserName}&page=${currentPage - 1}">
				＜ </a>
		</c:if>

		<span class="page-info"> ${currentPage} / ${totalPages} </span>

		<c:if test="${currentPage < totalPages}">
			<a class="secondary-button"
				href="${pageContext.request.contextPath}/user-permission-list?userName=${targetUserName}&page=${currentPage + 1}">
				＞ </a>
		</c:if>

	</div>
</div>