package laboratorio.http.dto.derivacion;

import lombok.Getter;
import lombok.Setter;

public class DerivacionesDTO {
    @Getter @Setter private String token;

    public DerivacionesDTO(String token) {
        this.token = token;
    }
}
