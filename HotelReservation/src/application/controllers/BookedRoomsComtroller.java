package application.controllers;

import application.classes.Reservation;
import application.database.JdbcDA;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class BookedRoomsComtroller {

    @FXML
    private TableView<Reservation> BookedRooms;

    @FXML
    private TableColumn<Reservation, String> custname;

    @FXML
    private TableColumn<Reservation, Integer> id;

    @FXML
    private Label numBooked;

    @FXML
    private TableColumn<Reservation, Integer> numDays;

    @FXML
    private TableColumn<Reservation, Integer> numRoom;

    @FXML
    private TableColumn<Reservation, String> roomType;

    @FXML
    private void initialize() {
    	JdbcDA da = new JdbcDA();
    	
    	custname.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        id.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        numDays.setCellValueFactory(new PropertyValueFactory<>("numberOfDays"));
        numRoom.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        roomType.setCellValueFactory(new PropertyValueFactory<>("typeOfRooms"));

        ObservableList<Reservation> bookedRooms = FXCollections.observableArrayList(da.getBookedRooms());

        BookedRooms.setItems(bookedRooms);

        numBooked.setText(String.valueOf(BookedRooms.getItems().size()));
    }

    
    @FXML
    void close(ActionEvent event) {
    	Node source = (Node) event.getSource();

        Stage stage = (Stage) source.getScene().getWindow();

        stage.close();
    }

}
