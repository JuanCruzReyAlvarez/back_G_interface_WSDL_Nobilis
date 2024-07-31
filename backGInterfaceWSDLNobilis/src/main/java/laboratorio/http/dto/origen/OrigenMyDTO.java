package laboratorio.http.dto.origen;

import lombok.Getter;

public class OrigenMyDTO {
    @Getter private String token;

    public OrigenMyDTO(String token) {
        this.token = token;
    }
}
