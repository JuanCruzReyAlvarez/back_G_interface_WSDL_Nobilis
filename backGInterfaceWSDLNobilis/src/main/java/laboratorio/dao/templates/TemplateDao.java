package laboratorio.dao.templates;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import laboratorio.dao.Dao;
import laboratorio.dao.EntityManagerHelper;
import laboratorio.entities.template.Formato;
import laboratorio.entities.template.Template;

public class TemplateDao extends Dao<Template>{
   
    private static TemplateDao instance;
    
    public static TemplateDao getInstance() {
        if (instance == null)
            instance = new TemplateDao();
        return instance;
    }

    public Template getFormat(Formato formato) {
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        entityManager.getTransaction().begin();
        String queryStringOrden = "from " +Template.class.getName() + " where formato = :formato";
        TypedQuery<Template> queryOrden = EntityManagerHelper.getEntityManager().createQuery(queryStringOrden, Template.class);
        queryOrden.setParameter("formato", formato);
        List<Template> resultListTemplate = queryOrden.getResultList();
        entityManager.getTransaction().commit();
        EntityManagerHelper.closeEntityManager();

        if ( resultListTemplate.isEmpty())
            return null;

        return resultListTemplate.get(0);
    }

}
