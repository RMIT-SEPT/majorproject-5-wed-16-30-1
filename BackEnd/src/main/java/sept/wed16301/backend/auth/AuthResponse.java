package sept.wed16301.backend.auth;

import org.springframework.http.HttpStatus;

public class AuthResponse {

    private String message;
    private HttpStatus status;

    public AuthResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
