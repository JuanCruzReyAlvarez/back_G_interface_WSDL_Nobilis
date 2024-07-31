package laboratorio.http.validators.resultado;

import laboratorio.http.dto.resultado.ResultadoDTO;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.validators.ValidateResult;
import laboratorio.http.validators.Validator;

public class ResultadoDTOValidator extends Validator<ResultadoDTO>{
    
        @Override
        public ValidateResult validate(ResultadoDTO dto) {
            if (dto.getIdOrden().getEncryptedData() == null || dto.getIdOrden().getEncryptedData().isEmpty())
                addError(MessageException.CUIT_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.CUIT_FIELD_NAME));
            
            if (dto.getIdOrden().getIv() == null || dto.getIdOrden().getIv().isEmpty())
                addError(MessageException.CUIT_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.CUIT_FIELD_NAME));
            
            if (dto.getToken() == null || dto.getToken().isEmpty())
            addError(MessageException.TOKEN_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.TOKEN_FIELD_NAME));
            return this;
        }

    
}
