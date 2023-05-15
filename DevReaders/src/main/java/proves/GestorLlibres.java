package proves;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestorLlibres {
    static final Logger LOGGER = Logger.getLogger(GestorLlibres.class.getName());

    Connection conn;

    public GestorLlibres(Connection conn) {
        this.conn = conn;
    }

    public int contarLlibresDisponibles(Usuari usuari) {
        String sql = "SELECT COUNT(*) AS total FROM public.llibres WHERE id_llibre NOT IN (SELECT id_llibre_prestem FROM public.prestems WHERE id_usuari_prestem = ?)";

        int totalLlibres = 0;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, usuari.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalLlibres = rs.getInt("total");
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        return totalLlibres;
    }
}
