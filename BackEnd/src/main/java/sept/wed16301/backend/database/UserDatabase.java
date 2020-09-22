package sept.wed16301.backend.database;

import sept.wed16301.backend.User;
import sept.wed16301.backend.auth.RegisterRequest;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDatabase {

    private DatabaseConnection conn;

    public UserDatabase() throws SQLException, ClassNotFoundException {
        conn = new DatabaseConnection();

        if (System.getenv("RESET_DB") != null) {
            conn.reset();
        }

        conn.initialise();
    }

    public void reset() throws SQLException {
        conn.reset();
        conn.initialise();
    }

    public boolean createOwner(RegisterRequest registerRequest) throws SQLException {
        String username = registerRequest.getUsername();
        String passwordHash;

        try {
            passwordHash = registerRequest.getPasswordHash();
        }
        catch (NoSuchAlgorithmException e) {
            return false;
        }

        return conn.execute("INSERT INTO OWNER VALUES ('" + username + "', '" + passwordHash + "');");
    }

    public boolean createCustomer(RegisterRequest registerRequest) throws SQLException {
        String username = registerRequest.getUsername();
        String passwordHash;

        try {
            passwordHash = registerRequest.getPasswordHash();
        }
        catch (NoSuchAlgorithmException e) {
            return false;
        }

        return conn.execute("INSERT INTO CUSTOMER VALUES ('" + username + "', '" + passwordHash + "');");
    }

    public boolean createWorker(RegisterRequest registerRequest) throws SQLException {
        String username = registerRequest.getUsername();
        String passwordHash;

        try {
            passwordHash = registerRequest.getPasswordHash();
        }
        catch (NoSuchAlgorithmException e) {
            return false;
        }

        return conn.execute("INSERT INTO WORKER VALUES ('" + username + "', '" + passwordHash + "');");
    }

    public User getOwner(String username) throws SQLException {
        ResultSet result = conn.query("SELECT username, passwordHash FROM OWNER\n" +
                "WHERE username = '" + username + "';");

        if (!result.next()) {
            return null;
        }

        return new User(result.getString("username"), result.getString("passwordHash"));
    }

    public User getCustomer(String username) throws SQLException {
        ResultSet result = conn.query("SELECT username, passwordHash FROM CUSTOMER\n" +
                "WHERE username = '" + username + "';");

        if (!result.next()) {
            return null;
        }

        return new User(result.getString("username"), result.getString("passwordHash"));
    }

    public User getWorker(String username) throws SQLException {
        ResultSet result = conn.query("SELECT username, passwordHash FROM WORKER\n" +
                "WHERE username = '" + username + "';");

        if (!result.next()) {
            return null;
        }

        return new User(result.getString("username"), result.getString("passwordHash"));
    }

}
