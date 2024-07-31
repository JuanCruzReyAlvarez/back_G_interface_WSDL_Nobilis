package laboratorio.http.validators.admin;

import laboratorio.http.dto.admin.UpdateDTO;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.validators.ValidateResult;
import laboratorio.http.validators.Validator;

public class UpdateDTOValidator extends Validator<UpdateDTO> {
        
        @Override
        public ValidateResult validate(UpdateDTO dto) {
            
            if (dto.getUsername().getEncryptedData() == null || dto.getUsername().getEncryptedData().isEmpty())
            addError(MessageException.USERNAME_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.USERNAME_FIELD_NAME));    
            
            if (dto.getUsername().getIv() == null || dto.getUsername().getIv().isEmpty())
                addError(MessageException.USERNAME_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.USERNAME_FIELD_NAME));
 
            if (dto.getTelefono().getEncryptedData() == null || dto.getTelefono().getEncryptedData().isEmpty())
                addError(MessageException.TELEFONO_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.TELEFONO_FIELD_NAME));
            
            if (dto.getTelefono().getIv() == null || dto.getTelefono().getIv().isEmpty())
                addError(MessageException.TELEFONO_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.TELEFONO_FIELD_NAME));
            
            if (dto.getDomicilio().getEncryptedData() == null || dto.getDomicilio().getEncryptedData().isEmpty())
                addError(MessageException.DOMICILIO_MESSAGE, String.format(MessageException.REQUIRED_MESSAGE, MessageException.DOMICILIO_MESSAGE));
    
            if (dto.getDomicilio().getIv() == null || dto.getDomicilio().getIv().isEmpty())
                addError(MessageException.DOMICILIO_MESSAGE, String.format(MessageException.REQUIRED_MESSAGE, MessageException.DOMICILIO_MESSAGE));
    
            if (dto.getLocalidad().getEncryptedData() == null || dto.getLocalidad().getEncryptedData().isEmpty())
                addError(MessageException.LOCALIDAD_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.LOCALIDAD_FIELD_NAME));
            
            if (dto.getLocalidad().getIv() == null || dto.getLocalidad().getIv().isEmpty())
                addError(MessageException.LOCALIDAD_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.LOCALIDAD_FIELD_NAME));
            
            if (dto.getProvincia().getEncryptedData() == null || dto.getProvincia().getEncryptedData().isEmpty())
                addError(MessageException.PROVINCIA_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.PROVINCIA_FIELD_NAME));
            
            if (dto.getProvincia().getIv() == null || dto.getProvincia().getIv().isEmpty())
                addError(MessageException.PROVINCIA_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.PROVINCIA_FIELD_NAME));
            
            if (dto.getRazonSocial().getEncryptedData() == null || dto.getRazonSocial().getEncryptedData().isEmpty())
                addError(MessageException.RAZON_SOCIAL_MESSAGE, String.format(MessageException.REQUIRED_MESSAGE, MessageException.RAZON_SOCIAL_MESSAGE));
    
            if (dto.getRazonSocial().getIv() == null || dto.getRazonSocial().getIv().isEmpty())
                addError(MessageException.RAZON_SOCIAL_MESSAGE, String.format(MessageException.REQUIRED_MESSAGE, MessageException.RAZON_SOCIAL_MESSAGE));
    
            if (dto.getMail().getEncryptedData() == null || dto.getMail().getEncryptedData().isEmpty())
                addError(MessageException.MAIL_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.MAIL_FIELD_NAME));
            
            if (dto.getMail().getIv() == null || dto.getMail().getIv().isEmpty())
                addError(MessageException.MAIL_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.MAIL_FIELD_NAME));
            
            if (dto.getCuit().getEncryptedData() == null || dto.getCuit().getEncryptedData().isEmpty())
                addError(MessageException.CUIT_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.CUIT_FIELD_NAME));
            
            if (dto.getCuit().getIv() == null || dto.getCuit().getIv().isEmpty())
                addError(MessageException.CUIT_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.CUIT_FIELD_NAME));
    
            if (dto.getCP().getEncryptedData() == null || dto.getCP().getEncryptedData().isEmpty())
                addError(MessageException.CP_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.CP_FIELD_NAME));
    
            if (dto.getCP().getIv() == null || dto.getCP().getIv().isEmpty())
                addError(MessageException.CP_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.CP_FIELD_NAME));
    
            if (dto.getToken() == null || dto.getToken().isEmpty())
                addError(MessageException.TOKEN_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.TOKEN_FIELD_NAME));

            if (dto.getIDOrigen().getEncryptedData() == null || dto.getIDOrigen().getEncryptedData().isEmpty())
                addError(MessageException.ID_ORIGEN_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.ID_ORIGEN_FIELD_NAME));
            
            if (dto.getIDOrigen().getIv() == null || dto.getIDOrigen().getIv().isEmpty())
                addError(MessageException.ID_ORIGEN_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.ID_ORIGEN_FIELD_NAME));
            
            if (dto.getEstado().getEncryptedData() == null || dto.getEstado().getEncryptedData().isEmpty())
                addError(MessageException.ID_ORIGEN_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.ESTADO_FIELD_NAME));
            
            if (dto.getEstado().getIv() == null || dto.getEstado().getIv().isEmpty())
                addError(MessageException.ID_ORIGEN_FIELD_NAME, String.format(MessageException.REQUIRED_MESSAGE, MessageException.ESTADO_FIELD_NAME));
            
            return this;
        }
    }

