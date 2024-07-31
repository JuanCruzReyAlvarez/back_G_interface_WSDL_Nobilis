package laboratorio.http.validators.admin.template;

import laboratorio.http.dto.admin.templates.TemplateEncryptedDTO;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.validators.ValidateResult;
import laboratorio.http.validators.Validator;

public class TemplateDTOGetValidator extends Validator<TemplateEncryptedDTO> {
        @Override
        public ValidateResult validate(TemplateEncryptedDTO dto) {
                
            if (dto.getToken()== null || dto.getToken().isEmpty())
                addError(MessageException.PASSWORD_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.TOKEN_FIELD_NAME));    
            
        return this;
        
        }
}
