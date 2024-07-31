package laboratorio.dao.geografia;

import javax.persistence.TypedQuery;

import laboratorio.dao.Dao;
import laboratorio.dao.EntityManagerHelper;
import laboratorio.entities.geografia.Provincia;

public class ProvinciaDao extends Dao<Provincia>{
    private static ProvinciaDao instance;

    public static ProvinciaDao getInstance() {
        if (instance == null)
            instance = new ProvinciaDao();
        return instance;
    }

    public Provincia getProvinciaByName(String nombre) {
        String queryString = "from " + Provincia.class.getName() + " where nombre = :nombre";
        TypedQuery<Provincia> query = EntityManagerHelper.getEntityManager().createQuery(queryString, Provincia.class);
        query.setParameter("nombre", nombre);
        if (query.getResultList().isEmpty())
            return null;
        return query.getResultList().get(0);
    }

    
}