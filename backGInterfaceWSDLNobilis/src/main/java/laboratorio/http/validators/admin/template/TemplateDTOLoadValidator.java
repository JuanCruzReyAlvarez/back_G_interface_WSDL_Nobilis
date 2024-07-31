package laboratorio.http.validators.admin.template;

import laboratorio.http.dto.admin.templates.TemplateEncryptedDTO;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.validators.ValidateResult;
import laboratorio.http.validators.Validator;

public class TemplateDTOLoadValidator extends Validator<TemplateEncryptedDTO> {
        @Override
        public ValidateResult validate(TemplateEncryptedDTO dto) {
            
            if (dto.getBytes().getEncryptedData() == null || dto.getBytes().getEncryptedData().isEmpty())
            addError(MessageException.USERNAME_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.ARCHIVO_ENVIADO_INCORRECTAMENTE));    
            
            if (dto.getBytes().getIv() == null || dto.getBytes().getIv().isEmpty())
                addError(MessageException.USERNAME_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.ARCHIVO_ENVIADO_INCORRECTAMENTE));  
            
        return this;
        
        }
}


