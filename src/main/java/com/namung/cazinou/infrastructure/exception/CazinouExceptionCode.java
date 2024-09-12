package com.namung.cazinou.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CazinouExceptionCode {
  CAZINOU_MEMBER_IS_BOT("Cazinou_001", "해당 사용자는 봇 입니다."),
  NOT_FOUND_SEQUENCE("Cazinou_002", "해당 시퀀스를 찾을 수 없습니다."),
  BAD_REQUEST("Cazinou_003", "잘못된 요청입니다."),
  NOT_FOUND_USER("Cazinou_004", "해당 사용자를 찾을 수 없습니다."),
  UPDATE_FAIL("Cazinou_005", "업데이트에 실패하였습니다.");

  private final String code;
  private final String message;
}
