package laboratorio.dao.geografia;

import javax.persistence.TypedQuery;

import laboratorio.dao.Dao;
import laboratorio.dao.EntityManagerHelper;
import laboratorio.entities.geografia.Localidad;

public class LocalidadDao extends Dao<Localidad>{
    private static LocalidadDao instance;

    public static LocalidadDao getInstance() {
        if (instance == null)
            instance = new LocalidadDao();
        return instance;
    }

    public Localidad getLocalidadByNameAndProvinciaID(String nombre, String provincia_id) {
        String queryString = "from " + Localidad.class.getName() + " where nombre = :nombre and provincia_id = :provincia_id";
        TypedQuery<Localidad> query = EntityManagerHelper.getEntityManager().createQuery(queryString, Localidad.class);
        query.setParameter("nombre", nombre);
        query.setParameter("provincia_id", provincia_id);
            if (query.getResultList().isEmpty())
                return null;
        return query.getResultList().get(0);
    }
    
}
