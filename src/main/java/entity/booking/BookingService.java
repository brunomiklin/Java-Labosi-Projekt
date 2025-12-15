package entity.booking;

import entity.person.coach.Coach;
import entity.hall.Hall;
import entity.person.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class BookingService {
    static Logger log = LoggerFactory.getLogger(BookingService.class);
    /**
     * Kreiranje bookinga pomoću niza osoba (trenera) i dvorana.
     *
     * Metoda uzima niz osoba iz kojih instancira trenere te traži od korisnika
     * odabir određenog trenera. Odabrani trener se kasnije povezuje sa dvoranom te
     * u zajedno čine određenu cijelinu koju nazivamo booking.
     *
     * @param osobe niz svih korisnika i trenera (u ovoj metodi koristimo samo instance klase {@link Coach}).
     * @param sc Scanner se šalje kroz parametar a definiran je napočetku u metodi main.
     * @param dvorane niz svih dostupnih dvorana kreiranih u metodi main.
     */
    public static void createBooking(Set<? extends Person> osobe, Scanner sc, List<Hall> dvorane) {
        log.trace("Ulaz u metodu createBooking.");
        log.info("Kreiramo booking.");
        String choice="";
        do {
            log.info("Odabir trenera.");
            System.out.println("Odaberi trenera:");
            Integer choiceHall,cohiceCoach;
            List<Coach> choiceCoaches = osobe.stream()
                    .filter(p->p instanceof Coach)
                    .map(p->(Coach) p)
                    .collect(Collectors.toList()); //vraća modified listu

            choiceCoaches.sort(Comparator.comparing(Coach::getLastName).reversed()); //sortiranje trenera po prezimenu
            for(Integer i=0;i<choiceCoaches.size();i++)
            {
                Coach tempCoach = choiceCoaches.get(i);
                System.out.println((i+1) + "." + tempCoach.getFirstName() + " " + tempCoach.getLastName());
            }
            try
            {
                System.out.print("Odabir >>");
                cohiceCoach = sc.nextInt();
                sc.nextLine();
                if (cohiceCoach < 1 || cohiceCoach > choiceCoaches.size()) {
                    System.out.println("Nevažeći izbor trenera! Pokušajte ponovo!");
                    log.warn("Korisnik je pokušao odabrati trenera koji nepostoji!");
                    continue;

                }
            }   catch (InputMismatchException ime)
            {
                System.out.println("Unijeli ste slovo umjesto broja!");
                log.error(ime.getMessage());

                continue;
            }
            dvorane.sort(Comparator.comparingInt(Hall::getCapacity).reversed()); // prikaz dvorana od veće prema manjoj
            do {
                log.info("Odabir dvorana.");
                System.out.println("Odberi dvoranu:");
                for (Integer i = 0; i < dvorane.size(); i++) {
                    System.out.println((i + 1) + ". " + dvorane.get(i).toString());

                }
                try {
                    System.out.print("Odabir >> ");
                    choiceHall = sc.nextInt();
                    sc.nextLine();
                    if (choiceHall < 1 || choiceHall > dvorane.size()) {
                        System.out.println("Nevažeći izbor dvorane! Pokušajte ponovo!");
                        log.warn("Korisnik je pokušao odabrati dvoranu koja nepostoji!");
                        continue;
                    }
                } catch (InputMismatchException ime) {
                    log.error(ime.getMessage());
                    System.out.println("Unijeli ste slovo umjesto broja!");
                    sc.nextLine();
                    continue;
                }
                LocalDateTime datum = null;
                while (true) {
                    try {
                        log.info("Unos datuma i vremena.");
                        System.out.print("Unesi datum i vrijeme (dd.MM.yy HH:mm): ");
                        String datumString = sc.nextLine();
                        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
                        datum = LocalDateTime.parse(datumString, fmt);
                        break;
                    } catch (DateTimeParseException dtpe) {
                        log.error(dtpe.getMessage());
                        System.out.println("Unijeli ste krivi format! Pokušajte ponovo!");
                    }
                }
                log.info("Unos duljine trajanja treninga (u minutama).");
                while(true)
                {
                    try
                    {
                        System.out.print("Unesi duljinu trajanja treninga (u minutama): ");
                        Integer trainingTime = sc.nextInt();
                        sc.nextLine();
                        choiceCoaches.get(cohiceCoach-1).createBooking(dvorane.get(choiceHall - 1), datum, trainingTime * 60);
                        break;
                    }
                    catch (ArrayIndexOutOfBoundsException aiofbe)
                    {
                        System.out.println("Desila se greška pristupa trenerima!");
                        sc.nextLine();
                        log.error(aiofbe.getMessage());

                    }
                    catch (InputMismatchException ime)
                    {
                        System.out.println("Unijeli ste broj za minute! Pokušajte ponovo.");
                        sc.nextLine();
                        log.error(ime.getMessage());
                    }
                }
                System.out.println("Želite li dodati još bookinga za trenra " + choiceCoaches.get(cohiceCoach - 1).getFirstName() + " (da/ne)?");
                choice = sc.nextLine();
            } while ("DA".equalsIgnoreCase(choice));
            System.out.println("Želite li nastaviti s kreiranjem bookinga (da/ne)?");
            choice = sc.nextLine();
        } while ("DA".equalsIgnoreCase(choice));
        log.trace("Izlaz iz metode createBooking");
    }
}
