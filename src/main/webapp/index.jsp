<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.musicplate.models.Plate" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.example.musicplate.models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.musicplate.service.UserService" %>
<%@ page import="com.example.musicplate.repositories.impl.UserRepoImpl" %>
<%@ page import="com.example.musicplate.service.PlateService" %>
<%@ page import="com.example.musicplate.repositories.impl.PlateRepoImpl" %>
<%@ page import="com.example.musicplate.db.HibernateUtil" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
//
//    List<User> allUsers = userService.getAllUsers();
//    boolean exist = false;
//    if (allUsers != null) {
//        for (User user : allUsers) {
//            if (user.isAdmin()) {
//                exist = true;
//                break;
//            }
//        }
//    }
//    if(!exist) {
////        try {
////            User admin = new User(0L, "Super", "User", "admin@gmail.com", "admin", true);
////            DBManager.addUser(admin);
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
//
//        User admin = new User(0L, "Super", "User", "admin@gmail.com", "admin", true);
//        userService.addUser(admin);
//    }
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
    <div class="jumbotron d-flex justify-content-center align-items-center flex-column text-light" style="background:
		                                linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)),
		                                url(./banner.jpg);
	                                    background-size: cover;
	                                    background-repeat: no-repeat;
	                                    background-attachment: fixed;
	                                    background-position: bottom;
                                        width: 100%;
                                        height: 650px;">
        <h1 class="display-1">Скидки и распродажи!</h1>
        <p class="lead">
            Не упустите возможность приобрести любимые пластинки по выгодной
            цене!
        </p>
        <hr class="my-4"/>
        <p>Подробности на странице магазина.</p>
        <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/products" role="button">Перейти в
            магазин</a>
    </div>
    <h1 class="h1 my-5">Популярные товары</h1>
    <!-- Популярные продажи -->
    <div style="display: flex;" class="my-5">
        <div style="display: flex; width: 100%; flex-wrap: wrap; justify-content: space-between;">
            <%
                PlateService plateService = new PlateService(new PlateRepoImpl(HibernateUtil.getSession().getSessionFactory()));

                ArrayList<Plate> plates = (ArrayList<Plate>) plateService.getAllPlates();
                        //DBManager.getAllPlates();
                for (int i = Math.max(plates.size() - 4, 0); i < plates.size(); i++) {
            %>
            <form method="post" action="${pageContext.request.contextPath}/buy-plate" class="col-lg-3 col-md-4 col-sm-6 mb-4" style="width: 20%;">
                <div class="card h-100">
                    <img class="card-img-top" src="<%=plates.get(i).getPreview()%>" alt=""/>
                    <div class="card-body">
                        <h5 class="card-title"><%=plates.get(i).getName()%>
                        </h5>
                        <p class="card-text">
                            <%=plates.get(i).getDescription()%>
                        </p>
                        <p class="card-text fs-3">
                            <%=plates.get(i).getPrice()%>&#8376;
                        </p>
                        <input type="hidden" name="id" value="<%=plates.get(i).getId()%>">
                        <input type="hidden" name="page" value="main">
                        <button type="submit" class="btn btn-primary">Добавить в корзину</button>
                    </div>
                </div>
            </form>
            <%

                }
            %>
        </div>
    </div>
    <!-- Доверительные слова -->
    <div class="container-fluid bg-light py-3">
        <div class="container">
            <div class="row">
                <div class="col-md-4">
                    <p class="lead">
                        "Отличный выбор и быстрая доставка! Рекомендую магазин!"
                    </p>
                    <p class="text-muted">- Иван Петров</p>
                </div>
                <div class="col-md-4">
                    <p class="lead">
                        "Качественные пластинки и отличное обслуживение клиентов! Спасибо!"
                    </p>
                    <p class="text-muted">- Ольга Иванова</p>
                </div>
                <div class="col-md-4">
                    <p class="lead">
                        "Магазин предоставляет широкий выбор музыки разных жанров.
                        Всегда нахожу, что мне нужно!"
                    </p>
                    <p class="text-muted">- Алексей Смирнов</p>
                </div>
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