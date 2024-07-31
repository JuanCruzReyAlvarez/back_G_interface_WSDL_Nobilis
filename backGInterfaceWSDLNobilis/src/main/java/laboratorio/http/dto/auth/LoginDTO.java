package laboratorio.http.dto.auth;

import laboratorio.http.dto.auth.fields.PasswordDTO;
import laboratorio.http.dto.auth.fields.UsernameDTO;
import lombok.Getter;
import lombok.Setter;

public class LoginDTO {
    @Getter @Setter private UsernameDTO username;
    @Getter @Setter private PasswordDTO password;

    public LoginDTO(UsernameDTO username, PasswordDTO password) {
        this.username = username;
    }
}


