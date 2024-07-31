package laboratorio.entities.estudios;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table (name = "estudio")
public class Estudio{    
    
    @Id
    @GeneratedValue
    private @Getter Integer id;

    @Column
    @Getter public String codigo;

    public Estudio(String codigo){
        this.codigo = codigo;
    }

    public Estudio(){}

}
