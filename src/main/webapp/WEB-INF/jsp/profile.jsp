<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags/" %>

<tags:head/>

<body>
<tags:left-menu/>
<div class="main__content">
    <div class="main__content__inner">
        <tags:header/>
        <div class="profile" align="center">
            <a href="${pageContext.request.contextPath}/logout">
                <div class="profile__logout" align="center">
                    <div>Выйти из аккаунта</div>
                </div>
            </a>
            <div class="profile__greetings">
                <span>Здраствуйте,</span>
                <span>${user.getName()}!</span>
            </div>
            <div class="profile__image">
                <img src="${pageContext.request.contextPath}/resources/img/ProfileImages/default-profile-picture.png" alt="">
            </div>
            <div class="profile__information">
                <div class="profile__name">
                    <span>Имя:</span>
                    <span>${user.getName()}</span>
                </div>
                <div class="profile__email">
                    <span>Электронная почта:</span>
                    <span>${user.getEmail()}</span>
                </div>
                <div class="profile__discount">
                    <span>Скидка:</span>
                    <span>${user.getDiscount()}</span>
                    <span>&#8381</span>
                </div>
                <a href="${pageContext.request.contextPath}/profile/edit" class="profile__logout">Редактировать</a>
            </div>
        </div>
    </div>
    <tags:footer/>
</div>
</body>
</html>
