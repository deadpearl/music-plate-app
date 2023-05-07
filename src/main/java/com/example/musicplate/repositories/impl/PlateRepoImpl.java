package com.example.musicplate.repositories.impl;

import com.example.musicplate.db.HibernateUtil;
import com.example.musicplate.models.Plate;
import com.example.musicplate.models.User;
import com.example.musicplate.repositories.PlateRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PlateRepoImpl implements PlateRepo {
    private final SessionFactory sessionFactory;

    public PlateRepoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addPlate(Plate plate) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(plate);
            tx.commit();
        }
    }

    @Override
    public Plate getPlate(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Plate.class, id);
        }
    }

    @Override
    public void updatePlate(Long id, Plate plate) {
        try (Session session = sessionFactory.openSession()) {
            Plate existingPlate = session.get(Plate.class, id);
            if (existingPlate != null) {
                existingPlate.setName(plate.getName());
                existingPlate.setPreview(plate.getPreview());
                existingPlate.setDescription(plate.getDescription());
                existingPlate.setPrice(plate.getPrice());

                session.update(existingPlate);
            }
        }
    }

    @Override
    public List<Plate> getAllPlates() {
//        List<Plate> plates;
//        try (Session session = sessionFactory.openSession()) {
//            CriteriaBuilder cb = session.getCriteriaBuilder();
//            CriteriaQuery<Plate> cq = cb.createQuery(Plate.class);
//            Root<Plate> root = cq.from(Plate.class);
//            cq.select(root);
//            Query<Plate> query = session.createQuery(cq);
//            plates = query.getResultList();
//        } catch (HibernateException e) {
//            plates = new ArrayList<>();
//            e.printStackTrace();
//        }
//        return plates;

        Session session = HibernateUtil.getSession().getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<Plate> plates = null;
        try {
            Query<Plate> query = session.createQuery("FROM Plate", Plate.class);
            plates = query.getResultList();
            System.out.println(plates);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
        return plates;


    }

    @Override
    public void removePlate(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Plate plate = session.get(Plate.class, id);
            session.delete(plate);
            tx.commit();
        }
    }
}
