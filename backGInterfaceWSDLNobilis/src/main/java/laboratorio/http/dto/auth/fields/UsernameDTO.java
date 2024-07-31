package laboratorio.http.dto.auth.fields;

import laboratorio.http.dto.FieldDTO;

public class UsernameDTO extends FieldDTO{
    public UsernameDTO(String iv, String encryptedData) {
        super(iv, encryptedData);
    }
    public UsernameDTO(){}
}
