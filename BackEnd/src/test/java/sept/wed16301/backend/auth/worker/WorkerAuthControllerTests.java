package sept.wed16301.backend.auth.worker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sept.wed16301.backend.Response;
import sept.wed16301.backend.auth.LoginRequest;
import sept.wed16301.backend.auth.RegisterRequest;
import sept.wed16301.backend.database.UserDatabase;

@SpringBootTest
public class WorkerAuthControllerTests {

    private WorkerAuthController workerAuthController;

    public WorkerAuthControllerTests() {
        workerAuthController = new WorkerAuthController();
    }

    @BeforeAll
    static void setUp() throws Exception {
        UserDatabase users = new UserDatabase();
        users.reset();

        //Create new worker with username "testworker1" and password "password123" in the worker database
        RegisterRequest registerRequest = new RegisterRequest("testworker1", "password123", "password123");
        users.createWorker(registerRequest);
    }

    @AfterAll
    static void tearDown() throws Exception {
        UserDatabase users = new UserDatabase();
        users.reset();
    }

    @Test
    void loginValidDetails() {
        // Ensure that a user with the username "testworker1", and password "password123" exists in the worker database.

        // Test the login function.
        LoginRequest loginRequest = new LoginRequest("testworker1", "password123");
        ResponseEntity<Response> authResponse = workerAuthController.login(loginRequest);

        // If 200 OK returned, then the test is a success.
        // If 401 Unauthorized returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void loginWrongUsername() {
        // Ensure that no user with the username "testworker2" and password "password123" exists in the worker database.

        // Test the login function.
        LoginRequest loginRequest = new LoginRequest("testworker2", "password123");
        ResponseEntity<Response> authResponse = workerAuthController.login(loginRequest);

        // If 401 Unauthorized returned, then the test is a success.
        // If 200 OK returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void loginWrongPassword() {
        // Ensure that a user with the username "testworker1", but without the password "123password" exists in the
        // worker database.

        // Test the login function.
        LoginRequest loginRequest = new LoginRequest("testworker1", "123password");
        ResponseEntity<Response> authResponse = workerAuthController.login(loginRequest);

        // If 401 Unauthorized returned, then the test is a success.
        // If 200 OK returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void registerNoExisting() {
        // Ensure that no user with the username "testworker3" exists in the worker database.

        // Test the register function.
        RegisterRequest registerRequest = new RegisterRequest("testworker3", "password123", "password123");
        ResponseEntity<Response> authResponse = workerAuthController.register(registerRequest);

        // If 201 Created returned, then the test is a success.
        // If 409 Conflict returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void registerAlreadyExists() {
        // Ensure that a user with the username "testworker1" already exists in the worker database.

        // Test the register function.
        RegisterRequest registerRequest = new RegisterRequest("testworker1", "password123", "password123");
        ResponseEntity<Response> authResponse = workerAuthController.register(registerRequest);

        // If 409 Conflict returned, then the test is a success.
        // If 201 Created returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void registerPasswordsNoMatch() {
        // Test the register function.
        RegisterRequest registerRequest = new RegisterRequest("testworker4", "password", "password123");
        ResponseEntity<Response> authResponse = workerAuthController.register(registerRequest);

        // If 400 Bad Request returned, then the test is a success.
        // If 201 Created returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
