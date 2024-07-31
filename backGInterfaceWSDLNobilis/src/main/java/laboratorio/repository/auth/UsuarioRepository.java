package laboratorio.repository.auth;

import laboratorio.dao.auth.UsuarioDao;
import laboratorio.entities.auth.Usuario;

public class UsuarioRepository {
    
    private UsuarioDao dao;

    public UsuarioRepository() {
        this.dao = UsuarioDao.getInstance();
        this.dao.setClazz(Usuario.class);
    } 
    
    public boolean isValidLogin(String username, String hashPassword) { 

        Usuario user = this.getUsuarioByUsername(username);
        return user.getUsername().equals(username) && user.getPassword().equals(hashPassword); 
    }

    public Usuario getUsuarioById(Integer id){
        return this.dao.findOne(id);
    }

    public Usuario getUsuarioByUsername(String username) {
        Usuario usuarioEncontrado =  this.dao.getUsuarioByUsername(username);                                       
        return usuarioEncontrado;
    }
    
    
    public Usuario save(Usuario usuario) {
        return this.dao.save(usuario);
    }

    public void delete(Usuario usuario){
        this.dao.delete(usuario);;
    }
}
