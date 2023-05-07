<%@ page import="com.example.musicplate.models.Plate" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.example.musicplate.models.User" %>
<%@ page import="com.example.musicplate.service.UserService" %>
<%@ page import="com.example.musicplate.repositories.impl.UserRepoImpl" %>
<%@ page import="com.example.musicplate.service.PlateService" %>
<%@ page import="com.example.musicplate.repositories.impl.PlateRepoImpl" %>
<%@ page import="com.example.musicplate.db.HibernateUtil" %><%--
  Created by IntelliJ IDEA.
  User: toleu
  Date: 22.04.2023
  Time: 3:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>MUSIC Store</title>
    <!-- Подключение Bootstrap -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.2.3/css/bootstrap.min.css"
          integrity="sha512-SbiR/eusphKoMVVXysTKG/7VseWii+Y3FdHrt0EpKgpToZeemhqHeZeLWLhJutz/2ut2Vw1uQEj2MbRF+TVBUA=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>

<body style="display: flex; flex-direction: column; width: 100%;">
<!-- Хедер -->
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

//        try {
//            current = DBManager.getUser(id);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
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
<main class="px-5 pt-5" style="flex-grow: 1; display: flex; flex-direction: column;">
    <!-- Баннер -->

    <h1 class="h1 my-5">Продукты</h1>
    <!-- Популярные продажи -->
    <div style="display: flex;" class="my-5">
        <div style="display: grid; width: 100%; grid-template-columns: 1fr 1fr 1fr 1fr; justify-content: space-between; gap: 90px">
            <%
                PlateService plateService = new PlateService(new PlateRepoImpl(HibernateUtil.getSession().getSessionFactory()));

                ArrayList<Plate> plates = (ArrayList<Plate>) plateService.getAllPlates();
                        //DBManager.getAllPlates();
                for (Plate plate : plates) {
            %>
            <form method="post" action="${pageContext.request.contextPath}/buy-plate" class="col-lg-3 col-md-4 col-sm-6 mb-4" style="width: 100%;">
                <div class="card h-100">
                    <img class="card-img-top" src="<%=plate.getPreview()%>" alt=""/>
                    <div class="card-body">
                        <h5 class="card-title"><%=plate.getName()%>
                        </h5>
                        <p class="card-text">
                            <%=plate.getDescription()%>
                        </p>
                        <p class="card-text fs-3">
                            <%=plate.getPrice()%>&#8376;
                        </p>
                        <input type="hidden" value="<%=plate.getId()%>">
                        <button type="submit" class="btn btn-primary">Добавить в корзину</button>
                    </div>
                </div>
            </form>
            <%
                    }


            %>
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
                        <input type="email" class="form-control" id="emailInput" placeholder="Введите email"/>
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
</body>

</html>
