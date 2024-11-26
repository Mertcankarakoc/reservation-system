package otomasyon.reservationsystem.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import otomasyon.reservationsystem.model.Reservation;
import otomasyon.reservationsystem.model.User;
import otomasyon.reservationsystem.util.HibernateUtil;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationService {

    public void makeReservation(User user, int numberOfPeople, LocalDate reservationDate, LocalTime startTime, LocalTime endTime, String specialRequests) {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setNumberOfPeople(numberOfPeople);
        reservation.setReservationDate(reservationDate);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setSpecialRequests(specialRequests);

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(reservation);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    }