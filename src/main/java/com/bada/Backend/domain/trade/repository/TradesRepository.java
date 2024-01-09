package com.bada.Backend.domain.trade.repository;

import com.bada.Backend.domain.trade.entity.Trades;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradesRepository extends JpaRepository<Trades, Long> {
}
