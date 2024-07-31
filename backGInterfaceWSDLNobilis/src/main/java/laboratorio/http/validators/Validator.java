package laboratorio.http.validators;

public abstract class Validator<T> extends ValidateResult {
    public abstract ValidateResult validate(T dto);
}
