<%@tag description="Left menu tag" pageEncoding="UTF-8"%>

<div class="left__menu">
    <div class="left__menu__inner">
        <div class="logo" align="center">
            <a href="${pageContext.request.contextPath}/welcome"><img src="${pageContext.request.contextPath}/resources/img/logo.jpg" alt=""></a>
        </div>
        <nav class="navigation">
            <a href="${pageContext.request.contextPath}/product/liquid">
                <div class="orange__element">Жидкий мед</div>
            </a>
            <a href="${pageContext.request.contextPath}/product/honeycomb">
                <div class="orange__element">Сотовый мед</div>
            </a>
        </nav>
    </div>
</div>