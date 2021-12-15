package com.somsemente.organicos.errorHandlers;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.mongodb.MongoWriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErroDeValidacaoHandler {

    @Autowired
    private MessageSource messageSource;
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)

    public List<String> handle(MethodArgumentNotValidException exception){
        List<String> erros = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(e->{

            String erro = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            String mensagem = e.getField().toUpperCase()+": "+erro;
            erros.add(mensagem);

        });
        return erros;
    }

    @ResponseStatus(code = HttpStatus.FOUND)
    @ExceptionHandler(org.springframework.dao.DuplicateKeyException.class)

    public String handle(DuplicateKeyException exception) {

        String[] erro = exception.getCause().getLocalizedMessage().split("dup key: ");
        String[] e = erro[1].split("'");
        return e[0].replace('{', ' ').replace('}', ' ') + "já existe.";

    }
}