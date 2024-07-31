package laboratorio.http.dto.admin.fields;

import laboratorio.http.dto.FieldDTO;

public class CuitDTO extends FieldDTO {
    public CuitDTO(String iv, String encryptedData) {
        super(iv, encryptedData);
    }
    public CuitDTO() {}
}
