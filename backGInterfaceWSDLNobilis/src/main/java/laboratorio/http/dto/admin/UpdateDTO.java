package laboratorio.http.dto.admin;

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
import lombok.Getter;
import lombok.Setter;

public class UpdateDTO {
    
    @Getter @Setter private String token;
    @Getter @Setter private UsernameDTO username;
    @Getter @Setter private RazonSocialDTO razonSocial;
    @Getter @Setter private CuitDTO cuit;
    @Getter @Setter private DomicilioDTO domicilio;
    @Getter @Setter private LocalidadDTO localidad;
    @Getter @Setter private ProvinciaDTO provincia;
    @Getter @Setter private CPDTO CP;
    @Getter @Setter private TelefonoDTO telefono;
    @Getter @Setter private MailDTO mail;
    @Getter @Setter private IDOrigenDTO iDOrigen;
    @Getter @Setter private EstadoDTO estado;

    public UpdateDTO(String token, UsernameDTO username, RazonSocialDTO razonSocial, CuitDTO cuit,
                       DomicilioDTO domicilio, LocalidadDTO localidad, ProvinciaDTO provincia, CPDTO CP, TelefonoDTO telefono,
                       MailDTO mail, IDOrigenDTO iDOrigen, EstadoDTO estado){
        this.token = token;
        this.username = username;
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.domicilio = domicilio;
        this.localidad = localidad;
        this.provincia = provincia;
        this.CP = CP;
        this.telefono = telefono;
        this.mail = mail;
        this.iDOrigen = iDOrigen;
        this.estado = estado;
    }
}
