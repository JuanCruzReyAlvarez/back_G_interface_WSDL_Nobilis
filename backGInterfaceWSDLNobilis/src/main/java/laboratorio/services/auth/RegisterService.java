package laboratorio.services.auth;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import laboratorio.entities.auth.Rol;
import laboratorio.entities.auth.Usuario;
import laboratorio.entities.geografia.Localidad;
import laboratorio.entities.geografia.Provincia;
import laboratorio.entities.origen.Origen;
import laboratorio.http.exceptions.BadResquestException;
import laboratorio.http.exceptions.HttpException;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.session.SessionMessage;
import laboratorio.repository.auth.UsuarioRepository;
import laboratorio.repository.geografia.LocalidadRepository;
import laboratorio.repository.geografia.ProvinciaRepository;
import laboratorio.repository.origen.OrigenRepository;

public class RegisterService {

    private UsuarioRepository usuarioRepository;
    private OrigenRepository origenRepository;
    private ProvinciaRepository provinciaRepository;
    private LocalidadRepository localidadRepository;

    public RegisterService() {
        this.usuarioRepository = new UsuarioRepository();
        this.origenRepository = new OrigenRepository();
        this.provinciaRepository = new ProvinciaRepository();
        this.localidadRepository = new LocalidadRepository();
    }

    public SessionMessage register(String username, String password, String CP,String cuit,
                                   String domicilio,  String localidad, String mail, String provincia,
                                   String razonSocial, String telefono, String estado) 
                                   throws HttpException {

        Usuario usuario = validateAndSaveUsuario(this.buildUsuario(username, password)); 
        validateAndSaveOrigen(this.buildNewOrigen(CP, cuit, domicilio, localidad, mail, 
                                              provincia, razonSocial, telefono, usuario, estado));                              
        return new SessionMessage(MessageException.USUARIO_REGISTRADO, MessageException.ORIGEN_REGISTRADO);
    }

