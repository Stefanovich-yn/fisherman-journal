package com.steff.fishermanjournal.controller;

import com.steff.fishermanjournal.logic.RecordLogic;

public class Controller {
	private final char paramDelimiter = '\n';

	private final CommandProvider provider = new CommandProvider();


	public String doAction(String request) {
		try {
			if (request == null || request.isBlank()) {
				return provider.getCommand("WRONG_REQUEST").execute(request);
			}

			String commandName;
			int delimiterIndex = request.indexOf(paramDelimiter);

			if (delimiterIndex == -1) {
				commandName = request.trim();
			} else {
				commandName = request.substring(0, delimiterIndex).trim();
			}

			Command executionCommand = provider.getCommand(commandName);
			return executionCommand.execute(request);

		} catch (Exception e) {
			return "ERROR: Critical controller error. " + e.getMessage();
		}
	}
}
