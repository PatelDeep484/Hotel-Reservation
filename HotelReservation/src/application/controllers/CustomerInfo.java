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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CustomerInfo {

    @FXML
    private TextField Title;

    @FXML
    private TextField address;

    @FXML
    private TextField email;

    @FXML
    private TextField fname;

    @FXML
    private TextField lname;

    @FXML
    private TextField phone;

    private Reservation reservationInfo; 
    
    @FXML
    void cancel(ActionEvent event) {
    	Node source = (Node) event.getSource();

        Stage stage = (Stage) source.getScene().getWindow();

        stage.close();
    }

    public void setReservationInfo(Reservation reservationInfo) {
        this.reservationInfo = reservationInfo;
        System.out.println("Got the reservation");
        // You can use the reservationInfo instance as needed
        // For example, you might want to display reservation details in your UI
    }
    
    
    @FXML
    void confirm(ActionEvent event) {
        if (validateFields()) {
        	JdbcDA da = new JdbcDA();
            int guestId = da.insertCustomer(Title.getText(), fname.getText(), lname.getText(), address.getText(), Integer.parseInt(phone.getText()), email.getText());

        	if(guestId != -1) {
        		System.out.println(guestId);
        		da.updateReservation(reservationInfo.getBookingDate(), reservationInfo.getCheckInDate(), reservationInfo.getCheckOutDate(), guestId, reservationInfo.getNumberOfGuests(), reservationInfo.getNumberOfDays());
        		List<Integer> roomIds = new ArrayList<>();
                for (room selectedRoom : reservationInfo.getSelectedRooms()) {
                    roomIds.add(selectedRoom.getRoomId());
                }

                da.updateRoomAvailabilityForGuest(guestId, roomIds);
        	}
        	
            // Proceed with the confirmation logic
        	Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Do you want to proceed?");

            ButtonType confirmButton = new ButtonType("Confirm");
            ButtonType cancelButton = new ButtonType("Cancel");
            confirmationAlert.getButtonTypes().setAll(confirmButton, cancelButton);

            // Show the alert and proceed only if the user clicks the Confirm button
            if (confirmationAlert.showAndWait().orElse(ButtonType.CANCEL) == confirmButton) {
                // Close the current stage and navigate back to the previous scene
            	Node source = (Node) event.getSource();

                // Get the stage (window) that contains the button
                Stage stage = (Stage) source.getScene().getWindow();

                // Close the stage
                stage.close();
            }
        }
    }

    private boolean validateFields() {
        if (!phone.getText().matches("\\d{10}")) {
            showAlert("Invalid Phone Number", "Phone number should be a 10-digit number.");
            return false;
        }

        if (!email.getText().matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")) {
            showAlert("Invalid Email Address", "Email should be in the format 'something@something.com'.");
            return false;
        }


        return true; // All fields are valid
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
