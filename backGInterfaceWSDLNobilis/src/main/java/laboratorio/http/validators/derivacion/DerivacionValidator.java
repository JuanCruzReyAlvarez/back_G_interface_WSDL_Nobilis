package laboratorio.http.validators.derivacion;

import laboratorio.http.dto.derivacion.DerivacionesDTO;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.validators.ValidateResult;
import laboratorio.http.validators.Validator;

public class DerivacionValidator extends Validator<DerivacionesDTO>{
    @Override
    public ValidateResult validate(DerivacionesDTO dto) {
        
        if (dto.getToken() == null || dto.getToken().isEmpty())
            addError(MessageException.TOKEN_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.TOKEN_FIELD_NAME));
            return this;
        }
    
}

        

