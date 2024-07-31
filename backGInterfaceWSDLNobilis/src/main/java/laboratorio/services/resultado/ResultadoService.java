package laboratorio.services.resultado;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;

import laboratorio.entities.orden.Estado;
import laboratorio.entities.resultado.Resultado;
import laboratorio.http.dto.admin.fields.EstadoDTO;
import laboratorio.http.dto.resultado.ResultadoEncryptedDTO;
import laboratorio.http.dto.resultado.fields.PDFProtocolDTO;
import laboratorio.http.exceptions.BadResquestException;
import laboratorio.http.exceptions.HttpException;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.session.AES.EncrtptAES;
import laboratorio.repository.resultado.ResultadoRepository;
import nobilis.services.resultados.pedidoPorOrden.PedidoPorOrdenServiceNobilis;

import org.apache.http.client.ClientProtocolException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPException;

public class ResultadoService {

    private ResultadoRepository resultadoRepository;

    public ResultadoService() {
        this.resultadoRepository = new ResultadoRepository();
    }

    public Resultado updateAndGetResultado(String idOrden) throws HttpException {  
        try{   
            Resultado resultadoPedido = this.resultadoRepository.getResultadoByOrdenId(Integer.parseInt(idOrden)); 


            if(resultadoPedido.estado.name() == Estado.C.toString() ){return resultadoPedido;}
            

            String responseBodyResultado = this.getResultadoFromNobilis(idOrden);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(responseBodyResultado)));


            try{
            String estado = document.getElementsByTagName("estado").item(0).getTextContent();
            if(estado.equals(Estado.C.toString()) != true){
                    return resultadoPedido;
            } 

                String pdf_protocolo = document.getElementsByTagName("protocolo").item(0).getTextContent();
                resultadoPedido.setEstado(Estado.C);
                resultadoPedido.setPdf_protocol(pdf_protocolo);
                this.resultadoRepository.update(resultadoPedido);

            return resultadoPedido;
            }catch(Exception ex){
                return resultadoPedido; 
            }
            
    }catch(Exception ex){
        throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.FALLO_LOGICA_PEDIDO_RESULTADO)); 
    }
}

    String getResultadoFromNobilis(String idOrden) throws HttpException, SOAPException, ClientProtocolException, IOException{
        PedidoPorOrdenServiceNobilis pedidoPorOrdenServiceNobilis = new PedidoPorOrdenServiceNobilis();
        pedidoPorOrdenServiceNobilis.soapResultadoServiceNobilis.addChildElementsToXML(idOrden);
        Object[] responseResultado = pedidoPorOrdenServiceNobilis.httpClientWSDL.execute(pedidoPorOrdenServiceNobilis.getSoapResultadoServiceNobilis().getSoapMessage());
        
        if((Integer)responseResultado[0] != 200){
                throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.FALLO_PEDIDO_RESULTADO + "codigo: " + responseResultado[0]));
            }
        return (String)responseResultado[1];
    }

    public ResultadoEncryptedDTO encryptResultado(Resultado resultado) throws HttpException {
        try {
            EncrtptAES<EstadoDTO> AESEstado = new EncrtptAES<EstadoDTO>();
            ResultadoEncryptedDTO resultadoEncryptedDTO    = new ResultadoEncryptedDTO();   
            resultadoEncryptedDTO.setEstado(AESEstado.encrypt(resultado.getEstado().toString(),resultadoEncryptedDTO.getEstado()));    

            EncrtptAES<PDFProtocolDTO> AESPDFProtocol = new EncrtptAES<PDFProtocolDTO>();            
            resultadoEncryptedDTO.setPdfProtocol(AESPDFProtocol.encrypt(resultado.getPdf_protocol(),resultadoEncryptedDTO.getPdfProtocol())); 
            return resultadoEncryptedDTO;
            }catch (Exception ex) {
                throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_ENCRIPTACION_EXTERNA));
            } 
    }
}
