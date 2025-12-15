package entity.person;

import entity.person.coach.Coach;
import entity.person.user.User;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * Predstavlja generički tip osobe sa osobnim podacima i ponašanjima.
 * Služi kao šablona za klase poput {@link Coach} i {@link User}.
 */
public abstract class Person implements Serializable {

    protected String OIB;
    protected String firstName;
    protected String lastName;


    protected String email;
    protected String phoneNumber;

    /**
     * Konstruktor koji se koristi iz PersonBuilder-a
     */
    protected Person(PersonBuilder builder) {
        this.OIB = builder.OIB;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email == null ? "" : builder.email;
        this.phoneNumber = builder.phoneNumber == null ? "" : builder.phoneNumber;

    }

    /**
     * Obavezan prazan konstruktor za pravilnu deserializaciju
     */
    public Person() {
        this.email = "";
        this.phoneNumber = "";
    }



    public void setOIB(String OIB) {
        this.OIB = OIB;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = (email == null ? "" : email);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = (phoneNumber == null ? "" : phoneNumber);
    }


    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    public Optional<String> getPhoneNumber() {
        return Optional.ofNullable(phoneNumber);
    }

    public String getOIB() {
        return OIB;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public abstract static class PersonBuilder {
        private final String OIB;
        private final String firstName;
        private final String lastName;

        private String email;
        private String phoneNumber;

        public PersonBuilder(String OIB, String firstName, String lastName) {
            this.OIB = OIB;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public PersonBuilder email(String email) {
            this.email = email;
            return this;
        }

        public PersonBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public abstract Person build();
    }



    public abstract void printPersonalData();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return OIB.equals(person.OIB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(OIB);
    }
}
