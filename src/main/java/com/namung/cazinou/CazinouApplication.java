package com.namung.cazinou;

import com.namung.cazinou.infrastructure.config.jda.JDAConfig;
import com.namung.cazinou.infrastructure.util.jda.MessageListener;
import com.namung.cazinou.infrastructure.util.jda.SlashCommandListener;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
@RequiredArgsConstructor
public class CazinouApplication {

  private final JDAConfig jdaConfig;
  private final SlashCommandListener slashCommandListener; // 의존성 주입
  private final MessageListener messageListener;

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
      .addEventListeners(slashCommandListener) // 이벤트 리스너 등록
      .build();

    // JDA가 준비될 때까지 대기
    jda.awaitReady();
    log.info("JDA is ready!");

    // 명령어 등록
    slashCommandListener.registerCommands(jda);

    jda
      .retrieveCommands()
      .queue(commands -> {
        for (Command command : commands) {
          log.info("Command: {}", command.getName());
        }
      });
  }
}
