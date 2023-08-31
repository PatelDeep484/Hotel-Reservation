# Hotel Room Booking System

The Hotel Room Booking System is a Java Eclipse project that utilizes JavaFX and FXML files to create a user-friendly interface for booking hotel rooms. The project runs on a multithreaded server to ensure efficient communication. Data for rooms, registration, users, and customers are stored in a SQL server.

## Features

- User Authentication: Users are required to log in using their credentials before accessing the booking system.
- User Registration: New users can easily register by providing their information, which is securely stored in the SQL server.
- Room Booking: Users can search for available rooms, specify the number of guests, and book a room if it's available.
- Customer Information: Users need to provide relevant information about the guests staying in the booked room.
- Available Rooms: Users can view a list of currently available rooms.
- Current Bookings: Users can see a list of their current bookings.
- Check-out: Users can check-out from their booked rooms when their stay is complete.
- Multithreaded Server: The project utilizes multithreading to handle multiple user requests simultaneously, ensuring responsiveness.

## Prerequisites

- Java Development Kit (JDK) installed
- Eclipse IDE (or any Java IDE of your choice)
- JavaFX libraries
- SQL server (e.g., MySQL, PostgreSQL) for storing project data

## Installation

1. Clone the repository: `git clone https://github.com/your-username/hotel-booking-system.git`
2. Set up the SQL server and create the necessary tables using the provided SQL scripts in the `sql` directory.
3. Open the project in Eclipse IDE.
4. Set up JavaFX libraries in your project build path.

## Usage

1. Run the `ServerMain` class to start the multithreaded server.
2. Launch the JavaFX application by running the `ClientMain` class.
3. The login screen will appear. Existing users can log in, while new users can register.
4. After logging in, you'll be able to:
   - Search for available rooms.
   - Make bookings.
   - View available rooms.
   - See your current bookings.
   - Check-out from booked rooms.
5. Provide guest information as required during the booking process.
6. Data for rooms, registration, users, and customers are securely stored in the SQL server.

## License

This project is licensed under the [MIT License](LICENSE).

## Contact

For any inquiries or suggestions, feel free to contact the project owner at your.pateldeep0295@gmail.com.
