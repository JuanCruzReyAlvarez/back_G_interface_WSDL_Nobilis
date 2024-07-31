package laboratorio.http.dto.admin.templates;

import laboratorio.http.dto.FieldDTO;

public class BytesDTO extends FieldDTO {
    public BytesDTO(String iv, String encryptedData) {
        super(iv, encryptedData);
    }
    public BytesDTO(){}
}
