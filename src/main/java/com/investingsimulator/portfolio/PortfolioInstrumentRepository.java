package com.investingsimulator.portfolio;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PortfolioInstrumentRepository extends PagingAndSortingRepository<PortfolioInstrument, Integer> { }
