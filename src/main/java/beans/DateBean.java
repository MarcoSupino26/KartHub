package beans;

import java.time.LocalDate;

public class DateBean {
    private final LocalDate date;

    public DateBean(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {return date;}
}
