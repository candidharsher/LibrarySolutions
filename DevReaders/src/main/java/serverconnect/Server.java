package serverconnect;

import java.io.*;
import java.net.*;
import java.sql.*;

public class Server {
	/*
	 * La classe Server permetrà establir una connexió a la base de dades
	 * PostgreSQL i obrir un socket per escoltar connexions de clients. La classe
	 * ClientHandler s'encarregarà de manejar cada connexió individual amb un
	 * client, incloent-hi l'autenticació de l'usuari mitjançant el procés de login
	 * i logout. La classe Usuari representa el model de dades d'un usuari a la
	 * nostra aplicació i la classe Client representa el model de dades d'un client
	 * connectat al servidor.
	 */
	private int port;
	private ServerSocket serverSocket;
	private boolean running;
	private Connection connection;

	public Server(int port) {
		this.port = port;
		running = false;
	}

	public void start() {
		try {
			// Crear una conexión a la base de datos PostgreSQL
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/library", "postgres",
					"super3");
			System.out.println("Conexión a la base de datos establecida.");

			// Crear el socket del servidor en el puerto especificado
			serverSocket = new ServerSocket(port);
			System.out.println("Servidor iniciado en el puerto " + port + "...");

			running = true;
			while (running) {
				// Esperar a que un cliente se conecte
				Socket clientSocket = serverSocket.accept();
				System.out.println("Cliente conectado desde " + clientSocket.getInetAddress());

				// Crear un nuevo hilo para manejar la conexión con el cliente
				ClientHandler clientHandler = new ClientHandler(clientSocket, connection);
				clientHandler.start();
			}
		} catch (IOException e) {
			System.out.println("Error al iniciar el servidor: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error al conectar a la base de datos: " + e.getMessage());
		}
	}

	public void stop() {
		try {
			// Cerrar la conexión a la base de datos
			if (connection != null && !connection.isClosed()) {
				connection.close();
				System.out.println("Conexión a la base de datos cerrada.");
			}

			// Cerrar el socket del servidor
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
				System.out.println("Servidor detenido.");
			}
		} catch (IOException e) {
			System.out.println("Error al detener el servidor: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error al cerrar la conexión a la base de datos: " + e.getMessage());
		}
		running = false;
	}

	public static void main(String[] args) {
		int port = 5432; // Puerto del servidor

		// Crear instancia del servidor
		Server server = new Server(port);

		// Iniciar el servidor
		server.start();
	}
}
