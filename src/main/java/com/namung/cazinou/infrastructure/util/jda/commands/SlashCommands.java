package com.namung.cazinou.infrastructure.util.jda.commands;

import com.namung.cazinou.infrastructure.util.jda.message.EmbedUtil;
import com.namung.cazinou.infrastructure.util.jda.message.MessageModel;
import com.namung.cazinou.user.service.CazinouUserService;
import java.awt.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface SlashCommands {
  void execute(SlashCommandInteractionEvent e);
}

@RequiredArgsConstructor
class GiveMoneyCommand implements SlashCommands {

  private final CazinouUserService cazinouUserService;

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    MessageModel messageModel = MessageModel.builder()
      .title("출석 체크")
      .description(cazinouUserService.attendanceCheck(event.getUser()))
      .build();
    EmbedUtil.sendEmbed(event, messageModel, false);
  }
}

@Log4j2
@RequiredArgsConstructor
class CheckBalanceCommand implements SlashCommands {

  private final CazinouUserService cazinouUserService;

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    MessageModel messageModel = MessageModel.builder()
      .color(Color.CYAN)
      .title("잔액 확인")
      .description(
        event.getMember().getEffectiveName() +
        "님의 잔액은: " +
        cazinouUserService.getUserBalance(event.getUser()) +
        "원 입니다."
      )
      .build();
    EmbedUtil.sendEmbed(event, messageModel, false);
  }
}

@RequiredArgsConstructor
class GetBotCraterGinHubCommand implements SlashCommands {

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    MessageModel messageModel = MessageModel.builder()
      .color(Color.CYAN)
      .author("namung08")
      .authorUrl("https://github.com/namung08")
      .authorImageUrl("https://avatars.githubusercontent.com/u/113490780?v=4")
      .build();
    EmbedUtil.sendEmbed(event, messageModel, false);
  }
}

class UnknownCommand implements SlashCommands {

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    MessageModel messageModel = MessageModel.builder()
      .title("알 수 없는 명령어")
      .description(event.getName() + "이 명령어는 존재하지 않는 명령어 입니다.")
      .build();
    EmbedUtil.sendEmbed(event, messageModel, false);
  }
}
