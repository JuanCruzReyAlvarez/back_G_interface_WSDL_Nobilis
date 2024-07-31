package laboratorio.repository.geografia;

import laboratorio.dao.geografia.LocalidadDao;
import laboratorio.entities.geografia.Localidad;

public class LocalidadRepository {
    private LocalidadDao dao;

    public LocalidadRepository() {
        this.dao = LocalidadDao.getInstance();
        this.dao.setClazz(Localidad.class);
    } 
    
    public boolean isSavedLocalidad(String nombre, String provincia_id) { 
        Localidad localidad = this.dao.getLocalidadByNameAndProvinciaID(nombre, provincia_id);
        return localidad.getNombre().equals(nombre); 
    }

    public Localidad getLocalidadByNameAndProvinciaID(String nombre, Integer provincia_id){
        try{
            Localidad localidad = this.dao.getLocalidadByNameAndProvinciaID(nombre, String.valueOf(provincia_id));
            return localidad;
        }catch (Exception ex) {
            return null;
        } 
    }

    public Localidad getLocalidadByID(Integer id){
        return this.dao.findOne(id);
    }
    
    public Localidad save(Localidad localidad) {
        return this.dao.save(localidad);
    }

    public void delete(Localidad localidad){
        this.dao.delete(localidad);
    }
}
