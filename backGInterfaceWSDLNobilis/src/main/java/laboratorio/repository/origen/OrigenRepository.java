package laboratorio.repository.origen;

import java.util.ArrayList;
import java.util.Set;

import laboratorio.dao.origen.OrigenDao;
import laboratorio.entities.auth.Usuario;
import laboratorio.entities.origen.Origen;

public class OrigenRepository {
    private OrigenDao dao;

    public OrigenRepository() {
        this.dao = OrigenDao.getInstance();
        this.dao.setClazz(Origen.class);
    } 
    
    public boolean isSavedOrigen(String razonSocial, Long cuit) { 
        Origen origen = this.getOrigenByCuit(cuit);
        return origen.getRazonSocial().equals(razonSocial); 
    }

    public Origen getOrigenById(Integer id){
        return this.dao.findOne(id);
    }

    public Origen getOrigenByCuit(Long cuit){
        Origen origenEncontrado =  this.dao.getOrigenByCuit(cuit);                                 
        return origenEncontrado;
    }

    public Origen getOrigenByRazonSocial(String razonSocial){
        Origen origenEncontrado =  this.dao.getOrigenByRazonSocial(razonSocial);                                 
        return origenEncontrado;
    }

    public Origen getOrigenByUsuario(Usuario usuario){
        Origen origenEncontrado =  this.dao.getOrigenByUsuario(usuario);                                 
        return origenEncontrado;
    }

    public Set<Origen> getAllOrigenesSimplificados(){
        return dao.getAllOrigenesSimplificados();
    }

    public void updateState(Origen origen){
        if(origen.getEstado() == 1){
            origen.setEstado(0);
            this.update(origen);}
        else{origen.setEstado(1);
             this.update(origen);}
    }

    public void update(Origen origen){
        this.dao.update(origen);
    }

    public Origen save(Origen origen) {
        return this.dao.save(origen);
    }

    public void saveAll(Origen origen) {
        ArrayList<Origen> origenes = new ArrayList<Origen>();
        origenes.add(origen);
        this.dao.saveAll(origenes);
    }

    public void delete(Origen origen){
        this.dao.delete(origen);;
    }
}
