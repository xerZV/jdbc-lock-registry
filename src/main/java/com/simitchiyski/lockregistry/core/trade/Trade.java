package com.simitchiyski.lockregistry.core.trade;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TRADE")
@ToString(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Trade extends AbstractPersistable<Long> {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private TradeStatus status;

    @NotNull
    @Column(name = "AMOUNT", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);

    @NotNull
    @Column(name = "RELEASED_DATE_TIME", nullable = false)
    private LocalDateTime releasedDateTime;

    @NotNull
    @Column(name = "RELEASE_DATE_TIME", nullable = false)
    private LocalDateTime releaseDateTime;

    @NotNull
    @Column(name = "CUTOFF_DATE_TIME", nullable = false, updatable = false)
    private LocalDateTime cutoffDateTime;

    @NotNull
    @CreatedDate
    @Column(name = "CREATED_DATE_TIME", nullable = false, updatable = false)
    private LocalDateTime dateCreated;
}
