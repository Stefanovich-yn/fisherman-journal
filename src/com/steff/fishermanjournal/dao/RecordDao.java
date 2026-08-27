package com.steff.fishermanjournal.dao;

import com.steff.fishermanjournal.entity.CatchRecord;

import java.util.List;

public interface RecordDao {

    CatchRecord save(CatchRecord record) throws DaoException;

    void update(CatchRecord record) throws DaoException;

    List<CatchRecord> findAll() throws DaoException;

    CatchRecord findById(Long id) throws DaoException;

}
