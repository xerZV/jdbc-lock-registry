package com.simitchiyski.lockregistry.core.queue;

import com.simitchiyski.lockregistry.config.queue.QueueProperties;
import com.simitchiyski.lockregistry.core.trade.TradeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.support.locks.LockRegistry;

import java.time.Clock;
import java.time.LocalDateTime;

import static java.time.ZoneOffset.UTC;
import static java.time.ZonedDateTime.now;

@Getter
@RequiredArgsConstructor
public abstract class TradeScheduler {
    private final LockRegistry lockRegistry;
    private final TradeService tradeService;
    private final QueueProperties queueProperties;

    public abstract void process();

    boolean isReadyForSubmit(final LocalDateTime releaseDateTime) {
        final LocalDateTime nowInUTC = now(Clock.systemDefaultZone()).withZoneSameInstant(UTC).toLocalDateTime();

        return nowInUTC.isEqual(releaseDateTime) || nowInUTC.isAfter(releaseDateTime);
    }
}
