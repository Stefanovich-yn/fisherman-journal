package com.steff.fishermanjournal.controller.impl;

import com.steff.fishermanjournal.controller.Command;
import com.steff.fishermanjournal.logic.RecordLogic;
import com.steff.fishermanjournal.logic.LogicException;

import java.util.HashMap;
import java.util.Map;

public class GetStatsCommand implements Command {

    private final RecordLogic recordService;

    public GetStatsCommand(RecordLogic recordService) {
        this.recordService = recordService;
    }

    @Override
    public String execute(String request) {
        try {
            Map<String, String> params = parseParameters(request);
            String fisherman = params.get("fisherman");

            String stats = recordService.getStats(fisherman);
            return "SUCCESS:\n" + stats;

        } catch (LogicException e) {
            return "ERROR: Missing required parameter 'fisherman'.";
        }
    }

    private Map<String, String> parseParameters(String request) {
        Map<String, String> params = new HashMap<>();
        String[] lines = request.split("\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split("=", 2);
            if (parts.length == 2) {
                params.put(parts[0].trim(), parts[1].trim());
            }
        }
        return params;
    }
}