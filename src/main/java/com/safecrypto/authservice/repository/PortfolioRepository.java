package com.safecrypto.authservice.repository;
import com.safecrypto.authservice.model.PortfolioEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PortfolioRepository extends JpaRepository<PortfolioEntry, Long> {
    List<PortfolioEntry> findByEmail(String email);
    PortfolioEntry findByEmailAndCoinName(String email, String coinName);
}
