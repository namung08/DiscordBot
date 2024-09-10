package com.namung.cazinou;

import com.namung.cazinou.infrastructure.config.jda.JDAConfig;
import com.namung.cazinou.infrastructure.util.jda.MessageListener;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
@RequiredArgsConstructor
public class CazinouApplication {

  private final JDAConfig jdaConfig;
  private final MessageListener messageListener; // 의존성 주입

  public static void main(String[] args) {
    SpringApplication.run(CazinouApplication.class, args);
  }

  @PostConstruct
  public void startJDA() throws Exception {
    log.info("Starting JDA...");

    // JDA를 생성합니다.
    JDA jda = JDABuilder.createDefault(jdaConfig.getToken())
      .enableIntents(GatewayIntent.MESSAGE_CONTENT) // 메시지 내용을 읽어오기 위한 권한
      .setActivity(Activity.playing("개발중...")) // 봇의 상태 메시지 설정
      .addEventListeners(messageListener) // MessageListener를 추가
      .build();

    // JDA가 준비될 때까지 대기
    jda.awaitReady();
    log.info("JDA is ready!");
  }
}
