package laboratorio.http.dto.resultado.fields;

import laboratorio.http.dto.FieldDTO;

public class PDFProtocolDTO extends FieldDTO{
    public PDFProtocolDTO (String iv, String encryptedData) {
        super(iv, encryptedData);
    }
    public PDFProtocolDTO (){}
}

