<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>文書編集</h1>

<div class="form-card">

    <form action="${pageContext.request.contextPath}/document-edit"
          method="post"
          enctype="multipart/form-data">

        <div class="form-row">
            <label>文書ID</label>
            <input type="text"
                   name="documentId"
                   value="${document.documentId}"
                   readonly>
        </div>

        <div class="form-row">
            <label>文書タイトル</label>
            <input type="text"
                   name="title"
                   value="${document.title}">
        </div>

        <div class="form-row">
            <label>著者</label>
            <input type="text"
                   name="author"
                   value="${document.author}">
        </div>

        <div class="form-row">
            <label>発行年</label>
            <input type="text"
                   name="publishYear"
                   value="${document.publishYear}">
        </div>

        <div class="form-row">
            <label>アブストラクト</label>
            <textarea name="abstractText"
                      class="textarea-input">${document.abstractText}</textarea>
        </div>

        <div class="form-row">
            <label>PDFファイル</label>
            <input type="file"
                   name="pdfFile">
        </div>

        <div class="button-row">

            <button type="submit"
                    class="primary-button">
                登録
            </button>

            <button type="submit"
                    name="action"
                    value="delete"
                    class="danger-button">
                削除
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