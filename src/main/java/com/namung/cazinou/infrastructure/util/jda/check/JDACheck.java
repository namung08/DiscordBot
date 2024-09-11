package com.namung.cazinou.infrastructure.util.jda.check;

import com.namung.cazinou.infrastructure.exception.CazinouException;
import com.namung.cazinou.infrastructure.exception.CazinouExceptionCode;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.http.HttpStatus;

public class JDACheck {

  private static void checkBot(MessageReceivedEvent e) {
    if (e.getAuthor().isBot()) {
      throw new CazinouException(
        HttpStatus.BAD_REQUEST,
        CazinouExceptionCode.CAZINOU_MEMBER_IS_BOT
      );
    }
  }
}
