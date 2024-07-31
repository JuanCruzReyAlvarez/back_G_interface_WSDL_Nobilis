package laboratorio.http.controllers;


import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import laboratorio.http.exceptions.BadResquestException;
import laboratorio.http.exceptions.HttpException;
import laboratorio.http.routes.Routes;
import laboratorio.http.routes.Uri;
import laboratorio.http.validators.ValidateResult;
import laboratorio.http.validators.Validator;
import spark.Request;

public abstract class Controller implements IController {

    protected <T> T getBody(Request request, Class<T> type, Validator<T> validator) throws HttpException {
        try {
            T data = new Gson().fromJson(request.body(), type);

            if (validator == null)
                return data;

            return validateInput(data, validator);
        } catch (JsonParseException ex) {
            throw new BadResquestException("Malformed Body");
        }
    }

    protected <T> T validateInput(T data, Validator<T> validator) throws HttpException {
        ValidateResult validation = validator.validate(data);
        if (validation.isValid())
            return data;

        throw new BadResquestException(validation.getErrors()); 
    }

    //protected Map<String, String> formFields(Request request) throws HttpException {
    //    List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(), Charset.defaultCharset());
    //    Map<String, String> map = new HashMap<>();
    //    pairs.forEach(p -> map.put(p.getName(), p.getValue()));
    //
    //    return map;
    //}

    protected String json(Object obj) {
        return new Gson().toJson(obj);
    }

    protected String path(Uri uri) {
        return Routes.getInstance().getPaths().get(uri);
    }
}
