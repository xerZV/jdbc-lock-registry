package com.simitchiyski.lockregistry.core.queue;

import com.simitchiyski.lockregistry.config.queue.QueueProperties;
import com.simitchiyski.lockregistry.core.trade.Trade;
import com.simitchiyski.lockregistry.core.trade.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.Lock;

import static java.util.Objects.nonNull;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Slf4j
@Service
public class DefaultTradeScheduler extends TradeScheduler {

    @Autowired
    public DefaultTradeScheduler(final LockRegistry lockRegistry, final TradeService tradeService, final QueueProperties queueProperties) {
        super(lockRegistry, tradeService, queueProperties);
    }

    @Override
    @Scheduled(fixedRateString = "#{queueProperties.getExecution().getRate().toMillis()}")
    public void process() {
        if (getQueueProperties().getExecution().isEnabled()) {
            log.info("Start processing queued trades");

            Lock lock = null;
            boolean isLockAcquired = false;

            try {
                lock = getLockRegistry().obtain(this.getClass().getSimpleName());

                log.debug("Trying to obtain lock");
                isLockAcquired = lock.tryLock(getQueueProperties().getLock().getTimeToWait().toMillis(), MILLISECONDS);
                log.debug("isLockAcquired={}", isLockAcquired);

                if (isLockAcquired) {
                    log.info("Lock acquired");
                    submitQueuedOrders(getTradeService().getQueuedTradesOrderByReleaseDateTime(getQueueProperties().getExecution().getBatchSize()));
                }

            } catch (Exception e) {
                log.error("Exception occurred while acquiring lock", e);
            } finally {
                if (nonNull(lock) && isLockAcquired) {
                    lock.unlock();
                    log.info("Lock released");
                }
            }
        } else {
            log.info("The execution of the scheduled process for submitting queued orders is disabled via configuration properties");
        }
    }

    private void submitQueuedOrders(List<Trade> queuedTrades) {
        for (final Trade trade : queuedTrades) {
            final LocalDateTime releaseDateTime = trade.getReleaseDateTime();
            if (nonNull(releaseDateTime) && isReadyForSubmit(releaseDateTime)) {
                getTradeService().submit(trade.getId());
            }
        }
    }
}
