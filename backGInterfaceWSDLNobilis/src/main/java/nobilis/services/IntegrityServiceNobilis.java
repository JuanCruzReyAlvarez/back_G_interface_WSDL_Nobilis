package nobilis.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPException;

import laboratorio.entities.derivacion.Derivacion;
import laboratorio.entities.orden.Orden;
import laboratorio.entities.paciente.Paciente;
import laboratorio.http.exceptions.BadResquestException;
import laboratorio.http.exceptions.HttpException;
import laboratorio.http.exceptions.MessageException;
import laboratorio.services.derivacion.DerivacionService;
import laboratorio.services.orden.OrdenService;

import nobilis.services.ordenes.carga.CargaOrdenServiceNobilis;
import nobilis.services.pacientes.carga.CargaPacienteServiceNobilis;
import org.apache.http.client.ClientProtocolException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IntegrityServiceNobilis {

    OrdenService ordenservice;
    DerivacionService derivacionService;

    public void controlAndPushOrdenIndividual(Derivacion derivacion, String username) throws HttpException, SOAPException, ClientProtocolException, IOException, InterruptedException, ExecutionException, SAXException, ParseException{
        
            
        ArrayList <Orden> ordenesToPersist = this.ordenservice.getOrdenesFromDerivacion(username, derivacion);
        Paciente pacienteToPersist = this.ordenservice.getPacientesNoCargadosFromOrden(ordenesToPersist).get(0);
        CargaPacienteServiceNobilis cargaPacienteServiceNobilis = new CargaPacienteServiceNobilis();

        // SimpleDateFormat formatoFechaEntrada = new SimpleDateFormat("yyyy-MM-dd");
        // SimpleDateFormat formatoFechaSalida = new SimpleDateFormat("dd/MM/yyyy");
        // Date fecha = formatoFechaEntrada.parse(pacienteToPersist.getFechaNacimiento());
        // String fechaDeNacimientoFormateada = formatoFechaSalida.format(fecha);
 
        cargaPacienteServiceNobilis.soapPacienteServiceNobilis.addChildElementsToXML(pacienteToPersist.getDni().toString(), pacienteToPersist.getFechaNacimiento(), pacienteToPersist.getGenero().toString(),
                                                                                    pacienteToPersist.getId().toString(), pacienteToPersist.getNombre(), pacienteToPersist.getApellido(),derivacion.getOrigen().getRazonSocial());  
                                                                                            
        ExecutorService executorPaciente = Executors.newSingleThreadExecutor();
        Future<Object[]> future_pacientes = executorPaciente.submit(() -> {
            cargaPacienteServiceNobilis.getSoapPacienteServiceNobilis().getSoapMessage().writeTo(System.out);
            return cargaPacienteServiceNobilis.httpClientWSDL.execute(cargaPacienteServiceNobilis.getSoapPacienteServiceNobilis().getSoapMessage());
        }); 

        try{ 
            
                Object[] responsePacienteService = future_pacientes.get(5, TimeUnit.SECONDS);                                                                                                                                                                                                                        
                Integer responseCodePaciente = (Integer) responsePacienteService[0]; // mensaje seria: (String)response[1]   
                System.out.println((String) responsePacienteService[1]);
                System.out.println((Integer) responsePacienteService[0]);
                Integer nobilisInternalCodeResponsePaciente = this.getNobilisInternalCode((String) responsePacienteService[1]);
                if(responseCodePaciente != 200 || nobilisInternalCodeResponsePaciente != 202){
                        this.derivacionService.deleteDerivacion(this.derivacionService.getDerivacionByID(derivacion.getId())); //Se borra todo salvo los estudios que son reutilizados mas adelante y ademas ya habian sido asignados a otras ordenes. Y los pacientes tampoco se borran obviamente.
                        throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.FALLO_CARGA_PACIENTE_INDIVIDUAL_NOBILIS + responsePacienteService[1]));
                }else{
                     this.ordenservice.pacienteRepository.updatePacienteNobilisPersisted(pacienteToPersist); 
                     ExecutorService executorOrden = Executors.newSingleThreadExecutor();
                     Orden ordenPersisted = this.ordenservice.getOrdenesFromDerivacion(username, derivacion).get(0);
                     CargaOrdenServiceNobilis cargaOrdenServiceNobilis = new CargaOrdenServiceNobilis();
                     cargaOrdenServiceNobilis.soapOrdenServiceNobilis.addChildElementsToXML(ordenPersisted.getUrgencia(), ordenPersisted.getId().toString(), ordenPersisted.getPaciente().getId().toString(),
                                                                                           ordenPersisted.getEstudios(),ordenPersisted.getObservaciones(),derivacion.getOrigen().getRazonSocial());
                                                                                           
                    cargaOrdenServiceNobilis.getSoapOrdenServiceNobilis().getSoapMessage().writeTo(System.out);
                    Future<Object[]> future_ordenes = executorOrden.submit(() -> {
                    return cargaOrdenServiceNobilis.httpClientWSDL.execute(cargaOrdenServiceNobilis.getSoapOrdenServiceNobilis().getSoapMessage());
                    });  
                    Object[] responseOrden = future_ordenes.get(5, TimeUnit.SECONDS);
                    Integer responseCodeOrden = (Integer) responseOrden[0]; // mensaje seria: (String)response[1]
                    
                    System.out.println((Integer)responseOrden[0]);
                    System.out.println((String) responseOrden[1]);

                    Integer nobilisInternalCodeResponseOrden = this.getNobilisInternalCode((String) responseOrden[1]);
                     
                    if(responseCodeOrden != 200 || nobilisInternalCodeResponseOrden != 202){
                            this.derivacionService.deleteDerivacion(this.derivacionService.getDerivacionByID(derivacion.getId())); //Se borra todo salvo los estudios que son reutilizados mas adelante y ademas ya habian sido asignados a otras ordenes. Y los pacientes tampoco se borran obviamente.
                            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.FALLO_CARGA_ORDEN_INDIVIDUAL_NOBILIS + responseOrden[1]));
                        }
                    
                }     
    }catch (TimeoutException e) {
        this.derivacionService.deleteDerivacion(this.derivacionService.getDerivacionByID(derivacion.getId()));
        throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.TIME_OUT_NOBILIS));
    }
}

