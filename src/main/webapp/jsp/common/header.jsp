<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div class="header-inner">

    <div class="header-left">

        <img src="${pageContext.request.contextPath}/images/logo.png"
             class="logo">

        <span class="system-title">

            文書管理システム

        </span>

    </div>

    <div class="header-right">

<div class="user-name">
    ${sessionScope.loginUser.realName}
</div>

        <form action="${pageContext.request.contextPath}/logout"
              method="post">

            <button class="logout-button"
                    type="submit">

                ログアウト

            </button>

        </form>

    </div>

</div>