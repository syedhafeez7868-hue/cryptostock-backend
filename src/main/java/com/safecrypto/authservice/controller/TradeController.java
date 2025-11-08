package com.safecrypto.authservice.controller;

import com.safecrypto.authservice.model.Trade;
import com.safecrypto.authservice.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/trades")
@CrossOrigin(origins = "http://localhost:3000")
public class TradeController {
    @Autowired
    private TradeRepository tradeRepo;

    @PostMapping
    public ResponseEntity<Trade> createTrade(@RequestBody Trade trade) {
        Trade saved = tradeRepo.save(trade);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Trade>> getTradesByEmail(@PathVariable String email) {
        List<Trade> trades = tradeRepo.findByEmail(email);
        return ResponseEntity.ok(trades);
    }
}
