package beans;

public class PaymentBean {
    private String cardName;
    private String cardSurname;
    private String cardNumber;
    private String expiryMonth;
    private String securityCode;

    public PaymentBean() {//La new non ha bisogno di un parametro
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardSurname() {
        return cardSurname;
    }

    public void setCardSurname(String cardSurname) {
        this.cardSurname = cardSurname;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
}
