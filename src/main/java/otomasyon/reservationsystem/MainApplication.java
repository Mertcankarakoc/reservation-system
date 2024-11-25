package otomasyon.reservationsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import otomasyon.reservationsystem.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        startConfiguration();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Merhaba!");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void startConfiguration() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if (sessionFactory != null) {
            System.out.println("Hibernate SessionFactory initialized successfully.");
        } else {
            System.out.println("Failed to initialize Hibernate SessionFactory.");
        }
    }
}