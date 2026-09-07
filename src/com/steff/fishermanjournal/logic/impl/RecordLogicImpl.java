package com.steff.fishermanjournal.logic.impl;

import com.steff.fishermanjournal.dao.DaoProvider;
import com.steff.fishermanjournal.dao.RecordDao;
import com.steff.fishermanjournal.dao.DaoException;
import com.steff.fishermanjournal.entity.CatchRecord;
import com.steff.fishermanjournal.entity.RecordStatus;
import com.steff.fishermanjournal.logic.RecordLogic;
import com.steff.fishermanjournal.logic.LogicException;

import java.util.ArrayList;
import java.util.List;

public class RecordLogicImpl implements RecordLogic {

    private final RecordDao recordDao = DaoProvider.getInstance().getRecordDao();

    private static final int MIN_WATER_BODY_LENGTH = 3;
    private static final int MAX_WATER_BODY_LENGTH = 50;

    private static final int MIN_QUANTITY = 1;
    private static final int MAX_QUANTITY = 100;

    private static final int MIN_WEIGHT = 100;
    private static final int MAX_WEIGHT = 30000;

    public RecordLogicImpl() {
    }

    @Override
    public CatchRecord createRecord(String fishermanName, String fishType, int quantity, int weight, String waterBody) throws LogicException {
        validate(fishermanName, fishType, quantity, weight, waterBody);

        CatchRecord newRecord = new CatchRecord(fishermanName, fishType, quantity, weight, waterBody);

        try {
            return recordDao.save(newRecord);
        } catch (DaoException e) {
            throw new LogicException("Failed to save the catch record.", e);
        }
    }

    @Override
    public void changeStatus(Long id, RecordStatus newStatus) throws LogicException {
        if (id == null) {
            throw new LogicException("Cannot change status: record ID is missing.");
        }
        if (newStatus == null) {
            throw new LogicException("Cannot change status: target status is missing.");
        }

        try {
            CatchRecord record = recordDao.findById(id);
            if (record == null) {
                throw new LogicException(String.format("Record with ID %d not found.", id));
            }

            RecordStatus currentStatus = record.getStatus();
            if (!isTransitionAllowed(currentStatus, newStatus)) {
                throw new LogicException(String.format("Transition from status %s to %s is not allowed.", currentStatus, newStatus));
            }

            record.setStatus(newStatus);
            recordDao.update(record);

        } catch (DaoException e) {
            throw new LogicException("Failed to update record status in the database.", e);
        }

    }

    @Override
    public List<CatchRecord> findByFisherman(String fisherman) throws LogicException {
        if (isInvalidString(fisherman)) {
            throw new LogicException("Search query for fisherman name cannot be empty.");
        }

        try {
            List<CatchRecord> allRecords = recordDao.findAll();
            List<CatchRecord> matchedRecords = new ArrayList<>();

            String searchLower = fisherman.toLowerCase();

            for (CatchRecord r : allRecords) {
                if (r.getFishermanName() != null && r.getFishermanName().toLowerCase().contains(searchLower)) {
                    matchedRecords.add(r);
                }
            }
            return matchedRecords;

        } catch (DaoException e) {
            throw new LogicException("Failed to search records by fisherman name.", e);
        }
    }

    @Override
    public List<CatchRecord> findByFish(String fish) throws LogicException {
        if (isInvalidString(fish)) {
            throw new LogicException("Search query for fish type cannot be empty.");
        }

        try {
            List<CatchRecord> allRecords = recordDao.findAll();
            List<CatchRecord> matchedRecords = new ArrayList<>();
            String searchLower = fish.toLowerCase();

            for (CatchRecord r : allRecords) {
                if (r.getFishType() != null && r.getFishType().toLowerCase().contains(searchLower)) {
                    matchedRecords.add(r);
                }
            }
            return matchedRecords;

        } catch (DaoException e) {
            throw new LogicException("Failed to search records by fish type.", e);
        }
    }

    @Override
    public List<CatchRecord> findByWaterBody(String waterBody) throws LogicException {
        if (isInvalidString(waterBody)) {
            throw new LogicException("Search query for water body cannot be empty.");
        }

        try {
            List<CatchRecord> allRecords = recordDao.findAll();
            List<CatchRecord> matchedRecords = new ArrayList<>();
            String searchLower = waterBody.toLowerCase();

            for (CatchRecord r : allRecords) {
                if (r.getWaterBody() != null && r.getWaterBody().toLowerCase().contains(searchLower)) {
                    matchedRecords.add(r);
                }
            }
            return matchedRecords;

        } catch (DaoException e) {
            throw new LogicException("Failed to search records by water body name.", e);
        }
    }

