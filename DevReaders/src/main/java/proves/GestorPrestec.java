package proves;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestorPrestec {
    static final Logger LOGGER = Logger.getLogger(GestorPrestec.class.getName());

    Connection conn;

    public GestorPrestec(Connection conn) {
        this.conn = conn;
    }

    public void assignarPrestec(Usuari usuari, Llibre llibre) {
        String sql = "INSERT INTO public.prestems(id_usuari_prestem, id_llibre_prestem, data_prestem) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, usuari.getId());
            ps.setInt(2, llibre.getId());
            ps.setDate(3, new java.sql.Date(new Date().getTime()));
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
