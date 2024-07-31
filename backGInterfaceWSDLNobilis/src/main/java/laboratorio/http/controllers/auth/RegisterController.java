package laboratorio.http.controllers.auth;


import laboratorio.http.controllers.Controller;
import laboratorio.http.dto.admin.DeactivateDTO;
import laboratorio.http.dto.admin.RegisterDTO;
import laboratorio.http.dto.admin.UpdateDTO;
import laboratorio.http.exceptions.BadResquestException;
import laboratorio.http.exceptions.HttpException;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.context.CORS;
import laboratorio.http.routes.Uri;
import laboratorio.http.session.SessionMessage;
import laboratorio.http.session.TokenService;
import laboratorio.http.session.AES.DecryptAES;
import laboratorio.http.validators.admin.RegisterDTOValidator;
import laboratorio.http.validators.admin.UpdateDTOValidator;
import laboratorio.http.validators.auth.DeactivateDTOValidator;
import laboratorio.http.validators.auth.UpdateMyDTOValidator;
import laboratorio.services.auth.RegisterService;
import spark.Request;
import spark.Response;
import spark.Spark;


public class RegisterController extends Controller {
    

    private RegisterService service;
    private TokenService tokenService;
    private DecryptAES decryptAes;
    
    public RegisterController(RegisterService service, TokenService tokenService, DecryptAES decryptAes) {
        this.service = service;
        this.tokenService = tokenService;
        this.decryptAes = decryptAes;
    }

    @Override
    public void routes() {
        CORS.enableCORS();
        Spark.post(path(Uri.REGISTER), (rq, rs) -> this.register(rq, rs));
        Spark.post(path(Uri.UPDATE), (rq, rs) -> this.update(rq, rs));
        Spark.post(path(Uri.UPDATEMY), (rq, rs) -> this.updateMy(rq, rs));
        Spark.post(path(Uri.CHANGE_STATE), (rq, rs) -> this.changeState(rq, rs));
    }

    private String register(Request request, Response response) throws HttpException {

        RegisterDTO inputEncrypted = getBody(request, RegisterDTO.class, new RegisterDTOValidator());
        this.tokenService.validateTokenAndRolAdmin(inputEncrypted.getToken());

        try {
            SessionMessage session = service.register(decryptAes.decrypt(inputEncrypted.getUsername().getEncryptedData(),inputEncrypted.getUsername().getIv()),
                                                      decryptAes.decrypt(inputEncrypted.getPassword().getEncryptedData(),inputEncrypted.getPassword().getIv()),
                                                      decryptAes.decrypt(inputEncrypted.getCP().getEncryptedData(),inputEncrypted.getCP().getIv()),
                                                      decryptAes.decrypt(inputEncrypted.getCuit().getEncryptedData(),inputEncrypted.getCuit().getIv()),
                                                      decryptAes.decrypt(inputEncrypted.getDomicilio().getEncryptedData(),inputEncrypted.getDomicilio().getIv()),
                                                      decryptAes.decrypt(inputEncrypted.getLocalidad().getEncryptedData(),inputEncrypted.getLocalidad().getIv()),
                                                      decryptAes.decrypt(inputEncrypted.getMail().getEncryptedData(),inputEncrypted.getMail().getIv()),
                                                      decryptAes.decrypt(inputEncrypted.getProvincia().getEncryptedData(),inputEncrypted.getProvincia().getIv()),
                                                      decryptAes.decrypt(inputEncrypted.getRazonSocial().getEncryptedData(),inputEncrypted.getRazonSocial().getIv()),
                                                      decryptAes.decrypt(inputEncrypted.getTelefono().getEncryptedData(),inputEncrypted.getTelefono().getIv()),
                                                      decryptAes.decrypt(inputEncrypted.getEstado().getEncryptedData(),inputEncrypted.getEstado().getIv()));
            return json(session.getMessage()); 
        } catch (Exception ex) {
            throw new BadResquestException(ex);   
        } 
    }

