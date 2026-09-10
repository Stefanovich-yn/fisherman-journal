package com.steff.fishermanjournal.controller.impl;

import com.steff.fishermanjournal.controller.Command;
import com.steff.fishermanjournal.entity.CatchRecord;
import com.steff.fishermanjournal.logic.RecordLogic;
import com.steff.fishermanjournal.logic.LogicException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindByFishCommand implements Command {

    private final RecordLogic recordService;

    public FindByFishCommand(RecordLogic recordService) {
        this.recordService = recordService;
    }

    @Override
    public String execute(String request) {
        try {
            Map<String, String> params = parseParameters(request);
            String fish = params.get("fish");

            List<CatchRecord> records = recordService.findByFish(fish);
            return formatResult(records);

        } catch (LogicException e) {
            return "ERROR: Missing required parameter 'fish'.";
        }
    }

    private String formatResult(List<CatchRecord> records) {
        if (records.isEmpty()) {
            return "SUCCESS: No records found.";
        }
        StringBuilder sb = new StringBuilder("SUCCESS: Found " + records.size() + " record(s):\n");
        for (CatchRecord r : records) {
            sb.append(r.toString()).append("\n");
        }
        return sb.toString().trim();
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