package com.safecrypto.authservice.controller;

import com.safecrypto.authservice.model.Watchlist;
import com.safecrypto.authservice.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
@CrossOrigin(origins = "http://localhost:3000")
public class WatchlistController {
    @Autowired
    private WatchlistRepository watchRepo;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Watchlist w) {
        Watchlist exists = watchRepo.findByEmailAndCoinId(w.getEmail(), w.getCoinId());
        if (exists != null) return ResponseEntity.ok(exists);
        Watchlist saved = watchRepo.save(w);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Watchlist>> get(@PathVariable String email) {
        List<Watchlist> list = watchRepo.findByEmail(email);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/remove")
    public ResponseEntity<String> remove(@RequestBody Watchlist w) {
        Watchlist exists = watchRepo.findByEmailAndCoinId(w.getEmail(), w.getCoinId());
        if (exists != null) {
            watchRepo.delete(exists);
            return ResponseEntity.ok("Removed");
        }
        return ResponseEntity.ok("Not found");
    }
}
