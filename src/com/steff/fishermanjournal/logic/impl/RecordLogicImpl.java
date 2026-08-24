package com.steff.fishermanjournal.logic.impl;

import com.steff.fishermanjournal.dao.RecordDao;
import com.steff.fishermanjournal.dao.DaoException;
import com.steff.fishermanjournal.entity.CatchRecord;
import com.steff.fishermanjournal.entity.RecordStatus;
import com.steff.fishermanjournal.logic.RecordLogic;
import com.steff.fishermanjournal.logic.LogicException;

import java.util.ArrayList;
import java.util.List;

public class RecordLogicImpl implements RecordLogic {

	private final RecordDao recordDao;

	public RecordLogicImpl(RecordDao recordDao) {
		this.recordDao = recordDao;
	}

	private void validate(String fishermanName, String fishType, int quantity, int weight, String waterBody)
			throws LogicException {
		if (fishermanName == null || fishermanName.isBlank()) {
			throw new LogicException("The fisherman's name cannot be empty or consist only of spaces.");
		}
		if (fishType == null || fishType.isBlank()) {
			throw new LogicException("Fish type cannot be empty or consist only of spaces.");
		}
		if (waterBody == null || waterBody.isBlank() || waterBody.length() < 3 || waterBody.length() > 50) {
			throw new LogicException("The name of the water body must be from 3 to 50 characters inclusive.");
		}
		if (quantity < 1 || quantity > 100) {
			throw new LogicException("The quantity of fish should be in the range from 1 to 100 pieces inclusive.");
		}
		if (weight < 100 || weight > 30000) {
			throw new LogicException(
					"The total weight of the catch must be between 100 and 30,000 grams inclusive.");
		}
	}

	@Override
	public CatchRecord createRecord(String fishermanName, String fishType, int quantity, int weight, String waterBody)
			throws LogicException {
		validate(fishermanName, fishType, quantity, weight, waterBody);

		CatchRecord newRecord = new CatchRecord(fishermanName, fishType, quantity, weight, waterBody);

		try {
			return recordDao.save(newRecord);
		} catch (DaoException e) {
			throw new LogicException("Не удалось сохранить запись об улове.", e);
		}
	}

	@Override
	public void changeStatus(Long id, RecordStatus newStatus) throws LogicException {

	}

	@Override
	public List<CatchRecord> findByFisherman(String fishermanName) throws LogicException {

		return new ArrayList<>();
	}

	@Override
	public List<CatchRecord> findByFish(String fishType) throws LogicException {

		return new ArrayList<>();
	}

	@Override
	public List<CatchRecord> findByWaterBody(String waterBody) throws LogicException {

		return new ArrayList<>();
	}

	@Override
	public List<CatchRecord> showActive() throws LogicException {

		return new ArrayList<>();
	}

	@Override
	public List<CatchRecord> showAll() throws LogicException {

		return new ArrayList<>();
	}

	@Override
	public String getStats(String fishermanName) throws LogicException {

		return "";
	}
}
