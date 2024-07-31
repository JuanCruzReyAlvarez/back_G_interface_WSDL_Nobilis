package laboratorio.http.validators.orden;

import laboratorio.http.dto.orden.OrdenIndividualDTO;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.validators.ValidateResult;
import laboratorio.http.validators.Validator;

public class OrdenDTOIndividualValidator extends Validator<OrdenIndividualDTO> {

    @Override
    public ValidateResult validate(OrdenIndividualDTO dto) {
         
        if (dto.getNombre().getEncryptedData() == null || dto.getNombre().getEncryptedData().isEmpty())
            addError(MessageException.NOMBRE_PACIENTE_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.NOMBRE_PACIENTE_FIELD_NAME));

        if (dto.getNombre().getIv() == null || dto.getNombre().getIv().isEmpty())
            addError(MessageException.NOMBRE_PACIENTE_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.NOMBRE_PACIENTE_FIELD_NAME));

        if (dto.getApellido().getEncryptedData() == null || dto.getApellido().getEncryptedData().isEmpty())
            addError(MessageException.APELLIDO_PACIENTE_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.APELLIDO_PACIENTE_FIELD_NAME));

        if (dto.getApellido().getIv() == null || dto.getApellido().getIv().isEmpty())
            addError(MessageException.APELLIDO_PACIENTE_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.APELLIDO_PACIENTE_FIELD_NAME));

        if (dto.getDni().getEncryptedData() == null || dto.getDni().getIv().isEmpty())
            addError(MessageException.DNI_PACIENTE_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.DNI_PACIENTE_FIELD_NAME));

        if (dto.getDni().getIv() == null || dto.getDni().getIv().isEmpty())
            addError(MessageException.DNI_PACIENTE_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.DNI_PACIENTE_FIELD_NAME));

        if (dto.getFechaNacimiento().getEncryptedData() == null || dto.getFechaNacimiento().getIv().isEmpty())
            addError(MessageException.FECHA_NACIMIENTO_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.FECHA_NACIMIENTO_FIELD_NAME));

        if (dto.getFechaNacimiento().getIv() == null || dto.getFechaNacimiento().getIv().isEmpty())
            addError(MessageException.FECHA_NACIMIENTO_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.FECHA_NACIMIENTO_FIELD_NAME));

        if (dto.getEstudios().getEncryptedData() == null || dto.getEstudios().getIv().isEmpty())
            addError(MessageException.ESTUDIOS_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.ESTUDIOS_FIELD_NAME));

        if (dto.getEstudios().getIv() == null || dto.getEstudios().getIv().isEmpty())
            addError(MessageException.ESTUDIOS_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.ESTUDIOS_FIELD_NAME));

        if (dto.getGenero().getEncryptedData() == null || dto.getGenero().getIv().isEmpty())
            addError(MessageException.GENERO_PACIENTE_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.GENERO_PACIENTE_FIELD_NAME));

        if (dto.getGenero().getIv() == null || dto.getGenero().getIv().isEmpty())
            addError(MessageException.GENERO_PACIENTE_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.GENERO_PACIENTE_FIELD_NAME));

        if (dto.getUrgente().getEncryptedData() == null || dto.getUrgente().getIv().isEmpty())
            addError(MessageException.URGENTE_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.URGENTE_FIELD_NAME));

        if (dto.getUrgente().getIv() == null || dto.getUrgente().getIv().isEmpty())
            addError(MessageException.URGENTE_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.URGENTE_FIELD_NAME));

        if (dto.getToken() == null || dto.getToken().isEmpty())
            addError(MessageException.TOKEN_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.TOKEN_FIELD_NAME));

         
        return this;
    
    }
}