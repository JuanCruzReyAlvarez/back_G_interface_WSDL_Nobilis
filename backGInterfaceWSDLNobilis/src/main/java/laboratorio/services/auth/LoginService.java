package laboratorio.services.auth;

import laboratorio.entities.auth.Usuario;
import laboratorio.repository.auth.UsuarioRepository;

public class LoginService {

    private UsuarioRepository repository;

    public LoginService() {
        this.repository = new UsuarioRepository();
    }

    public Usuario login(String username, String password) throws Exception {

        if (!this.repository.isValidLogin(username, password))
            throw new Exception("Unauthorized");    

        Usuario usuario = new Usuario();
        usuario = this.repository.getUsuarioByUsername(username);  

        return usuario;
    }
}