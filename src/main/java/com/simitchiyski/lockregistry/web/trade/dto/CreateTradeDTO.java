package com.simitchiyski.lockregistry.web.trade.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class CreateTradeDTO {
    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private ZonedDateTime cutoffDateTime;
}
