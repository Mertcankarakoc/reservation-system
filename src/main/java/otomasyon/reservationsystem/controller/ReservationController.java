package otomasyon.reservationsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class ReservationController {

    @FXML
    private MenuItem rezervasyonMenuItem;

    @FXML
    private MenuItem vipOdaMenuItem;

    @FXML
    private MenuItem menuMenuItem;

    @FXML
    private MenuItem rezervasyonlarimMenuItem;

    @FXML
    private MenuItem siparislerimMenuItem;

    @FXML
    private void handleRezervasyonMenuItemAction(ActionEvent event) {
        loadView(event, "/otomasyon/reservationsystem/reservation-view.fxml", "Rezervasyon");
    }

    @FXML
    private void handleVipOdaMenuItemAction(ActionEvent event) {
        loadView(event, "/otomasyon/reservationsystem/vip-oda-view.fxml", "VIP Oda");
    }

    @FXML
    private void handleMenuMenuItemAction(ActionEvent event) {
        loadView(event, "/otomasyon/reservationsystem/menu-view.fxml", "Menü");
    }

    @FXML
    private void handleRezervasyonlarimMenuItemAction(ActionEvent event) {
        loadView(event, "/otomasyon/reservationsystem/rezervasyonlarim-view.fxml", "Rezervasyonlarım");
    }

    @FXML
    private void handleSiparislerimMenuItemAction(ActionEvent event) {
        loadView(event, "/otomasyon/reservationsystem/siparislerim-view.fxml", "Siparişlerim");
    }

    private void loadView(ActionEvent event, String fxmlFile, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

            // Close the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}