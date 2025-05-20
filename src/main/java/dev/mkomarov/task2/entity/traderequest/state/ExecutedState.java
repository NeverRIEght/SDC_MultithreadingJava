package dev.mkomarov.task2.entity.traderequest.state;

import dev.mkomarov.task2.entity.Exchange;
import dev.mkomarov.task2.entity.traderequest.TradeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutedState implements TradeState {
    private static final Logger LOG = LoggerFactory.getLogger(ExecutedState.class);

    @Override
    public void handle(TradeRequest request, Exchange exchange) {
        LOG.info("Trade executed.");
    }

    @Override
    public boolean isFinal() {
        return true;
    }
}
