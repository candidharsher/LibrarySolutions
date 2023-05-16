/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proves;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class GestorUsuari {
	static final Logger LOGGER = Logger.getLogger(GestorUsuari.class.getName());
	static List<Usuari> usuarisConnectats = new ArrayList<Usuari>();
	Connection conn;

	public GestorUsuari(Connection conn) {
		this.conn = conn;
	}

	public GestorUsuari() {
		super();
	}

	/**
	 * Insereix un usuari a la base de dades
	 *
	 * @param usuari l'usuari
	 * @throws SQLException si no s'ha pogut fer la inserció
	 */
	public void registrarUsuari(Usuari usuari) throws SQLException {
		String sql = "INSERT INTO public.usuaris(id_usuari, nom_usuari, cognoms_usuari, email_usuari, contransenya_usuari, rol_usuari) VALUES (?, ?, ?, ?, ?, ?)";
		if (usuari.getId() == null) {
			LOGGER.log(Level.SEVERE, "El id del usuario es nulo");
			return;
		}
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, usuari.getId());
			ps.setString(2, usuari.getNombre());
			ps.setString(3, usuari.getApellidos());
			ps.setString(4, usuari.getEmail());
			ps.setString(5, usuari.getContrasenia());
			ps.setString(6, usuari.getRol());
			ps.executeUpdate();
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, null, ex);
		}
		this.usuarisConnectats.add(usuari);
	}

	/**
	 * Elimina un ordinador de la base de dades
	 *
	 * @param nom l'identificador de l'usuari
	 * @throws SQLException si no s'ha pogut fger l'eliminació
	 */
	public void eliminarUsuari(String nom) {
		// TODO
		String sql = "DELETE FROM public.usuaris WHERE nom_usuari = ?";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, nom);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Modificar un usuari de la base de dades
	 *
	 * @param usuari l'usuari a modificar
	 * @throws SQLException si no s'ha pogut fer la modificació
	 */
	public void modificarUsuari(Usuari usuari) {
		// TODO
		PreparedStatement ps;
		String sql = "UPDATE public.usuaris SET id_usuari=" + usuari.getId() + ", nom_usuari=" + usuari.getNombre()
				+ ", cognoms_usuari=" + usuari.getApellidos() + ", email_usuari=" + usuari.getEmail()
				+ ", contransenya_usuari=" + usuari.getContrasenia() + ", rol_usuari=" + usuari.getRol()
				+ "WHERE id_usuari=" + usuari.getId() + ";";
		try {
			ps = conn.prepareStatement(sql);
			ps.executeQuery();
			ps.close();
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Obte un usuari a partir de l'identificador
	 *
	 * @param nomUsuari l'identificador
	 * @return l'usuari, null si no existeix
	 * @throws SQLException si no s'ha pogut obtenir (error accedint a la base de
	 *                      dades)
	 */
	public Usuari iniciarSessio(String nomUsuari) {
		Usuari us = null;
		ResultSet rs = null;
		PreparedStatement pstm = null;
		try {
			pstm = conn.prepareStatement(
					"SELECT id_usuari, nom_usuari, cognoms_usuari, email_usuari, contransenya_usuari, rol_usuari FROM public.usuaris WHERE nom_usuari = ?");
			pstm.setString(1, nomUsuari);
			rs = pstm.executeQuery();
			while (rs.next()) {
				String nom = rs.getString("nom_usuari");
				int id = rs.getInt("id_usuari");
				String cognoms_usuari = rs.getString("cognoms_usuari");
				String email = rs.getString("email_usuari");
				String contrassenya = rs.getString("contransenya_usuari");
				String rol_usuari = rs.getString("rol_usuari");
				us = new Usuari();
				us.setNombre(nom);
				us.setApellidos(cognoms_usuari);
				us.setEmail(email);
				us.setId(id);
				us.setContrasenia(contrassenya);
				us.setRol(rol_usuari);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, null, e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstm != null) {
					pstm.close();
				}
			} catch (SQLException ex) {
				LOGGER.log(Level.SEVERE, null, ex);
			}
		}
		this.usuarisConnectats.add(us);
		return us;
	}

	public void editarPerfil(Usuari usuari) {
		String sql = "UPDATE public.usuaris SET nom_usuari=?, cognoms_usuari=?, email_usuari=?, contransenya_usuari=? WHERE id_usuari=?";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, usuari.getNombre());
			ps.setString(2, usuari.getApellidos());
			ps.setString(3, usuari.getEmail());
			ps.setString(4, usuari.getContrasenia());
			ps.setInt(5, usuari.getId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, null, ex);
		}
	}

	public List<Prestec> consultarPrestamosActivos(Usuari usuari) {
		List<Prestec> prestamos = new ArrayList<Prestec>();
		String sql = "SELECT id_prestem, id_llibre, data_prestec, data_retorn, estat_prestem FROM public.prestems WHERE id_usuari = ? AND estat_prestem = 'ACTIU'";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, usuari.getId());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int idPrestem = rs.getInt("id_prestem");
				int idLlibre = rs.getInt("id_llibre");
				String dataPrestec = rs.getString("data_prestec");
				String dataRetorn = rs.getString("data_retorn");
				String estatPrestem = rs.getString("estat_prestem");
				Date data;
				Prestec prestem = new Prestec();
				try {
					data = new SimpleDateFormat("dd/MM/yyyy").parse(dataPrestec);
					Date dataR = new SimpleDateFormat("dd/MM/yyyy").parse(dataRetorn);
					// Obtener el objeto libro usando el id_llibre
					Llibre llibre = obtenerLlibrePorId(idLlibre);

					prestem.setId(idPrestem);
					prestem.setUsuari(usuari);
					prestem.setLlibre(llibre);
					prestem.setDataPrestem(data);
					prestem.setDataDevolucio(dataR);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				prestamos.add(prestem);

			}

			rs.close();
			ps.close();
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, null, ex);
		}

		return prestamos;
	}

	private Llibre obtenerLlibrePorId(int idLlibre) {
		String sql = "SELECT id_llibre, titol_llibre, autor_llibre, any_publicacio FROM public.llibres WHERE id_llibre = ?";
		Llibre llibre = null;

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, idLlibre);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("id_llibre");
				String titol = rs.getString("titol_llibre");
				int autor = rs.getInt("autor_llibre");
				int anyPublicacio = rs.getInt("any_publicacio");
				llibre = new Llibre(id, titol, autor, anyPublicacio);
			}

			rs.close();
			ps.close();
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, null, ex);
		}

		return llibre;
	}

	/*
	 * Esta función otorgarRol verifica si el usuario que llama a la función tiene
	 * el rol de administrador ("admin"). Si es así, ejecuta la consulta de
	 * actualización para cambiar el rol del usuario especificado por usuari.getId()
	 * al nuevo rol especificado por nuevoRol. Si el usuario que llama a la función
	 * no tiene el rol de administrador, se registra un mensaje de advertencia
	 */
	public void otorgarRol(Usuari usuari, String nuevoRol) {
		if (usuari.getRol().equals("admin")) {
			String sql = "UPDATE public.usuaris SET rol_usuari = ? WHERE id_usuari = ?";

			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, nuevoRol);
				ps.setInt(2, usuari.getId());
				ps.executeUpdate();
				ps.close();
			} catch (SQLException ex) {
				LOGGER.log(Level.SEVERE, null, ex);
			}
		} else {
			LOGGER.log(Level.SEVERE, "No tens permisos d'administrador per atorgar rols.");
		}
	}

	public void logout(Usuari usuari) {
		// Elimina el usuario de la lista de usuarios conectados
		this.getUsuarisConnectats().remove(usuari);

		// Realiza las acciones necesarias para cerrar la sesión del usuario
		// Por ejemplo, cerrar la conexión con la base de datos, eliminar tokens de
		// sesión, etc.

		System.out.println("Sesión cerrada correctamente para el usuario: " + usuari.getNombre());
	}

	private List<Usuari> getUsuarisConnectats() {
		// TODO Auto-generated method stub
		return this.usuarisConnectats;
	}

}
