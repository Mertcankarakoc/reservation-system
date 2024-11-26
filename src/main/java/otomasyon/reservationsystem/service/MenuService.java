package otomasyon.reservationsystem.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import otomasyon.reservationsystem.model.Menu;
import otomasyon.reservationsystem.util.HibernateUtil;

import java.util.List;

public class MenuService {

    public void createMenu(String name, String description, double price) {
        Menu menu = new Menu();
        menu.setName(name);
        menu.setDescription(description);
        menu.setPrice(price);

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(menu);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Menu> getAllMenus() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Menu", Menu.class).list();
        }
    }
}