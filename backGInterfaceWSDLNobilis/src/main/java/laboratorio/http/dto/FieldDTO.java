package laboratorio.http.dto;

import laboratorio.http.session.AES.Encryptable;
import lombok.Getter;
import lombok.Setter;

public abstract class FieldDTO implements Encryptable{
    @Getter @Setter private String iv;
    @Getter @Setter private String encryptedData;

    public FieldDTO(String iv, String encryptedData){
        this.iv = iv;
        this.encryptedData = encryptedData;}
        
    public FieldDTO(){}
}
