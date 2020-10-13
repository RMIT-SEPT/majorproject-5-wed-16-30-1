package sept.wed16301.backend.booking;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sept.wed16301.backend.Booking;
import sept.wed16301.backend.Response;
import sept.wed16301.backend.User;
import sept.wed16301.backend.database.BookingDatabase;
import sept.wed16301.backend.database.UserDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BookingController {

    static final int MINIMUM_HOURS_AWAY_TO_DELETE_BOOKING = 48;

    @PutMapping("/api/booking/")
    public ResponseEntity<Response> createBooking(@RequestBody BookingRequest bookingRequest) {
        Response response;

        //Check that a valid durations is given
        if(bookingRequest.getDuration() <= 0) {
            response = new Response("Invalid durations was given", HttpStatus.BAD_REQUEST);
            return  new ResponseEntity<>(response, response.getStatus());
        }

        //Open connection to Booking Database
        BookingDatabase bookingDatabase;
        try {
            bookingDatabase = new BookingDatabase();
        } catch(Exception e) {
            response = new Response("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
            return  new ResponseEntity<>(response, response.getStatus());
        }

        //Retrieve customer's bookings
        UserDatabase users;
        User customer = null;

        try{
            users = new UserDatabase();
            customer = users.getCustomer(bookingRequest.getCustomerUsername());
        } catch (Exception e) {
            response = new Response("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
            System.out.println(response.getMessage() + "when trying to retrieve customer bookings");
            return  new ResponseEntity<>(response, response.getStatus());
        }

        //check that customer exists in database
        if(customer == null) {
            response = new Response("Cannot Create Booking as an Invalid User was Given.", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, response.getStatus());
        }

        //Try to retrieve list of customer's existing bookings
        List<Booking> customerBookings = getBookings(bookingRequest.getCustomerUsername());

        //If booking list is not empty check if overlapping bookings exist
        if(customerBookings != null) {
            //Check that new booking doesn't overlap with customer's existing bookings
            if(DateRangesOverlap(bookingRequest, customerBookings)) {
                response = new Response("Booking overlaps with an existing booking", HttpStatus.CONFLICT);
                System.out.println(response.getMessage());
                return new ResponseEntity<>(response, response.getStatus());
            }
        }

        //Check that service ID is unique
        List<Booking> allBookings;
        try {
            allBookings = bookingDatabase.getAllBookings();
        } catch (Exception e) {
            response = new Response("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
            return  new ResponseEntity<>(response, response.getStatus());
        }

        if(allBookings.size() > 0) {
            for(Booking booking : allBookings) {
                if(booking.getServiceID().equals(bookingRequest.getServiceID())) {
                    response = new Response("Service ID is not unique", HttpStatus.CONFLICT);
                    return new ResponseEntity<>(response, response.getStatus());
                }
            }
        }

        //Check if new booking is created in the past
        if(bookingRequest.getServiceDate().isBefore(LocalDateTime.now())) {
            response = new Response("Cannot create a booking in the past", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, response.getStatus());
        }

        response = new Response("Booking was Successful", HttpStatus.CREATED);
        return new ResponseEntity<>(response, response.getStatus());
    }

    private boolean DateRangesOverlap(BookingRequest bookingRequest, List<Booking> userBookings) {
        boolean overLapOccurs = false;

        //Print booking intervals:

        //Get the end time for new booking request
        LocalDateTime newBookingEndTime = bookingRequest.getServiceDate().plusMinutes(bookingRequest.getDuration());

        LocalDateTime currBookingEndTime;
        if(userBookings != null) {
            for (Booking booking : userBookings) {

                //Get the end time for new booking request
                currBookingEndTime = booking.getServiceDate().plusMinutes(booking.getDuration());
                System.out.println();

                System.out.println("Booking Request: start - " + bookingRequest.getServiceDate().toString() + " end - " + newBookingEndTime.toString());
                System.out.println("Customer Booking: start - " + booking.getServiceDate().toString() + " end - " + currBookingEndTime.toString());


//                DateRangesOverlap = max(start1, start2) < min(end1, end2)

                // DateRangesOverlap = max(start1, start2) < min(end1, end2)
//                LocalDateTime maxStart = bookingRequest.getServiceDate();
//                if(booking.getServiceDate().isAfter(bookingRequest.getServiceDate())) {
//                    maxStart = booking.getServiceDate();
//                }
//
//
//                LocalDateTime minEnd = newBookingEndTime;
//                if(currBookingEndTime.isBefore(newBookingEndTime)) {
//                    minEnd = currBookingEndTime;
//                }

//
//                if(maxStart.isBefore(minEnd)) {
//                    overLapOccurs = true;
//                }


//                //overlap is true if startFirst.endTime.isAfter(other.startTime)
//                if(bookingRequest.getServiceDate().isBefore(booking.getServiceDate())) {
//                    System.out.println("startsFirst = booking request");
//                    overLapOccurs = newBookingEndTime.isAfter(booking.getServiceDate());
//                } else {
////                    startsFirst = currBooking
//                    System.out.println("startsFirst = currBooking");
//                    overLapOccurs = currBookingEndTime.isAfter(bookingRequest.getServiceDate());
//
//                }

                // !t1.end.isBefore(t2.begin) && !t1.begin.isAfter(t2.end);
                overLapOccurs = !(currBookingEndTime.isBefore(newBookingEndTime)) &&
                        !(booking.getServiceDate().isAfter(bookingRequest.getServiceDate()));
                System.out.println();

//                if((StartDate1 <= EndDate2) && (StartDate2 <= EndDate1)) {
//                    //overlapping dates
//                }
//                overLapOccurs = booking.getServiceDate().isBefore(newBookingEndTime) && bookingRequest.getServiceDate().isBefore(currBookingEndTime);
            }
        }

        return overLapOccurs;
    }

    @GetMapping("/api/booking/{customerUsername}")
    public List<Booking> getBookings(@PathVariable String customerUsername) {
        // e.g. usage: /api/booking/?customerUsername=Customer1

        List<Booking> allBookings = new ArrayList<>();

        //Open connection to Booking Database
        BookingDatabase bookingDatabase;
        try {
            bookingDatabase = new BookingDatabase();
        } catch (Exception e) {
            return allBookings;
        }

        //Retrieve bookings
        try {
            allBookings = bookingDatabase.getBookings(customerUsername);
        } catch (Exception e) {
            return allBookings;
        }

        return allBookings;
    }

    @DeleteMapping("/api/booking/")
    public ResponseEntity<Response> deleteBooking(@RequestBody BookingRequest bookingRequest) {
        Response response;

        //Open connection to Booking Database
        BookingDatabase bookingDatabase;
        try {
            bookingDatabase = new BookingDatabase();
        } catch (Exception e) {
            response = new Response("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
            return  new ResponseEntity<>(response, response.getStatus());
        }

        //Check that booking is more than 48 hours away
        LocalDateTime minBookingHours = LocalDateTime.now().plusHours(MINIMUM_HOURS_AWAY_TO_DELETE_BOOKING);
        if(bookingRequest.getServiceDate().isBefore(minBookingHours)) { //.before(minBookingHours.getTime())) {
            response = new Response("Cannot delete a booking in the past or less than 48 hours away", HttpStatus.BAD_REQUEST);
            System.out.println(response.getMessage());
            return new ResponseEntity<>(response, response.getStatus());
        }

        //TODO Check that booking request match to booking at given id:

        //Delete the booking
        try {
            bookingDatabase.deleteBooking(bookingRequest);
        } catch (Exception e) {
            response = new Response("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
            return  new ResponseEntity<>(response, response.getStatus());
        }

        response = new Response("Booking was successfully deleted", HttpStatus.OK);
        return new ResponseEntity<>(response, response.getStatus());
    }
}