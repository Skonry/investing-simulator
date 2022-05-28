package com.investingsimulator.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{id}/addInstrument")
    public void addInstrument(@PathVariable("id") int id, @RequestBody AddInstrumentRequest addInstrumentRequest) {
        portfolioService.addInstrument(id, addInstrumentRequest);
    }
}
