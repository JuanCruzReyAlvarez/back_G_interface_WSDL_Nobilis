package laboratorio.http.validators.derivacion;

import laboratorio.http.dto.derivacion.OrdenDeDerivacionDTO;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.validators.ValidateResult;
import laboratorio.http.validators.Validator;

public class OrdenDeDerivacionValidator extends Validator<OrdenDeDerivacionDTO>{
    @Override
    public ValidateResult validate(OrdenDeDerivacionDTO dto) {

    if (dto.getToken() == null || dto.getToken().isEmpty())
    addError(MessageException.TOKEN_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.TOKEN_FIELD_NAME));

    if (dto.getIdDerivacion().getEncryptedData() == null || dto.getIdDerivacion().getEncryptedData().isEmpty())
    addError(MessageException.NOMBRE_PACIENTE_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.NOMBRE_PACIENTE_FIELD_NAME));

    if (dto.getIdDerivacion().getIv() == null || dto.getIdDerivacion().getIv().isEmpty())
    addError(MessageException.NOMBRE_PACIENTE_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.NOMBRE_PACIENTE_FIELD_NAME));

    return this;
    
    }
}
