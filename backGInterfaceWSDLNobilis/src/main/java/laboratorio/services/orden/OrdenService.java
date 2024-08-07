package laboratorio.services.orden;

import laboratorio.entities.derivacion.Derivacion;
import laboratorio.entities.estudios.Estudio;
import laboratorio.entities.orden.Estado;
import laboratorio.entities.orden.Orden;
import laboratorio.entities.origen.Origen;
import laboratorio.entities.paciente.Genero;
import laboratorio.entities.paciente.Paciente;
import laboratorio.entities.resultado.Resultado;
import laboratorio.http.dto.orden.OrdenEncryptedDTO;
import laboratorio.http.dto.orden.fields.ApellidoDTO;
import laboratorio.http.dto.orden.fields.EstudiosDTO;
import laboratorio.http.dto.orden.fields.FlagDownloadDTO;
import laboratorio.http.dto.orden.fields.DniDTO;
import laboratorio.http.dto.orden.fields.NombreDTO;
import laboratorio.http.dto.orden.fields.ObservacionesDTO;
import laboratorio.http.dto.orden.fields.OrdenesIDDTO;
import laboratorio.http.dto.resultado.fields.IdOrdenDTO;
import laboratorio.http.exceptions.BadResquestException;
import laboratorio.http.exceptions.HttpException;
import laboratorio.http.exceptions.MessageException;
import laboratorio.http.session.AES.DecryptAES;
import laboratorio.http.session.AES.EncrtptAES;
import laboratorio.repository.derivacion.DerivacionRepository;
import laboratorio.repository.estudio.EstudioRepository;
import laboratorio.repository.orden.OrdenRepository;
import laboratorio.repository.paciente.PacienteRepository;
//import laboratorio.services.resultado.ResultadoService;
import nobilis.services.IntegrityServiceNobilis;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.*;

public class OrdenService {

    public PacienteRepository pacienteRepository;
    private OrdenRepository ordenRepository;
    private EstudioRepository estudioRepository;
    private DerivacionRepository derivacionRepository;
    //private ResultadoService resultadoService;

    public OrdenService() {
        this.ordenRepository = new OrdenRepository();
        this.pacienteRepository = new PacienteRepository();
        this.estudioRepository = new EstudioRepository();
        this.derivacionRepository = new DerivacionRepository();
    }

