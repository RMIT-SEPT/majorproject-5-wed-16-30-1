package sept.wed16301.backend.auth.owner;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sept.wed16301.backend.auth.AuthResponse;
import sept.wed16301.backend.auth.LoginRequest;
import sept.wed16301.backend.auth.RegisterRequest;

@RestController
public class OwnerAuthController {

    @PostMapping("/api/auth/owner/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        // Retrieve user from database.
        // If user doesn't exist, return 401 Unauthorized.
        AuthResponse response = new AuthResponse("Invalid username or password.", HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(response, response.getStatus());

        // Check that password hash matches.
        // If password doesn't match, return 401 Unauthorized.

        // Initialise user session.

        // Return 200 OK.
        //AuthResponse response = new AuthResponse("Successfully logged in.", HttpStatus.OK);
        //return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/api/auth/owner/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        // Check if passwords match.
        // If passwords don't match, return 400 Bad Request.

        // Check if user already exists in database.
        // If user already exists, return 409 Conflict.

        // Add user to database if it doesn't exist.

        // Return 201 Created.
        AuthResponse resp = new AuthResponse("Owner successfully created.", HttpStatus.CREATED);
        return new ResponseEntity<>(resp, resp.getStatus());
    }

}
