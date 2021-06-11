package com.simitchiyski.lockregistry.core.trade;

import com.simitchiyski.lockregistry.web.trade.dto.CreateTradeDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface TradeService {
    Trade findOneById(final Long id);
    List<Trade> findAll();
    List<Trade> getQueuedTradesOrderByReleaseDateTime(final int batchSize);

    Trade create(final @Valid @NotNull CreateTradeDTO tradeDTO);

    void submit(final Long id);
    void submitOrQueue(final Long id);
}
