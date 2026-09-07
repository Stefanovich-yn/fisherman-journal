package com.steff.fishermanjournal.dao;

import com.steff.fishermanjournal.dao.impl.FileRecordDao;

public final class DaoProvider {
    private static final DaoProvider INSTANCE = new DaoProvider();

    private DaoProvider() {}

    private RecordDao recordDao = new FileRecordDao();

    public RecordDao getRecordDao() {
        return recordDao;
    }

    public static DaoProvider getInstance() {
        return INSTANCE;
    }
}