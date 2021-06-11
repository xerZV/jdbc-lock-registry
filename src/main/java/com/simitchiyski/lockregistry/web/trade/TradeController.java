package com.simitchiyski.lockregistry.web.trade;

import com.simitchiyski.lockregistry.core.trade.TradeService;
import com.simitchiyski.lockregistry.core.trade.mapper.TradeMapper;
import com.simitchiyski.lockregistry.web.trade.dto.CreateTradeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.simitchiyski.lockregistry.web.trade.dto.TradeDTO;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/trades")
public class TradeController {

    private final TradeMapper tradeMapper;
    private final TradeService tradeService;

    @GetMapping
    public ResponseEntity<List<TradeDTO>> getAll() {
        return ok(tradeMapper.toDto(tradeService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TradeDTO> getOne(final @NotNull @Positive @PathVariable Long id) {
        return ok(tradeMapper.toDto(tradeService.findOneById(id)));
    }

    @GetMapping("/{id}/submit")
    public ResponseEntity<Void> submitOrQueue(final @NotNull @Positive @PathVariable Long id) {
        tradeService.submitOrQueue(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> create(final @Valid @NotNull @RequestBody CreateTradeDTO tradeDTO) {
        return ResponseEntity.created(fromCurrentRequest().path("/{id}")
                .buildAndExpand(this.tradeService.create(tradeDTO).getId())
                .toUri())
                .build();
    }
}
