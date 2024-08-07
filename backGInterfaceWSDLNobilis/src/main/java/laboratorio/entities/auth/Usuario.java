package laboratorio.entities.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue
    @Getter @Setter private Integer id;
    
    @Column
    @Getter @Setter private String username;
    @Column
    @Getter @Setter private String password;

    @Enumerated(value = EnumType.STRING)
    @Getter @Setter private Rol rol;

    public Usuario(){}
}