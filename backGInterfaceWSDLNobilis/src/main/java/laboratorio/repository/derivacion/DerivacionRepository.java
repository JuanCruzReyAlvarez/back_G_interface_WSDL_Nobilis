package laboratorio.repository.derivacion;

import java.util.List;

import laboratorio.dao.derivacion.DerivacionDao;
import laboratorio.entities.derivacion.Derivacion;
import laboratorio.entities.origen.Origen;

public class DerivacionRepository {

    private DerivacionDao dao;

    public DerivacionRepository() {
        this.dao = DerivacionDao.getInstance();
        this.dao.setClazz(Derivacion.class);
    } 
    
    public boolean isSavedDerivacion(String nombreExcel, String ordenId) { 
        Derivacion derivacion = this.dao.getDerivacionByNameAndOrden(ordenId, nombreExcel);
        return derivacion.getNombre().equals(nombreExcel); 
    }

    public List<Derivacion> getDerivacionesByOrigen(Origen origen) { 
        List<Derivacion> derivaciones = this.dao.getDerivacionesByOrigen(origen.getId());
        return derivaciones; 
    }
    public Derivacion getDerivacionByOrigenAndName(Origen origen, String nameDerivacion){
        return this.dao.getDerivacionByNameAndOrden(origen.getId().toString(), nameDerivacion);
    }

    public Derivacion getDerivacionByID(Integer id){
        return this.dao.findOne(id);
    }
    
    public Derivacion save(Derivacion derivacion) {
        return this.dao.save(derivacion);
    }

    public void delete(Derivacion derivacion){
        this.dao.delete(derivacion);
    }
}
