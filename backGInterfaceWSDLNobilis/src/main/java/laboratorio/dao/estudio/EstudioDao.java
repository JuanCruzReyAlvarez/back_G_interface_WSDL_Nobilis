package laboratorio.dao.estudio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import laboratorio.dao.Dao;
import laboratorio.dao.EntityManagerHelper;
import laboratorio.entities.estudios.Estudio;

public class EstudioDao extends Dao<Estudio>{
    private static EstudioDao instance;
    
    public static EstudioDao getInstance() {
        if (instance == null)
            instance = new EstudioDao();
        return instance;
    }

    public Estudio findEstudioByCodigo(String codigo){
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        entityManager.getTransaction().begin();
        String queryString = "from " + Estudio.class.getName() + " where codigo = :codigo ";
        TypedQuery<Estudio> query = EntityManagerHelper.getEntityManager().createQuery(queryString, Estudio.class);
        query.setParameter("codigo", codigo);
        List<Estudio> resultList = query.getResultList();
        entityManager.getTransaction().commit();

        EntityManagerHelper.closeEntityManager();
            if (resultList.isEmpty())
                return null;
        return resultList.get(0);
    }

}
