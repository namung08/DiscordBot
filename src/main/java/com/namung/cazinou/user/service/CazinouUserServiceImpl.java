package com.namung.cazinou.user.service;

import com.mongodb.MongoTimeoutException;
import com.namung.cazinou.infrastructure.exception.CazinouException;
import com.namung.cazinou.infrastructure.exception.CazinouExceptionCode;
import com.namung.cazinou.infrastructure.util.jda.message.WorkMessage;
import com.namung.cazinou.user.model.CazinouUser;
import com.namung.cazinou.user.repository.CazinouUserRepository;
import com.namung.cazinou.user.repository.CustomCazinouUserRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.User;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CazinouUserServiceImpl implements CazinouUserService {

  private final CazinouUserRepository repository;
  private final CustomCazinouUserRepository customRepository;

  @Override
  public String saveUser(User user)
    throws MongoTimeoutException, DataAccessException {
    repository.save(
      CazinouUser.builder()
        .discordId(user.getIdLong())
        .balance(0L)
        .lastAttendance(LocalDate.now().minusDays(1L))
        .build()
    );
    return "Success to save user";
  }

  @Override
  public Boolean isUserExist(User user) {
    return repository.existsByDiscordId(user.getIdLong());
  }

  @Override
  public String modificationBalanceByMoney(User user, Long money) {
    CazinouUser cazinouUser = getUserByDiscordId(user.getIdLong());
    cazinouUser.setBalance(cazinouUser.getBalance() + money);
    cazinouUser.setLastAttendance(LocalDate.now());
    return customRepository.updateUser(cazinouUser);
  }

  @Override
  public String attendanceCheck(User user) {
    CazinouUser cazinouUser = getUserByDiscordId(user.getIdLong());
    if (cazinouUser.getLastAttendance().isBefore(LocalDate.now())) {
      modificationBalanceByMoney(user, 10000L);
      return "출석체크 완료! 10000원이 지급되었습니다.";
    }
    return "이미 오늘 출석 체크를 하셨습니다.";
  }

  @Override
  public Long getUserBalance(User user) {
    return getUserByDiscordId(user.getIdLong()).getBalance();
  }

  @Override
  public Integer work(User user) {
    CazinouUser cazinouUser = getUserByDiscordId(user.getIdLong());
    // 0부터 10 까지 랜덤한 숫자를 생성
    int result = (int) (Math.random() * 10);
    modificationBalanceByMoney(user, WorkMessage.getMoneyList().get(result));
    return result;
  }

  private CazinouUser getUserByDiscordId(Long discordId) {
    return repository
      .findByDiscordId(discordId)
      .orElseThrow(() ->
        new CazinouException(
          HttpStatus.BAD_REQUEST,
          CazinouExceptionCode.NOT_FOUND_USER
        )
      );
  }
}
