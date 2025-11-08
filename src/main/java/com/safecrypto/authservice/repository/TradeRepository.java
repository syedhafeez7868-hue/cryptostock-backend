package com.safecrypto.authservice.repository;
import com.safecrypto.authservice.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findByEmail(String email);
}
