package com.steff.fishermanjournal.controller;

import java.util.HashMap;
import java.util.Map;

import com.steff.fishermanjournal.controller.impl.*;

public class CommandProvider {
	private final Map<CommandName, Command> repository = new HashMap<>();

	public CommandProvider() {
		repository.put(CommandName.CREATE_RECORD, new CreateRecordCommand());
		repository.put(CommandName.CHANGE_STATUS, new ChangeStatusCommand());
		repository.put(CommandName.FIND_BY_FISHERMAN, new FindByFishermanCommand());
		repository.put(CommandName.FIND_BY_FISH, new FindByFishCommand());
		repository.put(CommandName.FIND_BY_WATERBODY, new FindByWaterBodyCommand());
		repository.put(CommandName.GET_STATS, new GetStatsCommand());
		repository.put(CommandName.SHOW_ACTIVE, new ShowActiveCommand());
		repository.put(CommandName.SHOW_ALL, new ShowAllCommand());
		repository.put(CommandName.WRONG_REQUEST, new NoSuchCommand());
	}

	public Command getCommand(String name) {
		CommandName commandName = null;
		Command command = null;

		try {
			commandName = CommandName.valueOf(name.toUpperCase());
			command = repository.get(commandName);
		} catch (IllegalArgumentException | NullPointerException e) {
			command = repository.get(CommandName.WRONG_REQUEST);
		}
		return command;
	}
}