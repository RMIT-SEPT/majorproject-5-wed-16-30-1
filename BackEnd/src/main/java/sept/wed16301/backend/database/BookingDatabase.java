package sept.wed16301.backend.database;

import sept.wed16301.backend.Booking;
import sept.wed16301.backend.auth.RegisterRequest;
import sept.wed16301.backend.booking.BookingRequest;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingDatabase {

    private DatabaseConnection conn;

    public BookingDatabase() throws SQLException, ClassNotFoundException {
        conn = new DatabaseConnection();

        if (System.getenv("RESET_DB") != null) {
            this.reset();
        }

        conn.initialise();
    }

    public void reset() throws SQLException {
        conn.reset();
        conn.initialise();
    }

    public boolean createBooking(BookingRequest bookingRequest) throws SQLException {
        return conn.execute(
            "INSERT INTO BOOKING VALUES (" +
            "'" + bookingRequest.getServiceID() + "', " +
            "'" + bookingRequest.getWorkerName() + "', " +
            "'" + bookingRequest.getServiceName() + "', " +
            "'" + bookingRequest.getServiceDate() + "');"
        );
    }

    public Booking getBooking(String serviceID) throws SQLException {
        ResultSet result = conn.query("SELECT * FROM BOOKING WHERE serviceID='" + serviceID + "';");

        if (!result.next()) {
            return null;
        }

        return new Booking(
                result.getString("serviceID"),
                result.getString("workerName"),
                result.getString("serviceName"),
                result.getString("serviceDate")
        );
    }

    public ArrayList<Booking> getBookings() throws SQLException {
        ResultSet result = conn.query("SELECT * FROM BOOKING;");

        ArrayList<Booking> bookings = new ArrayList<>();
        while (!result.next()) {
            bookings.add(new Booking(
                    result.getString("serviceID"),
                    result.getString("workerName"),
                    result.getString("serviceName"),
                    result.getString("serviceDate")
            ));
        }

        return bookings;
    }

    public boolean deleteBooking(BookingRequest bookingRequest) throws SQLException {
        return conn.execute("DELETE FROM BOOKING WHERE serviceID='" + bookingRequest.getServiceID() + "';");
    }

}
