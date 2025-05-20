package dev.mkomarov.task2.entity.traderequest.state;

import dev.mkomarov.task2.entity.Account;
import dev.mkomarov.task2.entity.Exchange;
import dev.mkomarov.task2.entity.Participant;
import dev.mkomarov.task2.entity.traderequest.TradeRequest;

import java.time.ZonedDateTime;

public class LockedState implements TradeState {
    @Override
    public void handle(TradeRequest request, Exchange exchange) {
        System.out.println("[State: Locked] Attempting to trade...");

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
            System.out.println("[State: Locked] Can not acquire all locks, abort");
            if (lockedBuyer) buyerAccount.unlock();
            if (lockedSeller) sellerAccount.unlock();
            return;
        }

        try {
            if (buyerAccount.getAmount() < buyerToWithdraw) {
                System.out.println("[State: Locked] Unsufficient funds on buyer side. Trade canceled.");
                request.setState(new CanceledState());
                return;
            }

            if (sellerAccount.getAmount() < sellerToWithdraw) {
                System.out.println("[State: Locked] Unsufficient funds on seller side. Trade canceled.");
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

            System.out.printf("[State: Locked] Trade Completed. Commission: %.4f %s%n",
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
