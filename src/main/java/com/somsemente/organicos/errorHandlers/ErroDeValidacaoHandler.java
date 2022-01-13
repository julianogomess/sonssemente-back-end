package com.somsemente.organicos.errorHandlers;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
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
        log.info("Erros de validação : " + erros);
        return erros;
    }

    @ResponseStatus(code = HttpStatus.FOUND)
    @ExceptionHandler(org.springframework.dao.DuplicateKeyException.class)

    public String handle(DuplicateKeyException exception) {

        String[] erro = exception.getCause().getLocalizedMessage().split("dup key: ");
        String[] e = erro[1].split("'");
        log.info("Cadastro de item ja conta na base, erro: " + e);
        return e[0].replace('{', ' ').replace('}', ' ') + "já existe.";

    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(com.fasterxml.jackson.databind.exc.InvalidFormatException.class)
    public Map<Object, Object> handle(InvalidFormatException exception) {
        Map<Object, Object> model = new HashMap<>();
        model.put("message","Formatação da mensagem invalida! Procure pelo tipo de dado correto.");
        log.error(exception.getLocalizedMessage());
        return model;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public String handle(NullPointerException exception){
        log.error(exception.getLocalizedMessage());
        return exception.getLocalizedMessage();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParseException.class)
    public Map<Object, Object> handle(ParseException exception){
        Map<Object, Object> model = new HashMap<>();
        String msg = "Erro ao encontrar a data, verifique a escrita correta do dataVenc: (dd-mm-yyyy)";
        log.error(msg);
        model.put("message",msg);
        return model;
    }
}