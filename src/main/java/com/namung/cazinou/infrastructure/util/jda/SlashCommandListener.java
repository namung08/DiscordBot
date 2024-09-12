package com.namung.cazinou.infrastructure.util.jda;

import com.namung.cazinou.infrastructure.exception.CazinouException;
import com.namung.cazinou.infrastructure.util.jda.commands.CommandManager;
import com.namung.cazinou.infrastructure.util.jda.commands.SlashCommands;
import com.namung.cazinou.infrastructure.util.jda.message.EmbedUtil;
import com.namung.cazinou.infrastructure.util.jda.message.MessageModel;
import com.namung.cazinou.user.service.CazinouUserService;
import jakarta.validation.ConstraintViolationException;
import java.awt.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Log4j2
@Component
@RequiredArgsConstructor
public class SlashCommandListener extends ListenerAdapter {

  private final CazinouUserService cazinouUserService;
  private final CommandManager commandManager; // 명령어 관리 객체

  public JDA registerCommands(JDA jda) {
    log.info("Registering commands...");
    // 명령어 등록
    List<CommandData> commands = commandManager.getCommandDataList();
    jda.updateCommands().addCommands(commands).queue();
    log.info("Commands registered!");
    return jda;
  }

  @Override
  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
    if (event.getUser().isBot()) {
      return;
    }
    // 명령어가 입력된 채널의 주제가 #cazinou인지 확인
    if (differentChannelTopic(event)) {
      return;
    }
    // 명령어를 입력한 사용자의 정보를 MongoDB에 저장
    if (!cazinouUserService.isUserExist(event.getUser())) {
      cazinouUserService.saveUser(event.getUser());
    }
    event
      .deferReply(false)
      .queue(hook -> {
        EmbedUtil.sendProcessingEmbedWithHook(hook); // 명령어가 처리 중임을 알림
        try {
          // 명령어 처리 로직
          SlashCommands command = commandManager.getCommand(event.getName());
          command.execute(event, hook);
        } catch (CazinouException e) {
          handleException(event, e, "오류 발생", e.getMessage(), Color.RED);
        } catch (IllegalArgumentException e) {
          handleException(
            event,
            e,
            "잘못된 인자",
            e.getMessage(),
            Color.YELLOW
          );
        } catch (MethodArgumentTypeMismatchException e) {
          handleException(
            event,
            e,
            "잘못된 인자 타입",
            e.getMessage(),
            Color.YELLOW
          );
        } catch (ConstraintViolationException e) {
          handleException(
            event,
            e,
            "유효성 검증 실패",
            e.getMessage(),
            Color.ORANGE
          );
        } catch (RuntimeException e) {
          handleException(event, e, "런타임 오류", e.getMessage(), Color.RED);
        } catch (Exception e) {
          handleException(
            event,
            e,
            "서버 내부 오류",
            e.getMessage(),
            Color.RED
          );
        }
      });
  }

  private void handleException(
    SlashCommandInteractionEvent event,
    Exception e,
    String title,
    String description,
    Color color
  ) {
    log.error("예외 발생: {}", e.getMessage(), e);

    MessageModel messageModel = MessageModel.builder()
      .title(title)
      .description(description)
      .color(color)
      .build();

    EmbedUtil.sendEmbed(event, messageModel, true);
  }

  private Boolean differentChannelTopic(SlashCommandInteractionEvent e) {
    // 채널 정보 가져오기
    Channel channel = e.getChannel();
    if (channel instanceof TextChannel textChannel) {
      String topic = textChannel.getTopic();
      if (topic == null || !topic.trim().equals("#cazinou")) {
        String description = (topic == null)
          ? "채널 주제가 없습니다. 명령어를 입력하려면 채널 주제를 `#cazinou`로 추가해 주세요."
          : "채널 주제가 #cazinou가 아닙니다. 명령어를 입력하려면 채널 주제를 `#cazinou`로 변경해주세요.";

        MessageModel messageModel = MessageModel.builder()
          .title("채널 주제 오류")
          .description(description)
          .color(Color.RED)
          .build();

        // Embed 메시지 전송 (비공개 응답)
        EmbedUtil.sendEmbed(e, messageModel, true);
        return true;
      }
    }
    return false;
  }
}
