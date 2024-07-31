package laboratorio.http.controllers.orden;

import laboratorio.entities.derivacion.Derivacion;
import laboratorio.entities.origen.Origen;
import laboratorio.http.controllers.Controller;
import laboratorio.http.dto.derivacion.DerivacionesDTO;
import laboratorio.http.dto.derivacion.OrdenDeDerivacionDTO;
import laboratorio.http.dto.orden.OrdenIndividualDTO;
import laboratorio.http.dto.orden.OrdenesToUploadFlagDTO;
import laboratorio.http.dto.orden.fields.EstudiosDTO;
import laboratorio.http.exceptions.BadResquestException;
import laboratorio.http.exceptions.HttpException;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.routes.Uri;
import laboratorio.http.session.TokenService;
import laboratorio.http.session.AES.DecryptAES;
import laboratorio.http.validators.derivacion.DerivacionValidator;
import laboratorio.http.validators.derivacion.OrdenDeDerivacionValidator;
import laboratorio.http.validators.orden.OrdenDTOIndividualValidator;
import laboratorio.http.validators.orden.OrdenDeDerivacionUploadFlagValidator;
import laboratorio.services.derivacion.DerivacionService;
import laboratorio.services.orden.OrdenService;
import laboratorio.services.origen.OrigenService;
import nobilis.services.IntegrityServiceNobilis;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class OrdenController extends Controller {

    private OrdenService ordenservice;
    private DerivacionService derivacionService;
    private OrigenService origenService;
    private TokenService tokenService;
    private DecryptAES decryptAes;
    private IntegrityServiceNobilis integrityServiceNobilis;
    
    public OrdenController(OrdenService ordenService, TokenService tokenService, DecryptAES decryptAes, DerivacionService derivacionService, OrigenService origenService,
                           IntegrityServiceNobilis integrityServiceNobilis) {
        this.ordenservice = ordenService;
        this.tokenService = tokenService;
        this.decryptAes = decryptAes;
        this.derivacionService = derivacionService;
        this.origenService = origenService;
        this.integrityServiceNobilis = integrityServiceNobilis;
    }
    
    @Override
    public void routes( ) {
        Spark.post(path(Uri.ORDEN_INDIVIDUAL), (rq, rs) -> this.ordenIndividual(rq, rs));
        Spark.post(path(Uri.ORDENES_EXCEL), (rq, rs) -> this.ordenesExcel(rq, rs));
        Spark.post(path(Uri.DERIVACIONES), (rq, rs) -> this.getDerivaciones(rq, rs));
        Spark.post(path(Uri.ORDEN_DERIVACION), (rq, rs) -> this.getOrdenesDerivacion(rq, rs));
        Spark.post(path(Uri.ACTUALIZACION_FLAG_DECARGA_ORDEN),(rq,rs) -> this.uploadFlagDownloadOrders(rq,rs));
    }

    public String ordenIndividual(Request request, Response response) throws HttpException {
        OrdenIndividualDTO ordenIndividualEncrypted = getBody(request, OrdenIndividualDTO.class, new OrdenDTOIndividualValidator()); 
        this.tokenService.validateTokenAndEstado(ordenIndividualEncrypted.getToken());
        try {
            
            String username = this.tokenService.getUsernameAndRolFromJWT(ordenIndividualEncrypted.getToken())[0];
            Origen origen = this.origenService.getOrigenByUsernameUsuario(username);
            Derivacion derivacion = this.derivacionService.createAndSaveDerivacion("Derivacion Individual", origen);
            //EstudiosDTO.estudiosDTOToList(decryptAes.decrypt(ordenIndividualEncrypted.getEstudios().getEncryptedData(),ordenIndividualEncrypted.getEstudios().getIv()));
           
            this.ordenservice.createOrdenIndividual(origen,
                                                    decryptAes.decrypt(ordenIndividualEncrypted.getNombre().getEncryptedData(),ordenIndividualEncrypted.getNombre().getIv()),
                                                    decryptAes.decrypt(ordenIndividualEncrypted.getApellido().getEncryptedData(),ordenIndividualEncrypted.getApellido().getIv()),
                                                    decryptAes.decrypt(ordenIndividualEncrypted.getDni().getEncryptedData(),ordenIndividualEncrypted.getDni().getIv()),
                                                    decryptAes.decrypt(ordenIndividualEncrypted.getObservaciones().getEncryptedData(),ordenIndividualEncrypted.getObservaciones().getIv()),
                                                    EstudiosDTO.estudiosDTOToList(decryptAes.decrypt(ordenIndividualEncrypted.getEstudios().getEncryptedData(),ordenIndividualEncrypted.getEstudios().getIv())), 
                                                    decryptAes.decrypt(ordenIndividualEncrypted.getGenero().getEncryptedData(),ordenIndividualEncrypted.getGenero().getIv()),
                                                    decryptAes.decrypt(ordenIndividualEncrypted.getUrgente().getEncryptedData(),ordenIndividualEncrypted.getUrgente().getIv()),
                                                    decryptAes.decrypt(ordenIndividualEncrypted.getFechaNacimiento().getEncryptedData(),ordenIndividualEncrypted.getFechaNacimiento().getIv()),
                                                    derivacion);

            this.integrityServiceNobilis.controlAndPushOrdenIndividual(derivacion, username);                 
                                    
            return json(MessageException.ORDEN_INDIVIDUAL_CREATED);
        } 
        catch (Exception ex) {
            throw new BadResquestException(ex);   
        }
    }

    public String ordenesExcel(Request request, Response response) throws HttpException {
        this.tokenService.validateTokenAndEstado(request.headers("token"));
        
        try {

            byte[] excelContent = request.bodyAsBytes();
            Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelContent));
            Sheet sheet = workbook.getSheetAt(0); // Primera hoja del libro.

            Origen origen = this.origenService.getOrigenByUsernameUsuario(this.tokenService.getUsernameAndRolFromJWT(request.headers("token"))[0]);
            Derivacion derivacion = this.derivacionService.createAndSaveDerivacion(request.headers("excelName"), origen);
            String username = this.tokenService.getUsernameAndRolFromJWT(request.headers("token"))[0];

            this.ordenservice.createPacientesAndOrdenes(sheet, derivacion, origen, this.integrityServiceNobilis, username);
           
            workbook.close();

        return "Archivo Excel procesado exitosamente.";
        }catch (Exception ex) {
                    throw new BadResquestException(ex);   
                }
    }

    public String getDerivaciones(Request request, Response response) throws HttpException{

        DerivacionesDTO derivacionesDTO = getBody(request, DerivacionesDTO.class, new DerivacionValidator()); 
        this.tokenService.validateTokenAndEstado(derivacionesDTO.getToken());
        try {
            return json(this.derivacionService.encryptDerivaciones(this.derivacionService.getDerivaciones(this.tokenService.getUsernameAndRolFromJWT(
                        derivacionesDTO.getToken())[0])));
            }catch (Exception ex) {
                        throw new BadResquestException(ex);   
                    }
    }

    public String getOrdenesDerivacion(Request request, Response response) throws HttpException{
        OrdenDeDerivacionDTO ordenDerivacionDTO = getBody(request, OrdenDeDerivacionDTO.class, new OrdenDeDerivacionValidator()); 
        this.tokenService.validateTokenAndEstado(ordenDerivacionDTO.getToken());
        try {
            String username = this.tokenService.getUsernameAndRolFromJWT(ordenDerivacionDTO.getToken())[0];
            return json(this.ordenservice.encryptOrdenes(this.ordenservice.getOrdenesFromDerivacion(username,
                        this.derivacionService.getDerivacionByID(Integer.parseInt(this.decryptAes.decrypt(
                        ordenDerivacionDTO.getIdDerivacion().getEncryptedData(),ordenDerivacionDTO.
                        getIdDerivacion().getIv()))))));
                                
                        
        }catch (Exception ex) {
                    throw new BadResquestException(ex);   
                }
    }

    public String uploadFlagDownloadOrders(Request request, Response response) throws HttpException{
        OrdenesToUploadFlagDTO ordenDerivacionDTOEncrypted = getBody(request, OrdenesToUploadFlagDTO.class, new OrdenDeDerivacionUploadFlagValidator()); 
        this.tokenService.validateTokenAndEstado(ordenDerivacionDTOEncrypted.getToken());
        try {
            List<String>idOrdenesDecrypted = this.ordenservice.getListEncryptedIdOrdenes(ordenDerivacionDTOEncrypted.getIdOrdenes(),this.decryptAes);
            for(String idOrden : idOrdenesDecrypted ){
                this.ordenservice.updateFlagDownloadOrdenes(idOrden);
            }
                
            return json("Flag marcado correctamente");         
        }catch (Exception ex) {
                    throw new BadResquestException(ex);   
                }
    }

}

