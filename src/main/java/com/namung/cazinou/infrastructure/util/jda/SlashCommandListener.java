package com.namung.cazinou.infrastructure.util.jda;

import com.namung.cazinou.infrastructure.util.jda.commands.CommandManager;
import com.namung.cazinou.infrastructure.util.jda.commands.SlashCommands;
import com.namung.cazinou.user.service.CazinouUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDA;
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
    // 명령어를 입력한 사용자의 정보를 MongoDB에 저장을 해야함
    if (!cazinouUserService.isUserExist(event.getUser())) {
      cazinouUserService.saveUser(event.getUser());
    }
    // 명령어를 핸들러로 매핑하여 실행
    SlashCommands command = commandManager.getCommand(event.getName());

    command.execute(event);
  }
}
