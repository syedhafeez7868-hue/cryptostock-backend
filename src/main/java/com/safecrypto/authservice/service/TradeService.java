package com.safecrypto.authservice.service;

import com.safecrypto.authservice.model.Trade;
import com.safecrypto.authservice.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    public Trade saveTrade(Trade trade) {
        trade.setDate(java.time.LocalDateTime.now().toString());
        return tradeRepository.save(trade);
    }

    public List<Trade> getTradesByEmail(String email) {
        return tradeRepository.findByEmail(email);
    }
}
