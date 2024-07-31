package laboratorio.entities.geografia;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "provincia")
public class Provincia {

    @Id
    @GeneratedValue
    @Getter @Setter private Integer Id;

    @Column
    @Getter public String nombre;
    
    @OneToMany(mappedBy = "provincia", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @Setter @Getter public Set<Localidad> localidades;

    public Localidad getLocalidadByName(String nombre){
        return this.localidades.stream().filter(o -> o.getNombre() == nombre).findFirst().orElse(null);  
    }

    public Provincia(String nombre){
        this.nombre = nombre;
    }
    public Provincia(){} // No eliminar, Hibernate lo necesita.

}
