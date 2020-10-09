package sept.wed16301.backend.database;

import java.sql.*;

public class DatabaseConnection {

    private Connection conn;

    public DatabaseConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
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

        // Drop the booking table.
        statement = conn.createStatement();
        statement.executeUpdate("DROP TABLE BOOKING;");
    }

    public void initialise() {
        try {
            createOwnerTable();
            createCustomerTable();
            createWorkerTable();
            createBookingTable();
        }
        catch (SQLException e) {
            // do nothing
        }
    }

    private void createOwnerTable() throws SQLException {
        Statement statement = conn.createStatement();

        statement.executeUpdate("CREATE TABLE OWNER (\n" +
                "username VARCHAR(20) NOT NULL,\n" +
                "passwordHash CHAR(64) NOT NULL,\n" +
                "PRIMARY KEY (username)\n" +
                ");");
    }

    private void createCustomerTable() throws SQLException {
        Statement statement = conn.createStatement();

        statement.executeUpdate("CREATE TABLE CUSTOMER (\n" +
                "username VARCHAR(20) NOT NULL,\n" +
                "passwordHash CHAR(64) NOT NULL,\n" +
                "PRIMARY KEY (username)\n" +
                ");");
    }

    private void createWorkerTable() throws SQLException {
        Statement statement = conn.createStatement();

        statement.executeUpdate("CREATE TABLE WORKER (\n" +
                "username VARCHAR(20) NOT NULL,\n" +
                "passwordHash CHAR(64) NOT NULL,\n" +
                "PRIMARY KEY (username)\n" +
                ");");
    }

    private void createBookingTable() throws SQLException {
        Statement statement = conn.createStatement();

        statement.executeUpdate("CREATE TABLE OWNER (\n" +
                "serviceID TEXT NOT NULL,\n" +
                "workerName TEXT NOT NULL,\n" +
                "serviceName TEXT NOT NULL,\n" +
                "serviceDate TEXT NOT NULL,\n" +
                "PRIMARY KEY (serviceID)\n" +
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
