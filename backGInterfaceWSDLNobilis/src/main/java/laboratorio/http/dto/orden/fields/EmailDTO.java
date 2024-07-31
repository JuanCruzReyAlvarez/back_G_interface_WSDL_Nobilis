package laboratorio.http.dto.orden.fields;

import laboratorio.http.dto.FieldDTO;

public class EmailDTO extends FieldDTO{
    public EmailDTO(String iv, String encryptedData) {
        super(iv, encryptedData);
    }
    public EmailDTO(){}
}


