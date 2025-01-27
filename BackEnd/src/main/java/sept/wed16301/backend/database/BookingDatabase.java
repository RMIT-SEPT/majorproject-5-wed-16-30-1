package sept.wed16301.backend.database;

import sept.wed16301.backend.Booking;
import sept.wed16301.backend.booking.BookingRequest;

import java.text.ParseException;
import java.time.LocalDateTime;
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
            "'" + bookingRequest.getCustomerUsername() + "', " +
            "'" + bookingRequest.getWorkerName() + "', " +
            "'" + bookingRequest.getServiceName() + "', " +
            "'" + bookingRequest.getServiceDate().toString() + "', " +
            "'" + bookingRequest.getDuration() + "');"
        );
    }

    public Booking getBooking(String serviceID) throws SQLException, ParseException {
        ResultSet result = conn.query("SELECT * FROM BOOKING WHERE serviceID='" + serviceID + "';");

        if (!result.next()) {
            return null;
        }

        return new Booking(
                result.getString("serviceID"),
                result.getString("customerUsername"),
                result.getString("workerName"),
                result.getString("serviceName"),
                LocalDateTime.parse(result.getString("serviceDate")),
                Integer.parseInt(result.getString("duration"))
        );
    }

    public ArrayList<Booking> getBookings(String customerUsername) throws SQLException, ParseException {
        ResultSet result = conn.query("SELECT * FROM BOOKING WHERE customerUsername='" + customerUsername + "';");

        ArrayList<Booking> bookings = new ArrayList<>();
//        SimpleDateFormat bookingDate = new SimpleDateFormat(DATE_FORMAT);


        while (result.next()) {

            bookings.add(new Booking(
                    result.getString("serviceID"),
                    result.getString("customerUsername"),
                    result.getString("workerName"),
                    result.getString("serviceName"),
                    LocalDateTime.parse(result.getString("serviceDate")),
//                    bookingDate.parse(result.getString("serviceDate", TimeZone.getTimeZone("AEDT"))),
                    Integer.parseInt(result.getString("duration"))
            ));
        }

        return bookings;
    }

    public ArrayList<Booking> getAllBookings() throws SQLException, ParseException {
        ResultSet result = conn.query("SELECT * FROM BOOKING;");

        ArrayList<Booking> bookings = new ArrayList<>();
//        SimpleDateFormat bookingDate = new SimpleDateFormat(DATE_FORMAT);

        while(result.next()) {
            bookings.add(new Booking(
                    result.getString("serviceID"),
                    result.getString("customerUsername"),
                    result.getString("workerName"),
                    result.getString("serviceName"),
                    LocalDateTime.parse(result.getString("serviceDate")),
//                    bookingDate.parse(result.getString("serviceDate")),
                    Integer.parseInt(result.getString("duration"))
            ));
        }

        return bookings;
    }

    public boolean deleteBooking(BookingRequest bookingRequest) throws SQLException {
        return conn.execute("DELETE FROM BOOKING WHERE serviceID='" + bookingRequest.getServiceID() + "';");
    }

}
