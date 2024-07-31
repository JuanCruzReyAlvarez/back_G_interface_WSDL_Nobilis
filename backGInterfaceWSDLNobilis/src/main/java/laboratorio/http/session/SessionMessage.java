package laboratorio.http.session;

import lombok.Getter;
import lombok.Setter;

public class SessionMessage {

    @Getter @Setter private String message;
    @Getter private String token;

    public SessionMessage(String message) {
        this.message = message; 
    }
    public SessionMessage(String messageOne, String messageTwo) {
        this.message = messageOne + messageTwo; 

    }

}
