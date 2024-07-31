package laboratorio.http.dto.origen;

import laboratorio.http.dto.admin.fields.CuitDTO;
import lombok.Getter;

public class OrigenIndividualDTO {
    @Getter private String token;
    @Getter private CuitDTO cuit;

    public OrigenIndividualDTO(String token, CuitDTO cuit) {
        this.cuit = cuit;
        this.token = token;
    }
}



