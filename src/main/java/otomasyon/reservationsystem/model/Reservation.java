package otomasyon.reservationsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import otomasyon.reservationsystem.model.Enum.TableType;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservations")
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private TableType tableType;

    private int numberOfPeople;

    private LocalDate reservationDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private int tableNumber;

    private String specialRequests;
}