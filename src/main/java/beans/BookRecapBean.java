package beans;

public class BookRecapBean {
    int rentalKarts;
    int personalKarts;
    double bookingCost;
    String bookingID;
    String bookingDesc;
    String bookedTrack;
    String shift;

    public int getRentalKarts() {
        return rentalKarts;
    }

    public void setRentalKarts(int rentalKarts) {
        this.rentalKarts = rentalKarts;
    }

    public int getPersonalKarts() {
        return personalKarts;
    }

    public void setPersonalKarts(int personalKarts) {
        this.personalKarts = personalKarts;
    }

    public double getBookingCost() {
        return bookingCost;
    }

    public void setBookingCost(double bookingCost) {
        this.bookingCost = bookingCost;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getBookingDesc() {
        return bookingDesc;
    }

    public void setBookingDesc(String bookingDesc) {
        this.bookingDesc = bookingDesc;
    }

    public String getBookedTrack() {
        return bookedTrack;
    }

    public void setBookedTrack(String bookedTrack) {
        this.bookedTrack = bookedTrack;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
