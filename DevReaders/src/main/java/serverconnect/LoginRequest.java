package serverconnect;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters y setters
    // ...
}

