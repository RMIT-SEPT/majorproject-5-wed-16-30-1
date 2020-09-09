package sept.wed16301.backend.auth.owner;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sept.wed16301.backend.auth.AuthResponse;
import sept.wed16301.backend.auth.LoginRequest;
import sept.wed16301.backend.auth.RegisterRequest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OwnerAuthControllerTests {

    private OwnerAuthController ownerAuthController;

    public OwnerAuthControllerTests() {
        this.ownerAuthController = new OwnerAuthController();
    }

    @Test
    void loginValidDetails() {
        // Ensure that a user with the username "testowner1", and password "password123" exists in the owner database.

        // Test the login function.
        LoginRequest loginRequest = new LoginRequest("testowner1", "password123");
        ResponseEntity<AuthResponse> authResponse = ownerAuthController.login(loginRequest);

        // If 200 OK returned, then the test is a success.
        // If 401 Unauthorized returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void loginWrongPassword() {
        // Ensure that no user with the username "testowner2" and password "password123" exists in the owner database.

        // Test the login function.
        LoginRequest loginRequest = new LoginRequest("testowner2", "password123");
        ResponseEntity<AuthResponse> authResponse = ownerAuthController.login(loginRequest);

        // If 401 Unauthorized returned, then the test is a success.
        // If 200 OK returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void loginWrongUsername() {
        // Ensure that a user with the username "testowner1", but without the password "123password" exists in the
        // owner database.

        // Test the login function.
        LoginRequest loginRequest = new LoginRequest("testowner1", "123password");
        ResponseEntity<AuthResponse> authResponse = ownerAuthController.login(loginRequest);

        // If 401 Unauthorized returned, then the test is a success.
        // If 200 OK returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void registerNoExisting() {
        // Ensure that no user with the username "testowner3" exists in the owner database.

        // Test the register function.
        RegisterRequest registerRequest = new RegisterRequest("testowner3", "password123", "password123");
        ResponseEntity<AuthResponse> authResponse = ownerAuthController.register(registerRequest);

        // If 201 Created returned, delete the "testowner3" user recently added to the owner database.
        if (authResponse.getStatusCode() == HttpStatus.CREATED) {
            // Delete "testowner3" from the owner database.
        }

        // If 201 Created returned, then the test is a success.
        // If 409 Conflict returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void registerAlreadyExists() {
        // Ensure that a user with the username "testowner1" already exists in the owner database.

        // Test the register function.
        RegisterRequest registerRequest = new RegisterRequest("testowner1", "password123", "password123");
        ResponseEntity<AuthResponse> authResponse = ownerAuthController.register(registerRequest);

        // If 201 Created returned, delete the "testowner1" user recently added to the owner database.
        if (authResponse.getStatusCode() == HttpStatus.CREATED) {
            // Delete "testowner1" from the owner database.
        }

        // If 409 Conflict returned, then the test is a success.
        // If 201 Created returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void registerPasswordsNoMatch() {
        // Test the register function.
        RegisterRequest registerRequest = new RegisterRequest("testowner1", "password", "password123");
        ResponseEntity<AuthResponse> authResponse = ownerAuthController.register(registerRequest);

        // If 201 Created returned, delete the "testowner1" user recently added to the owner database.
        if (authResponse.getStatusCode() == HttpStatus.CREATED) {
            // Delete "testowner1" from the owner database.
        }

        // If 409 Conflict returned, then the test is a success.
        // If 201 Created returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

}