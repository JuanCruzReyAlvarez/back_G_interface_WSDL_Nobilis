package laboratorio.entities.template;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "template")
public class Template {
    @Id
    @GeneratedValue
    private Integer id; 

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    @Getter @Setter String bytes; 

    @Enumerated(value = EnumType.STRING)
    @Getter @Setter private Formato formato;

    public Template(){}

    public Template(Formato formato){
        this.formato = formato;
    }
    public Template(String bytes, Formato formato){
        this.bytes = bytes;
        this.formato = formato;
    }
}


    



