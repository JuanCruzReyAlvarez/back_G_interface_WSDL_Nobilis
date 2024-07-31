package laboratorio.dao.paciente;

import javax.persistence.TypedQuery;

import laboratorio.dao.Dao;
import laboratorio.dao.EntityManagerHelper;
import laboratorio.entities.paciente.Paciente;

public class PacienteDao extends Dao<Paciente>{
    private static PacienteDao instance;

    public static PacienteDao getInstance() {
        if (instance == null)
            instance = new PacienteDao();
        return instance;
    }

    public Paciente getPacienteByNombreApellidoDNI(String nombre, String apellido, Long dni) {
        String queryString = "from " + Paciente.class.getName() + " where nombre = :nombre and apellido = :apellido and dni = :dni";
        TypedQuery<Paciente> query = EntityManagerHelper.getEntityManager().createQuery(queryString, Paciente.class);
        query.setParameter("nombre", nombre);
        query.setParameter("apellido", apellido);
        query.setParameter("dni", dni);
        if (query.getResultList().isEmpty())
            return null;
        return query.getResultList().get(0);
    }
    
}
