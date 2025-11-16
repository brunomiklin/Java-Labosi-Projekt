package app;


import java.util.*;

import entity.booking.BookingService;
import entity.hall.Hall;
import entity.hall.HallService;
import entity.person.coach.CoachService;
import entity.person.Person;
import entity.person.PersonService;
import entity.search.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Klasa {@link Main} za upravljanje sportskim centrima i terminima.
 *
 * Sadrži metode za generiranje korisnika i trenera, kreiranje dvorana,
 * upravljanje bookingom te pretragu korisnika i dvorana.
 *
 * @author Bruno Miklin
 * @since JAVA 25
 */
public class Main {
    static Logger log = LoggerFactory.getLogger(Main.class);
    public static final Integer NUMBER_OF_OBJECTS = 5;
    public static final Integer NUMBER_OF_USERS = 5;

    /**
     * Pozivanje različitih metoda te generianje i manipulacija korisnicima, bookingom i dvoranama
     *
     * Glavna metoda u aplikaciji omogućuje generiranje trenera i korisnika, koje kasnije koristi
     * kao izvor kod kreiranja bookinga, pretrage i u konačnici pružanje usluge istima
     */
    static void main() {
        log.trace("Ulaz u glavnu metodu main.");
        Scanner sc = new Scanner(System.in);

        log.info("Generiranje osoba");
        Set<Person> osobe = PersonService.generatePeople(sc, NUMBER_OF_USERS);

        log.info("Generiranje dvorana");
        List<Hall> dvorane = HallService.generateHalls(NUMBER_OF_OBJECTS,sc);

        log.info("Kreiranje bookinga");
        BookingService.createBooking(osobe, sc, dvorane);

        log.info("Pridruživanje korisnika bookinzima");
        HallService.joinBooking(osobe, sc);

        log.info("Pretraga korisnika");
        SearchService.searchUsers(osobe, sc);

        log.info("Pretraga dvorana");
        SearchService.searchHalls(dvorane, sc);

        log.info("Grupiranje osoba po tipu");
        PersonService.groupPeople(osobe, sc);

        log.info("Particioniranje osoba po emailu");
        SearchService.partitioningByEmail(osobe);

        log.info("Ispis top 3 trenera po broju termina");
        CoachService.printTop3CoachesByBookings(osobe);

        log.trace("Izlaz iz glavne metode main.");
    }

    }











