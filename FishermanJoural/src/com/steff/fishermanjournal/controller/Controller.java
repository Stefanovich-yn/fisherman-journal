package com.steff.fishermanjournal.controller;

public class Controller {
	private final char paramDelimiter = '\n';
	private final CommandProvider provider = new CommandProvider();

	public String doAction(String request) {
		if (request == null || request.isBlank() || request.indexOf(paramDelimiter) == -1) {
			return provider.getCommand("WRONG_REQUEST").execute(request);
		}

		String commandName;
		commandName = request.substring(0, request.indexOf(paramDelimiter)).trim();

		Command executionCommand;
		executionCommand = provider.getCommand(commandName);

		String response;
		response = executionCommand.execute(request);

		return response;
	}
}