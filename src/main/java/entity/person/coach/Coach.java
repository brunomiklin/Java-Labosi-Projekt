package entity.person.coach;

import entity.hall.Hall;
import entity.person.Person;
import entity.booking.Booking;
import jakarta.json.bind.annotation.JsonbTransient;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Predstavlja trenera koji nasljeđuje klasu Person i ima mogućnost
 * kreiranja rezervacija termina za treninge u dvoranama.
 *
 * Trener ima specijalizaciju i može držati ograničen broj rezervacija.
 *
 * Konstrukcija objekta koristi Builder obrazac za lakše i sigurnije kreiranje trenera.
 *
 * Metoda {@link #createBooking(Hall, LocalDateTime, Integer)} omogućuje kreiranje nove rezervacije,
 * dok {@link #printMyBookings} ispisuje sve rezervacije trenera.
 */
public class Coach extends Person implements Serializable {
    private static final Integer BrojBookinga = 5;

    private String specialization;
    @JsonbTransient
    private List<Booking> myBookings = new ArrayList<>();
    public Coach(){
        super();
    }

    /**
     * Privatni konstruktor koji se koristi unutar CoachBuildera.
     *
     * @param builder objekt buildera sa svim potrebnim podacima
     */
    private Coach(CoachBuilder builder) {
        super(builder);
        this.specialization = builder.specialization;
    }

    /**
     * Builder klase za kreiranje instanci trenera.
     */
    public static class CoachBuilder extends Person.PersonBuilder {
        private String specialization = "";

        /**
         * Konstruktor buildera s obaveznim podacima.
         *
         * @param OIB identifikacijski broj trenera
         * @param firstName ime trenera
         * @param lastName prezime trenera
         */
        public CoachBuilder(String OIB, String firstName, String lastName) {
            super(OIB, firstName, lastName);
        }

        /**
         * Postavlja specijalizaciju trenera.
         *
         * @param specialization specijalizacija
         * @return trenutni builder za moguću daljnju konfiguraciju
         */
        public CoachBuilder specialization(String specialization) {
            this.specialization = specialization;
            return this;
        }

        /**
         * Kreira instancu trenera s prethodno postavljenim podacima.
         *
         * @return novi trener
         */
        public Coach build() {
            return new Coach(this);
        }
    }

    /**
     * Kreira novu rezervaciju termina u dvorani ako je termin slobodan.
     *
     * @param hall dvorana za rezervaciju
     * @param dateTime početno vrijeme termina
     * @param trainingTime trajanje treninga u minutama
     */
    public void createBooking(Hall hall, LocalDateTime dateTime, Integer trainingTime) {
        Optional<Hall> maybeHall = Optional.ofNullable(hall);

        maybeHall.ifPresentOrElse(
                h -> {
                    if (!h.isAvailable(dateTime, trainingTime)) {
                        System.out.println("Dvorana " + h.getName() + " je zauzeta "
                                + dateTime.toLocalDate() + " u " + dateTime.toLocalTime());
                        return;
                    }

                    if (myBookings.size() >= BrojBookinga) {
                        System.out.println(getFirstName() + " ima popunjen raspored!");
                        return;
                    }

                    Booking booking = new Booking(this, h, dateTime, trainingTime);
                    h.addBooking(booking);
                    myBookings.add(booking);
                },

                () -> System.out.println("Greška: dvorana ne može biti null!")
        );
    }

    /**
     * Dohvaća sve rezervacije trenera kao array (kompatibilno za top3 servis)
     *
     * @return niz rezervacija
     */
    public Booking[] getMyBookings() {
        return myBookings.toArray(new Booking[0]);
    }

    /**
     * Ispisuje sve rezervirane treninge trenera.
     */
    public void printMyBookings() {
        Optional<List<Booking>> maybe = Optional.ofNullable(myBookings);

        maybe.filter(list -> !list.isEmpty())
                .ifPresentOrElse(
                        list -> {
                            System.out.println("Svi rezervirani treninzi trenera " + getFirstName() + ":");
                            list.stream().filter(Objects::nonNull).forEach(System.out::println);
                        },
                        () -> System.out.println("Trener nema rezerviranih treninga.")
                );
    }

    /**
     * Ispisuje osobne podatke trenera.
     */
    @Override
    public void printPersonalData() {
        System.out.println("Trener: OIB: " + getOIB() + " " + getFirstName() + " " + getLastName());
    }
}
