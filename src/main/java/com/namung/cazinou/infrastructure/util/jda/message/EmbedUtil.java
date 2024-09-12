package com.namung.cazinou.infrastructure.util.jda.message;

import java.awt.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

public class EmbedUtil {

  public static EmbedBuilder createEmbedFromMessageModel(
    MessageModel messageModel
  ) {
    // EmbedBuilder 인스턴스 생성
    EmbedBuilder embedBuilder = new EmbedBuilder();

    // 타이틀과 타이틀 URL 설정
    if (messageModel.getTitle() != null) {
      if (messageModel.getTitleUrl() != null) {
        embedBuilder.setTitle(
          messageModel.getTitle(),
          messageModel.getTitleUrl()
        );
      } else {
        embedBuilder.setTitle(messageModel.getTitle());
      }
    }

    // 설명 추가
    if (messageModel.getDescription() != null) {
      embedBuilder.setDescription(messageModel.getDescription());
    }

    // 색상 설정
    if (messageModel.getColor() != null) {
      embedBuilder.setColor(messageModel.getColor());
    }

    // 필드 추가 (인라인 여부에 따라)
    if (
      messageModel.getFieldTitle() != null &&
      messageModel.getFieldDescription() != null
    ) {
      embedBuilder.addField(
        messageModel.getFieldTitle(),
        messageModel.getFieldDescription(),
        messageModel.isInline()
      );
    }

    // 푸터 설정
    if (messageModel.getFooterTitle() != null) {
      if (messageModel.getFooterUrl() != null) {
        embedBuilder.setFooter(
          messageModel.getFooterTitle(),
          messageModel.getFooterUrl()
        );
      } else {
        embedBuilder.setFooter(messageModel.getFooterTitle());
      }
    }

    // 작성자 설정
    if (messageModel.getAuthor() != null) {
      if (
        messageModel.getAuthorUrl() != null &&
        messageModel.getAuthorImageUrl() != null
      ) {
        embedBuilder.setAuthor(
          messageModel.getAuthor(),
          messageModel.getAuthorUrl(),
          messageModel.getAuthorImageUrl()
        );
      } else if (messageModel.getAuthorUrl() != null) {
        embedBuilder.setAuthor(
          messageModel.getAuthor(),
          messageModel.getAuthorUrl()
        );
      } else {
        embedBuilder.setAuthor(messageModel.getAuthor());
      }
    }

    // 썸네일 추가
    if (messageModel.getThumbnailUrl() != null) {
      embedBuilder.setThumbnail(messageModel.getThumbnailUrl());
    }

    // 이미지 추가
    if (messageModel.getImageUrl() != null) {
      embedBuilder.setImage(messageModel.getImageUrl());
    }

    return embedBuilder;
  }

  // 예시로 사용될 메서드 (SlashCommandInteractionEvent로 임베드 전송)
  public static void sendEmbed(
    SlashCommandInteractionEvent event,
    MessageModel messageModel,
    Boolean isEphemeral
  ) {
    EmbedBuilder embed = createEmbedFromMessageModel(messageModel);
    embed.setFooter(
      event.getMember().getEffectiveName(),
      event.getUser().getEffectiveAvatarUrl()
    );
    event.replyEmbeds(embed.build()).setEphemeral(isEphemeral).queue();
  }

  // InteractionHook을 통해 임베드 전송
  public static void sendProcessingEmbedWithHook(InteractionHook hook) {
    // 처리 중 메시지 전송
    MessageModel loadingEmbed = MessageModel.builder()
      .title("명령어 처리 중")
      .description("명령어가 처리되고 있습니다. 잠시만 기다려 주세요.")
      .color(Color.YELLOW)
      .build();
    hook
      .sendMessageEmbeds(createEmbedFromMessageModel(loadingEmbed).build())
      .setEphemeral(true)
      .queue();
  }

  // InteractionHook을 통해 임베드 수정
  public static void sendSuccessEmbedWithHook(
    InteractionHook hook,
    MessageModel messageModel
  ) {
    EmbedBuilder embedBuilder = createEmbedFromMessageModel(messageModel);
    hook.editOriginalEmbeds(embedBuilder.build()).queue();
  }
}
