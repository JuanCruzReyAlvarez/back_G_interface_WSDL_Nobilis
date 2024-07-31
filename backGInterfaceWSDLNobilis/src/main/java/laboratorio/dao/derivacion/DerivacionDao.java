package laboratorio.dao.derivacion;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import laboratorio.dao.Dao;
import laboratorio.dao.EntityManagerHelper;
import laboratorio.entities.derivacion.Derivacion;

public class DerivacionDao extends Dao<Derivacion>{
    
    private static DerivacionDao instance;

    public static DerivacionDao getInstance() {
        if (instance == null)
            instance = new DerivacionDao();
        return instance;
    }

    public List<Derivacion> getDerivacionesByOrigen(Integer origen_id) {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        entityManager.clear();
        entityManager.getTransaction().begin();
        String jpqlQuery = "SELECT NEW Derivacion( d.id, d.fechaEmision, d.nombre ) FROM Derivacion d WHERE d.origen.id = :origen_id";
        TypedQuery<Derivacion> query = entityManager.createQuery(jpqlQuery, Derivacion.class);
        query.setParameter("origen_id", origen_id);
        List<Derivacion> resultList = query.getResultList();
        entityManager.close();
            if (resultList.isEmpty())
                return new ArrayList<Derivacion>();
        return resultList;
    }

    public Derivacion getDerivacionByNameAndOrden(String origenId, String nombreExcel){
        String queryString = "from " + Derivacion.class.getName() + " where origen_id = :origen_id and nombre = :nombreExcel";
        TypedQuery<Derivacion> query = EntityManagerHelper.getEntityManager().createQuery(queryString, Derivacion.class);
        query.setParameter("origen_id", origenId);
        query.setParameter("nombreExcel", nombreExcel);
            if (query.getResultList().isEmpty())
                return null;
        return query.getResultList().get(0);
    }


}