package com.investingsimulator.instrument;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface InstrumentRepository extends PagingAndSortingRepository<Instrument, Integer> {
    List<Instrument> findAll();
}
