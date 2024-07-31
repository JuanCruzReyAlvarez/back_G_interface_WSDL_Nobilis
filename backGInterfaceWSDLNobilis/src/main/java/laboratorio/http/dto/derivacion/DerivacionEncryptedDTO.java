package laboratorio.http.dto.derivacion;

import laboratorio.http.dto.admin.fields.IDDerivacionDTO;
import laboratorio.http.dto.derivacion.fields.FechaEmisionDerivacionDTO;
import laboratorio.http.dto.derivacion.fields.NombreDerivacionDTO;
import lombok.Getter;
import lombok.Setter;

public class DerivacionEncryptedDTO {
    @Getter @Setter private IDDerivacionDTO id;
    @Getter @Setter private FechaEmisionDerivacionDTO fechaEmision;
    @Getter @Setter private NombreDerivacionDTO nombre; 
    

    public DerivacionEncryptedDTO (IDDerivacionDTO id, FechaEmisionDerivacionDTO fechaEmision, NombreDerivacionDTO nombre){
        this.id = id;
        this.fechaEmision = fechaEmision;
        this.nombre = nombre;
    }
    public DerivacionEncryptedDTO(){
        this.id = new IDDerivacionDTO();
        this.fechaEmision = new FechaEmisionDerivacionDTO();
        this.nombre = new NombreDerivacionDTO();
    }
}

