package laboratorio.services.origen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import laboratorio.entities.auth.Usuario;
import laboratorio.entities.origen.Origen;
import laboratorio.http.dto.admin.fields.CPDTO;
import laboratorio.http.dto.admin.fields.CuitDTO;
import laboratorio.http.dto.admin.fields.DomicilioDTO;
import laboratorio.http.dto.admin.fields.EstadoDTO;
import laboratorio.http.dto.admin.fields.IDOrigenDTO;
import laboratorio.http.dto.admin.fields.LocalidadDTO;
import laboratorio.http.dto.admin.fields.MailDTO;
import laboratorio.http.dto.admin.fields.ProvinciaDTO;
import laboratorio.http.dto.admin.fields.RazonSocialDTO;
import laboratorio.http.dto.admin.fields.TelefonoDTO;
import laboratorio.http.dto.auth.fields.UsernameDTO;
import laboratorio.http.dto.origen.OrigenEncryptedDTO;
import laboratorio.http.dto.origen.OrigenSimplificadoEncryptedDTO;
import laboratorio.http.exceptions.BadResquestException;
import laboratorio.http.exceptions.HttpException;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.session.AES.EncrtptAES;
import laboratorio.repository.origen.OrigenRepository;
import laboratorio.repository.auth.UsuarioRepository;

public class OrigenService{
        
    private OrigenRepository origenRepository;
    private UsuarioRepository usuarioRepository;

    public OrigenService() {
        this.origenRepository = new OrigenRepository();
        this.usuarioRepository = new UsuarioRepository();
    }

