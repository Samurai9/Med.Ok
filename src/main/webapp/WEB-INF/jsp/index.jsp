<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags/" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tags:head/>

<body>
<tags:left-menu/>
<div class="main__content">
    <div class="main__content__inner">
        <tags:header/>
        <div class="leader__text" align="center">
            <h1>Лидеры продаж</h1>
        </div>
        <div class="leaders">
            <c:forEach items="${products}" var="product">
                <a href="${pageContext.request.contextPath}/product/${product.getProductId()}">
                    <div class="leader__element">
                        <div class="leader__element__name">
                            <h2>${product.getName()}</h2>
                            <hr class="hr_black">
                        </div>
                        <div class="leader__element__img">
                            <img src=".${product.getPicture()}" alt="">
                        </div>
                        <div class="leader__element__rating">
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
                        </div>
                        <span><b>${product.getRating()}</b></span>
                        <div class="leader__element__price">
                            <b><h3>${product.getPrice()}&#8381</h3></b>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </div>
    <tags:footer/>
</div>
</body>
</html>
