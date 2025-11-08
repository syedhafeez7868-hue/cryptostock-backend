package com.safecrypto.authservice.repository;
import com.safecrypto.authservice.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByEmail(String email);
}
