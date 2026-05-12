<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<h1>文書検索</h1>

<div class="form-card">

    <form action="${pageContext.request.contextPath}/document-search-result"
          method="get">

        <div class="form-row">
            <label>検索キーワード</label>

            <input type="text"
                   name="keyword"
                   placeholder="文書タイトル・著者名を入力">
        </div>

        <div class="button-row">
            <button type="submit"
                    class="primary-button">
                検索
            </button>
        </div>

    </form>

</div>