package laboratorio.repository.paciente;

import java.util.ArrayList;
import java.util.List;

import laboratorio.dao.paciente.PacienteDao;
import laboratorio.entities.paciente.Paciente;

public class PacienteRepository {
    private PacienteDao dao;

    public PacienteRepository() {
        this.dao = PacienteDao.getInstance();
        this.dao.setClazz(Paciente.class);
    } 
    
    public boolean isSavedPaciente(String nombre, String apellido, Long dni) { 
        Paciente paciente = this.getPacienteByNombreApellidoDni(nombre, apellido, dni);
        return ( paciente.getNombre().equals(nombre) && paciente.getApellido().equals(apellido) 
               && paciente.getDni().equals(dni)); 
    }

    public Paciente getPacienteById(Integer id){
        return this.dao.findOne(id);
    }

    public Paciente getPacienteByNombreApellidoDni(String nombre, String apellido, Long dni){
        Paciente pacienteEncontrado =  this.dao.getPacienteByNombreApellidoDNI(nombre, apellido, dni);                                 
        return pacienteEncontrado;
    }
    
    public Paciente save(Paciente paciente) {
        return this.dao.save(paciente);
    }

    public void update(Paciente paciente) {
        this.dao.update(paciente);
    }

    public void updatePacienteNobilisPersisted(Paciente paciente) {
        paciente.setNobilisPersisted(1);
        this.update(paciente);
    }
    public void updatePacientesNobilisPersisted(ArrayList <Paciente> pacientes) {
        for(Paciente p : pacientes){
            this.updatePacienteNobilisPersisted(p);
        }
    }
    public void delete(Paciente paciente){
        this.dao.delete(paciente);
    }

    public void deleteAll(List<Paciente> pacientes){
        List<Paciente> pacientesConId = new ArrayList<Paciente>();
        for(Paciente paciente: pacientes)
        {pacientesConId.add(this.dao.getPacienteByNombreApellidoDNI(paciente.getNombre(), paciente.getApellido(), paciente.getDni()));}
        this.dao.deleteAll(pacientes);
    }

}
