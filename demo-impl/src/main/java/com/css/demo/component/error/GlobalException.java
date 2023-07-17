package com.css.demo.component.error;

import com.css.demo.model.common.BaseMessage;
import com.css.demo.model.common.ResultBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalException {

    private static final int DEFAULT_ERROR_CODE = 9999;
    private static final String DEFAULT_ERROR_MSG = "System Error";

    private static final int UNKNOWN_ERROR_CODE = 9988;
    private static final String UNKNOWN_ERROR_MSG = "Unknown System Error";

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public ResponseEntity<Object> handleBizException(BizException bizException) {
        log.warn("HandleBizException:", bizException);
        return new ResponseEntity<>(ResultBuilder.build(false,
                bizException.getCode(),
                bizException.getMessage()),
                HttpStatus.OK);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException runtimeException) {
        log.error("HandleRuntimeException:", runtimeException);
        return new ResponseEntity<>(ResultBuilder.build(false,
                DEFAULT_ERROR_CODE,
                DEFAULT_ERROR_MSG),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        log.error("HandleMethodArgumentNotValidException:", exception);
        BindingResult bindResult = exception.getBindingResult();
        List<FieldError> fieldErrorList = bindResult.getFieldErrors();
        String errMsg = fieldErrorList
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(","));
        return new ResponseEntity<>(ResultBuilder.build(false,
                BaseMessage.PARAM_CHECK_ERROR.getCode(),
                errMsg), HttpStatus.OK);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException exception) {
        log.error("handleBindException:", exception);

        BindingResult bindResult = exception.getBindingResult();
        List<FieldError> fieldErrorList = bindResult.getFieldErrors();
        String errMsg = fieldErrorList.stream().map(FieldError::getDefaultMessage).collect(
                Collectors.joining(","));
        return new ResponseEntity<>(ResultBuilder.build(Boolean.FALSE,
                BaseMessage.PARAM_CHECK_ERROR.getCode(),
                errMsg), HttpStatus.OK);
    }

    /**
     * catch 404，400, and Not find controller and Other unknown error
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> defaultErrorHandler(Exception ex) {
        log.error("defaultErrorHandler:", ex);

        int errorCode = 0;
        String errorMsg = null;
        if (ex instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            errorCode = UNKNOWN_ERROR_CODE;
            errorMsg = UNKNOWN_ERROR_MSG;
        } else {
            errorCode = DEFAULT_ERROR_CODE;
            errorMsg = DEFAULT_ERROR_MSG;
        }
        return new ResponseEntity<>(ResultBuilder.build(false, errorCode, errorMsg), HttpStatus.OK);
    }


}
