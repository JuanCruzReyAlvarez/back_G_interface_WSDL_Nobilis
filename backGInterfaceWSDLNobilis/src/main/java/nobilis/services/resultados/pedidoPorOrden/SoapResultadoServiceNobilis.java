package nobilis.services.resultados.pedidoPorOrden;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import nobilis.services.SoapServiceNobilis;

import javax.xml.namespace.QName;

public class SoapResultadoServiceNobilis extends SoapServiceNobilis{
    
    public SOAPElement resultadoElement;

    public SoapResultadoServiceNobilis() throws SOAPException{
        
        super();
        QName cargarResultadoQName = new QName(null,"obtenerResultadosPorOrden","wsdl");
        SOAPElement resultadoElement = soapBody.addChildElement(cargarResultadoQName);
    
        this.resultadoElement = resultadoElement;
        
    }

    public SOAPElement addChildElementsToXML(String idOrden) throws SOAPException{                                                

        resultadoElement.addChildElement("idExterno").setValue(idOrden);

        return resultadoElement;
    }
}
