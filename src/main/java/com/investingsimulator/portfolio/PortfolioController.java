package com.investingsimulator.portfolio;

import com.investingsimulator.instrument.InstrumentResponse;
import com.investingsimulator.instrument.InstrumentResult;
import com.investingsimulator.instrument.InstrumentResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping
    public List<PortfolioResponse> index() {
        List<Portfolio> portfolios = portfolioService.getPortfolios();

        return portfolios.stream().map(PortfolioResponse::new).toList();
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

    @PostMapping
    public int create(@RequestBody PortfolioCreationRequest portfolioCreationRequest) {
        Portfolio portfolio = portfolioService.createPortfolio(portfolioCreationRequest);

        return portfolio.getId();
    }
}
