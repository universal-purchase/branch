package com.chocolate.blogsch.core.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private ZonedDateTime timestamp;
    private int status;
    private String code;
    private String message;
    private List<FieldError> errors;

    private ErrorResponse(ErrorCode errorCode, MessageSource messageSource) {
        this.timestamp = ZonedDateTime.now();
        this.code = errorCode.getCode();
        this.status = errorCode.getStatus();
        this.message = messageSource.getMessage(errorCode.getMessage(), new Object[]{}, LocaleContextHolder.getLocale());
    }

    private ErrorResponse(ErrorCode errorCode, String customMessage) {
        this.timestamp = ZonedDateTime.now();
        this.code = errorCode.getCode();
        this.status = errorCode.getStatus();
        this.message = customMessage;
    }

    private ErrorResponse(ErrorCode errorCode, MessageSource messageSource, String errorMessage) {
        this.timestamp = ZonedDateTime.now();
        this.code = errorCode.getCode();
        this.status = errorCode.getStatus();
        this.message = messageSource.getMessage(errorCode.getMessage(), new Object[]{errorMessage}, LocaleContextHolder.getLocale());
    }

    private ErrorResponse(ErrorCode errorCode, MessageSource messageSource, List<FieldError> errors) {
        this.timestamp = ZonedDateTime.now();
        this.code = errorCode.getCode();
        this.status = errorCode.getStatus();
        this.message = messageSource.getMessage(errorCode.getMessage(), new Object[]{}, LocaleContextHolder.getLocale());
        this.errors = new ArrayList<>(errors);
    }

    public static ErrorResponse of(ErrorCode code, String customMessage) {
        return new ErrorResponse(code, customMessage);
    }

    public static ErrorResponse of(ErrorCode errorCode, MessageSource messageSource) {
        return new ErrorResponse(errorCode, messageSource);
    }

    public static ErrorResponse of(ErrorCode errorCode, MessageSource messageSource, String errorMessage) {
        return new ErrorResponse(errorCode, messageSource, errorMessage);
    }

    public static ErrorResponse of(ErrorCode errorCode, MessageSource messageSource, BindingResult bindingResult) {
        return new ErrorResponse(errorCode, messageSource, FieldError.of(bindingResult));
    }

    /**
     * java validator 에러 발생 시 에러 정보 중 필요한 내용만 반환한다
     */
    @Getter
    @NoArgsConstructor(access = PROTECTED)
    public static class FieldError {
        private String message;
        private String field;
        private String rejectedValue;

        private FieldError(final String field, final String rejectedValue, final String message) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.message = message;
        }

        public static List<FieldError> of(final String field, final String rejectedValue, final String message) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, rejectedValue, message));
            return fieldErrors;
        }

        /**
         * BindingResult to FieldError
         *
         * @param bindingResult
         * @return
         */
        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}
