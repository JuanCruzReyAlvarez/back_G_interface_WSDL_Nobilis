package laboratorio.http.dto.orden.fields;

import laboratorio.http.dto.FieldDTO;

public class FlagDownloadDTO extends FieldDTO{
    public FlagDownloadDTO(String iv, String encryptedData) {
        super(iv, encryptedData);
    }
    public FlagDownloadDTO(){}
}
