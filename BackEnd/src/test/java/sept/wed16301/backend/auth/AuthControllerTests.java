package sept.wed16301.backend.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sept.wed16301.backend.Response;

@SpringBootTest
public class AuthControllerTests {

    private AuthController authController;

    public AuthControllerTests() {
        this.authController = new AuthController();
    }

    @Test
    void authValidSession() {
        // Ensure that there is a current user session.

        // Test the auth function.
        ResponseEntity<Response> authResponse = authController.index();

        // If 200 OK returned, then the test is a success.
        // If 401 Unauthorized returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void authNoSession() {
        // Ensure that there is no current user session.

        // Test the auth function.
        ResponseEntity<Response> authResponse = authController.index();

        // If 401 Unauthorized returned, then the test is a success.
        // If 200 OK returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void logoutValidSession() {
        // Ensure that there is a current user session.

        // Test the logout function.
        ResponseEntity<Response> authResponse = authController.logout();

        // If 200 OK returned, then the test is a success.
        // If 401 Unauthorized returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void logoutNoSession() {
        // Ensure that there is no current user session.

        // Test the logout function.
        ResponseEntity<Response> authResponse = authController.logout();

        // If 401 Unauthorized returned, then the test is a success.
        // If 200 OK returned, the the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

}
