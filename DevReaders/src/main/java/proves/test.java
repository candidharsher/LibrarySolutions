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
        String url = "postgres://uiomrdwe:zYBtVHDLBIrm7YGFMPvcm7daP5Fru0hL@kandula.db.elephantsql.com/uiomrdwe";
        String usuari = "uiomrdwe";
        String contrasenya = "zYBtVHDLBIrm7YGFMPvcm7daP5Fru0hL";
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(url, usuari, contrasenya);
        conn.setAutoCommit(true);
        GestorUsuari gestor = new GestorUsuari(conn);
        List<Usuari> llistaUsuaris = creaLlista();
        TestRegister(llistaUsuaris, gestor);
        System.out.println("usuaris afegits a la BD library");
        Client client = new Client(5432,"localhost");
        Servidor servidor = new Servidor(client);
        client.connexioClient(llistaUsuaris.get(1));
        servidor.connexioServidor();
    }
     private static List<Usuari> creaLlista() {
        return Arrays.asList(
                new Usuari(1, "juanma", "aguilar", "a", "a", "admin"),
                new Usuari(2, "afri", "pacheco", "a", "a",  "admin"),
                new Usuari(3, "joan", "marti", "a", "a",  "admin"),
                new Usuari(4, "jana", "cruz", "a", "a",  "admin"),
                new Usuari(5, "valentina", "daniela", "a", "a",  "admin"),
                new Usuari(6, "paula", "perez", "a", "a",  "admin"),
                new Usuari(7, "marta", "freixas", "a", "a",  "admin"),
                new Usuari(8, "anna", "lavin", "a", "a",  "admin")
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
    
    public static Usuari iniciarSessioTest(List<Usuari> usuaris, GestorUsuari gestor) throws SQLException {
        System.out.println("obtenir usuaris de la bd");
        Usuari expResult = usuaris.get(1);
        Usuari result = gestor.iniciarSessio(usuaris.get(1).getNombre());
        if(expResult==result){
            return result;
        }
        return null;
    }

    /**
     * Test of eliminarUsuari method, of class GestorUsuari.
     */
    
    public static void EliminarUsuariTest(GestorUsuari gestor, String nom) throws SQLException {
        System.out.println("eliminar usuari de la bd");        
        gestor.eliminarUsuari(nom);
  
    }
}
