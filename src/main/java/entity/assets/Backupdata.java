package entity.assets;

import entity.hall.Hall;
import entity.person.Person;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Backupdata implements Serializable {
    
    private Set<Person> osobe;
    private List<Hall> dvorane;

    public Backupdata(){}
    public Backupdata(Set<Person> osobe, List<Hall> dvorane)
    {
        this.osobe = osobe;
        this.dvorane = dvorane;
    }

    public List<Hall> getDvorane() {
        return dvorane;
    }

    public Set<Person> getOsobe() {
        return osobe;
    }
}
