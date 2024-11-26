package otomasyon.reservationsystem.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import otomasyon.reservationsystem.model.Order;
import otomasyon.reservationsystem.model.User;
import otomasyon.reservationsystem.util.HibernateUtil;

import java.time.LocalDateTime;
import java.util.List;

public class OrderService {

    public void createOrder(User user, String details) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setDetails(details);

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Order> getOrdersByUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Order where user = :user", Order.class)
                    .setParameter("user", user)
                    .list();
        }
    }
}