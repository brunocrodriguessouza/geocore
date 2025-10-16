package com.sccon.geocore.web.error;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Handler global para tratamento de exceções na aplicação.
 * Centraliza o tratamento de erros e retorna respostas padronizadas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Trata exceções de pessoa não encontrada.
     * 
     * @param ex exceção de elemento não encontrado
     * @return detalhes do problema com status 404
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ProblemDetail handlePersonNotFound(NoSuchElementException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Pessoa não encontrada");
        problemDetail.setProperty("errorCode", "PESSOA_NOT_FOUND");
        problemDetail.setProperty("message", "A pessoa solicitada não foi encontrada no sistema");
        return problemDetail;
    }

    /**
     * Trata exceções de parâmetros inválidos.
     * 
     * @param ex exceção de argumento ilegal
     * @return detalhes do problema com status 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleInvalidParameter(IllegalArgumentException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Parâmetro inválido");
        problemDetail.setProperty("errorCode", "INVALID_PARAMETER");
        problemDetail.setProperty("message", "Os parâmetros fornecidos são inválidos");
        return problemDetail;
    }

    /**
     * Trata exceções de validação de argumentos de método.
     * 
     * @param ex exceção de validação
     * @return detalhes do problema com status 400 e erros de campo
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationError(MethodArgumentNotValidException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Erro de validação");
        problemDetail.setDetail("Dados de entrada inválidos");
        
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            fieldErrors.put(error.getField(), error.getDefaultMessage()));
        
        problemDetail.setProperty("fieldErrors", fieldErrors);
        problemDetail.setProperty("errorCode", "VALIDATION_ERROR");
        problemDetail.setProperty("message", "Os dados fornecidos não atendem aos critérios de validação");
        return problemDetail;
    }

    /**
     * Trata exceções de violação de restrições.
     * 
     * @param ex exceção de violação de restrição
     * @return detalhes do problema com status 400
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Violação de restrição");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("errorCode", "CONSTRAINT_VIOLATION");
        problemDetail.setProperty("message", "Os dados fornecidos violam as restrições do sistema");
        return problemDetail;
    }

    /**
     * Trata exceções não esperadas.
     * 
     * @param ex exceção genérica
     * @return detalhes do problema com status 500
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpectedError(Exception ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Erro interno do servidor");
        problemDetail.setDetail("Ocorreu um erro inesperado no servidor");
        problemDetail.setProperty("errorCode", "INTERNAL_ERROR");
        problemDetail.setProperty("message", "Entre em contato com o suporte técnico");
        return problemDetail;
    }
}
