package laboratorio.http.dto.resultado;

import laboratorio.http.dto.resultado.fields.IdOrdenDTO;
import lombok.Getter;

public class ResultadoDTO {
        @Getter private String token;
        @Getter private IdOrdenDTO idOrden;
    
        public ResultadoDTO(String token) {
            this.token = token;
        }
    }

