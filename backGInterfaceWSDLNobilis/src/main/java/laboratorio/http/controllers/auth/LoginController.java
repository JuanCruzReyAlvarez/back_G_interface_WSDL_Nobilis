package laboratorio.http.controllers.auth;


import java.util.Collections;

import laboratorio.entities.auth.Usuario;
import laboratorio.http.controllers.Controller;
import laboratorio.http.dto.auth.LoginDTO;
import laboratorio.http.exceptions.BadResquestException;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.routes.Uri;
import laboratorio.http.session.TokenService;
import laboratorio.http.session.AES.DecryptAES;
import laboratorio.http.validators.admin.LoginDTOValidator;
import laboratorio.services.auth.LoginService;
import spark.Request;
import spark.Response;
import spark.Spark;


public class LoginController extends Controller {

    private LoginService loginService;
    private TokenService tokenService;
    private DecryptAES decryptAes;

    public LoginController(LoginService loginService, TokenService tokenService, DecryptAES decryptAes) {
        this.loginService = loginService;
        this.tokenService = tokenService;
        this.decryptAes = decryptAes;
    }
    
    @Override
    public void routes( ) {
        Spark.post(path(Uri.LOGIN), (rq, rs) -> this.login(rq, rs));
    }

    public String login(Request request, Response response) throws Exception {
        LoginDTO loggerEncrypted = getBody(request, LoginDTO.class, new LoginDTOValidator() );
        this.tokenService.isValidEstadoAtLogin(decryptAes.decrypt(loggerEncrypted.getUsername().getEncryptedData(),loggerEncrypted.getUsername().getIv()));
        try {  
            Usuario usuario = loginService.login(decryptAes.decrypt(loggerEncrypted.getUsername().getEncryptedData(),loggerEncrypted.getUsername().getIv()),
                                                 decryptAes.decrypt(loggerEncrypted.getPassword().getEncryptedData(),loggerEncrypted.getPassword().getIv()));
            String token = this.tokenService.generateJWTencrypted(usuario.getUsername(), 
                                                                  usuario.getRol().name());  
                                             
            return json(token);
        } 
        
        catch (Exception ex) {
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.UNAUTHORIZED_MESSAGE));   
        }

    }

}
