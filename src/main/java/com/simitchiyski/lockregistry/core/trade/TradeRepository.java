package com.simitchiyski.lockregistry.core.trade;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findAllByStatusAndReleaseDateTimeIsLessThanEqualOrderByReleaseDateTime(final TradeStatus status, final LocalDateTime beforeTime, Pageable pageable);

    @Modifying
    @Query(value = "update Trade t set t.status = ?2, t.releasedDateTime = ?3 where t.id = ?1")
    void updateStatusAndReleasedDatetime(final Long id, final TradeStatus status, final LocalDateTime now);
}
