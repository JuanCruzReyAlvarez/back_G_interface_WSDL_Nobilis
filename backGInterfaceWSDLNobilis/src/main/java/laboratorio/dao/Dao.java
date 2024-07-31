package laboratorio.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;



public abstract class Dao<T> {

    private Class< T > clazz; 

    public final void setClazz( Class< T > clazzToSet ){
        this.clazz = clazzToSet;
    }

    public T save(T item) {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        
        try {
            tx.begin();
            if (!entityManager.contains(item))
                {item = entityManager.merge(item);
                entityManager.flush();}
            entityManager.persist(item);
            entityManager.flush();
            tx.commit();
            return item;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) 
                tx.rollback();
            throw e; // Vuelve a lanzar la excepción para que el cliente pueda manejarla
        } finally {
            entityManager.close();
        }
    }

    public void update(T item) {
        
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().merge(item);
        //EntityManagerHelper.getEntityManager().flush();
        EntityManagerHelper.commit();
        EntityManagerHelper.closeEntityManager();

    }

    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
        List<T> r = new ArrayList<T>(c.size());
        for(Object o: c)
          r.add(clazz.cast(o));
        return r;
    }

    public List<T> getAll() {
    
        CriteriaBuilder builder = EntityManagerHelper.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criterios = builder.createQuery(this.clazz);
        criterios.from(clazz);
        List<T> entidades = EntityManagerHelper.getEntityManager().createQuery(criterios).getResultList();                                                 
        return entidades;
    
    }

    public void saveAll(List<T> entities) {
        for(T entity: entities){ 
            save(entity); 
        }
	}

    public void deleteAll(List<T> entities){
        for(T entity: entities){ 
            this.delete(entity);
        }
    }

    public void delete(T item) {
            EntityManager entityManager = EntityManagerHelper.getEntityManager();
            EntityTransaction tx = entityManager.getTransaction();
            try {
                tx.begin();
                if (!entityManager.contains(item)) // Se comprueba si el objeto está en estado detached y fusionarlo si es necesario
                    item = entityManager.merge(item);
                entityManager.remove(item);
                tx.commit();
            } catch (Exception e){
                if (tx != null && tx.isActive()) 
                    tx.rollback();      
            } finally {entityManager.close();}
        }
    

    public T findOne(Integer id ){
        return (T)EntityManagerHelper.getEntityManager().find( clazz, id );        
     }

    public void deleteById(Integer entityId ){
        
        EntityManagerHelper.beginTransaction();
        T entity = findOne( entityId );
        delete( entity );
        EntityManagerHelper.closeEntityManager();
     }

}
