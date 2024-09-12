package com.namung.cazinou.infrastructure.util.jda.commands;

import com.namung.cazinou.user.service.CazinouUserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

@Component
public class CommandManager {

  private final Map<String, SlashCommands> commandMap = new HashMap<>();

  public CommandManager(CazinouUserService cazinouUserService) {
    // 명령어와 핸들러 등록
    commandMap.put("출석체크", new GiveMoneyCommand(cazinouUserService));
    commandMap.put("잔액확인", new CheckBalanceCommand(cazinouUserService));
    commandMap.put("개발자", new GetBotCraterGinHubCommand());
  }

  public SlashCommands getCommand(String commandName) {
    return commandMap.getOrDefault(commandName, new UnknownCommand());
  }

  public List<CommandData> getCommandDataList() {
    // 돈줘
    CommandData giveMoneyCommand = Commands.slash(
      "출석체크",
      "출석체크를 합니다."
    );

    CommandData checkBalanceCommand = Commands.slash(
      "잔액확인",
      "잔액을 확인합니다."
    );

    CommandData botCraterInfoCommand = Commands.slash(
      "개발자",
      "개발자 정보를 확인합니다."
    );

    return List.of(giveMoneyCommand, checkBalanceCommand, botCraterInfoCommand);
  }
}
