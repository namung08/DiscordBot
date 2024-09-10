package com.namung.cazinou.infrastructure.config.jda;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JDAConfig {

  @Value("${jda.token}")
  private String token;
}
