package school.cesar.eta.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class PersonTest {

   private Person person = new Person();


    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow() {
        person.setName("Jon");
        person.setLastName("Snow");
        Assertions.assertEquals("Jon",person.getFirstName());
        Assertions.assertEquals("Snow",person.getLastName());
        Assertions.assertEquals("JonSnow",person.getName());
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
        person = new Person() {
            @Override
            public LocalDate getNow() {
                return LocalDate.now();
            }
        };

        person.setBirthday(LocalDate.of(2020,7 ,19));
        Assertions.assertNotEquals(person.getBirthday().getMonth(),LocalDate.now().getMonth());
        Assertions.assertNotEquals(person.getBirthday().getDayOfMonth(),LocalDate.now().getDayOfMonth());
        Assertions.assertFalse(person.isBirthdayToday());

    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        person = new Person() {
            @Override
            public LocalDate getNow() {
                return LocalDate.now();
            }
        };
        person.setBirthday(LocalDate.of(2020, 8, 7));
        Assertions.assertEquals(person.getBirthday().getMonth(),LocalDate.now().getMonth());
        Assertions.assertNotEquals(person.getBirthday().getDayOfMonth(),LocalDate.now().getDayOfMonth());
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        person = new Person() {
            @Override
            public LocalDate getNow() {
                return LocalDate.now();
            }
        };
        person.setBirthday(LocalDate.now());
        Assertions.assertEquals(person.getBirthday().getMonth(),LocalDate.now().getMonth());
        Assertions.assertEquals(person.getBirthday().getDayOfMonth(),LocalDate.now().getDayOfMonth());
        Assertions.assertTrue(person.isBirthdayToday());
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
        Person firstPerson = new Person();
        firstPerson.setName("Paula");
        firstPerson.setLastName("Silva");
        firstPerson.setBirthday(LocalDate.of(1983, 8, 14));

        firstPerson.addToFamily(firstPerson);
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {

    }

    @Test
    public void isFamily_nonRelativePerson_false() {



    }

    @Test
    public void isFamily_relativePerson_true() {

    }
}
