<%@ page import="java.sql.SQLException" %>
<%@ page import="com.example.musicplate.models.User" %>
<%@ page import="com.example.musicplate.service.UserService" %>
<%@ page import="com.example.musicplate.repositories.impl.UserRepoImpl" %><%--
  Created by IntelliJ IDEA.
  User: toleu
  Date: 22.04.2023
  Time: 3:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta http-equiv="X-UA-Compatible" content="ie=edge" />
  <title>MUSIC Store</title>
  <!-- Подключение Bootstrap -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.2.3/css/bootstrap.min.css"
        integrity="sha512-SbiR/eusphKoMVVXysTKG/7VseWii+Y3FdHrt0EpKgpToZeemhqHeZeLWLhJutz/2ut2Vw1uQEj2MbRF+TVBUA=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>

<body>
<!-- Хедер -->
<div style="display: grid; height: 100vh; grid-template-rows: auto 1fr auto;">
  <%
    UserService userService = new UserService(new UserRepoImpl());

    Cookie[] cookies = request.getCookies();
    boolean isAuth = false;
    long id = -1L;

    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("current")) {
        if(cookie.getValue().equals("-1")) break;
        isAuth = true;
        id = Long.parseLong(cookie.getValue());
      }
    }
    User current = null;
    if (id != -1L) {

//      try {
//        current = DBManager.getUser(id);
//      } catch (SQLException e) {
//        throw new RuntimeException(e);
//      }
      current= userService.getUser(id);
    }
  %>
  <header class="d-flex">
    <nav class="d-flex ps-5 px-5 justify-content-between w-100 navbar-expand-lg navbar navbar-dark bg-dark">
      <a class="navbar-brand fs-2" href="${pageContext.request.contextPath}/main">MUSIC</a>
      <div class="collapse navbar-collapse w-auto d-flex justify-content-end" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item active">
            <a class="nav-link" href="${pageContext.request.contextPath}/about">О компании</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/products">Магазин</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/cart">Корзина</a>
          </li>
          <%
            if (!isAuth) {
          %>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/login">Логин</a>
          </li>
          <%
          } else {
            assert current != null;
            if (current.isAdmin()) {
          %>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/admin">Админ панель</a>
          </li>
          <%
            }
          %>
          <li class="nav-item">
            <a class="nav-link"><%=current.getName() + " " + current.getSurname()%>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/logout">Выйти</a>
          </li>

          <%
            }
          %>
        </ul>
      </div>
    </nav>
  </header>
  <!-- Основное содержимое -->
  <main class="px-5 pt-5 d-flex align-items-center justify-content-center">
    <!-- Баннер -->
    <div class="container">
      <div class="row">
        <div class="col-md-6 offset-md-3">
          <h2 class="text-center mb-4">Создать пользователя</h2>
          <form method="post" action="${pageContext.request.contextPath}/reg" class="flex-column d-flex gap-3 align-items-center">
            <div class="form-group w-100">
              <label for="inputName">Имя:</label>
              <input type="text" name="name" class="form-control" id="inputName" placeholder="Введите имя">
            </div>
            <div class="form-group w-100">
              <label for="inputSur">Фамилия:</label>
              <input type="text" name="surname" class="form-control" id="inputSur" placeholder="Введите фамилию">
            </div>
            <div class="form-group w-100">
              <label for="inputEmail">Email:</label>
              <input type="email" name="email" class="form-control" id="inputEmail" placeholder="Введите email">
            </div>
            <div class="form-group w-100">
              <label for="inputPassword">Пароль:</label>
              <input type="password" name="password" class="form-control" id="inputPassword"
                     placeholder="Введите пароль">
            </div>
            <input type="hidden" name="page" value="admin">
            <button type="submit" class="btn btn-primary px-5">Создать</button>
          </form>
        </div>
      </div>
    </div>
  </main>
  <!-- Footer -->
  <footer class="bg-dark text-center text-white pt-5 pb-3">
    <div class="container">
      <div class="d-flex justify-content-around">
        <div class="d-flex flex-column w-auto align-items-start">
          <h5>Магазин</h5>
          <ul class="list-unstyled text-small text-start">
            <li><a class="text-white" href="${pageContext.request.contextPath}/about">О компании</a></li>
            <li><a class="text-white" href="${pageContext.request.contextPath}/products">Магазин</a></li>
            <li><a class="text-white" href="${pageContext.request.contextPath}/cart">Корзина</a></li>
            <li><a class="text-white" href="${pageContext.request.contextPath}/login">Логин</a></li>
          </ul>
        </div>
        <div class="d-flex flex-column w-auto align-items-start">
          <h5>Социальные сети</h5>
          <ul class="list-unstyled text-small text-start">
            <li><a class="text-white" href="#">Instagram</a></li>
            <li><a class="text-white" href="#">Facebook</a></li>
            <li><a class="text-white" href="#">Twitter</a></li>
          </ul>
        </div>
        <div class="d-flex flex-column w-auto align-items-start">
          <h5>Контакты</h5>
          <ul class="list-unstyled text-small text-start">
            <li>
              <a class="text-white" href="mailto:info@musicstore.com">info@musicstore.com</a>
            </li>
            <li><a class="text-white" href="#">+7 (495) 123-45-67</a></li>
          </ul>
          <form class="d-flex align-items-center">
            <div class="form-group">
              <input type="email" class="form-control" id="emailInput" placeholder="Введите email" />
            </div>
            <button type="submit" class="btn btn-primary">Отправить</button>
          </form>
        </div>
      </div>
      <div class="row pt-2">
        <div class="col-md-12">
          <p class="text-muted small mb-0">
            © 2023 Музыкальный магазин "MUSIC". Все права
            защищены.
          </p>
        </div>
      </div>
    </div>
  </footer>
</div>
</body>

</html>
