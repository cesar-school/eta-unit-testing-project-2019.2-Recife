package school.cesar.eta.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.fail;

public class PersonTest {

    private Person person;

    @BeforeEach
    public void  setupPerson(){
        person = new Person();
    }

    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow(){
        person.setName("Jon");
        person.setLastName("Snow");
        Assertions.assertEquals("JonSnow",person.getName());
    }

    @Test
    public void getName_firstNameJonNoLastName_jon() {
        person.setName("Jon");
        Assertions.assertEquals("Jon",person.getName());
    }

    @Test
    public void getName_noFirstNameLastNameSnow_snow() {
        person.setLastName("Snow");
        Assertions.assertEquals("Snow",person.getName());
    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException() {
        Assertions.assertThrows(RuntimeException.class, () -> person.getName());
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {
        person = new Person(){
            @Override
            public LocalDate getNow() {
                LocalDate dateNow = LocalDate.now();
                return LocalDate.of(dateNow.getYear(),dateNow.getMonthValue()+4,dateNow.getDayOfMonth()+3);
            }
        };
        person.setBirthday(LocalDate.now());
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        person = new Person(){
            @Override
            public LocalDate getNow() {
                LocalDate dateNow = LocalDate.now();
                return LocalDate.of(dateNow.getYear(),dateNow.getMonthValue(),dateNow.getDayOfMonth()-6);
            }
        };
        person.setBirthday(LocalDate.now());
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        person = new Person(){
            @Override
            public LocalDate getNow() {
                LocalDate dateNow = LocalDate.now();
                return LocalDate.of(dateNow.getYear(),dateNow.getMonthValue(),dateNow.getDayOfMonth());
            }
        };
        person.setBirthday(LocalDate.now());
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
