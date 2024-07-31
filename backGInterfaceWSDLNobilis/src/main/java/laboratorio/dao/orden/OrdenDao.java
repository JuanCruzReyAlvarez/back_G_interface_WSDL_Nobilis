package laboratorio.dao.orden;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import laboratorio.dao.Dao;
import laboratorio.dao.EntityManagerHelper;
import laboratorio.entities.derivacion.Derivacion;
import laboratorio.entities.orden.Orden;

public class OrdenDao extends Dao<Orden>{
    private static OrdenDao instance;

    public static OrdenDao getInstance() {
        if (instance == null)
            instance = new OrdenDao();
        return instance;
    }

    public ArrayList<Orden> getOrdenesByDerivacion(Derivacion derivacion){
        String queryString = "from " + Orden.class.getName() + " where derivacion = :derivacion ";
        TypedQuery<Orden> query = EntityManagerHelper.getEntityManager().createQuery(queryString, Orden.class);
        query.setParameter("derivacion", derivacion);
            if (query.getResultList().isEmpty())
                return null;
        return new ArrayList<Orden>(query.getResultList());
    }
    
    public void uploadFlagDownloadFrom(Integer idOrden){
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        entityManager.getTransaction().begin();
        
        String queryString = "UPDATE Orden SET flag_Download = 1 WHERE id = :orderId";
        entityManager.createQuery(queryString)
                     .setParameter("orderId", idOrden)
                     .executeUpdate();

        entityManager.getTransaction().commit();
        EntityManagerHelper.closeEntityManager();

    }
}
