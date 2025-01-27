package sept.wed16301.backend.auth.customer;

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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CustomerAuthControllerTests {

    private CustomerAuthController customerAuthController;

    public CustomerAuthControllerTests() {
        this.customerAuthController = new CustomerAuthController();
    }

    @BeforeAll
    static void setUp() throws Exception {
        UserDatabase users = new UserDatabase();
        users.reset();

        // Create new customer with username "testcustomer1" and password "password123" in the customer database.
        RegisterRequest registerRequest = new RegisterRequest("testcustomer1", "password123", "password123");
        users.createCustomer(registerRequest);
    }

    @AfterAll
    static void tearDown() throws Exception {
        UserDatabase users = new UserDatabase();
        users.reset();
    }

    @Test
    void loginValidDetails() {
        // Ensure that a user with the username "testcustomer1", and password "password123" exists in the customer
        // database.

        // Test the login function.
        LoginRequest loginRequest = new LoginRequest("testcustomer1", "password123");
        ResponseEntity<Response> authResponse = customerAuthController.login(loginRequest);

        // If 200 OK returned, then the test is a success.
        // If 401 Unauthorized returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void loginWrongUsername() {
        // Ensure that no user with the username "testcustomer2" and password "password123" exists in the customer
        // database.

        // Test the login function.
        LoginRequest loginRequest = new LoginRequest("testcustomer2", "password123");
        ResponseEntity<Response> authResponse = customerAuthController.login(loginRequest);

        // If 401 Unauthorized returned, then the test is a success.
        // If 200 OK returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void loginWrongPassword() {
        // Ensure that a user with the username "testcustomer1", but without the password "123password" exists in the
        // customer database.

        // Test the login function.
        LoginRequest loginRequest = new LoginRequest("testcustomer1", "123password");
        ResponseEntity<Response> authResponse = customerAuthController.login(loginRequest);

        // If 401 Unauthorized returned, then the test is a success.
        // If 200 OK returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void registerNoExisting() {
        // Ensure that no user with the username "testcustomer3" exists in the customer database.

        // Test the register function.
        RegisterRequest registerRequest = new RegisterRequest("testcustomer3", "password123", "password123");
        ResponseEntity<Response> authResponse = customerAuthController.register(registerRequest);

        // If 201 Created returned, then the test is a success.
        // If 409 Conflict returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void registerAlreadyExists() {
        // Ensure that a user with the username "testcustomer1" already exists in the customer database.

        // Test the register function.
        RegisterRequest registerRequest = new RegisterRequest("testcustomer1", "password123", "password123");
        ResponseEntity<Response> authResponse = customerAuthController.register(registerRequest);

        // If 201 Created returned, delete the "testcustomer1" user recently added to the customer database.
        if (authResponse.getStatusCode() == HttpStatus.CREATED) {
            // Delete "testcustomer1" from the customer database.
        }

        // If 409 Conflict returned, then the test is a success.
        // If 201 Created returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void registerPasswordsNoMatch() {
        // Test the register function.
        RegisterRequest registerRequest = new RegisterRequest("testcustomer4", "password", "password123");
        ResponseEntity<Response> authResponse = customerAuthController.register(registerRequest);

        // If 400 Bad Request returned, then the test is a success.
        // If 201 Created returned, then the test is a failure.
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
