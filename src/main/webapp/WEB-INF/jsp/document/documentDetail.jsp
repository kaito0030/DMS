<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<h1>文書詳細</h1>

<div class="detail-card">

	<div class="detail-row">
		<div class="detail-label">文書ID</div>
		<div class="detail-value">${document.documentId}</div>
	</div>

	<div class="detail-row">
		<div class="detail-label">文書タイトル</div>
		<div class="detail-value">${document.title}</div>
	</div>

	<div class="detail-row">
		<div class="detail-label">著者</div>
		<div class="detail-value">${document.author}</div>
	</div>

	<div class="detail-row">
		<div class="detail-label">発行年</div>
		<div class="detail-value">${document.publishYear}</div>
	</div>

	<div class="detail-row">
		<div class="detail-label">アブストラクト</div>
		<div class="detail-value">${document.abstractText}</div>
	</div>


	<div class="detail-button-row">
		<a
			href="${pageContext.request.contextPath}/document-pdf?documentId=${document.documentId}"
			target="_blank" class="pdf-button"> PDF </a>

		<c:if test="${sessionScope.loginUser.admin}">

			<a
				href="${pageContext.request.contextPath}/document-edit?documentId=${document.documentId}"
				class="edit-button"> 編集 </a>

		</c:if>
	</div>

</div>