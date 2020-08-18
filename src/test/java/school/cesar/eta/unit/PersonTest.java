package school.cesar.eta.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.fail;

public class PersonTest {

    private Person person;
    private Person famMember;
    private Person newMember;

    @BeforeEach
    public void setUp(){
        person = new Person(){
            @Override
            public LocalDate getNow() {
                return LocalDate.of(
                        1986,
                        5,
                        30);
            }
        };
    }

    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow() {
        person.setName("Jon");
        person.setLastName("Snow");
        Assertions.assertEquals("JonSnow", person.getName());
    }

    @Test
    public void getName_firstNameJonNoLastName_jon() {
        person.setName("Jon");
        Assertions.assertEquals("Jon", person.getName());
    }

    @Test
    public void getName_noFirstNameLastNameSnow_snow() {
        person.setLastName("Snow");
        Assertions.assertEquals("Snow", person.getName());
    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException() {
        Exception validacao = Assertions.assertThrows(RuntimeException.class, () -> {
            person.getName();
        });

        String expectedMsg = "Name must be filled";
        String actualMsg = validacao.getMessage();

        Assertions.assertEquals(expectedMsg, actualMsg);
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {
        person.setBirthday(LocalDate.of(1986,4,10));
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        person.setBirthday(LocalDate.of(1986, 5, 10));
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        person.setBirthday(LocalDate.of(1986, 5, 30));
        Assertions.assertTrue(person.isBirthdayToday());
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
        famMember = new Person();
        famMember.addToFamily(famMember);

        Assertions.assertTrue(famMember.isFamily(famMember));
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
        famMember = new Person();
        newMember = new Person();
        newMember.addToFamily(famMember);

        Assertions.assertTrue(newMember.isFamily(famMember));
    }

    @Test
    public void isFamily_nonRelativePerson_false() {
        famMember = new Person();

        Assertions.assertFalse(person.isFamily(person));
    }

    @Test
    public void isFamily_relativePerson_true() {
        newMember = new Person();
        person.addToFamily(newMember);

        Assertions.assertTrue(person.isFamily(newMember));
    }
}
