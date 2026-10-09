package juanrodriguesdev.apisinan.exception;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tools.jackson.databind.exc.InvalidFormatException;

import java.net.URI;
import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail tratarNaoEncontrado(ResourceNotFoundException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Recurso não encontrado");
        pd.setType(URI.create("https://api.sinan.local/erros/nao-encontrado"));
        return pd;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request
    ){
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Um ou mais campos enviados são Inválidos");
        pd.setTitle("Dados Inválidos");
        pd.setType(URI.create("https://api.sinan.local/erros/dados-invalidos"));
        pd.setProperty("erros", ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ProblemDetail tratarRegraDeNegocio(BusinessRuleException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        pd.setTitle("Violação de regra de negócio");
        pd.setType(URI.create("https://api.sinan.local/erros/regra-de-negocio"));
        return pd;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail tratarParametroInvalido(IllegalArgumentException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        pd.setTitle("Parâmetro inválido");
        pd.setType(URI.create("https://api.sinan.local/erros/parametro-invalido"));
        return pd;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail tratarTipoInvalido(MethodArgumentTypeMismatchException ex) {
        String valoresAceitos = ex.getRequiredType() != null && ex.getRequiredType().isEnum()
                ? Arrays.toString(ex.getRequiredType().getEnumConstants())
                : "um formato válido";

        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "O parâmetro '%s' recebeu um valor inválido ('%s'). Valores aceitos: %s"
                        .formatted(ex.getName(), ex.getValue(), valoresAceitos));
        pd.setTitle("Parâmetro inválido");
        pd.setType(URI.create("https://api.sinan.local/erros/parametro-invalido"));
        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail tratarErroInesperado(Exception ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro inesperado ao processar a requisição");
        pd.setTitle("Erro interno");
        pd.setType(URI.create("https://api.sinan.local/erros/erro-interno"));
        return pd;
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        String detalhe = "O corpo da requisição está malformado ou contém um valor inválido";

        Throwable causaRaiz = ex.getMostSpecificCause();
        if (causaRaiz instanceof InvalidFormatException ife && ife.getTargetType().isEnum()) {
            detalhe = "Um dos campos recebeu um valor inválido ('%s'). Valores aceitos: %s"
                    .formatted(ife.getValue(), Arrays.toString(ife.getTargetType().getEnumConstants()));
        }

        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detalhe);
        pd.setTitle("Corpo da requisição inválido");
        pd.setType(URI.create("https://api.sinan.local/erros/corpo-invalido"));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }
}
