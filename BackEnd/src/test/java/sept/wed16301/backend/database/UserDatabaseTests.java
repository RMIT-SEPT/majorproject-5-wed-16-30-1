package sept.wed16301.backend.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sept.wed16301.backend.auth.RegisterRequest;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserDatabaseTests {

    private UserDatabase users;

    public UserDatabaseTests() throws SQLException, ClassNotFoundException {
        users = new UserDatabase();
    }

    @BeforeAll
    static void setUp() throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        //Initialize the database
        databaseConnection.reset();
        databaseConnection.initialise();

        //Fill database with initial data
        databaseConnection.execute("INSERT INTO CUSTOMER VALUES ('testCustomer0', " +
                "'EF92B778BAFE771E89245B89ECBC08A44A4E166C06659911881F383D4473E94F')");

        databaseConnection.execute("INSERT INTO OWNER VALUES ('testOwner0', " +
                "'EF92B778BAFE771E89245B89ECBC08A44A4E166C06659911881F383D4473E94F')");

        databaseConnection.execute("INSERT INTO WORKER VALUES ('testWorker0', " +
                "'EF92B778BAFE771E89245B89ECBC08A44A4E166C06659911881F383D4473E94F')");

    }

    @AfterAll
    static void tearDown() throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        //Clean up the Database
        databaseConnection.reset();
        databaseConnection.initialise();
    }

    @Test
    void createValidOwner() throws SQLException, ClassNotFoundException {
        //Create a new Owner in the database
        RegisterRequest registerRequest = new RegisterRequest("testOwner1", "password123", "password123");
        users.createOwner(registerRequest);

        //Check if testOwner1 was added to the database.
        DatabaseConnection dbconnection = new DatabaseConnection();
        ResultSet resultSet = dbconnection.query("SELECT USERNAME FROM OWNER WHERE USERNAME = 'testOwner1';");

        String databaseUserName = "";
        while (resultSet.next()) {
            databaseUserName = resultSet.getString("username");
        }

        assertThat(registerRequest.getUsername()).isEqualTo(databaseUserName);
    }

    @Test
    void createValidCustomer() throws SQLException, ClassNotFoundException {
        //Create a new Customer in the database
        RegisterRequest registerRequest = new RegisterRequest("testCustomer1", "password123", "password123");
        users.createCustomer(registerRequest);

        //Check if testOwner1 was added to the database.
        DatabaseConnection dbconnection = new DatabaseConnection();
        ResultSet resultSet = dbconnection.query("SELECT USERNAME FROM CUSTOMER WHERE USERNAME = 'testCustomer1';");

        String databaseUserName = "";
        while (resultSet.next()) {
            databaseUserName = resultSet.getString("username");
        }

        assertThat(registerRequest.getUsername()).isEqualTo(databaseUserName);
    }

    @Test
    void createValidWorker() throws SQLException, ClassNotFoundException {
        //Create a new Worker in the database
        RegisterRequest registerRequest = new RegisterRequest("testWorker1", "password123", "password123");
        users.createWorker(registerRequest);

        //Check if testOwner1 was added to the database.
        DatabaseConnection dbconnection = new DatabaseConnection();
        ResultSet resultSet = dbconnection.query("SELECT USERNAME FROM WORKER WHERE USERNAME = 'testWorker1';");

        String databaseUserName = "";
        while (resultSet.next()) {
            databaseUserName = resultSet.getString("username");
        }

        assertThat(registerRequest.getUsername()).isEqualTo(databaseUserName);
    }

    @Test
    void getValidOwner() throws SQLException, ClassNotFoundException {
        //Assuming that owner with the username "testOwner0" exists in the database
        assertThat(users.getOwner("testOwner0").getUsername()).isEqualTo("testOwner0");
    }


    @Test
    void getValidCustomer() throws SQLException, ClassNotFoundException {
        //Assuming that customer with the username "testCustomer0" exists in the database
        assertThat(users.getCustomer("testCustomer0").getUsername()).isEqualTo("testCustomer0");
    }


    @Test
    void getValidWorker() throws SQLException, ClassNotFoundException {
        //Assuming that worker with the username "testWorker0" exists in the database
        assertThat(users.getWorker("testWorker0").getUsername()).isEqualTo("testWorker0");
    }

    @Test
    void resetEmptiesDatabase() throws SQLException, ClassNotFoundException {
        users.reset();
        DatabaseConnection dbconnection = new DatabaseConnection();

        //Get sum of all rows
        int rows = 0;

        ResultSet resultSet = dbconnection.query("SELECT COUNT(*) AS ROWS FROM CUSTOMER;");
        while(resultSet.next()) {
            rows += Integer.parseInt(resultSet.getString(1));
        }

        resultSet = dbconnection.query("SELECT COUNT(*) AS ROWS FROM WORKER;");
        while(resultSet.next()) {
            rows += Integer.parseInt(resultSet.getString(1));
        }

        resultSet = dbconnection.query("SELECT COUNT(*) AS ROWS FROM OWNER;");
        while(resultSet.next()) {
            rows += Integer.parseInt(resultSet.getString(1));
        }
        
        assertThat(rows).isEqualTo(0);

        //Fill the database back up to continue with the rest of the tests
        setUp();
    }
}