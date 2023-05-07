//package com.example.musicplate.db;
//
//import com.example.musicplate.models.Cart;
//import com.example.musicplate.models.Plate;
//import com.example.musicplate.models.User;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class DBManager {
//    public static final String url = "jdbc:postgresql://localhost:5432/plates_db";
//    public static final String user = "postgres";
//    public static final String password = "admin";
//    public static Connection connection;
//
//    public static void connect() {
//        try {
//            Class.forName("org.postgresql.Driver");
//            connection = DriverManager.getConnection(url, user, password);
//        } catch (ClassNotFoundException | SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static List<User> getAllUsers() throws SQLException {
//        if (DBManager.connection == null) {
//            DBManager.connect();
//        }
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("SELECT * from customers");
//        ArrayList<User> users = new ArrayList<>();
//        while (resultSet.next()) {
//            users.add(new User(resultSet.getLong("id"),
//                    resultSet.getString("firstname"),
//                    resultSet.getString("surname"),
//                    resultSet.getString("email"),
//                    resultSet.getString("pass"),
//                    resultSet.getBoolean("superuser")));
//        }
//        statement.close();
//        return users;
//    }
//
//    public static List<Plate> getAllPlates() throws SQLException {
//        if (DBManager.connection == null) {
//            DBManager.connect();
//        }
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("SELECT * from plates");
//        ArrayList<Plate> plates = new ArrayList<>();
//        while (resultSet.next()) {
//            plates.add(new Plate(resultSet.getLong("id"),
//                    resultSet.getString("title"),
//                    resultSet.getString("preview"),
//                    resultSet.getString("description"),
//                    resultSet.getInt("price")));
//        }
//        statement.close();
//        return plates;
//    }
//
//    public static List<Cart> getAllCarts() throws SQLException {
//        if (DBManager.connection == null) {
//            DBManager.connect();
//        }
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("SELECT * from carts");
//        ArrayList<Cart> carts = new ArrayList<>();
//        Map<Long, ArrayList<Plate>> results = new HashMap<>();
//        while (resultSet.next()) {
//            Long user_id = resultSet.getLong("user_id");
//            Long plate_id = resultSet.getLong("plate_id");
//            if (results.containsKey(user_id)) {
//                ArrayList<Long> longs = results.get(user_id);
//                longs.add(plate_id);
//                results.replace(user_id, longs);
//            } else {
//                ArrayList<Long> longs = new ArrayList<>();
//                longs.add(plate_id);
//                results.put(user_id, longs);
//            }
//        }
//
//        for (Long key : results.keySet()) {
//            Cart cart = new Cart();
//            cart.setUser_id(key);
//            cart.setPlate_ids(results.get(key));
//            carts.add(cart);
//        }
//
//        statement.close();
//        return carts;
//    }
//
//    public static User getUser(Long Id) throws SQLException {
//        if (DBManager.connection == null) {
//            DBManager.connect();
//        }
//        List<User> allUsers = getAllUsers();
//        for (User user : allUsers) {
//            if (user.getId().equals(Id)) {
//                User current = new User(user);
//                Cart cart = getCart(user.getId());
//                if (cart != null) {
//                    System.out.println("getuser cart:" + cart);
//                    current.setCart(cart);
//                }
//                return current;
//            }
//        }
//        return null;
//    }
//
//    public static Plate getPlate(Long Id) throws SQLException {
//        if (DBManager.connection == null) {
//            DBManager.connect();
//        }
//        List<Plate> allPlates = getAllPlates();
//        for (Plate plate : allPlates) {
//            if (plate.getId().equals(Id)) return plate;
//        }
//        return null;
//    }
//
//    public static Cart getCart(Long user_id) throws SQLException {
//        if (DBManager.connection == null) {
//            DBManager.connect();
//        }
//        List<Cart> allCarts = getAllCarts();
//        System.out.println(allCarts);
//        for (Cart cart : allCarts) if (cart.getUser_id().equals(user_id)) return cart;
//
//        return null;
//    }
//
//    public static void addUser(User user) throws SQLException {
//        if (DBManager.connection == null) {
//            DBManager.connect();
//        }
//        Statement statement = connection.createStatement();
//        String sql = String.format("INSERT INTO customers (firstname, surname, email, pass, superuser) VALUES" +
//                "('%s', '%s', '%s', '%s', %b)", user.getName(), user.getSurname(), user.getEmail(), user.getPassword(), user.isAdmin());
//        statement.executeUpdate(sql);
//        statement.close();
//    }
//
//    public static void addPlate(Plate plate) throws SQLException {
//        if (DBManager.connection == null) {
//            DBManager.connect();
//        }
//        Statement statement = connection.createStatement();
//        String sql = String.format("INSERT INTO plates (title, preview, description, price) VALUES" +
//                "('%s', '%s', '%s', %d)", plate.getName(), plate.getPreview(), plate.getDescription(), plate.getPrice());
//        statement.executeUpdate(sql);
//        statement.close();
//    }
//
//    public static void addCart(Long user_id, Long plate_id) throws SQLException {
//        if (DBManager.connection == null) {
//            DBManager.connect();
//        }
//        Statement statement = connection.createStatement();
//        String sql = String.format("INSERT INTO carts (user_id, plate_id) VALUES" +
//                "(%d, %d);", user_id, plate_id);
//        statement.executeUpdate(sql);
//        statement.close();
//    }
//
//    public static void removeUser(Long id) throws SQLException {
//        if (DBManager.connection == null) {
//            DBManager.connect();
//        }
//        removeCart(id);
//        Statement statement = connection.createStatement();
//        String sql = String.format("DELETE FROM customers WHERE id = %d", id);
//        statement.executeUpdate(sql);
//        statement.close();
//    }
//
//    public static void removePlate(Long id) throws SQLException {
//        if (DBManager.connection == null) {
//            DBManager.connect();
//        }
//        Statement statement = connection.createStatement();
//        String sql = String.format("DELETE FROM customers_plates WHERE plate_id = %d", id);
//        statement.executeUpdate(sql);
//        sql = String.format("DELETE FROM plates WHERE id = %d", id);
//        statement.executeUpdate(sql);
//        statement.close();
//    }
//
//    public static void removeCart(Long id) throws SQLException {
//        if (DBManager.connection == null) {
//            DBManager.connect();
//        }
//        Statement statement = connection.createStatement();
//        String sql = String.format("DELETE FROM carts WHERE user_id = %d", id);
//        statement.executeUpdate(sql);
//        statement.close();
//    }
//
//    public static void removeCartByPlate(Long user_id, Long plate_id) throws SQLException {
//        if (DBManager.connection == null) {
//            DBManager.connect();
//        }
//        Statement statement = connection.createStatement();
//        String sql = String.format("DELETE FROM carts WHERE plate_id = %d AND user_id = %d", plate_id, user_id);
//        statement.executeUpdate(sql);
//        statement.close();
//    }
//
//    public static void updateUser(Long id, User user) throws SQLException {
//        if (DBManager.connection == null) {
//            DBManager.connect();
//        }
//        Statement statement = connection.createStatement();
//        String sql = String.format("UPDATE customers SET firstname = '%s', surname = '%s', email = '%s', pass = '%s', superuser = %b WHERE id = %d",
//                user.getName(), user.getSurname(), user.getEmail(), user.getPassword(), user.isAdmin(), id);
//        statement.executeUpdate(sql);
//        statement.close();
//    }
//
//    public static void updatePlate(Long id, Plate plate) throws SQLException {
//        if (DBManager.connection == null) {
//            DBManager.connect();
//        }
//        Statement statement = connection.createStatement();
//        String sql = String.format("UPDATE plates SET title = '%s', preview = '%s', description = '%s', price = %d WHERE id = %d",
//                plate.getName(), plate.getPreview(), plate.getDescription(), plate.getPrice(), id);
//        statement.executeUpdate(sql);
//        statement.close();
//    }
//}
