package laboratorio.http.dto.admin.fields;

import laboratorio.http.dto.FieldDTO;

public class MailDTO extends FieldDTO {
    public MailDTO(String iv, String encryptedData) {
        super(iv, encryptedData);
    }
    public MailDTO(){}
}
