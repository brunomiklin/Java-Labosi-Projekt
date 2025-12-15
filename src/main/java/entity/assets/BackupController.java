package entity.assets;

import entity.hall.Hall;
import entity.person.Person;

import java.io.*;
import java.util.List;
import java.util.Set;

public class BackupController  {


    public static void saveBackup(Set<Person> osobe, List<Hall> dvorane)
    {
        Backupdata backupdata = new Backupdata(osobe,dvorane);

        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("files/backup.bin"))) {

            output.writeObject(backupdata);
            System.out.println("Backup uspješno kreiran!\n");

        }catch (IOException ioe)
        {
            System.err.println("Greška kod pohrane backupa!");
            ioe.printStackTrace();
        }

    }
    public static Backupdata loadBackup()
    {
        Backupdata backupdata = new Backupdata();
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("files/backup.bin"))){
             backupdata = (Backupdata) input.readObject();

            System.out.println("Backup uspješno učitan!\n");

        }catch (IOException | ClassNotFoundException e)
        {
            System.out.println("Grška kod učitavanja backup-a");
        }


        return backupdata;
    }
}
