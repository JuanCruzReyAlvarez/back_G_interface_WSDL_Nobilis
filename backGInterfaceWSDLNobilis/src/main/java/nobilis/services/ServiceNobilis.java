package nobilis.services;

import javax.xml.soap.SOAPException;

public abstract class ServiceNobilis {
    
    public HttpClientWSDL httpClientWSDL;

    public ServiceNobilis(String serviceUrl) throws SOAPException{
        this.httpClientWSDL = new HttpClientWSDL(serviceUrl);
    }
}
