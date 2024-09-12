package com.namung.cazinou.user.repository;

import com.namung.cazinou.user.model.CazinouUser;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CazinouUserRepository
  extends MongoRepository<CazinouUser, String> {
  boolean existsByDiscordId(Long discordId);

  Optional<CazinouUser> findByDiscordId(Long discordId);
}
