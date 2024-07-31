package laboratorio.services.derivacion;

import java.util.ArrayList;
import java.util.Collections;

import laboratorio.entities.derivacion.Derivacion;
import laboratorio.entities.orden.Orden;
import laboratorio.entities.origen.Origen;
import laboratorio.http.dto.admin.fields.IDDerivacionDTO;
import laboratorio.http.dto.derivacion.DerivacionEncryptedDTO;
import laboratorio.http.dto.derivacion.fields.FechaEmisionDerivacionDTO;
import laboratorio.http.dto.derivacion.fields.NombreDerivacionDTO;
import laboratorio.http.exceptions.BadResquestException;
import laboratorio.http.exceptions.HttpException;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.session.AES.EncrtptAES;
import laboratorio.repository.auth.UsuarioRepository;
import laboratorio.repository.derivacion.DerivacionRepository;
import laboratorio.repository.origen.OrigenRepository;

public class DerivacionService {

    private DerivacionRepository derivacionRepository;
    private OrigenRepository origenRepository;
    private UsuarioRepository usuarioRepository;

    public DerivacionService(){
        this.derivacionRepository = new DerivacionRepository();
        this.origenRepository = new OrigenRepository();
        this.usuarioRepository = new UsuarioRepository();
    }

    public ArrayList<Derivacion> getDerivaciones(String username) throws HttpException {
        try {
            Origen origen = this.origenRepository.getOrigenByUsuario(this.usuarioRepository.getUsuarioByUsername(username));
        return new ArrayList<Derivacion>(this.derivacionRepository.getDerivacionesByOrigen(origen));
    }catch (Exception ex) {
        throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_OBTENIENDO_DERIVACIONES));
        } 
    }

    public Derivacion getDerivacionByID(Integer id) throws HttpException {
        try {
            return this.derivacionRepository.getDerivacionByID(id);
    }catch (Exception ex) {
        throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_OBTENIENDO_DERIVACIONES));
        } 
    }

    public Derivacion getDerivacionByOrigenAndName(String username, String nameDerivacion) throws HttpException {
        try {
        return this.derivacionRepository.getDerivacionByOrigenAndName(this.origenRepository.getOrigenByUsuario
                                                                     (this.usuarioRepository.getUsuarioByUsername
                                                                     (username)), nameDerivacion);
    }catch (Exception ex) {
        throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_OBTENIENDO_DERIVACION));
        } 
    }

    public Derivacion createAndSaveDerivacion(String nombreExcel, Origen origen){
        return this.derivacionRepository.save(new Derivacion(nombreExcel, origen));
    }

    public void createAndSaveDerivacion(Orden orden, String nombreExcel){
        ArrayList<Orden> ordenIndividual = new ArrayList<Orden>();
        ordenIndividual.add(orden);
        this.derivacionRepository.save(new Derivacion(nombreExcel, new ArrayList<>(ordenIndividual), orden.getPaciente().getOrigen()));
    }  

    public void deleteDerivacion(Derivacion derivacion) throws HttpException {
        try {
            this.derivacionRepository.delete(derivacion);
    }catch (Exception ex) {
        throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_ELIMINANDO_DERIVACION));
        } 
    }

    public DerivacionEncryptedDTO encryptDerivacion(Derivacion derivacion) throws HttpException {
        try {
            EncrtptAES<NombreDerivacionDTO> AESNombreDerivacion = new EncrtptAES<NombreDerivacionDTO>();
            DerivacionEncryptedDTO derivacionEncryptedDTO   = new DerivacionEncryptedDTO();  

            derivacionEncryptedDTO.setNombre(AESNombreDerivacion.encrypt(derivacion.getNombre(),derivacionEncryptedDTO.getNombre()));    
            EncrtptAES<FechaEmisionDerivacionDTO> AESFechaEmisionDerivacion = new EncrtptAES<FechaEmisionDerivacionDTO>(); 
            
            derivacionEncryptedDTO.setFechaEmision(AESFechaEmisionDerivacion.encrypt(derivacion.getFechaEmision().toString(), derivacionEncryptedDTO.getFechaEmision()));    
            EncrtptAES<IDDerivacionDTO> AESIDDerivacion = new EncrtptAES<IDDerivacionDTO>();
            derivacionEncryptedDTO.setId(AESIDDerivacion.encrypt(derivacion.getId().toString(), derivacionEncryptedDTO.getId()));  
            return derivacionEncryptedDTO;
            }catch (Exception ex) {
                throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_ENCRIPTACION_EXTERNA));
            } 
    }

    public ArrayList<DerivacionEncryptedDTO> encryptDerivaciones(ArrayList<Derivacion> derivaciones) throws HttpException {
        try {
            ArrayList<DerivacionEncryptedDTO> derivacionesEncriptadas = new ArrayList<>();
                for (Derivacion derivacion : derivaciones)
                    {   
                        DerivacionEncryptedDTO derivacionEncriptada = new DerivacionEncryptedDTO();
                        derivacionEncriptada = this.encryptDerivacion(derivacion);
                        derivacionesEncriptadas.add(derivacionEncriptada);
                    }
        return derivacionesEncriptadas;  
            }catch (Exception ex) {
                throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_ENCRIPTACION_EXTERNA));
            } 
    }
}



