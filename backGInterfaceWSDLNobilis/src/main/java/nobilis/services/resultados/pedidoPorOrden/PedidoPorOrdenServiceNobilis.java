package nobilis.services.resultados.pedidoPorOrden;

import nobilis.services.ServiceNobilis;

import javax.xml.soap.SOAPException;

import lombok.Getter;

public class PedidoPorOrdenServiceNobilis extends ServiceNobilis{

    public static final String serviceUrl = "http://172.20.30.11:8080/nobilis/servlet/wsdlservices/resultados?wsdl";
    @Getter public SoapResultadoServiceNobilis soapResultadoServiceNobilis;

    public PedidoPorOrdenServiceNobilis() throws SOAPException {
        super(serviceUrl);
        this.soapResultadoServiceNobilis = new SoapResultadoServiceNobilis();
    }
    
}
