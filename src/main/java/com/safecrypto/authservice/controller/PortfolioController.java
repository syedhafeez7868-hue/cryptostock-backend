package com.safecrypto.authservice.controller;

import com.safecrypto.authservice.model.PortfolioEntry;
import com.safecrypto.authservice.model.Trade;
import com.safecrypto.authservice.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/portfolio")
@CrossOrigin(origins = "http://localhost:3000")
public class PortfolioController {
    @Autowired
    private PortfolioRepository portfolioRepo;

    @GetMapping("/{email}")
    public ResponseEntity<?> getPortfolio(@PathVariable String email) {
        List<PortfolioEntry> entries = portfolioRepo.findByEmail(email);
        Map<String, Double> result = entries.stream()
                .collect(Collectors.toMap(PortfolioEntry::getCoinName, PortfolioEntry::getQuantity));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updatePortfolio(@RequestBody Trade trade) {
        try {
            String email = trade.getEmail();
            String coinName = trade.getCoinName();
            double qty = trade.getQuantity() == null ? 0.0 : trade.getQuantity();

            PortfolioEntry existing = portfolioRepo.findByEmailAndCoinName(email, coinName);
            if (existing == null) {
                existing = new PortfolioEntry();
                existing.setEmail(email);
                existing.setCoinName(coinName);
                existing.setQuantity(0.0);
            }

            if ("BUY".equalsIgnoreCase(trade.getType())) {
                existing.setQuantity(existing.getQuantity() + qty);
            } else if ("SELL".equalsIgnoreCase(trade.getType())) {
                existing.setQuantity(Math.max(0.0, existing.getQuantity() - qty));
            }

            portfolioRepo.save(existing);
            return ResponseEntity.ok("Portfolio updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Portfolio update failed: " + e.getMessage());
        }
    }
}
