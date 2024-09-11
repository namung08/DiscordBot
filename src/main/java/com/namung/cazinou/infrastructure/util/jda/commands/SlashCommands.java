package com.namung.cazinou.infrastructure.util.jda.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface SlashCommands {
  void execute(SlashCommandInteractionEvent e);
}

class HelloCommand implements SlashCommands {

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    String name = event.getOption("name").getAsString();
    event.reply("Hello, " + name + "!").queue();
  }
}

class GoodbyeCommand implements SlashCommands {

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    event.reply("Goodbye!").queue();
  }
}

class UnknownCommand implements SlashCommands {

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    event.reply("Unknown command: " + event.getName()).queue();
  }
}
