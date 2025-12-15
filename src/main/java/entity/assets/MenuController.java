package entity.assets;

import entity.booking.BookingService;
import entity.hall.Hall;
import entity.hall.HallService;
import entity.person.Person;
import entity.person.PersonService;
import entity.person.coach.CoachService;
import entity.search.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MenuController {

    static Logger log = LoggerFactory.getLogger(MenuController.class);
    public static void menu(Set<Person> osobe, List<Hall> dvorane, Scanner sc) {
        Integer odabir=0;

        do {
            System.out.println("\uD83D\uDCDD Meni:\n");

            System.out.println("|1| Dodaj osobu");
            System.out.println("|2| Dodaj dvoranu");
            System.out.println("|3| Kreiraj booking");
            System.out.println("|4| Pridruži korisnika bookingu");
            System.out.println("|5| Pretraga");
            System.out.println("|6| Statistika");
            System.out.println("|7| Ispiši 'LogXML'");
            System.out.println("|8| Save backup");
            System.out.println("|9| Load backup");
            System.out.println("|0| Izlaz");
            System.out.print("Odabir >> ");
            odabir = sc.nextInt();
            sc.nextLine();

            switch (odabir) {

                case 1 -> {
                    XmlLogger.log("Dodavanje osobe.");
                    log.info("Dodavanje nove osobe...");
                    osobe.add(PersonService.generatePerson(sc));
                    log.info("Osoba uspješno dodana. Trenutno osoba: {}", osobe.size());
                }

                case 2 -> {
                    XmlLogger.log("Dodavanje dvorane.");
                    log.info("Dodavanje nove dvorane...");
                    dvorane.add(HallService.createHall(sc));
                    log.info("Dvorana uspješno dodana. Trenutno dvorana: {}", dvorane.size());
                }

                case 3 -> {
                    XmlLogger.log("Kreiranje bookinga.");
                    log.info("Kreiranje booking-a...");
                    BookingService.createBooking(osobe, sc, dvorane);
                    log.info("Proces kreiranja booking-a završen.");
                }

                case 4 -> {
                    XmlLogger.log("Pridruživanje korisnika bookingu..");
                    log.info("Pridruživanje korisnika postojećem booking-u...");
                    HallService.joinBooking(osobe, sc);
                    log.info("Korisnik uspješno pridružen booking-u.");
                }

                case 5 -> {
                    XmlLogger.log("Pretraživanje.");
                    log.trace("Ulaz u podmeni PRETRAGA.");
                    System.out.println("\n \uD83D\uDD0D PRETRAGA \uD83D\uDD0E");
                    System.out.println("1. Pretraga osoba");
                    System.out.println("2. Pretraga dvorana");
                    System.out.print("Odabir >> ");

                    Integer o = sc.nextInt();
                    log.info("Korisnik odabrao opciju pretrage: {}", o);

                    switch (o) {
                        case 1 -> {
                            XmlLogger.log("Odabrana je pretraga osoba");
                            log.info("Pokrećemo pretragu osoba...");
                            SearchService.searchUsers(osobe, sc);
                        }
                        case 2 -> {
                            XmlLogger.log("Odabrana je pretraga dvorana");
                            log.info("Pokrećemo pretragu dvorana...");
                            SearchService.searchHalls(dvorane, sc);
                        }
                        default -> {
                            XmlLogger.log("Odabrana je nepoznata opcija pretrage");
                            log.warn("Nepoznata opcija pretrage: {}", o);
                            System.out.println("Nepoznata opcija!");
                        }
                    }
                }

                case 6 -> {
                    XmlLogger.log("Statistika");
                    log.trace("Ulaz u podmeni STATISTIKA.");

                    System.out.println("\n \uD83D\uDCC8 STATISTIKA \uD83D\uDCC9 \n");
                    System.out.println("1. Grupiranje osoba po tipu");
                    System.out.println("2. Particioniranje po emailu");
                    System.out.println("3. Top 3 trenera");
                    System.out.print("Odabir >> ");

                    Integer o = sc.nextInt();
                    sc.nextLine();
                    log.info("Korisnik odabrao opciju statistike: {}", o);

                    switch (o) {
                        case 1 -> {
                            XmlLogger.log("Grupiranje osoba");
                            log.info("Grupiranje osoba...");
                            PersonService.groupPeople(osobe, sc);
                        }
                        case 2 -> {
                            XmlLogger.log("Particioniranje osoba prema emailu");
                            log.info("Particioniranje osoba prema emailu...");
                            SearchService.partitioningByEmail(osobe);
                        }
                        case 3 -> {
                            XmlLogger.log("Odabran je ispis top 3 trenera");
                            log.info("Ispis top 3 trenera...");
                            CoachService.printTop3CoachesByBookings(osobe);
                        }
                        default -> {
                            log.warn("Nepoznata opcija statistike: {}", o);
                            System.out.println("Nepoznata opcija!");
                        }
                    }
                }
                case 0 -> {
                    XmlLogger.log("Odabran je izlaz iz aplikacije");
                    log.info("Korisnik odabrao izlaz iz aplikacije.");
                }
                case 7 -> {
                    XmlLogger.log("Odabran je ispis logova");

                    XmlLogger.printLog();
                }
                case 8->{
                    XmlLogger.log("Odabrano je spremanje backup-a");

                    BackupController.saveBackup(osobe,dvorane);
                }
                case 9 ->{
                    XmlLogger.log("Odabrano je ucitavanje backup-a");
                    Backupdata backupdata = BackupController.loadBackup();
                    osobe.clear();
                    dvorane.clear();
                    osobe.addAll(backupdata.getOsobe());
                    dvorane.addAll(backupdata.getDvorane());
                }
                default -> {
                    log.warn("Nepoznata opcija u glavnom meniju: {}", odabir);
                    System.out.println("Nepoznata opcija! Pokušajte ponovno.");
                }
            }
        }while (odabir!=0 );
    }
}
