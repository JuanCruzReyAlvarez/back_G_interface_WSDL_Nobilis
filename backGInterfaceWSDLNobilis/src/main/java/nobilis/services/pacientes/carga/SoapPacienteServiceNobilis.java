package nobilis.services.pacientes.carga;

import javax.xml.soap.*;

import nobilis.data.pacientes.IdTipoDocumento;
import nobilis.data.pacientes.PlanDesc;
import nobilis.services.SoapServiceNobilis;

import javax.xml.namespace.QName;

public class SoapPacienteServiceNobilis extends SoapServiceNobilis{
    
    public SOAPElement pacientesElement;

    public SoapPacienteServiceNobilis() throws SOAPException{
        super();
         // Crear el elemento cargarPaciente
         QName cargarPacienteQName = new QName(null,"cargarPaciente","wsdl");
         SOAPElement cargarPacienteElement = soapBody.addChildElement(cargarPacienteQName);

         // Agregar el elemento pacientesWsdl al cargarPaciente
         QName pacientesQName = new QName("pacientesWsdl");
         SOAPElement pacientesElement = cargarPacienteElement.addChildElement(pacientesQName);

        this.pacientesElement = pacientesElement;
    }

    public SOAPElement addChildElementsToXML(String nroDoc, String fechaNacimiento,String sexo, 
                                             String nroHistoriaClinica, String nombre, String apellido, String institucionesDesc) throws SOAPException {
                 pacientesElement.addChildElement("idTipoDocumento").setValue(IdTipoDocumento.DNI.toString());
                 pacientesElement.addChildElement("nroDoc").setValue(nroDoc);
                 pacientesElement.addChildElement("fechaNacimiento").setValue(fechaNacimiento);
                 pacientesElement.addChildElement("sexo").setValue(sexo);
                 pacientesElement.addChildElement("nroHistoriaClinica").setValue(nroHistoriaClinica);
                 pacientesElement.addChildElement("nombre").setValue(nombre);    
                 pacientesElement.addChildElement("apellido").setValue(apellido); 
                 pacientesElement.addChildElement("institucionesDesc").setValue(institucionesDesc);
                 pacientesElement.addChildElement("planDesc").setValue(PlanDesc.PLAN_DESC);
        
                 return pacientesElement;
    }
}
