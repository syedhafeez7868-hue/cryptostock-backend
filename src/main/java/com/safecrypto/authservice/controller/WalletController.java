package com.safecrypto.authservice.controller;

import com.safecrypto.authservice.model.Wallet;
import com.safecrypto.authservice.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/wallet")
@CrossOrigin(origins = "http://localhost:3000")
public class WalletController {

    @Autowired
    private WalletRepository walletRepo;

    @GetMapping("/{email}")
    public ResponseEntity<Wallet> getWallet(@PathVariable String email) {
        Optional<Wallet> walletOpt = walletRepo.findByEmail(email);
        Wallet wallet = walletOpt.orElseGet(() -> {
            Wallet w = new Wallet();
            w.setEmail(email);
            w.setBalanceUsd(0.0);
            return walletRepo.save(w);
        });
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/update")
    public ResponseEntity<Wallet> updateWallet(@RequestBody Wallet walletUpdate) {
        Wallet wallet = walletRepo.findByEmail(walletUpdate.getEmail())
                .orElseGet(() -> {
                    Wallet w = new Wallet();
                    w.setEmail(walletUpdate.getEmail());
                    w.setBalanceUsd(0.0);
                    return w;
                });
        wallet.setBalanceUsd(walletUpdate.getBalanceUsd());
        Wallet saved = walletRepo.save(wallet);
        return ResponseEntity.ok(saved);
    }
}
