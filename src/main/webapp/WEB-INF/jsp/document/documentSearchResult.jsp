<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h1>文書検索結果</h1>

<div class="search-condition">
	検索キーワード： <strong>${keyword}</strong>
</div>

<div class="table-container">

	<table class="data-table">

		<thead>
			<tr>
				<th>文書タイトル</th>
				<th>著者</th>
				<th>詳細</th>
			</tr>
		</thead>

		<tbody>

			<c:forEach var="document" items="${documentList}">
				<tr>
					<td>${document.title}</td>
					<td>${document.author}</td>
					<td><a
						href="${pageContext.request.contextPath}/document-detail?documentId=${document.documentId}"
						class="detail-button"> 詳細 </a></td>
				</tr>
			</c:forEach>

		</tbody>

	</table>

</div>
<div class="bottom-area">
	<div class="bottom-left">
		<a href="${pageContext.request.contextPath}/document-search"
			class="secondary-button"> 検索画面へ戻る </a>
	</div>
	<div class="bottom-center">

		<c:if test="${currentPage > 1}">
			<a class="secondary-button"
				href="${pageContext.request.contextPath}/document-search-result?searchColumn=${searchColumn}&keyword=${keyword}&page=${currentPage - 1}">
				＜ </a>
		</c:if>

		<span class="page-info"> ${currentPage} / ${totalPages} </span>

		<c:if test="${currentPage < totalPages}">
			<a class="secondary-button"
				href="${pageContext.request.contextPath}/document-search-result?searchColumn=${searchColumn}&keyword=${keyword}&page=${currentPage + 1}">
				＞ </a>
		</c:if>

	</div>
</div>
