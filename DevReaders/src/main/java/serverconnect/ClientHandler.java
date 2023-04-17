package serverconnect;

import java.io.*;
import java.net.*;
import java.sql.*;

public class ClientHandler implements Runnable {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private Connection connection;
	private Client client;

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
			client.start();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start();
	}

	public void start() {
		try {
			// Crear los flujos de entrada y salida para la comunicación con el cliente
			

			// Lógica de manejo del cliente
			// Utilizar inputReader y outputWriter para leer y escribir datos con el cliente

			// Ejemplo de un bucle para recibir y procesar mensajes del cliente
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				// Procesar el mensaje del cliente
				String response = "Respuesta del servidor: " + inputLine;
				out.println(response);
			}

			// Cerrar los flujos de entrada y salida y el socket del cliente
			in.close();
			out.close();
			socket.close();

		} catch (IOException e) {
			System.out.println("Error al manejar la conexión del cliente: " + e.getMessage());
		}
	}

}
