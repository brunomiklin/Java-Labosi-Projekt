package entity.search;

import entity.hall.Hall;
import entity.person.coach.Coach;
import entity.person.Person;
import entity.person.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class SearchService {
    static Logger log = LoggerFactory.getLogger(SearchService.class);
    /**
     * Pretraga niza "osobe" ovisno o parametrima koje korisnik unese.
     *
     *Od korisnika se traži odabir jednog od dva parametra "Ime/Prezime", u metodi se ovisno o odabiru
     * radi jedno od dvije mogućnosti, odnosno traži jednakost "Imena/Prezimena" nekog objekta iz "osobe" klasa {@link Person}
     * i Stringa kojeg je korisnik unio.
     *
     * @param osobe sadrži instance dviju različitih klasa ({@link User}/{@link Coach}), imajući to na umu ispisat će se različita poruka
     * @param sc - Scanner se šalje kroz parametar a definiran je napočetku u metodi main
     */
    public static void searchUsers(Set<Person> osobe, Scanner sc) {
        log.trace("Ulazak u metodu searchUsers.");
        log.info("Preražujemo korisnike.");

        System.out.println("Pretraži korisnika po imenu/prezimenu: ");
        System.out.println("1. ime");
        System.out.println("2. prezime");
        System.out.print("Odabir >> ");
        Integer choice = 0;

        while (choice != 1 && choice != 2) { {
            try {
                System.out.print("Odabir >> ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice != 1 && choice != 2) {
                    System.out.println("Krivo ste unjeli odabir!");
                    choice = 0;

                }
            } catch (InputMismatchException ime) {
                log.error(ime.getMessage());
                System.out.println("Unijeli ste slovo umjesto broja! Pokušajte ponovo.");
                sc.nextLine();
            }
        }


            switch (choice) {
                case 1 -> System.out.print("Unesi ime:");
                case 2 -> System.out.print("Unesi prezime:");
            }
            String input=sc.nextLine();


            final Integer finalChoice = choice;

            List<Person> results = osobe.stream()
                    .filter(p -> (finalChoice == 1 && p.getFirstName().equalsIgnoreCase(input)) ||
                            (finalChoice == 2 && p.getLastName().equalsIgnoreCase(input)))
                    .collect(Collectors.toList());

            if (results.isEmpty()) {
                System.out.println("Nije pronađen korisnik!");
            } else {
                for (Person p : results) {
                    if (p instanceof User user) {
                        System.out.println("Pronađen korisnik: " + user.getFirstName() + " " + user.getLastName());
                        user.allMyBookings();
                    } else if (p instanceof Coach coach) {
                        System.out.println("Pronađen trener: " + coach.getFirstName() + " " + coach.getLastName());
                        coach.printMyBookings();
                    }
                    System.out.println("---------------");
                }
            }

        }
        log.trace("Izlazak iz metode serachUsers.");
    }

    /**
     * Pretraga niza tipa "{@link Hall}" ovisno o parametrima koje korisnik unese.
     *
     *  Od korisnika se traži odabir jednog od dva parametra "Najveći/Najmanji", u metodi se ovisno o odabiru
     *  radi jedno od dvije mogućnosti, odnosno traži "Najmanju/Najveću" dvoranu po kapacitetu unutar niza
     *  "halls".
     *
     *  @param halls niz tipa "Hall" sadrži popis dvorana unesenih pomocu metodu "generateHalls"
     * @param sc Scanner se šalje kroz parametar a definiran je napočetku u metodi main.
     */
    public static void searchHalls(List<Hall> halls,Scanner sc)
    {
        log.trace("Ulazak u metodu searchHalls");
        log.info("Preražujemo dvorane.");
        System.out.println("Pronađi dvoranu najvećeg/najmanjeg kapaciteta nekog proizvoda: ");
        System.out.println("1. najveći");
        System.out.println("2. najmanji");
        System.out.print("Odabir >> ");
        Integer choice = 0;

        while (choice != 1 && choice != 2) {
            try {
                System.out.print("Odabir >> ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice != 1 && choice != 2) {
                    System.out.println("Krivi unos, pokušajte ponovno.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Unijeli ste nevažeći unos! Pokušajte ponovno.");
                sc.nextLine();
            }
        }

        Hall targetHall = new Hall();

        switch (choice)
        {
            case 1 -> targetHall = halls.stream().max(Comparator.comparingInt(Hall::getCapacity)).orElse(null);
            case 2 -> targetHall = halls.stream().min(Comparator.comparingInt(Hall::getCapacity)).orElse(null);
        }


        if(targetHall!=null)
        {
            if(choice==1)
            {
                System.out.println("Najveći kapacitet ima dvorana: " + targetHall.toString());
            }
            else
            {
                System.out.println("Najmanji kapacitet ima dvorana: " + targetHall.toString());
            }

        }
        else
        {
            System.out.println("Nije pronađena nijedna dvorana!");
        }

        log.trace("Izlazak iz metode searchHalls");

    }
    /**
     * Particiranje osoba po emailu (ako korisnika ima ili nema email).
     *
     *Ispisuje korisnike za koje smo unijeli mail i one koje nismo.
     */
    public static void partitioningByEmail(Set<Person> osobe)
    {
        Map<Boolean,List<Person>> resultat = osobe.stream().collect(Collectors.partitioningBy(p->p.getEmail()!=null && p.getEmail().isEmpty()));

        System.out.println("Osobe s mailom:");
        resultat.get(true).forEach(p-> System.out.println(p.getEmail() + " - " + p.getFirstName() + " " + p.getLastName()));

        System.out.println("Osobe bez maila:");
        resultat.get(false).forEach(p-> System.out.println(p.getFirstName() + " " + p.getLastName()));
    }
}
