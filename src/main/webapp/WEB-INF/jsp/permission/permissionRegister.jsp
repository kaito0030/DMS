<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h1>許可登録画面</h1>

<div class="form-card">

	<form id="permissionRegisterForm"
		action="${pageContext.request.contextPath}/permission-register"
		method="post">

		<div class="form-row">
			<label>許可登録ユーザ</label>

			<div class="select-input-row">
				<button class="plus-button" type="button" id="openUserModalButton">＋</button>

				<input type="text" id="userNameInput" name="userName"
					value="${userName}" placeholder="ユーザ名">
			</div>
		</div>

		<div class="form-row">
			<label>文書ID</label>

			<div class="select-input-row">
				<button class="plus-button" type="button"
					id="openDocumentModalButton">＋</button>

				<input type="text" id="documentIdInput" name="documentId"
					value="${documentId}" placeholder="文書ID">
			</div>
		</div>

		<div class="form-row">
			<label>処理区分</label>

			<div class="radio-row">
				<label> <input type="radio" name="permissionType"
					value="allow" checked> 許可
				</label> <label> <input type="radio" name="permissionType"
					value="stop"> 停止
				</label>
			</div>
		</div>

		<div class="button-row">
			<button type="button" class="primary-button"
				class="openConfirmModalButton">登録</button>

		</div>

	</form>

</div>

<div id="userModal" class="modal-overlay">

	<div class="large-modal">

		<h2>ユーザ選択</h2>

		<div class="popup-search-area">
			<select id="userApiSearchColumn" class="search-select">
				<option value="realName">本名</option>
				<option value="userName">ユーザID</option>
			</select> <input type="text" id="userApiKeyword" class="search-input"
				placeholder="検索文字列">

			<button type="button" class="primary-button" id="searchUserButton">
				検索</button>
		</div>

		<div class="popup-table">

			<table class="data-table">
				<thead>
					<tr>
						<th>本名</th>
						<th>ユーザID</th>
					</tr>
				</thead>

				<tbody id="userModalBody">
				</tbody>
			</table>

		</div>

		<div class="modal-bottom-area">

			<div class="modal-pagination-area">
				<button type="button" class="secondary-button"
					id="backUserPageButton">＜</button>

				<span id="userPageInfo" class="page-info"></span>

				<button type="button" class="secondary-button"
					id="nextUserPageButton">＞</button>
			</div>

			<button type="button" class="secondary-button"
				id="closeUserModalButton">閉じる</button>

		</div>

	</div>

</div>
<!-- 文書選択ポップアップ -->
<div id="documentModal" class="modal-overlay">

	<div class="large-modal">

		<h2>文書選択</h2>

		<div class="popup-search-area">
			<select id="documentApiSearchColumn" class="search-select">
				<option value="title">タイトル</option>
				<option value="author">著者</option>
				<option value="documentId">文書ID</option>
			</select> <input type="text" id="documentApiKeyword" class="search-input"
				placeholder="検索文字列">

			<button type="button" class="primary-button"
				id="searchDocumentButton">検索</button>
		</div>

		<div class="popup-table">

			<table class="data-table">
				<thead>
					<tr>
						<th>文書タイトル</th>
						<th>著者</th>
						<th>文書ID</th>
					</tr>
				</thead>

				<tbody id="documentModalBody">
				</tbody>
			</table>

		</div>

		<div class="modal-bottom-area">

			<div class="modal-pagination-area">
				<button type="button" class="secondary-button"
					id="backDocumentPageButton">＜</button>

				<span id="documentPageInfo" class="page-info"></span>

				<button type="button" class="secondary-button"
					id="nextDocumentPageButton">＞</button>
			</div>

			<button type="button" class="secondary-button"
				id="closeDocumentModalButton">閉じる</button>

		</div>

	</div>

</div>

<div id="confirmModal" class="modal-overlay">

	<div class="confirm-modal-box">

		<h2>確認</h2>

		<div class="confirm-modal-content">

			<div>
				<strong>ユーザ名：</strong> <span id="confirmPermissionUser"></span>
			</div>

			<div>
				<strong>文書ID：</strong> <span id="confirmPermissionDocument"></span>
			</div>

		</div>

		<div class="modal-button-area">

			<button type="button" class="primary-button" id="confirmSubmitButton">はい</button>

			<button type="button" class="secondary-button"
				id="confirmCancelButton">いいえ</button>

		</div>

	</div>

</div>
<script>
	const contextPath = "${pageContext.request.contextPath}";
</script>

<script src="${pageContext.request.contextPath}/js/permissionModal.js"></script>