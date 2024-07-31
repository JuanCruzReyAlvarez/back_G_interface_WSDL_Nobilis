package nobilis.services.ordenes.carga;

import javax.xml.soap.SOAPException;

import lombok.Getter;
import nobilis.services.ServiceNobilis;


public class CargaOrdenServiceNobilis extends ServiceNobilis{

    public static final String serviceUrl = "http://172.20.30.11:8080/nobilis/servlet/wsdlservices/ordenes?wsdl";
    @Getter public SoapOrdenServiceNobilis soapOrdenServiceNobilis;

    public CargaOrdenServiceNobilis() throws SOAPException {
        super(serviceUrl);
        this.soapOrdenServiceNobilis = new SoapOrdenServiceNobilis();
    }
}
