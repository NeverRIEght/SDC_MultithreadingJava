package dev.mkomarov.task2.entity.traderequest.state;

import dev.mkomarov.task2.entity.participant.Account;
import dev.mkomarov.task2.entity.Exchange;
import dev.mkomarov.task2.entity.participant.Participant;
import dev.mkomarov.task2.entity.traderequest.TradeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;

public class LockedState implements TradeState {
    private static final Logger LOG = LoggerFactory.getLogger(LockedState.class);

    @Override
    public void handle(TradeRequest request, Exchange exchange) {
        LOG.info("Locking accounts...");

        Participant buyer = request.getBuyer();
        Participant seller = request.getSeller();

        Account buyerAccount = buyer.getAccount(request.getFromCurrency());
        Account sellerAccount = seller.getAccount(request.getToCurrency());

        double buyerToAdd = request.getToCurrencyAmount();
        double commission = request.getFromCurrencyAmount() * 0.05;
        double buyerToWithdraw = request.getFromCurrencyAmount() + commission;

        double sellerToAdd = request.getFromCurrencyAmount();
        double sellerToWithdraw = request.getToCurrencyAmount();

        boolean lockedBuyer = buyerAccount.getLock().tryLock();
        boolean lockedSeller = sellerAccount.getLock().tryLock();

        if (!lockedBuyer || !lockedSeller) {
            LOG.info("Can not acquire all locks, abort");
            if (lockedBuyer) buyerAccount.unlock();
            if (lockedSeller) sellerAccount.unlock();
            return;
        }

        try {
            if (buyerAccount.getAmount() < buyerToWithdraw) {
                LOG.info("Unsufficient funds on buyer side, abort");
                request.setState(new CanceledState());
                return;
            }

            if (sellerAccount.getAmount() < sellerToWithdraw) {
                LOG.info("Unsufficient funds on seller side, abort");
                request.setState(new CanceledState());
                return;
            }

            buyerAccount.setAmount(buyerAccount.getAmount() - buyerToWithdraw);

            sellerAccount.setAmount(sellerAccount.getAmount() - sellerToWithdraw);

            buyer.getAccount(request.getToCurrency()).setAmount(
                    buyer.getAccount(request.getToCurrency()).getAmount() + buyerToAdd
            );

            seller.getAccount(request.getFromCurrency()).setAmount(
                    seller.getAccount(request.getFromCurrency()).getAmount() + sellerToAdd
            );

            LOG.info("Transfer completed, unlocking accounts. Commission taken: {} {}",
                    commission, request.getFromCurrency());

            request.setUpdatedDateTime(ZonedDateTime.now());
            request.setState(new ExecutedState());

        } finally {
            buyerAccount.unlock();
            sellerAccount.unlock();
        }
    }

    @Override
    public boolean isFinal() {
        return false;
    }
}
