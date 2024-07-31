package laboratorio.repository.resultado;

import java.util.List;

import laboratorio.dao.resultado.ResultadoDao;
import laboratorio.entities.resultado.Resultado;

public class ResultadoRepository {
    private ResultadoDao dao;

    public ResultadoRepository() {
        this.dao = ResultadoDao.getInstance();
        this.dao.setClazz(Resultado.class);
    } 

    public Resultado save(Resultado paciente) {
        return this.dao.save(paciente);
    }

    public void saveAll(List<Resultado> pacientes) {
        this.dao.saveAll(pacientes);
    }

    public void update(Resultado resultado) {
        this.dao.update(resultado);
    }

    public Resultado getResultadoById(Integer id){
        return this.dao.findOne(id);
    }

    public Resultado getResultadoByOrdenId(Integer idOrden){
        Resultado resultado =  this.dao.getResultadoByOrdenId(idOrden);                                 
        return resultado;
    }

}
