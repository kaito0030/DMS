<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>個人閲覧履歴</h1>

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