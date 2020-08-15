package school.cesar.eta.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.UnexpectedException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PersonTest {


    @BeforeEach
    public void setupTest() {
        person = new Person();
    };

    private Person person;
    private Person personOne;
    private Person personTwo;

    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow()  {
        person.setName("Jon");
        person.setLastName("Snow");
        Assertions.assertEquals("Jon",person.getFirstName());
        Assertions.assertEquals("Snow",person.getLastName());
        Assertions.assertEquals("JonSnow", person.getName());
    }

    @Test
    public void getName_firstNameJonNoLastName_jon() {
        person.setName("Jon");
        Assertions.assertEquals("Jon",person.getFirstName());
        Assertions.assertEquals("Jon",person.getName());
    }

    @Test
    public void getName_noFirstNameLastNameSnow_snow() {
        person.setLastName("Snow");
        Assertions.assertEquals("Snow",person.getLastName());
        Assertions.assertEquals("Snow",person.getName());
    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException() {
        Assertions.assertThrows(RuntimeException.class, () -> person.getName());
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {
        person.setBirthday(LocalDate.parse("1988-09-02"));
        Assertions.assertNotEquals(person.getBirthday().getMonth(),LocalDate.now().getMonth());
        Assertions.assertNotEquals(person.getBirthday().getDayOfMonth(),LocalDate.now().getDayOfMonth());
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        person.setBirthday(LocalDate.parse("1988-08-02"));
        Assertions.assertEquals(person.getBirthday().getMonth(),LocalDate.now().getMonth());
        Assertions.assertNotEquals(person.getBirthday().getDayOfMonth(),LocalDate.now().getDayOfMonth());
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        person.setBirthday(LocalDate.parse("1988-08-14"));
        Assertions.assertEquals(person.getBirthday().getMonth(),LocalDate.now().getMonth());
        Assertions.assertEquals(person.getBirthday().getDayOfMonth(),LocalDate.now().getDayOfMonth());
        Assertions.assertTrue(person.isBirthdayToday());
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
        personOne = new Person();
        personTwo = new Person();
        personOne.addToFamily(personTwo);
        Assertions.assertTrue(personOne.isFamily(personTwo));
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
        personOne = new Person();
        personTwo = new Person();
        personTwo.addToFamily(personOne);
        Assertions.assertTrue(personTwo.isFamily(personOne));
    }

    @Test
    public void isFamily_nonRelativePerson_false() {

        Assertions.assertFalse(person.isFamily(person));
    }

    @Test
    public void isFamily_relativePerson_true() {
        personTwo = new Person();
        person.addToFamily(personTwo);
        Assertions.assertTrue(person.isFamily(personTwo));
    }
}
