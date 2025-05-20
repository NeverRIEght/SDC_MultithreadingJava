package dev.mkomarov.task2.entity.traderequest.state;

import dev.mkomarov.task2.entity.Exchange;
import dev.mkomarov.task2.entity.traderequest.TradeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;

public class CreatedState implements TradeState {
    private static final Logger LOG = LoggerFactory.getLogger(CreatedState.class);

    @Override
    public void handle(TradeRequest request, Exchange exchange) {
        LOG.info("Trade created and accepted: {}", request);
        request.setUpdatedDateTime(ZonedDateTime.now());

        request.setState(new MatchingState());
    }

    @Override
    public boolean isFinal() {
        return false;
    }
}
