package com.steff.fishermanjournal.controller;

public interface Command {
    String execute(String request);
}