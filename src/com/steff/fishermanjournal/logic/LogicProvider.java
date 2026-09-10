package com.steff.fishermanjournal.logic;

import com.steff.fishermanjournal.logic.impl.RecordLogicImpl;

public final class LogicProvider {
    private static final LogicProvider instance = new LogicProvider();

    private LogicProvider() {}

    private RecordLogic logic = new RecordLogicImpl();

    public RecordLogic getRecordService() {
        return logic;
    }

    public static LogicProvider getInstance() {
        return instance;
    }
}