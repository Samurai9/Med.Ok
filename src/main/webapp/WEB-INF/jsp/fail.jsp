
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags/" %>

<tags:head/>

<body>
<tags:left-menu/>
<div class="main__content">
    <div class="main__content__inner">
        <tags:header/>
        <div class="fail">
            <h2>Ошибка</h2>
            <h3>${pageContext.getServletContext().getAttribute("text")}</h3>
            <% pageContext.getServletContext().setAttribute("text", "");%>
        </div>
    </div>
    <tags:footer/>
</div>
</body>
</html>
