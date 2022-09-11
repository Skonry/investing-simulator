package com.investingsimulator.portfolio;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PortfolioRepository extends PagingAndSortingRepository<Portfolio, Integer> {
    @Override
    List<Portfolio> findAll();
}
