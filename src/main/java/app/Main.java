package app;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import entity.assets.MenuController;
import entity.hall.Hall;
import entity.person.Person;
import entity.person.coach.Coach;
import entity.person.user.User;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
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

    /**
     * Pozivanje različitih metoda te generianje i manipulacija korisnicima, bookingom i dvoranama
     *
     * Glavna metoda u aplikaciji omogućuje generiranje trenera i korisnika, koje kasnije koristi
     * kao izvor kod kreiranja bookinga, pretrage i u konačnici pružanje usluge istima
     */
    static void main() {
        log.trace("Ulaz u glavnu metodu main.");
        log.trace("Deserijalizacija pomocu JSON-a");

        Scanner sc = new Scanner(System.in);
        Set<Person> osobe = new HashSet<>();
        List<Hall> dvorane = new ArrayList<>();
        try {
                Jsonb jsonb = JsonbBuilder.create();
                String jsonUser = Files.readString(Paths.get("files/user.json"));
                String jsonCoach = Files.readString(Paths.get("files/coach.json"));
                String jsonHall = Files.readString(Paths.get("files/hall.json"));

                 Set<Coach> coaches = jsonb.fromJson(jsonCoach,new HashSet<Coach>(){}.getClass().getGenericSuperclass());
                 Set<User> users = jsonb.fromJson(jsonUser,new HashSet<User>(){}.getClass().getGenericSuperclass());

                 osobe.addAll(coaches);
                 osobe.addAll(users);

                 dvorane = jsonb.fromJson(jsonHall,new ArrayList<Hall>(){}.getClass().getGenericSuperclass());


        }catch (IOException ioe)
        {
            log.error("Greška kod deserijalizacije JSON-a");
            ioe.printStackTrace();
            System.err.println("Grška kod serijalizacije JSON-a!");
        }


        log.trace("Ulazak u meni.");
        System.out.println("\nSustav za upravljanje sportskim dvoranama i terenima \uD83C\uDFBD\n");
        MenuController.menu(osobe, dvorane, sc);

        log.trace("Serijalizacija JSON-a.");
        try(FileWriter coachWritter = new FileWriter("files/coach.json");FileWriter hallWriter = new FileWriter("files/hall.json");FileWriter userWriter = new FileWriter("files/user.json")) {
                Jsonb jsonb = JsonbBuilder.create();
                String jsonCoach = jsonb.toJson(osobe.stream().filter(person -> person instanceof Coach).collect(Collectors.toList()));
                String jsonUser = jsonb.toJson(osobe.stream().filter(person -> person instanceof User).collect(Collectors.toList()));
                String jsonHall = jsonb.toJson(dvorane);
                userWriter.write(jsonUser);
                coachWritter.write(jsonCoach);
                hallWriter.write(jsonHall);

        }catch (IOException ioe)
        {
            System.err.println("Greška kod serijalizacije JSON-a");
        }

        log.trace("Izlaz iz main metode.");
    }
    }