public void controlAndPushOrdenExcel(Derivacion derivacion, String username) throws HttpException, SOAPException, ClientProtocolException, IOException, InterruptedException, ExecutionException, SAXException{

    try{
            ArrayList <Orden> ordenesPersisted = this.ordenservice.getOrdenesFromDerivacion(username, derivacion);
            ArrayList <Paciente> pacientesPersisted = this.ordenservice.getPacientesNoCargadosFromOrden(ordenesPersisted);
            if(pacientesPersisted.size() != 0){
                for(Paciente pacienteToPersist : pacientesPersisted){
                    CargaPacienteServiceNobilis cargaPacienteServiceNobilis = new CargaPacienteServiceNobilis();
                    cargaPacienteServiceNobilis.soapPacienteServiceNobilis.addChildElementsToXML(pacienteToPersist.getDni().toString(), pacienteToPersist.getFechaNacimiento(), 
                                                                                                pacienteToPersist.getGenero().toString(),pacienteToPersist.getId().toString(),
                                                                                                pacienteToPersist.getNombre(), pacienteToPersist.getApellido(),derivacion.getOrigen().getRazonSocial());
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    Future<Object[]> future_pacientes = executor.submit(() -> {
                        return cargaPacienteServiceNobilis.httpClientWSDL.execute(cargaPacienteServiceNobilis.getSoapPacienteServiceNobilis().getSoapMessage());
                    });
                    Object[] responsePaciente = future_pacientes.get(5, TimeUnit.SECONDS);
                    Integer responseCodePaciente = (Integer) responsePaciente[0]; // mensaje seria: (String)response[1]   
                    Integer nobilisInternalCode = this.getNobilisInternalCode((String) responsePaciente[1]);
                    System.out.println((Integer)responsePaciente[0]);
                    System.out.println((String) responsePaciente[1]);
                    if(responseCodePaciente != 200 || nobilisInternalCode!= 202){
                            this.derivacionService.deleteDerivacion(this.derivacionService.getDerivacionByID(derivacion.getId()));
                            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.FALLO_CARGA_PACIENTE_INDIVIDUAL_NOBILIS + responsePaciente[1]));
                    }
                }

                this.ordenservice.pacienteRepository.updatePacientesNobilisPersisted(pacientesPersisted);}

                System.out.println("1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"); 

                for(Orden orden : ordenesPersisted){
                    CargaOrdenServiceNobilis cargaOrdenServiceNobilis = new CargaOrdenServiceNobilis();
                    cargaOrdenServiceNobilis.soapOrdenServiceNobilis.addChildElementsToXML(orden.getUrgencia(), orden.getId().toString(), orden.getPaciente().getId().toString(),
                                                                                           orden.getEstudios(),orden.getObservaciones(),derivacion.getOrigen().getRazonSocial());
                                                                                           System.out.println("22222222222222222222222222222222222222222222222222222222222222222222222222222"); 
                    ExecutorService executorOrden = Executors.newSingleThreadExecutor();
                    System.out.println("3333333333333333333333333333333333333333333333333333333333333333"); 
                    Future<Object[]> future_ordenes = executorOrden.submit(() -> {
                        return cargaOrdenServiceNobilis.httpClientWSDL.execute(cargaOrdenServiceNobilis.getSoapOrdenServiceNobilis().getSoapMessage());
                    });
                    System.out.println("444444444444444444444444444444444444444444444444444444444444444444444444444444444444444"); 
                    // cargaOrdenServiceNobilis.getSoapOrdenServiceNobilis().getSoapMessage().writeTo(System.out); // Da informacion pero se subis muchas ordenes tarda tanto en cargar la data q rompe aca.
                    System.out.println("55555555555555555555555555555555555555555555555555555555555555555555555555555555555555"); 
                    Object[] responseOrden = future_ordenes.get(5, TimeUnit.SECONDS);
                    System.out.println("66666666666666666666666666666666666666666666666666666666666666666666666666666666666666666"); 
                    System.out.println((Integer)responseOrden[0]);
                    System.out.println("7777777777777777777777777777777777777777777777777777777777777777777777777777777777777"); 
                    System.out.println((String) responseOrden[1]);
                    System.out.println("88888888888888888888888888888888888888888888888888888888888888888888888888888888888"); 

                    Integer nobilisInternalCodeResponseOrden = this.getNobilisInternalCode((String) responseOrden[1]);

                    System.out.println("999999999999999999999999999999999999999999999999999999999999999999999999999"); 

                    if((Integer)responseOrden[0] != 200 || nobilisInternalCodeResponseOrden != 202){
                        this.derivacionService.deleteDerivacion(this.derivacionService.getDerivacionByID(derivacion.getId()));
                        throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.FALLO_CARGA_ORDEN_INDIVIDUAL_NOBILIS + responseOrden[1]));
                    }
                }

            }catch (TimeoutException e) {
                this.derivacionService.deleteDerivacion(this.derivacionService.getDerivacionByID(derivacion.getId()));
                throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.TIME_OUT_NOBILIS));
        }
    }

    public Integer getNobilisInternalCode(String xml) throws SAXException, IOException, BadResquestException{
        try{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));
        String codigo = document.getElementsByTagName("codigo").item(0).getTextContent();
        Integer codigonum = Integer.parseInt(codigo);

        return codigonum;
        }catch(Exception e){
        throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_CODIGO_DE_CARGA_DE_PACIENTE_INCORRECTO));
        }
    }


    public IntegrityServiceNobilis (OrdenService ordenservice, DerivacionService derivacionService){
        this.ordenservice = ordenservice;
        this.derivacionService = derivacionService;
    }

}

