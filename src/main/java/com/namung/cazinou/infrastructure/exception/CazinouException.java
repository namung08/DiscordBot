package com.namung.cazinou.infrastructure.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
public class CazinouException extends RuntimeException {

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  public CazinouException(
    HttpStatus httpStatus,
    CazinouExceptionCode cazinouExceptionCode
  ) {
    super(
      "Error Code: " +
      cazinouExceptionCode.getCode() +
      ", Message: " +
      cazinouExceptionCode.getMessage()
    );
    this.httpStatus = httpStatus;
    this.code = cazinouExceptionCode.getCode();
    this.message = cazinouExceptionCode.getMessage();
  }
}
