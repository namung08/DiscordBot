package com.namung.cazinou.user.repository;

import com.namung.cazinou.user.model.CazinouUser;
import net.dv8tion.jda.api.entities.User;

public interface CustomCazinouUserRepository {
  /**
   * 유저 정보를 업데이트 한다.
   * @param user {@link User} 객체
   * @return 업데이트 성공 여부
   */
  String updateUser(CazinouUser user);
}
