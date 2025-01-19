package beans;

public class OptionsBean {
    private boolean race;
    private boolean quali;
    private boolean fp;
    private boolean medals;
    private boolean champagne;
    private boolean onBoard;
    private int rental;
    private int personal;
    private String shifts;

    public OptionsBean() {
    }

    public void setRace(boolean race) {
        this.race = race;
    }

    public void setQuali(boolean quali) {
        this.quali = quali;
    }

    public void setFp(boolean fp) {
        this.fp = fp;
    }

    public void setMedals(boolean medals) {
        this.medals = medals;
    }

    public void setChampagne(boolean champagne) {
        this.champagne = champagne;
    }

    public void setOnBoard(boolean onBoard) {
        this.onBoard = onBoard;
    }

    public void setRental(int rental) {
        this.rental = rental;
    }

    public void setPersonal(int personal) {this.personal = personal;}

    public boolean isRace() {
        return race;
    }

    public boolean isQuali() {
        return quali;
    }

    public boolean isFp() {
        return fp;
    }

    public boolean isMedals() {
        return medals;
    }

    public boolean isChampagne() {
        return champagne;
    }

    public boolean isOnBoard() {
        return onBoard;
    }

    public int getRental() {
        return rental;
    }

    public int getPersonal() {
        return personal;
    }

    public String getShifts() {
        return shifts;
    }
}