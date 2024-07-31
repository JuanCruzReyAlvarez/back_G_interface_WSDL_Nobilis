package laboratorio.http.dto.admin.fields;

import laboratorio.http.dto.FieldDTO;

public class CPDTO extends FieldDTO {
    public CPDTO(String iv, String encryptedData) {
        super(iv, encryptedData);
    }
    public CPDTO(){}
}
