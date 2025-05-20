package dev.mkomarov.task2.entity.traderequest.state;

import dev.mkomarov.task2.entity.Exchange;
import dev.mkomarov.task2.entity.traderequest.TradeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatchedState implements TradeState {
    private static final Logger LOG = LoggerFactory.getLogger(MatchedState.class);

    @Override
    public void handle(TradeRequest request, Exchange exchange) {
        LOG.info("Seller found: {}", request.getSeller().getName());
        request.setState(new LockedState());
    }

    @Override
    public boolean isFinal() {
        return false;
    }
}
