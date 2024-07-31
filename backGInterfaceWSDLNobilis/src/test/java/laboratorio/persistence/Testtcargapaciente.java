package laboratorio.persistence;
/* 

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.xml.soap.SOAPMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


import org.junit.Test;
import javax.xml.soap.*;
import javax.xml.namespace.QName;

public class Testtcargapaciente {
    @Test
    public void pruebaNobilis() throws SOAPException, IOException{   
        // URL del servicio web SOAP
        String serviceUrl = "http://172.20.30.11:8080/nobilis/servlet/wsdlservices/pacientes?wsdl";

        // Crear el cliente HTTP
        HttpClient httpClient = HttpClients.createDefault();

        // Crear una solicitud HTTP POST
        HttpPost httpPost = new HttpPost(serviceUrl);

        // Configurar el encabezado de la solicitud HTTP
        httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");

         // Crear un mensaje SOAP
         MessageFactory messageFactory = MessageFactory.newInstance();
         SOAPMessage soapMessage = messageFactory.createMessage();

         // Crear el cuerpo SOAP
         SOAPPart soapPart = soapMessage.getSOAPPart();
         SOAPEnvelope envelope = soapPart.getEnvelope();
         envelope.addNamespaceDeclaration("wsdl", "http://wsdl.servicios.jalisco.com/");

         SOAPBody soapBody = envelope.getBody();
         // Crear el elemento cargarPaciente
         QName cargarPacienteQName = new QName(null,"cargarPaciente","wsdl");
         SOAPElement cargarPacienteElement = soapBody.addChildElement(cargarPacienteQName);

         // Agregar el elemento pacientesWsdl al cargarPaciente
         QName pacientesQName = new QName("pacientesWsdl");
         SOAPElement pacientesElement = cargarPacienteElement.addChildElement(pacientesQName);

         // Agregar los elementos del paciente al mensaje SOAP
        // pacientesElement.addChildElement("id").setValue("");  // Agregar el valor correspondiente

         pacientesElement.addChildElement("idTipoDocumento").setValue("DNI");
         pacientesElement.addChildElement("nroDoc").setValue("19722792345678");
         pacientesElement.addChildElement("fechaNacimiento").setValue("25/07/1995");  // Asegúrate de que el formato sea el correcto
        
        
         //pacientesElement.addChildElement("cuit").setValue("1234");  // Agregar el valor correspondiente
         pacientesElement.addChildElement("sexo").setValue("M");
         pacientesElement.addChildElement("nroHistoriaClinica").setValue("5");
         pacientesElement.addChildElement("nombre").setValue("juan_prueba_wsdl_nobilis");
        
         pacientesElement.addChildElement("apellido").setValue("cruz_prueba_wsdl_nobilis");
         pacientesElement.addChildElement("institucionesDesc").setValue("LABORATORIO CORDOBA");
         pacientesElement.addChildElement("planDesc").setValue("PLAN LABORATORIO CORDOBA");

         // Imprimir el mensaje SOAP
         soapMessage.writeTo(System.out);

        // Convertir el mensaje SOAP en bytes
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        soapMessage.writeTo(byteArrayOutputStream);
        byte[] soapBytes = byteArrayOutputStream.toByteArray();

        // Configurar el cuerpo de la solicitud HTTP con el mensaje SOAP
        HttpEntity entity = new ByteArrayEntity(soapBytes);
        httpPost.setEntity(entity);

                // Realizar la solicitud HTTP POST
                HttpResponse response = httpClient.execute(httpPost);

         // Procesar la respuesta
         if (response.getStatusLine().getStatusCode() == 200) {
            // La solicitud se realizó correctamente
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity, "UTF-8"); // Obtener el cuerpo de la respuesta como cadena de texto
            System.out.println("Respuesta del servicio web:");
            System.out.println(responseBody);
        } else {
            // Manejar errores, por ejemplo, lanzar una excepción
            throw new IOException("Error en la solicitud HTTP: " + response.getStatusLine().getReasonPhrase());
        }
    }
    
}

*/