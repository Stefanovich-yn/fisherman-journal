package com.steff.fishermanjournal.dao.impl;

import com.steff.fishermanjournal.dao.DaoException;
import com.steff.fishermanjournal.dao.RecordDao;
import com.steff.fishermanjournal.entity.CatchRecord;

import java.util.ArrayList;
import java.util.List;

public class FileRecordDao implements RecordDao {

	private static final String FILE_PATH = "catch_records.txt";

	private String recordToString(CatchRecord record) {
		
		return null;
	}

	private CatchRecord stringToRecord(String line) {

		return null;
	}

	@Override
	public CatchRecord save(CatchRecord record) throws DaoException {

		return record;
	}

	@Override
	public void update(CatchRecord record) throws DaoException {

	}

	@Override
	public CatchRecord findById(Long id) throws DaoException {

		return null;
	}

	@Override
	public List<CatchRecord> findAll() throws DaoException {

		return new ArrayList<>();
	}
}
