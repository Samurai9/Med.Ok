<%@tag description="Head tag" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <meta name="viewport">
    <title>${pageContext.getServletContext().getAttribute("title")}</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/profile.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/productPage.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/basket.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/popup.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/leaderOfSells.css">

    <script src="${pageContext.request.contextPath}/resources/js/popup.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/formValidator.js"></script>
</head>