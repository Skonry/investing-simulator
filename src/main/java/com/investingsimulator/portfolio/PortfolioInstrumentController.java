package com.investingsimulator.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/portfolio/{portfolioId}/instrument")
public class PortfolioInstrumentController {
    private PortfolioInstrumentService portfolioInstrumentService;

    @Autowired
    public PortfolioInstrumentController(PortfolioInstrumentService portfolioInstrumentService) {
        this.portfolioInstrumentService = portfolioInstrumentService;
    }

    @PostMapping
    public void addInstrument(
            @PathVariable("portfolioId") int portfolioId,
            @RequestBody AddInstrumentRequest addInstrumentRequest
    ) {
        portfolioInstrumentService.addInstrument(portfolioId, addInstrumentRequest);
    }

    @DeleteMapping("/{id}")
    public void removeInstrument(@PathVariable("id") int id) {
        portfolioInstrumentService.removeInstrument(id);
    }
}
