package br.com.systemsgs.ordem_servico_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionControllerAdvice {

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiRestErrors handlerMethodNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        List<String> errors =  methodArgumentNotValidException.getBindingResult().getAllErrors()
                .stream().map(erro -> erro.getDefaultMessage())
                    .collect(Collectors.toList());

        return new ApiRestErrors(errors);
    }

    @ExceptionHandler(EmailDuplicadoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiRestErrors emailDuplicadoException(EmailDuplicadoException emailDuplicadoException){
        return new ApiRestErrors(emailDuplicadoException.getMessage());
    }
}
