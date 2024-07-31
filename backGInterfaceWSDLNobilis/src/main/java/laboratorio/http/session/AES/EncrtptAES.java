package laboratorio.http.session.AES;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import laboratorio.http.exceptions.BadResquestException;
import laboratorio.http.exceptions.HttpException;
import laboratorio.http.exceptions.MessageException;

import java.util.Base64;
import java.util.Collections;
import java.security.SecureRandom;

public class EncrtptAES<T extends Encryptable> extends AES {

    public EncrtptAES(){}
    
    public T encrypt(String message ,T obj) throws HttpException {

        try {
            SecretKey key = new SecretKeySpec(SECRET_KEY_AES.getBytes(StandardCharsets.UTF_8), "AES");
            byte[] ivBytes = Base64.getDecoder().decode(this.generateRandomIV());
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); // Cambiar al modo GCM
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, ivBytes);
            cipher.init(Cipher.ENCRYPT_MODE, key, gcmParameterSpec);
            byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
            obj.setEncryptedData(Base64.getEncoder().encodeToString(encryptedBytes));
            obj.setIv(Base64.getEncoder().encodeToString(ivBytes));
            return obj;
        } catch (Exception ex) {
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_ENCRIPTACION_EXTERNA));
        }
    }

    private String generateRandomIV() {
        try {
            SecureRandom secureRandom = new SecureRandom();
            byte[] ivBytes = new byte[12]; // Tamaño del IV para AES-GCM es de 12 bytes
            secureRandom.nextBytes(ivBytes);
            return Base64.getEncoder().encodeToString(ivBytes);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


}
