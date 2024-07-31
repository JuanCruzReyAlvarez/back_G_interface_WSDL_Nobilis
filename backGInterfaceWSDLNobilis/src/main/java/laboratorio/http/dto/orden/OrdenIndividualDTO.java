package laboratorio.http.dto.orden;

import laboratorio.http.dto.orden.fields.ApellidoDTO;
import laboratorio.http.dto.orden.fields.EstudiosDTO;
import laboratorio.http.dto.orden.fields.DniDTO;
import laboratorio.http.dto.orden.fields.FechaNacimientoDTO;
import laboratorio.http.dto.orden.fields.GeneroDTO;
import laboratorio.http.dto.orden.fields.NombreDTO;
import laboratorio.http.dto.orden.fields.ObservacionesDTO;
import laboratorio.http.dto.orden.fields.UrgengenteDTO;
import lombok.Getter;


public class OrdenIndividualDTO {
    @Getter private String token;
    @Getter private NombreDTO nombre;
    @Getter private ApellidoDTO apellido;
    @Getter private DniDTO dni;
    @Getter private EstudiosDTO estudios;
    @Getter private ObservacionesDTO observaciones;
    @Getter private FechaNacimientoDTO fechaNacimiento;
    @Getter private GeneroDTO genero;
    @Getter private UrgengenteDTO urgente;

    public OrdenIndividualDTO(String token,NombreDTO nombre, ApellidoDTO apellido, DniDTO dni, EstudiosDTO estudios,
     ObservacionesDTO observaciones, FechaNacimientoDTO fechaNacimiento, GeneroDTO genero, UrgengenteDTO urgente){
        this.token = token;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.estudios = estudios;
        this.observaciones = observaciones;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.urgente = urgente;
    }
}
