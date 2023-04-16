package serverconnect;

import java.io.Serializable;

public class LogoutResponse implements Serializable {
	private boolean success;
	private String message;
	// Otros atributos

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	// Constructor
	public LogoutResponse(String message) {

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

	// Resto de los métodos
}