    private String update(Request request, Response response) throws HttpException { //Hay que updetear. 

        UpdateDTO inputEncrypted = getBody(request, UpdateDTO.class, new UpdateDTOValidator());
        this.tokenService.validateTokenAndRolAdmin(inputEncrypted.getToken());
        try { 
            SessionMessage session = service.updateOrigen(   decryptAes.decrypt(inputEncrypted.getCP().getEncryptedData(),inputEncrypted.getCP().getIv()),
                                                             decryptAes.decrypt(inputEncrypted.getCuit().getEncryptedData(),inputEncrypted.getCuit().getIv()),
                                                             decryptAes.decrypt(inputEncrypted.getDomicilio().getEncryptedData(),inputEncrypted.getDomicilio().getIv()),
                                                             decryptAes.decrypt(inputEncrypted.getLocalidad().getEncryptedData(),inputEncrypted.getLocalidad().getIv()),
                                                             decryptAes.decrypt(inputEncrypted.getMail().getEncryptedData(),inputEncrypted.getMail().getIv()),
                                                             decryptAes.decrypt(inputEncrypted.getProvincia().getEncryptedData(),inputEncrypted.getProvincia().getIv()),
                                                             decryptAes.decrypt(inputEncrypted.getRazonSocial().getEncryptedData(),inputEncrypted.getRazonSocial().getIv()),
                                                             decryptAes.decrypt(inputEncrypted.getTelefono().getEncryptedData(),inputEncrypted.getTelefono().getIv()),
                                                             decryptAes.decrypt(inputEncrypted.getUsername().getEncryptedData(),inputEncrypted.getUsername().getIv()),
                                                             decryptAes.decrypt(inputEncrypted.getIDOrigen().getEncryptedData(),inputEncrypted.getIDOrigen().getIv()),
                                                             decryptAes.decrypt(inputEncrypted.getEstado().getEncryptedData(),inputEncrypted.getEstado().getIv()));                                
                        
              
            return json(session.getMessage());
        } catch (Exception ex) {
            throw new BadResquestException(ex); 
        } 
    }

    private String updateMy(Request request, Response response) throws HttpException { //Hay que updetear. 

        UpdateDTO inputEncrypted = getBody(request, UpdateDTO.class, new UpdateMyDTOValidator());
        this.tokenService.validateTokenAndEstado(inputEncrypted.getToken());
        try { 
            SessionMessage session = service.updateMyOrigen( decryptAes.decrypt(inputEncrypted.getCP().getEncryptedData(),inputEncrypted.getCP().getIv()),
                                                             decryptAes.decrypt(inputEncrypted.getDomicilio().getEncryptedData(),inputEncrypted.getDomicilio().getIv()),
                                                             decryptAes.decrypt(inputEncrypted.getLocalidad().getEncryptedData(),inputEncrypted.getLocalidad().getIv()),
                                                             decryptAes.decrypt(inputEncrypted.getMail().getEncryptedData(),inputEncrypted.getMail().getIv()),
                                                             decryptAes.decrypt(inputEncrypted.getProvincia().getEncryptedData(),inputEncrypted.getProvincia().getIv()),
                                                             decryptAes.decrypt(inputEncrypted.getTelefono().getEncryptedData(),inputEncrypted.getTelefono().getIv()),
                                                             decryptAes.decrypt(inputEncrypted.getUsername().getEncryptedData(),inputEncrypted.getUsername().getIv()),
                                                             decryptAes.decrypt(inputEncrypted.getIDOrigen().getEncryptedData(),inputEncrypted.getIDOrigen().getIv()),
                                                             decryptAes.decrypt(inputEncrypted.getEstado().getEncryptedData(),inputEncrypted.getEstado().getIv()));                                
                        
              
            return json(session.getMessage());
        } catch (Exception ex) {
            throw new BadResquestException(ex); 
        } 
    }

    private String changeState(Request request, Response response) throws HttpException {

        DeactivateDTO inputEncrypted = getBody(request, DeactivateDTO.class, new DeactivateDTOValidator());
        this.tokenService.validateTokenAndRolAdmin(inputEncrypted.getToken());
        try {
            SessionMessage session= this.service.changeState(decryptAes.decrypt(inputEncrypted.getCuit().getEncryptedData(),
                                                            inputEncrypted.getCuit().getIv()));
            session.setMessage(MessageException.DEACTIVATE_STATUS_OK);
            return json(session.getMessage());
        } catch (Exception ex) {
            throw new BadResquestException(ex);
        } 
    }
}
