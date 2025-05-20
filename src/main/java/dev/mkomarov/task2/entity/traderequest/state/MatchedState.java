package dev.mkomarov.task2.entity.traderequest.state;

import dev.mkomarov.task2.entity.Exchange;
import dev.mkomarov.task2.entity.traderequest.TradeRequest;

public class MatchedState implements TradeState {
    @Override
    public void handle(TradeRequest request, Exchange exchange) {
        System.out.println("[State: Matching] Seller found, locking accounts...");
        request.setState(new LockedState());
    }

    @Override
    public boolean isFinal() {
        return false;
    }
}
