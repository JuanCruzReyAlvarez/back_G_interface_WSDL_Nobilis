package laboratorio.http.dto.derivacion;

import laboratorio.http.dto.admin.fields.IDDerivacionDTO;
import laboratorio.http.dto.derivacion.fields.FechaEmisionDerivacionDTO;
import laboratorio.http.dto.derivacion.fields.NombreDerivacionDTO;
import laboratorio.http.dto.derivacion.fields.TipoDerivacionDTO;
import lombok.Getter;
import lombok.Setter;

public class DerivacionEncryptedDTO {
    @Getter @Setter private IDDerivacionDTO id;
    @Getter @Setter private FechaEmisionDerivacionDTO fechaEmision;
    @Getter @Setter private NombreDerivacionDTO nombre; 
    @Getter @Setter private TipoDerivacionDTO tipo; 
    

    public DerivacionEncryptedDTO (IDDerivacionDTO id, FechaEmisionDerivacionDTO fechaEmision, NombreDerivacionDTO nombre, TipoDerivacionDTO tipo){
        this.id = id;
        this.fechaEmision = fechaEmision;
        this.nombre = nombre;
        this.tipo = tipo;
    }
    
    public DerivacionEncryptedDTO(){
        this.id = new IDDerivacionDTO();
        this.fechaEmision = new FechaEmisionDerivacionDTO();
        this.nombre = new NombreDerivacionDTO();
        this.tipo = new TipoDerivacionDTO();
    }
}

