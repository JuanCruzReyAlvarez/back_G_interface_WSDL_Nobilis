package laboratorio.entities.derivacion;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import laboratorio.entities.orden.Orden;
import laboratorio.entities.origen.Origen;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "derivacion")
public class Derivacion {
    @Id
    @GeneratedValue
    @Getter @Setter private Integer id;
    
    @Column
    @Getter @Setter private String nombre;

    @Column(name = "fecha", columnDefinition = "DATE")
    @Getter public LocalDate fechaEmision; 

    @OneToMany(mappedBy = "derivacion", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @Setter @Getter public List<Orden> ordenes; 

    @ManyToOne
    @JoinColumn(name = "origen_id")
    @Setter @Getter public Origen origen;

    @Enumerated(value = EnumType.STRING)
    @Setter @Getter public Tipo tipo;

    public Derivacion (String nombre, List<Orden> ordenes, Origen origen){
        this.nombre = nombre;
        this.ordenes = ordenes;
        this.origen = origen;
        this.fechaEmision = LocalDate.now();
    }
    public Derivacion (String nombre, Origen origen){
        this.nombre = nombre;
        this.origen = origen;
        this.fechaEmision = LocalDate.now();
    }
    public Derivacion (LocalDate fecha,String nombre){
        this.nombre = nombre;
        this.fechaEmision = fecha;
    }
    public Derivacion (Integer id, LocalDate fecha,String nombre){
        this.id = id;
        this.nombre = nombre;
        this.fechaEmision = fecha;
    }
    public Derivacion (Integer id, LocalDate fecha,String nombre, Tipo tipo){
        this.id = id;
        this.nombre = nombre;
        this.fechaEmision = fecha;
        this.tipo = tipo;
    }
    public Derivacion(String nombre, Origen origen, Tipo tipo) {
        this.nombre = nombre;
        this.origen = origen;
        this.tipo = tipo;
    }
    public Derivacion (){}

} 
