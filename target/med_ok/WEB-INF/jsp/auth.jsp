
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags/" %>

<tags:head/>

<body>
<tags:left-menu/>
<div class="main__content">
    <div class="main__content__inner">
        <tags:header/>
        <div class="reg__auth">
            <div class="auth">
                <h2><a href=""></a></h2>
                <h2>Авторизация</h2>
                <form action="${pageContext.request.contextPath}/auth" method="post" name="auth">
                    <h3>E-mail</h3>
                    <input type="text" placeholder="example@gmail.ru" name="email" class="base__input" size="30" id="auth_email_form" onfocus="validatorColorAuth()" required>
                    <h3>Пароль</h3>
                    <input type="password" placeholder="Пароль" name="password" class="base__input" size="30" id="auth_password_form" onfocus="validatorColorAuth()" required minlength="8">
                    <p><input type="submit" class="base__submit"></p>
                </form>
            </div>
            <div class="registration">
                <h2>Регистрация</h2>
                <form action="${pageContext.request.contextPath}/reg" method="post">
                    <h3>Имя</h3>
                    <input type="text" placeholder="Иван" name="name" class="base__input" size="30" required id="reg_name_form" onfocus="validatorColorReg()" minlength="2">
                    <h3>E-mail</h3>
                    <input type="text" placeholder="example@gmail.ru" name="email" class="base__input" size="30" required id="reg_email_form" onfocus="validatorColorReg()">
                    <h3>Пароль (не менее 8 символов)</h3>
                    <input type="password" placeholder="Пароль" name="password" class="base__input" size="30" required id="reg_password_form" onfocus="validatorColorReg()" minlength="8">
                    <h3>Повтор пароля</h3>
                    <input type="password" placeholder="Повтор пароля" name="repeat_password" class="base__input" size="30" required id="reg_repeat_password_form" onfocus="validatorColorReg()" minlength="8">
                    <p><input type="checkbox" name="licence_agreement" required id="reg_licence_form">Согласен с <a href="">условиями пользования сайтом</a></p>
                    <p><input type="submit" class="base__submit"></p>
                </form>
            </div>
        </div>
    </div>
    <tags:footer/>
</div>
</body>
</body>
</html>
