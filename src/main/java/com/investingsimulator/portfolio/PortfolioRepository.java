package com.investingsimulator.portfolio;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PortfolioRepository extends PagingAndSortingRepository<Portfolio, Integer> {
}
