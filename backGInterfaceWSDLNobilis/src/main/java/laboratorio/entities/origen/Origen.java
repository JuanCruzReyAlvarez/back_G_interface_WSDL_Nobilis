package laboratorio.entities.origen;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import laboratorio.entities.auth.Usuario;
import laboratorio.entities.derivacion.Derivacion;
import laboratorio.entities.geografia.Localidad;
import laboratorio.entities.geografia.Provincia;
import laboratorio.entities.paciente.Paciente;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table (name = "origen")
public class Origen{

    @Id
    @GeneratedValue
    @Getter @Setter private Integer id;

    @Column
    @Getter @Setter public Long cuit;

    @Column
    @Getter @Setter public String razonSocial;
    @Column
    @Getter@Setter public String domicilio;
    @Column
    @Getter @Setter public String mail;

    @Column
    @Getter @Setter public Long codigoPostal;
    @Column
    @Getter @Setter public String telefono;
    
    @OneToOne( fetch = FetchType.EAGER)
    @Getter @Setter private Usuario usuario;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "provincia_id", referencedColumnName = "id" )
    @Setter @Getter Provincia provincia;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "localidad_id", referencedColumnName = "id" )
    @Setter @Getter Localidad localidad;

    @OneToMany(mappedBy = "origen", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @Setter @Getter public Set<Paciente> pacientes;

    @OneToMany(mappedBy = "origen", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @Setter @Getter public List<Derivacion> derivaciones;

    @Column
    @Getter @Setter public Integer estado;

    public Origen(Long cuit, String razonSocial, Usuario usuario, List<Derivacion> derivaciones, String mail, Integer estado) {
        this.cuit = cuit;
        this.razonSocial = razonSocial;
        this.usuario = usuario;
        this.derivaciones = derivaciones;
        this.mail = mail;
        this.estado = estado;
    }
    public Origen(Long cuit, String razonSocial, Usuario usuario, String mail, Integer estado) {
        this.cuit = cuit;
        this.razonSocial = razonSocial;
        this.usuario = usuario;
        this.mail = mail;
        this.estado = estado;
    }

    public Origen(){}

}