package controllers;

import beans.PaymentBean;

public class PaymentManager {

    public PaymentManager() {
    }

    public boolean processPayment(PaymentBean payment) {
        String name = payment.getCardName();
        String surname = payment.getCardSurname();
        String cardNumber = payment.getCardNumber();
        String month = payment.getExpiryMonth();
        String cvv = payment.getSecurityCode();
        return check(name, surname, cardNumber, month, cvv);
    }

    private boolean check(String name, String surname, String cardNumber, String expiryMonth, String securityCode) {
        return name != null && surname != null && cardNumber != null && expiryMonth != null && securityCode != null;
    }
}
