package sept.wed16301.backend.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sept.wed16301.backend.booking.BookingRequest;
import sept.wed16301.backend.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
//import java.time.LocalDate;
import java.time.LocalDateTime;
//import java.util.Calendar;
//import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BookingDatabaseTests {

    private BookingDatabase bookings;

    public BookingDatabaseTests() throws SQLException, ClassNotFoundException {
        bookings = new BookingDatabase();
    }

    @BeforeAll
    static void setUp() throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        //Initialize the database
        databaseConnection.reset();
        databaseConnection.initialise();

//        Calendar bookingTime = Calendar.getInstance();
//        bookingTime.setTime(new Date());
//        bookingTime.add(Calendar.DATE, 6);


        databaseConnection.execute("INSERT INTO CUSTOMER VALUES ('testcustomer1', 'password123');");

        databaseConnection.execute("INSERT INTO BOOKING VALUES ('xyz', 'testcustomer1', 'Steve', '@xyz', '" + LocalDateTime.now().plusDays(6).toString() + "', 60);");

    }

    @AfterAll
    static void tearDown() throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        //Clean up the Database
        databaseConnection.reset();
        databaseConnection.initialise();
    }

    @Test
    void createValidBooking() throws SQLException, ClassNotFoundException, ParseException {
        // Create a Booking in the database
        BookingRequest bookingRequest = new BookingRequest("abcd", "testcustomer1", "Steve", "@abcd", LocalDateTime.now(), 60);
        assertThat(bookings.createBooking(bookingRequest)).isEqualTo(true);

        // Check if new booking as added to the database.
        Booking booking = bookings.getBooking("abcd");
        assertThat(booking.getServiceID()).isEqualTo("abcd");
    }

    @Test
    void getValidBookings() throws SQLException, ClassNotFoundException {
        // Not implemented yet.
        assertThat(0).isEqualTo(1);
    }

    @Test
    void deleteValidBooking() throws SQLException, ClassNotFoundException {
        BookingRequest bookingRequest = new BookingRequest("xyz", "testcustomer1", "Steve", "@xyz", LocalDateTime.now(), 60);
        assertThat(bookings.deleteBooking(bookingRequest)).isEqualTo(true);
    }

    @Test
    void resetEmptiesDatabase() throws SQLException, ClassNotFoundException {
        bookings.reset();
        DatabaseConnection dbconnection = new DatabaseConnection();

        //Get sum of all rows
        int rows = 0;

        ResultSet resultSet = dbconnection.query("SELECT COUNT(*) AS ROWS FROM BOOKING;");
        while(resultSet.next()) {
            rows += Integer.parseInt(resultSet.getString(1));
        }

        assertThat(rows).isEqualTo(0);

        //Fill the database back up to continue with the rest of the tests
        setUp();
    }
}