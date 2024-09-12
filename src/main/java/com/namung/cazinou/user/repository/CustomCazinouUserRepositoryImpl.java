package com.namung.cazinou.user.repository;

import com.mongodb.MongoTimeoutException;
import com.mongodb.client.result.UpdateResult;
import com.namung.cazinou.infrastructure.util.mongo.MongoTemplateUtils;
import com.namung.cazinou.user.model.CazinouUser;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomCazinouUserRepositoryImpl
  implements CustomCazinouUserRepository {

  private final MongoTemplate template;
  private final MongoTemplateUtils mongoTemplateUtils;

  @Override
  public String updateUser(CazinouUser user)
    throws MongoTimeoutException, DataAccessException {
    Query query = mongoTemplateUtils.getQueryByField(
      "discord_id",
      user.getDiscordId()
    );
    Update update = new Update()
      .set("balance", user.getBalance())
      .set("last_attendance", user.getLastAttendance())
      .currentDate("reg_date");
    UpdateResult result = template.updateFirst(
      query,
      update,
      CazinouUser.class
    );
    return mongoTemplateUtils.updateResultMessage(
      result,
      "Success to update user"
    );
  }
}
