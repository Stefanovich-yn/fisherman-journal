package com.steff.fishermanjournal.dao.impl;

import com.steff.fishermanjournal.dao.DaoException;
import com.steff.fishermanjournal.dao.RecordDao;
import com.steff.fishermanjournal.entity.CatchRecord;
import com.steff.fishermanjournal.entity.RecordStatus;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileRecordDao implements RecordDao {

    private static final String DELIMITER = ";";
    private static final String FILE_PATH = "catch_records.txt";

    @Override
    public CatchRecord save(CatchRecord record) throws DaoException {
        List<CatchRecord> existingRecords = findAll();

        record.setId(generateNextId(existingRecords));

        record.setStatus(RecordStatus.NEW);
        record.setCreatedAt(LocalDateTime.now());

        File file = new File(FILE_PATH);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            String recordLine = recordToString(record);
            writer.write(recordLine);
            writer.newLine();
        } catch (IOException e) {
            throw new DaoException("Failed to save the record to the file: " + FILE_PATH, e);
        }

        return record;
    }



    @Override
    public void update(CatchRecord recordToUpdate) throws DaoException {
        if (recordToUpdate == null || recordToUpdate.getId() == null) {
            throw new DaoException("Unable to update the record: ID is missing.");
        }

        List<CatchRecord> records = findAll();
        boolean isFound = false;

        for (int i = 0; i < records.size(); i++) {
            CatchRecord existingRecord = records.get(i);

            if (existingRecord.getId().equals(recordToUpdate.getId())) {
                records.set(i, recordToUpdate);
                isFound = true;
                break;
            }
        }

        if (!isFound) {
            throw new DaoException("Record with ID " + recordToUpdate.getId() + " not found for update.");
        }

        File file = new File(FILE_PATH);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {

            for (CatchRecord r : records) {
                String recordLine = recordToString(r);
                writer.write(recordLine);
                writer.newLine();
            }

        } catch (IOException e) {
            throw new DaoException("Failed to update the database file: " + FILE_PATH, e);
        }
    }

    @Override
    public List<CatchRecord> findAll() throws DaoException {
        List<CatchRecord> records = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return records;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                CatchRecord record = stringToRecord(line);

                records.add(record);
            }

        } catch (IOException e) {
            throw new DaoException("Failed to read data from the file: " + FILE_PATH, e);
        }

        return records;
    }

    @Override
    public CatchRecord findById(Long id) throws DaoException {
        if (id == null) {
            return null;
        }

        List<CatchRecord> records = findAll();

        for (CatchRecord record : records) {

            if (record.getId().equals(id)) {
                return record;
            }
        }

        return null;
    }

    private long generateNextId(List<CatchRecord> existingRecords) {
        return existingRecords.stream()
                .mapToLong(CatchRecord::getId)
                .max()
                .orElse(0L) + 1;
    }

    private String recordToString(CatchRecord record) {
        return record.getId() + DELIMITER + record.getFishermanName() + DELIMITER + record.getFishType() + DELIMITER + record.getQuantity() + DELIMITER + record.getWeight() + DELIMITER + record.getWaterBody() + DELIMITER + record.getStatus().name() + DELIMITER + record.getCreatedAt().toString();
    }

    private CatchRecord stringToRecord(String line) {
        String[] parts = line.split(DELIMITER);

        Long id = Long.parseLong(parts[0]);
        String fishermanName = parts[1];
        String fishType = parts[2];
        int quantity = Integer.parseInt(parts[3]);
        int weight = Integer.parseInt(parts[4]);
        String waterBody = parts[5];
        RecordStatus status = RecordStatus.valueOf(parts[6]);
        LocalDateTime createdAt = LocalDateTime.parse(parts[7]);

        return new CatchRecord(id, fishermanName, fishType, quantity, weight, waterBody, status, createdAt);
    }
}
