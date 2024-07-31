package laboratorio.http.dto.origen;

import lombok.Getter;
public class OrigenDTO {
    @Getter private String token;

    public OrigenDTO(String token) {
        this.token = token;
    }
}
