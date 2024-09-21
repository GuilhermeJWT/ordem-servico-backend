package br.com.systemsgs.ordem_servico_backend.exception;

import br.com.systemsgs.ordem_servico_backend.exception.errors.*;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiRestErrors httpMessageNotReadableException(){
        return new ApiRestErrors(new PayloadInexistenteException().getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiRestErrors handlerMethodNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        List<String> errors =  methodArgumentNotValidException.getBindingResult().getAllErrors()
                .stream().map(erro -> erro.getDefaultMessage())
                .toList();

        return new ApiRestErrors(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiRestErrors camposDuplicadoException(){
        return new ApiRestErrors(new CamposDuplicadosException().getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiRestErrors internalServerErroException(){
        return new ApiRestErrors(new ErroInternoException().getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiRestErrors metodoHttpNaoSuportadoException(){
        return new ApiRestErrors(new MetodoHttpNaoSuportadoException().getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiRestErrors endpointNaoEncontradoException(){
        return new ApiRestErrors(new EndpointNaoEncontradoException().getMessage());
    }

    @ExceptionHandler(FornecedorNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiRestErrors fornecedorNaoEncontradoException(){
        return new ApiRestErrors(new FornecedorNaoEncontradoException().getMessage());
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiRestErrors usuarioNaoEncontradoException(){
        return new ApiRestErrors(new UsuarioNaoEncontradoException().getMessage());
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiRestErrors recursoNaoEncontradoException(RecursoNaoEncontradoException recursoNaoEncontradoException){
        return new ApiRestErrors(recursoNaoEncontradoException.getMessage());
    }

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiRestErrors clienteNaoEncontradoException(ClienteNaoEncontradoException clienteNaoEncontradoException){
        return new ApiRestErrors(clienteNaoEncontradoException.getMessage());
    }

    @ExceptionHandler(TecnicoResponsavelNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiRestErrors tecnicoNaoEncontradoException(TecnicoResponsavelNaoEncontradoException tecnicoResponsavelNaoEncontradoException){
        return new ApiRestErrors(tecnicoResponsavelNaoEncontradoException.getMessage());
    }

    @ExceptionHandler(VendaNaoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiRestErrors vendaNaoEncontradaException(VendaNaoEncontradaException vendaNaoEncontradaException){
        return new ApiRestErrors(vendaNaoEncontradaException.getMessage());
    }

    @ExceptionHandler(ContasPagarReceberNaoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiRestErrors contasPagarReceberNaoEncontradaException(ContasPagarReceberNaoEncontradaException contasPagarReceberNaoEncontradaException){
        return new ApiRestErrors(contasPagarReceberNaoEncontradaException.getMessage());
    }

    @ExceptionHandler(EmailDuplicadoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiRestErrors emailDuplicadoException(EmailDuplicadoException emailDuplicadoException){
        return new ApiRestErrors(emailDuplicadoException.getMessage());
    }

    @ExceptionHandler(TokenInexistenteException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiRestErrors tokenInexistenteException(TokenInexistenteException tokenInexistenteException){
        return new ApiRestErrors(tokenInexistenteException.getMessage());
    }

    @ExceptionHandler(JWTVerificationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiRestErrors tokenExpirado(TokenExpiradoException tokenExpiradoException){
        return new ApiRestErrors(tokenExpiradoException.getMessage());
    }
}
