package com.bada.Backend.domain.trades.repository;

import com.bada.Backend.domain.trades.entity.Trades;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TradesRepository extends JpaRepository<Trades, Long> {
}
