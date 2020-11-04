
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags/" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>

<tags:head/>

<body>
<tags:left-menu/>
<div class="main__content">
    <div class="main__content__inner">
        <tags:header/>
        <div class="form-popup_fail" id="failure_add" align="center">
            <h5><span>Опрация доступна только авторизованным пользователям</span></h5>
        </div>
        <div class="product__page">
            <div class="product__page__header">
                <div class="product__page__header__img" align="center">
                    <img src="${pageContext.request.contextPath}${product.getPicture()}" alt="">
                    <div class="product__element__rating">
                        <c:forEach begin="1" end="5" var="i">
                            <c:if test="${i <= product.getRating()}">
                                <span class="active"></span>
                            </c:if>
                            <c:if test="${i > product.getRating()}">
                                <span></span>
                            </c:if>
                        </c:forEach>

                    </div>
                    <div class="product__element__rating__inner" align="center">${product.getRating()}</div>
                </div>

                <div class="product__page__header__info" align="center">
                    <div class="product__page__header__info__name">
                        <b><span>${product.getName()}</span></b>
                    </div>
                    <hr class="hr__black">
                    <div class="product__page__header__info__size">
                        <span>Масса:</span>
                        <span>${product.getSize()} кг.</span>
                    </div>
                    <div class="product__page__header__info__remainig">
                        <span>В наличии:</span>
                        <span>${product.getRemaining()}</span>
                    </div>
                </div>

                <div class="product__page__header__buy" align="center">
                    <div class="product__page__header__buy__price">
                        <b>
                            <span>${product.getPrice()}</span>
                            <span>&#8381</span>
                        </b>
                    </div>
                    <c:if test="${product.getRemaining() > 0}">
                        <div class="product__page__header__buy__button">
                            <c:if test="${user == null}">
                                <button class="product__page__header__buy__button" onclick="showForm('failure_add')">В корзину</button>
                            </c:if>
                            <c:if test="${user != null}">
                                <form action="${pageContext.request.contextPath}/basket" method="POST">
                                    <input type="hidden" name="product_id" value="${product.getProductId()}">
                                    <input type="hidden" name="method" value="PUT">
                                    <button class="product__page__header__buy__button">В корзину</button>
                                </form>
                            </c:if>

                        </div>
                    </c:if>
                    <c:if test="${product.getRemaining() == 0}">
                        <div class="product__page__header__buy__button__disable">
                            <span>Товара нет в наличии</span>
                        </div>
                    </c:if>
                </div>
            </div>
            <hr class="hr__black">
            <div class="product__page__description">
                <b><span>Описание:</span></b>
                <br>
                <span>${product.getDescription()}</span>
            </div>
            <hr class="hr__black">
            <div class="product__page__reviews">
                <b><span>Отзывы: ${product.getReviewsCount()}</span></b>
            </div>
            <br>


            <c:forEach items="${reviews}" var="review">
                <div class="product__page__review__element">
                    <div class="product__page__review__element__img">
                        <div class="product__page__review__element__img__name" align="center">
                            <span>${review.getAuthorName()}, ${review.beautifulTime()}</span>
                        </div>
                        <img src="${pageContext.request.contextPath}/resources/img/ProfileImages/default-profile-picture.png" alt="">
                    </div>
                    <div class="product__page__review__element__inner">
                        <div class="product__element__rating">
                            <c:forEach begin="1" end="5" var="i">

                                <c:if test="${i <= review.getRating()}">
                                    <span class="active"></span>
                                </c:if>
                                <c:if test="${i > review.getRating()}">
                                    <span></span>
                                </c:if>
                            </c:forEach>
                            <div class="product__element__rating__inner">${review.getRating()}</div>
                        </div>
                        <hr class="hr">
                        <div class="product__page__review__element__text">
                                ${review.getText()}
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <tags:footer/>
</div>
</body>
</html>



