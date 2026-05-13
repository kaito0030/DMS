<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>ユーザ一覧</h1>

<form action="${pageContext.request.contextPath}/user-list"
      method="get"
      class="list-search-form">

    <select name="searchColumn"
            id="userSearchColumn"
            class="search-select"
            onchange="changeUserSearchInput()">

        <option value="userName" ${searchColumn == 'userName' ? 'selected' : ''}>
            ユーザID
        </option>

        <option value="realName" ${searchColumn == 'realName' ? 'selected' : ''}>
            名前
        </option>

        <option value="userType" ${searchColumn == 'userType' ? 'selected' : ''}>
            ユーザ区分
        </option>

    </select>

    <input type="text"
           name="keyword"
           id="userKeywordInput"
           class="search-input"
           value="${keyword}"
           placeholder="検索文字列">

    <select name="keyword"
            id="userTypeSelect"
            class="search-input"
            disabled
            style="display:none;">

        <option value="">選択してください</option>

        <option value="admin" ${keyword == 'admin' ? 'selected' : ''}>
            管理ユーザ
        </option>

        <option value="general" ${keyword == 'general' ? 'selected' : ''}>
            一般ユーザ
        </option>

    </select>

    <button type="submit"
            class="primary-button">
        検索
    </button>

</form>

<div class="table-container">

    <table class="data-table">

        <thead>
        <tr>
            <th>
                <a class="sort-link"
                   href="${pageContext.request.contextPath}/user-list?searchColumn=${searchColumn}&keyword=${keyword}&sortColumn=userName&sortOrder=${sortColumn == 'userName' && sortOrder == 'asc' ? 'desc' : 'asc'}">
                    ユーザID
                </a>
            </th>

            <th>
                <a class="sort-link"
                   href="${pageContext.request.contextPath}/user-list?searchColumn=${searchColumn}&keyword=${keyword}&sortColumn=realName&sortOrder=${sortColumn == 'realName' && sortOrder == 'asc' ? 'desc' : 'asc'}">
                    名前
                </a>
            </th>

            <th>
                <a class="sort-link"
                   href="${pageContext.request.contextPath}/user-list?searchColumn=${searchColumn}&keyword=${keyword}&sortColumn=userType&sortOrder=${sortColumn == 'userType' && sortOrder == 'asc' ? 'desc' : 'asc'}">
                    ユーザ区分
                </a>
            </th>

            <th>許可登録</th>
            <th>閲覧可能文書</th>
            <th>編集</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="user" items="${userList}">
            <tr>
                <td>${user.userName}</td>

                <td>${user.realName}</td>

                <td>
                    <c:choose>
                        <c:when test="${user.admin}">
                            管理ユーザ
                        </c:when>
                        <c:otherwise>
                            一般ユーザ
                        </c:otherwise>
                    </c:choose>
                </td>

                <td>
                    <a href="${pageContext.request.contextPath}/permission-register?userName=${user.userName}"
                       class="action-button">
                        許可登録
                    </a>
                </td>

                <td>
                    <a href="${pageContext.request.contextPath}/user-permission-list?userName=${user.userName}"
                       class="detail-button">
                        閲覧可能文書
                    </a>
                </td>

                <td>
                    <a href="${pageContext.request.contextPath}/user-edit?userName=${user.userName}"
                       class="edit-button">
                        編集
                    </a>
                </td>
            </tr>
        </c:forEach>

        </tbody>

    </table>

</div>

<div class="pagination-area">

    <c:if test="${currentPage > 1}">
        <a class="secondary-button"
           href="${pageContext.request.contextPath}/user-list?searchColumn=${searchColumn}&keyword=${keyword}&sortColumn=${sortColumn}&sortOrder=${sortOrder}&page=${currentPage - 1}">
            ＜
        </a>
    </c:if>

    <span class="page-info">
        ${currentPage} / ${totalPages}
    </span>

    <c:if test="${currentPage < totalPages}">
        <a class="secondary-button"
           href="${pageContext.request.contextPath}/user-list?searchColumn=${searchColumn}&keyword=${keyword}&sortColumn=${sortColumn}&sortOrder=${sortOrder}&page=${currentPage + 1}">
            ＞
        </a>
    </c:if>

</div>

<div class="page-button-area">
    <a href="${pageContext.request.contextPath}/user-register"
       class="primary-button">
        新規ユーザ登録
    </a>
</div>

<script>
    function changeUserSearchInput() {

        const column = document.getElementById("userSearchColumn").value;
        const textInput = document.getElementById("userKeywordInput");
        const typeSelect = document.getElementById("userTypeSelect");

        if (column === "userType") {

            textInput.disabled = true;
            textInput.style.display = "none";

            typeSelect.disabled = false;
            typeSelect.style.display = "inline-block";

        } else {

            textInput.disabled = false;
            textInput.style.display = "inline-block";

            typeSelect.disabled = true;
            typeSelect.style.display = "none";
        }
    }

    window.addEventListener("load", changeUserSearchInput);
</script>