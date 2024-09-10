package com.namung.cazinou.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CazinouExceptionCode {
  CAZINOU_MEMBER_IS_BOT("Cazinou_001", "해당 사용자는 봇 입니다.");

  private final String code;
  private final String message;
}
