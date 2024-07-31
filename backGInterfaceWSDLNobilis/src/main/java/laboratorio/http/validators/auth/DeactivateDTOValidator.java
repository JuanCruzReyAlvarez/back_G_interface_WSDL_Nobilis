package laboratorio.http.validators.auth;

import laboratorio.http.dto.admin.DeactivateDTO;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.validators.ValidateResult;
import laboratorio.http.validators.Validator;

public class DeactivateDTOValidator extends Validator<DeactivateDTO> {

        @Override
        public ValidateResult validate(DeactivateDTO dto) {
           
            if (dto.getCuit().getEncryptedData() == null || dto.getCuit().getEncryptedData().isEmpty())
                addError(MessageException.USERNAME_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.CUIT_FIELD_NAME));    
            
            if (dto.getCuit().getIv() == null || dto.getCuit().getIv().isEmpty())
                addError(MessageException.USERNAME_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.CUIT_FIELD_NAME));
    
            if (dto.getToken() == null || dto.getToken().isEmpty())
                addError(MessageException.TOKEN_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.TOKEN_FIELD_NAME));
                
            return this;
        }
    }

