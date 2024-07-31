package nobilis.services.ordenes.carga;

import javax.xml.soap.*;

import laboratorio.entities.estudios.Estudio;
import nobilis.data.ordenes.Autorizada;
import nobilis.data.ordenes.CentroDesc;
import nobilis.data.ordenes.OrdenPendiente;
import nobilis.data.ordenes.OrigenPacienteDesc;
import nobilis.data.ordenes.ServicioDesc;
import nobilis.data.pacientes.PlanDesc;
import nobilis.services.SoapServiceNobilis;

import java.util.List;

import javax.xml.namespace.QName;
public class SoapOrdenServiceNobilis extends SoapServiceNobilis{
    
    public SOAPElement ordenElement;

    public SoapOrdenServiceNobilis() throws SOAPException{
        
        super();
         // Crear el elemento cargarPaciente
         QName cargarOrdenQName = new QName(null,"cargarOrden","wsdl");
         SOAPElement cargarPacienteElement = soapBody.addChildElement(cargarOrdenQName);

         // Agregar el elemento pacientesWsdl al cargarPaciente
         QName ordenQName = new QName("ordenesWsdl");
         SOAPElement ordenElement = cargarPacienteElement.addChildElement(ordenQName);

        this.ordenElement = ordenElement;
        
    }

    public SOAPElement addChildElementsToXML(Boolean urgente, String ordenID, String nroHistoriaClinica, 
                                             List<Estudio> estudios, String observacion, String institucionesDesc) throws SOAPException{                                                
        ordenElement.addChildElement("centroDesc").setValue(CentroDesc.CENTRO_DESC);
        ordenElement.addChildElement("urgente").setValue(urgente.toString());
        ordenElement.addChildElement("autorizada").setValue(Boolean.toString(Autorizada.AUTORIZADA));
        ordenElement.addChildElement("idExterno").setValue(ordenID);
        ordenElement.addChildElement("ordenPendiente").setValue(Boolean.toString(OrdenPendiente.ORDEN_PENDIENTE));
        ordenElement.addChildElement("institucionDesc").setValue(institucionesDesc);
        ordenElement.addChildElement("planDesc").setValue(PlanDesc.PLAN_DESC);
        ordenElement.addChildElement("servicioDesc").setValue(ServicioDesc.SERVICIO_DESC);
        ordenElement.addChildElement("observacion").setValue(observacion);
        ordenElement.addChildElement("origenPacienteDesc").setValue(OrigenPacienteDesc.ORIGEN_PACIENTE_DESC); 
        ordenElement.addChildElement("nroHistClinica").setValue(nroHistoriaClinica); 

        for(Estudio estudio : estudios){
            ordenElement.addChildElement("estudios").setValue(estudio.getCodigo());
        }

        return ordenElement;
    }
}
