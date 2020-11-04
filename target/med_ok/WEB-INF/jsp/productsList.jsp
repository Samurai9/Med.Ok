
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="entities.Product" %>

<%@taglib prefix="tags" tagdir="/WEB-INF/tags/" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tags:head/>

<body>
<tags:left-menu/>
<div class="main__content">
    <div class="main__content__inner">
        <tags:header/>
        <div class="form-popup" id="success_add" align="center">
            <h5><span>Продукт успешно добавлен</span></h5>
        </div>

        <div class="form-popup_fail" id="failure_add" align="center">
            <h5><span>Опрация доступна только авторизованным пользователям</span></h5>
        </div>
        <c:forEach items="${products}" var="product">
        <div class="products">
            <a href="${pageContext.request.contextPath}/product/${product.getProductId()}" class="product__element">
                <div class="product__element__inner1">
                    <img src="${pageContext.request.contextPath}${product.getPicture()}" alt="">
                </div>
                <div class="product__element__inner2">
                    <div class="product__element__title">
                        <b><span>${product.getName()}</span></b>
                        <hr class="hr_black">
                    </div>
                    <div class="product__element__size">
                        <span>Масса: </span>
                        <span>${product.getSize()} кг.</span>
                    </div>
                    <div class="product__element__review">
                        <span>Отзывы:</span>
                        <span>${product.getReviewsCount()}</span>
                    </div>
                    <div class="product__element__rating">
                        <c:forEach begin="1" end="5" var="i">

                            <c:if test="${i <= product.getRating()}">
                                <span class="active"></span>
                            </c:if>
                            <c:if test="${i > product.getRating()}">
                                <span></span>
                            </c:if>
                        </c:forEach>
                        <div class="product__element__rating__inner">${product.getRating()}</div>
                    </div>

                </div>
                <div class="product__element__inner3">
                    <div class="product__element__price">
                        <b><span>${product.getPrice()}</span></b>
                        <b><span>&#8381</span></b>
                    </div>
<%--                    <c:if test="${product.getRemaining() == 0}">--%>
<%--                        <div class="product__element__not__found" align="center">Товара нет в наличии</div>--%>
<%--                    </c:if>--%>
<%--                    <c:if test="${product.getRemaining() > 0}">--%>
<%--                        <c:if test="${user != null}">--%>
<%--                            <form action="${pageContext.request.contextPath}/product/${category}" method="POST">--%>
<%--                                <input type="hidden" name="product_id" value="${product.getProductId()}">--%>
<%--                                <input type="hidden" name="method" value="PUT">--%>
<%--                                <button class="product__element__button" onclick="showForm('success_add')">В корзину</button>--%>
<%--                            </form>--%>
<%--                        </c:if>--%>
<%--                        <c:if test="${user == null}">--%>
<%--                            <button class="product__element__button" onclick="showForm('failure_add')">В корзину</button>--%>
<%--                        </c:if>--%>
<%--                    </c:if>--%>

                </div>
            </a>
        </div>
        </c:forEach>

    </div>
    <tags:footer/>
</div>
</body>
</html>
