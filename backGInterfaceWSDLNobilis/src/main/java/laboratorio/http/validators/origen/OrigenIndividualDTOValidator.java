package laboratorio.http.validators.origen;

import laboratorio.http.dto.origen.OrigenIndividualDTO;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.validators.ValidateResult;
import laboratorio.http.validators.Validator;

public class OrigenIndividualDTOValidator extends Validator<OrigenIndividualDTO>{
    
        @Override
        public ValidateResult validate(OrigenIndividualDTO dto) {
            if (dto.getCuit().getEncryptedData() == null || dto.getCuit().getEncryptedData().isEmpty())
                addError(MessageException.CUIT_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.CUIT_FIELD_NAME));
            
            if (dto.getCuit().getIv() == null || dto.getCuit().getIv().isEmpty())
                addError(MessageException.CUIT_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.CUIT_FIELD_NAME));
            
            if (dto.getToken() == null || dto.getToken().isEmpty())
            addError(MessageException.TOKEN_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.TOKEN_FIELD_NAME));
            return this;
        }
    }
