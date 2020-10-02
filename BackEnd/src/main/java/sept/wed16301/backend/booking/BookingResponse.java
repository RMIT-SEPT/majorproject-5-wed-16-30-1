package sept.wed16301.backend.booking;

import org.springframework.http.HttpStatus;

public class BookingResponse {
    private String message;
    private HttpStatus status;

    public BookingResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() { return message; }

    public HttpStatus getStatus() { return status; }
}
