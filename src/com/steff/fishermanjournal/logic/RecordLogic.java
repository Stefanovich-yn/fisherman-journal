package com.steff.fishermanjournal.logic;

import com.steff.fishermanjournal.entity.CatchRecord;
import com.steff.fishermanjournal.entity.RecordStatus;

import java.util.List;

public interface RecordLogic {

	CatchRecord createRecord(String fisherman, String fish, int quantity, int weight, String waterBody)
			throws LogicException;

	void changeStatus(Long id, RecordStatus newStatus) throws LogicException;

	List<CatchRecord> findByFisherman(String fisherman) throws LogicException;

	List<CatchRecord> findByFish(String fish) throws LogicException;

	List<CatchRecord> findByWaterBody(String waterBody) throws LogicException;

	String getStats(String fisherman) throws LogicException;

	List<CatchRecord> showActive() throws LogicException;

	List<CatchRecord> showAll() throws LogicException;

}