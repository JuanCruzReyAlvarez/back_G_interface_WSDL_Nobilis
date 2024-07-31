package laboratorio.http.session;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import laboratorio.entities.auth.Rol;
import laboratorio.entities.auth.Usuario;
import laboratorio.http.exceptions.BadResquestException;
import laboratorio.http.exceptions.HttpException;
import laboratorio.http.exceptions.MessageException;
import laboratorio.repository.auth.UsuarioRepository;
import laboratorio.repository.origen.OrigenRepository;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Collections;

public class TokenService {
    
    private static final String SECRET_KEY_JWT =  "Z2ZkNDU2Ly80KzU0OStiZmRidmZkVkdGRFNGRFY="; 
    private OrigenRepository origenRepository;
    private UsuarioRepository usuarioRepository;

    public String generateJWTencrypted(String username, String rol) throws HttpException {   
       try {
        long expirationTime = 43200000; // (12 horas)
        byte[] secretKeyBytes = Base64.getDecoder().decode(SECRET_KEY_JWT);
        SecretKey key = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName());
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", rol);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    } catch (Exception ex) {
        throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_ENCRIPTACION_INTERNA));
    }
}
    
    public String[] getUsernameAndRolFromJWT(String token) throws HttpException{
        try {
                byte[] secretKeyBytes = Base64.getDecoder().decode(SECRET_KEY_JWT);
                SecretKey key = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName());
                Jws<Claims> jws = Jwts.parser()
                                       .setSigningKey(key)
                                       .parseClaimsJws(token);
            Claims claims = jws.getBody();
            String username = claims.getSubject();
            String rol = (String) claims.get("rol");
            return new String[]{username, rol};
            } catch (Exception ex) { 
                throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.ERROR_DESENCRIPCTACION_INTERNA));
            }
        }

    public boolean isValidTokenEncrypted(String token) throws HttpException {
        try {
            byte[] secretKeyBytes = Base64.getDecoder().decode(SECRET_KEY_JWT);
            SecretKey key = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName());
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.TOKEN_EXPIRADO));
        } catch (Exception ex) {
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.TOKEN_INVALIDO));
        }
    }

    public void validateTokenAndRolAdmin(String token) throws HttpException{
        this.isValidTokenEncrypted(token);
       
        if((this.getUsernameAndRolFromJWT(token))[1].compareTo(Rol.ADMIN.name().toString()) != 0)
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.NO_ACCESS));
    }

    public boolean isValidEstado(String token)throws HttpException{
        try{
            if(this.origenRepository.getOrigenByUsuario(this.usuarioRepository.getUsuarioByUsername(this.getUsernameAndRolFromJWT(token)[0])).getEstado().equals(1))
                return true;
            else throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.NO_ACCESS));
        }catch(Exception ex){
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.NO_ACCESS));
        }
    }

    public boolean isValidEstadoAtLogin(String usuario)throws HttpException{
        try{
            Usuario user = this.usuarioRepository.getUsuarioByUsername(usuario);
            if(user.getRol() == Rol.ADMIN){return true;}
            if(this.origenRepository.getOrigenByUsuario(user).getEstado().equals(1))
                return true;
            else throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.NO_ACCESS_USERNAME));
        }catch(Exception ex){
            throw new BadResquestException(Collections.singletonMap(MessageException.FIELD_NAME, MessageException.NO_ACCESS_USERNAME));
        }
    }

    public void validateTokenAndEstado(String token) throws HttpException{
        this.isValidTokenEncrypted(token);
        this.isValidEstado(token);
    }

    public TokenService(OrigenRepository origenRepository, UsuarioRepository usuarioRepository){
        this.origenRepository = origenRepository;
        this.usuarioRepository = usuarioRepository;
    }

}
