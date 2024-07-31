package laboratorio.http.validators.origen;

import laboratorio.http.dto.origen.OrigenMyDTO;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.validators.ValidateResult;
import laboratorio.http.validators.Validator;

public class OrigenMyDTOValidator extends Validator<OrigenMyDTO>{

    @Override
    public ValidateResult validate(OrigenMyDTO dto) {
        
        if (dto.getToken() == null || dto.getToken().isEmpty())
        addError(MessageException.TOKEN_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.TOKEN_FIELD_NAME));
        return this;
    }
}
