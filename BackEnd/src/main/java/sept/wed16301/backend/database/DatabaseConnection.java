package sept.wed16301.backend.database;

import java.sql.*;

public class DatabaseConnection {

    private Connection conn;

    public DatabaseConnection() throws SQLException {
        conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/main");

        if (conn != null) {
            System.out.println("Database connection successful.");
        }
        else {
            System.out.println("Database connection failed.");
        }
    }

    public void reset() throws SQLException {
        // Drop the owner table.
        Statement statement = conn.createStatement();
        statement.executeUpdate("DROP TABLE OWNER;");

        // Drop the customer table.
        statement = conn.createStatement();
        statement.executeUpdate("DROP TABLE CUSTOMER;");

        // Drop the worker table.
        statement = conn.createStatement();
        statement.executeUpdate("DROP TABLE WORKER;");
    }

    public void initialise() throws SQLException {
        createOwnerTable();
        createCustomerTable();
        createWorkerTable();
    }

    private void createOwnerTable() throws SQLException {
        Statement statement = conn.createStatement();

        statement.executeUpdate("CREATE TABLE OWNER (" +
                "username VARCHAR(20) NOT NULL," +
                "passwordHash CHAR(64) NOT NULL," +
                "PRIMARY KEY (username)" +
                ");");
    }

    private void createCustomerTable() throws SQLException {
        Statement statement = conn.createStatement();

        statement.executeUpdate("CREATE TABLE CUSTOMER (" +
                "username VARCHAR(20) NOT NULL," +
                "passwordHash CHAR(64) NOT NULL," +
                "PRIMARY KEY (username)" +
                ");");
    }

    private void createWorkerTable() throws SQLException {
        Statement statement = conn.createStatement();

        statement.executeUpdate("CREATE TABLE WORKER (" +
                "username VARCHAR(20) NOT NULL," +
                "passwordHash CHAR(64) NOT NULL," +
                "PRIMARY KEY (username)" +
                ");");
    }

    public boolean execute(String query) throws SQLException {
        Statement statement = conn.createStatement();

        int result = statement.executeUpdate(query);
        conn.commit();

        return result != 0;
    }

    public ResultSet query(String query) throws SQLException {
        Statement statement = conn.createStatement();
        return statement.executeQuery(query);
    }

}
