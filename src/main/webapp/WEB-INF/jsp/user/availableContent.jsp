<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h1>閲覧可能コンテンツ一覧</h1>

<div class="table-container">

	<table class="data-table">

		<thead>
			<tr>
				<th>文書ID</th>
				<th>文書タイトル</th>
				<th>著者</th>
				<th>発行年</th>
				<th>詳細</th>
				<th>PDF</th>
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
						href="${pageContext.request.contextPath}/document-pdf?documentId=${document.documentId}"
						target="_blank" class="pdf-button"> 開く </a></td>
				</tr>
			</c:forEach>

		</tbody>

	</table>
	
</div>
<div class="pagination-area">

		<c:if test="${currentPage > 1}">
			<a class="secondary-button"
				href="${pageContext.request.contextPath}/available-content?page=${currentPage - 1}">
				＜ </a>
		</c:if>

		<span class="page-info"> ${currentPage} / ${totalPages} </span>

		<c:if test="${currentPage < totalPages}">
			<a class="secondary-button"
				href="${pageContext.request.contextPath}/available-content?page=${currentPage + 1}">
				＞ </a>
		</c:if>

	</div>