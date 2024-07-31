package laboratorio.http.dto.orden.fields;

import java.util.ArrayList;
import java.util.List;

import laboratorio.entities.estudios.Estudio;
import laboratorio.http.dto.FieldDTO;

public class EstudiosDTO extends FieldDTO{
    public EstudiosDTO(String iv, String encryptedData) {
        super(iv, encryptedData);
    }

    public static List<Estudio> estudiosDTOToList(String estudios){

        String[] estudiosArray;
        if (estudios.contains(",")){
            estudiosArray = estudios.split(",");
            }else{
            estudiosArray = new String[] { estudios.replaceAll("\\.0*$", "") };
            }
        
        ArrayList<Estudio> listaEstudios = new ArrayList<Estudio>();
        for (String estudioArray : estudiosArray) {
            Estudio estudio = new Estudio(estudioArray);
            listaEstudios.add(estudio);
        }
    return listaEstudios;
    }

    public static String estudiosListToString(List<Estudio> listaEstudios) {
        StringBuilder resultado = new StringBuilder();
        for (Estudio estudio : listaEstudios) {
            if (resultado.length() > 0) {
                resultado.append(","); // Agregar una coma si ya hay elementos en la cadena
            }
            resultado.append(estudio.getCodigo());
        }
        
        return resultado.toString();
    }

    public static String[] estudiosListToArriveString(List<Estudio> listaEstudios) {
        String[] resultado = new String[listaEstudios.size()];
        for (int i = 0; i < listaEstudios.size(); i++) {
            resultado[i] = listaEstudios.get(i).getCodigo();
        }
        return resultado;
    }
    public EstudiosDTO(){}
}
