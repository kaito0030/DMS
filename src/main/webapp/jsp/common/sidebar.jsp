<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav>

    <div class="menu-section">
        <div class="menu-title">共通メニュー</div>

        <ul class="menu-list">
            <li>
                <a href="${pageContext.request.contextPath}/document-search">
                    文書検索
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/available-content">
                    閲覧可能文書一覧
                </a>
            </li>
        </ul>
    </div>



<c:if test="${sessionScope.loginUser.admin}">

    <div class="menu-section">

        <div class="menu-title">
            管理メニュー
        </div>

        <ul class="menu-list">

            <li>
                <a href="${pageContext.request.contextPath}/user-list">
                    ユーザ一覧
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/document-list">
                    文書一覧
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/permission-register">
                    許可登録画面
                </a>
            </li>

        </ul>

    </div>

</c:if>

</nav>