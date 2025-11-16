package entity.hall;

import entity.booking.Booking;
import entity.person.coach.Coach;
import entity.person.Person;
import entity.person.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class HallService {
    static Logger log = LoggerFactory.getLogger(HallService.class);

    public static List<Hall> generateHalls(Integer numberOfHalls, Scanner sc) {
        List<Hall> dvorane = new ArrayList<>();
        for (int i = 0; i < numberOfHalls; i++) {
            try {
                System.out.println("Unos dvorane " + (i + 1));
                dvorane.add(generateHall(sc));
            } catch (InvalidHallCapacity e) {
                System.out.println(e.getMessage() + " Pokušajte ponovno.");
                i--; // ponovi unos iste dvorane
            }
        }
        return dvorane;
    }
    /**
     * Generainje singularne dvorane i vraćanje iste u main
     *
     * Od korisnika se traći unos parametara za dvoranu, uz provjeru kapaciteta te par
     * sitnih provjera unosa sa konzole.
     *
     * @param sc - Scanner se šalje kroz parametar a definiran je napočetku u metodi main.
     * @return vraća singularni objekt tipa "Hall"
     * @throws InvalidHallCapacity provjerava je li unos za kapacitet koji je korisnik unio manji od 500
     */
    public static Hall generateHall(Scanner sc) throws InvalidHallCapacity
    {
        log.trace("Ulazak u metodu generateHall");
        log.info("Generiramo dvorane.");
        System.out.print("Unesi ime dvorane: ");
        String tempName = sc.nextLine();
        System.out.println("Unesi broj vrata:");
        String tempDoorNumber = sc.nextLine();
        Integer tempCapacity=0;
        boolean uspjeh=false;
        while(!uspjeh)
        {
            while (true)
            {
                try
                {
                    System.out.println("Unesi kapactitet dvorane:");
                    tempCapacity = sc.nextInt();
                    break;
                }
                catch (InputMismatchException ime)
                {
                    log.error(ime.getMessage());
                    System.out.println("Unijeli ste slovo! Pokušajte ponovo.");
                    sc.nextLine();
                }
            }
            if(tempCapacity>500)
            {
                throw new InvalidHallCapacity("Unijeli ste preveliki kapacitet za dvoranu!");
            }
            else if(tempCapacity<=0)
            {
                throw new InvalidHallCapacity("Vrijednost kapaciteta dvorena ne može biti negativan!");
            }
            uspjeh=true;
        }
        sc.nextLine();
        log.trace("Ulazak u metodu unesiSport");
        SportType tempSport = SportType.choseSport(sc);
        log.trace("Izlazak iz metode generateHall");
        return new Hall(tempName,tempDoorNumber,tempCapacity,tempSport);
    }

    /**
     * Pridruživanje korisnika iz niza osobe({@link User}) postojećim bookinzima u nizu osobe({@link Coach}).
     *
     *Traži se da se prvotno odabere određeni korisnik, a kasnije i trener kod kojeg su nam ponuđeni
     * svi njegovi treninzi koji nas onda opet tjeraju na odabir. Koristimo instaciranje odnosno pattern matching
     * kako bi razdjelili niz osobe na trenere i usere.
     *
     * @param osobe sadrži instance dviju različitih klasa ({@link User}/{@link Coach}) koje koristimo u dva navarata u klasi.
     * @param sc Scanner se šalje kroz parametar a definiran je napočetku u metodi main.
     */
    public static void joinBooking(Set<Person> osobe, Scanner sc) {
        log.trace("Ulazak u metodu joinBooking");
        log.info("Pridužujemo booking korisniku.");
        String choiceUserContinue="";
        User chosenUser;
        Coach chosenCoach;
        do
        {
            log.info("Odabir korinsika.");
            System.out.println("Odaberi korisnika:");

            Integer userCount = 0;

            List<User> choiceUsers = osobe.stream().filter(p->p instanceof User)
                    .map(u->(User) u)
                    .collect(Collectors.toList());

            for (Integer i = 0; i < choiceUsers.size(); i++) {

                User tempUser = choiceUsers.get(i);
                System.out.println((i+1)+ ". " + tempUser.getOIB() + " " + tempUser.getFirstName() + " " + tempUser.getLastName());

            }

            try
            {
                System.out.print("Odabir >> ");
                Integer chosenUserIndex = sc.nextInt() - 1;
                sc.nextLine();
                chosenUser = choiceUsers.get(chosenUserIndex);
            }
            catch (InputMismatchException ime)
            {
                System.out.println("Unijeli ste slovo umjesto broja! Pokušajte ponovo.");
                log.error(ime.getMessage());
                sc.nextLine();
                continue;
            }
            catch (ArrayIndexOutOfBoundsException aioobe)
            {
                log.error(aioobe.getMessage());
                System.out.println("Unijeli ste nevažeći broj! Pokušajte ponovo.");
                continue;
            }

            log.info("Odabir trenera");
            System.out.println("Odaberi trenera:");
            List<Coach> choiceCoaches = osobe.stream().filter(p->p instanceof Coach).
                    map(p-> (Coach) p).collect(Collectors.toList());

            for (Integer i = 0; i < choiceCoaches.size(); i++) {
                Coach tempCoach = choiceCoaches.get(i);
                System.out.println((i+1)+". " + tempCoach.getOIB() + " " + tempCoach.getFirstName() + " " + tempCoach.getLastName());

            }
            try
            {
                System.out.print("Odabir >> ");
                Integer chosenCoachIndex = sc.nextInt() - 1;
                sc.nextLine();
                chosenCoach = choiceCoaches.get(chosenCoachIndex);
            }
            catch (InputMismatchException ime)
            {
                log.error(ime.getMessage());
                System.out.println("Unijeli ste slovo umjesto broja! Pokušajte ponovo.");
                sc.nextLine();

                continue;
            }
            catch (ArrayIndexOutOfBoundsException aioobe)
            {
                log.error(aioobe.getMessage());
                System.out.println("Unijeli ste nevažeći broj! Pokušajte ponovo.");
                continue;
            }
            log.info("Odabir booking-a.");
            System.out.println("Odaberi booking:");
            for (Integer i = 0; i < chosenCoach.getMyBookings().length; i++) {
                if (chosenCoach.getMyBookings()[i] != null) {
                    System.out.println((i + 1) + ". " + chosenCoach.getMyBookings()[i]);
                }
            }
            try
            {
                System.out.print("Odabir >> ");
                Integer chosenBookingIndex = sc.nextInt() - 1;
                sc.nextLine();

                Booking chosenBooking = chosenCoach.getMyBookings()[chosenBookingIndex];
                chosenUser.joinBooking(chosenBooking);
            }catch (InputMismatchException ime)
            {

                System.out.println("Unijeli ste slovo umjesto broja! Pokušajte ponovo.");
                sc.nextLine();
                log.error(ime.getMessage());
                continue;
            }
            catch (ArrayIndexOutOfBoundsException aioobe)
            {
                log.error(aioobe.getMessage());
                System.out.println("Unijeli ste nevažeći broj! Pokušajte ponovo.");
                continue;
            }

            System.out.println("Želiš li nastaviti dalje(da/ne)?");
            choiceUserContinue = sc.nextLine();

        }while("DA".equalsIgnoreCase(choiceUserContinue));
        log.trace("Izlazak iz metode joinBooking");
    }
}
