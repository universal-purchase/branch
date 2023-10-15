package com.chocolate.blogsch.core.exception;

import com.chocolate.blogsch.core.domain.ErrorCode;
import com.chocolate.blogsch.core.domain.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerAdvice {

    private final MessageSource messageSource;

    /**
     * default
     * @param request^
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponse handleException(HttpServletRequest request,
                                            Exception e) {
        log.error("handleException={}, {} (URL={}, METHOD={})", e.getClass().getName(), e.getMessage(), request.getRequestURL(), request.getMethod(), e);


        return ErrorResponse.of(ErrorCode.API_SERVICE_UNAVAILABLE.INTERNAL_SERVER_ERROR, messageSource, e.getMessage());
    }

    /**
     * 잘못된 URL로 접근했을 경우
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse handleNoHandlerFoundException(HttpServletRequest request,
                                                          NoHandlerFoundException e) {
        log.error("handleException={} (URL={}, METHOD={})", e.getMessage(), request.getRequestURL(), request.getMethod());

        return ErrorResponse.of(ErrorCode.NOT_FOUND, messageSource, e.getMessage());

    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected ErrorResponse handleHttpRequestMethodNotSupportedException(HttpServletRequest request,
                                                                         HttpRequestMethodNotSupportedException e) {
        log.error("handleException={} (URL={}, METHOD={})", e.getMessage(), request.getRequestURL(), request.getMethod(), e);

        return ErrorResponse.of(ErrorCode.NOT_FOUND, messageSource, e.getMessage());

    }

    /**
     * 지원하지 않은 Media Type으로 호출 할 경우 발생
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected ErrorResponse handleHttpMediaTypeNotSupportedException(HttpServletRequest request,
                                                                     HttpMediaTypeNotSupportedException e) {
        log.error("handleException={} (URL={}, METHOD={})", e.getMessage(), request.getRequestURL(), request.getMethod(), e);

        return ErrorResponse.of(ErrorCode.NOT_FOUND, messageSource, e.getMessage());

    }

    /**
     * 데이터가 없을 경우
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({ResourceNotFoundException.class, EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse handleResourceNotFoundException(HttpServletRequest request,
                                                            Exception e) {
        log.error("handleException={} (URL={}, METHOD={})", e.getMessage(), request.getRequestURL(), request.getMethod(), e);

        return ErrorResponse.of(ErrorCode.RESOURCE_NOT_FOUND, messageSource, e.getMessage());
    }

    /**
     * 데이터가 이미 존재할 경우
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ErrorResponse handleResourceAlreadyExistsException(HttpServletRequest request,
                                                                 ResourceAlreadyExistsException e) {
        log.error("handleException={} (URL={}, METHOD={})", e.getMessage(), request.getRequestURL(), request.getMethod(), e);

        return ErrorResponse.of(ErrorCode.RESOURCE_ALREADY_EXISTS, messageSource, e.getMessage());
    }

    /**
     * @Valid binding 오류
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleMethodArgumentNotValidException(HttpServletRequest request,
                                                                  MethodArgumentNotValidException e) {
        log.error("handleException={} (URL={}, METHOD={})", e.getMessage(), request.getRequestURL(), request.getMethod(), e);

        return ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, messageSource, e.getBindingResult());

    }

    /**
     * Request Param Binding 오류
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleMissingServletRequestParameterException(HttpServletRequest request,
                                                                    MissingServletRequestParameterException e){

        log.error("handleException={} (URL={}, METHOD={})", e.getMessage(), request.getRequestURL(), request.getMethod(), e);

        return ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, messageSource);
    }

    /**
     * Json Parser 오류
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleHttpMessageNotReadableException(HttpServletRequest request,
                                                                  HttpMessageNotReadableException e){

        log.error("handleException={} (URL={}, METHOD={})", e.getMessage(), request.getRequestURL(), request.getMethod(), e);

        return ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, messageSource);
    }

}
