package com.safecrypto.authservice.service;

import com.safecrypto.authservice.model.Trade;
import com.safecrypto.authservice.model.Wallet;
import com.safecrypto.authservice.repository.TradeRepository;
import com.safecrypto.authservice.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TradeRepository tradeRepository;

    // ✅ Add money after Razorpay success
    public void addFunds(String email, double amount) {
        Wallet wallet = walletRepository.findByEmail(email).orElse(null);

        if (wallet == null) {
            wallet = new Wallet();
            wallet.setEmail(email);
            wallet.setBalanceUsd(0.0);
        }

        double newBalance = wallet.getBalanceUsd() + amount;
        wallet.setBalanceUsd(newBalance);
        walletRepository.save(wallet);

        Trade tx = new Trade();
        tx.setEmail(email);
        tx.setCoinName("USD");
        tx.setSymbol("USD");
        tx.setType("DEPOSIT");
        tx.setQuantity(amount);
        tx.setPrice(1.0);
        tx.setTotal(amount);
        tx.setStatus("Completed");
        tx.setDate(LocalDateTime.now().toString());
        tradeRepository.save(tx);
    }

    // ✅ Withdraw money manually
    public void withdrawFunds(String email, double amount) {
        Wallet wallet = walletRepository.findByEmail(email).orElse(null);
        if (wallet == null || wallet.getBalanceUsd() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        wallet.setBalanceUsd(wallet.getBalanceUsd() - amount);
        walletRepository.save(wallet);

        Trade tx = new Trade();
        tx.setEmail(email);
        tx.setCoinName("USD");
        tx.setSymbol("USD");
        tx.setType("WITHDRAW");
        tx.setQuantity(amount);
        tx.setPrice(1.0);
        tx.setTotal(amount);
        tx.setStatus("Completed");
        tx.setDate(LocalDateTime.now().toString());
        tradeRepository.save(tx);
    }
}
