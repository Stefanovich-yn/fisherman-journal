package com.steff.fishermanjournal.controller;

import java.util.HashMap;
import java.util.Map;

import com.steff.fishermanjournal.controller.impl.*;
import com.steff.fishermanjournal.logic.RecordLogic;

public class CommandProvider {
	private final Map<CommandName, Command> repository = new HashMap<>();

	public CommandProvider(RecordLogic recordLogic) {
		repository.put(CommandName.CREATE_RECORD, new CreateRecordCommand(recordLogic));
		repository.put(CommandName.CHANGE_STATUS, new ChangeStatusCommand(recordLogic));
		repository.put(CommandName.FIND_BY_FISHERMAN, new FindByFishermanCommand(recordLogic));
		repository.put(CommandName.FIND_BY_FISH, new FindByFishCommand(recordLogic));
		repository.put(CommandName.FIND_BY_WATERBODY, new FindByWaterBodyCommand(recordLogic));
		repository.put(CommandName.GET_STATS, new GetStatsCommand(recordLogic));
		repository.put(CommandName.SHOW_ACTIVE, new ShowActiveCommand(recordLogic));
		repository.put(CommandName.SHOW_ALL, new ShowAllCommand(recordLogic));
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