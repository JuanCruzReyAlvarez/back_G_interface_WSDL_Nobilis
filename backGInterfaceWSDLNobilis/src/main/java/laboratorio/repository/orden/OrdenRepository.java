package laboratorio.repository.orden;

import java.util.ArrayList;
import java.util.List;

import laboratorio.dao.orden.OrdenDao;
import laboratorio.entities.derivacion.Derivacion;
import laboratorio.entities.orden.Orden;

public class OrdenRepository {
    private OrdenDao dao;

    public OrdenRepository() {
        this.dao = OrdenDao.getInstance();
        this.dao.setClazz(Orden.class);
    } 

    public ArrayList<Orden> getOrdenesByDerivacion(Derivacion derivacion){
        return this.dao.getOrdenesByDerivacion(derivacion);
    }

    public void uploadFlagDownloadFrom(List<Integer>idOrdenes){
        for(Integer idOrden : idOrdenes){
            this.dao.uploadFlagDownloadFrom(idOrden);
        }
    }

    public Orden getOrdenById(Integer id){
        return this.dao.findOne(id);
    }
    
    public Orden save(Orden orden) {
        return this.dao.save(orden);
    }

    public void saveAll(List<Orden> ordenes) {
        this.dao.saveAll(ordenes);
    }

    public void delete(Orden orden){
        this.dao.delete(orden);
    }
}
