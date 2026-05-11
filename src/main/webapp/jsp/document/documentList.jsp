<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>文書一覧</h1>

<div class="table-container">

    <table class="data-table">

        <thead>
        <tr>
            <th>文書ID</th>
            <th>文書タイトル</th>
            <th>著者</th>
            <th>発行年</th>
            <th>許可登録</th>
            <th>編集</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="document" items="${documentList}">
            <tr>
                <td>${document.documentId}</td>
                <td>${document.title}</td>
                <td>${document.author}</td>
                <td>${document.publishYear}</td>

                <td>
                    <a href="${pageContext.request.contextPath}/permission-register?documentId=${document.documentId}">
                        <button class="action-button">許可登録</button>
                    </a>
                </td>

                <td>
                    <a href="${pageContext.request.contextPath}/document-edit?documentId=${document.documentId}">
                        <button class="edit-button">編集</button>
                    </a>
                </td>
            </tr>
        </c:forEach>

        </tbody>

    </table>

</div>

<div class="page-button-area">
    <a href="${pageContext.request.contextPath}/document-register">
        <button class="primary-button">
            新規文書登録
        </button>
    </a>
</div>