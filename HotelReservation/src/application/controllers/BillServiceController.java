package application.controllers;

import java.util.ArrayList;
import java.util.List;

import application.classes.Reservation;
import application.classes.room;
import application.database.JdbcDA;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class BillServiceController {

    @FXML
    private TextField Discount;

    @FXML
    private TextField GuesName;

    @FXML
    private TextField Rate;

    @FXML
    private TextField TotalAmount;

    @FXML
    private TextField bookingId;

    @FXML
    private TextField nunmRooms;

    @FXML
    private TextField typeOfRooms;
    
    private Integer bookingID; 

    public void setBookingId(Integer bookingID) {
        this.bookingID = bookingID;
        System.out.println("Got the id");
    }
    
    @FXML
    public void initialize() {
    	if (bookingID != null) {
            JdbcDA jdbcDA = new JdbcDA();
            Reservation reservation = jdbcDA.getReservationById(bookingID);
            if (reservation != null) {
                // Populate the fields with the reservation data
                GuesName.setText("Guest Name: " + reservation.getCustomerName());
                Rate.setText("Rate: " + reservation.getRate());
                bookingId.setText(String.valueOf(bookingID));
                nunmRooms.setText(String.valueOf(reservation.getNumberOfRooms()));
                typeOfRooms.setText(reservation.getTypeOfRooms());
                Rate.setText(String.valueOf(jdbcDA.totalRate));
                
                TotalAmount.setText(String.valueOf( reservation.getRate() * (double)reservation.getNumberOfDays()));
                
                Discount.textProperty().addListener((observable, oldValue, newValue) -> {
                	updateTotalAmount(newValue, reservation.getRate(), reservation.getNumberOfDays());
                });
            }
        }
    }
    
    private void updateTotalAmount(String discountValue, double rate, int numDays) {
        if (discountValue == null || discountValue.isEmpty()) {
            double totalAmount = rate * numDays;
            TotalAmount.setText(String.valueOf( totalAmount));
        } else {
            try {
                double discountPercentage = Double.parseDouble(discountValue);
                double discountedAmount = rate * numDays * (1 - (discountPercentage / 100));
                TotalAmount.setText(String.valueOf( discountedAmount));
            } catch (NumberFormatException e) {
                TotalAmount.setText("Invalid Discount");
            }
        }
    }
    @FXML
    void cancel(ActionEvent event) {
    	Node source = (Node) event.getSource();

        Stage stage = (Stage) source.getScene().getWindow();

        stage.close();
    }

    @FXML
    void confirm(ActionEvent event) {
    	if (!Discount.getText().isEmpty()) {
            try {
                double discountValue = Double.parseDouble(Discount.getText());
                if (discountValue > 25) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Discount");
                    alert.setContentText("Discount value cannot be greater than 25%");
                    alert.showAndWait();
                    return; 
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Discount");
                alert.setContentText("Please enter a valid discount value.");
                alert.showAndWait();
                return; 
            }
        }
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Bill Payment");
        alert.setHeaderText("Please confirm the bill payment.");
        alert.setContentText("Are you sure you want to confirm and proceed with the payment?");

        ButtonType confirmButtonType = new ButtonType("Confirm");
        ButtonType cancelButtonType = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(confirmButtonType, cancelButtonType);

        // Show the confirmation dialog and wait for the user's response
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == confirmButtonType) {
                // Perform the necessary actions here
                performPaymentAndUpdates();
                Node source = (Node) event.getSource();

                Stage stage = (Stage) source.getScene().getWindow();

                stage.close();
            }
        });
    }
    private void performPaymentAndUpdates() {
        JdbcDA jdbcDA = new JdbcDA();
        Reservation reservation = jdbcDA.getReservationById(bookingID);
        
        if (reservation != null) {
            // Update database: Remove registration, update room availability, etc.
            // Example:
            jdbcDA.removeReservation(bookingID);
            
            List<room> roomsToUpdate = reservation.getSelectedRooms();
            List<Integer> roomIds = new ArrayList<>();

            for (room roomObj : roomsToUpdate) {
                roomIds.add(roomObj.getRoomId());
            }
            jdbcDA.updateRoomAvailability(roomIds, reservation.getCustomerId());

            // Show a success message
            Alert successAlert = new Alert(AlertType.INFORMATION);
            successAlert.setTitle("Payment Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Payment has been successfully confirmed and actions performed.");
            successAlert.showAndWait();

            // Close the current window or perform other necessary actions
        }
    }
}
