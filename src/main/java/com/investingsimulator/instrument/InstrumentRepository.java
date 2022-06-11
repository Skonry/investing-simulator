package com.investingsimulator.instrument;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface InstrumentRepository extends PagingAndSortingRepository<Instrument, Integer> {
    Page<Instrument> findAll(Pageable pageable);
}
