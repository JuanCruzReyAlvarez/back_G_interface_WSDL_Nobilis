package nobilis.services;

import javax.xml.soap.*;

import lombok.Getter;

public abstract class SoapServiceNobilis {

    public SOAPBody soapBody;
    @Getter public SOAPMessage soapMessage;

    public SoapServiceNobilis() throws SOAPException{
           // Crear un mensaje SOAP
           MessageFactory messageFactory = MessageFactory.newInstance();
           SOAPMessage soapMessage = messageFactory.createMessage();
  
           // Crear el cuerpo SOAP
           SOAPPart soapPart = soapMessage.getSOAPPart();
           SOAPEnvelope envelope = soapPart.getEnvelope();
           envelope.addNamespaceDeclaration("wsdl", "http://wsdl.servicios.jalisco.com/");
           SOAPBody soapBody = envelope.getBody();
           this.soapBody = soapBody;
           this.soapMessage = soapMessage;
    }
}
