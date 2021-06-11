package com.simitchiyski.lockregistry.core.trade;

import com.simitchiyski.lockregistry.config.queue.QueueProperties;
import com.simitchiyski.lockregistry.core.trade.mapper.CreateTradeMapper;
import com.simitchiyski.lockregistry.web.trade.dto.CreateTradeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import static com.simitchiyski.lockregistry.config.queue.QueueProperties.*;
import static com.simitchiyski.lockregistry.core.trade.TradeStatus.*;
import static java.lang.String.*;
import static java.time.ZoneOffset.UTC;
import static java.time.ZonedDateTime.now;
import static org.springframework.data.domain.PageRequest.of;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class DefaultTradeService implements TradeService {

    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Trade with id=%s was not found";
    private final QueueProperties queueProperties;
    private final TradeRepository tradeRepository;
    private final CreateTradeMapper createTradeMapper;

    @Override
    public Trade findOneById(Long id) {
        return tradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(format(NOT_FOUND_EXCEPTION_MESSAGE, id)));
    }

    @Override
    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    @Override
    public List<Trade> getQueuedTradesOrderByReleaseDateTime(int batchSize) {
        log.info("Fetching first {} queued orders ordered by releaseDateTime", batchSize);
        return tradeRepository.findAllByStatusAndReleaseDateTimeIsLessThanEqualOrderByReleaseDateTime(
                QUEUED,
                nowInUTC(),
                of(0, batchSize)
        );
    }

    @Override
    @Transactional
    public Trade create(final @Valid @NotNull CreateTradeDTO tradeDTO) {
        final Trade trade = createTradeMapper.toEntity(tradeDTO);
        trade.setStatus(PENDING_NEW);

        return tradeRepository.save(trade);
    }

    @Override
    @Transactional
    public void submit(final Long id) {
        tradeRepository.updateStatusAndReleasedDatetime(id, SUBMITTED, nowInUTC());
    }

    @Override
    @Transactional
    public void submitOrQueue(Long id) {
        final Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(format(NOT_FOUND_EXCEPTION_MESSAGE, id)));

        if (PENDING_NEW.equals(trade.getStatus())) {
            if (isReadyToSubmit(trade)) {
                submit(trade.getId());
            } else {
                queue(trade);
            }
        }
    }

    private boolean isReadyToSubmit(Trade trade) {
        return nowInUTC().isBefore(trade.getCutoffDateTime());
    }

    private void queue(Trade trade) {
        trade.setStatus(QUEUED);
        trade.setReleaseDateTime(releaseDateTime(trade.getCutoffDateTime().toLocalDate()));

        tradeRepository.save(trade);
    }

    private LocalDateTime releaseDateTime(final LocalDate cutoffDate) {
        final ReleaseDate releaseDate = queueProperties.getReleaseDate();

        return ZonedDateTime.of(
                cutoffDate.plusDays(releaseDate.getCutoffDateOffset()),
                releaseDate.getTime(),
                releaseDate.getZone()
        ).withZoneSameInstant(UTC).toLocalDateTime();
    }

    private LocalDateTime nowInUTC() {
        return ZonedDateTime.now(UTC).toLocalDateTime();
    }
}
