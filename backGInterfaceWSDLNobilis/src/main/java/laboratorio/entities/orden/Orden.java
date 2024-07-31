package laboratorio.entities.orden;

import lombok.Getter;
import lombok.Setter;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import laboratorio.entities.derivacion.Derivacion;
import laboratorio.entities.estudios.Estudio;
import laboratorio.entities.origen.Origen;
import laboratorio.entities.paciente.Paciente;
import laboratorio.entities.resultado.Resultado;


@Entity
@Table (name = "orden")
public class Orden {

    @Id
    @GeneratedValue
    private @Getter Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "paciente_id", referencedColumnName = "id")
    @Getter public Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origen_id", referencedColumnName = "id")
    public Origen origen;

    @Column(name = "fecha", columnDefinition = "DATE")
    public LocalDate fechaEmision; 
    @Column
    @Getter public String Observaciones;
    
    @Enumerated(value = EnumType.STRING)
    public Estado estado;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "derivacion_id", referencedColumnName = "id")
    @Getter @Setter public Derivacion derivacion;
    
    
    @ManyToMany(fetch = FetchType.EAGER) 
    @JoinTable(
        name = "orden_estudio",
        joinColumns = @JoinColumn(name = "orden_id"),
        inverseJoinColumns = @JoinColumn(name = "estudio_id")
    )
    @Getter @Setter public List<Estudio> estudios;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resultado_id")
    @Getter @Setter public Resultado resultado;

    @Column
    @Getter @Setter public Boolean urgencia;

    @Column(name = "flag_download")
    @Getter @Setter public Integer flag_download;


    public Orden(Paciente paciente, Origen origen, List<Estudio> estudios, String observaciones, Estado estado, Derivacion derivacion, Boolean urgencia, Resultado resultado, Integer flag_Download){
        this.paciente = paciente;
        this.origen = origen;
        this.fechaEmision = LocalDate.now();
        this.estudios = estudios;
        this.Observaciones = observaciones;
        this.derivacion = derivacion;
        this.estado = estado;
        this.urgencia = urgencia;
        this.resultado = resultado;
        this.flag_download = flag_Download;
    }
    public Orden(){}
}
