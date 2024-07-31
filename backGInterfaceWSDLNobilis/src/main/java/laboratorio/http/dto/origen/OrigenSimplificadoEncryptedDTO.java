package laboratorio.http.dto.origen;

import laboratorio.http.dto.admin.fields.CuitDTO;
import laboratorio.http.dto.admin.fields.EstadoDTO;
import laboratorio.http.dto.admin.fields.MailDTO;
import laboratorio.http.dto.admin.fields.RazonSocialDTO;
import laboratorio.http.dto.auth.fields.UsernameDTO;

import lombok.Getter;
import lombok.Setter;

public class OrigenSimplificadoEncryptedDTO {
    @Setter @Getter private CuitDTO cuit;
    @Setter @Getter private RazonSocialDTO razonSocial;
    @Setter @Getter private UsernameDTO usernameDeUsuario;
    @Setter @Getter private MailDTO mail;
    @Setter @Getter private EstadoDTO estado;

    public OrigenSimplificadoEncryptedDTO(CuitDTO cuit, RazonSocialDTO razonSocial, UsernameDTO usernameDeUsuario, MailDTO mail, EstadoDTO estado) {
        this.cuit = cuit ;
        this.razonSocial = razonSocial ;
        this.usernameDeUsuario = usernameDeUsuario ;
        this.mail = mail ;
        this.estado = estado;
    }

    public OrigenSimplificadoEncryptedDTO() {
        this.cuit = new CuitDTO() ;
        this.razonSocial = new RazonSocialDTO() ;
        this.usernameDeUsuario = new UsernameDTO() ;
        this.mail = new MailDTO() ;
        this.estado = new EstadoDTO();
    }
}
