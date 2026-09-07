package com.steff.fishermanjournal.controller.impl;

import com.steff.fishermanjournal.controller.Command;

public class NoSuchCommand implements Command {
    @Override
    public String execute(String request) {
        return "ERROR: Unknown command or invalid request format.";
    }
}