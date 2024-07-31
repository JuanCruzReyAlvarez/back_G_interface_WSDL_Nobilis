package laboratorio.entities.resultado;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import laboratorio.entities.orden.Estado;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "resultado")
public class Resultado {

    @Id
    @GeneratedValue
    @Getter @Setter private Integer id;   

    @Enumerated(value = EnumType.STRING)
    @Getter @Setter public Estado estado;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    @Getter @Setter String pdf_protocol; 

    public Resultado(){
        this.estado = Estado.EN_PROCESO;
        pdf_protocol = "";
    }
    
}
