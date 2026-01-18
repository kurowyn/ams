package service;

import dao.*;

public class AuthService {

    private EtudiantDAO etudiantDAO = new EtudiantDAO();
    private EnseignantDAO enseignantDAO = new EnseignantDAO();
    private ResponsableDAO responsableDAO = new ResponsableDAO();

    public Integer loginEtudiant(String login, String pwd) {
        return etudiantDAO.login(login, pwd);
    }

    public Integer loginEnseignant(String login, String pwd) {
        return enseignantDAO.login(login, pwd);
    }

    public Integer loginResponsable(String login, String pwd) {
        return responsableDAO.login(login, pwd);
    }
}
