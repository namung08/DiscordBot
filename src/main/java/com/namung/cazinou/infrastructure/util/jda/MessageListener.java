package com.namung.cazinou.infrastructure.util.jda;

import com.namung.cazinou.infrastructure.exception.CazinouException;
import com.namung.cazinou.infrastructure.exception.CazinouExceptionCode;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MessageListener extends ListenerAdapter {

  // 특정 서버의 id
  private static final String TARGET_CHANNEL_ID = "1282954141634793526";

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    checkBot(event);
    if (!checkChannel(event)) {
      return;
    }
  }

  private void checkBot(MessageReceivedEvent e) {
    if (e.getAuthor().isBot()) {
      throw new CazinouException(
        HttpStatus.BAD_REQUEST,
        CazinouExceptionCode.CAZINOU_MEMBER_IS_BOT
      );
    }
  }

  private boolean checkChannel(MessageReceivedEvent e) {
    if (!e.getChannel().getId().equals(TARGET_CHANNEL_ID)) {
      log.warn(
        "This message was sent from a different channel: {}",
        e.getChannel().getName()
      );
      return false;
    }
    return true;
  }

  private void sendMessage(MessageReceivedEvent event, String message) {
    event.getChannel().sendMessage(message).queue();
  }
}
