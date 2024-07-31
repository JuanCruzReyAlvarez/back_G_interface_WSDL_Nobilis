package laboratorio.repository.estudio;

import java.util.List;

import laboratorio.dao.estudio.EstudioDao;
import laboratorio.entities.estudios.Estudio;


public class EstudioRepository {
    private EstudioDao dao;

    public EstudioRepository() {
        this.dao = EstudioDao.getInstance();
        this.dao.setClazz(Estudio.class);
    } 

    public Estudio getEstudioByCodigo(String codigo){
        return this.dao.findEstudioByCodigo(codigo);
    }

    public Estudio getOrdenById(Integer id){
        return this.dao.findOne(id);
    }
    
    public Estudio save(Estudio estudio) {
        return this.dao.save(estudio);
    }

    public void saveAll(List<Estudio> estudios) {
        this.dao.saveAll(estudios);
    }

    public void delete(Estudio estudio){
        this.dao.delete(estudio);
    }

}








