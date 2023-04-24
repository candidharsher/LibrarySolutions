package serverconnect;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.Scanner;

public class ClientHandler {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private Connection connection;
	private Client client;
	private Scanner sc;

	public ClientHandler(Socket socket, Connection connection) throws IOException {
		this.socket = socket;
		this.connection = connection;

		// Crear los streams de entrada y salida para el socket
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void start() {
		try {
			// Crear los flujos de entrada y salida para la comunicación con el cliente
			
			// Lógica de manejo del cliente
			 // Establecer la conexión con el servidor
            

            // Crear un objeto PrintWriter para enviar solicitudes al servidor
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Enviar una solicitud de conexión al servidor
            out.println("Conectar");

            // Cerrar la conexión
            out.close();
            socket.close();
			// Utilizar inputReader y outputWriter para leer y escribir datos con el cliente

			// Ejemplo de un bucle para recibir y procesar mensajes del cliente
			// String inputLine = in.readLine();
			sc = new Scanner(System.in);
			String inputLine = sc.nextLine();
			System.out.println(inputLine);
			if ((inputLine = in.readLine()) != null) {
				// Procesar el mensaje del cliente
				client.start();

			}
			// Cerrar los flujos de entrada y salida y el socket del cliente
			client.logout();
			in.close();
			out.close();
			socket.close();
			sc.close();

		} catch (IOException e) {
			System.out.println("Error al manejar la conexión del cliente: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
