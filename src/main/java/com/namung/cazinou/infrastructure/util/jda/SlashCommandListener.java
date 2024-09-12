package com.namung.cazinou.infrastructure.util.jda;

import com.namung.cazinou.infrastructure.util.jda.commands.CommandManager;
import com.namung.cazinou.infrastructure.util.jda.commands.SlashCommands;
import com.namung.cazinou.infrastructure.util.jda.message.EmbedUtil;
import com.namung.cazinou.infrastructure.util.jda.message.MessageModel;
import com.namung.cazinou.user.service.CazinouUserService;
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
    // 명령어가 입력이 된 체널의 주제가 #cazinou인지 확인
    if (differentChannelTopic(event)) {
      return;
    }
    // 명령어를 입력한 사용자의 정보를 MongoDB에 저장을 해야함
    if (!cazinouUserService.isUserExist(event.getUser())) {
      cazinouUserService.saveUser(event.getUser());
    }
    // 명령어를 핸들러로 매핑하여 실행
    SlashCommands command = commandManager.getCommand(event.getName());

    command.execute(event);
  }

  private Boolean differentChannelTopic(SlashCommandInteractionEvent e) {
    // 채널 정보 가져오기
    Channel channel = e.getChannel();
    if (channel instanceof TextChannel textChannel) {
      // 채널 주제가 없는 경우 처리
      if (textChannel.getTopic() == null) {
        // 이미 응답했는지 확인
        MessageModel messageModel = MessageModel.builder()
          .title("채널 주제가 없습니다.")
          .description(
            "명령어를 입력하려면 채널 주제를 `#cazinou`를 추가해 주세요."
          )
          .color(Color.RED)
          .build();
        // Embed 메시지 전송 (비공개 응답)
        EmbedUtil.sendEmbed(e, messageModel, true);
        return true;
      }

      // 채널 주제가 `#cazinou`가 아닌 경우 처리
      if (!textChannel.getTopic().trim().equals("#cazinou")) {
        // 이미 응답했는지 확인
        MessageModel messageModel = MessageModel.builder()
          .title("채널 주제가 #cazinou가 아닙니다.")
          .description(
            "명령어를 입력하려면 채널 주제를 `#cazinou`로 변경해주세요."
          )
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
