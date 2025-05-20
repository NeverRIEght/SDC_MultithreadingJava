package dev.mkomarov.task2.entity.traderequest.state;

import dev.mkomarov.task2.entity.Exchange;
import dev.mkomarov.task2.entity.participant.Participant;
import dev.mkomarov.task2.entity.traderequest.TradeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.util.List;

public class MatchingState implements TradeState {
    private static final Logger LOG = LoggerFactory.getLogger(MatchingState.class);

    @Override
    public void handle(TradeRequest request, Exchange exchange) {
        LOG.info("Looking for a seller...");

        List<Participant> participants = exchange.getParticipants();

        for (Participant candidate : participants) {
            if (candidate.equals(request.getBuyer())) {
                continue;
            }

            if (candidate.isWillingToSell(request)) {
                request.setSeller(candidate);
                request.setUpdatedDateTime(ZonedDateTime.now());

                request.setState(new MatchedState());
                return;
            }
        }
    }

    @Override
    public boolean isFinal() {
        return false;
    }
}
