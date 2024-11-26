package otomasyon.reservationsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import otomasyon.reservationsystem.model.Enum.TableType;
import otomasyon.reservationsystem.model.Reservation;
import otomasyon.reservationsystem.model.User;
import otomasyon.reservationsystem.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationController {

    @FXML
    private GridPane tableGrid;

    @FXML
    private ComboBox<Integer> personCountComboBox;

    @FXML
    private TextField startTimeField;

    @FXML
    private TextArea specialRequestsField;

    private ReservationService reservationService;
    private User currentUser;
    private List<Integer> selectedTableNumbers = new ArrayList<>();
    private static final int MAX_TABLE_SELECTION = 2;

    public ReservationController() {
        this.reservationService = new ReservationService();
        this.currentUser = new User(); // Assume current user is set here
    }

    @FXML
    public void initialize() {
        loadTables();
    }

    private void loadTables() {
        List<Reservation> reservations = reservationService.getReservationsByDate(LocalDate.now());
        boolean[] tableAvailability = new boolean[12];

        for (Reservation reservation : reservations) {
            int tableIndex = reservation.getTableNumber() - 1;
            tableAvailability[tableIndex] = true;
        }

        for (int i = 0; i < 12; i++) {
            final int tableNumber = i + 1;
            Button tableButton = new Button("Table " + tableNumber);
            tableButton.setStyle(tableAvailability[i] ? "-fx-background-color: red;" : "-fx-background-color: blue;");
            tableButton.setOnAction(event -> handleTableSelection(tableButton, tableNumber));
            tableGrid.add(tableButton, i % 4, i / 4);
        }
    }

    private void handleTableSelection(Button tableButton, int tableNumber) {
        if (selectedTableNumbers.contains(tableNumber)) {
            selectedTableNumbers.remove(Integer.valueOf(tableNumber));
            tableButton.setStyle("-fx-background-color: blue;");
        } else if (selectedTableNumbers.size() < MAX_TABLE_SELECTION) {
            selectedTableNumbers.add(tableNumber);
            tableButton.setStyle("-fx-background-color: red;");
        }
    }

    @FXML
    private void handleCreateReservation() {
        try {
            int personCount = personCountComboBox.getValue();
            String startTimeText = startTimeField.getText();

            // Validate time format
            if (!startTimeText.matches("\\d{2}:\\d{2}")) {
                throw new IllegalArgumentException("Invalid time format. Please use HH:mm.");
            }

            LocalTime startTime = LocalTime.parse(startTimeText);
            LocalTime endTime = startTime.plusHours(5);

            // Ensure the end time does not exceed 2 AM
            if (endTime.isAfter(LocalTime.of(2, 0))) {
                endTime = LocalTime.of(2, 0);
            }

            for (int tableNumber : selectedTableNumbers) {
                TableType tableType = (tableNumber <= 8) ? TableType.TABLE : TableType.VIP;
                int maxPersonCount = (tableType == TableType.TABLE) ? 6 : 12;

                if (personCount > maxPersonCount) {
                    throw new IllegalArgumentException("Person count exceeds the limit for the selected table type.");
                }

                reservationService.createReservation(currentUser, tableType, personCount, LocalDate.now(), startTime, endTime, specialRequestsField.getText(), selectedTableNumbers);
            }

            loadTables(); // Refresh table availability

            // Show success message
            showAlert("Success", "Reservation created successfully.");

            // Center the stage on the screen
            Stage currentStage = (Stage) tableGrid.getScene().getWindow();
            currentStage.centerOnScreen();
            currentStage.close();
        } catch (IllegalArgumentException e) {
            // Handle exception (e.g., show an alert)
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}