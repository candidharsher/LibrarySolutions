package serverconnect;

import java.io.*;
import java.net.*;
import java.sql.*;

public class Client {
	private String serverAddress;
	private int serverPort;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private Connection connection;
	private boolean admin;

	public Client(String serverAddress, int serverPort) {
		/*
		 * Hola devreaders!!! Perquè el meu codi Java pugui accedir a un servidor remot
		 * de PostgreSQL amb pgAdmin, necessitaràs configurar el servidor remot i la
		 * connexió al teu codi Java de la següent manera: Assegureu-vos que el servidor
		 * remot tingui PostgreSQL instal·lat i estigui configurat per acceptar
		 * connexions remotes. Habilita l'opció d'accés remot al fitxer de configuració
		 * de PostgreSQL (pg_hba.conf) per permetre connexions des de l'adreça IP de
		 * l'ordinador on s'executarà el codi Java. Configura el fitxer de configuració
		 * de PostgreSQL (postgresql.conf) perquè escolti a l'adreça IP del servidor
		 * remot i el número de port correcte. a l'arxiu persistence.xml reemplaçar
		 * TU_IP per la ip del server remot.
		 */
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;

	}

	public Usuari login(int username, String password) throws IOException {

		// Obtener los datos del usuario desde la base de datos
		String query = "SELECT * FROM public.usuaris WHERE id_usuari = ?";
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, username);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				// Aquí se obtienen otros datos del usuario desde la base de datos

				// Crear un objeto de la clase Usuario con los datos obtenidos

				// Aquí se establecen otros datos del usuario en el objeto Usuario

				int userId = resultSet.getInt("id_usuari");
				String fullName = resultSet.getString("nom_usuari");
				String cognom = resultSet.getString("cognom_usuari");
				String email = resultSet.getString("email_usuari");
				String passwordBD = resultSet.getString("contransenya_usuari");
				String rol = resultSet.getString("rol_usuari");
				if (rol.equals("admin")) {
					this.admin = true;
				} else {
					this.admin = false;
				}
				Usuari usuario = new Usuari(userId, fullName, cognom, email, passwordBD, rol);
				if (passwordBD.equals(password)) {
					// Enviar los datos de login al servidor
					out.println(username);
					out.println(password);

					// Leer la respuesta del servidor
					String response = in.readLine();
					if (response.equals("OK")) {
						System.out.println("Inicio de sesión exitoso.");
					} else {
						System.out.println("Error: no se encontraron datos del usuario en la base de datos.");
						logout(usuario);
					}
				}
				return usuario;
			}

		}

		// Realizar acciones con el objeto Usuario obtenido, como imprimir sus datos
		// Aquí se imprimen otros datos del usuario obtenidos desde el objeto Usuario

		catch (SQLException e) {
			System.out.println("Error de base de datos: " + e.getMessage());
			return null;

		}
		return null;

	}

	public void logout(Usuari usuari) throws IOException, SQLException {
		// Enviar el comando de logout al servidor
		out.println("LOGOUT");

		// Cerrar los streams y el socket
		in.close();
		out.close();
		socket.close();
		// Cerrar la conexión a la base de datos
		connection.close();
		System.out.println("Conexión a la base de datos cerrada.");
	}

	public static void main(String[] args) {
		String serverAddress = "localhost"; // Dirección IP del servidor
		int serverPort = 12345; // Puerto del servidor

		// Crear instancia del cliente
		Client client = new Client(serverAddress, serverPort);

		// Iniciar el cliente
		try {
			client.start();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**
		 * proporcionar la dirección IP correcta y el puerto del servidor donde esté
		 * ejecutándose el servidor Java con la lógica de login y logout junto con la
		 * conexión a la base de datos PostgreSQL
		 */
	}

	public void start() throws SQLException, IOException, ClassNotFoundException {

		// Crear una conexión a la base de datos PostgreSQL
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/library", "postgres",
				"super3");
		System.out.println("Conexión a la base de datos establecida.");

		// Crear el socket del cliente
		socket = new Socket(serverAddress, serverPort);
		System.out.println("Conexión al servidor establecida.");

		// Crear streams de entrada y salida para comunicarse con el servidor
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// Realizar el login del usuario
		System.out.print("Introduzca su nombre de usuario: ");
		String username = new String(System.console().readLine());
		int id = Integer.valueOf(username);
		System.out.print("Introduzca su contraseña: ");
		String password = new String(System.console().readLine());
		Usuari usuario = login(id, password);
		// sessió iniciada
		try {
			usuario.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
