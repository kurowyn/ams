package dao;

import util.DBConnection;
import java.sql.*;

public class EtudiantDAO {

    public Integer login(String login, String pwd) {
        String sql = "SELECT id_etudiant FROM Etudiant WHERE login=? AND pwd=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, login);
            ps.setString(2, pwd);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("id_etudiant");

        } catch (Exception e) {
            System.out.println(" Erreur login étudiant");
        }
        return null;
    }
}

