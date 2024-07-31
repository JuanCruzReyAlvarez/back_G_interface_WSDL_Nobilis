package laboratorio.dao.origen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;



import laboratorio.dao.Dao;
import laboratorio.dao.EntityManagerHelper;
import laboratorio.entities.auth.Usuario;
import laboratorio.entities.origen.Origen;


public class OrigenDao extends Dao<Origen>{
    private static OrigenDao instance;

    public static OrigenDao getInstance() {
        if (instance == null)
            instance = new OrigenDao();
        return instance;
    }

    public Origen getOrigenByCuit(Long cuit) {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        entityManager.getTransaction().begin();
        //entityManager.flush();
        String queryString = "from " + Origen.class.getName() + " where cuit = :cuit";
        TypedQuery<Origen> query = EntityManagerHelper.getEntityManager().createQuery(queryString, Origen.class);
        query.setParameter("cuit", cuit);
        List<Origen> resultList = query.getResultList();
        entityManager.getTransaction().commit();
        EntityManagerHelper.closeEntityManager();
        if (resultList.isEmpty() )
            return null;
        return resultList.get(0);
    }

    public Origen getOrigenByRazonSocial (String razonSocial){
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        entityManager.getTransaction().begin();
        String queryString = "from " + Origen.class.getName() + " where razonSocial = :razonSocial";
        TypedQuery<Origen> query = EntityManagerHelper.getEntityManager().createQuery(queryString, Origen.class);
        query.setParameter("razonSocial", razonSocial);
        List<Origen> resultList = query.getResultList();
        entityManager.getTransaction().commit();
        EntityManagerHelper.closeEntityManager();
        if (resultList.isEmpty())
            return null;
        return query.getResultList().get(0);
    }

    public Origen getOrigenByUsuario(Usuario usuario){
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        entityManager.getTransaction().begin();
        String queryString = "from " + Origen.class.getName() + " where usuario_id = :usuario_id";
        TypedQuery<Origen> query = EntityManagerHelper.getEntityManager().createQuery(queryString, Origen.class);
        query.setParameter("usuario_id", usuario.getId());
        List<Origen> resultList = query.getResultList();
        entityManager.getTransaction().commit();
        EntityManagerHelper.closeEntityManager();
        if (resultList.isEmpty() )
            return null;
        return resultList.get(0);
    }

    public Set<Origen> getAllOrigenesSimplificados() {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        entityManager.getTransaction().begin();
        //entityManager.flush();
        String jpqlQuery = "SELECT NEW Origen( o.cuit, o.razonSocial, o.usuario, o.mail ,o.estado ) FROM Origen o";
        TypedQuery<Origen> query = entityManager.createQuery(jpqlQuery, Origen.class);
        List<Origen> resultList = query.getResultList();
        entityManager.getTransaction().commit();
        Set<Origen> set = new HashSet<>(resultList);
        EntityManagerHelper.closeEntityManager();
        if (set.isEmpty())
            return null;
        return set;
    }
    
}
