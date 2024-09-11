package com.namung.cazinou.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CazinouExceptionCode {
  CAZINOU_MEMBER_IS_BOT("Cazinou_001", "해당 사용자는 봇 입니다."),
  NOT_FOUND_SEQUENCE("Cazinou_002", "해당 시퀀스를 찾을 수 없습니다.");

  private final String code;
  private final String message;
}
