package dev.mkomarov.task2.entity.traderequest.state;

import dev.mkomarov.task2.entity.Exchange;
import dev.mkomarov.task2.entity.Participant;
import dev.mkomarov.task2.entity.traderequest.TradeRequest;

import java.time.ZonedDateTime;
import java.util.List;

public class MatchingState implements TradeState {
    @Override
    public void handle(TradeRequest request, Exchange exchange) {
        System.out.println("[State: Matching] Поиск подходящего продавца...");

        List<Participant> participants = exchange.getParticipants();

        for (Participant candidate : participants) {
            if (candidate.equals(request.getBuyer())) {
                continue;
            }

            if (candidate.isWillingToSell(request, exchange)) {
                request.setSeller(candidate);
                request.setUpdatedDateTime(ZonedDateTime.now());

                System.out.println("[State: Matching] Seller found: " + candidate.getName());
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
