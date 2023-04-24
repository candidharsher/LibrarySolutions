package serverconnect;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.Scanner;

public class Client {
	private String serverAddress;
	private int serverPort;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private Connection connection;
	private boolean admin;
	private Scanner sc;

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

	public Usuari login(int username, String password) throws SQLException {
		String query = "SELECT * FROM public.usuaris WHERE id_usuari = ? AND contransenya_usuari = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, username);
		statement.setString(2, password);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			int userId = resultSet.getInt("id_usuari");
			String fullName = resultSet.getString("nom_usuari");
			String cognom = resultSet.getString("cognom_usuari");
			String email = resultSet.getString("email_usuari");
			String passwordBD = resultSet.getString("contransenya_usuari");
			String rol = resultSet.getString("rol_usuari");
			boolean isAdmin = rol.equals("admin");
			Usuari usuario = new Usuari(userId, fullName, cognom, email, passwordBD, rol);
			if (rol.equals("admin")) {
				this.admin = true;
			} else {
				this.admin = false;
			}

			System.out.println("objecte Usuari amb les dades= " + usuario.getId() + usuario.getNombre()
					+ usuario.getApellidos() + usuario.getEmail() + usuario.getRol());
			if (passwordBD.equals(password)) {
				// Enviar los datos de login al servidor
				out.println(username);
				out.println(password);
				// Leer la respuesta del servidor
				/*
				 * String response = in.readLine();
				 * 
				 * if (response.equals("OK")) { System.out.println("Inicio de sesión exitoso.");
				 * } else { System.out.
				 * println("Error: no se encontraron datos del usuario en la base de datos.");
				 * logout(); }
				 */
				return usuario;
			}

		} else {
			System.out.println("Les teves dades no figuren a la nostra BD. Prova a registrar-te.");
			return null;
		}
		System.out.println("Error de Client.login(), s'ha retornat un Usuari buit.");
		return null;
	}

	public Usuari login1(int username, String password) throws IOException {

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
				System.out.println("objecte Usuari amb les dades= " + usuario.getId() + usuario.getNombre()
						+ usuario.getApellidos() + usuario.getEmail() + usuario.getRol());
				if (passwordBD.equals(password)) {
					// Enviar los datos de login al servidor
					out.println(username);
					out.println(password);
					// Leer la respuesta del servidor
					/*
					 * String response = in.readLine();
					 * 
					 * if (response.equals("OK")) { System.out.println("Inicio de sesión exitoso.");
					 * } else { System.out.
					 * println("Error: no se encontraron datos del usuario en la base de datos.");
					 * logout(); }
					 */
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

	/**
	 * public static void main(String[] args) { String serverAddress =
	 * "kandula.db.elephantsql.com"; // Dirección IP del servidor int serverPort =
	 * 5432; // Puerto del servidor
	 * 
	 * // Crear instancia del cliente Client client = new Client(serverAddress,
	 * serverPort);
	 * 
	 * // Iniciar el cliente try { client.start(); } catch (ClassNotFoundException
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
	 * (SQLException e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } /** proporcionar la dirección IP correcta y el puerto
	 * del servidor donde esté ejecutándose el servidor Java con la lógica de login
	 * y logout junto con la conexión a la base de datos PostgreSQL
	 */

	public void start() throws SQLException, IOException, ClassNotFoundException {

		// Crear una conexión a la base de datos PostgreSQL
		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection(
				"jdbc:postgresql://kandula.db.elephantsql.com:5432/uiomrdwe?user=uiomrdwe&password=WrJSaAzjdK8J8Et-KcoQQmniy47rfl3-",
				"uiomrdwe", "zYBtVHDLBIrm7YGFMPvcm7daP5Fru0hL");
		System.out.println("Conexión a la base de datos por parte del cliente establecida.");

		// Crear el socket del cliente
		socket = new Socket(serverAddress, serverPort);
		System.out.println("Conexión al servidor establecida por parte del cliente.");

		// Crear streams de entrada y salida para comunicarse con el servidor
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// Realizar el login del usuario
		sc = new Scanner(System.in);
		System.out.print("Introduzca su id de usuario: ");
		int username = sc.nextInt();
		System.out.print("Introduzca su contraseña: ");
		String password = new String(sc.nextLine());
		Usuari usuario = login(username, password);
		// sessió iniciada
		System.out.println("funció login acabada, ens torna l'usuari " + usuario.toString());
		System.out.println("anem a provar el logout");
		System.out.println("vols fer un logout? Y=sí N=no");
		while (sc.nextLine().equals("Y")) {
			System.out.println("vols fer un logout? Y=sí N=no");

		}
		this.logout();
		socket.close();

	}

}
