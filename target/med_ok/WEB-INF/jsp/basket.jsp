
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags/" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="java.util.List" %>
<%@ page import="entities.Product" %>

<tags:head/>

<body>
<tags:left-menu/>
<div class="main__content">
    <div class="main__content__inner">
        <tags:header/>

        <h2>Корзина</h2>
        <div class="basket">
            <div class="basket__products__inner">
                <c:if test="${basket.getProducts().size() == 0}">
                    <div class="basket__products__empty">
                        <b><span>В корзине нет товаров</span></b>
                    </div>
                </c:if>
                <c:forEach items="${basket.getProducts()}" var="map">
                <c:forEach items="${map}" var="product">
                    <div class="products">
                        <a href="${pageContext.request.contextPath}/product/${product.key.getProductId()}" class="product__element__basket">
                            <div class="product__element__inner1">
                                <img src="${pageContext.request.contextPath}${product.key.getPicture()}" alt="">
                            </div>
                            <div class="product__element__inner2">
                                <div class="product__element__title">
                                    <b><span>${product.key.getName()}</span></b>
                                    <hr class="hr_black">
                                </div>
                                <div class="product__element__size">
                                    <span>Масса: </span>
                                    <span>${product.key.getSize()} кг.</span>
                                </div>
                                <div class="product__element__review">
                                    <span>Отзывы:</span>
                                    <span>${product.key.getReviewsCount()}</span>
                                </div>
                                <div class="product__element__rating">
                                    <c:forEach begin="1" end="5" var="i">

                                        <c:if test="${i <= product.key.getRating()}">
                                            <span class="active"></span>
                                        </c:if>
                                        <c:if test="${i > product.key.getRating()}">
                                            <span></span>
                                        </c:if>
                                    </c:forEach>
                                    <div class="product__element__rating__inner">${product.key.getRating()}</div>
                                </div>

                            </div>
                            <div class="product__element__inner3">
                                <div class="product__element__price">
                                    <b><span>${product.key.getPrice()}</span></b>
                                    <b><span>&#8381</span></b>
                                </div>
                                <div>
                                    <b><span>${product.value}</span></b>
                                    <span> шт.</span>
                                </div>
                                <c:if test="${product.key.getRemaining() == 0}">
                                    <div class="product__element__not__found" align="center">Товара нет в наличии</div>
                                </c:if>
                                <c:if test="${product.key.getRemaining() > 0}">
                                    <form action="${pageContext.request.contextPath}/basket" method="POST">
                                        <input type="hidden" name="product_id" value="${product.key.getProductId()}">
                                        <input type="hidden" name="method" value="DELETE">
                                        <button class="product__element__button">Удалить</button>
                                    </form>
                                </c:if>
                            </div>
                        </a>
                    </div>
                </c:forEach>
                </c:forEach>
            </div>
            <div class="basket__info">
                <span>Информация</span>
                <hr class="hr__black">
                <div class="basket__info__total__products" align="center">
                    <span>Количество продуктов:</span>
                    <br>
                    <b><span>${basket.getCountOfProducts()}</span></b>
                </div>
                <div class="basket__info__total_price" align="center">
                    <span>Итоговая цена:</span>
                    <br>
                    <b><span>${basket.getTotalPrice()-user.getDiscount()*basket.getCountOfProducts()} &#8381</span></b>
                </div>
                <div class="basket__info__buy">
                    <button class="product__element__button" id="buy"><b><span>Купить</span></b></button>
                </div>
            </div>
        </div>
    </div>
    <tags:footer/>
</div>
</body>
</html>