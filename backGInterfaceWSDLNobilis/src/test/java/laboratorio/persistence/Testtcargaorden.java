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

import nobilis.data.ordenes.Autorizada;
import nobilis.data.ordenes.CentroDesc;
import nobilis.data.ordenes.OrdenPendiente;
import nobilis.data.pacientes.PlanDesc;

import javax.xml.soap.*;
import javax.xml.namespace.QName;

public class Testtcargaorden {
    @Test
    public void pruebaNobilis() throws SOAPException, IOException{   
        // URL del servicio web SOAP
        String serviceUrl = "http://172.20.30.11:8080/nobilis/servlet/wsdlservices/ordenes?wsdl";

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
         QName cargarOrdenQName = new QName(null,"cargarOrden","wsdl");
         SOAPElement cargarPacienteElement = soapBody.addChildElement(cargarOrdenQName);

         // Agregar el elemento pacientesWsdl al cargarPaciente
         QName ordenesQName = new QName("ordenesWsdl");
         SOAPElement ordenesElement = cargarPacienteElement.addChildElement(ordenesQName);

         // Agregar los elementos del paciente al mensaje SOAP
        // pacientesElement.addChildElement("id").setValue("");  // Agregar el valor correspondiente

        ordenesElement.addChildElement("centroDesc").setValue(CentroDesc.CENTRO_DESC);
        ordenesElement.addChildElement("urgente").setValue(Boolean.TRUE.toString());
        ordenesElement.addChildElement("autorizada").setValue(Boolean.toString(Autorizada.AUTORIZADA));
        ordenesElement.addChildElement("idExterno").setValue("999999999999999999999999999999999999999999999999999999999999999999999");
        ordenesElement.addChildElement("ordenPendiente").setValue(Boolean.toString(OrdenPendiente.ORDEN_PENDIENTE));
        ordenesElement.addChildElement("institucionDesc").setValue("Hay que agregar una INSTITUCIONDESC que ya este dada de alta en Nobilis"); //Hay que agregar una INSTITUCIONDESC que ya este dada de alta en Nobilis
        ordenesElement.addChildElement("planDesc").setValue(PlanDesc.PLAN_DESC);
        ordenesElement.addChildElement("estudios").setValue("475");
        ordenesElement.addChildElement("estudios").setValue("711");
        ordenesElement.addChildElement("servicioDesc").setValue("no service");
        ordenesElement.addChildElement("observacion").setValue("Probando Servicio WSDL");
        ordenesElement.addChildElement("origenPacienteDesc").setValue("laboratorio"); 
        ordenesElement.addChildElement("nroHistClinica").setValue("1"); 

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
         System.out.println(response.getStatusLine().getStatusCode());
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
