package laboratorio.http.controllers.origen;

import java.util.ArrayList;
import java.util.Set;


import laboratorio.entities.origen.Origen;
import laboratorio.http.controllers.Controller;
import laboratorio.http.dto.origen.OrigenDTO;
import laboratorio.http.dto.origen.OrigenIndividualDTO;
import laboratorio.http.dto.origen.OrigenMyDTO;
import laboratorio.http.dto.origen.OrigenSimplificadoEncryptedDTO;
import laboratorio.http.exceptions.BadResquestException;
import laboratorio.http.exceptions.HttpException;
import laboratorio.http.routes.Uri;
import laboratorio.http.session.TokenService;
import laboratorio.http.session.AES.DecryptAES;
import laboratorio.http.validators.origen.OrigenDTOValidator;
import laboratorio.http.validators.origen.OrigenIndividualDTOValidator;
import laboratorio.http.validators.origen.OrigenMyDTOValidator;
import laboratorio.services.origen.OrigenService;
import spark.Spark;

import spark.Request;
import spark.Response;

public class OrigenController extends Controller {

    private OrigenService service;
    private TokenService tokenService;
    private DecryptAES decryptAes;
    
    public OrigenController(OrigenService service, TokenService tokenService, DecryptAES decryptAes) {
        this.service = service;
        this.tokenService = tokenService;
        this.decryptAes = decryptAes;
    }

    @Override
    public void routes() {
        Spark.post(path(Uri.ORIGENES_SIMPLIFICADOS), (rq, rs) -> this.getOrigenesSimplificados(rq, rs));
        Spark.post(path(Uri.ORIGEN), (rq, rs) -> this.getOrigen(rq, rs));
        Spark.post(path(Uri.MYORIGEN), (rq, rs) -> this.getMyOrigen(rq, rs));
    }

    private String getOrigenesSimplificados(Request request, Response response) throws HttpException {
        OrigenDTO inputEncrypted = getBody(request, OrigenDTO.class, new OrigenDTOValidator());
        this.tokenService.validateTokenAndRolAdmin(inputEncrypted.getToken());
        try {
            return json(this.getOrigenesSimplificadosAndEncrypted()); 
        } catch (Exception ex) {             
            throw new BadResquestException(ex);
        } 
    }

    private String getOrigen(Request request, Response response) throws HttpException {
        OrigenIndividualDTO inputEncrypted = getBody(request, OrigenIndividualDTO.class, new OrigenIndividualDTOValidator());
        this.tokenService.validateTokenAndRolAdmin(inputEncrypted.getToken());
        try {
            Origen origen = service.getOrigenByCuit(decryptAes.decrypt(inputEncrypted.getCuit().getEncryptedData(),inputEncrypted.getCuit().getIv()));
            return json(service.encryptOrigen(origen));
        } catch (Exception ex) {
            throw new BadResquestException(ex);
        } 
    } 

    private String getMyOrigen(Request request, Response response) throws HttpException {
        OrigenMyDTO inputEncrypted = getBody(request, OrigenMyDTO.class, new OrigenMyDTOValidator());
        this.tokenService.validateTokenAndEstado(inputEncrypted.getToken());
        try {
            Origen origen = service.getOrigenByUsernameUsuario(this.tokenService.getUsernameAndRolFromJWT(inputEncrypted.getToken())[0]);
            return json(service.encryptOrigen(origen));
        } catch (Exception ex) {
            throw new BadResquestException(ex);
        } 
    } 

    private ArrayList<OrigenSimplificadoEncryptedDTO> getOrigenesSimplificadosAndEncrypted() throws HttpException{
        try {
        Set<Origen> origenesSimplificados = service.getOrigenesSimplificados();     
            return service.encryptOrigenesSimplificados(origenesSimplificados);
        }catch (Exception ex) {
            throw new BadResquestException(ex);
        } 
    }

}
