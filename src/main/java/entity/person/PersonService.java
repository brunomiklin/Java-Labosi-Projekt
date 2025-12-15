package entity.person;

import app.Main;
import entity.person.coach.Coach;
import entity.person.coach.CoachService;
import entity.person.user.User;
import entity.person.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class PersonService {
    static Logger log = LoggerFactory.getLogger(Main.class);
    private final Scanner sc;
    public PersonService(Scanner sc) {
        this.sc = sc;
    }
    public static Person generatePerson(Scanner sc) {
        log.trace("Ulazak u generatePerson");

        while (true) {
            try {
                System.out.print("Odaberi tip korisnika (1. User, 2. Trener): ");
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> {
                        log.info("Generiramo User-a");
                        return UserService.generateUser(sc);
                    }
                    case 2 -> {
                        log.info("Generiramo Coach-a");
                        return CoachService.generateCoach(sc);
                    }
                    default -> {
                        System.out.println("Nepoznat izbor, unosimo User po defaultu.");
                        return UserService.generateUser(sc);
                    }
                }

            } catch (InvalidOibException | IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
                log.error(ex.getMessage());
            }
        }
    }


    /**
     * Grupiranje osoba po tipu (User ili Coach) i ispis ovisno o korisničkom izboru.
     *
     * @param osobe skup osoba koje sadrže instance klasa {@link User} i {@link Coach}
     * @param sc Scanner za unos korisničkog izbora
     */
    public static void groupPeople(Set<? extends Person> osobe, Scanner sc) {
        log.trace("Ulazak u metodu groupPeople");
        log.info("Grupiramo osobe po tipu (User/Coach).");

        Map<String, List<Person>> grouped = osobe.stream()
                .collect(Collectors.groupingBy(p -> {
                    if (p instanceof Coach) {
                        return "Coach";
                    } else {
                        return "User";
                    }
                }));

        System.out.println("Želite li vidjeti popis korisnika ili trenera?");
        System.out.println("1. Korisnici");
        System.out.println("2. Treneri");
        System.out.print("Odabir >> ");

        Integer choice = 0;
        while (choice != 1 && choice != 2) {
            try {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice != 1 && choice != 2) {
                    System.out.println("Nevažeći unos! Pokušajte ponovo.");
                }
            } catch (InputMismatchException ime) {
                System.out.println("Unijeli ste slovo umjesto broja! Pokušajte ponovo.");
                log.error(ime.getMessage());
                sc.nextLine();
            }
        }

        String key = (choice == 1) ? "User" : "Coach";
        List<Person> selectedGroup = grouped.getOrDefault(key, new ArrayList<>());

        selectedGroup.stream().sorted(Comparator.comparing(Person::getFirstName)); //sortiranje po imenu

        if (selectedGroup.isEmpty()) {
            System.out.println("Nema pronađenih osoba za odabrani tip!");
        } else {
            System.out.println("--- Popis " + (choice == 1 ? "korisnika" : "trenera") + " ---");
            selectedGroup.sort(
                    Comparator.comparing(
                            Person::getLastName,
                            Comparator.nullsLast(String::compareTo)
                    )
            );
            for (Person p : selectedGroup) {
                if (p instanceof User user) {
                    System.out.println("User: " + user.getFirstName() + " " + user.getLastName());
                } else if (p instanceof Coach coach) {
                    System.out.println("Coach: " + coach.getFirstName() + " " + coach.getLastName());
                }
            }
        }


        log.trace("Izlazak iz metode groupPeople");
    }

    // Ovo ce mi koristiti u JAVA FX
    public static <T extends Person & Comparable<T>> void printSortedPeople(List<T> people) {
        people.stream().sorted().forEach(Person::printPersonalData);
    }





}
