<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>文書一覧</h1>

<form action="${pageContext.request.contextPath}/document-list"
      method="get"
      class="list-search-form">

    <select name="searchColumn"
            class="search-select">

        <option value="documentId"
                ${searchColumn == 'documentId' ? 'selected' : ''}>
            文書ID
        </option>

        <option value="author"
                ${searchColumn == 'author' ? 'selected' : ''}>
            著者
        </option>

        <option value="title"
                ${searchColumn == 'title' ? 'selected' : ''}>
            タイトル
        </option>

    </select>

    <input type="text"
           name="keyword"
           class="search-input"
           value="${keyword}"
           placeholder="検索文字列">

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
                   href="${pageContext.request.contextPath}/document-list?searchColumn=${searchColumn}&keyword=${keyword}&sortColumn=documentId&sortOrder=${sortColumn == 'documentId' && sortOrder == 'asc' ? 'desc' : 'asc'}">
                    文書ID
                </a>
            </th>

            <th>
                <a class="sort-link"
                   href="${pageContext.request.contextPath}/document-list?searchColumn=${searchColumn}&keyword=${keyword}&sortColumn=title&sortOrder=${sortColumn == 'title' && sortOrder == 'asc' ? 'desc' : 'asc'}">
                    文書タイトル
                </a>
            </th>

            <th>
                <a class="sort-link"
                   href="${pageContext.request.contextPath}/document-list?searchColumn=${searchColumn}&keyword=${keyword}&sortColumn=author&sortOrder=${sortColumn == 'author' && sortOrder == 'asc' ? 'desc' : 'asc'}">
                    著者
                </a>
            </th>

            <th>
                <a class="sort-link"
                   href="${pageContext.request.contextPath}/document-list?searchColumn=${searchColumn}&keyword=${keyword}&sortColumn=publishYear&sortOrder=${sortColumn == 'publishYear' && sortOrder == 'asc' ? 'desc' : 'asc'}">
                    発行年
                </a>
            </th>

            <th>詳細</th>
            <th>許可登録</th>
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
                    <a href="${pageContext.request.contextPath}/document-detail?documentId=${document.documentId}"
                       class="detail-button">
                        詳細
                    </a>
                </td>

                <td>
                    <a href="${pageContext.request.contextPath}/permission-register?documentId=${document.documentId}"
                       class="action-button">
                        許可登録
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
           href="${pageContext.request.contextPath}/document-list?searchColumn=${searchColumn}&keyword=${keyword}&sortColumn=${sortColumn}&sortOrder=${sortOrder}&page=${currentPage - 1}">
            ＜
        </a>
    </c:if>

    <span class="page-info">
        ${currentPage} / ${totalPages}
    </span>

    <c:if test="${currentPage < totalPages}">
        <a class="secondary-button"
           href="${pageContext.request.contextPath}/document-list?searchColumn=${searchColumn}&keyword=${keyword}&sortColumn=${sortColumn}&sortOrder=${sortOrder}&page=${currentPage + 1}">
            ＞
        </a>
    </c:if>

</div>

<div class="page-button-area">
    <a href="${pageContext.request.contextPath}/document-register"
       class="primary-button">
        新規文書登録
    </a>
</div>
