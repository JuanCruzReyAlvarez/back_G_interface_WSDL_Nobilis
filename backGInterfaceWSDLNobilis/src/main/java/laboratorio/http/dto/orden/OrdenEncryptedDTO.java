package laboratorio.http.dto.orden;

import laboratorio.http.dto.auth.fields.UsernameDTO;
import laboratorio.http.dto.orden.fields.ApellidoDTO;
import laboratorio.http.dto.orden.fields.EstudiosDTO;
import laboratorio.http.dto.orden.fields.FlagDownloadDTO;
import laboratorio.http.dto.orden.fields.DniDTO;
import laboratorio.http.dto.orden.fields.NombreDTO;
import laboratorio.http.dto.orden.fields.ObservacionesDTO;
import laboratorio.http.dto.resultado.fields.IdOrdenDTO;
import lombok.Getter;
import lombok.Setter;

public class OrdenEncryptedDTO {
    @Getter @Setter private UsernameDTO username;
    @Getter @Setter private NombreDTO nombre;
    @Getter @Setter private ApellidoDTO apellido;
    @Getter @Setter private DniDTO dni;
    @Getter @Setter private EstudiosDTO estudios;
    @Getter @Setter private ObservacionesDTO observaciones;
    @Getter @Setter private IdOrdenDTO idOrden;
    @Getter @Setter private FlagDownloadDTO flag_Download;

    public OrdenEncryptedDTO(UsernameDTO username, NombreDTO nombre, ApellidoDTO apellido, DniDTO dni, EstudiosDTO estudios, 
                             ObservacionesDTO observaciones, IdOrdenDTO idOrden,FlagDownloadDTO flag_DownloadDTO){
        this.username = username ;
        this.nombre = nombre;
        this.apellido = apellido; 
        this.dni = dni;
        this.estudios = estudios;
        this.observaciones = observaciones;
        this.idOrden = idOrden;
        this.flag_Download = flag_DownloadDTO;
    }

    public OrdenEncryptedDTO(){
        this.username = new UsernameDTO();
        this.nombre = new NombreDTO();
        this.apellido = new ApellidoDTO(); 
        this.dni = new DniDTO();
        this.estudios = new EstudiosDTO();
        this.observaciones = new ObservacionesDTO();
        this.idOrden = new IdOrdenDTO(); 
        this.flag_Download = new FlagDownloadDTO();
    }
}
