package com.investingsimulator.portfolio;

import com.investingsimulator.instrument.InstrumentResult;
import com.investingsimulator.instrument.InstrumentResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/{id}")
    public PortfolioResponse show(@PathVariable("id") int id) {
        Portfolio portfolio = portfolioService.getPortfolio(id);

        return new PortfolioResponse(portfolio);
    }

    @GetMapping("/{id}/result")
    public PortfolioResultResponse result(
            @PathVariable("id") int id,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        PortfolioResult portfolioResult = portfolioService.getPortfolioResult(id, startDate, endDate);

        return new PortfolioResultResponse(portfolioResult);
    }
}
