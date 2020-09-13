package sept.wed16301.backend.auth.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sept.wed16301.backend.User;
import sept.wed16301.backend.auth.AuthResponse;
import sept.wed16301.backend.auth.LoginRequest;
import sept.wed16301.backend.auth.RegisterRequest;
import sept.wed16301.backend.database.UserDatabase;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@RestController
public class CustomerAuthController {

    @PostMapping("/api/auth/customer/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse response;

        //Connect to database
        UserDatabase users;
        try {
            users = new UserDatabase();
        } catch (Exception e) {
            response = new AuthResponse("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // Retrieve user from database.
        User customer = null;
        try {
            customer = users.getCustomer(loginRequest.getUsername());
        } catch(SQLException e) {
            response = new AuthResponse("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // If user doesn't exist, return 401 Unauthorized.
        if(customer == null) {
            response = new AuthResponse("Invalid username or password.", HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // Check that password hash matches.
        // If password doesn't match, return 401 Unauthorized.
        try {
            boolean passwordMatch = customer.getPasswordHash().equals(loginRequest.getPasswordHash());
            if(customer == null || !passwordMatch) {
                response = new AuthResponse("Invalid username or password.", HttpStatus.UNAUTHORIZED);
                return new ResponseEntity<>(response, response.getStatus());
            }
        } catch (NoSuchAlgorithmException e) {
            response = new AuthResponse("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // Initialise user session.

        // Return 200 OK.
        response = new AuthResponse("Successfully logged in.", HttpStatus.OK);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/api/auth/customer/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        AuthResponse response;

        // Check if passwords match.
        // If passwords don't match, return 400 Bad Request.
        if(!registerRequest.passwordMatches()) {
            response = new AuthResponse("Passwords don't match.", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, response.getStatus());
        }

        //Connect to database
        UserDatabase users;
        try {
            users = new UserDatabase();
        } catch (Exception e) {
            response = new AuthResponse("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // Check if user already exists in database.
        User customer = null;
        try {
            customer = users.getCustomer(registerRequest.getUsername());
        } catch(SQLException e) {
            response = new AuthResponse("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // If user already exists, return 409 Conflict.
        if(customer != null) {
            response = new AuthResponse("That username is already taken.", HttpStatus.CONFLICT);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // Add user to database if it doesn't exist.
        try {
            if(!users.createCustomer(registerRequest)) {
                response = new AuthResponse("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
                return new ResponseEntity<>(response, response.getStatus());
            }
        } catch (SQLException e) {
            response = new AuthResponse("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // Return 201 Created.
        AuthResponse resp = new AuthResponse("Customer successfully created.", HttpStatus.CREATED);
        return new ResponseEntity<>(resp, resp.getStatus());
    }

}
