module HotelReservation {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml;
	exports application.controllers to javafx.fxml;
	opens application.controllers to javafx.fxml;
	opens application.classes to javafx.base;
}
