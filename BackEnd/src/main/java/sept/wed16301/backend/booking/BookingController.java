package sept.wed16301.backend.booking;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingController {
    @PostMapping("api/booking")
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest) {
        BookingResponse response;

        //Connect with database

        response = new BookingResponse("Booking was Successful", HttpStatus.CREATED);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("api/booking/")
    public ResponseEntity<BookingResponse> getBooking(@RequestBody BookingRequest bookingRequest) {
        BookingResponse response;

        //Connect with database

        response = new BookingResponse("Successfully retrieved Booking", HttpStatus.OK);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("api/booking")
    public ResponseEntity<BookingResponse> deleteBooking() {
        BookingResponse response;

        //Connect with database

        response = new BookingResponse("Booking was successfully deleted", HttpStatus.OK);
        return new ResponseEntity<>(response, response.getStatus());
    }
}