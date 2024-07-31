package laboratorio.http.dto.orden;

import java.util.ArrayList;

import laboratorio.http.dto.orden.fields.OrdenesIDDTO;
import lombok.Getter;
import lombok.Setter;

public class OrdenesToUploadFlagDTO {
    @Getter @Setter private String token;
    @Getter @Setter private ArrayList<OrdenesIDDTO> idOrdenes;

    public OrdenesToUploadFlagDTO(String token, ArrayList<OrdenesIDDTO> idOrdenes) {
        this.token = token;
        this.idOrdenes = idOrdenes; 
    }
}
