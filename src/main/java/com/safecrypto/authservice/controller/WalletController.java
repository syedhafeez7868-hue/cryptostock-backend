package com.safecrypto.authservice.controller;

import com.safecrypto.authservice.model.Wallet;
import com.safecrypto.authservice.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
@CrossOrigin(origins = "http://localhost:3000")
public class WalletController {
    @Autowired
    private WalletRepository walletRepo;

    @GetMapping("/{email}")
    public ResponseEntity<?> getWallet(@PathVariable String email) {
        Wallet wallet = walletRepo.findByEmail(email);
        if (wallet == null) {
            wallet = new Wallet();
            wallet.setEmail(email);
            wallet.setBalanceUsd(10000.0);
            walletRepo.save(wallet);
        }
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateWallet(@RequestBody Wallet walletRequest) {
        try {
            Wallet wallet = walletRepo.findByEmail(walletRequest.getEmail());
            if (wallet == null) {
                wallet = new Wallet();
                wallet.setEmail(walletRequest.getEmail());
                wallet.setBalanceUsd(walletRequest.getBalanceUsd());
            } else {
                wallet.setBalanceUsd(walletRequest.getBalanceUsd());
            }
            walletRepo.save(wallet);
            return ResponseEntity.ok("Wallet updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Wallet update failed: " + e.getMessage());
        }
    }
}
