<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>新規ユーザ登録</h1>

<div class="form-card">

    <form id="userRegisterForm"
          action="${pageContext.request.contextPath}/user-register"
          method="post">

        <div class="form-row">
            <label>本名</label>
            <input type="text"
                   name="realName"
                   value="${realName}">
        </div>

        <div class="form-row">
            <label>ユーザ名</label>
            <input type="text"
                   name="userName"
                   value="${userName}">

            <c:if test="${not empty userNameError}">
                <div class="error-text">
                    ${userNameError}
                </div>
            </c:if>
        </div>

        <div class="form-row">
            <label>パスワード</label>
            <input type="password"
                   name="password">
        </div>

        <div class="form-row">
            <label>パスワード確認</label>
            <input type="password"
                   name="passwordConfirm">

            <c:if test="${not empty passwordError}">
                <div class="error-text">
                    ${passwordError}
                </div>
            </c:if>
        </div>
        <div class="form-row">
            <label>管理者権限</label>

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
                    onclick="openConfirmModal('userRegisterForm')">
                    
                登録
            </button>

            <a href="${pageContext.request.contextPath}/user-list">
                <button type="button"
                        class="secondary-button">
                    戻る
                </button>
            </a>

        </div>

    </form>

</div>
<div id="confirmModal" class="modal-overlay">

    <div class="confirm-modal-box">

        <h2>確認</h2>

        <div class="confirm-modal-content">

            <div>
                <strong>ユーザ名：</strong>
                <span id="confirmUserName"></span>
            </div>

            <div>
                <strong>名前：</strong>
                <span id="confirmRealName"></span>
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
    let targetFormId = null;

    function openConfirmModal() {

        targetFormId = "userRegisterForm";

        document.getElementById("confirmUserName").textContent =
            document.querySelector("input[name='userName']").value;

        document.getElementById("confirmRealName").textContent =
            document.querySelector("input[name='realName']").value;

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