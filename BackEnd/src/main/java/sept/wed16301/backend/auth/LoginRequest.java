package sept.wed16301.backend.auth;

import java.security.MessageDigest;

public class LoginRequest {

    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() throws java.security.NoSuchAlgorithmException {
        // Generate password hash.
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encDigest = digest.digest();

        // Convert to string.
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : encDigest) {
            stringBuilder.append(String.format("%02X", b));
        }

        // Set isHashed boolean flag to true, and set password to it's hashed value.
        return stringBuilder.toString();
    }

}
