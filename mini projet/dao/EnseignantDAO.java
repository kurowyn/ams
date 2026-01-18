package dao;

import util.DBConnection;
import java.sql.*;

public class EnseignantDAO {

    public Integer login(String login, String pwd) {
        String sql = "SELECT id_enseignant FROM Enseignant WHERE login=? AND pwd=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, login);
            ps.setString(2, pwd);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("id_enseignant");

        } catch (Exception e) {
            System.out.println(" Erreur login enseignant");
        }
        return null;
    }

    public boolean enseigneClasse(int idEnseignant, int idClasse) {
        String sql = "SELECT * FROM Correspondance WHERE id_enseignant=? AND id_classe=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idEnseignant);
            ps.setInt(2, idClasse);

            return ps.executeQuery().next();

        } catch (Exception e) {
            return false;
        }
    }
}
