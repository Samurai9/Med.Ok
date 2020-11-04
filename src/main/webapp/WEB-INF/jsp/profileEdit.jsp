<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags/" %>

<tags:head/>

<body>
<tags:left-menu/>
<div class="main__content">
    <div class="main__content__inner">
        <tags:header/>

        <div class="profile__edit">
<%--            ${pageContext.request.contextPath}/profile/edit --%>
            <form action="" method="post">
                <input type="hidden" name="action" value="change_img">
                <div class="profile__image">
                    <img src="${pageContext.request.contextPath}/resources/img/ProfileImages/default-profile-picture.png" alt="">
                </div>
                <div class="profile__change__picture">
                    <a href="">
                        <span>Изменить</span>
                    </a>
                </div>
            </form>
            <br>

            <h2><span>Изменение информации</span></h2>
            <form action="${pageContext.request.contextPath}/profile/edit" method="post">
                <input type="hidden" name="action" value="change_inf">

                <h3><span>Имя:</span></h3>
                <input type="text" name="name" class="profile__edit__input" value="${user.getName()}" required minlength="2">

                <h3><span>Электронная почта:</span></h3>
                <input type="text" name="email" class="profile__edit__input" value="${user.getEmail()}" required>

                <h3><span>Для подтверждения изменений введите Ваш пароль:</span></h3>
                <input type="password" name="password" class="profile__edit__input" placeholder="Пароль" required minlength="8">

                <p><input type="submit" class="base__submit"></p>
            </form>
            <br>

            <h2><span>Изменение пароля</span></h2>
            <form action="${pageContext.request.contextPath}/profile/edit" method="post">
                <input type="hidden" name="action" value="change_pas">
                <h3><span>Введите новый пароль (должен быть не менее 8 символов):</span></h3>
                <input type="password" name="new_password" class="profile__edit__input" placeholder="Новый пароль" required minlength="8">
                <h3><span>Подтвердите новый пароль:</span></h3>
                <input type="password" name="repeat_password" class="profile__edit__input" placeholder="Новый пароль" required minlength="8">

                <h3><span>Для подтверждения изменений введите Ваш старый пароль:</span></h3>
                <input type="password" name="password" class="profile__edit__input" placeholder="Пароль" required minlength="8">
                <p><input type="submit" class="base__submit"></p>
            </form>
            <br>

            <h2><span>Удаление аккаунта</span></h2>
            <form action="" method="post" name="deleteAcc">
                <input type="hidden" name="action" value="delete_acc">
                <h3><span>Для подтверждения удаления введите Ваш пароль:</span></h3>
                <input type="password" name="password" class="profile__edit__input" placeholder="Пароль" required minlength="8">
                <p><input type="checkbox" name="delete_agreement" required>Я подтверждаю, что хочу удалить аккаунт без возможности восстановления данных</p>
                <p><input type="submit" class="base__submit"></p>
            </form>
        </div>
    </div>
    <tags:footer/>
</div>
</body>
</html>
