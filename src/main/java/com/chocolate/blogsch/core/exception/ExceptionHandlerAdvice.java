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
     * @RequestParam binding 못했을 경우 발생
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleMethodArgumentTypeMismatchException(HttpServletRequest request,
                                                                      MethodArgumentTypeMismatchException e) {
        log.error("handleException={} (URL={}, METHOD={})", e.getMessage(), request.getRequestURL(), request.getMethod(), e);

        return ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, messageSource, e.getMessage());

    }

    /**
     * 바인딩 에러 발생했을 경우
     * @param e
     * @return
     */
    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleBindException(HttpServletRequest request,
                                                BindException e) {
        log.error("handleException={} (URL={}, METHOD={})", e.getMessage(), request.getRequestURL(), request.getMethod(), e);

        return ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, messageSource, e.getBindingResult());
//        return ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, messageSource, e.getMessage());
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

    /**
     * dto enum 맵핑 오류
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleInvalidFormatException(HttpServletRequest request,
                                                         InvalidFormatException e){
        log.error("handleException={} (URL={}, METHOD={})", e.getMessage(), request.getRequestURL(), request.getMethod(), e);

        return ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, messageSource);
    }


    /**
     * Rest URI Key값과 RequestBody의 Key값이 다를 경우
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(ResourceBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleResourceBadRequestException(HttpServletRequest request,
                                                              ResourceBadRequestException e){
        log.error("handleException={}, {} (URL={}, METHOD={})", e.getClass().getName(), e.getMessage(), request.getRequestURL(), request.getMethod());

        return ErrorResponse.of(ErrorCode.URI_PATH_AND_BODY_NOT_MATCH, messageSource);
    }

    /**
     * DB 데이터 무결성 오류
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ErrorResponse handleDataIntegrityViolationException(HttpServletRequest request,
                                                                  DataIntegrityViolationException e){
        log.error("handleException={} (URL={}, METHOD={})", e.getMessage(), request.getRequestURL(), request.getMethod(), e);

        return ErrorResponse.of(ErrorCode.DUPLICATE_KEY, messageSource);
    }

    /**
     * 시퀀스에 값을 입력하고 저장할 경우 발생하는 오류
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ErrorResponse handleInvalidDataAccessApiUsageException(HttpServletRequest request,
                                                                     InvalidDataAccessApiUsageException e){
        log.error("handleException={} (URL={}, METHOD={})", e.getMessage(), request.getRequestURL(), request.getMethod(), e);

        return ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, messageSource);
    }


    /**
     * JDBC SQLState: 22011
     * The value is too large for the column. (Actual value: 21, Maximum value: 20)
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(DataException.class)
    protected ErrorResponse handleDataException(HttpServletRequest request,
                                                                   DataException e) {
        log.error("handleException={} (URL={}, METHOD={})", e.getMessage(), request.getRequestURL(), request.getMethod(), e);

        return ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, messageSource);
    }


    /**
     * 서비스 단에서 발생한 오류
     * - 오류 코드를 정의하여 사용
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    protected ResponseEntity<ErrorResponse> handleServiceException(HttpServletRequest request,
                                                                   ServiceException e) {
        log.error("handleException={}, {} (URL={}, METHOD={})", e.getClass().getName(), e.getMessage(), request.getRequestURL(), request.getMethod(), e);
        return ResponseEntity.status(HttpStatus.valueOf(e.errorCode.getStatus()))
                .body(ErrorResponse.of(e.errorCode, messageSource, e.getMessage()));
    }

}
