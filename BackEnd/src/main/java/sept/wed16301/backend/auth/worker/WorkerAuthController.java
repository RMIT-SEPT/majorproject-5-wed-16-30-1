package sept.wed16301.backend.auth.worker;

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
public class WorkerAuthController {

    @PostMapping("/api/auth/worker/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        //Capture response
        Response response;

        //Open connection to user Database
        UserDatabase users;
        try {
            users = new UserDatabase();
        } catch(Exception e) {
            response = new Response("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, response.getStatus());
        }

        //Get the worker from User Database
        User worker = null;
        try {
            worker = users.getWorker(loginRequest.getUsername());
        } catch (SQLException e) {
            response = new Response("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, response.getStatus());
        }

        //If worker does not exist - return 401 Unauthorized.
        if(worker == null) {
            response = new Response("Invalid username or password.", HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(response, response.getStatus());
        }

        //Check if password hash matches, otherwise return 401 Unauthorized
        try {
            if(worker == null || !worker.getPasswordHash().equals(loginRequest.getPasswordHash())) {
                response = new Response("Invalid username or password.", HttpStatus.UNAUTHORIZED);
                return new ResponseEntity<>(response, response.getStatus());
            }
        } catch (NoSuchAlgorithmException e) {
            response = new Response("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // Initialise user session.

        // Return 200 OK.
        response = new Response("Successfully logged in.", HttpStatus.OK);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/api/auth/worker/register")
    public ResponseEntity<Response> register(@RequestBody RegisterRequest registerRequest) {
        Response response;

        // Check if passwords match.
        // If passwords don't match, return 400 Bad Request.
        if(!registerRequest.passwordMatches()) {
            response = new Response("Passwords don't match.", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, response.getStatus());
        }

        //Connect to database
        UserDatabase users;
        try {
            users = new UserDatabase();
        } catch (Exception e) {
            response = new Response("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // Check if user already exists in database.
        User worker = null;
        try {
            worker = users.getWorker(registerRequest.getUsername());
        } catch(SQLException e) {
            response = new Response("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // If user already exists, return 409 Conflict.
        if(worker != null) {
            response = new Response("That username is already taken.", HttpStatus.CONFLICT);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // Add user to database if it doesn't exist.
        try {
            if(!users.createWorker(registerRequest)) {
                response = new Response("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
                return new ResponseEntity<>(response, response.getStatus());
            }
        } catch (SQLException e) {
            response = new Response("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, response.getStatus());
        }

        // Return 201 Created.
        Response resp = new Response("Worker successfully created.", HttpStatus.CREATED);
        return new ResponseEntity<>(resp, resp.getStatus());
    }

}
