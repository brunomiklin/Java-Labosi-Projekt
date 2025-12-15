package entity.person.user;

import entity.person.InvalidOibException;
import entity.person.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class UserService {
    static Logger log = LoggerFactory.getLogger(UserService.class);
    private final Scanner sc;
    public UserService(Scanner sc) {
        this.sc = sc;
    }
    /**
     *Generianje korisnika tipa "{@link User}" kojeg spremamo u objekt tipa {@link Person} (polimorfizam)
     *
     * Od korisnika se traži unos parametara za klasu "User" te se nudi još dodatnih mogućnosti
     * unosa koje se mogu preskoćiti određenim unusom u konzoli. Nakon unosa poziva se BuilderPattern
     * iz klase User koji se preko nasljeđivanje wrappa u klasu "Person" te vraća u main klasu kao gotovi
     * objekt.
     *
     * @param sc - Scanner se šalje kroz parametar a definiran je napočetku u metodi main.
     * @return vraća objekt tipa "Person" napravljen pomoću User.BuilderPattern-a.
     * @throws InvalidOibException - provjera korisnikovog unosa za OIB (veličina mu mora biti toćno 11).
     */
    public static Person generateUser(Scanner sc) throws InvalidOibException
    {
        log.trace("Ulazak u metode generateUser");

        log.info("Generiramo korisnike.");
        System.out.print("Unesi OIB: ");
        String tempOIB = sc.nextLine();
        String tempFirstName,tempLastName;
        if(tempOIB.length()!=11)
        {
            throw new InvalidOibException("OIB mora imati točno 11 znakova!");
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
        String tempEmail="";
        String tempPhoneNumber="";
        String tempUsername="";
        String tempPassword="";
        System.out.println("Želiš li dodati email? (da/ne)");
        String choice =  sc.nextLine();
        if("DA".equalsIgnoreCase(choice))
        {
            while (true) {
                System.out.print("Unesi mail:");
                tempEmail = sc.nextLine();

                if (tempEmail == null || tempEmail.isEmpty()) {
                    throw new IllegalArgumentException("Email ne može biti prazan!");
                }

                if (!tempEmail.contains("@")) {
                    throw new IllegalArgumentException("Email mora sadržavati znak @!");
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
        System.out.println("Želiš li dodati username i password? (da/ne)");
        choice = sc.nextLine();
        if ("DA".equalsIgnoreCase(choice)) {

            System.out.print("Unesi username: ");
            tempUsername = sc.nextLine();
            System.out.print("Unesi password: ");
            tempPassword = sc.nextLine();
        }

        Person tempUser = new User.UserBuilder(tempOIB,tempFirstName,tempLastName)
                .username(tempUsername.isEmpty()?"":tempUsername)
                .password(tempPassword.isEmpty()?"":tempPassword)
                .email(tempEmail.isEmpty()?"":tempEmail)
                .phoneNumber(tempPhoneNumber.isEmpty()?"":tempPhoneNumber)
                .build();


        log.trace("Izlazak iz metode generateUser");

        return tempUser;
    }


    // Trebat ce mi kod JAVAFX
    public static void addUsertToList(List<? super User> listUsera,User user)
    {
        listUsera.add(user);
    }

}
