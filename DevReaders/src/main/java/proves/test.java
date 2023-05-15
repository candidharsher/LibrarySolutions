/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proves;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
/**
 *
 * @author User
 */
public class test {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        String url = "jdbc:postgresql://kandula.db.elephantsql.com:5432/uiomrdwe";
        String usuari = "uiomrdwe";
        String contrasenya = "zYBtVHDLBIrm7YGFMPvcm7daP5Fru0hL";
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(url, usuari, contrasenya);
        conn.setAutoCommit(true);
        GestorUsuari gestor = new GestorUsuari(conn);
        List<Usuari> llistaUsuaris = creaLlista();
        //TestRegister(llistaUsuaris, gestor);
        //System.out.println("usuaris afegits a la BD library");
        //System.out.println(iniciarSessioTest(llistaUsuaris, gestor).toString());
        eliminarUsuariTest(gestor, "devreader");
        Client client = new Client(5432,"kandula.db.elephantsql.com");
        Servidor servidor = new Servidor(client);
        client.connexioClient(llistaUsuaris.get(1));
        servidor.connexioServidor();
    }
     private static List<Usuari> creaLlista() {
        return Arrays.asList(
                new Usuari(13, "juanma", "aguilar", "a", "qa@a.com", "admin"),
                new Usuari(14, "afri", "pacheco", "a", "wa@a.com",  "admin"),
                new Usuari(15, "joan", "marti", "a", "eaa@a.com",  "admin"),
                new Usuari(16, "jana", "cruz", "a", "raa@a.com",  "admin"),
                new Usuari(17, "valentina", "daniela", "a", "taa@a.com",  "admin"),
                new Usuari(18, "paula", "perez", "a", "yaa@a.com",  "admin"),
                new Usuari(19, "marta", "freixas", "a", "uaa@a.com",  "admin"),
                new Usuari(20, "anna", "lavin", "a", "iaa@a.com",  "admin")
        );
    }
      /**
     * Test of registrarUsuari method, of class GestorOrdinador.
     */
    
    public static void TestRegister(List<Usuari> llistaUsuaris, GestorUsuari gestor) throws SQLException {
        System.out.println("afegir usuaris de bd");
        for (Usuari usuari : llistaUsuaris) {
            gestor.registrarUsuari(usuari);
        }
        
    }

    /**
     * Test of iniciarSessio method, of class GestorUsuari.
     */
    
    public static Usuari iniciarSessioTest(List<Usuari> llista, GestorUsuari gestor) throws SQLException {
        System.out.println("obtenir usuaris de la bd");
        Usuari result = gestor.iniciarSessio(llista.get(0).getNombre());
     
            return result;
        
    }

    /**
     * Test of eliminarUsuari method, of class GestorUsuari.
     */
    
    public static void eliminarUsuariTest(GestorUsuari gestor, String nom) throws SQLException {
        System.out.println("eliminar usuari de la bd");        
        gestor.eliminarUsuari(nom);
  
    }
}
