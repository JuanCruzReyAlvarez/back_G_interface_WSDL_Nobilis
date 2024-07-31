package laboratorio.http.dto.admin.templates;

import lombok.Getter;
import lombok.Setter;

public class TemplateEncryptedDTO {
    @Getter @Setter private String token;
    @Getter @Setter private BytesDTO bytes;

    public TemplateEncryptedDTO(BytesDTO bytes ,String token) {
        this.bytes = bytes;
        this.token = token;
    }
    public TemplateEncryptedDTO(){}
}


