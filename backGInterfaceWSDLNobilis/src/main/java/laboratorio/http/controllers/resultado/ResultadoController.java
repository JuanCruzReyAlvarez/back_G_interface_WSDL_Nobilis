package laboratorio.http.controllers.resultado;

import laboratorio.entities.resultado.Resultado;
import laboratorio.http.controllers.Controller;
import laboratorio.http.dto.resultado.ResultadoDTO;
import laboratorio.http.dto.resultado.ResultadoEncryptedDTO;
import laboratorio.http.exceptions.BadResquestException;
import laboratorio.http.exceptions.HttpException;
import laboratorio.http.routes.Uri;
import laboratorio.http.session.TokenService;
import laboratorio.http.session.AES.DecryptAES;
import laboratorio.http.validators.resultado.ResultadoDTOValidator;
import laboratorio.services.resultado.ResultadoService;
import spark.Spark;

import spark.Request;
import spark.Response;

public class ResultadoController extends Controller {

    private ResultadoService service;
    private TokenService tokenService;
    private DecryptAES decryptAes;
    
    public ResultadoController(ResultadoService service, TokenService tokenService, DecryptAES decryptAes) {
        this.service = service;
        this.tokenService = tokenService;
        this.decryptAes = decryptAes;
    }

    @Override
    public void routes() {
        Spark.post(path(Uri.RESULTADO), (rq, rs) -> this.getResultado(rq, rs));
    }

    private String getResultado(Request request, Response response) throws HttpException {

        ResultadoDTO inputEncrypted = getBody(request, ResultadoDTO.class, new ResultadoDTOValidator());
        this.tokenService.validateTokenAndEstado(inputEncrypted.getToken());
        try {
            String idOrden = decryptAes.decrypt(inputEncrypted.getIdOrden().getEncryptedData(),inputEncrypted.getIdOrden().getIv()); 
            return json(this.getResultadoEncrypted(idOrden)); 
        } catch (Exception ex) {             
            throw new BadResquestException(ex);
        } 
    }

    private ResultadoEncryptedDTO getResultadoEncrypted(String idOrden) throws HttpException{
        try {
        Resultado resultado = service.updateAndGetResultado(idOrden);     
            return service.encryptResultado(resultado); 
        }catch (Exception ex) { 
            throw new BadResquestException(ex); 
        }   
    }   

}   
