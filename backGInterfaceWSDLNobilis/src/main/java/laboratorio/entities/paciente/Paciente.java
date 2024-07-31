package laboratorio.entities.paciente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import laboratorio.entities.origen.Origen;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "paciente")
public class Paciente {

    @Id
    @GeneratedValue
    @Getter @Setter private Integer id;

    @Column
    @Setter @Getter public String nombre;

    @Column
    @Getter public String apellido;

    @Column
    @Getter public Long dni;

    @Column
    @Getter public String fechaNacimiento;

    @Enumerated(value = EnumType.STRING)
    @Getter @Setter private Genero genero;

    @Column
    @Setter @Getter public int nobilisPersisted;

    @ManyToOne
    @JoinColumn(name = "origen_id")
    @Getter @Setter public Origen origen;

    public Paciente (Long dni, String nombre, String apellido, Genero genero, String fechaDeNacimiento){
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.fechaNacimiento = fechaDeNacimiento;
        this.nobilisPersisted = 0;
    }
    public Paciente (){}
}
