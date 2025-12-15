package entity.hall;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

public enum SportType implements Serializable {
    NOGOMET,RUKOMET,TENNIS,KOSARKA,PLIVANJE,ODBOJKA,JOGA,WELNES;

    /**
     * Omogućuje korisniku da odabere sport iz definirane liste {@link SportType} enum vrijednosti.
     *
     * Metoda ispisuje sve moguće sportove prema njihovim vrijednostima u enum-u te traži unos
     * korisnika kao brojčani indeks. Unos se provjerava na ispravnost i ponavlja dok korisnik ne
     * unese valjanu vrijednost.
     *
     * @param sc Scanner koji se koristi za unos s konzole.
     * @return Odabrani sport kao vrijednost tipa {@link SportType}.
     */
    public static SportType choseSport(Scanner sc)
    {
        SportType[] sports = SportType.values();
        System.out.println("Odaberite sport za koji je namijenjena dvorana:");
        for (int i = 0; i < sports.length; i++) {
            System.out.println((i + 1) + ". " + sports[i]);
        }
        Integer choice = 0;
        while (choice < 1 || choice > sports.length) {
            try {
                System.out.print("Odabir (1-" + sports.length + "): ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 1 || choice > sports.length) {
                    System.out.println("Nevažeći unos, pokušajte ponovno.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Unijeli ste nevažeći unos, pokušajte ponovo.");
                sc.nextLine();
            }
        }
        return sports[choice - 1];
    }
}
