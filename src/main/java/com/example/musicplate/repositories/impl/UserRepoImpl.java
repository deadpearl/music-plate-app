package com.example.musicplate.repositories.impl;

import com.example.musicplate.db.HibernateUtil;
import com.example.musicplate.models.Cart;
import com.example.musicplate.models.User;
import com.example.musicplate.repositories.UserRepo;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserRepoImpl implements UserRepo {

    @Override
    public User getUser(Long id) {
        Session session = HibernateUtil.getSession().getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        User user = null;
        try {
            user = session.get(User.class, id);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void addUser(User user) {
        Session session = HibernateUtil.getSession().getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(Long id, User user) {
        Session session = HibernateUtil.getSession().getSessionFactory().getCurrentSession();
        User existingUser = session.get(User.class, id);
        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setSurname(user.getSurname());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setAdmin(user.isAdmin());
            session.update(existingUser);
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = HibernateUtil.getSession().getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<User> users = null;
        try {
            Query<User> query = session.createQuery("FROM User", User.class);
            users = query.getResultList();
            System.out.println(users);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
        return users;
    }


    @Override
    public void removeUser(Long id) {
        Session session = HibernateUtil.getSession().getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeCart(Long id) {
        Session session = HibernateUtil.getSession().getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            User user = session.get(User.class, id);
            if (user != null) {
                Cart cart = user.getCart();
                if (cart != null) {
                    session.delete(cart);
                }
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }
}
