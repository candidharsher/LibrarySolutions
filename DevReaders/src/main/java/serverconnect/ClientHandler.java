package serverconnect;

import java.io.*;
import java.net.*;
import java.sql.*;

public class ClientHandler implements Runnable {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private Connection connection;

	public ClientHandler(Socket socket, Connection connection) throws IOException {
		this.socket = socket;
		this.connection = connection;

		// Crear los streams de entrada y salida para el socket
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	@Override
	public void run() {
		try {
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.equals("LOGOUT")) {
					// Realizar el logout del usuario
					System.out.println("Realizando logout...");
					out.println("OK");
					break;
				} else if (inputLine.equals("DISCONNECT")) {
					// Realizar la desconexión del cliente
					System.out.println("Realizando desconexión...");
					out.println("OK");
					break;
				} else {
					// Procesar la petición del cliente
					System.out.println("Petición del cliente: " + inputLine);
					// Aquí se puede implementar la lógica de negocio específica del servidor
					// basada en la petición del cliente
					out.println("Respuesta del servidor a la petición: " + inputLine);
				}
			}

			// Cerrar los streams y el socket
			in.close();
			out.close();
			socket.close();

		} catch (IOException e) {
			System.out.println("Error de E/S: " + e.getMessage());
		}
	}

	public void start() {
		try {
			// Crear los flujos de entrada y salida para la comunicación con el cliente
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true);

			// Lógica de manejo del cliente
			// Utilizar inputReader y outputWriter para leer y escribir datos con el cliente

			// Ejemplo de un bucle para recibir y procesar mensajes del cliente
			String inputLine;
			while ((inputLine = inputReader.readLine()) != null) {
				// Procesar el mensaje del cliente
				String response = "Respuesta del servidor: " + inputLine;
				outputWriter.println(response);
			}

			// Cerrar los flujos de entrada y salida y el socket del cliente
			inputReader.close();
			outputWriter.close();
			socket.close();

		} catch (IOException e) {
			System.out.println("Error al manejar la conexión del cliente: " + e.getMessage());
		}
	}

}
