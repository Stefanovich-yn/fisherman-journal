package com.steff.fishermanjournal.controller.impl;

import com.steff.fishermanjournal.controller.Command;
import com.steff.fishermanjournal.entity.RecordStatus;
import com.steff.fishermanjournal.logic.RecordLogic;
import com.steff.fishermanjournal.logic.LogicException;

import java.util.HashMap;
import java.util.Map;

public class ChangeStatusCommand implements Command {

    private final RecordLogic recordService;

    public ChangeStatusCommand(RecordLogic recordService) {
        this.recordService = recordService;
    }

    @Override
    public String execute(String request) {
        try {
            Map<String, String> params = parseParameters(request);

            Long id = parseLong(params.get("id"), "id");
            String statusStr = params.get("status");

            if (statusStr == null || statusStr.isBlank()) {
                throw new IllegalArgumentException("Parameter 'status' is missing.");
            }

            // Переводим строковый статус в Enum (с защитой от неправильного ввода)
            RecordStatus status;
            try {
                status = RecordStatus.valueOf(statusStr.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unknown status: " + statusStr);
            }

            // Вызываем логику обновления
            recordService.changeStatus(id, status);
            return "SUCCESS: Status updated successfully for record ID " + id;

        } catch (LogicException e) {
            return "ERROR: " + e.getMessage();
        } catch (IllegalArgumentException e) {
            return "ERROR: Invalid parameter format. " + e.getMessage();
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

    private Long parseLong(String value, String paramName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Parameter '" + paramName + "' is missing.");
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Parameter '" + paramName + "' must be a valid number.");
        }
    }
}