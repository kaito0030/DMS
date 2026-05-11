<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>許可登録画面</h1>

<div class="form-card">

    <form id="permissionRegisterForm"
          action="${pageContext.request.contextPath}/permission-register"
          method="post">

        <div class="form-row">
            <label>許可登録ユーザ</label>

            <div class="select-input-row">
                <button class="plus-button"
                        type="button"
                        onclick="openModal('userModal')">
                    ＋
                </button>

                <input type="text"
                       id="userNameInput"
                       name="userName"
                       value="${userName}"
                       placeholder="ユーザ名">
            </div>
        </div>

        <div class="form-row">
            <label>文書ID</label>

            <div class="select-input-row">
                <button class="plus-button"
                        type="button"
                        onclick="openModal('documentModal')">
                    ＋
                </button>

                <input type="text"
                       id="documentIdInput"
                       name="documentId"
                       value="${documentId}"
                       placeholder="文書ID">
            </div>
        </div>

        <div class="form-row">
            <label>処理区分</label>

            <div class="radio-row">
                <label>
                    <input type="radio"
                           name="permissionType"
                           value="allow"
                           checked>
                    許可
                </label>

                <label>
                    <input type="radio"
                           name="permissionType"
                           value="stop">
                    停止
                </label>
            </div>
        </div>

        <div class="button-row">
            <button type="button"
                    class="primary-button"
                    onclick="openConfirmModal('permissionRegisterForm')">
                登録
            </button>

        </div>

    </form>

</div>

<!-- ユーザ選択ポップアップ -->
<div id="userModal" class="modal-overlay">

    <div class="modal-box large-modal">

        <h2>ユーザ選択</h2>

        <div class="table-container popup-table">

            <table class="data-table">

                <thead>
                <tr>
                    <th>本名</th>
                    <th>ユーザ名</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="user" items="${userList}">
                    <tr class="selectable-row"
                        onclick="selectUser('${user.userName}')">
                        <td>${user.realName}</td>
                        <td>${user.userName}</td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>

        </div>

        <div class="modal-button-area">
            <button type="button"
                    class="secondary-button"
                    onclick="closeModal('userModal')">
                閉じる
            </button>
        </div>

    </div>

</div>

<!-- 文書選択ポップアップ -->
<div id="documentModal" class="modal-overlay">

    <div class="modal-box large-modal">

        <h2>文書選択</h2>

        <div class="table-container popup-table">

            <table class="data-table">

                <thead>
                <tr>
                    <th>文書タイトル</th>
                    <th>著者</th>
                    <th>文書ID</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="document" items="${documentList}">
                    <tr class="selectable-row"
                        onclick="selectDocument('${document.documentId}')">
                        <td>${document.title}</td>
                        <td>${document.author}</td>
                        <td>${document.documentId}</td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>

        </div>

        <div class="modal-button-area">
            <button type="button"
                    class="secondary-button"
                    onclick="closeModal('documentModal')">
                閉じる
            </button>
        </div>

    </div>

</div>
<div id="confirmModal" class="modal-overlay">

    <div class="confirm-modal-box">

        <h2>確認</h2>

        <div class="confirm-modal-content">

            <div>
                <strong>ユーザ名：</strong>
                <span id="confirmPermissionUser"></span>
            </div>

            <div>
                <strong>文書ID：</strong>
                <span id="confirmPermissionDocument"></span>
            </div>

        </div>

        <div class="modal-button-area">

            <button type="button"
                    class="primary-button"
                    onclick="submitConfirmedForm()">
                はい
            </button>

            <button type="button"
                    class="secondary-button"
                    onclick="closeConfirmModal()">
                いいえ
            </button>

        </div>

    </div>

</div>
<script>
    function openModal(id) {
        document.getElementById(id).classList.add("active");
    }

    function closeModal(id) {
        document.getElementById(id).classList.remove("active");
    }

    function selectUser(userName) {
        document.getElementById("userNameInput").value = userName;
        closeModal("userModal");
    }

    function selectDocument(documentId) {
        document.getElementById("documentIdInput").value = documentId;
        closeModal("documentModal");
    }
</script>
<<script>
    let targetFormId = null;

    function openConfirmModal() {

        targetFormId = "permissionRegisterForm";

        document.getElementById("confirmPermissionUser").textContent =
            document.querySelector("input[name='userName']").value;

        document.getElementById("confirmPermissionDocument").textContent =
            document.querySelector("input[name='documentId']").value;

        document.getElementById("confirmModal")
                .classList.add("active");
    }

    function closeConfirmModal() {
        document.getElementById("confirmModal")
                .classList.remove("active");
    }

    function submitConfirmedForm() {
        document.getElementById(targetFormId).submit();
    }
</script>