package serverconnect;

import java.io.Serializable;

public class LogoutRequest implements Serializable {
    private String username;

    public LogoutRequest(String username) {
        this.username = username;
    }

    // Getter y setter para el nombre de usuario
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
