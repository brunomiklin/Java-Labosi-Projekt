package entity.person.coach;

import entity.person.InvalidOibException;
import entity.person.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Gatherers;

public class CoachService {
    static Logger log = LoggerFactory.getLogger(CoachService.class);
    /**
     *Generianje korisnika tipa "{@link Coach}" kojeg spremamo u objekt tipa {@link Person} (polimorfizam)
     *
     * Od korisnika se traži unos parametara za klasu "Coach" te se nudi još dodatnih mogućnosti
     * unosa koje se mogu preskoćiti određenim unusom u konzoli. Nakon unosa poziva se BuilderPattern
     * iz klase Coach koji se preko nasljeđivanje wrappa u klasu "Person" te vraća u main klasu kao gotovi
     * objekt.
     *
     * @param sc - Scanner se šalje kroz parametar a definiran je napočetku u metodi main
     * @return vraća objekt tipa "Person" napravljen pomoću Coach.BuilderPattern-a
     * @throws InvalidOibException - provjera korisnikovog unosa za OIB (veličina mu mora biti toćno 11)
     */
    public static Person generateCoach(Scanner sc) throws InvalidOibException{
        log.trace("Ulazak u metodu generateCoach");
        log.info("Generiramo trenere.");

        String tempSpecialization = "";
        String tempPhoneNumber = "";
        String tempEmail = "";
        String tempFirstName,tempLastName;
        System.out.print("Unesi OIB: ");
        String tempOIB = sc.nextLine();
        if(tempOIB.length()!=11)
        {
            throw new InvalidOibException("Oib mora imati točno 11 znakova!");
        }
        while(true)
        {
            System.out.print("Unesi ime: ");
            tempFirstName = sc.nextLine();
            if (tempFirstName.matches(".*\\d.*")) {
                throw new IllegalArgumentException("Ime ne smije sadržavati broj!");

            }
            System.out.print("Unesi prezime: ");
            tempLastName = sc.nextLine();
            if (tempLastName.matches(".*\\d.*")) {
                throw new IllegalArgumentException("Prezime ne smije sadržavati broj!");

            }
            break;
        }

        String choice;
        System.out.println("Želiš li dodati email? (da/ne)");
        choice = sc.nextLine();
        if ("DA".equalsIgnoreCase(choice)) {

            while (true) {
                System.out.print("Unesi mail:");
                tempEmail = sc.nextLine();
                if (tempEmail == null || tempEmail.isEmpty()) {
                    System.out.println("Email ne može biti prazan!");
                    continue;
                } else if (!tempEmail.contains("@")) {
                    System.out.println("Email mora sadržavati @");
                    continue;
                }
                break;
            }

        }
        System.out.println("Želiš li dodati telefon? (da/ne)");
        choice = sc.nextLine();
        if ("DA".equalsIgnoreCase(choice)) {

            System.out.print("Unesi telefon: ");
            tempPhoneNumber = sc.nextLine();
        }
        System.out.println("Želiš li dodati specijaliciju? (da/ne): ");
        choice = sc.nextLine();
        if ("DA".equalsIgnoreCase(choice)) {

            System.out.print("Unesi specijalizaciju: ");
            tempSpecialization = sc.nextLine();
        }

        Person tempCoach = new Coach.CoachBuilder(tempOIB,tempFirstName,tempLastName)
                .specialization(tempSpecialization.isEmpty()?"":tempSpecialization)
                .email(tempEmail.isEmpty()?"":tempEmail)
                .phoneNumber(tempPhoneNumber.isEmpty()?"":tempPhoneNumber)
                .build(); //nebu radilo ako nije gore Person tip



        log.trace("Izlazak iz metode generateCoach");
        return tempCoach;
    }


    /**
     *
     * @param osobe
     */
    public static void printTop3CoachesByBookings(Set<Person> osobe) {
        List<List<Coach>> top3Coaches = osobe.stream()
                .filter(p->p instanceof Coach)
                .map(p->(Coach)p)
                .sorted((c1, c2) -> Integer.compare(c2.getMyBookings().length, c1.getMyBookings().length))
                .gather(Gatherers.windowFixed(3))
                .toList();



        System.out.println("Top 3 trenera po broju zakazanih termina:");
        for (List<Coach> window : top3Coaches) {
            for (Coach coach : window) {
                System.out.println(coach.getFirstName() + " " + coach.getLastName()
                        + " - broj termina: " + coach.getMyBookings().length);
            }
            System.out.println("---"); // razdvoji "prozor" radi čitljivosti
        }

        log.trace("Izlaz iz metode main.");
    }


}