    public SessionMessage changeState (String cuit)throws HttpException { 
        try{   
        Origen origen = this.origenRepository.getOrigenByCuit(Long.parseLong(cuit));
        Integer estadoAnterior = origen.getEstado();
        this.origenRepository.updateState((origen));
        if(estadoAnterior == this.origenRepository.getOrigenByCuit(Long.parseLong(cuit)).getEstado())
           throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.DESACTIVACION_INCORRECTA));
        return new SessionMessage("Origen Eliminado correctamente");
        }catch(Exception ex){
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.DESACTIVACION_INCORRECTA));
        } 
    }

    public Usuario buildUsuario(String username, String password) {
        Usuario usuario = new Usuario();
        usuario.setPassword(password);
        usuario.setUsername(username);
        usuario.setRol(Rol.ORIGEN);

        return usuario;
    } 

    public Usuario getUsuario(String username){
        return this.usuarioRepository.getUsuarioByUsername(username);
    }

    public SessionMessage updateOrigen(String CP,String cuit, String domicilio,  String localidad, String mail, String provincia,
                            String razonSocial, String telefono, String usuario, String iDOrigen, String estado){
        this.origenRepository.update(this.buildOldOrigen(CP, cuit, domicilio, localidad, mail, provincia, razonSocial, 
                                     telefono, usuario, iDOrigen, estado));
        return new SessionMessage(MessageException.UPDATE_STATUS_OK);
        }

    public SessionMessage updateMyOrigen(String CP,String domicilio,  String localidad, String mail, String provincia,
                            String telefono, String usuario, String iDOrigen, String estado){
        this.origenRepository.update(this.buildOldMyOrigen(CP, domicilio, localidad, mail, provincia, 
                                     telefono, usuario, iDOrigen, estado));
        return new SessionMessage(MessageException.UPDATE_STATUS_OK);
        }

    private Usuario validateAndSaveUsuario (Usuario usuario) throws HttpException{
        if (this.usuarioRepository.getUsuarioByUsername(usuario.getUsername()) != null)
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.USUARIO_REPETIDO));
        return this.usuarioRepository.save(usuario);
    }

    private Origen validateAndSaveOrigen (Origen origen) throws HttpException{
        if (this.origenRepository.getOrigenByCuit(origen.getCuit()) != null){
            this.usuarioRepository.delete(origen.getUsuario());
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ORIGEN_REPETIDO));}
        if (this.origenRepository.getOrigenByRazonSocial(origen.getRazonSocial()) != null){
            this.usuarioRepository.delete(origen.getUsuario());
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.RAZON_SOCIAL_REPETIDA));
        }
        return this.origenRepository.save(origen);
    }

    private Origen validateProvinciaAndLocalidad(Origen origen, String provincia, String localidad){
        Provincia prov = provinciaRepository.getProvinciaByName(provincia);
        if(prov == null)      
            return this.setProvinciaAndLocalidadAmbasInexistentes(prov, provincia, localidad, origen);
        Localidad loc = localidadRepository.getLocalidadByNameAndProvinciaID(localidad,prov.getId());
        if( loc == null)
            return this.setProvinciaExistenteAndLocalidadInexistente(prov, localidad, origen);
        return this.setProvinciaExistenteAndLocalidadExistente(prov,loc,origen);                
    }      
    
    private Origen setProvinciaAndLocalidadAmbasInexistentes(Provincia prov, String provincia, String localidad, Origen origen){
        prov = new Provincia(provincia);
        Localidad loc = new Localidad(localidad);  
    return this.setLocalidadInexistente(prov, loc, localidad, origen);
    }

    private Origen setProvinciaExistenteAndLocalidadInexistente(Provincia prov, String localidad, Origen origen){
        Localidad loc = new Localidad(localidad);
    return this.setLocalidadInexistente(prov, loc, localidad, origen);
    }

    private Origen setProvinciaExistenteAndLocalidadExistente(Provincia prov, Localidad loc, Origen origen){
        origen.setProvincia(prov);
        origen.setLocalidad(loc);
    return origen;
    }

    private Origen setLocalidadInexistente(Provincia prov, Localidad loc, String localidad, Origen origen){
        Set<Localidad> locs = new HashSet<Localidad>();
        loc.setProvincia(prov);
        locs.add(loc);
        prov.setLocalidades(locs);
        origen.setProvincia(prov);
        origen.setLocalidad(loc);     
    return origen;
    }
    private Origen buildNewOrigen(String CP,String cuit, String domicilio,  String localidad, String mail, String provincia,
        String razonSocial, String telefono, Usuario usuario, String estado) {
        Origen origen = new Origen();
        origen.setUsuario(usuario);
        origen.setCodigoPostal(Long.parseLong(CP));
        origen.setCuit(Long.parseLong(cuit));
        origen.setDomicilio(domicilio); 
        origen.setMail(mail);
        origen.setRazonSocial(razonSocial);
        origen.setTelefono(telefono); 
        origen.setEstado(Integer.parseInt(estado)); 

        origen = this.validateProvinciaAndLocalidad(origen, provincia, localidad);

    return origen;
    } 


    private Origen buildOldOrigen(String CP,String cuit, String domicilio,  String localidad, String mail, String provincia,
        String razonSocial, String telefono, String user, String iDOrigen, String estado) {
        Origen origen = this.origenRepository.getOrigenById(Integer.parseInt(iDOrigen));
        Usuario usuario = origen.getUsuario();
        usuario.setUsername(user);
        this.usuarioRepository.save(usuario);
        origen.setUsuario(usuario);
        origen.setCodigoPostal(Long.parseLong(CP));
        origen.setCuit(Long.parseLong(cuit));
        origen.setDomicilio(domicilio); 
        origen.setMail(mail);
        origen.setRazonSocial(razonSocial);
        origen.setTelefono(telefono); 
        origen.setId(Integer.parseInt(iDOrigen)); 
        origen.setEstado(Integer.parseInt(estado));

        origen = this.validateProvinciaAndLocalidad(origen, provincia, localidad);
    return origen;
    } 

    private Origen buildOldMyOrigen(String CP, String domicilio,  String localidad, String mail, String provincia,
        String telefono, String user, String iDOrigen, String estado){
        Origen origen = this.origenRepository.getOrigenById(Integer.parseInt(iDOrigen));
        origen.setCodigoPostal(Long.parseLong(CP));
        origen.setDomicilio(domicilio); 
        origen.setMail(mail);
        origen.setTelefono(telefono); 
        origen.setId(Integer.parseInt(iDOrigen)); 
        origen.setEstado(Integer.parseInt(estado));
        origen = this.validateProvinciaAndLocalidad(origen, provincia, localidad);
    return origen;
    }
}
