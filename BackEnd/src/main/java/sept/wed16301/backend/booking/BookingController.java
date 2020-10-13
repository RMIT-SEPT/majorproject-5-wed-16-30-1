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
                return new ResponseEntity<>(response, response.getStatus());
            }
        }

        //Check that service ID is unique
        List<Booking> allBookings = null;
        try {
            allBookings = bookingDatabase.getAllBookings();
        } catch (Exception e) {
            response = new Response("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
            return  new ResponseEntity<>(response, response.getStatus());
        }

        if(allBookings != null) {
            for(Booking booking : allBookings) {
                if(booking.getServiceID().equals(bookingRequest.getServiceID())) {
                    response = new Response("Service ID is not unique", HttpStatus.CONFLICT);
                    return new ResponseEntity<>(response, response.getStatus());
                }
            }
        }

        //Check if new booking is created in the past
        if(bookingRequest.getServiceDate().compareTo(LocalDateTime.now()) < 0) {
            response = new Response("Cannot create a booking in the past", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, response.getStatus());
        }

        response = new Response("Booking was Successful", HttpStatus.CREATED);
        return new ResponseEntity<>(response, response.getStatus());
    }

    private boolean DateRangesOverlap(BookingRequest bookingRequest, List<Booking> userBookings) {
        boolean overLapOccurs = false;

        //Get the end time for new booking request
        LocalDateTime newBookingEndTime = bookingRequest.getServiceDate().plusMinutes(bookingRequest.getDuration());

//        Calendar endTimeCalendar = Calendar.getInstance();
//        endTimeCalendar.setTime(bookingRequest.getServiceDate());
//        endTimeCalendar.add(Calendar.MINUTE, bookingRequest.getDuration());
//        Date newBookingEndTime = endTimeCalendar.getTime();


        if(userBookings != null) {
            for (Booking booking : userBookings) {
                //Get the end time for new booking request
                LocalDateTime currBookingEndTime = booking.getServiceDate().plusMinutes(booking.getDuration());

//                Calendar currEndTimeCalendar = Calendar.getInstance();
//                currEndTimeCalendar.setTime(bookingRequest.getServiceDate());
//                currEndTimeCalendar.add(Calendar.MINUTE, bookingRequest.getDuration());
//                Date currBookingEndTime = currEndTimeCalendar.getTime();


                // DateRangesOverlap = max(start1, start2) < min(end1, end2)

                LocalDateTime maxStart = bookingRequest.getServiceDate();
                if(booking.getServiceDate().compareTo(bookingRequest.getServiceDate()) > 0) {
                    maxStart = booking.getServiceDate();
                }

                LocalDateTime minEnd = newBookingEndTime;
                if(currBookingEndTime.compareTo(newBookingEndTime) < 0) {
                    minEnd = currBookingEndTime;
                }

                if(maxStart.compareTo(minEnd) < 0) {
                    overLapOccurs = true;
                }

//                if(Math.max(booking.getServiceDate().toEpochSecond(ZoneOffset.UTC), bookingRequest.getServiceDate().toEpochSecond(ZoneOffset.UTC))
//                        < Math.min(currBookingEndTime.toEpochSecond(ZoneOffset.UTC), newBookingEndTime.toEpochSecond(ZoneOffset.UTC))) {
//                    overLapOccurs = true;
//                }
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

        //Get date 'MINIMUM_HOURS_AWAY_TO_DELETE_BOOKING' hours away
        LocalDateTime minBookingHours = bookingRequest.getServiceDate().plusHours(MINIMUM_HOURS_AWAY_TO_DELETE_BOOKING);

//        Calendar minBookingHours = Calendar.getInstance();
//        minBookingHours.setTime(new Date());
//        minBookingHours.add(Calendar.HOUR_OF_DAY, MINIMUM_HOURS_AWAY_TO_DELETE_BOOKING);

        if(bookingRequest.getServiceDate().compareTo(minBookingHours) < 0) { //.before(minBookingHours.getTime())) {
            response = new Response("Cannot delete a booking in the past or less than 48 hours away", HttpStatus.BAD_REQUEST);
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