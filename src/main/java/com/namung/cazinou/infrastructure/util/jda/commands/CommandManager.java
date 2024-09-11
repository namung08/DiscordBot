package com.namung.cazinou.infrastructure.util.jda.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;

@Component
public class CommandManager {

  private final Map<String, SlashCommands> commandMap = new HashMap<>();

  public CommandManager() {
    // 명령어와 핸들러 등록
    commandMap.put("hello", new HelloCommand());
    commandMap.put("goodbye", new GoodbyeCommand());
  }

  public SlashCommands getCommand(String commandName) {
    return commandMap.getOrDefault(commandName, new UnknownCommand());
  }

  public List<CommandData> getCommandDataList() {
    // 명령어 등록 (hello 명령어 등)
    CommandData helloCommand = Commands.slash(
      "hello",
      "Say hello to a user."
    ).addOptions(
      new OptionData(
        OptionType.STRING,
        "name",
        "The name of the person to greet"
      ).setRequired(true)
    );

    CommandData goodbyeCommand = Commands.slash("goodbye", "Say goodbye.");

    return List.of(helloCommand, goodbyeCommand);
  }
}
