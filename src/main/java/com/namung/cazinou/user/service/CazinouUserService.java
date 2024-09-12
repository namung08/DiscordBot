package com.namung.cazinou.user.service;

import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface CazinouUserService {
  /**
   * 유저 정보 저장<br>
   * JDA의 {@link User} 객체를 받아서 저장한다.
   * 저장할 데이터는 {@link User#getIdLong()} 이다.
   * @param user {@link User} 객체
   * @return 저장의 성공 여부
   * @throws com.mongodb.MongoTimeoutException if a timeout occurs while waiting for a pooled connection
   * @throws org.springframework.dao.DataAccessException if a data access exception occurs while performing the operation
   */
  String saveUser(User user);

  /**
   * 유저 중복 여부 확인
   * @param user {@link User} 객체
   * @return {@code true} 중복, {@code false} 중복 아님
   */
  Boolean isUserExist(User user);

  /**
   * 유저의 잔액을 변경한다.
   * @param user {@link User} 객체
   * @param money 가감될 금액 (음수 가능)
   * @return 변경 성공 여부
   */
  String modificationBalanceByMoney(User user, Long money);

  /**
   * 출석체크<br>
   * 출석체크를 하고, 보상을 지급한다. <br>
   * 보상은 {@link #modificationBalanceByMoney(User, Long)} 메서드를 호출하여 지급한다. <br>
   * 1일 1회만 보상을 지급한다.<br>
   *
   * @see #modificationBalanceByMoney(User, Long)
   * @param user {@link User} 객체
   * @return 출석체크 결과
   */
  String attendanceCheck(User user);

  Long getUserBalance(User user);

  Integer work(User user);
}
