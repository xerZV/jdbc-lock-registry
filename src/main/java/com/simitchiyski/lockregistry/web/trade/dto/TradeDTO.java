package com.simitchiyski.lockregistry.web.trade.dto;

import com.simitchiyski.lockregistry.core.trade.TradeStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TradeDTO {
    private Long id;
    private TradeStatus status;
    private BigDecimal amount;
    private LocalDateTime releasedDateTime;
    private LocalDateTime releaseDateTime;
    private LocalDateTime cutoffDateTime;
    private LocalDateTime dateCreated;
}
