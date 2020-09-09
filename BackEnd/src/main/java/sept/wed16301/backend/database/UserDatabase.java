package sept.wed16301.backend.database;

import sept.wed16301.backend.User;
import sept.wed16301.backend.auth.RegisterRequest;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDatabase {

    private DatabaseConnection conn;

    public UserDatabase() throws SQLException {
        conn = new DatabaseConnection();
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
        ResultSet result = conn.query("SELECT username, passwordHash FROM OWNER" +
                "WHERE username = '" + username + "';");

        if (!result.next()) {
            return null;
        }

        return new User(result.getString("username"), result.getString("password"));
    }

    public User getCustomer(String username) throws SQLException {
        ResultSet result = conn.query("SELECT username, passwordHash FROM CUSTOMER" +
                "WHERE username = '" + username + "';");

        if (!result.next()) {
            return null;
        }

        return new User(result.getString("username"), result.getString("password"));
    }

    public User getWorker(String username) throws SQLException {
        ResultSet result = conn.query("SELECT username, passwordHash FROM WORKER" +
                "WHERE username = '" + username + "';");

        if (!result.next()) {
            return null;
        }

        return new User(result.getString("username"), result.getString("password"));
    }

}