    public Set<Origen> getOrigenesSimplificados() throws HttpException {
        Set<Origen> origenesSimplificados = new HashSet<Origen>(); 
        origenesSimplificados = this.origenRepository.getAllOrigenesSimplificados();  
        if (origenesSimplificados == null)
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.WITHOUT_ORIGENES));
        return origenesSimplificados;
    }

    public Origen getOrigenByCuit(String cuit) throws HttpException {
        try{
        Origen origen = this.origenRepository.getOrigenByCuit(Long.parseLong(cuit));  
        if (origen == null)
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.WITHOUT_ORIGEN));
        return origen;
        }catch(Exception ex){
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.SERVICE_ORIGEN_FAIL));
        }
    }
    public Origen getOrigenByUsuario(Usuario usuario){
        return this.origenRepository.getOrigenByUsuario(usuario);
    }
    public Origen getOrigenByUsernameUsuario(String username){
        return this.origenRepository.getOrigenByUsuario(this.usuarioRepository.getUsuarioByUsername(username));
    }

    public OrigenEncryptedDTO encryptOrigen(Origen origen) throws HttpException {
        try {

            EncrtptAES<CuitDTO> AESCuit = new EncrtptAES<CuitDTO>();
            OrigenEncryptedDTO origenEncriptadoDTO    = new OrigenEncryptedDTO();   
            origenEncriptadoDTO.setCuit(AESCuit.encrypt(Long.toString(origen.getCuit()),origenEncriptadoDTO.getCuit()));    

            EncrtptAES<MailDTO> AESMail = new EncrtptAES<MailDTO>();    
            origenEncriptadoDTO.setMail(AESMail.encrypt(origen.getMail(),origenEncriptadoDTO .getMail()));  

            EncrtptAES<RazonSocialDTO> AESRazonSocial = new EncrtptAES<RazonSocialDTO>();   
            origenEncriptadoDTO.setRazonSocial(AESRazonSocial.encrypt(origen.getRazonSocial(),origenEncriptadoDTO .getRazonSocial()));  

            EncrtptAES<UsernameDTO> AESUsername = new EncrtptAES<UsernameDTO>();    
            origenEncriptadoDTO.setUsername(AESUsername.encrypt(origen.getUsuario().getUsername(),origenEncriptadoDTO.getUsername()));

            EncrtptAES<DomicilioDTO> AESDomicilio = new EncrtptAES<DomicilioDTO>();
            origenEncriptadoDTO.setDomicilio(AESDomicilio.encrypt(origen.getDomicilio(),origenEncriptadoDTO.getDomicilio()));

            EncrtptAES<LocalidadDTO> AESLocalidad = new EncrtptAES<LocalidadDTO>();
            origenEncriptadoDTO.setLocalidad(AESLocalidad.encrypt(origen.getLocalidad().getNombre(),origenEncriptadoDTO.getLocalidad()));

            EncrtptAES<ProvinciaDTO> AESProvincia = new EncrtptAES<ProvinciaDTO>();
            origenEncriptadoDTO.setProvincia(AESProvincia.encrypt(origen.getProvincia().getNombre(),origenEncriptadoDTO.getProvincia()));

            EncrtptAES<CPDTO> AESCP = new EncrtptAES<CPDTO>();
            origenEncriptadoDTO.setCP(AESCP.encrypt(Long.toString(origen.getCodigoPostal()),origenEncriptadoDTO.getCP()));

            EncrtptAES<TelefonoDTO> AESTelefono = new EncrtptAES<TelefonoDTO>();
            origenEncriptadoDTO.setTelefono(AESTelefono.encrypt(origen.getTelefono(),origenEncriptadoDTO.getTelefono ()));

            EncrtptAES<IDOrigenDTO> AESIDOrigen = new EncrtptAES<IDOrigenDTO>();
            origenEncriptadoDTO.setIDOrigen(AESIDOrigen.encrypt(Integer.toString(origen.getId()),origenEncriptadoDTO.getIDOrigen()));

            EncrtptAES<EstadoDTO> AESEstado= new EncrtptAES<EstadoDTO>();
            origenEncriptadoDTO.setEstado(AESEstado.encrypt(Integer.toString(origen.getEstado()),origenEncriptadoDTO.getEstado()));

            return origenEncriptadoDTO;
            }catch (Exception ex) {
                throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_ENCRIPTACION_EXTERNA));
            } 
    }

    public ArrayList<OrigenSimplificadoEncryptedDTO> encryptOrigenesSimplificados ( Set<Origen> origenesSimplificados ) throws HttpException {
            try{
                ArrayList<OrigenSimplificadoEncryptedDTO> origenesSimplificadosEncriptados = new ArrayList<>();
                for (Origen origenSimplificado : origenesSimplificados)
                    {   
                        EncrtptAES<CuitDTO> AESCuit = new EncrtptAES<CuitDTO>();
                        OrigenSimplificadoEncryptedDTO origenSimplificadoEncriptadoDTO    = new OrigenSimplificadoEncryptedDTO();   
                        origenSimplificadoEncriptadoDTO.setCuit(AESCuit.encrypt(Long.toString(origenSimplificado.getCuit()),origenSimplificadoEncriptadoDTO.getCuit()));
                        
                        EncrtptAES<MailDTO> AESMail = new EncrtptAES<MailDTO>();
                        origenSimplificadoEncriptadoDTO.setMail(AESMail.encrypt(origenSimplificado.getMail(),origenSimplificadoEncriptadoDTO.getMail()));
                        
                        EncrtptAES<RazonSocialDTO> AESRazonSocial = new EncrtptAES<RazonSocialDTO>();
                        origenSimplificadoEncriptadoDTO.setRazonSocial(AESRazonSocial.encrypt(origenSimplificado.getRazonSocial(),origenSimplificadoEncriptadoDTO.getRazonSocial()));
                        
                        EncrtptAES<UsernameDTO> AESUsername = new EncrtptAES<UsernameDTO>();
                        origenSimplificadoEncriptadoDTO.setUsernameDeUsuario(AESUsername.encrypt(origenSimplificado.getUsuario().getUsername(),origenSimplificadoEncriptadoDTO.getUsernameDeUsuario()));
                        
                        EncrtptAES<EstadoDTO> AESEstado = new EncrtptAES<EstadoDTO>();
                        origenSimplificadoEncriptadoDTO.setEstado(AESEstado.encrypt(Integer.toString(origenSimplificado.getEstado()),origenSimplificadoEncriptadoDTO.getEstado()));

                       
                        origenesSimplificadosEncriptados.add(origenSimplificadoEncriptadoDTO);
                    }
        return origenesSimplificadosEncriptados;  
        
        }catch (Exception ex) {
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_ENCRIPTACION_EXTERNA));
        } 
        
    }

}
