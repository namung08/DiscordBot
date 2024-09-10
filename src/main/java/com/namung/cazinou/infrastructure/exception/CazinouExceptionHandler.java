package com.namung.cazinou.infrastructure.exception;

import com.namung.cazinou.web.dto.CommonRes;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@ControllerAdvice
public class CazinouExceptionHandler {

  @ExceptionHandler(CazinouException.class)
  public ResponseEntity<?> handlerCazinouException(CazinouException e) {
    log.error(
      "{} error: Code {}, Message {}",
      e.getClass(),
      e.getCode(),
      e.getMessage()
    );

    Map<String, Object> res = new HashMap<>();
    res.put("code", e.getCode());
    res.put("message", e.getMessage());
    return ResponseEntity.status(e.getHttpStatus()).body(CommonRes.fail(res));
  }

  /**
   * {@link MethodArgumentNotValidException} 발생 시 처리하는 메서드입니다.
   *
   * @param e 발생한 {@link MethodArgumentNotValidException}
   * @return HTTP 400 상태 코드와 유효성 검증 실패 메시지를 반환합니다.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> handleMethodArgumentNotValidException(
    MethodArgumentNotValidException e
  ) {
    // 에러 로그 남기기
    log.error("유효성 검증 실패: {}", e.getMessage(), e);

    Map<String, Object> res = new HashMap<>();
    res.put("errorCode", "VALIDATION_ERROR");
    res.put(
      "message",
      e.getBindingResult().getAllErrors().get(0).getDefaultMessage()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
      CommonRes.fail(res)
    );
  }

  /**
   * {@link MethodArgumentTypeMismatchException} 발생 시 처리하는 메서드입니다.
   *
   * @param e 발생한 {@link MethodArgumentTypeMismatchException}
   * @return HTTP 400 상태 코드와 잘못된 인자 타입 오류 메시지를 반환합니다.
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> handleMethodArgumentTypeMismatchException(
    MethodArgumentTypeMismatchException e
  ) {
    // 에러 로그 남기기
    log.error(
      "MethodArgumentTypeMismatchException 발생: {}",
      e.getMessage(),
      e
    );

    Map<String, Object> res = new HashMap<>();
    res.put("errorCode", "ARGUMENT_TYPE_MISMATCH");
    res.put("message", e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
      CommonRes.fail(res)
    );
  }

  /**
   * {@link NoResourceFoundException} 발생 시 처리하는 메서드입니다.
   *
   * @param e 발생한 {@link NoResourceFoundException}
   * @return HTTP 404 상태 코드와 리소스가 없다는 메시지를 반환합니다.
   */
  @ExceptionHandler(NoResourceFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<?> handleNoResourceFoundException(
    NoResourceFoundException e
  ) {
    // 에러 로그 남기기
    log.error("NoResourceFoundException 발생: {}", e.getMessage(), e);

    Map<String, Object> res = new HashMap<>();
    res.put("errorCode", "RESOURCE_NOT_FOUND");
    res.put("message", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
      CommonRes.fail(res)
    );
  }

  /**
   * {@link ConstraintViolationException} 발생 시 처리하는 메서드입니다.
   *
   * @param e 발생한 {@link ConstraintViolationException}
   * @return HTTP 400 상태 코드와 유효성 검사 실패 메시지를 반환합니다.
   */
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> handleConstraintViolationException(
    ConstraintViolationException e
  ) {
    // 에러 로그 남기기
    log.error("ConstraintViolationException 발생: {}", e.getMessage(), e);

    Map<String, Object> res = new HashMap<>();
    res.put("errorCode", "VALIDATION_ERROR");
    res.put("message", e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
      CommonRes.fail(res)
    );
  }

  /**
   * {@link RuntimeException} 발생 시 처리하는 메서드입니다.
   *
   * @param e 발생한 {@link RuntimeException}
   * @return HTTP 500 상태 코드와 런타임 예외 메시지를 반환합니다.
   */
  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
    // 에러 로그 남기기
    log.error("RuntimeException 발생: {}", e.getMessage(), e);

    Map<String, Object> res = new HashMap<>();
    res.put("errorCode", "RUNTIME_EXCEPTION");
    res.put("message", e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
      CommonRes.fail(res)
    );
  }

  /**
   * 모든 {@link Exception} 유형의 예외를 처리하는 메서드입니다.
   *
   * @param e 발생한 일반 예외
   * @return HTTP 500 상태 코드와 서버 내부 오류 메시지를 반환합니다.
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<?> handleException(Exception e) {
    // 에러 로그 남기기
    log.error("Exception 발생: {}", e.getMessage(), e);

    Map<String, Object> res = new HashMap<>();
    res.put("errorCode", "INTERNAL_SERVER_ERROR");
    res.put("message", e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
      CommonRes.fail(res)
    );
  }
}
