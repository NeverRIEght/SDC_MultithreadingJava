package dev.mkomarov.task2.entity.traderequest.state;

import dev.mkomarov.task2.entity.Exchange;
import dev.mkomarov.task2.entity.traderequest.TradeRequest;

import java.time.ZonedDateTime;

public class CreatedState implements TradeState {
    @Override
    public void handle(TradeRequest request, Exchange exchange) {
        System.out.println("[State: Created] Trade created by: " + request.getBuyer().getName());
        request.setUpdatedDateTime(ZonedDateTime.now());

        request.setState(new MatchingState());
    }

    @Override
    public boolean isFinal() {
        return false;
    }
}
