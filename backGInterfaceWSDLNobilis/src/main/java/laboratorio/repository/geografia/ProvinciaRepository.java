package laboratorio.repository.geografia;

import laboratorio.dao.geografia.ProvinciaDao;
import laboratorio.entities.geografia.Provincia;

public class ProvinciaRepository {
    private ProvinciaDao dao;

    public ProvinciaRepository() {
        this.dao = ProvinciaDao.getInstance();
        this.dao.setClazz(Provincia.class);
    } 
    
    public boolean isSavedProvincia(String nombre) { 
        Provincia provincia = this.getProvinciaByName(nombre);
        return provincia.getNombre().equals(nombre); 
    }

    public Provincia getOrigenById(Integer id){
        return this.dao.findOne(id);
    }

    public Provincia getProvinciaByName(String nombre){
        Provincia provinciaEncontrada =  this.dao.getProvinciaByName(nombre);                                
        return provinciaEncontrada;
    }
    
    public Provincia save(Provincia provincia) {
        return this.dao.save(provincia);
    }

    public void delete(Provincia provincia){
        this.dao.delete(provincia);
    }
}
