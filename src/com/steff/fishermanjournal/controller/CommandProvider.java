package com.steff.fishermanjournal.controller;

import java.util.HashMap;
import java.util.Map;

import com.steff.fishermanjournal.controller.impl.*;
import com.steff.fishermanjournal.logic.RecordLogic;

public class CommandProvider {
	private final Map<CommandName, Command> repository = new HashMap<>();

	public CommandProvider(RecordLogic recordService) {
		repository.put(CommandName.CREATE_RECORD, new CreateRecordCommand(recordService));
		repository.put(CommandName.CHANGE_STATUS, new ChangeStatusCommand(recordService));
		repository.put(CommandName.FIND_BY_FISHERMAN, new FindByFishermanCommand(recordService));
		repository.put(CommandName.FIND_BY_FISH, new FindByFishCommand(recordService));
		repository.put(CommandName.FIND_BY_WATERBODY, new FindByWaterBodyCommand(recordService));
		repository.put(CommandName.GET_STATS, new GetStatsCommand(recordService));
		repository.put(CommandName.SHOW_ACTIVE, new ShowActiveCommand(recordService));
		repository.put(CommandName.SHOW_ALL, new ShowAllCommand(recordService));
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