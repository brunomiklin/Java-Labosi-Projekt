package entity.person.user;

import entity.person.Person;
import entity.booking.Booking;

import java.io.Serializable;
import java.util.*;

/**
 * Predstavlja korisnika sustava koji nasljeđuje osobu (Person) i ima mogućnost
 * upravljanja rezervacijama dvorana.
 *
 * Korisnik ima korisničko ime, lozinku i ograničen broj rezervacija koje može držati.
 *
 * Konstrukcija objekta koristi Builder obrazac za lakše i sigurnije kreiranje korisnika.
 *
 * Metoda {@link #joinBooking(Booking)} omogućuje dodavanje nove rezervacije u korisnikov popis,
 * dok {@link #allMyBookings} ispisuje sve rezervacije korisnika.
 *
 */
public class User extends Person implements Serializable {
    private static final Integer BrojBookinga = 5;
    private String username;
    private String password;
    private List<Booking> bookings = new ArrayList<>();

    public User() {
        super();
        this.username = "";
        this.password = "";
        this.bookings = new ArrayList<>();
    }
    /**
     * Ispisuje osnovne podatke o korisniku.
     */
    @Override
    public void printPersonalData() {
        System.out.println("Korisnik: OIB: " + getOIB() + " " + getFirstName() + " " + getLastName());
    }

    /**
     * Privatni konstruktor koji se koristi unutar UserBuildera.
     *
     * @param builder objekt buildera sa svim potrebnim podacima
     */

    
    private User(UserBuilder builder) {
        super(builder);
        this.username = builder.username == null ? "" : builder.username;
        this.password = builder.password == null ? "" : builder.password;
    }

    /**
     * Builder klase za kreiranje instanci korisnika.
     */
    public static class UserBuilder extends Person.PersonBuilder {
        private String username = "";
        private String password = "";

        /**
         * Konstruktor buildera s obaveznim podacima.
         *
         * @param OIB identifikacijski broj korisnika
         * @param firstName ime korisnika
         * @param lastName prezime korisnika
         */
        public UserBuilder(String OIB, String firstName, String lastName) {
            super(OIB, firstName, lastName);
        }

        /**
         * Postavlja korisničko ime.
         *
         * @param username korisničko ime
         * @return trenutni builder za moguću daljnju konfiguraciju
         */
        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * Postavlja lozinku.
         *
         * @param password lozinka
         * @return trenutni builder za moguću daljnju konfiguraciju
         */
        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        /**
         * Kreira instancu korisnika s prethodno postavljenim podacima.
         *
         * @return novi korisnik
         */
        public User build() {
            return new User(this);
        }
    }

    /**
     * Dodaje rezervaciju korisniku ako još nije dosegnut maksimalni broj rezervacija.
     *
     * @param booking rezervacija koju korisnik želi dodati
     */
    public void joinBooking(Booking booking) {
        Optional<Booking> maybeBooking = Optional.ofNullable(booking);

        maybeBooking.ifPresentOrElse(
                b -> {
                    if (bookings.size() >= BrojBookinga) {
                        System.out.println("Dosegli ste maksimalan broj rezervacija (" + BrojBookinga + ").");
                        return;
                    }

                    bookings.add(b);

                    System.out.println("Korisnik " + getFirstName()
                            + " uspješno je rezervirao termin u dvorani "
                            + b.hall().getName() + " -> " + b.dateTime());
                },
                () -> System.out.println("Greška: Booking ne može biti null!")
        );
    }

    /**
     * Ispisuje sve rezervacije koje korisnik ima.
     */
    public void allMyBookings() {
        Optional<List<Booking>> maybe = Optional.ofNullable(bookings);

        List<Booking> list = maybe
                .map(l -> l.stream()
                        .filter(Objects::nonNull)
                        .toList())
                .orElse(List.of());

        if (list.isEmpty()) {
            System.out.println("Nemate rezerviranih termina!");
            return;
        }

        System.out.println("Treninzi korisnika " + this.getFirstName() + ":");
        list.forEach(System.out::println);
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

}


