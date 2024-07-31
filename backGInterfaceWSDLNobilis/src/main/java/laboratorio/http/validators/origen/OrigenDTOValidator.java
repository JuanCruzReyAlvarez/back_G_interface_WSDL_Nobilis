package laboratorio.http.validators.origen;

import laboratorio.http.dto.origen.OrigenDTO;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.validators.ValidateResult;
import laboratorio.http.validators.Validator;

public class OrigenDTOValidator extends Validator<OrigenDTO> {

    @Override
    public ValidateResult validate(OrigenDTO dto) {
        if (dto.getToken() == null || dto.getToken().isEmpty())
            addError(MessageException.TOKEN_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.TOKEN_FIELD_NAME));   
        return this;
    }
}