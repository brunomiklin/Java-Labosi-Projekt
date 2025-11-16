package entity.booking;

import entity.person.coach.Coach;
import entity.hall.Hall;

import java.time.LocalDateTime;

public record Booking(Coach coach, Hall hall, LocalDateTime dateTime, Integer trainingTime) {

    @Override
    public LocalDateTime dateTime() {
        return dateTime;
    }

    @Override
    public Hall hall() {
        return hall;
    }

    @Override
    public Integer trainingTime() {
        return trainingTime;
    }

    @Override
    public String toString() {
        return "Trening-> " +
                " u zgradi " + hall.getName() +
                ", datum i vrijeme:" + dateTime +
                ", Vrijeme trajanja(u minutam):" + trainingTime/60;
    }


}
