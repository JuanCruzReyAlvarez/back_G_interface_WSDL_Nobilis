package laboratorio.http.session.AES;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;

public class DecryptAES extends AES{

    public String decrypt(String data, String iv) {
        try {
            SecretKey key = new SecretKeySpec(SECRET_KEY_AES.getBytes(StandardCharsets.UTF_8), "AES");
            byte[] ivBytes = Base64.getDecoder().decode(iv);
            byte[] ciphertext = Base64.getDecoder().decode(data);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, ivBytes);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); // Cambiar al modo GCM
            cipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpec);
            byte[] tokenDesencriptadoBytes = cipher.doFinal(ciphertext);
            return new String(tokenDesencriptadoBytes, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
