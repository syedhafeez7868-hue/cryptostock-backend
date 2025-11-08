package com.safecrypto.authservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private Double balanceUsd;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Double getBalanceUsd() { return balanceUsd == null ? 0.0 : balanceUsd; }
    public void setBalanceUsd(Double balanceUsd) { this.balanceUsd = balanceUsd; }
}
