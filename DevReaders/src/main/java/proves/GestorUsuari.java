/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proves;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class GestorUsuari {
    static final Logger LOGGER = Logger.getLogger(GestorUsuari.class.getName());
   
    Connection conn;

    public GestorUsuari(Connection conn) {
        this.conn=conn;
    }
    public GestorUsuari(){
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
    }

    /**
     * Elimina un ordinador de la base de dades
     *
     * @param nom l'identificador de l'usuari
     * @throws SQLException si no s'ha pogut fger
     * l'eliminació
     */
    public void eliminarUsuari(String nom){
        //TODO
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
     * @throws SQLException si no s'ha pogut fer la
     * modificació
     */
    public void modificarUsuari(Usuari usuari){
        //TODO
        PreparedStatement ps;  
        String sql = "UPDATE public.usuaris SET id_usuari="+usuari.getId()+", nom_usuari="+usuari.getNombre()+", cognoms_usuari="+usuari.getApellidos()+", email_usuari="+usuari.getEmail()+", contransenya_usuari="+usuari.getContrasenia()+", rol_usuari="+usuari.getRol()+"WHERE id_usuari="+usuari.getId()+";";
          try{
          ps = conn.prepareStatement(sql);
          ps.executeQuery();
          ps.close();
          }catch(SQLException ex){
              LOGGER.log(Level.SEVERE, null, ex);
          }
    }

    /**
     * Obte un usuari a partir de l'identificador
     *
     * @param nomUsuari l'identificador
     * @return l'usuari, null si no existeix
     * @throws SQLException si no s'ha pogut obtenir (error accedint a la base de dades)
     */
    public Usuari iniciarSessio(String nomUsuari) {
        Usuari us = null;
        ResultSet rs = null;
        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement("SELECT id_usuari, nom_usuari, cognoms_usuari, email_usuari, contransenya_usuari, rol_usuari FROM public.usuaris WHERE nom_usuari = ?");
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
        return us;
    }

}
