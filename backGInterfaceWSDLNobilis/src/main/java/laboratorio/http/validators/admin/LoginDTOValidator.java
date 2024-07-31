package laboratorio.http.validators.admin;

import laboratorio.http.dto.auth.LoginDTO;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.validators.ValidateResult;
import laboratorio.http.validators.Validator;

public class LoginDTOValidator extends Validator<LoginDTO> {

    @Override
    public ValidateResult validate(LoginDTO dto) {
        
        if (dto.getUsername().getEncryptedData() == null || dto.getUsername().getEncryptedData().isEmpty())
        addError(MessageException.USERNAME_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.USERNAME_FIELD_NAME));    
        
        if (dto.getUsername().getIv() == null || dto.getUsername().getIv().isEmpty())
            addError(MessageException.USERNAME_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.USERNAME_FIELD_NAME));

        if (dto.getPassword().getEncryptedData() == null || dto.getPassword().getEncryptedData().isEmpty())
            addError(MessageException.PASSWORD_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.PASSWORD_FIELD_NAME));    

        if (dto.getPassword().getIv() == null || dto.getPassword().getIv().isEmpty())
            addError(MessageException.PASSWORD_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.PASSWORD_FIELD_NAME));    
    
    return this;
    
    }

}