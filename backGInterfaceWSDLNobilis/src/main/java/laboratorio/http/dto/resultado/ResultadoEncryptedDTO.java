package laboratorio.http.dto.resultado;

import laboratorio.http.dto.admin.fields.EstadoDTO;
import laboratorio.http.dto.resultado.fields.PDFProtocolDTO;
import lombok.Getter;
import lombok.Setter;

public class ResultadoEncryptedDTO {
    @Getter @Setter private EstadoDTO estado;
    @Getter @Setter private PDFProtocolDTO pdfProtocol;

    public ResultadoEncryptedDTO (EstadoDTO estado, PDFProtocolDTO pdfProtocolDTO){
        this.estado = estado;
        this.pdfProtocol = pdfProtocolDTO;
    }
    public ResultadoEncryptedDTO (){
        this.estado = new EstadoDTO();
        this.pdfProtocol = new PDFProtocolDTO();
    }
}
