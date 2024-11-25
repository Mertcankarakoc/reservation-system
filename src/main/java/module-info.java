module otomasyon.reservationsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires lombok;
    requires mysql.connector.j;
    requires java.sql;

    opens otomasyon.reservationsystem to javafx.fxml, javafx.graphics;
    opens otomasyon.reservationsystem.model to org.hibernate.orm.core;

    exports otomasyon.reservationsystem;
    exports otomasyon.reservationsystem.util;
    exports otomasyon.reservationsystem.service;
    exports otomasyon.reservationsystem.model;
    exports otomasyon.reservationsystem.controller;
    opens otomasyon.reservationsystem.controller to javafx.fxml;
}