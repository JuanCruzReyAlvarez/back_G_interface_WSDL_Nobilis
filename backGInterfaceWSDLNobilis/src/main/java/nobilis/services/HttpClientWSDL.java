package nobilis.services;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import lombok.Getter;
import lombok.Setter;

public class HttpClientWSDL {

     @Getter @Setter public  HttpClient httpClient;
     @Getter @Setter public HttpPost httpPost;

     public HttpClientWSDL( String serviceUrl ){
     // Crear una solicitud HTTP POST
     HttpPost httpPostToSet = new HttpPost(serviceUrl);

     // Configurar el encabezado de la solicitud HTTP
     httpPostToSet.setHeader("Content-Type", "text/xml; charset=utf-8");

     this.httpPost = httpPostToSet;
     // Crear el cliente HTTP
     this.httpClient = HttpClients.createDefault();

     }

     public Object[] execute(SOAPMessage soapMessage) throws ClientProtocolException, IOException, SOAPException{
          // Convertir el mensaje SOAP en bytes
          ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
          soapMessage.writeTo(byteArrayOutputStream);
          byte[] soapBytes = byteArrayOutputStream.toByteArray();
          // Configurar el cuerpo de la solicitud HTTP con el mensaje SOAP
          HttpEntity entity = new ByteArrayEntity(soapBytes);
          httpPost.setEntity(entity);
          // Realizar la solicitud HTTP POST
          
          try{
          HttpResponse response = httpClient.execute(httpPost);
          Integer code = response.getStatusLine().getStatusCode();
          HttpEntity responseEntity = response.getEntity();
          String responseBody = EntityUtils.toString(responseEntity, "UTF-8"); // Obtener el cuerpo de la respuesta como cadena de texto
          Object[] responseArrive = new Object[] {code,responseBody};     
          return responseArrive;
          }catch(IOException io){
               throw new IOException(io); 
          }
     }
}

  

