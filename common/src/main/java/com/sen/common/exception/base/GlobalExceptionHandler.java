package com.sen.common.exception.base;

import com.sen.common.constant.enums.ExceptionEnum;
import com.sen.common.core.domain.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-17
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = BaseException.class)
    public ResponseData baseExceptionHandler(HttpServletRequest req, BaseException e) {
        log.error("发生业务异常！异常原因：{}",e.getMessage());
        return new ResponseData(Integer.parseInt(e.getCode()), e.getMessage());
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseData exceptionHandler(HttpServletRequest req, NullPointerException e) {
        log.error("发生空指针异常！原因是： ", e);
        return new ResponseData(ExceptionEnum.BODY_NOT_MATCH.getErrorCode(), ExceptionEnum.BODY_NOT_MATCH.getErrorMsg());
    }


    @ExceptionHandler(value = Exception.class)
    public ResponseData exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("未知异常！原因是： ", e);
        return new ResponseData(ExceptionEnum.INTERNAL_SERVER_ERROR.getErrorCode(), ExceptionEnum.INTERNAL_SERVER_ERROR.getErrorMsg());
    }
}
