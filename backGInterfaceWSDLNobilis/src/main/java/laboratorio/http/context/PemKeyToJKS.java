package laboratorio.http.context;

import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


import java.security.PrivateKey;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;


public class PemKeyToJKS {



        public void convert(String certFilePath, String keyFilePath, String keystorePassword, String jksFilePath) throws Exception {
            
            char[] passwordChars = keystorePassword.toCharArray();

            // Lee el certificado PEM
            byte[] certBytes = Files.readAllBytes(Paths.get(certFilePath));

            // Crea un certificado X.509 desde el contenido PEM
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            
            X509Certificate cert = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(certBytes));
           
            // Leer todo el contenido del archivo
            byte[] keyBytes = Files.readAllBytes(Paths.get(keyFilePath));
            String pemData = new String(keyBytes);
            String privateKeyPEM = pemData.replace("-----BEGIN PRIVATE KEY-----", "")
                                            .replace("-----END PRIVATE KEY-----", "")
                                            .replaceAll("\\s", "");
            // Decodificar el contenido Base64
            byte[] decodedKeyBytes = Base64.getDecoder().decode(privateKeyPEM);


            // Crea la clave privada desde el contenido PEM
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKeyBytes);
            
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
 
             // Crea la clave privada desde el contenido PEM

            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);          
    
            // Crea el keystore y agrega el certificado y la clave privada
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(null, passwordChars);
            keystore.setCertificateEntry("alias", cert);
            keystore.setKeyEntry("alias", privateKey, passwordChars, new X509Certificate[]{cert});
    
            // Guarda el keystore en un archivo JKS
            try (FileOutputStream fos = new FileOutputStream(jksFilePath)) {
                keystore.store(fos, passwordChars);
            }
        }
}
