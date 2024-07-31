package laboratorio.http.dto.auth.fields;

import laboratorio.http.dto.FieldDTO;

public class PasswordDTO extends FieldDTO{
    public PasswordDTO(String iv, String encryptedData) {
        super(iv, encryptedData);
    }
}

