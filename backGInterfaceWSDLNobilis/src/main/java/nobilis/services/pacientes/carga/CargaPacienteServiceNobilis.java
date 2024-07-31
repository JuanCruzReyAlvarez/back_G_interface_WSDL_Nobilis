package nobilis.services.pacientes.carga;

import javax.xml.soap.SOAPException;

import lombok.Getter;
import nobilis.services.ServiceNobilis;

public class CargaPacienteServiceNobilis extends ServiceNobilis{

    public static final String serviceUrl = "http://172.20.30.11:8080/nobilis/servlet/wsdlservices/pacientes?wsdl";
    @Getter public SoapPacienteServiceNobilis soapPacienteServiceNobilis;

    public CargaPacienteServiceNobilis() throws SOAPException {
        super(serviceUrl);
        this.soapPacienteServiceNobilis = new SoapPacienteServiceNobilis();
    }
}
