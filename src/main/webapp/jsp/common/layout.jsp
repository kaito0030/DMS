<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ja">

<head>

    <meta charset="UTF-8">

    <title>
        メイン画面
    </title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<div class="container">

    <header class="header">

        <jsp:include page="header.jsp"/>

    </header>

    <aside class="sidebar">

        <jsp:include page="sidebar.jsp"/>

    </aside>

    <main class="main-content">

        <jsp:include page="${contentPage}" />

    </main>

</div>

</body>

</html>