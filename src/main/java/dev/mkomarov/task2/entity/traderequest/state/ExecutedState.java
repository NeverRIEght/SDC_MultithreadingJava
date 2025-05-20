package dev.mkomarov.task2.entity.traderequest.state;

import dev.mkomarov.task2.entity.Exchange;
import dev.mkomarov.task2.entity.traderequest.TradeRequest;

public class ExecutedState implements TradeState {
    @Override
    public void handle(TradeRequest request, Exchange exchange) {
        // final state, do nothing
    }

    @Override
    public boolean isFinal() {
        return true;
    }
}
