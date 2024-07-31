package laboratorio.http.dto.derivacion;

import laboratorio.http.dto.admin.fields.IDDerivacionDTO;
import lombok.Getter;
import lombok.Setter;

public class OrdenDeDerivacionDTO {
    @Getter @Setter private String token;
    @Getter @Setter private IDDerivacionDTO idDerivacion;

    public OrdenDeDerivacionDTO(String token, IDDerivacionDTO idDerivacion) {
        this.token = token;
        this.idDerivacion = idDerivacion; 
    }
}
