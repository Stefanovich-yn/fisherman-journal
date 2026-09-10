package com.steff.fishermanjournal.controller.impl;

import com.steff.fishermanjournal.controller.Command;
import com.steff.fishermanjournal.entity.CatchRecord;
import com.steff.fishermanjournal.logic.RecordLogic;
import com.steff.fishermanjournal.logic.LogicException;

import java.util.HashMap;
import java.util.Map;

public class CreateRecordCommand implements Command {
    private final RecordLogic recordLogic;

    public CreateRecordCommand(RecordLogic recordLogic) {
        this.recordLogic = recordLogic;
    }

    @Override
    public String execute(String request) {
        try {
            Map<String, String> params = parseParameters(request);

            String fisherman = params.get("fisherman");
            String fish = params.get("fish");

            int quantity = parseInteger(params.get("quantity"), "quantity");
            int weight = parseInteger(params.get("weight"), "weight");

            String waterBody = params.get("waterBody");

            CatchRecord createdRecord = recordLogic.createRecord(fisherman, fish, quantity, weight, waterBody);

            return "SUCCESS: Record created successfully. " + createdRecord.toString();

        } catch (LogicException e) {
            String reason = (e.getMessage() != null) ? e.getMessage() : "validation failed";
            return "ERROR: Failed to create record. Reason: " + reason;

        } catch (IllegalArgumentException e) {
            return "ERROR: Invalid parameter format. Reason: " + e.getMessage();
        }
    }

    private Map<String, String> parseParameters(String request) {
        Map<String, String> params = new HashMap<>();
        String[] lines = request.split("\n");

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split("=", 2);
            if (parts.length == 2) {
                params.put(parts[0].trim(), parts[1].trim());
            }
        }
        return params;
    }

    private int parseInteger(String value, String paramName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Parameter '" + paramName + "' is missing.");
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Parameter '" + paramName + "' must be a valid number.");
        }
    }
}
