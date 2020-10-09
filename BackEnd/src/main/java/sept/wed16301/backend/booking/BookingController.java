package sept.wed16301.backend.booking;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sept.wed16301.backend.Response;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookingController {
    @PutMapping("/api/booking/")
    public ResponseEntity<Response> createBooking(@RequestBody BookingRequest bookingRequest) {
        Response response;

        //Connect with database
        //Validate booking

        response = new Response("Booking was Successful", HttpStatus.CREATED);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/api/booking/")
    public List<?> getBookings(@RequestParam String customerUsername) {
        // e.g. usage: /api/booking/?customerUsername=Customer1

        Response response;

        //Connect with database
        //Should handle empty List

        //PLACEHOLDER - should change
        return new ArrayList<Object>();
    }

    @DeleteMapping("/api/booking/")
    public ResponseEntity<Response> deleteBooking(@RequestBody BookingRequest bookingRequest) {
        Response response;

        //Connect with database
        //Ensure booking exists so it can be deleted

        response = new Response("Booking was successfully deleted", HttpStatus.OK);
        return new ResponseEntity<>(response, response.getStatus());
    }
}