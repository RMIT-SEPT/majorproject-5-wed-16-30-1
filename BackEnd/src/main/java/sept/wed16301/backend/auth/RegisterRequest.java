package sept.wed16301.backend.auth;

import java.security.MessageDigest;

public class RegisterRequest {

    private String username;
    private String password;
    private String checkPassword;

    public RegisterRequest(String username, String password, String checkPassword) {
        this.username = username;
        this.password = password;
        this.checkPassword = checkPassword;
    }

    public String getUsername() {
        return username;
    }

    public boolean passwordMatches() {
        return password.equals(checkPassword);
    }

    public String getPasswordHash() throws java.security.NoSuchAlgorithmException {
        // Generate password hash.
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encDigest = digest.digest(password.getBytes());

        // Convert to string.
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : encDigest) {
            stringBuilder.append(String.format("%02X", b));
        }

        // Set isHashed boolean flag to true, and set password to it's hashed value.
        return stringBuilder.toString();
    }
}
