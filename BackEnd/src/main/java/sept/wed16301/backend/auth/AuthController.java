package sept.wed16301.backend.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sept.wed16301.backend.Response;

@RestController
public class AuthController {

    @GetMapping("/api/auth")
    public ResponseEntity<Response> index() {
        // Check if valid user session.
        // If invalid, return 401 Unauthorized.

        // Return 200 OK.
        Response resp = new Response("Successfully authorized.", HttpStatus.OK);
        return new ResponseEntity<>(resp, resp.getStatus());
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<Response> logout() {
        // Check if valid user session.
        // If invalid, return 401 Unauthorized.

        // End user session.

        // Return 200 OK.
        Response resp = new Response("Successfully logged out.", HttpStatus.OK);
        return new ResponseEntity<>(resp, resp.getStatus());
    }

}
