package laboratorio.http.context;

import java.util.Arrays;
import com.google.gson.Gson;

import laboratorio.http.controllers.IController;
import laboratorio.http.exceptions.HttpException;
import laboratorio.http.exceptions.HttpExceptionMessage;
import spark.Spark;

public class HttpContext {
    
    public HttpContext addRouting(IController ...controllers) {
        Arrays.stream(controllers).forEach(c -> c.routes());
        return this;
    }

    public HttpContext setPort(int port) {
        Spark.port(port);
        return this;
    }

    public HttpContext setip(String ip) {
        Spark.ipAddress(ip);
        return this;
    }

    public HttpContext secure(java.lang.String keystoreFile, java.lang.String keystorePassword) {
        Spark.secure(keystoreFile, keystorePassword, null, null);
        return this;
    }

    public HttpContext addExceptionHandling() {
        Spark.exception(HttpException.class, (exception, request, response) -> {
            HttpExceptionMessage data = new Gson().fromJson(exception.getMessage(), HttpExceptionMessage.class);
            response.status(data.getCode());
            response.body(data.getResponse());
        });
        return this;
    }
}
