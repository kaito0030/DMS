<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>ユーザ一覧</h1>

<div class="table-container">

    <table class="data-table">

        <thead>
        <tr>
            <th>本名</th>
            <th>ユーザ名</th>
            <th>許可登録</th>
            <th>履歴</th>
            <th>編集</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="user" items="${userList}">
            <tr>
                <td>${user.realName}</td>
                <td>${user.userName}</td>

                <td>
                    <a href="${pageContext.request.contextPath}/permission-register?userName=${user.userName}">
                        <button class="action-button">許可登録</button>
                    </a>
                </td>

                <td>
                    <a href="${pageContext.request.contextPath}/user-history?userName=${user.userName}">
                        <button class="action-button">履歴</button>
                    </a>
                </td>

                <td>
                    <a href="${pageContext.request.contextPath}/user-edit?userName=${user.userName}">
                        <button class="edit-button">編集</button>
                    </a>
                </td>
            </tr>
        </c:forEach>

        </tbody>

    </table>

</div>

<div class="page-button-area">
    <a href="${pageContext.request.contextPath}/user-register">
        <button class="primary-button">
            新規ユーザ登録
        </button>
    </a>
</div>