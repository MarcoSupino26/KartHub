package beans;

public class OptionsBean {
    private final boolean race;
    private final boolean quali;
    private final boolean fp;
    private final boolean medals;
    private final boolean champagne;
    private boolean onBoard;
    private int rental;
    private int personal;
    private String shifts;

    public OptionsBean(boolean race, boolean quali, boolean fp, boolean medals, boolean champagne, boolean onBoard, int rental, int personal, String shifts) {
        this.race = race;
        this.quali = quali;
        this.fp = fp;
        this.medals = medals;
        this.champagne = champagne;
        this.onBoard = onBoard;
        this.rental = rental;
        this.personal = personal;
        this.shifts = shifts;
    }

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