    @Override
    public List<CatchRecord> showAll() throws LogicException {
        try {
            return recordDao.findAll();
        } catch (DaoException e) {
            throw new LogicException("Failed to retrieve all records from the database.", e);
        }
    }

    @Override
    public List<CatchRecord> showActive() throws LogicException {
        try {
            List<CatchRecord> allRecords = recordDao.findAll();
            List<CatchRecord> activeRecords = new ArrayList<>();

            for (CatchRecord r : allRecords) {
                if (r.getStatus() != RecordStatus.ARCHIVED && r.getStatus() != RecordStatus.CANCELLED) {
                    activeRecords.add(r);
                }
            }

            return activeRecords;

        } catch (DaoException e) {
            throw new LogicException("Failed to retrieve active records from the database.", e);
        }
    }

    @Override
    public String getStats(String fisherman) throws LogicException {
        if (isInvalidString(fisherman)) {
            throw new LogicException("Fisherman name for statistics cannot be empty.");
        }

        try {
            String searchLower = fisherman.toLowerCase();
            List<CatchRecord> fishermanRecords = recordDao.findAll().stream().filter(r -> r.getFishermanName() != null && r.getFishermanName().toLowerCase().contains(searchLower)).toList();

            if (fishermanRecords.isEmpty()) {
                return String.format("Fisherman %s: no catch records found.", fisherman);
            }

            int totalCatches = fishermanRecords.size();

            int totalQuantity = fishermanRecords.stream().mapToInt(CatchRecord::getQuantity).sum();

            int totalWeight = fishermanRecords.stream().mapToInt(CatchRecord::getWeight).sum();

            java.util.Optional<CatchRecord> heaviestCatchOpt = fishermanRecords.stream().max(java.util.Comparator.comparingInt(CatchRecord::getWeight));

            String heaviestCatchInfo = "None";
            if (heaviestCatchOpt.isPresent()) {
                CatchRecord heaviest = heaviestCatchOpt.get();
                heaviestCatchInfo = String.format("%s (%d g)", heaviest.getFishType(), heaviest.getWeight());
            }

            return String.format("Fisherman %s: total catches — %d, fish caught — %d pcs., total weight — %d g, heaviest catch — %s.", fisherman, totalCatches, totalQuantity, totalWeight, heaviestCatchInfo);

        } catch (DaoException e) {
            throw new LogicException("Failed to calculate statistics.", e);
        }
    }

    private void validate(String fishermanName, String fishType, int quantity, int weight, String waterBody) throws LogicException {

        if (isInvalidString(fishermanName)) {
            throw new LogicException("The fisherman's name cannot be empty or consist only of spaces.");
        }

        if (isInvalidString(fishType)) {
            throw new LogicException("Fish type cannot be empty or consist only of spaces.");
        }

        if (isInvalidString(waterBody) || waterBody.length() < MIN_WATER_BODY_LENGTH || waterBody.length() > MAX_WATER_BODY_LENGTH) {

            throw new LogicException(String.format("The name of the water body must be between %d and %d characters inclusive.", MIN_WATER_BODY_LENGTH, MAX_WATER_BODY_LENGTH));
        }

        if (quantity < MIN_QUANTITY || quantity > MAX_QUANTITY) {
            throw new LogicException(String.format("The quantity of fish must be between %d and %d pieces inclusive.", MIN_QUANTITY, MAX_QUANTITY));
        }

        if (weight < MIN_WEIGHT || weight > MAX_WEIGHT) {
            throw new LogicException(String.format("The total weight of the catch must be between %d and %d grams inclusive.", MIN_WEIGHT, MAX_WEIGHT));
        }
    }

    private boolean isInvalidString(String value) {
        return value == null || value.isBlank();
    }

    private boolean isTransitionAllowed(RecordStatus current, RecordStatus next) {
        switch (current) {
            case NEW:
                return next == RecordStatus.VERIFIED || next == RecordStatus.CANCELLED;

            case VERIFIED:
                return next == RecordStatus.REGISTERED || next == RecordStatus.CANCELLED;

            case REGISTERED:
                return next == RecordStatus.ARCHIVED;

            case ARCHIVED:
            case CANCELLED:
            default:
                return false;
        }
    }
}
