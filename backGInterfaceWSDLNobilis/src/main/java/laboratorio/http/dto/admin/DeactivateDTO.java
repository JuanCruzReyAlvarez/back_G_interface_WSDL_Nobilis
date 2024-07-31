package laboratorio.http.dto.admin;

import laboratorio.http.dto.admin.fields.CuitDTO;
import lombok.Getter;
import lombok.Setter;

public class DeactivateDTO {
    @Getter @Setter private CuitDTO cuit;
    @Getter @Setter private String token;

    public DeactivateDTO(CuitDTO cuit ,String token) {
        this.cuit = cuit;
        this.token = token;
    }
}

