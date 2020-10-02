package sept.wed16301.backend.auth.owner;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sept.wed16301.backend.User;
import sept.wed16301.backend.Response;
import sept.wed16301.backend.auth.LoginRequest;
import sept.wed16301.backend.auth.RegisterRequest;
import sept.wed16301.backend.database.UserDatabase;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@RestController
public class OwnerAuthController {

    @PostMapping("/api/auth/owner/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        Response response;

        // If there is no database connection.
        UserDatabase users;
        try {
            users = new UserDatabase();
        }
        catch (Exception e) {
            response = new Response("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // Retrieve user from database.
        User owner = null;
        try {
            owner = users.getOwner(loginRequest.getUsername());
        }
        catch (SQLException e) {
            response = new Response("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // If user doesn't exist, return 401 Unauthorized.
        if (owner == null) {
            response = new Response("Invalid username or password.", HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // Check that password hash matches.
        // If password doesn't match, return 401 Unauthorized.
        try {
            // IDE linting is weird here, users.getOwner can indeed return null.
            System.out.println("PASSWORD HASH: " + loginRequest.getPasswordHash());
            if (owner == null || !owner.getPasswordHash().equals(loginRequest.getPasswordHash())) {
                response = new Response("Invalid username or password.", HttpStatus.UNAUTHORIZED);
                return new ResponseEntity<>(response, response.getStatus());
            }
        }
        catch (NoSuchAlgorithmException e) {
            response = new Response("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // Initialise user session.

        // Return 200 OK.
        response = new Response("Successfully logged in.", HttpStatus.OK);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/api/auth/owner/register")
    public ResponseEntity<Response> register(@RequestBody RegisterRequest registerRequest) {
        Response response;

        // Check if passwords match.
        // If passwords don't match, return 400 Bad Request.
        if (!registerRequest.passwordMatches()) {
            response = new Response("Passwords don't match.", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // If there is no database connection.
        UserDatabase users;
        try {
            users = new UserDatabase();
        }
        catch (Exception e) {
            response = new Response("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // Check if user already exists in database.
        User owner = null;
        try {
            owner = users.getOwner(registerRequest.getUsername());
        }
        catch (SQLException e) {
            response = new Response("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // If user already exists, return 409 Conflict.
        if (owner != null) {
            response = new Response("That username is already taken.", HttpStatus.CONFLICT);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // Add user to database if it doesn't exist.
        try {
            if (!users.createOwner(registerRequest)) {
                response = new Response("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
                return new ResponseEntity<>(response, response.getStatus());
            }
        }
        catch (SQLException e) {
            response = new Response("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // Return 201 Created.
        Response resp = new Response("Owner successfully created.", HttpStatus.CREATED);
        return new ResponseEntity<>(resp, resp.getStatus());
    }

}
