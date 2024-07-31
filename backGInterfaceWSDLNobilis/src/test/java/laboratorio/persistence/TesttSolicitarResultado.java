package laboratorio.persistence;
/* 
//import org.apache.hadoop.shaded.com.squareup.okhttp.ResponseBody;
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
import java.io.StringReader;

import org.junit.Test;
import javax.xml.soap.*;
import javax.xml.namespace.QName;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class TesttSolicitarResultado {
    @Test
    public void pruebaNobilis() throws SOAPException, IOException, SAXException, ParserConfigurationException{   
        // URL del servicio web SOAP
        String serviceUrl = "http://172.20.30.11:8080/nobilis/servlet/wsdlservices/resultados?wsdl";
        
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
         QName cargarResultadoQName = new QName(null,"obtenerResultadosPorOrden","wsdl");
         SOAPElement cargarResultadoElement = soapBody.addChildElement(cargarResultadoQName);

         // Agregar el elemento pacientesWsdl al cargarPaciente
         QName resultadoQName = new QName("resultadoWsdl");
         SOAPElement resultadoElement = cargarResultadoElement.addChildElement(resultadoQName);

         // Agregar los elementos del paciente al mensaje SOAP
        // pacientesElement.addChildElement("id").setValue("");  // Agregar el valor correspondiente

        resultadoElement.addChildElement("idExterno").setValue("57");

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



                                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document document = builder.parse(new InputSource(new StringReader(responseBody)));

                            // Obtener los nodos de resultados
                            NodeList resultadosList = document.getElementsByTagName("resultados");


                            String idExterno = document.getElementsByTagName("idExterno").item(0).getTextContent();
                            String nroOrden = document.getElementsByTagName("nroOrden").item(0).getTextContent();
                            String estado = document.getElementsByTagName("estado").item(0).getTextContent();
                        
                            // Imprimir los valores de idExterno, nroOrden y estado
                            System.out.println("idExterno: " + idExterno);
                            System.out.println("nroOrden: " + nroOrden);
                            System.out.println("estado: " + estado);


                            for (int i = 0; i < resultadosList.getLength(); i++) {
                                Element resultadosElement = (Element) resultadosList.item(i);

                                // Obtener los valores de cada campo dentro de resultados
                                String descripcion = resultadosElement.getElementsByTagName("descripcion").item(0).getTextContent();
                                String resultado = resultadosElement.getElementsByTagName("resultado").item(0).getTextContent();
                                String rangoNormal = resultadosElement.getElementsByTagName("rangoNormal").item(0).getTextContent();

                                // Hacer lo que necesites con estos valores, por ejemplo:
                                
                                System.out.println("Descripción: " + descripcion);
                                System.out.println("Resultado: " + resultado);
                                System.out.println("Rango Normal: " + rangoNormal);
                                System.out.println("------------------------");
                            }
        
        } else {
            // Manejar errores, por ejemplo, lanzar una excepción
            throw new IOException("Error en la solicitud HTTP: " + response.getStatusLine().getReasonPhrase());
        }

        
    }
    
}
*/