package dev.mkomarov.task2.entity.traderequest.state;

import dev.mkomarov.task2.entity.Exchange;
import dev.mkomarov.task2.entity.traderequest.TradeRequest;

public interface TradeState {
    void handle(TradeRequest request, Exchange exchange);
    boolean isFinal();
}
