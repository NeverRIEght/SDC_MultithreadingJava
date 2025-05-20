package dev.mkomarov.task2.entity.traderequest;

import dev.mkomarov.task2.entity.participant.Currency;
import dev.mkomarov.task2.entity.Exchange;
import dev.mkomarov.task2.entity.participant.Participant;
import dev.mkomarov.task2.entity.traderequest.state.CreatedState;
import dev.mkomarov.task2.entity.traderequest.state.TradeState;

import java.time.ZonedDateTime;

public class TradeRequest {
    private TradeState state;

    private final Participant buyer;
    private Participant seller;

    private final Currency fromCurrency;
    private final Currency toCurrency;

    private final double fromCurrencyAmount;
    private final double toCurrencyAmount;

    private final ZonedDateTime createdDateTime;
    private ZonedDateTime updatedDateTime;

    public TradeRequest(Participant buyer,
                        Currency fromCurrency,
                        Currency toCurrency,
                        double fromCurrencyAmount,
                        double toCurrencyAmount) {
        this.state = new CreatedState();
        this.buyer = buyer;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.fromCurrencyAmount = fromCurrencyAmount;
        this.toCurrencyAmount = toCurrencyAmount;
        this.createdDateTime = ZonedDateTime.now();
        this.updatedDateTime = ZonedDateTime.now();
    }

    public void proceed(Exchange exchange) {
        while (!state.isFinal()) {
            state.handle(this, exchange);
        }
    }

    public void setState(TradeState newState) {
        this.state = newState;
    }

    public TradeState getState() {
        return state;
    }

    public Participant getBuyer() {
        return buyer;
    }

    public Participant getSeller() {
        return seller;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public double getFromCurrencyAmount() {
        return fromCurrencyAmount;
    }

    public double getToCurrencyAmount() {
        return toCurrencyAmount;
    }

    public ZonedDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public ZonedDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setSeller(Participant seller) {
        this.seller = seller;
    }

    public void setUpdatedDateTime(ZonedDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    @Override
    public String toString() {
        return "TradeRequest{" +
                "state=" + state +
                ", buyer=" + buyer +
                ", seller=" + seller +
                ", fromCurrency=" + fromCurrency +
                ", toCurrency=" + toCurrency +
                ", fromCurrencyAmount=" + fromCurrencyAmount +
                ", toCurrencyAmount=" + toCurrencyAmount +
                ", createdDateTime=" + createdDateTime +
                ", updatedDateTime=" + updatedDateTime +
                '}';
    }
}
