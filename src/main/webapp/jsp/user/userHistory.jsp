<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>ユーザ閲覧履歴</h1>

<div class="detail-card history-user-box">
    <div class="detail-row">
        <div class="detail-label">対象ユーザ</div>
        <div class="detail-value">${targetUserName}</div>
    </div>
</div>

<div class="table-container">

    <table class="data-table">

        <thead>
        <tr>
            <th>閲覧日時</th>
            <th>文書ID</th>
            <th>文書タイトル</th>
            <th>著者</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="history" items="${historyList}">
            <tr>
                <td>${history.viewedAt}</td>
                <td>${history.documentId}</td>
                <td>${history.title}</td>
                <td>${history.author}</td>
            </tr>
        </c:forEach>

        </tbody>

    </table>

</div>

<div class="page-button-area">
    <a href="${pageContext.request.contextPath}/user-list">
        <button class="secondary-button">
            戻る
        </button>
    </a>
</div>