    public Orden createOrdenIndividual(Origen origen, String nombre, String apellido, String dni,
                        String observaciones, List<Estudio> estudios, String genero,
                        String urgente, String fechaDeNacimiento, Derivacion derivacion) throws HttpException {                    
        try{   
        List<Estudio> estudiosValidatedAndSaved = this.assingValidateAndSaveEstudios(estudios);

        Orden orden= new Orden(this.assingValidateAndSavePacientes(Collections.singletonList(new Paciente(Long.parseLong(dni),
                                nombre, apellido, checkGenero(genero), this.checkDate(fechaDeNacimiento))), origen).get(0), origen, estudiosValidatedAndSaved, observaciones, 
                                Estado.EN_PROCESO, derivacion, isUrgencia(urgente), new Resultado(), 0);            
        return(this.ordenRepository.save(orden));
        }catch(Exception ex){
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_GUARDANDO_ORDEN_INDIVIDUAL));   
        }
    }

    public List<Orden> createPacientesAndOrdenes(Sheet sheet, Derivacion derivacion, Origen origen, IntegrityServiceNobilis integrityServiceNobilis,
                                                 String username) throws HttpException {
        try {
            return this.saveOrdenes(this.validateAndCreateOrdenes(sheet, origen, this.validateAndCreatePacientes(sheet, origen, derivacion), derivacion), integrityServiceNobilis, username);
        }catch( Exception ex){
            throw new BadResquestException(ex);   
        }
    }

    private List<Paciente> getPacientesFromOrdenes(List<Orden> ordenes){
        ArrayList <Paciente> pacientesDeOrdenes = new ArrayList<Paciente>();
        for(Orden orden : ordenes){
            pacientesDeOrdenes.add(orden.getPaciente());
        }
        return pacientesDeOrdenes;
    }

    public ArrayList<Paciente> getPacientesNoCargadosFromOrden(List<Orden> ordenes){
        return (ArrayList<Paciente>) this.getPacientesFromOrdenes(ordenes).stream().
        filter(paciente -> paciente.getNobilisPersisted() == 0).collect(Collectors.toList());
    }

    private List<Orden> saveOrdenes(List<Orden> ordenes, IntegrityServiceNobilis integrityServiceNobilis, String username) throws HttpException{
        try{  
            this.ordenRepository.saveAll(ordenes);
            integrityServiceNobilis.controlAndPushOrdenExcel(ordenes.get(0).getDerivacion(), username); 
            return ordenes;                         
        }catch(Exception ex){
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_GUARDANDO_ORDENES));   
        }
    }

    public void deleteOrden(Orden orden) throws HttpException{
        try{
            this.ordenRepository.delete(orden);
        }catch(Exception ex){
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_ELIMINANDO_ORDENES));   
        }
    }

        public void deleteAllOrdenes(ArrayList<Orden> ordenes) throws HttpException{
        try{
            for(Orden orden : ordenes){
                this.ordenRepository.delete(orden);
            }
        }catch(Exception ex){
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_ELIMINANDO_ORDENES));   
        }
    }



    private List<Paciente> validateAndCreatePacientes (Sheet sheet, Origen origen, Derivacion derivacion) throws HttpException{
        try{
        List<Paciente> pacientes = new ArrayList<Paciente>();
            for (int rowIndex = 2; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if(row.getCell(0).toString() != "" ){
                    Paciente paciente = new Paciente(new BigDecimal((row.getCell(0).toString())).longValue(),
                                        row.getCell(1).toString().toLowerCase(),row.getCell(2).toString().toLowerCase(),
                                        this.checkGenero(row.getCell(3).toString().toUpperCase()),this.checkDate(convertDate(row.getCell(4).toString()))); 
                    pacientes.add(paciente);
                    }
                }
        return this.assingValidateAndSavePacientes(pacientes, origen);
        }catch(Exception ex){
            this.derivacionRepository.delete(derivacion);
            throw new BadResquestException(ex);   
        }
    }

    private List<Orden> validateAndCreateOrdenes(Sheet sheet, Origen origen, List<Paciente> pacientesGuardados, Derivacion derivacion) throws HttpException{
        try{
            int numPaciente = 0;
            List<Orden> ordenes = new ArrayList<Orden>();
            
                for (int rowIndex = 2 ; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    if(row.getCell(0).toString() != "" ){
                        List<Estudio> estudiosValidatedAndSaved = this.assingValidateAndSaveEstudios(EstudiosDTO.estudiosDTOToList((row.getCell(5).toString())));/////////////////nuevooo
                        Orden orden = new Orden(pacientesGuardados.get(numPaciente), origen,estudiosValidatedAndSaved , 
                                                row.getCell(6).toString(), Estado.EN_PROCESO, derivacion, isUrgencia(row.getCell(7).toString().toUpperCase()), new Resultado(), 0);

                        ordenes.add(orden); numPaciente = numPaciente + 1; 
                        } }
            return ordenes;
        }catch(Exception ex){
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_ORDEN_URGENCIA));  
        }
    }

    private List<Estudio> assingValidateAndSaveEstudios(List<Estudio> estudios) throws HttpException{
        try{
            List<Estudio> estudiosValidatedAndSaved = new ArrayList<Estudio>();
            for(Estudio estudio : estudios){
                Estudio estudioDB = this.estudioRepository.getEstudioByCodigo( estudio.getCodigo() );
               if(estudioDB == null){this.estudioRepository.save(estudio);estudiosValidatedAndSaved.add(this.estudioRepository.getEstudioByCodigo(estudio.getCodigo()));}  
               else{estudiosValidatedAndSaved.add(estudioDB);}                                                                        
               }
            return estudiosValidatedAndSaved;
        }
        catch(Exception ex){
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_GUARDANDO_ESTUDIOS));   
        }
    }

    private List<Paciente> assingValidateAndSavePacientes( List<Paciente> pacientes, Origen origen) throws HttpException{
        try{
            List<Paciente> pacientesModificados = new ArrayList<Paciente>();
            for(Paciente paciente: pacientes){
                Paciente pacienteDB = this.pacienteRepository.getPacienteByNombreApellidoDni( paciente.getNombre(),paciente.getApellido(), paciente.getDni() );
               if(pacienteDB == null){paciente.setOrigen(origen);this.pacienteRepository.save(paciente);pacientesModificados.add(this.pacienteRepository.
                                      getPacienteByNombreApellidoDni( paciente.getNombre(),paciente.getApellido(),paciente.getDni() ) );}  
               else{this.updateOldOrigenPaciente(pacienteDB, origen);pacientesModificados.add(this.pacienteRepository.getPacienteByNombreApellidoDni(pacienteDB.getNombre(), 
                                      pacienteDB.getApellido(), pacienteDB.getDni() ));}                                                                         
               }
            return pacientesModificados;
        }catch(Exception ex){
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_GUARDANDO_PACIENTES));   
        }
    }

    public ArrayList<Orden> getOrdenesFromDerivacion(String username, Derivacion derivacion) throws HttpException {
        try {
            ArrayList<Orden> ordenes = this.ordenRepository.getOrdenesByDerivacion(derivacion);
            return ordenes;
        }catch (Exception ex) {
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_OBTENIENDO_ORDENES_DERIVACION));
        } 
    }

    public Boolean isUrgencia(String urgencia) throws BadResquestException{
        if(urgencia.equals("SI"))
            return true; 
            else if(urgencia.equals("NO")) return false;
        throw new BadResquestException(urgencia);
    }
    public Genero checkGenero(String genero) throws BadResquestException{
        if(genero.equals("M"))
            return Genero.M;
            else if(genero.equals("F")) return Genero.F;
    throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_ORDEN_GENERO)); 
    }
    private String convertDate(String fechaExcel) throws BadResquestException{
        SimpleDateFormat formatoInicial = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            Date fechaInicial = formatoInicial.parse(fechaExcel);
            SimpleDateFormat formatoFinal = new SimpleDateFormat("dd/MM/yyyy");
            String fechaFinalStr = formatoFinal.format(fechaInicial);
            return fechaFinalStr;
        } catch (ParseException e) {
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_EN_EL_FORMATO_DE_LA_FECHA_DEL_EXCEL));
        }
    }
    private String checkDate(String fechaExcel) throws BadResquestException, ParseException{
        System.err.println("11111111111111111111111111111111111111111111111");
        boolean cumpleFormato = verificarFormatoFecha(fechaExcel,"dd/MM/yyyy");
        if (cumpleFormato == false) {
            boolean cumpleFormatov2 = verificarFormatoFecha(fechaExcel,"yyyy-MM-dd");
            if(cumpleFormatov2 == false){
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_EN_EL_FORMATO_DE_LA_FECHA_DEL_EXCEL));
            }else{
                System.err.println("22222222222222222222222222222222222222222222222222");
                return this.fixDate(fechaExcel);
            }
        } 
            return fechaExcel;
    }
    private String fixDate(String date) throws ParseException, BadResquestException{
        try{
        SimpleDateFormat formatoFechaEntrada = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoFechaSalida = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = formatoFechaEntrada.parse(date);
        String fechaDeNacimientoFormateada = formatoFechaSalida.format(fecha);
        System.err.println("33333333333333333333333333333333333333333333333333");
        System.err.println(fechaDeNacimientoFormateada);
        return fechaDeNacimientoFormateada;
        }catch(Exception e){
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_EN_EL_CORRECTOR_AUTOMATICO_DE_FECHA));
        }
        
    }
    
    public static boolean verificarFormatoFecha(String fechaString, String formatoFecha) {
            SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha);
            sdf.setLenient(false); // Esto hace que el formato sea estricto
            try {
                sdf.parse(fechaString);
                return true; // La cadena cumple con el formato
            } catch (ParseException e) {
                return false; // La cadena no cumple con el formato
            }
        }
    
    
    private void updateOldOrigenPaciente(Paciente pacienteDB, Origen origen){
        pacienteDB.setOrigen(origen);
        this.pacienteRepository.update(pacienteDB);
    }

    public void updateFlagDownloadOrdenes(String idOrdenes) throws HttpException {
        try{
        List<Integer> idOrdenesList = this.getIDOrdenes(idOrdenes);
        this.ordenRepository.uploadFlagDownloadFrom(idOrdenesList);
        }catch (Exception ex) {
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_ACTUALIZANDO_FLAGS));
        } 
    }

    private List<Integer> getIDOrdenes(String idOrdenes) throws HttpException{
        try{
        List<String> stringList = Arrays.asList(idOrdenes.split(","));
        List<Integer> integerList = stringList.stream()
        .map(Integer::parseInt)
        .collect(Collectors.toList());
        return integerList;
        }catch (Exception ex) {
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_SINTAXIS_ID_ORDENES));
        } 
    }

    public List<String> getListEncryptedIdOrdenes(ArrayList<OrdenesIDDTO> idOrdenesEncrypted, DecryptAES decryptAES)throws HttpException{

        ArrayList<String> ordenesIdsDecrypted = new ArrayList<String>();
        for(OrdenesIDDTO ordenId : idOrdenesEncrypted){
            ordenesIdsDecrypted.add(decryptAES.decrypt(ordenId.getEncryptedData(), ordenId.getIv()));
        }
        return ordenesIdsDecrypted;
    }

    
    public OrdenEncryptedDTO encryptOrden(Orden orden) throws HttpException {
        try {

            EncrtptAES<NombreDTO> AESNombre = new EncrtptAES<NombreDTO>();
            OrdenEncryptedDTO ordenEncriptadoDTO    = new OrdenEncryptedDTO();   
            ordenEncriptadoDTO.setNombre(AESNombre.encrypt(orden.getPaciente().getNombre(),ordenEncriptadoDTO.getNombre()));    

            EncrtptAES<ApellidoDTO> AESApellido = new EncrtptAES<ApellidoDTO>(); 
            ordenEncriptadoDTO.setApellido(AESApellido.encrypt(orden.getPaciente().getApellido(),ordenEncriptadoDTO.getApellido())); 

            EncrtptAES<DniDTO> AESDNI = new EncrtptAES<DniDTO>(); 
            ordenEncriptadoDTO.setDni(AESDNI.encrypt(Long.toString(orden.getPaciente().getDni()),ordenEncriptadoDTO.getDni())); 

            EncrtptAES<EstudiosDTO> AESestudios = new EncrtptAES<EstudiosDTO>(); 
            ordenEncriptadoDTO.setEstudios(AESestudios.encrypt(EstudiosDTO.estudiosListToString(orden.getEstudios()),ordenEncriptadoDTO.getEstudios())); 

            EncrtptAES<ObservacionesDTO> AESObservacion = new EncrtptAES<ObservacionesDTO>(); 
            ordenEncriptadoDTO.setObservaciones(AESObservacion.encrypt(orden.getObservaciones(),ordenEncriptadoDTO.getObservaciones())); 

            EncrtptAES<IdOrdenDTO> AESIDOrden = new EncrtptAES<IdOrdenDTO>(); 
            ordenEncriptadoDTO.setIdOrden(AESIDOrden.encrypt(Integer.toString(orden.getId()),ordenEncriptadoDTO.getIdOrden())); 

            EncrtptAES<FlagDownloadDTO> AESFlagDownload = new EncrtptAES<FlagDownloadDTO>(); 
            ordenEncriptadoDTO.setFlag_Download(AESFlagDownload.encrypt(Integer.toString(orden.getFlag_download()),ordenEncriptadoDTO.getFlag_Download()));

            return ordenEncriptadoDTO;
            }catch (Exception ex) {
                throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_ENCRIPTACION_EXTERNA));
            } 
    }

    public ArrayList<OrdenEncryptedDTO> encryptOrdenes(ArrayList<Orden> ordenes) throws HttpException {

        try {
            ArrayList<OrdenEncryptedDTO> ordenesEncriptadas = new ArrayList<>();
                for (Orden orden : ordenes)
                    {   
                        OrdenEncryptedDTO ordenEncriptada = new OrdenEncryptedDTO();
                        ordenEncriptada = this.encryptOrden(orden);
                        ordenesEncriptadas.add(ordenEncriptada);
                    }
            return ordenesEncriptadas;  
        }catch(Exception ex) {
                    throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_ENCRIPTACION_EXTERNA));
                } 
    }
}



