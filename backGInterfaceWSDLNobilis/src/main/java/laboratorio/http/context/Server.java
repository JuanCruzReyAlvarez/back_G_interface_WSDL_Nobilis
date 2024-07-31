package laboratorio.http.context;

import laboratorio.http.controllers.IController;
import laboratorio.http.controllers.archivos.TemplatesController;
import spark.servlet.SparkApplication;

import laboratorio.http.controllers.auth.LoginController;
import laboratorio.http.controllers.auth.RegisterController;
import laboratorio.http.controllers.origen.OrigenController;
import laboratorio.http.controllers.orden.OrdenController;
import laboratorio.http.controllers.resultado.ResultadoController;
import laboratorio.http.session.TokenService;
import laboratorio.http.session.AES.DecryptAES;
import laboratorio.repository.auth.UsuarioRepository;
import laboratorio.repository.origen.OrigenRepository;
import laboratorio.services.auth.LoginService;
import laboratorio.services.auth.RegisterService;
import laboratorio.services.derivacion.DerivacionService;
import laboratorio.services.orden.OrdenService;
import laboratorio.services.origen.OrigenService;
import laboratorio.services.resultado.ResultadoService;
import laboratorio.services.templates.TemplatesService;
import nobilis.services.IntegrityServiceNobilis;


public class Server implements SparkApplication {

        private final int PORT = 8081;
        private final String IP = "0.0.0.0";


        //private final String SSL_KEY_FILE = "/home/portal/public_html/certs/ssl.key";
        //private final String SSL_CERT_FILE = "/home/portal/public_html/certs/ssl.cert";
        //private final String jksFilePath = "/home/portal/public_html/be/interfaceWebMindBeat/back_G_interface_WSDL_Nobilis/backGInterfaceWSDLNobilis/src/main/java/laboratorio/keystore.jks";


        //private final String SSL_KEY_FILE = "C:/Users/JuanUTN/back_G_interface_WSDL_Nobilis/backGInterfaceWSDLNobilis/src/main/java/laboratorio/ssl.key";
        //private final String SSL_CERT_FILE = "C:/Users/JuanUTN/back_G_interface_WSDL_Nobilis/backGInterfaceWSDLNobilis/src/main/java/laboratorio/ssl.cert";
        //private final String jksFilePath = "C:/Users/JuanUTN//back_G_interface_WSDL_Nobilis//backGInterfaceWSDLNobilis/src/main/java/laboratorio/keystore.jks";

        @Override
        public void init() {
    
            HttpContext http = new HttpContext(); 
            http.setip(IP);
            PemKeyToJKS pemKeyToJKS = new PemKeyToJKS();
            try {
                //pemKeyToJKS.convert(SSL_CERT_FILE, SSL_KEY_FILE, "labCordobaHttps1234",jksFilePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //http.secure(jksFilePath, "labCordobaHttps1234");
            IController[] controllers = registerControllers(); 

            http.setPort(PORT)
                .addExceptionHandling()
                .addRouting(controllers);
        }

        private IController[] registerControllers() {
            return new IController[] {
                new LoginController(new LoginService(), new TokenService(new OrigenRepository(), new UsuarioRepository()), new DecryptAES()),
                new RegisterController(new RegisterService(), new TokenService(new OrigenRepository(), new UsuarioRepository()), new DecryptAES()),
                new OrigenController(new OrigenService(), new TokenService(new OrigenRepository(), new UsuarioRepository()), new DecryptAES()),
                new OrdenController(new OrdenService(), new TokenService(new OrigenRepository(), new UsuarioRepository()), new DecryptAES(),
                new DerivacionService(), new OrigenService(), new IntegrityServiceNobilis(new OrdenService(), new DerivacionService())),
                new ResultadoController(new ResultadoService(), new TokenService(new OrigenRepository(), new UsuarioRepository()), new DecryptAES()),
                new TemplatesController(new TemplatesService(), new TokenService(new OrigenRepository(), new UsuarioRepository()), new DecryptAES())
            };
        }
    
}
