package otomasyon.reservationsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import otomasyon.reservationsystem.model.Enum.TableType;
import otomasyon.reservationsystem.model.Reservation;
import otomasyon.reservationsystem.model.User;
import otomasyon.reservationsystem.util.HibernateUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationService {

    public void createReservation(User user, TableType tableType, int numberOfPeople, LocalDate reservationDate, LocalTime startTime, LocalTime endTime, String specialRequests, List<Integer> tableNumbers) throws IllegalArgumentException {
        if (tableNumbers.size() > 2) {
            throw new IllegalArgumentException("A customer can select a maximum of 2 tables.");
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Save the user if not already saved
            if (user.getId() == null) {
                session.save(user);
            }

            for (int tableNumber : tableNumbers) {
                if (tableType == TableType.TABLE && numberOfPeople > 6) {
                    throw new IllegalArgumentException("Normal table reservations can have a maximum of 6 people.");
                } else if (tableType == TableType.VIP && numberOfPeople > 12) {
                    throw new IllegalArgumentException("VIP table reservations can have a maximum of 12 people.");
                }

                Reservation reservation = new Reservation();
                reservation.setUser(user);
                reservation.setTableType(tableType);
                reservation.setNumberOfPeople(numberOfPeople);
                reservation.setReservationDate(reservationDate);
                reservation.setStartTime(startTime);
                reservation.setEndTime(endTime);
                reservation.setSpecialRequests(specialRequests);
                reservation.setTableNumber(tableNumber);

                session.save(reservation);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create reservation", e);
        }
    }

    public List<Reservation> getReservationsByDate(LocalDate reservationDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Reservation where reservationDate = :reservationDate", Reservation.class)
                    .setParameter("reservationDate", reservationDate)
                    .list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get reservations by date", e);
        }
    }
}