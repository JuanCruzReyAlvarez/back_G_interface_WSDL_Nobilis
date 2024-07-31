package laboratorio.dao.resultado;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import laboratorio.dao.Dao;
import laboratorio.dao.EntityManagerHelper;
import laboratorio.entities.orden.Orden;
import laboratorio.entities.resultado.Resultado;

public class ResultadoDao extends Dao<Resultado>{
        private static ResultadoDao instance;
    
        public static ResultadoDao getInstance() {
            if (instance == null)
                instance = new  ResultadoDao();
            return instance;
        }

public Resultado getResultadoByOrdenId(Integer ordenId) {

        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        entityManager.getTransaction().begin();
        String queryStringOrden = "from " +Orden.class.getName() + " where id = :orden_id";
        TypedQuery<Orden> queryOrden = EntityManagerHelper.getEntityManager().createQuery(queryStringOrden, Orden.class);
        queryOrden.setParameter("orden_id", ordenId);
        List<Orden> resultListOrden = queryOrden.getResultList();

        String queryStringResultado = "from " +Resultado.class.getName() + " where id = :resultado_id";
        TypedQuery<Resultado> queryResultado = EntityManagerHelper.getEntityManager().createQuery(queryStringResultado, Resultado.class);
        queryResultado.setParameter("resultado_id", resultListOrden.get(0).getResultado().getId());
        List<Resultado> resultListResultado = queryResultado.getResultList();

        entityManager.getTransaction().commit();
        EntityManagerHelper.closeEntityManager();

        if (resultListResultado.isEmpty())
            return null;
        return resultListResultado.get(0);    
    }
}

