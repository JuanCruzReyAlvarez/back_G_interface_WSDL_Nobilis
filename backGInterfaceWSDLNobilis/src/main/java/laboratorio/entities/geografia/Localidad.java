package laboratorio.entities.geografia;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "localidad")
public class Localidad {

    @Id
    @GeneratedValue
    @Getter @Setter private Integer id;

    @Column
    @Getter public String nombre;

    @ManyToOne
    @JoinColumn(name = "provincia_id")
    @Setter private Provincia provincia;

    public Localidad(String nombre){
        this.nombre = nombre;
    }
    public Localidad(){}
}
