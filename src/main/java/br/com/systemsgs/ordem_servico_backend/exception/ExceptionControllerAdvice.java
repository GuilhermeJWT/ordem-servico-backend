package br.com.systemsgs.ordem_servico_backend.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiRestErrors handlePedidoNotFoundException(RecursoNaoEncontradoException recursoNaoEncontradoException){
        return new ApiRestErrors(recursoNaoEncontradoException.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiRestErrors handlerMethodNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        List<String> errors =  methodArgumentNotValidException.getBindingResult().getAllErrors()
                .stream().map(erro -> erro.getDefaultMessage())
                    .collect(Collectors.toList());

        return new ApiRestErrors(errors);
    }
}
