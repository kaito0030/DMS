<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>新規文書登録</h1>

<div class="form-card">

    <form id="documentRegisterForm"
    		  action="${pageContext.request.contextPath}/document-register"
          method="post"
          enctype="multipart/form-data">

        <div class="form-row">
            <label>文書ID</label>
            <input type="text"
                   name="documentId"
                   value="${documentId}">

            <c:if test="${not empty documentIdError}">
                <div class="error-text">
                    ${documentIdError}
                </div>
            </c:if>
        </div>

        <div class="form-row">
            <label>文書タイトル</label>
            <input type="text"
                   name="title"
                   value="${title}">
        </div>

        <div class="form-row">
            <label>著者</label>
            <input type="text"
                   name="author"
                   value="${author}">
        </div>

        <div class="form-row">
            <label>発行年</label>
            <input type="text"
                   name="publishYear"
                   value="${publishYear}">
        </div>

        <div class="form-row">
            <label>アブストラクト</label>
            <textarea name="abstractText"
                      class="textarea-input">${abstractText}</textarea>
        </div>

        <div class="form-row">
            <label>PDFファイル</label>
            <input type="file"
                   name="pdfFile">
        </div>

        <div class="button-row">

            <button type="button"
                    class="primary-button"
                    onclick="openConfirmModal('documentRegisterForm')">
                登録
                
            </button>

            <a href="${pageContext.request.contextPath}/document-list">
                <button type="button"
                        class="secondary-button">
                    戻る
                </button>
            </a>

        </div>

    </form>

</div>
<div id="confirmModal" class="modal-overlay">

    <div class="document-confirm-modal-box">

        <h2>確認</h2>

        <div class="document-confirm-content">

            <div>
                <strong>文書ID：</strong>
                <span id="confirmDocumentId"></span>
            </div>

            <div>
                <strong>タイトル：</strong>
                <span id="confirmTitle"></span>
            </div>

            <div>
                <strong>著者：</strong>
                <span id="confirmAuthor"></span>
            </div>

            <div>
                <strong>発行年：</strong>
                <span id="confirmPublishYear"></span>
            </div>

            <div>
                <strong>アブストラクト：</strong>
                <span id="confirmAbstract"
                      class="confirm-abstract"></span>
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

        targetFormId = "documentRegisterForm";

        document.getElementById("confirmDocumentId").textContent =
            document.querySelector("input[name='documentId']").value;

        document.getElementById("confirmTitle").textContent =
            document.querySelector("input[name='title']").value;

        document.getElementById("confirmAuthor").textContent =
            document.querySelector("input[name='author']").value;

        document.getElementById("confirmPublishYear").textContent =
            document.querySelector("input[name='publishYear']").value;

        document.getElementById("confirmAbstract").textContent =
            document.querySelector("textarea[name='abstractText']").value;

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