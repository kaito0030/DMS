<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<h1>文書検索</h1>

<div class="form-card">

<form action="${pageContext.request.contextPath}/document-search-result"
      method="get"
      class="list-search-form">

    <select name="searchColumn"
            class="search-select">

        <option value="documentId">文書ID</option>
        <option value="author">著者</option>
        <option value="title">タイトル</option>

    </select>

    <input type="text"
           name="keyword"
           class="search-input"
           placeholder="検索文字列">

    <button type="submit"
            class="primary-button">
        検索
    </button>

</form>

</div>