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
    public static Set<Person> generatePeople(Scanner sc, int number_of_users) {
            {
                Set<Person> osobe = new HashSet<>();

                for (Integer i = 0; i < number_of_users; i++) {

                    log.trace("Iteracija kroz for-petlju (NUMBER_OF_USERS)");
                    while (true) {
                        try {
                            System.out.print("Odaberi tip korisnika (1. User, 2. Trener): ");
                            Integer choice = Integer.parseInt(sc.nextLine());
                            switch (choice) {
                                case 1 -> osobe.add(UserService.generateUser(sc));
                                case 2 -> osobe.add(CoachService.generateCoach(sc));
                                default -> {
                                    System.out.println("Nepoznat izbor, unosimo User po defaultu.");
                                    osobe.add(UserService.generateUser(sc));
                                }
                            };
                            log.info("Korisnik je uspješno kreiran!");
                            break;
                        } catch (InvalidOibException e) {
                            System.out.println(e.getMessage());
                            log.error(e.getMessage());
                        } catch (IllegalArgumentException iag) {
                            System.out.println(iag.getMessage());
                            log.error(iag.getMessage());
                        }


                    }
                }
                return osobe;
            }

        }

    /**
     * Grupiranje osoba po tipu (User ili Coach) i ispis ovisno o korisničkom izboru.
     *
     * @param osobe skup osoba koje sadrže instance klasa {@link User} i {@link Coach}
     * @param sc Scanner za unos korisničkog izbora
     */
    public static void groupPeople(Set<Person> osobe, Scanner sc) {
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
            selectedGroup.sort(Comparator.comparing(Person::getLastName)); // opcionalno sortiranje
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


}
