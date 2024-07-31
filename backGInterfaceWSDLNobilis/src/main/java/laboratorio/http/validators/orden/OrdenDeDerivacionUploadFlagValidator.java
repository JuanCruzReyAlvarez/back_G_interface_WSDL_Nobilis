package laboratorio.http.validators.orden;

import laboratorio.http.dto.orden.OrdenesToUploadFlagDTO;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.validators.ValidateResult;
import laboratorio.http.validators.Validator;

public class OrdenDeDerivacionUploadFlagValidator extends Validator<OrdenesToUploadFlagDTO>{
    
    @Override
    public ValidateResult validate(OrdenesToUploadFlagDTO dto) {
    
    if (dto.getToken() == null || dto.getToken().isEmpty())
    addError(MessageException.TOKEN_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.TOKEN_FIELD_NAME));
    
    if (dto.getIdOrdenes().get(0).getEncryptedData() == null || dto.getIdOrdenes().get(0).getEncryptedData().isEmpty())
    addError(MessageException.ID_ORDEN_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.ID_ORDEN_FIELD_NAME));
    
    if (dto.getIdOrdenes().get(0).getIv() == null || dto.getIdOrdenes().get(0).getIv().isEmpty())
    addError(MessageException.ID_ORDEN_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.ID_ORDEN_FIELD_NAME));
    
    return this;
        
    }
}