package sept.wed16301.backend.booking;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sept.wed16301.backend.Booking;
import sept.wed16301.backend.Response;
import sept.wed16301.backend.auth.RegisterRequest;
import sept.wed16301.backend.database.BookingDatabase;
import sept.wed16301.backend.database.UserDatabase;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class BookingControllerTests {
    private BookingController bookingController;

    public BookingControllerTests() { bookingController = new BookingController(); }

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        BookingDatabase bookings = new BookingDatabase();
        UserDatabase users = new UserDatabase();
        bookings.reset();

        RegisterRequest registerCustomer1 = new RegisterRequest("testcustomer01", "password123", "password123");
        users.createCustomer(registerCustomer1);

        RegisterRequest registerCustomer2 = new RegisterRequest("testcustomer02", "password123", "password123");
        users.createCustomer(registerCustomer2);

        BookingRequest futureBooking = new BookingRequest("01", "testcustomer01", "testworker1", "Hair Dresser", LocalDateTime.now().plusDays(7), 60);
        bookings.createBooking(futureBooking);

        BookingRequest todayBooking = new BookingRequest("02", "testcustomer01", "testworker1", "Hair Dresser", LocalDateTime.now(), 60);
        bookings.createBooking(todayBooking);

        BookingRequest pastBooking = new BookingRequest("03", "testcustomer01", "testworker1", "Hair Dresser", LocalDateTime.now().minusDays(1), 60);
        bookings.createBooking(pastBooking);
    }

    @AfterEach
    void tearDown() throws Exception {
        BookingDatabase bookings = new BookingDatabase();
        UserDatabase users = new UserDatabase();
        bookings.reset();
    }

    @Test
    void createValidBookingInTheFuture() {
        //Ensure a valid booking can be successfully created

        BookingRequest bookingRequest = new BookingRequest("2", "testcustomer01", "testworker2", "Hair Dresser", LocalDateTime.now().plusDays(4), 60);
        ResponseEntity<Response> createBookingResponse = bookingController.createBooking(bookingRequest);

        assertThat(createBookingResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void createBookingWithNonExistingCustomer() throws SQLException, ClassNotFoundException {
        //Ensure that a booking cannot be made with a non existing customer

        BookingRequest bookingRequest = new BookingRequest("3" ,"nonExistingCustomer", "worker2", "Hair Dresser", LocalDateTime.now().plusDays(5), 60);

        ResponseEntity<Response> createBookingResponse = bookingController.createBooking(bookingRequest);

        assertThat(createBookingResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void createBookingWithNonUniqueServiceID() {
        //Ensure that controller catches booking requests that do not have a unique ID

        BookingRequest bookingRequest = new BookingRequest("01" ,"testcustomer01", "testworker2", "Harold Plumping", LocalDateTime.now().plusDays(6), 60);
        ResponseEntity<Response> createBookingResponse = bookingController.createBooking(bookingRequest);

        assertThat(createBookingResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void createBookingInPast() {
        //Ensure booking cannot be made in the past

        BookingRequest bookingRequest = new BookingRequest("4" ,"testcustomer01", "testworker2", "Harold Plumping", LocalDateTime.now().minusDays(1), 60);
        ResponseEntity<Response> createBookingResponse = bookingController.createBooking(bookingRequest);

        assertThat(createBookingResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void newBookingOverlapsWithExistingCustomerBooking() {
        //Ensure that customer bookings do not overlap
        BookingRequest bookingRequest = new BookingRequest("06" ,"testcustomer01", "testworker2", "Harold Plumping", LocalDateTime.now().plusDays(7), 80);
        ResponseEntity<Response> createBookingResponse = bookingController.createBooking(bookingRequest);

        assertThat(createBookingResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void createBookingWithZeroDuration() {
        //Ensure that customer bookings do not overlap
        BookingRequest bookingRequest = new BookingRequest("7" ,"testcustomer01", "testworker2", "Harold Plumping", LocalDateTime.now().plusDays(6), 0);
        ResponseEntity<Response> createBookingResponse = bookingController.createBooking(bookingRequest);

        assertThat(createBookingResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    void deleteFutureBooking() {
        //Ensure future bookings can be deleted successfully

        BookingRequest bookingRequest = new BookingRequest("01", "testcustomer01", "testworker1", "Hair Dresser", LocalDateTime.now().plusDays(7), 60);
        ResponseEntity<Response> deleteBookingResponse = bookingController.deleteBooking(bookingRequest);



        assertThat(deleteBookingResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deleteBookingOccurringToday() {
        //Ensure bookings less than 48 hours away cannot be deleted

        BookingRequest bookingRequest = new BookingRequest("02", "testcustomer01", "testworker1", "Hair Dresser", LocalDateTime.now(), 60);
        ResponseEntity<Response> deleteBookingResponse = bookingController.deleteBooking(bookingRequest);

        assertThat(deleteBookingResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void deleteBookingInThePast() {
        //Ensure bookings made in the past are not deleted

        BookingRequest bookingRequest = new BookingRequest("03", "testcustomer01", "testworker1", "Hair Dresser", LocalDateTime.now().minusDays(1), 60);
        ResponseEntity<Response> deleteBookingResponse = bookingController.deleteBooking(bookingRequest);

        assertThat(deleteBookingResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void retrieveCustomerBookings() {
        //Check that customer bookings can be retrieved
        List<Booking> customerBookings = bookingController.getBookings("testcustomer01");

        //Should retrieve 3 bookings
        assertThat(customerBookings.size()).isEqualTo(3);
    }

    @Test
    void retrieveEmptyListOfBookings() {
        //Check that customer with no bookings returns no bookings
        List<Booking> customerBookings = bookingController.getBookings("testcustomer02");

        //Should retrieve 3 bookings
        assertThat(customerBookings.size()).isEqualTo(0);
    }
}
