package com.example.musicplate.repositories.impl;

import com.example.musicplate.db.HibernateUtil;
import com.example.musicplate.models.Cart;
import com.example.musicplate.models.Plate;
import com.example.musicplate.models.User;
import com.example.musicplate.repositories.CartRepo;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CartRepoImpl implements CartRepo {
    private final SessionFactory sessionFactory;

    public CartRepoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addCart(Cart cart) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(cart);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeCart(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Cart cart = session.get(Cart.class, id);
            if (cart != null) {
                session.delete(cart);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeCartByPlate(Long user_id, Long plate_id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("FROM Cart c JOIN c.plates p WHERE c.userId=:userId AND p.id=:plateId");
        query.setParameter("userId", user_id);
        query.setParameter("plateId", plate_id);
        List<Cart> carts = (List<Cart>) ((org.hibernate.query.Query<?>) query).list();
        for (Cart cart : carts) {
            cart.getPlates().removeIf(p -> p.getId().equals(plate_id));
        }
        tx.commit();
        session.close();
    }

    @Override
    public Cart getCart(Long id) {
        Session session = sessionFactory.openSession();
        Cart cart = null;
        try {
            cart = session.get(Cart.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return cart;
    }

    @Override
    public List<Plate> getCartPlates() {
        Session session = HibernateUtil.getSession().getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<Plate> plates = null;
        try {
            org.hibernate.query.Query<Plate> query = session.createQuery("FROM Plate",
                    Plate.class);
            plates = query.getResultList();
            System.out.println(plates);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
        return plates;

    }
}
