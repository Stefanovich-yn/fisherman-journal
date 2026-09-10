package com.steff.fishermanjournal.controller;

import com.steff.fishermanjournal.logic.RecordLogic;

public class Controller {
    private final char paramDelimiter = '\n';
    private final CommandProvider provider = new CommandProvider();

    public String doAction(String request) {
        String response;
        try {
            String commandName = getCommandName(request);

            Command executionCommand = provider.getCommand(commandName);
            response = executionCommand.execute(request);

        } catch (Exception e) {
            response = formatErrorMessage("An unexpected error occurred while processing the request", e);
        }
        return response;
    }


    private String getCommandName(String request) {
        if (request == null || request.isBlank()) {
            return "WRONG_REQUEST";
        }

        String commandName;
        int delimiterIndex = request.indexOf(paramDelimiter);

        if (delimiterIndex == -1) {
            commandName = request.trim();
        } else {
            commandName = request.substring(0, delimiterIndex).trim();
        }

        return commandName;
    }

    private String formatErrorMessage(String message, Exception e) {
        String errorDetail = (e.getMessage() != null && !e.getMessage().isBlank())
                ? e.getMessage()
                : "no technical details available";

        return String.format("ERROR: %s (Details: %s).", message, errorDetail);
    }
}