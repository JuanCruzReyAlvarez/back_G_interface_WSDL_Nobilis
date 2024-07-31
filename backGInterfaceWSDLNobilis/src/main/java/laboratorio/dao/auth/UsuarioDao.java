package laboratorio.dao.auth;

import javax.persistence.TypedQuery;

import laboratorio.dao.Dao;
import laboratorio.dao.EntityManagerHelper;
import laboratorio.entities.auth.Usuario;

public class UsuarioDao extends Dao<Usuario>{
    private static UsuarioDao instance;

    public static UsuarioDao getInstance() {
        if (instance == null)
            instance = new UsuarioDao();
        return instance;
    }

    public Usuario getUsuarioByUsername(String username) {
        String queryString = "from " + Usuario.class.getName() + " where username = :username";
        TypedQuery<Usuario> query = EntityManagerHelper.getEntityManager().createQuery(queryString, Usuario.class);
        query.setParameter("username", username);
        if (query.getResultList().isEmpty())
            return null;
        return query.getResultList().get(0);
    }

    public void deleteUsuarioByUsername(String username){
        Usuario usuario = this.getUsuarioByUsername(username);
        this.delete(usuario);                  
    }
    
}
