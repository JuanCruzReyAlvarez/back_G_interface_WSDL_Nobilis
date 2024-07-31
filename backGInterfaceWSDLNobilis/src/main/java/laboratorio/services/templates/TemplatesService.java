package laboratorio.services.templates;

import java.util.Collections;

import laboratorio.entities.template.Formato;
import laboratorio.entities.template.Template;
import laboratorio.http.dto.admin.templates.BytesDTO;
import laboratorio.http.dto.admin.templates.TemplateEncryptedDTO;
import laboratorio.http.exceptions.BadResquestException;
import laboratorio.http.exceptions.HttpException;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.session.AES.EncrtptAES;
import laboratorio.repository.templates.TemplateRepository;

public class TemplatesService {

    private TemplateRepository templateRepository;


    public TemplatesService() {
        this.templateRepository = new TemplateRepository();

    }

    public void loadOrUpdatePDFInstructive(String bytes) throws HttpException{
        try{
        Template templateDB = this.templateRepository.getFormat(Formato.PDF);
            if( templateDB == null)
                this.templateRepository.save(new Template(bytes,Formato.PDF));
            else{templateDB.setBytes(bytes);
                this.templateRepository.update(templateDB);}
        }catch(Exception ex){
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.FALLO_LOGICA_CARGA_DE_TEMPLATE)); 
        }
    }

    public void loadOrUpdateExcelTemplate(String bytes) throws HttpException{
        try{
            
        Template templateDB = this.templateRepository.getFormat(Formato.EXCEL);
        
            if( templateDB == null)
                this.templateRepository.save(new Template(bytes,Formato.EXCEL));
            else{templateDB.setBytes(bytes);
                this.templateRepository.update(templateDB);}
        }catch(Exception ex){
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.FALLO_LOGICA_CARGA_DE_TEMPLATE)); 
        }
    }


    public Template getPDFInstructive() throws HttpException{
        try{
            Template template = this.templateRepository.getFormat(Formato.PDF);
            return template;
        }catch(Exception ex){
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.FALLO_PEDIDO_ARCHIVO_PDF));
        }
    }

    public Template getExcelTemplate() throws HttpException{
        try{
            Template template = this.templateRepository.getFormat(Formato.EXCEL);
            return template;
        }catch(Exception ex){
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.FALLO_PEDIDO_ARCHIVO_EXCEL));
        }
    }

    public TemplateEncryptedDTO encryptTemplate(Template template) throws HttpException{
        try {
            EncrtptAES<BytesDTO> AESBytes= new EncrtptAES<BytesDTO>();
            TemplateEncryptedDTO templateEncryptedDTO = new TemplateEncryptedDTO();   
            templateEncryptedDTO.setBytes(AESBytes.encrypt(template.getBytes(),templateEncryptedDTO.getBytes()));    
            return templateEncryptedDTO ;
            }catch(Exception ex) {
                throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_ENCRIPTACION_EXTERNA));
            } 
    }
}
