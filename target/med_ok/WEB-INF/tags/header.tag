<%@tag description="Header tag" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="header">
    <div class="header__up">
        <div class="header__location">
            <a href="">
                <span class="header__text">г.Временный, ул. Тестовая, д. 404</span>
            </a>
        </div>
        <div class="header__email">
            <a href="#">
                <span class="header__text">med.ok@gmail.ru</span>
            </a>
        </div>
        <div class="header__tel">
            <a href="tel:88005553535">
                <span class="header__text">8-(800)-555-35-35</span>
            </a>
        </div>
    </div>

    <div class="header__down">
        <form action="${pageContext.request.contextPath}/search" method="get" class="search">
            <input type="search" placeholder="Поиск по товарам.." class="search__bar" name="text">
            <input type="submit" value="Найти" class="search__submit">
        </form>
        <div class="header__down__inner">
            <a href="${pageContext.request.contextPath}/basket">
                <div class="header__down__button" id="shopping__basket">
                    Корзина
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/auth">

                <c:if test="${user == null}">
                    <c:set var="id" value="login"/>
                </c:if>
                <c:if test="${user != null}">
                    <c:set var="id" value="logout"/>
                </c:if>

            <div class="header__down__button" id=${pageContext.getAttribute("id")}>
                <c:if test="${user == null}">
                    Регистрация/Войти
                </c:if>
                <c:if test="${user != null}">
                    Профиль/Выйти
                </c:if>
            </div>
            </a>
        </div>
    </div>
</header>
<hr class="hr">