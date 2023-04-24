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
        //TODO
        PreparedStatement ps;  
        String sql = "INSERT INTO library1.usuaris(id_usuari, nom_usuari, cognoms_usuari, email_usuari, contransenya_usuari, rol_usuari)VALUES ("+usuari.getId()+","+usuari.getNombre()+","+usuari.getApellidos()+","+usuari.getContrasenia()+","+usuari.getContrasenia()+","+usuari.getRol()+");";
          try{
          ps = conn.prepareStatement(sql);
          ps.executeQuery();
          ps.close();
          }catch(SQLException ex){
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
        String sql= "DELETE FROM library1.usuaris WHERE nom_usuari ="+ nom +";";
        PreparedStatement ps;
        try{
          ps = conn.prepareStatement(sql);
          ps.executeQuery();
          ps.close();
          }catch(SQLException ex){
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
        String sql = "UPDATE library1.usuaris SET id_usuari="+usuari.getId()+", nom_usuari="+usuari.getNombre()+", cognoms_usuari="+usuari.getApellidos()+", email_usuari="+usuari.getEmail()+", contransenya_usuari="+usuari.getContrasenia()+", rol_usuari="+usuari.getRol()+"WHERE id_usuari="+usuari.getId()+";";
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
    public Usuari iniciarSessio(String nomUsuari)  {
        //TODO
        Usuari us = null;
        ResultSet rs = null;
PreparedStatement pstm=null;
try {
pstm = conn.prepareStatement("SELECT id_usuari, nom_usuari, cognoms_usuari, email_usuari, contransenya_usuari, rol_usuari FROM library1.usuaris WHERE nom="+nomUsuari);
pstm.setString(1, nomUsuari);
rs = pstm.executeQuery();
while (rs.next()) {
    /*id_usuari serial NOT NULL,
    nom_usuari character varying(32) NOT NULL,
    cognoms_usuari character varying(64) NOT NULL,
    email_usuari TEXT NOT NULL,
    contransenya_usuari TEXT NOT NULL,
    rol_usuari TEXT NOT NULL,*/
                String nom = rs.getString("nom_usuari");
                int id = rs.getInt("id");
                String cognoms_usuari = rs.getString("cognoms_usuari");
                String email = rs.getString("email_usuari");
                String contrassenya = rs.getString("contransenya_usuari");
                String rol_usuari = rs.getString("rol_usuari");
                us.setNombre(nom);
                us.setApellidos(cognoms_usuari);
                us.setEmail(email);
                us.setId(id);
                us.setContrasenia(contrassenya);
                us.setRol(rol_usuari);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return us;
    

    }
}
