package com.namung.cazinou.infrastructure.util.jda.commands;

import com.namung.cazinou.infrastructure.util.jda.message.EmbedUtil;
import com.namung.cazinou.infrastructure.util.jda.message.MessageModel;
import com.namung.cazinou.infrastructure.util.jda.message.WorkMessage;
import com.namung.cazinou.user.service.CazinouUserService;
import java.awt.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

public interface SlashCommands {
  void execute(SlashCommandInteractionEvent e, InteractionHook h);
}

@RequiredArgsConstructor
class GiveMoneyCommand implements SlashCommands {

  private final CazinouUserService cazinouUserService;

  @Override
  public void execute(SlashCommandInteractionEvent event, InteractionHook h) {
    MessageModel messageModel = MessageModel.builder()
      .title("출석 체크")
      .description(cazinouUserService.attendanceCheck(event.getUser()))
      .build();
    EmbedUtil.sendSuccessEmbedWithHook(h, messageModel);
  }
}

@Log4j2
@RequiredArgsConstructor
class CheckBalanceCommand implements SlashCommands {

  private final CazinouUserService cazinouUserService;

  @Override
  public void execute(SlashCommandInteractionEvent event, InteractionHook h) {
    MessageModel messageModel = MessageModel.builder()
      .color(Color.CYAN)
      .title("잔액 확인")
      .description(
        event.getMember().getEffectiveName() +
        "님의 잔액은 " +
        cazinouUserService.getUserBalance(event.getUser()) +
        "원 입니다."
      )
      .build();
    EmbedUtil.sendSuccessEmbedWithHook(h, messageModel);
  }
}

@RequiredArgsConstructor
class GetBotCraterGinHubCommand implements SlashCommands {

  @Override
  public void execute(SlashCommandInteractionEvent event, InteractionHook h) {
    MessageModel messageModel = MessageModel.builder()
      .color(Color.CYAN)
      .author("namung08")
      .authorUrl("https://github.com/namung08")
      .authorImageUrl("https://avatars.githubusercontent.com/u/113490780?v=4")
      .build();
    EmbedUtil.sendSuccessEmbedWithHook(h, messageModel);
  }
}

@RequiredArgsConstructor
class WorkCommand implements SlashCommands {

  private final CazinouUserService service;

  @Override
  public void execute(SlashCommandInteractionEvent e, InteractionHook h) {
    long lowSuccess = 20L;
    long success = (long) (Math.random() * 40);
    EmbedUtil.sendSuccessEmbedWithHook(
      h,
      MessageModel.builder()
        .title("성공 확률: " + lowSuccess + success + "%")
        .description("돈을 벌고 있습니다. 10초 후에 결과가 나옵니다.")
        .color(Color.YELLOW)
        .build()
    );
    long rerole = (long) (Math.random() * 100);
    if (rerole > success) {
      EmbedUtil.sendSuccessEmbedWithHook(
        h,
        MessageModel.builder()
          .title("실패")
          .description("돈을 벌지 못했습니다.")
          .color(Color.RED)
          .build()
      );
      return;
    }
    // 10초 지연 후 돈 지급
    CompletableFuture.runAsync(
      () -> {
        // 실제 돈을 지급하는 로직
        Integer result = service.work(e.getUser());
        MessageModel resultMessage = WorkMessage.generateWorkRewards()
          .get(result);
        EmbedUtil.sendSuccessEmbedWithHook(h, resultMessage);
      },
      CompletableFuture.delayedExecutor(10, TimeUnit.SECONDS)
    );
  }
}

class UnknownCommand implements SlashCommands {

  @Override
  public void execute(SlashCommandInteractionEvent event, InteractionHook h) {
    MessageModel messageModel = MessageModel.builder()
      .title("알 수 없는 명령어")
      .description(event.getName() + "이 명령어는 존재하지 않는 명령어 입니다.")
      .build();
    EmbedUtil.sendSuccessEmbedWithHook(h, messageModel);
  }
}
