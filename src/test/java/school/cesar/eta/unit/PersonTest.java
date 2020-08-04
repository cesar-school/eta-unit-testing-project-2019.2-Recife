package school.cesar.eta.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PersonTest {


    private Person person;

    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow()  {
        person = new Person();
        person.setName("Jon");
        person.setLastName("Snow");
        String fullName = person.getName();
        Assertions.assertEquals("JonSnow", fullName);
    }

    @Test
    public void getName_firstNameJonNoLastName_jon() {
        person = new Person();
        person.setName("Jon");
        String noSnow = person.getName();
        Assertions.assertEquals("Jon", noSnow);
    }

    @Test
    public void getName_noFirstNameLastNameSnow_snow() {
        person = new Person();
        person.setLastName("Snow");
        String noFirstName = person.getName();
        Assertions.assertEquals("Snow", noFirstName);
    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException() {
        person = new Person();
        //Assertions.assertThrows("Name must be filled", ()-> person.getName());

    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {
        person = new Person();
        person.setBirthday(LocalDate.parse("1988-09-02"));
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        person = new Person();
        person.setBirthday(LocalDate.parse("1988-08-02"));
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        person = new Person();
        person.setBirthday(LocalDate.parse("1988-08-04"));
        Assertions.assertTrue(person.isBirthdayToday());
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
        fail();
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
        fail();
    }

    @Test
    public void isFamily_nonRelativePerson_false() {
        fail();
    }

    @Test
    public void isFamily_relativePerson_true() {
        fail();
    }
}
