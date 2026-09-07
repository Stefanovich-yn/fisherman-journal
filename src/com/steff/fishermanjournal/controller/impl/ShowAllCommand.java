package com.steff.fishermanjournal.controller.impl;

import com.steff.fishermanjournal.controller.Command;
import com.steff.fishermanjournal.entity.CatchRecord;
import com.steff.fishermanjournal.logic.RecordLogic;
import com.steff.fishermanjournal.logic.LogicException;

import java.util.List;

public class ShowAllCommand implements Command {

    private final RecordLogic recordLogic;

    public ShowAllCommand(RecordLogic recordLogic) {
        this.recordLogic = recordLogic;
    }

    @Override
    public String execute(String request) {
        try {
            List<CatchRecord> records = recordLogic.showAll();
            if (records.isEmpty()) {
                return "SUCCESS: Journal is empty.";
            }

            StringBuilder sb = new StringBuilder("SUCCESS: Journal contains " + records.size() + " record(s):\n");
            for (CatchRecord r : records) {
                sb.append(r.toString()).append("\n");
            }
            return sb.toString().trim();

        } catch (LogicException e) {
            return "ERROR: " + e.getMessage();
        }
    }
}