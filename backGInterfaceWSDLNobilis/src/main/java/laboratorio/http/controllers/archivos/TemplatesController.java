package laboratorio.http.controllers.archivos;

import laboratorio.entities.template.Template;
import laboratorio.http.controllers.Controller;
import laboratorio.http.dto.admin.templates.TemplateEncryptedDTO;
import laboratorio.http.exceptions.BadResquestException;
import laboratorio.http.exceptions.HttpException;
import laboratorio.http.routes.Uri;
import laboratorio.http.session.AES.DecryptAES;
import laboratorio.http.validators.admin.template.TemplateDTOGetValidator;
import laboratorio.http.validators.admin.template.TemplateDTOLoadValidator;
import laboratorio.services.templates.TemplatesService;
import spark.Request;
import spark.Response;
import spark.Spark;
import laboratorio.http.session.TokenService;

public class TemplatesController extends Controller{

    private TemplatesService templateService;
    private TokenService tokenService;
    private DecryptAES decryptAes;

    public TemplatesController(TemplatesService templateService, TokenService tokenService, DecryptAES decryptAes) {
        this.templateService = templateService;
        this.tokenService = tokenService;
        this.decryptAes = decryptAes;
    }

    @Override
    public void routes( ) {
        Spark.post(path(Uri.EXCEL_TEMPLATE_DERIVACION_LOTE_LOAD), (rq, rs) -> this.loadExcelTemplate(rq, rs));
        Spark.post(path(Uri.PDF_TEMPLATE_INSTRUCTIVO_LOAD), (rq, rs) -> this.loadPDFInstructiveTemplate(rq, rs));

        Spark.post(path(Uri.EXCEL_TEMPLATE_DERIVACION_LOTE_GET), (rq, rs) -> this.getExcelTemplate(rq, rs));
        Spark.post(path(Uri.PDF_TEMPLATE_INSTRUCTIVO_GET), (rq, rs) -> this.getPDFInstructiveTemplate(rq, rs));
    }
    
    private String getPDFInstructiveTemplate(Request request, Response response) throws HttpException{
        TemplateEncryptedDTO templatePDFInstructiveEncrypted = getBody(request, TemplateEncryptedDTO.class, new TemplateDTOGetValidator());
        this.tokenService.validateTokenAndEstado(templatePDFInstructiveEncrypted.getToken());
        try{
            Template template = this.templateService.getPDFInstructive();
            return json(template.getBytes()); // return json(this.templateService.encryptTemplate(template)); TODO
            }catch(Exception ex){
                throw new BadResquestException(ex); 
            }
    }

    private String getExcelTemplate(Request request, Response response) throws HttpException{
        TemplateEncryptedDTO templateExcelEncrypted = getBody(request, TemplateEncryptedDTO.class, new TemplateDTOGetValidator());
        this.tokenService.validateTokenAndEstado(templateExcelEncrypted.getToken());
        try{
            Template template = this.templateService.getExcelTemplate();
            return json(template.getBytes()); // return json(this.templateService.encryptTemplate(template)); TODO
            }catch(Exception ex){
                throw new BadResquestException(ex); 
            }
    }

    private String loadPDFInstructiveTemplate(Request request, Response response) throws HttpException{
        TemplateEncryptedDTO templatePDFInstructiveEncrypted = getBody(request, TemplateEncryptedDTO.class, new TemplateDTOLoadValidator());
        this.tokenService.validateTokenAndRolAdmin(request.headers("token"));
        try{
        this.templateService.loadOrUpdatePDFInstructive(decryptAes.decrypt(templatePDFInstructiveEncrypted.getBytes().getEncryptedData(),templatePDFInstructiveEncrypted.getBytes().getIv()));
        }catch(Exception ex){
            throw new BadResquestException(ex); 
        }
        return "Archivo PDF cargado exitosamente.";
    }

    private String loadExcelTemplate(Request request, Response response) throws HttpException{
        TemplateEncryptedDTO templateExcelEncrypted = getBody(request, TemplateEncryptedDTO.class, new TemplateDTOLoadValidator());
        this.tokenService.validateTokenAndRolAdmin(request.headers("token"));

        try{
        this.templateService.loadOrUpdateExcelTemplate(decryptAes.decrypt(templateExcelEncrypted.getBytes().getEncryptedData(),templateExcelEncrypted.getBytes().getIv()));
        }catch(Exception ex){
            throw new BadResquestException(ex); 
        }
        return "Archivo Excel cargado exitosamente.";
    }


}
