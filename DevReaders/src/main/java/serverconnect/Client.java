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

	public Client(String serverAddress, int serverPort) {
		/*Hola devreaders!!!
		 * Perquè el meu codi Java pugui accedir a un servidor remot de PostgreSQL amb
		 * pgAdmin, necessitaràs configurar el servidor remot i la connexió al teu codi
		 * Java de la següent manera: Assegureu-vos que el servidor remot tingui
		 * PostgreSQL instal·lat i estigui configurat per acceptar connexions remotes.
		 * Habilita l'opció d'accés remot al fitxer de configuració de PostgreSQL
		 * (pg_hba.conf) per permetre connexions des de l'adreça IP de l'ordinador on
		 * s'executarà el codi Java. Configura el fitxer de configuració de PostgreSQL
		 * (postgresql.conf) perquè escolti a l'adreça IP del servidor remot i el número
		 * de port correcte. a l'arxiu persistence.xml reemplaçar TU_IP per la ip del
		 * server remot.
		 */
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;

	}

	public void login(String username, String password) throws IOException {
		// Enviar los datos de login al servidor
		out.println(username);
		out.println(password);

		// Leer la respuesta del servidor
		String response = in.readLine();
		if (response.equals("OK")) {
			System.out.println("Inicio de sesión exitoso.");
			// Obtener los datos del usuario desde la base de datos
			String query = "SELECT * FROM usuarios WHERE username = ?";
			PreparedStatement statement;
			try {
				statement = connection.prepareStatement(query);
				statement.setString(1, username);
				ResultSet resultSet = statement.executeQuery();
				if (resultSet.next()) {
					int userId = resultSet.getInt("id");
					String fullName = resultSet.getString("full_name");
					String email = resultSet.getString("email");
					// Aquí se obtienen otros datos del usuario desde la base de datos

					// Crear un objeto de la clase Usuario con los datos obtenidos
					Usuari usuario = new Usuari(userId, username, fullName, email);
					// Aquí se establecen otros datos del usuario en el objeto Usuario

					// Realizar acciones con el objeto Usuario obtenido, como imprimir sus datos
					System.out.println("Datos del usuario obtenidos:");
					System.out.println("ID: " + usuario.getId());
					System.out.println("Nombre de usuario: " + usuario.getUsername());
					System.out.println("Nombre completo: " + usuario.getFullName());
					System.out.println("Correo electrónico: " + usuario.getEmail());
					// Aquí se imprimen otros datos del usuario obtenidos desde el objeto Usuario

					// Realizar el logout del usuario
					System.out.println("Realizando logout...");
					out.println("LOGOUT");

				} else {
					System.out.println("Error: no se encontraron datos del usuario en la base de datos.");
				}

			} catch (SQLException e) {
				System.out.println("Error de base de datos: " + e.getMessage());
			}

		} else {
			System.out.println("Error: inicio de sesión fallido.");

		}
	}

	public void logout() throws IOException, SQLException {
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

	public void sendRequest(String request) throws IOException {
		// Enviar la petición al servidor
		out.println(request);

		// Leer la respuesta del servidor
		String response = in.readLine();
		System.out.println("Respuesta del servidor: " + response);
	}

	public void disconnect() throws IOException, SQLException {
		// Enviar el comando de desconexión al servidor
		out.println("DISCONNECT");

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
		try {
			// Crear una conexión a la base de datos PostgreSQL
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/biblioteca", "usuario",
					"contraseña");
			System.out.println("Conexión a la base de datos establecida.");

			// Crear el socket del cliente
			socket = new Socket(serverAddress, serverPort);
			System.out.println("Conexión al servidor establecida.");

			// Crear streams de entrada y salida para comunicarse con el servidor
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// Realizar el login del usuario
			System.out.print("Introduzca su nombre de usuario: ");
			String username = System.console().readLine();
			System.out.print("Introduzca su contraseña: ");
			String password = new String(System.console().readPassword());

			// Enviar los datos al servidor
			out.println(username);
			out.println(password);

			// Leer la respuesta del servidor
			String response = in.readLine();
			if (response.equals("OK")) {
				System.out.println("Inicio de sesión exitoso.");
				// Obtener los datos del usuario desde la base de datos
				String query = "SELECT * FROM usuarios WHERE username = ?";
				PreparedStatement statement = connection.prepareStatement(query);
				statement.setString(1, username);
				ResultSet resultSet = statement.executeQuery();
				if (resultSet.next()) {
					int userId = resultSet.getInt("id");
					String fullName = resultSet.getString("full_name");
					String email = resultSet.getString("email");
					// Aquí se obtienen otros datos del usuario desde la base de datos

					// Crear un objeto de la clase Usuario con los datos obtenidos
					Usuari usuario = new Usuari(userId, username, fullName, email);
					// Aquí se establecen otros datos del usuario en el objeto Usuario

					// Realizar acciones con el objeto Usuario obtenido, como imprimir sus datos
					System.out.println("Datos del usuario obtenidos:");
					System.out.println("ID: " + usuario.getId());
					System.out.println("Nombre de usuario: " + usuario.getUsername());
					System.out.println("Nombre completo: " + usuario.getFullName());
					System.out.println("Correo electrónico: " + usuario.getEmail());
					// Aquí se imprimen otros datos del usuario obtenidos desde el objeto Usuario

					// Realizar el logout del usuario
					System.out.println("Realizando logout...");
					out.println("LOGOUT");

				} else {
					System.out.println("Error: no se encontraron datos del usuario en la base de datos.");
				}

			} else {
				System.out.println("Error: inicio de sesión fallido.");

			}

			// Cerrar los streams y el socket
			in.close();
			out.close();
			socket.close();
			// Cerrar la conexión a la base de datos
			connection.close();
			System.out.println("Conexión a la base de datos cerrada.");
		} catch (IOException e) {
			System.out.println("Error de comunicación con el servidor: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error al conectar a la base de datos: " + e.getMessage());
		}
		// Crear socket y conectarse al servidor
		Socket socket;

		socket = new Socket(serverAddress, serverPort);

		System.out.println("Conexión establecida con el servidor.");

		// Obtener streams de entrada y salida del socket
		ObjectOutputStream out;

		out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		// Lógica de login
		String username = "usuario"; // Nombre de usuario
		String password = "contraseña"; // Contraseña

		// Enviar solicitud de login al servidor
		out.writeObject(new LoginRequest(username, password)); // Enviar objeto LoginRequest al servidor
		out.flush();

		// Recibir respuesta del servidor
		LoginResponse response = (LoginResponse) in.readObject(); // Leer objeto LoginResponse del servidor

		// Verificar respuesta del servidor
		if (response.isSuccess()) {
			System.out.println("Inicio de sesión exitoso.");
			response.setSuccess(true);
			// Realizar acciones adicionales después del login exitoso
		} else {
			System.out.println("Inicio de sesión fallido: " + response.getMessage());
			// Realizar acciones adicionales en caso de login fallido
			response.setSuccess(false);
		}
		// Lógica de logout
		if (response.isSuccess()) {
			// Enviar solicitud de logout al servidor
			out.writeObject(new LogoutRequest(username)); // Enviar objeto LogoutRequest al servidor
			out.flush();

			// Recibir respuesta del servidor
			LogoutResponse logoutResponse = (LogoutResponse) in.readObject(); // Leer objeto LogoutResponse del
																				// servidor

			// Verificar respuesta del servidor
			if (logoutResponse.isSuccess()) {
				logoutResponse.setSuccess(true);
				System.out.println("Cierre de sesión exitoso.");

				// Realizar acciones adicionales después del logout exitoso

				// Obtener información del usuario desde la base de datos
				// Crear conexión a la base de datos
				Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/biblioteca", "usuario",
						"contraseña");

				// Preparar consulta SQL para obtener información del usuario
				String sql = "SELECT * FROM usuaris WHERE nom_usuari = ?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, username);

				// Ejecutar consulta
				ResultSet rs = stmt.executeQuery();

				// Leer datos del resultado de la consulta
				while (rs.next()) {
					int idUsuari = rs.getInt("id_usuari");
					String nomUsuari = rs.getString("nom_usuari");
					String cognomsUsuari = rs.getString("cognoms_usuari");
					String emailUsuari = rs.getString("email_usuari");
					String contransenyaUsuari = rs.getString("contransenya_usuari");
					String rolUsuari = rs.getString("rol_usuari");

					// Crear objeto Usuari con la información obtenida
					Usuari usuari = new Usuari(idUsuari, nomUsuari, cognomsUsuari, emailUsuari, contransenyaUsuari,
							rolUsuari);

					// Realizar acciones con el objeto Usuari
					// Por ejemplo, imprimir la información del usuario
					System.out.println("Información del usuario:");
					System.out.println("ID: " + usuari.getId());
					System.out.println("Nombre: " + usuari.getNombre());
					System.out.println("Apellidos: " + usuari.getApellidos());
					System.out.println("Email: " + usuari.getEmail());
					System.out.println("Contraseña: " + usuari.getContrasenia());
					System.out.println("Rol: " + usuari.getRol());

					// Realizar acciones adicionales con la información del usuario obtenida
					// ...
					// Cerrar conexiones y recursos
					rs.close();
					stmt.close();
					conn.close();
					// Cerrar streams y socket
					out.close();
					in.close();
					socket.close();
				}

			} else {
				System.out.println("Cierre de sesión fallido: " + logoutResponse.getMessage());
				// Realizar acciones adicionales en caso de logout fallido
				logoutResponse.setSuccess(false);
			}

		}

	}
}
