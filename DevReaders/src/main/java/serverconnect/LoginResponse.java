package serverconnect;

import java.io.Serializable;

import java.io.Serializable;

public class LoginResponse implements Serializable {
	private boolean success;
	private String message;
	// Otros atributos

	// Constructor
	public LoginResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	// Getter y setter para success y message

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	// Resto de los métodos
}
