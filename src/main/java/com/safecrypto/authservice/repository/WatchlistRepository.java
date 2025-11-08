package com.safecrypto.authservice.repository;
import com.safecrypto.authservice.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    List<Watchlist> findByEmail(String email);
    Watchlist findByEmailAndCoinId(String email, String coinId);
}
